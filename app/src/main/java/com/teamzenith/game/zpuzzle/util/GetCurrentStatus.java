package com.teamzenith.game.zpuzzle.util;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by alaaalkassar on 3/16/17.
 */

public class GetCurrentStatus {
    HashMap<Integer, Bitmap> orignalStatus;
    HashMap<Integer, Bitmap> newStatus;
    private boolean status = false;
    private boolean[] elementsStatus= new  boolean[9];

    private int count;
    public boolean checkCurrentImage(HashMap<Integer, Bitmap> orignalStatus,HashMap<Integer, Bitmap> newStatus ){

        for(int index =0; index<elementsStatus.length; index++) elementsStatus[index]=false;

        this.orignalStatus = orignalStatus;
        this.newStatus = newStatus;
        for (int index= 0; index<newStatus.size(); index++){
            switch (index){
                case 0:{
                    System.out.println("Test 0");
                    elementsStatus[0]= getStatus(0);
                    return  checkValues();
                }
                case 1:{
                    return elementsStatus[1]=getStatus(1);
                }
                case 2:{
                    return elementsStatus[2]= getStatus(2);
                }
                case 3:{
                    return elementsStatus[3]= getStatus(index);
                }
                case 4:{
                    return elementsStatus[4]= getStatus(index);
                }
                case 5:{
                    return elementsStatus[5]= getStatus(index);
                }
                case 6:{
                    return elementsStatus[6]= getStatus(index);
                }
                case 7:{
                    return elementsStatus[7]= getStatus(index);
                }
                case 8:{
                    return elementsStatus[8]= getStatus(index);
                }
            }

        }


        /*for(int index =0;index<orignalStatus.size();index++){
            if (orignalStatus.get(index) != newStatus.get(index)){
                status = false;
            }else{
                orignalStatus.remove(index);
                newStatus.remove(index);
                status=true;
            }
        }*/
        return status;
    }

    private boolean getStatus(int index){
        boolean tmpstatus = false;
        if (orignalStatus.get(index) == newStatus.get(index)) tmpstatus = true ;
        return tmpstatus;
    }

    private boolean checkValues(){

        for(int index =0; index<elementsStatus.length; index++) {
            if (count!=elementsStatus.length){
                if(elementsStatus[index]==true){ System.out.println("True counter is: "+count);count++;}else {
                    return status = true;
                }
            }
        }
        return status;
    }
}
