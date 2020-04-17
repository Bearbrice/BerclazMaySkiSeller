package com.example.berclazmayskiseller.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.berclazmayskiseller.db.entity.ProductEntity;
import com.example.berclazmayskiseller.db.repository.ProductRepository;
import com.example.berclazmayskiseller.util.OnAsyncEventListener;

public class ProductIdViewModel extends AndroidViewModel {

    private ProductRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ProductEntity> observableProduct;

    public ProductIdViewModel(@NonNull Application application,
                              final int id, ProductRepository productRepository) {
        super(application);

        repository = productRepository;

        applicationContext = application.getApplicationContext();

        observableProduct = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableProduct.setValue(null);

        LiveData<ProductEntity> product = repository.getProductById(id, applicationContext);

        // observe the changes of the product entity from the database and forward them
        observableProduct.addSource(product, observableProduct::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int id;

        private final ProductRepository repository;

        public Factory(@NonNull Application application, int id) {
            this.application = application;
            this.id = id;
            repository = ProductRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProductIdViewModel(application, id, repository);
        }
    }

    /**
     * Expose the LiveData ProductEntity query so the UI can observe it.
     */
    public LiveData<ProductEntity> getProduct() {
        return observableProduct;
    }

    public void createProduct(ProductEntity product, OnAsyncEventListener callback) {
        repository.insert(product, callback, applicationContext);
    }

    public void updateProduct(ProductEntity product, OnAsyncEventListener callback) {
        repository.update(product, callback, applicationContext);
    }

    public void deleteProduct(ProductEntity product, OnAsyncEventListener callback) {
        repository.delete(product, callback, applicationContext);
    }

    public void getProduct(int id, OnAsyncEventListener callback) {
        repository.getProductById(id, applicationContext);
    }
}
