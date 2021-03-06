package com.tpcstld.jetris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

public class ResetSettingsPreference extends DialogPreference {

	private Context mContext;

	@SuppressLint("Recycle")
	public ResetSettingsPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		int[] attrsArray = new int[] { android.R.attr.title, // 0
				android.R.attr.summary, // 1
		};

		TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
		setTitle(ta.getString(0));
		setDialogMessage(Constants.CONFIRM_SETTINGS_MESSAGE);
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(android.R.string.cancel);
		setDialogIcon(null);
	}

	public void onDialogClosed(boolean positiveResult) {
		if (positiveResult) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(mContext);
			SharedPreferences.Editor editor = settings.edit();
			// Changing hold to tap
			for (String key : Constants.defaultBooleanSettings.keySet()) {
				editor.putBoolean(key,
						Constants.defaultBooleanSettings.get(key));
			}
			for (String settingName : Constants.defaultSettings.keySet()) {
				editor.putString(settingName,
						Constants.defaultSettings.get(settingName));
			}
			editor.commit();
			SettingsFragment.setSummariesString(Constants.defaultSettings,
					settings);
			SettingsFragment.setBooleanStatus(Constants.defaultBooleanSettings,
					settings);
		}
	}
}
