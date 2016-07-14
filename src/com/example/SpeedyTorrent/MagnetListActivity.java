package com.example.SpeedyTorrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Wouter
 */
public class MagnetListActivity extends Activity {

    final String KEY_TORRENT = "torrent";
    final String KEY_NAME = "name";
    final String KEY_MAGNET = "magnet";
    final String KEY_SEEDERS = "seeders";
    final String KEY_LEECHERS = "leechers";

    HashMap<String, List<Map<String, String>>> torrentMap;
    HashMap<String, List<Map<String, String>>> completeTorrentMap;
    List<String> tvShowList = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.torrents);

        Intent i = getIntent();

        torrentMap = (HashMap<String, List<Map<String, String>>>) i.getSerializableExtra("magnetList");

        try {
            fillTorrentTable(torrentMap, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillTorrentTable(HashMap<String, List<Map<String, String>>> torrentMap, boolean updateShowList) throws IOException {

        TableLayout table = (TableLayout) findViewById(R.id.torrentTableLayout);
        table.removeAllViews();

        if (updateShowList) {
        tvShowList.add("Select show");
        }
        for (Map.Entry entry : torrentMap.entrySet()) {
            if (updateShowList) {
                tvShowList.add(entry.getKey().toString());
            }

            for (final Map<String, String> tor : torrentMap.get(entry.getKey())) {

                TableRow tr = new TableRow(this);
                tr.setId(100 + entry.hashCode());
                tr.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                TableRow trNewLine = new TableRow(this);
                trNewLine.setId(100 + entry.hashCode());
                trNewLine.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                TableRow trSize = new TableRow(this);
                trNewLine.setId(100 + entry.hashCode());
                trNewLine.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                TextView txtTorrentName = new TextView(this);
                TextView txtTorrentNewLine = new TextView(this);
                txtTorrentName.setId(200 + entry.hashCode());
                txtTorrentNewLine.setId(600 + entry.hashCode());
                System.out.println(txtTorrentName.getId());
                txtTorrentName.setSingleLine(false);
                String originalEntryName = tor.get("name");

                String magnet = "";
                if (originalEntryName.length() > 35) {
                    magnet = originalEntryName.substring(0, 35).concat("\n").concat(originalEntryName.substring(35, Math.min(originalEntryName.length(), 70)));
                } else {
                    magnet = originalEntryName;
                }

                txtTorrentName.setText(magnet);
                txtTorrentNewLine.setText("\n");

                Button btnMagnet = new Button(this);
                btnMagnet.setText("Get");

                btnMagnet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MagnetExecuterActivity magnetExecuter = new MagnetExecuterActivity("magnet");
                        String magnet = tor.get("magnet");
                        magnetExecuter.execute(magnet);
                    }
                });


                tr.addView(txtTorrentName);
                TextView txtSize = new TextView(this);
                txtSize.setText("\n" + tor.get("torrentsize"));
                txtSize.setTextColor(Color.BLACK);
                txtSize.setTextSize(12F);
                trSize.addView(txtSize);

                //trNewLine.addView(txtTorrentNewLine);
                tr.addView(btnMagnet);

                table.addView(tr, new TableLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                table.addView(trSize, new TableLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                table.addView(trNewLine, new TableLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }

        final List<String> showList = new ArrayList<String>(tvShowList);
        if (updateShowList) {
            completeTorrentMap = torrentMap;
        }
        final HashMap<String, List<Map<String, String>>> torrentMapFinal = completeTorrentMap;

        Spinner spnShows;
        spnShows = (Spinner) findViewById(R.id.spnShows);

        Intent i = getIntent();
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tvShowList);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnShows.setAdapter(spnAdapter);
        //    for (List<Map<String, String>> torrent : torrentMap.values()) {
        spnShows.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, List<Map<String, String>>> singleMap = new HashMap<String, List<Map<String, String>>>();
                if (position != 0) {
                    singleMap.put(showList.get(position), torrentMapFinal.get(showList.get(position)));


                    try {
                        fillTorrentTable(singleMap, false);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String test = "sfd";
            }
        });

    }
}

