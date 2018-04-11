package com.janeandjohnstudio.appwidedebugpanel;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;


public class DebugPanelInjector implements Application.ActivityLifecycleCallbacks {

  private DebugPanel panel = DebugPanel.getInstance();

  @Override
  public void onActivityCreated(Activity activity, Bundle bundle) {}

  @Override
  public void onActivityStarted(Activity activity) {}

  @Override
  public void onActivityResumed(Activity activity) {
    panel.inject(activity);
  }

  @Override
  public void onActivityPaused(Activity activity) {
    panel.remove();
  }

  @Override
  public void onActivityStopped(Activity activity) {}

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}

  @Override
  public void onActivityDestroyed(Activity activity) {}
}
