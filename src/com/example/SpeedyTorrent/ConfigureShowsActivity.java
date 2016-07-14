package com.example.SpeedyTorrent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wouter
 * Date: 15/04/14
 * Time: 22:55
 * To change this template use File | Settings | File Templates.
 */
public class ConfigureShowsActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shows);
        Intent i = getIntent();
        ArrayList<String> showList = (ArrayList<String>) i.getSerializableExtra("showList");

        fillShowsTable(showList);
    }

    private void fillShowsTable(final ArrayList<String> tvshowList) {

        final List<String> showList = new ArrayList<String>(tvshowList);

        TableLayout table = (TableLayout) findViewById(R.id.showsTableLayout);
        table.removeAllViews();

        for (int i = 0; i < showList.size(); i++) {

            TableRow trShow = new TableRow(this);
            trShow.setId(i);
            trShow.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView txtShowName = new TextView(this);
            txtShowName.setTextSize(20);
            txtShowName.setId(100 + i);
            txtShowName.setText(showList.get(i));


            trShow.addView(txtShowName);

            table.addView(trShow, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        Button btnReadButton = new Button(this);
        btnReadButton.setText("get");


       // final EditText editText = (EditText) findViewById(R.id.inputShow);

        TableRow trInput = new TableRow(this);
        trInput.setId(900);
        trInput.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

       // trInput.addView(editText);
        // trInput.addView(btnReadButton);
        table.addView(trInput, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tvshowsmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addShow:
                addShowToList("brickleberry");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addShowToList(String tvshow) {

        MagnetExecuterActivity magnetExecuter = new MagnetExecuterActivity("show");
        magnetExecuter.execute(tvshow);
    }
}




