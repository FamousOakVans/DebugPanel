package com.janeandjohnstudio.appwidedebugpanel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DebugOptionCheckBox extends LinearLayout {

  public DebugOptionCheckBox(Context context, String title, boolean checked, final Callback callback) {
    super(context);

    setOrientation(LinearLayout.HORIZONTAL);
    setGravity(Gravity.CENTER_VERTICAL);

    LayoutInflater inflater = LayoutInflater.from(context);
    inflater.inflate(R.layout.debug_option_checkbox, this, true);

    LinearLayout ll = (LinearLayout) getChildAt(0);
    CheckBox checkBox = (CheckBox) ll.getChildAt(0);
    TextView description = (TextView) ll.getChildAt(1);

    checkBox.setChecked(checked);
    description.setText(title);
    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> callback.onClick(isChecked));
  }

  public DebugOptionCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public interface Callback {
    void onClick(boolean isChecked);
  }
}
