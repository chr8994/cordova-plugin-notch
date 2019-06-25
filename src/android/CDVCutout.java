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

    private void adjust(CallbackContext callbackContext) {
      int statusBarHeight = cordova.getActivity().getWindow().getDecorView().getRootWindowInsets().getDisplayCutout().getSafeInsetTop();
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
