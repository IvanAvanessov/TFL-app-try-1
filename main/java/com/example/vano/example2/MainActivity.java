package com.example.vano.example2;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.*;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

import java.net.HttpURLConnection;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class MainActivity extends MainViewActivity{

    private TextView thistext;
    private TextView textTime;
    private TableLayout arrivalTable;
    private TableRow arrivalItem;
    private TextView debugView;
    private Button refreshButton;
    private Button debugButton;
    String errorCode, answer, requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thistext = (TextView) findViewById(R.id.textView);
        textTime = (TextView) findViewById(R.id.textTime);
        arrivalTable = (TableLayout) findViewById(R.id.arrivalTable);
        //arrivalItem = (TableRow) findViewById(R.id.arrivalItem);
        debugView = (TextView) findViewById(R.id.debugView);

        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        layoutParams.setMargins (5, 5, 5, 5);

        TableRow tempRow2 = new TableRow(this);
        TextView tempView = new TextView(this);

        arrivalTable.addView(new TableRow(this));
        arrivalTable.addView(tempRow2);

        int temp2 = arrivalTable.getChildCount();
        textTime.setText("Number of items: " + String.valueOf(temp2));
        debugView.setText(String.valueOf(arrivalTable.getId()));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        answer="";
        errorCode="";
        requestCode = "1";
        new HttpRequest(MainActivity.this).execute("https://api.tfl.gov.uk/StopPoint/490012279S1/arrivals", "", requestCode, errorCode);

        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new HttpRequest(MainActivity.this).execute("https://api.tfl.gov.uk/StopPoint/490012279S1/arrivals", "", requestCode, errorCode);

                // Code here executes on main thread after user presses refreshButton
            }
        });

        debugButton = findViewById(R.id.debugButton);
        debugButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent( MainActivity.this, FindBusStop.class );
                //intent.putExtra( EXTRA_MESSAGE )
                //v = new FindBusStop(this);

                startActivity(intent);
                overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
            }
        });



        thistext.setMovementMethod(new ScrollingMovementMethod());
        thistext.setText("Adios");
        //DBHandler dbHandler = new DBHandler(this);
        //debugView.setText(dbHandler.loadHandler());

    }
    @Override
    public void parseJSON(String str, String reqCode) {
        try {
            thistext.setText("");
            arrivalTable.removeAllViews();

            JSONObject[] finalAnswer = new JSONHandler().sortJsonArray(new JSONArray(str),"timeToStation");
            for (int i = 0; i < finalAnswer.length; i++) {
                JSONObject thisBus = finalAnswer[i];

                String minName = thisBus.get("vehicleId").toString();

                arrivalTable.addView(new ArrivalItemRow(this, thisBus.get("lineName").toString(), thisBus.get("destinationName").toString(),
                        formatHHMM(Integer.parseInt(thisBus.get("timeToStation").toString()))));
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                textTime.setText(dateFormat.format(date));

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

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {

        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
