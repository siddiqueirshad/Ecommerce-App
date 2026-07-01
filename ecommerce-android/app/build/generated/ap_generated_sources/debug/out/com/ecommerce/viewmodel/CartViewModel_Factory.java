package com.ecommerce.viewmodel;

import com.ecommerce.repository.OrderRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class CartViewModel_Factory implements Factory<CartViewModel> {
  private final Provider<OrderRepository> orderRepositoryProvider;

  public CartViewModel_Factory(Provider<OrderRepository> orderRepositoryProvider) {
    this.orderRepositoryProvider = orderRepositoryProvider;
  }

  @Override
  public CartViewModel get() {
    return newInstance(orderRepositoryProvider.get());
  }

  public static CartViewModel_Factory create(Provider<OrderRepository> orderRepositoryProvider) {
    return new CartViewModel_Factory(orderRepositoryProvider);
  }

  public static CartViewModel newInstance(OrderRepository orderRepository) {
    return new CartViewModel(orderRepository);
  }
}
