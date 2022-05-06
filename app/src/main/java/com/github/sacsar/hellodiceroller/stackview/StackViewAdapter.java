package com.github.sacsar.hellodiceroller.stackview;

import android.content.Context;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;

public class StackViewAdapter extends ArrayAdapter<Integer> {
  public StackViewAdapter(@NonNull Context context, int resource, int targetId) {
    super(context, resource, targetId);
  }
}
