package com.bhoomi.pradhans.helloworld;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rinky on 15-10-2017.
 */

public class LatLongAdapter extends ArrayAdapter<LatLongHolder> {
    private final Context context;

    public LatLongAdapter(@NonNull Context context, ArrayList<LatLongHolder> holder) {
        super(context, R.layout.row, holder);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LatLongHolder latLongHolder = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, parent, false);
            TextView latitude = (TextView) convertView.findViewById(R.id.rowTextLat);
            TextView longitude = (TextView) convertView.findViewById(R.id.rowTextLong);
            TextView rowNumber = (TextView) convertView.findViewById(R.id.rowNumber);
            // Populate the data into the template view using the data object
            latitude.setText(latLongHolder.getLatitude());
            longitude.setText(latLongHolder.getLongitude());
            rowNumber.setText("Point" + latLongHolder.getLocationNumber());
            // Return the completed view to render on screen
        }
        return convertView;
    }
}
