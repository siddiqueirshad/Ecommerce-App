package com.ecommerce.network;

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
public final class ApiClient_Factory implements Factory<ApiClient> {
  private final Provider<SharedPrefManager> prefManagerProvider;

  public ApiClient_Factory(Provider<SharedPrefManager> prefManagerProvider) {
    this.prefManagerProvider = prefManagerProvider;
  }

  @Override
  public ApiClient get() {
    return newInstance(prefManagerProvider.get());
  }

  public static ApiClient_Factory create(Provider<SharedPrefManager> prefManagerProvider) {
    return new ApiClient_Factory(prefManagerProvider);
  }

  public static ApiClient newInstance(SharedPrefManager prefManager) {
    return new ApiClient(prefManager);
  }
}
