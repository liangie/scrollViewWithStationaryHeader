package com.leon.scrollviewwithstationaryheader;

import android.content.Context;

/**
 * Created by leon on 11/10/16.
 */
public class FunctionUtil {
    public static int dpToPx(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
