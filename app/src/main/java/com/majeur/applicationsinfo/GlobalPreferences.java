package com.majeur.applicationsinfo;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.support.v4.app.*;
import android.view.*;

public class GlobalPreferences extends PreferenceActivity
implements SharedPreferences.OnSharedPreferenceChangeListener
{
	AlertDialog.Builder ad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_preferences);

		setupActionBar();
		updateListPrefSummary();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
			.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
			.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateListPrefSummary() {
		ListPreference mSort = (ListPreference) findPreference("pref_sort_by");
		CharSequence entrySort = mSort.getEntry();
		mSort.setSummary("Current Sort: " + entrySort);

		ListPreference prefsTheme = (ListPreference) findPreference("prefs_theme");
		CharSequence entryTheme = prefsTheme.getEntry();
		prefsTheme.setSummary("Current Theme: " + entryTheme);

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

		if (key.equals("pref_sort_by")) {
			updateListPrefSummary();
		} else if (key.equals("prefs_theme")) {
			updateListPrefSummary();
		}

	}

}

