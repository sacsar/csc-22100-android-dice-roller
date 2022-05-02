package com.github.sacsar.hellodiceroller.mainmenu;

import com.google.common.collect.ImmutableList;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import java.util.List;

@InstallIn(SingletonComponent.class)
@Module
public class MenuItems {
  /**
   * NOTE: I was hoping that there was a way to extract the menu items from the navigation graph,
   * but it doesn't seem to be possible. Hence, just hard-coding everything.
   */
  private static final List<MainMenuItem> menuItems =
      ImmutableList.of(
          MainMenuItem.create(
              MainMenuFragmentDirections.actionMainMenuRecyclerViewToDiceRoller(), "Dice Roller"),
          MainMenuItem.create(
              MainMenuFragmentDirections.actionMainMenuFragmentToShoppingListFragment(),
              "Shopping List"));

  @Provides
  List<MainMenuItem> provideMenuItems() {
    return menuItems;
  }
}
