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

import com.example.berclazmayskiseller.db.entity.ProductEntity;
import com.example.berclazmayskiseller.db.repository.ProductRepository;

/**
 * Class to display the list of products
 */
public class ProductListViewModel extends AndroidViewModel {

    private ProductRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<ProductEntity>> observableProducts;

    public ProductListViewModel(@NonNull Application application, ProductRepository productRepository) {
        super(application);

        repository = productRepository;

        applicationContext = application.getApplicationContext();

        observableProducts = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableProducts.setValue(null);

        LiveData<List<ProductEntity>> products = repository.getAllProducts(applicationContext);

        // observe the changes of the entities from the database and forward them
        observableProducts.addSource(products, observableProducts::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final ProductRepository productRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            productRepository = ProductRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProductListViewModel(application, productRepository);
        }
    }

    /**
     * Expose the LiveData ProductEntities query so the UI can observe it.
     */
    public LiveData<List<ProductEntity>> getProducts() {
        return observableProducts;
    }
}
