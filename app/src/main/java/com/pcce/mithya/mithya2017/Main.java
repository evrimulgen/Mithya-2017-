package com.pcce.mithya.mithya2017;

import android.graphics.Typeface;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Magnus Fernandes on 07-Mar-17.
 */

public class Main {
    public static Typeface myCustomFont;

    public static Event sharedEvent;
    public static ArrayList<String> images = new ArrayList<>();

    public static String splitName(String name, String type){
        String lastName = "";
        String firstName= "";
        if(name.split("\\w+").length>1){
            lastName = name.substring(name.lastIndexOf(" ")+1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        }
        Log.d("Operations", lastName + firstName);
        if (type.equals("last")){
            return lastName;
        }
        return firstName;
    }
}
