package com.vukstankovic.cutout;

import android.os.Build;
import android.view.DisplayCutout;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class CDVCutout extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getStatusBarHeight")) {
            this.adjust(callbackContext);
            return true;
        }
        return false;
    }

    public static int convertDpToPixel(float dp) {
      DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
      float px = dp * (metrics.densityDpi / 160f);
      return Math.round(px);
    }

    private void adjust(CallbackContext callbackContext) {
      int statusBarHeight = 0;
      int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
      if (resourceId > 0) {
          statusBarHeight = getResources().getDimensionPixelSize(resourceId);
      }
      if(statusBarHeight > convertDpToPixel(24)) {
        RelativeLayout.LayoutParams topbarLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        topbarLp.setMargins(0, statusBarHeight, 0, 0);
        topbarLp.setLayoutParams(topbarLp);
      }
      callbackContext.success(statusBarHeight);
    }

    private void has(CallbackContext callbackContext) {
        boolean cutout = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            DisplayCutout displayCutout = cordova.getActivity().getWindow().getDecorView().getRootWindowInsets().getDisplayCutout();
            System.out.println(displayCutout);
            if (displayCutout != null) {
                cutout = true;
            }
        }
        callbackContext.success(cutout ? "true" : "false");
    }
}
