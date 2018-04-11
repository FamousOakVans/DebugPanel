package com.janeandjohnstudio.appwidedebugpanel;

import android.content.Context;
import com.janeandjohnstudio.appwidedebugpanel.DebugOptionCheckBox.Callback;

public class OptionsFactory {

  public static DebugOptionCheckBox createCheckBox(Context context, String title, boolean isChecked,
      Callback callback) {
    return new DebugOptionCheckBox(context, title, isChecked, callback);
  }

}
