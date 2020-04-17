package com.example.sudoku;

import java.util.HashSet;
import android.content.Context;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
        images = new ImageView[ROW_LENGTH][COLUMN_LENGTH];
    }

    public int getCount() {
        return GRID_SIZE;
    }

    public Object getItem(int position) {
        return images[position/9][position%9];
    }

    public long getItemId(int position) {
        return position+1;
    }

    public void setChosen(int position)
    {
        chosenPosition = position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(Resources.getSystem().getDisplayMetrics().widthPixels / 9, Resources.getSystem().getDisplayMetrics().widthPixels / 9));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 2, 2, 2);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(R.drawable.blank);

        if (row_count < ROW_LENGTH)
        {
            if (col_count < COLUMN_LENGTH)
            {
                images[row_count][col_count] = imageView;
                col_count++;
            }
            else
            {
                row_count++;
                col_count = 0;
                if (row_count < ROW_LENGTH)
                {
                    images[row_count][col_count] = imageView;
                    col_count++;
                }

            }
        }
        imageView.setBackgroundColor(Color.BLACK);
        return imageView;
    }

    private Context mContext;

    public final static int GRID_SIZE = 81;
    public final static int ROW_LENGTH = 9;
    public final static int COLUMN_LENGTH = 9;
    public final static int SQUARE_LENGTH = 3;
    public int row_count = 0;
    public int col_count = 0;
    public final ImageView[][] images;
    public int chosenPosition;
    public HashSet<Integer> defaultValues;

}
