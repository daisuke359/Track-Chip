package com.example.trackchip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SearchPackage extends AppCompatActivity {

    final String KEY = "id";
    private PackageListOpenHelper mDB;
    Package pack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_package);
        EditText trackId;
        Button b1;
        trackId = (EditText) findViewById(R.id.trackingId);
        b1 = (Button) findViewById(R.id.trackItem);
        Button home = (Button) findViewById(R.id.btnHome);

        mDB = new PackageListOpenHelper(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = trackId.getText().toString();

                int Id = Integer.parseInt(trackId.getText().toString());
                pack = new Package();
                pack = mDB.getbyId(Id);

                if(pack == null) {
                    Toast.makeText(SearchPackage.this, "Tracking id not valid",
                            Toast.LENGTH_LONG).show();
                }
                else {

                    Intent intent = new Intent(v.getContext(), PackageView.class);
                    intent.putExtra(KEY,Integer.parseInt(trackId.getText().toString()));
                    v.getContext().startActivity(intent);
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchPackage.this, MainActivity.class));
            }
        });
    }


}



