package com.ecommerce.di;

import com.ecommerce.network.ApiClient;
import com.ecommerce.network.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideApiServiceFactory implements Factory<ApiService> {
  private final AppModule module;

  private final Provider<ApiClient> apiClientProvider;

  public AppModule_ProvideApiServiceFactory(AppModule module,
      Provider<ApiClient> apiClientProvider) {
    this.module = module;
    this.apiClientProvider = apiClientProvider;
  }

  @Override
  public ApiService get() {
    return provideApiService(module, apiClientProvider.get());
  }

  public static AppModule_ProvideApiServiceFactory create(AppModule module,
      Provider<ApiClient> apiClientProvider) {
    return new AppModule_ProvideApiServiceFactory(module, apiClientProvider);
  }

  public static ApiService provideApiService(AppModule instance, ApiClient apiClient) {
    return Preconditions.checkNotNullFromProvides(instance.provideApiService(apiClient));
  }
}
