package com.example.vano.example2;

import android.app.Activity;

public abstract class SimpleActivity extends Activity {
    abstract void parseJSON(String str, String requestCode);
}
