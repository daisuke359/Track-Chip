package com.example.trackchip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class EstimateShippingCosts extends AppCompatActivity {

    double width, height, weight, depth, totalCost, volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_shipping_costs);

        final EditText estWidth = (EditText) findViewById(R.id.estWidth);
        final EditText estHeight = (EditText) findViewById(R.id.estHeight);
        final EditText estWeight = (EditText) findViewById(R.id.estWeight);
        final EditText estDepth = (EditText) findViewById(R.id.estDepth);
        final TextView estimate = (TextView) findViewById(R.id.txtEstimateResults);
        final TextView prioityResults = (TextView) findViewById(R.id.txtPrioityResults);
        final TextView overnightResults = (TextView) findViewById(R.id.txtOvernightResults);

        Button calculate =  (Button) findViewById(R.id.btnCalculateEstimate);

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
                    Toast.makeText(EstimateShippingCosts.this, "Volume must be less than 50", Toast.LENGTH_LONG).show();

                if(weight < 10)
                    totalCost += 4.99;
                else if(weight < 30)
                    totalCost += 9.99;
                else if(weight < 50)
                    totalCost += 19.99;
                else
                    Toast.makeText(EstimateShippingCosts.this, "Weight must be less than 50", Toast.LENGTH_LONG).show();

                estimate.setText(money.format(totalCost));
                prioityResults.setText(money.format((totalCost + 5)));
                overnightResults.setText(money.format((totalCost + 10)));
            }

        });
    }
}