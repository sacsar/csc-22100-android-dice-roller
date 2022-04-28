package com.github.sacsar.hellodiceroller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;

/** A completely empty activity that exists to be used with {@link HiltFragmentUtils} */
@AndroidEntryPoint
public class HiltTestActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
}
