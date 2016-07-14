package com.example.SpeedyTorrent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoadTorrentsActivity extends Activity {

    Button btnLoadSerieTorrents;
    Button btnLoadTopMovieTorrents;
    Button btnEditShows;
    Button btnRefreshTorrents;
    Button btnUpdatesEpisodes;
    HashMap<String, List<Map<String, String>>> torrentMap;
    ArrayList<String> followingShows;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        final String KEY_TORRENT = "torrent";
        final String KEY_NAME = "name";
        final String KEY_MAGNET = "magnet";
        final String KEY_SEEDERS = "seeders";
        final String KEY_LEECHERS = "leechers";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btnLoadSerieTorrents = (Button) findViewById(R.id.btnLoadSerieTorrents);
        btnLoadSerieTorrents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Loading Torrents");
                // Intent loadTorrentsIntent = new Intent(getApplicationContext(), LoadTorrentsActivity.class);
                // startActivity(loadTorrentsIntent);
                Toast loadingTorrentsToast = Toast.makeText(getApplicationContext(), "Loading torrents ...", Toast.LENGTH_LONG);
                loadingTorrentsToast.show();
                try {
                    XmlMagnetParser magnetParser = (XmlMagnetParser) new XmlMagnetParser("results//kickassTorrents.xml", "tv-show").execute("");
                    torrentMap = magnetParser.get();// getting XML
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                List<String> torrentMapKeys = new ArrayList<String>();
                Intent magnetScreen = new Intent(getApplicationContext(), MagnetListActivity.class);
                HashMap<String, List<Map<String, String>>> singleMap = new HashMap<String, List<Map<String, String>>>();
                for (Map.Entry<String, List<Map<String, String>>> entry : torrentMap.entrySet()) {
                    torrentMapKeys.add(entry.getKey());
                }

                //singleMap.put(torrentMapKeys.get(0), torrentMap.get(torrentMapKeys.get(0)));
                //magnetScreen.putExtra("magnetList", singleMap);
                magnetScreen.putExtra("magnetList", torrentMap);
                startActivity(magnetScreen);
                loadingTorrentsToast.cancel();
            }
        });

        btnLoadTopMovieTorrents = (Button) findViewById(R.id.btnLoadTopMovieTorrents);
        btnLoadTopMovieTorrents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Loading Torrents");
               // Intent loadTorrentsIntent = new Intent(getApplicationContext(), LoadTorrentsActivity.class);
               // startActivity(loadTorrentsIntent);
                Toast loadingTorrentsToast = Toast.makeText(getApplicationContext(), "Loading torrents ...", Toast.LENGTH_LONG);
                loadingTorrentsToast.show();
                try {
                    XmlMagnetParser magnetParser = (XmlMagnetParser) new XmlMagnetParser("results//kickassTopMovies.xml", "movie").execute("");
                    torrentMap = magnetParser.get();// getting XML
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                List<String> torrentMapKeys = new ArrayList<String>();
                Intent magnetScreen = new Intent(getApplicationContext(), MagnetListActivity.class);
                HashMap<String, List<Map<String, String>>> singleMap = new HashMap<String, List<Map<String, String>>>();
                for (Map.Entry<String, List<Map<String, String>>> entry : torrentMap.entrySet()) {
                    torrentMapKeys.add(entry.getKey());
                }

                //singleMap.put(torrentMapKeys.get(0), torrentMap.get(torrentMapKeys.get(0)));
                //magnetScreen.putExtra("magnetList", singleMap);
                magnetScreen.putExtra("magnetList", torrentMap);
                startActivity(magnetScreen);
                loadingTorrentsToast.cancel();
            }
        });


        btnEditShows = (Button) findViewById(R.id.btnEditShows);
        btnEditShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Getting Shows");

                try {
                    XmlShowsParser showParser = (XmlShowsParser) new XmlShowsParser().execute("");
                    followingShows = (ArrayList<String>) showParser.get();// getting XML
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Intent configureShowsScreen = new Intent(getApplicationContext(), ConfigureShowsActivity.class);
                configureShowsScreen.putExtra("showList", followingShows);
                startActivity(configureShowsScreen);
            }
        });

        btnRefreshTorrents = (Button) findViewById(R.id.btnRefreshTorrents);
        btnRefreshTorrents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MagnetExecuterActivity magnetExecuter = new MagnetExecuterActivity("parsepb");
                String optional = "parsepb";
                magnetExecuter.execute(optional);
            }
        });

        btnUpdatesEpisodes = (Button) findViewById(R.id.btnUpdateEpisodeList);
        btnUpdatesEpisodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MagnetExecuterActivity magnetExecuter = new MagnetExecuterActivity("updateallepisodes");
                String optional = "updateall";
                magnetExecuter.execute(optional);
            }
        });
    }
}

