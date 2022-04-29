package com.github.sacsar.hellodiceroller.shoppinglist.di;

import android.content.Context;
import androidx.room.Room;
import com.github.sacsar.hellodiceroller.shoppinglist.ShoppingListDatabase;
import com.github.sacsar.hellodiceroller.shoppinglist.dao.ShoppingListDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@InstallIn(SingletonComponent.class)
@Module
public class DatabaseModule {
  /** Provide an instance of the {@link ShoppingListDatabase} to hilt. */
  @Provides
  @Singleton
  public ShoppingListDatabase provideDatabase(@ApplicationContext Context context) {
    return Room.databaseBuilder(context, ShoppingListDatabase.class, "shoppingListDatabase")
        .build();
  }

  @Provides
  @Singleton
  public ShoppingListDao provideShoppingListDao(ShoppingListDatabase database) {
    return database.shoppingListDao();
  }
}
