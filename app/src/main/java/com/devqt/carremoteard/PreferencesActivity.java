package com.devqt.carremoteard;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Button;

import java.util.List;

public class PreferencesActivity extends PreferenceActivity {

    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    if (hasHeaders()) {
        Button button = new Button(this);
        button.setText(R.string.some);
        setListFooter(button);
    }
}

    @Override
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        loadHeadersFromResource(R.xml.headers, target);
    }


public static class ArdFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);
        addPreferencesFromResource(R.xml.preferences);
    }
}
}