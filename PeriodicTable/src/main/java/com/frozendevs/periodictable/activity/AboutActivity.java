package com.frozendevs.periodictable.activity;

import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import com.frozendevs.periodictable.R;

public class AboutActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.about);

        String versionName = null;
        PackageManager packageManager = getPackageManager();
        if (packageManager != null) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
                versionName = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                versionName = "N/A";
            }
        }
        findPreference("version").setSummary(versionName);

        findPreference("licences").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                WebView webView = new WebView(getApplicationContext());
                webView.loadUrl("file:///android_asset/html/licenses.html");

                AlertDialog dialog = new AlertDialog.Builder(AboutActivity.this).create();
                dialog.setTitle(R.string.preference_open_source_licences);
                dialog.setView(webView);
                dialog.show();

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
