package com.janeandjohnstudio.appwidedebugpanel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import com.janeandjohnstudio.appwidedebugpanel.DebugOptionCheckBox.Callback;
import java.util.ArrayList;
import java.util.List;

public class OptionsFactory {

  public static DebugOptionCheckBox createCheckBox(Context context, String title, boolean isChecked, Callback callback) {
    return new DebugOptionCheckBox(context, title, isChecked, callback);
  }

}
