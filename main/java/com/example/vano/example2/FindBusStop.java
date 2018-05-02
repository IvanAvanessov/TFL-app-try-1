package com.example.vano.example2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FindBusStop extends MainViewActivity {

    private Button backButton;
    private Button searchButton;
    private EditText searchText;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bus_stop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ////////////////////////////////////////////////
        searchText = (EditText) findViewById( R.id.searchText );
        resultText = (TextView) findViewById( R.id.resultsText );

        backButton = (Button) findViewById( R.id.backButton );
        backButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
            }
        } );

        searchButton = (Button) findViewById( R.id.searchButton );
        searchButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchString = searchText.getText().toString();
                if(searchString.length()<3){
                    Toast.makeText(FindBusStop.this, "Your Search message is too short", Toast.LENGTH_LONG).show();
                    return;
                }
                try{
                    searchString = URLEncoder.encode( searchString, "UTF-8" );
                    String answer ="", errorCode="";
                    new HttpRequest(FindBusStop.this).execute("https://api.tfl.gov.uk/StopPoint/Search/" + searchString.replace( "+", "%20" ),
                            answer, errorCode);

                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        } );

/*
        public void goBack(View view){


        }*/
    }

    public void parseJSON(String str) {
        resultText.setText( str );
        try {
            if(str.length()>0) {

                JSONObject response = new JSONObject( str );
                int responseSize = Integer.parseInt( response.get( "total" ).toString());

                if(responseSize > 0) {
                    //resultText.setText(response.get( "matches" ).toString());//String.valueOf( responseSize));
                    JSONArray responseArray = new JSONArray( response.get( "matches" ).toString() );

                    resultText.setText(responseArray.toString());//response.get( "matches" ).toString());
                }

            /*
            JSONObject[] finalAnswer = new JSONHandler().sortJsonArray(new JSONArray(str),"timeToStation");
            for (int i = 0; i < finalAnswer.length; i++) {
                JSONObject thisBus = finalAnswer[i];

                String minName = thisBus.get("vehicleId").toString();


                arrivalTable.addView(new ArrivalItemRow(this, thisBus.get("lineName").toString(), thisBus.get("destinationName").toString(),
                        formatHHMM(Integer.parseInt(thisBus.get("timeToStation").toString()))));
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                textTime.setText(dateFormat.format(date));
*/
                //display.concat();
                //thistext.setText(s);
                //if(JSONResponse.length()>0) {

                //thistext.setText(String.valueOf(JSONResponse.length()));
                //}
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    /*
    public void parseJSON(String str) {
        try {
            thistext.setText("");
            arrivalTable.removeAllViews();

            JSONObject[] finalAnswer = new JSONHandler().sortJsonArray(new JSONArray(str),"timeToStation");
            for (int i = 0; i < finalAnswer.length; i++) {
                JSONObject thisBus = finalAnswer[i];

                String minName = thisBus.get("vehicleId").toString();

                thistext.append(thisBus.get("lineName").toString() + " to " +
                        thisBus.get("destinationName").toString() + "; Arriving in " +
                        formatHHMM(Integer.parseInt(thisBus.get("timeToStation").toString())) + "\n");


                arrivalTable.addView(new ArrivalItemRow(this, thisBus.get("lineName").toString(), thisBus.get("destinationName").toString(),
                        formatHHMM(Integer.parseInt(thisBus.get("timeToStation").toString()))));
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                textTime.setText(dateFormat.format(date));

                //display.concat();
            }
            //thistext.setText(s);
            //if(JSONResponse.length()>0) {

            //thistext.setText(String.valueOf(JSONResponse.length()));
            //}

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/

}
