package com.example.sudoku;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

public class SettingsFragment extends Fragment {

    private Globals g = Globals.getInstance();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    //

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FragmentActivity activity = this.getActivity();
        //Grab the saved SharePreferences first
        final SharedPreferences prefs = this.getActivity().getSharedPreferences(g.getPrefs(),this.getActivity().MODE_PRIVATE);

        //Create prefs editor
        final SharedPreferences.Editor editor = prefs.edit();

        //Set Sound_switch and add listener to save the value when clicked.
        Switch sound_switch = (Switch) view.findViewById(R.id.switch_sound);
        sound_switch.setChecked(prefs.getBoolean("sound_on", false));
        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //If switched on, immmediately set audio to off.
                if (isChecked)
                {
                    //unmute audio
                    AudioManager amanager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                }
                else
                {
                    //mute audio
                    AudioManager amanager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                }

                //commit prefs on change
                editor.putBoolean("sound_on", isChecked);
                editor.commit();
            }
        });

        //Set Autosave Switch and add listener to save value when clicked
        Switch autosave_switch = (Switch) view.findViewById(R.id.switch_autosave);
        autosave_switch.setChecked(prefs.getBoolean("autosave_on", false));
        autosave_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //commit prefs on change
                editor.putBoolean("autosave_on", isChecked);
                editor.commit();
            }
        });

        //Get Saved Radio Button selection
        int i = prefs.getInt("color_theme",0);
        if( i >= 0)
        {
            ((RadioButton) ((RadioGroup)view.findViewById(R.id.radio_group_theme)).getChildAt(i)).setChecked(true);
        }

        //Create Event Listener for Radio buttons  to save value when clicked and update app theme instantly.
        RadioGroup theme_radio_group = (RadioGroup) view.findViewById(R.id.radio_group_theme);
        theme_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {
                        if (checkedId == R.id.radio_light_theme)
                        {
                            //Save Color theme as 0 (Light Theme).
                            editor.putInt("color_theme", 0);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                        else if (checkedId == R.id.radio_dark_theme)
                        {
                            //Save Color theme as 1 (Dark Theme).
                            editor.putInt("color_theme", 1);
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }

                        //Commit Changes
                        editor.apply();
                    }
                });



        //Enable the "Go Back" button and add listener to navigate back to the main menu.
        view.findViewById(R.id.button_settings_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SettingsFragment.this)
                        .navigate(R.id.action_SettingsFragment_to_MainMenuFragment);
            }
        });
    }
}