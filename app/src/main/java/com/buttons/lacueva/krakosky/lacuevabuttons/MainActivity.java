package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtview_btnA;
    private ImageButton imgbtn_btnA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtview_btnA = (TextView) findViewById(R.id.txtButtonA);
        imgbtn_btnA = (ImageButton) findViewById(R.id.imgButtonA);

        imgbtn_btnA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                txtview_btnA.setAllCaps(true);
            }
        });
    }
}
