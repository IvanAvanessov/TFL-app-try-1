package com.example.vano.example2;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TempBusStop extends SimpleActivity{

    String naptanID;
    TableLayout arrivalTable;
    private Button cancelButton;
    private Button addButton;
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_bus_popup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( dm );
        int height = dm.heightPixels;
        width = dm.widthPixels;
        getWindow().setLayout( (int) (width * 1), (int) (height * 0.6));
        naptanID = getIntent().getStringExtra("NAPTAN_ID");

        arrivalTable = (TableLayout) findViewById(R.id.arrivalTable);

        cancelButton = (Button) findViewById(R.id.cancel);
        addButton = (Button) findViewById(R.id.addBusStop);

        cancelButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
            }
        } );





        ArrivalItemRow nameRow = new ArrivalItemRow( this, "Bus", "Direction", "Time",(int) (width * 0.60) );

        new HttpRequest(TempBusStop.this).execute("https://api.tfl.gov.uk/StopPoint/" + naptanID + "/arrivals", "", "1", "");


        arrivalTable.removeAllViews();
        arrivalTable.addView( nameRow );


    }



    @Override
    public void parseJSON(String str, String requestCode){
        try {

            JSONObject[] finalAnswer = new JSONHandler().sortJsonArray(new JSONArray(str),"timeToStation");
            for (int i = 0; i < finalAnswer.length; i++) {
                JSONObject thisBus = finalAnswer[i];

                String minName = thisBus.get("vehicleId").toString();

                ArrivalItemRow  temp = new ArrivalItemRow( this, thisBus.get("lineName").toString(), thisBus.get("destinationName").toString(),
                        formatHHMM(Integer.parseInt(thisBus.get("timeToStation").toString())), (int) (width*0.60));

                temp.setBackgroundResource( R.drawable.row_border );
                arrivalTable.addView(temp);
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String formatHHMM(int seconds){
        String min;
        min = Integer.toString(seconds/60);
        String sec;
        if (seconds%60 >= 10){
            sec = Integer.toString(seconds%60);
        }
        else{
            sec = "0" + Integer.toString(seconds%60);
        }
        return (min + ":" + sec);
    }
}
