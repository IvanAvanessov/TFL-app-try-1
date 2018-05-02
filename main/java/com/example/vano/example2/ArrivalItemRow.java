package com.example.vano.example2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ArrivalItemRow extends TableRow {
    TextView busNumber;
    TextView busDir;
    TextView timeArr;
    public ArrivalItemRow(Context context,String busn, String busd, String time) {
        super(context);
        busNumber = new TextView(context);
        busDir = new TextView(context);
        timeArr = new TextView(context);

        this.addView(busNumber);
        this.addView(busDir);
        this.addView(timeArr);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;


        busNumber.setText(busn);
        busDir.setText(busd);
        timeArr.setText(time);
        this.setMinimumHeight(50);
        //this.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        busNumber.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        busDir.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        timeArr.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        busNumber.setMinimumWidth(width/6);
        busDir.setMinimumWidth(width/2);
        timeArr.setMinimumWidth(width/4);
    }
}
