/*
 * Copyright (C) 2024 Project-Pixelstar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.star.field.category;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.internal.logging.nano.MetricsProto;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable;
import com.android.settingslib.search.SearchIndexable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SearchIndexable
public class Misc extends SettingsPreferenceFragment 
            implements Preference.OnPreferenceChangeListener {

    private static final String KEY_POCKET_CATEGORY = "pocket_mode_category";
    private static final String KEY_POCKET_DETECTION = "pocket_judge";

    private PreferenceCategory mPocketCategory;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.category_misc);
        final Context context = getContext();
        PreferenceScreen prefSet = getPreferenceScreen();
        final Resources res = context.getResources();
        final PreferenceScreen prefScreen = getPreferenceScreen();
        mPocketCategory = (PreferenceCategory) findPreference(KEY_POCKET_CATEGORY);

        boolean pocketDetectionAvailable = res.getBoolean(
                com.android.internal.R.bool.config_pocketModeSupported);
        if (!pocketDetectionAvailable) {
            prefScreen.removePreference(mPocketCategory);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }  

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.STARFIELD;
    }
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(
                        Context context, boolean enabled) {
                    final SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.category_misc;
                    return Arrays.asList(sir);
                }

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    final List<String> keys = super.getNonIndexableKeys(context);
                    final Resources res = context.getResources();
                boolean pocketDetectionAvailable = res.getBoolean(
                        com.android.internal.R.bool.config_pocketModeSupported);
                if (!pocketDetectionAvailable) {
                    keys.add(KEY_POCKET_DETECTION);
                }
                    return keys;
                }
            };
}