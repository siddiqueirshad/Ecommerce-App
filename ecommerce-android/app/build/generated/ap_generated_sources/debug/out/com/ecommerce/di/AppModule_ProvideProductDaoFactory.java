package com.ecommerce.di;

import com.ecommerce.db.AppDatabase;
import com.ecommerce.db.ProductDao;
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
public final class AppModule_ProvideProductDaoFactory implements Factory<ProductDao> {
  private final AppModule module;

  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideProductDaoFactory(AppModule module,
      Provider<AppDatabase> databaseProvider) {
    this.module = module;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ProductDao get() {
    return provideProductDao(module, databaseProvider.get());
  }

  public static AppModule_ProvideProductDaoFactory create(AppModule module,
      Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideProductDaoFactory(module, databaseProvider);
  }

  public static ProductDao provideProductDao(AppModule instance, AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(instance.provideProductDao(database));
  }
}
