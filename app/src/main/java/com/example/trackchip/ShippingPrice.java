package com.example.trackchip;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ShippingPrice extends AppCompatActivity {

    double width, height, weight, depth, totalCost, volume;

    private PackageListOpenHelper mDB;
    Package pack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_price);

        mDB = new PackageListOpenHelper(this);
        pack = new Package();

        final EditText estWidth = (EditText) findViewById(R.id.estWidth);
        final EditText estHeight = (EditText) findViewById(R.id.estHeight);
        final EditText estWeight = (EditText) findViewById(R.id.estWeight);
        final EditText estDepth = (EditText) findViewById(R.id.estDepth);
        final TextView standardResults = (TextView) findViewById(R.id.rbStandard);
        final TextView priorityResults = (TextView) findViewById(R.id.rbPriority);
        final TextView overnightResults = (TextView) findViewById(R.id.rbOvernight);
        final RadioGroup shippingSpeed = (RadioGroup) findViewById(R.id.rbgShippingCost);
        final TextView originAddress = (TextView) findViewById(R.id.inputOrigin);
        final TextView desinationAddress = (TextView) findViewById(R.id.inputDestination);

        final String KEY = "id";

        Button calculate =  (Button) findViewById(R.id.btnCalculateEstimate);
        Button purchase = (Button) findViewById(R.id.btnPurchaseShipping);

        calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                width = Double.parseDouble(estWidth.getText().toString());
                height = Double.parseDouble(estHeight.getText().toString());
                weight = Double.parseDouble(estWeight.getText().toString());
                depth = Double.parseDouble(estDepth.getText().toString());
                DecimalFormat money = new DecimalFormat("$###,###.##");

                volume = (width * height) * depth;

                if(volume > 10)
                    totalCost += 9.99;
                else if(volume > 30)
                    totalCost += 19.99;
                else if(volume > 50)
                    totalCost += 49.99;
                else
                    Toast.makeText(ShippingPrice.this, "Volume must be less than 50", Toast.LENGTH_LONG).show();

                if(weight < 10)
                    totalCost += 4.99;
                else if(weight < 30)
                    totalCost += 9.99;
                else if(weight < 50)
                    totalCost += 19.99;
                else
                    Toast.makeText(ShippingPrice.this, "Weight must be less than 50", Toast.LENGTH_LONG).show();

                standardResults.setText("Standard: " + money.format(totalCost));
                priorityResults.setText("Priority Post: " + money.format((totalCost + 5)));
                overnightResults.setText("Overnight: " + money.format((totalCost + 10)));
            }

        });

        purchase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Random r = new Random();
                pack.setID(r.nextInt(9999-999)+999);

                pack.setOrigin((String) originAddress.getText().toString());
                pack.setDestination((String) desinationAddress.getText().toString());
                pack.setStatus("SHIPPED");

                int selectedShipping = shippingSpeed.getCheckedRadioButtonId();

                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd", Locale.getDefault());
                Calendar c = Calendar.getInstance();
                Date d = new Date();

                if(selectedShipping == 2131296547 ) {
                    c.add(Calendar.DAY_OF_YEAR, 14);
                    d = c.getTime();
                    pack.setEst_date(dateFormat.format(d));
                }
                else if(selectedShipping == 2131296546 ){
                    c.add(Calendar.DAY_OF_YEAR, 5);
                    d = c.getTime();
                    pack.setEst_date(dateFormat.format(d));
                }
                else{
                    c.add(Calendar.DAY_OF_YEAR, 2);
                    d = c.getTime();
                    pack.setEst_date(dateFormat.format(d));
                }

                pack.setDc_location("New Westminster");

                mDB.insert(pack);

                //Toast.makeText(ShippingPrice.this, "the date is" + selectedShipping, Toast.LENGTH_LONG).show();
                int toastDurationInMilliSeconds = 20000;
                Toast shippingToast;
                shippingToast = Toast.makeText(ShippingPrice.this, "Your Tracking Number is: " + pack.getID() + "\nPlease visit your nearest shipping location to drop off your package!", Toast.LENGTH_LONG);

                CountDownTimer toastCountDown;
                toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
                    public void onTick(long millisUntilFinished) {
                        shippingToast.show();
                    }
                    public void onFinish() {
                        shippingToast.cancel();
                    }
                };

                shippingToast.show();
                toastCountDown.start();


                Intent intent = new Intent(v.getContext(), PackageView.class);
                intent.putExtra(KEY,pack.getID());
                v.getContext().startActivity(intent);
            }
        });


    }

}