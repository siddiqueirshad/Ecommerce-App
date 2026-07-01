package com.ecommerce.di;

import android.content.Context;

import com.ecommerce.db.AppDatabase;
import com.ecommerce.db.ProductDao;
import com.ecommerce.network.ApiClient;
import com.ecommerce.network.ApiService;
import com.ecommerce.utils.SharedPrefManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public SharedPrefManager provideSharedPrefManager(@ApplicationContext Context context) {
        return new SharedPrefManager(context);
    }

    @Provides
    @Singleton
    public ApiClient provideApiClient(SharedPrefManager prefManager) {
        return new ApiClient(prefManager);
    }

    @Provides
    @Singleton
    public ApiService provideApiService(ApiClient apiClient) {
        return apiClient.getApiService();
    }

    @Provides
    @Singleton
    public AppDatabase provideDatabase(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    public ProductDao provideProductDao(AppDatabase database) {
        return database.productDao();
    }
}
