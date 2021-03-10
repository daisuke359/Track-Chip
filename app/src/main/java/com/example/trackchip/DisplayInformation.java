package com.example.trackchip;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayInformation extends ListActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private PackageListOpenHelper mDB;
    Package pack; // Package object to hold the tracking information

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDB = new PackageListOpenHelper(this);
        int passedArg = getIntent().getExtras().getInt("id");
        pack = new Package();
        pack = mDB.getbyId(passedArg);
        String[] trackingInformation;
        if(pack != null) {
            //Adding the tracking information retrieved from the database to array
            trackingInformation = new String[]{"Status : " + pack.getStatus().toString(),
                    "Origin : " + pack.getOrigin().toString(),
                    "Destination : " + pack.getDestination(),
                    "Estimate Date : " + pack.getEst_date().toString(),
                    "Current Location : " + pack.getDc_location()};
        }
        else {

            trackingInformation =  new String[]{"No package found with id : " + passedArg};
        }
        //Using the list adapter to display the tracking information
        setListAdapter(new ArrayAdapter<String>(
                this,
                R.layout.activity_tracking_information,
                R.id.details,
                trackingInformation));


    }

}