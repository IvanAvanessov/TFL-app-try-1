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
                resultText.setText( "" );
                String searchString = searchText.getText().toString();
                if(searchString.length()<3){
                    Toast.makeText(FindBusStop.this, "Your Search message is too short", Toast.LENGTH_LONG).show();
                    return;
                }
                //check if need to look for sms code

                try{
                    searchString = URLEncoder.encode( searchString, "UTF-8" );
                    String answer ="", errorCode="";
                    new HttpRequest(FindBusStop.this).execute("https://api.tfl.gov.uk/StopPoint/Search/" + searchString.replace( "+", "%20" ),
                            answer,"2", errorCode);

                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        } );

/*
        public void goBack(View view){


        }*/
    }

    public void parseJSON(String str, String reqID) {
        //resultText.setText( str );

        //Just looking for list of busses here
        if(reqID.equals("1")){
            parseAsFinalBusStop(str);
        }

        else if (reqID.equals("2")){

            try {

                JSONObject searchRes = new JSONObject( str );
                int totalMatches = Integer.parseInt( searchRes.get("total").toString());
                if (totalMatches == 0) { //0 matches returned
                    resultText.setText( "There is no results. Please check your internet and query and try again" );
                } else {
                    resultText.setText("");
                    JSONArray tempResults = new JSONArray(searchRes.get("matches").toString());
                    JSONObject[] matchDetails = new JSONObject[totalMatches];
                    for (int i = 0; i < totalMatches && i < 15; i ++) { //do not start more than 15 asynctasks
                        matchDetails[i] = (JSONObject) tempResults.get( i );

                        //resultText.append( matchDetails[i].get( "id" ).toString() );
                        new HttpRequest(FindBusStop.this).execute("https://api.tfl.gov.uk/StopPoint/" + matchDetails[i].get( "id" ).toString(),
                                "","3", "");

                        //resultText.append("\n");
                    }
                }


            } catch (JSONException e){
                e.printStackTrace();
            }
            //result of a first stage search
        }
        else if (reqID.equals("3")){ //
            try {

                JSONObject searchRes = new JSONObject( str );
                //resultText.append( searchRes.get("modes").toString().equals( "[\"bus\"]" ) + " " + searchRes.get("modes").toString() +  "ZZZ\n");
                if(searchRes.get("modes").toString().equals( "[\"bus\"]" )){
                    parseAsBus(searchRes.get("children").toString());
                }
                else {
                    JSONArray tempResults = new JSONArray( searchRes.get( "children" ).toString() );
                    int totalStops = tempResults.length();
                    JSONObject[] matchDetails = new JSONObject[totalStops];
                    for (int i = 0; i < totalStops; i++) {
                        matchDetails[i] = (JSONObject) tempResults.get( i );
                        String commonName = matchDetails[i].get( "commonName" ).toString();
                        String transport;
                        //resultText.append( matchDetails[i].get( "modes" ).toString() + "S" + " [\"bus\"]" );
                        if (matchDetails[i].get( "modes" ).toString().equals( "[\"bus\"]" )) {
                            parseAsBus(matchDetails[i].get("children").toString());

                        } else if (matchDetails[i].get( "modes" ).toString().equals( "[\"cable-car\"]" )) {
                            resultText.append( "CABLE_CAR_SKIP" );
                            resultText.append( "\n" );
                            //skip
                        } else if (matchDetails[i].get( "modes" ).toString().equals( "[\"tube\"]" )) {
                            commonName = matchDetails[i].get( "commonName" ).toString();

                            JSONArray tempLineGroup = new JSONArray( matchDetails[i].get( "lineGroup" ).toString() );
                            JSONObject lineGroup = tempLineGroup.getJSONObject( 0 );
                            String lineNames = lineGroup.get( "lineIdentifier" ).toString();
                            resultText.append( commonName + " " + " " + lineNames + " " + "Tube" + " "
                                    + matchDetails[i].get( "naptanId" ).toString() );
                            resultText.append( "\n" );

                        } else if (matchDetails[i].get( "modes" ).toString().equals( "[\"river-bus\"]" )){
                            resultText.append( "RIVER_BUS_SKIP  \n" );
                        }
                    }
                    //}

                }
            } catch (JSONException e){
                e.printStackTrace();
            }

        }
        else if (reqID.equals("4")){

        }

    }
    private void parseAsBus(String str){
        try {
            JSONArray tempResults = new JSONArray( str );
            int totalStops = tempResults.length();
            JSONObject[] busStops = new JSONObject[totalStops];

            for (int i = 0; i < totalStops; i++) {
                busStops[i] = (JSONObject) tempResults.get( i );
                String commonName = busStops[i].get( "commonName" ).toString();
                String naptanID = busStops[i].get( "naptanId" ).toString();
                String transport = "Bus";
                String busNumbers;
                String indicator = busStops[i].get( "indicator" ).toString();

                JSONArray tempLineGroup = new JSONArray( busStops[i].get( "lineGroup" ).toString() );
                JSONObject lineGroup = tempLineGroup.getJSONObject( 0 );
                busNumbers = lineGroup.get( "lineIdentifier" ).toString();

                resultText.append( commonName + " " + transport + " " + busNumbers + " " + indicator + " "
                            + naptanID);
                resultText.append( "\n" );
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }


    private void parseAsFinalBusStop(String str){
        if(str.length()>0) {
            try {
                JSONObject response = new JSONObject( str );
                int responseSize = Integer.parseInt( response.get( "total" ).toString());
                if(responseSize > 0) {
                    JSONArray responseArray = new JSONArray( response.get( "matches" ).toString() );

                    resultText.setText(responseArray.toString());
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
