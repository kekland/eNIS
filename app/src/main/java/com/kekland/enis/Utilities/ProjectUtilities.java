package com.kekland.enis.Utilities;

import android.content.Context;

/**
 * Created by Gulnar on 01.10.2017.
 */

public class ProjectUtilities {
    public static Context base;
    public static float scale;

    public static int DefaultPadding;
    public static void Init(Context c) {
        base = c;
        scale = c.getResources().getDisplayMetrics().density;
        DefaultPadding = DpToPx(16);
    }

    public static String GetString(Integer id) {
        return base.getResources().getString(id);
    }

    public static int DpToPx(int dp) {
        return (int)(dp * scale + 0.5f);
    }
}
