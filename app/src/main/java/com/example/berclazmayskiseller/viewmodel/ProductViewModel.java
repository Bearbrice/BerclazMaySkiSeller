package com.example.berclazmayskiseller.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.berclazmayskiseller.database.entity.ProductEntity;
import com.example.berclazmayskiseller.database.repository.ProductRepository;
import com.example.berclazmayskiseller.util.OnAsyncEventListener;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ProductEntity> observableProduct;

    public ProductViewModel(@NonNull Application application,
                            final String productName, ProductRepository productRepository) {
        super(application);

        repository = productRepository;

        observableProduct = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableProduct.setValue(null);

        if(productName!=null){
            LiveData<ProductEntity> product = repository.getProduct(productName);

            // observe the changes of the product entity from the database and forward them
            observableProduct.addSource(product, observableProduct::setValue);
        }

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;

        private final ProductRepository repository;

        public Factory(@NonNull Application application, String productEmail) {
            this.application = application;
            this.email = productEmail;
            repository = ProductRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProductViewModel(application, email, repository);
        }
    }

    /**
     * Expose the LiveData ProductEntity query so the UI can observe it.
     */
    public LiveData<ProductEntity> getProduct() {
        return observableProduct;
    }

    public void createProduct(ProductEntity product, OnAsyncEventListener callback) {
        repository.insert(product, callback);
    }

    public void updateProduct(ProductEntity product, OnAsyncEventListener callback) {
        repository.update(product, callback);
    }

    public void deleteProduct(ProductEntity product, OnAsyncEventListener callback) {
        repository.delete(product, callback);
    }

    public void getProduct(String id, OnAsyncEventListener callback) {
        repository.getProductById(id);
    }
}
