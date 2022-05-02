package com.github.sacsar.hellodiceroller.shoppinglist.di;

import com.github.sacsar.hellodiceroller.shoppinglist.ShoppingListAdapter;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class ShoppingListModule {

  @Provides
  @Singleton
  public ShoppingListAdapter.ShoppingListItemDiff provideDiff() {
    return new ShoppingListAdapter.ShoppingListItemDiff();
  }
}
