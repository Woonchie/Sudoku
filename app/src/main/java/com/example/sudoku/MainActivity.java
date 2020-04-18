package com.example.sudoku;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Globals g = Globals.getInstance();
    private SharedPreferences prefs;

    /**
     *  Initializes our application
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates the toolbar, but it is not needed. so we hide it.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        //Preferences is where the saved settings and saved games are.
        prefs = getSharedPreferences(g.getPrefs(), MODE_PRIVATE);

        //Set the app color theme
        //0 value is Light Theme
        //1 value is Dark Theme
        switch (prefs.getInt("color_theme", 0))
        {
            case(0):
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case(1):
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }

        //Set the auto baes on saved preference.
        if (prefs.getBoolean("sound_on", true))
        {
            //unmute audio
            AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
        }
        else
        {
            //mute audio
            AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
        }
    }

    /**
     * Applies any Preferences updates still needing to be committed.
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = prefs.edit();
        preferencesEditor.apply();
    }

    /**
     * Creates the tool bar and adds items
     * @param menu the menu calling the toolbar
     * @return true to signify menu has been created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * Method to handle click events on the options in the menu drop down from the tool bar, it is
     * not implemented by our app.
     * @param item the option that is clicked
     * @return true signifying that and item was selected.
     */
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
