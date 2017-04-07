package com.teamzenith.game.zpuzzle.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.teamzenith.game.zpuzzle.dbhandler.getAllUserGameHistory;
import com.teamzenith.game.zpuzzle.model.UserHistoryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by memmi on 2017-04-07.
 */

public class TopGamesActivity extends AppCompatActivity implements getAllUserGameHistory {
    private HashMap<Integer,UserHistoryEntry> allUsersHistoryHashMap=new HashMap<>();
    private int i=0;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        HistoryController historyController=new HistoryController();
        historyController.setToControllerFromToGamesActivity(this);

       /* */



    }

    @Override
    public void getAll(UserHistoryEntry userHistoryEntry) {



        if(allUsersHistoryHashMap.size()<10){
            allUsersHistoryHashMap.put(allUsersHistoryHashMap.size(),userHistoryEntry);
            i++;
        }
        else{
            i=0;
        }



      if((allUsersHistoryHashMap.size()==10)&&(i==10)){
            Map<Integer,UserHistoryEntry> h=  sortHashMapByValues(allUsersHistoryHashMap);


            HashMap<Integer,UserHistoryEntry> allusersss=new HashMap<>();
            for (Map.Entry<Integer, UserHistoryEntry> entry : h.entrySet()){

                allusersss.put(allusersss.size(),entry.getValue());
            }

            System.out.println(allusersss.size());

            for(int i=0;i<allusersss.size();i++){
                System.out.println(allusersss.get(i).getTimerCounterString());
            }


          /*  System.out.println(h.get(0).getTimerCounterString());
            System.out.println(h.get(1).getTimerCounterString());
            System.out.println(h.get(2).getTimerCounterString());
            System.out.println(h.get(3).getTimerCounterString());
            System.out.println(h.get(4).getTimerCounterString());
            System.out.println(h.get(5).getTimerCounterString());
            System.out.println(h.get(6).getTimerCounterString());
            System.out.println(h.get(7).getTimerCounterString());
            System.out.println(h.get(8).getTimerCounterString());
            System.out.println(h.get(9).getTimerCounterString());
            h*/
        }



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
}
