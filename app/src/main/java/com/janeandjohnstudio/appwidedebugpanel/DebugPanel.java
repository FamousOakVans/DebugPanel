package com.janeandjohnstudio.appwidedebugpanel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"InflateParams", "StaticFieldLeak", "CheckResult", "ApplySharedPref",
    "WeakerAccess", "SimplifiableIfStatement", "unused"})
public class DebugPanel {

  private static DebugPanel INSTANCE;

  private long flags = 0;
  public static final long OVERLAY = 1 << 0;
  public static final long PORTFOLIO = 1 << 1;
  public static final long OFFERS = 1 << 2;
  public static final long AUTH = 1 << 3;

  private Activity a;

  private DrawerLayout drawer;
  private ViewGroup root;
  private LinearLayout llOverlay;
  private TextView tvActivity;
  private TextView tvRequest;
  private LinearLayout content;

  private Subject<String> reqSubject = BehaviorSubject.create();

  private DebugPanel() {
    reqSubject.onNext("None");
  }

  public synchronized static DebugPanel getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new DebugPanel();
    }
    return INSTANCE;
  }

  public long getFlags() {
    return flags;
  }

  public Subject<String> getReqSubject() {
    return reqSubject;
  }

  public void inject(Activity activity) {
    this.a = activity;
    ViewGroup megaRoot = activity.findViewById(android.R.id.content);
    root = (ViewGroup) megaRoot.getChildAt(0);
    megaRoot.removeAllViews();

    if (llOverlay == null) {
      initOverlay();
    }
    if (drawer == null) {
      initDrawer();
      loadOptions(getDefault());
    }

    tvActivity.setText(activity.getClass().getSimpleName());

    drawer.addView(root, 0, root.getLayoutParams());
    activity.setContentView(drawer);
  }

  private void initDrawer() {
    drawer = new DrawerLayout(CustomApp.getContext());
    content = (LinearLayout) LayoutInflater.from(CustomApp.getContext())
        .inflate(R.layout.nav_container, null);

    drawer.setLayoutParams(new DrawerLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));

    DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
    lp.gravity = Gravity.END;
    content.setLayoutParams(lp);
    drawer.addView(content);
    drawer.addView(llOverlay, 0);
    llOverlay.setVisibility(GONE);
  }

  private void initOverlay() {
    llOverlay = (LinearLayout) LayoutInflater.from(CustomApp.getContext())
        .inflate(R.layout.ll_overlay, null);
    tvActivity = llOverlay.findViewById(R.id.tv_activity);
    tvRequest = llOverlay.findViewById(R.id.tv_request);
    reqSubject.subscribe(this::onRequest);
  }

  private void loadOptions(List<View> options) {
    List<View> save = new ArrayList<View>() {{
      add(content.getChildAt(0));
      add(content.getChildAt(1));
      add(content.getChildAt(2));
    }};
    content.removeAllViews();
    for (View v : save) {
      content.addView(v);
    }

    for (View o : options) {
      content.addView(o);
    }
  }

  private void toast(String text) {
    Toast.makeText(a, text, Toast.LENGTH_SHORT).show();
  }

  private void onRequest(String name) {
    tvRequest.setText(name);
  }

  public void remove() {
    a.setContentView(root);
    root = null;
  }

  private List<View> getDefault() {
    List<View> options = new ArrayList<>();

    options.add(
        OptionsFactory.createCheckBox(
            CustomApp.getContext(),
            "Показывать оверлэй",
            (flags & OVERLAY) != 0,
            isChecked -> {
              flags ^= OVERLAY;
              llOverlay.setVisibility(isChecked ? VISIBLE : GONE);
            }
        ));

    options.add(divider());
    options.add(header("Мокать"));

    options.add(
        OptionsFactory.createCheckBox(
            CustomApp.getContext(),
            "Портфолио",
            (flags & PORTFOLIO) != 0,
            isChecked -> {
              flags ^= PORTFOLIO;
              toast("Портфолио");
            }
        ));

    options.add(
        OptionsFactory.createCheckBox(
            CustomApp.getContext(),
            "Предложения",
            (flags & OFFERS) != 0,
            isChecked -> {
              flags ^= OFFERS;
              toast("Предложения");
            }
        ));

    options.add(
        OptionsFactory.createCheckBox(
            CustomApp.getContext(),
            "Авторизацию полностью",
            (flags & AUTH) != 0,
            isChecked -> {
              flags ^= AUTH;
              toast("Авторизация");
            }
        )
    );

    options.add(divider());

    return options;
  }

  public View header(String s) {
    DisplayMetrics metrics = new DisplayMetrics();
    a.getWindowManager().getDefaultDisplay().getMetrics(metrics);

    TextView title = new TextView(CustomApp.getContext());
    title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
    title.setTextColor(Color.BLACK);
    title.setText(s);

    LayoutParams lp = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
    float mLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, metrics);
    float mTop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, metrics);
    float mBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, metrics);

    lp.setMargins((int) mLeft, (int) mTop, 0, (int) mBottom);
    title.setLayoutParams(lp);
    return title;
  }

  public View divider() {
    View div = new View(CustomApp.getContext());
    div.setBackgroundColor(Color.BLACK);
    LayoutParams lp = new LayoutParams(MATCH_PARENT, 1);
    div.setLayoutParams(lp);
    return div;
  }
}
