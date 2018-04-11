package com.janeandjohnstudio.appwidedebugpanel;

import android.app.Application;
import android.content.Context;


public class CustomApp extends Application {

  private static Context APPLICATION;

  @Override
  public void onCreate() {
    super.onCreate();

    APPLICATION = this;

    registerActivityLifecycleCallbacks(new DebugPanelInjector());
  }

  public static Context getContext() {
    return APPLICATION;
  }

}
