package com.ecommerce.repository;

import com.ecommerce.network.ApiService;
import com.ecommerce.utils.SharedPrefManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<SharedPrefManager> prefManagerProvider;

  public AuthRepository_Factory(Provider<ApiService> apiServiceProvider,
      Provider<SharedPrefManager> prefManagerProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.prefManagerProvider = prefManagerProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(apiServiceProvider.get(), prefManagerProvider.get());
  }

  public static AuthRepository_Factory create(Provider<ApiService> apiServiceProvider,
      Provider<SharedPrefManager> prefManagerProvider) {
    return new AuthRepository_Factory(apiServiceProvider, prefManagerProvider);
  }

  public static AuthRepository newInstance(ApiService apiService, SharedPrefManager prefManager) {
    return new AuthRepository(apiService, prefManager);
  }
}
