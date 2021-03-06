package com.example.berclazmayskiseller.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.berclazmayskiseller.database.entity.OrderEntity;
import com.example.berclazmayskiseller.database.repository.OrderRepository;
import com.example.berclazmayskiseller.util.OnAsyncEventListener;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<OrderEntity> observableOrder;

    public OrderViewModel(@NonNull Application application,
                          final String accountId, OrderRepository orderRepository) {
        super(application);

        repository = orderRepository;

        observableOrder = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableOrder.setValue(null);

        if (accountId != null) {
            LiveData<OrderEntity> order = repository.getByOwner(accountId);

            // observe the changes of the order entity from the database and forward them
            observableOrder.addSource(order, observableOrder::setValue);
        }
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String accountId;

        private final OrderRepository repository;

        public Factory(@NonNull Application application, String accountId) {
            this.application = application;
            this.accountId = accountId;
            repository = OrderRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new OrderViewModel(application, accountId, repository);
        }
    }

    /**
     * Expose the LiveData OrderEntity query so the UI can observe it.
     */
    public LiveData<OrderEntity> getOrder() {
        return observableOrder;
    }

    public void createOrder(OrderEntity order, OnAsyncEventListener callback) {
        repository.insert(order, callback);
    }

    public void updateOrder(OrderEntity order, OnAsyncEventListener callback) {
        repository.update(order, callback);
    }

    public void deleteOrder(OrderEntity order, OnAsyncEventListener callback) {
        repository.delete(order, callback);
    }
}
