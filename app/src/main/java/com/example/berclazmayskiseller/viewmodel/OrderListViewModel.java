package com.example.berclazmayskiseller.viewmodel;

import android.app.Application;
import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.berclazmayskiseller.db.entity.OrderEntity;
import com.example.berclazmayskiseller.db.repository.OrderRepository;

/** Class to display the list of orders */
public class OrderListViewModel extends AndroidViewModel {

    private OrderRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<OrderEntity>> observableOrders;

    public OrderListViewModel(@NonNull Application application, OrderRepository orderRepository, String email) {
        super(application);

        repository = orderRepository;

        applicationContext = application.getApplicationContext();

        observableOrders = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableOrders.setValue(null);

        LiveData<List<OrderEntity>> orders = repository.getByOwners(email, applicationContext);

        // observe the changes of the entities from the database and forward them
        observableOrders.addSource(orders, observableOrders::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final OrderRepository orderRepository;

        private final String email;

        public Factory(@NonNull Application application, String email) {
            this.application = application;
            orderRepository = OrderRepository.getInstance();
            this.email=email;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new OrderListViewModel(application, orderRepository, email);
        }
    }

    /**
     * Expose the LiveData OrderEntities query so the UI can observe it.
     */
    public LiveData<List<OrderEntity>> getOrders() {
        return observableOrders;
    }
}
