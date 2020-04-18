package com.example.sudoku;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;
import android.app.Activity;
import android.graphics.Color;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Button;

public class GameFragment extends Fragment {

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
        });

        // Enables functionality of exit game button
        Button exit_game = getView().findViewById(R.id.button_exit_game);
        exit_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //Return to the Main Menu
                NavHostFragment.findNavController(GameFragment.this)
                        .navigate(R.id.action_GameFragment_to_MainMenuFragment);
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
                        curChosen.setImageResource(numbers[ref]);
                        //TODO ADD FUNCTIONALITY WITH ACTUAL PUZZLE
                    }
                }
            });
        }
    }

    public static final int OFFSET = 2;
}