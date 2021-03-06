package com.example.sudoku;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.FileUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;
import android.app.Activity;
import android.graphics.Color;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class GameFragment extends Fragment {

    private Globals g = Globals.getInstance();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
        view.findViewById(R.id.button_game_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GameFragment.this)
                        .navigate(R.id.action_GameFragment_to_MainMenuFragment);
            }
        });

 */
        //Grab the saved SharePreferences first
        final SharedPreferences prefs = this.getActivity().getSharedPreferences(g.getPrefs(),this.getActivity().MODE_PRIVATE);
        //Create prefs editor
        final SharedPreferences.Editor editor = prefs.edit();

        // Creates the Sudoku Grid
        final GridView gridview = (GridView) getView().findViewById(R.id.gridview);
        final ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        gridview.setAdapter(imageAdapter);

        // Enables the elements of the grid to be selected
        gridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                ImageView lastChosen = (ImageView) imageAdapter.getItem(imageAdapter.chosenPosition);
                lastChosen.setBackgroundColor(Color.BLACK);

                ImageView image = (ImageView) imageAdapter.getItem(position + OFFSET);
                image.setBackgroundColor(Color.CYAN);
                imageAdapter.setChosen(position +OFFSET);
            }
        });

        // Enables functionality of rest grid button
        Button reset_grid = getView().findViewById(R.id.button_reset_grid);
        reset_grid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //Create Dialog Interface to prompt the user to make sure they actually want to
                //reset the Grid
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choice) {
                        switch (choice) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Reset the Grid
                                ResetGrid(imageAdapter);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                            default:
                                //Do Nothing
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Reset Grid?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        // Enables functionality of exit game button
        Button exit_game = getView().findViewById(R.id.button_exit_game);
        exit_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //Create Dialog Interface to prompt the user to make sure they actually want to
                //exit game and delete non-saved progress
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choice) {
                        switch (choice) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Return to the Main Menu
                                NavHostFragment.findNavController(GameFragment.this)
                                        .navigate(R.id.action_GameFragment_to_MainMenuFragment);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                            default:
                                //Do Nothing
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Exit Game? Progress not saved will be deleted.")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        // Enables functionality of delete button
        Button delete = getView().findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ImageView curChosen = (ImageView) imageAdapter.getItem(imageAdapter.chosenPosition);
                if (curChosen != null)
                {
                    curChosen.setImageResource(R.drawable.blank);
                    //TODO ADD FUNCTIONALITY WITH ACTUAL PUZZLE
                }
            }
        });

        // Enables functionality of number buttons
        Integer[] buttons = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
                R.id.button6, R.id.button7, R.id.button8, R.id.button9};

        final Integer[] numbers = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four,
                R.drawable.five, R.drawable.six, R.drawable.seven,R.drawable.eight, R.drawable.nine};

        for (int i = 0; i < buttons.length; i++)
        {
            Button b = getView().findViewById(buttons[i]);
            final int ref = i;
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    ImageView curChosen = (ImageView) imageAdapter.getItem(imageAdapter.chosenPosition);
                    if (curChosen != null)
                    {
                        //Sound when clicked, if setting is ON
                        //if (prefs.getBoolean("sound_on", false))
                        //    mp.start();

                        curChosen.setImageResource(numbers[ref]);
                        //TODO ADD FUNCTIONALITY WITH ACTUAL PUZZLE
                    }
                }
            });
        }
    }

    /**
     * Function to reset the puzzle Grid
     * @param imageAdapter the adapter that is used to select each grid cell and delete the value.
     */
    public void ResetGrid(ImageAdapter imageAdapter)
    {
        //Iterate through all positions possible and reset them. 9x9 = 81
        for (int x = 0; x < 81; x++)
        {
            //Get ImageView to reset to blank.
            ImageView curChosen = (ImageView) imageAdapter.getItem(x);
            if (curChosen != null)
            {
                //Reset to blank.
                curChosen.setImageResource(R.drawable.blank);
                //TODO ADD FUNCTIONALITY WITH ACTUAL PUZZLE
            }
        }
    }

    public static final int OFFSET = 2;
}