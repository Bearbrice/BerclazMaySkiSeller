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

import com.example.berclazmayskiseller.BaseApp;
import com.example.berclazmayskiseller.database.entity.OrderEntity;
import com.example.berclazmayskiseller.database.repository.OrderRepository;

/**
 * Class to display the list of orders
 */
public class OrderListViewModel extends AndroidViewModel {

    private static final String TAG = "OrderListViewModel";

    private OrderRepository repository;

//    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<OrderEntity>> observableOrders;

    public OrderListViewModel(@NonNull Application application, OrderRepository orderRepository, String ownerId) {
        super(application);

        repository = orderRepository;

//        applicationContext = application.getApplicationContext();

        observableOrders = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableOrders.setValue(null);

        LiveData<List<OrderEntity>> orders = repository.getByOwners(ownerId);

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

        private final String ownerId;

        public Factory(@NonNull Application application, String ownerId) {
            this.application = application;
//            orderRepository = OrderRepository.getInstance();
            orderRepository = ((BaseApp) application).getOrderRepository();
            this.ownerId = ownerId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new OrderListViewModel(application, orderRepository, ownerId);
        }
    }

    /**
     * Expose the LiveData OrderEntities query so the UI can observe it.
     */
    public LiveData<List<OrderEntity>> getOrders() {
        return observableOrders;
    }
}
