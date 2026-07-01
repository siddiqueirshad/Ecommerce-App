package com.ecommerce.di;

import android.content.Context;
import com.ecommerce.utils.SharedPrefManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideSharedPrefManagerFactory implements Factory<SharedPrefManager> {
  private final AppModule module;

  private final Provider<Context> contextProvider;

  public AppModule_ProvideSharedPrefManagerFactory(AppModule module,
      Provider<Context> contextProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
  }

  @Override
  public SharedPrefManager get() {
    return provideSharedPrefManager(module, contextProvider.get());
  }

  public static AppModule_ProvideSharedPrefManagerFactory create(AppModule module,
      Provider<Context> contextProvider) {
    return new AppModule_ProvideSharedPrefManagerFactory(module, contextProvider);
  }

  public static SharedPrefManager provideSharedPrefManager(AppModule instance, Context context) {
    return Preconditions.checkNotNullFromProvides(instance.provideSharedPrefManager(context));
  }
}
