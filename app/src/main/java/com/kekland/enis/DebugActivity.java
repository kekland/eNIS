package com.kekland.enis;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.kekland.enis.NIS.NISData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Admin on 10/09/2017.
 */

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        LinkedHashMap<String, ?> map = new LinkedHashMap<>(prefs.getAll());
        Object[] keys = map.keySet().toArray();

        Arrays.sort(keys);
        String text = "";
        for(int i = 0; i < keys.length; i++) {
            String key = keys[i].toString();
            String val = map.get(key).toString();
            text += key + " : " + val + "\n\n";
        }

        text += "\n\n\n\n\n\nCurrent NISData values : \n";
        text += NISData.getPIN() + "\n";
        text += NISData.getPassword() + "\n";
        text += NISData.getSchool() + "\n";
        text += NISData.getNickname() + "\n";
        text += NISData.getRole() + "\n";
        text += NISData.getDiary() + "\n";
        ((TextView)findViewById(R.id.debugText)).setText(text);
    }
}
