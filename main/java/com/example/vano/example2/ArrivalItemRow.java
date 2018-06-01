package com.example.vano.example2;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static android.view.Gravity.CENTER;

public class ArrivalItemRow extends TableRow {
    TextView busNumber;
    TextView busDir;
    TextView timeArr;
    public ArrivalItemRow(Context context,String busn, String busd, String time, int width) {
        super(context);
        busNumber = new TextView(context);
        busDir = new TextView(context);
        timeArr = new TextView(context);


        busNumber.setTextColor( Color.parseColor("#060003") );
        busDir.setTextColor( Color.parseColor("#060003") );
        timeArr.setTextColor( Color.parseColor("#060003") );

        busNumber.setGravity( CENTER );
        busDir.setGravity( CENTER );
        timeArr.setGravity( CENTER );



        this.addView(busNumber);
        this.addView(busDir);
        this.addView(timeArr);

        //int width = 1;
        //Resources.getSystem().getDisplayMetrics().widthPixels - 16;


        busNumber.setText(busn);
        busDir.setText(busd);
        timeArr.setText(time);
        this.setMinimumHeight(50);
        //this.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        busNumber.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        busDir.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        timeArr.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        busNumber.setMinimumWidth(width/7);
        busDir.setMinimumWidth(width/2);
        timeArr.setMinimumWidth(width*5/14);

        busDir.setMaxWidth( width/2 );
        //this.setOnTouchListener(  );
    }
}
