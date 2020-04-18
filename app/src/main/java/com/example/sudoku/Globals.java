package com.example.sudoku;

public class Globals
{
    private static Globals instance;

    // Global variables

    //variable use to point to where the preferences are saved
    private String sharedPrefFile = "com.example.android.sudokusettingsprefs";

    // Restrict the constructor from being instantiated
    private Globals(){}

    public String getPrefs(){
        return sharedPrefFile;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}
