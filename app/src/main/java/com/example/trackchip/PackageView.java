package com.example.trackchip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PackageView extends AppCompatActivity {

    final String KEY = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_view);

        Button b1 = (Button) findViewById(R.id.b1);
        Button b2 = (Button) findViewById(R.id.b2);
        Button b3 = (Button) findViewById(R.id.b3);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    int id = getIntent().getExtras().getInt(KEY);
                    Intent intent = new Intent(v.getContext(), DisplayInformation.class);
                    intent.putExtra(KEY,id);v.getContext().startActivity(intent);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = getIntent().getExtras().getInt(KEY);
                Intent intent = new Intent(v.getContext(), PackageLocation.class);
                intent.putExtra(KEY,id);v.getContext().startActivity(intent);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PackageView.this, MainActivity.class));
            }
        });
    }
}