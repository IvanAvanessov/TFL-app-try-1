package com.example.vano.example2;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class SearchItemRow extends TableRow {
    TextView transpType;
    TextView stopName;
    TextView transpIDs;
    String  stopNaptanID;
    public SearchItemRow(Context context, String trType, String name, String IDs, String naptan) {
        super(context);
        transpType = new TextView(context);
        stopName = new TextView(context);
        transpIDs = new TextView(context);
        stopNaptanID = naptan;

        transpType.setTextAlignment( View.TEXT_ALIGNMENT_CENTER);
        this.addView(transpType);
        this.addView(stopName);
        this.addView(transpIDs);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels-20;
        stopName.setMaxWidth( width/2 );
        stopName.setMinWidth( width/2 );

        transpType.setText(trType);
        stopName.setText(name);
        transpIDs.setText(IDs);
        this.setMinimumHeight(50);
        //this.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //busNumber.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //busDir.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //timeArr.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        transpType.setMaxWidth(width/6);
        transpType.setMinWidth(width/6);

        //stopName.setMinimumWidth(width/2);
        transpIDs.setMinWidth(width/6*2);
        transpIDs.setMaxWidth(width/6*2);
        this.setClickable( true );
        this.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );
    }


}
