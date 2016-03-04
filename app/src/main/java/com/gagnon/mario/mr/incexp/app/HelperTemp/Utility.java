package com.gagnon.mario.mr.incexp.app.HelperTemp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gagnon.mario.mr.incexp.app.R;

/**
 * Created by mario on 2/24/2016.
 */
public class Utility {
    public static String getPreferredDefaultCurrency(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_default_currency_key),
                context.getString(R.string.pref_CAD_currency));
    }
}
