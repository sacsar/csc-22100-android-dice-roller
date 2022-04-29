package com.github.sacsar.hellodiceroller.mainmenu;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import java.util.Collections;
import java.util.List;

@InstallIn(SingletonComponent.class)
@Module
public class MenuItems {
  private static final List<MainMenuItem> menuItems =
      Collections.singletonList(
          MainMenuItem.create(
              MainMenuFragmentDirections.actionMainMenuRecyclerViewToDiceRoller(), "Dice Roller"));

  @Provides
  List<MainMenuItem> provideMenuItems() {
    return menuItems;
  }
}
