package com.ecommerce.di;

import com.ecommerce.network.ApiClient;
import com.ecommerce.utils.SharedPrefManager;
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
public final class AppModule_ProvideApiClientFactory implements Factory<ApiClient> {
  private final AppModule module;

  private final Provider<SharedPrefManager> prefManagerProvider;

  public AppModule_ProvideApiClientFactory(AppModule module,
      Provider<SharedPrefManager> prefManagerProvider) {
    this.module = module;
    this.prefManagerProvider = prefManagerProvider;
  }

  @Override
  public ApiClient get() {
    return provideApiClient(module, prefManagerProvider.get());
  }

  public static AppModule_ProvideApiClientFactory create(AppModule module,
      Provider<SharedPrefManager> prefManagerProvider) {
    return new AppModule_ProvideApiClientFactory(module, prefManagerProvider);
  }

  public static ApiClient provideApiClient(AppModule instance, SharedPrefManager prefManager) {
    return Preconditions.checkNotNullFromProvides(instance.provideApiClient(prefManager));
  }
}
