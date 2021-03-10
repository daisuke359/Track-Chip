package com.example.trackchip;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PackageLocation extends AppCompatActivity {

    private PackageListOpenHelper db;

    private Package newPackage;
    private TextView est_dateview;
    private TextView pack_numview;
    private TextView originview;
    private TextView dc_locview;
    private TextView destinationview;
    private String status;

    private ImageView imgOut;
    private ImageView imgDelivered;
    private View line1;
    private View line2;
    private View view;
    private View view2;
    private View view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_location);

        db = new PackageListOpenHelper(this);

        est_dateview = (TextView) findViewById(R.id.estimateDate);
        pack_numview = (TextView) findViewById(R.id.trackNum);
        originview = (TextView) findViewById(R.id.origin);
        dc_locview = (TextView) findViewById(R.id.dcCenter);
        destinationview = (TextView) findViewById(R.id.destination);

        //attributes that need to be changed
        imgOut = (ImageView) findViewById(R.id.imgOut);
        imgDelivered = (ImageView) findViewById(R.id.imgDelivered);
        line1 = (View) findViewById(R.id.line1);
        line2 = (View) findViewById(R.id.line2);
        view = (View) findViewById(R.id.view);
        view2 = (View) findViewById(R.id.view2);
        view3 = (View) findViewById(R.id.view3);


        //id needs to come from searching activity
        //newPackage = db.getbyId(3412);

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        newPackage = db.getbyId(id);

        bind();
        changeXMLAtributes();
    }

    public void bind (){

        status = newPackage.getStatus();

        pack_numview.setText("#" + newPackage.getID());
        est_dateview.setText(newPackage.getEst_date().toString());
        originview.setText(newPackage.getOrigin());

        switch(status) {
            case "SHIPPED":
                dc_locview.setText("Your package will be out for delivery soon");
                destinationview.setText("Your package will delivered on: " + newPackage.getEst_date());

                break;

            case "IN_TRANSIT":
                dc_locview.setText(newPackage.getDc_location());
                destinationview.setText("Your package will delivered on: " + newPackage.getEst_date());
                break;

            case "DELIVERED":
                dc_locview.setText(newPackage.getDc_location());
                destinationview.setText("Your package has been delivered to: " + newPackage.getDestination());
                break;
            default:break;
        }
    }


    //method to change attributes in XML file according to status og the package
    public void changeXMLAtributes() {

        status = newPackage.getStatus();

        switch (status) {
            case "SHIPPED":
                //change attributes here
                imgOut.setImageResource(R.drawable.icons_package2);
                imgDelivered.setImageResource(R.drawable.icons_package2);
                view.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                break;

            case "IN_TRANSIT":
                //change attributes here
                imgOut.setImageResource(R.drawable.icon_package1);
                imgDelivered.setImageResource(R.drawable.icons_package2);
                line1.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                view.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                view2.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));

                break;

            case "DELIVERED":
                //change attributes here
                imgOut.setImageResource(R.drawable.icon_package1);
                imgDelivered.setImageResource(R.drawable.icon_package1);
                line1.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                line2.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                view.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                view2.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                view3.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                break;

            default:break;
        }

    }
}


