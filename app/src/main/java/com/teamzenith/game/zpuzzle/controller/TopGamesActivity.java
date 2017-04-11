package com.teamzenith.game.zpuzzle.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.dbhandler.getAllUserGameHistory;
import com.teamzenith.game.zpuzzle.model.Easy;
import com.teamzenith.game.zpuzzle.model.Hard;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.Medium;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Hichem Memmi on 2017-04-07.
 */

public class TopGamesActivity extends AppCompatActivity implements getAllUserGameHistory{
    private HashMap<Integer, UserHistoryEntry> allUsersHistoryHashMap = new HashMap<>();
    private int i = 0;
    private ListView listView;
    private Spinner spinner;
    Vector<Level> spinnerCont;
    ArrayAdapter<Level> adapter;
    TopGamesAdapter topGamesAdapter;
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_top_games);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerCont= new Vector<>();

        Level hard=new Hard();
        Level easy=new Easy();
        Level medium=new Medium();

        spinnerCont.add(hard);
        spinnerCont.add(easy);
        spinnerCont.add(medium);


        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.spinnerCont);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        listView = (ListView) findViewById(R.id.listTopGames);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Level s = (Level) spinner.getItemAtPosition(spinner.getSelectedItemPosition());
                HistoryController historyController = new HistoryController();
                historyController.setToControllerFromToGamesActivity(TopGamesActivity.this,s);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       /* */


    }



    public LinkedHashMap<Integer, UserHistoryEntry> sortHashMapByValues(
            HashMap<Integer, UserHistoryEntry> passedMap) {
        List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
        List<UserHistoryEntry> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<Integer, UserHistoryEntry> sortedMap =
                new LinkedHashMap<>();

        Iterator<UserHistoryEntry> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            UserHistoryEntry val = valueIt.next();
            Iterator<Integer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                UserHistoryEntry comp1 = passedMap.get(key);
                UserHistoryEntry comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    @Override
    public void getAll(UserHistoryEntry userHistoryEntry, int i) {

        System.out.println(i);
        if (allUsersHistoryHashMap.size() < i) {
            allUsersHistoryHashMap.put(allUsersHistoryHashMap.size(), userHistoryEntry);
            this.i++;
        } else {
            this.i = 0;
        }


        if ((allUsersHistoryHashMap.size() == i) && (this.i == i)) {
            Map<Integer, UserHistoryEntry> h = sortHashMapByValues(allUsersHistoryHashMap);


            HashMap<Integer, UserHistoryEntry> allusersss = new HashMap<>();
            for (Map.Entry<Integer, UserHistoryEntry> entry : h.entrySet()) {

                allusersss.put(allusersss.size(), entry.getValue());
            }

            System.out.println(allusersss.size());

           /* for (int i = 0; i < allusersss.size(); i++) {
                System.out.println(allusersss.get(i).getTimerCounterString());
            }*/
            topGamesAdapter = new TopGamesAdapter(TopGamesActivity.this, allusersss);
            listView.setAdapter(topGamesAdapter);
        }


    }
}
