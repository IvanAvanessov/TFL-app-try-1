package com.example.vano.example2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;

public class tempBusDialog extends AlertDialog {

    protected tempBusDialog(@NonNull Context context) {
        super( context );
        setContentView( R.layout.temp_bus_popup );

        DisplayMetrics dm = new DisplayMetrics();
        /*
        getWindowManager().getDefaultDisplay().getMetrics( dm )
        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout( (int) (width * 0.9), (int) (height * 0.6));*/
    }
}
