package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtview_btnA;
    private ImageButton imgbtn_btnA;

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SoundButton sbutton = new SoundButton(
                MainActivity.this,
                "Sample",
                R.raw.sample,
                SoundButton.Color.BLUE
        );

        txtview_btnA = (TextView) findViewById(R.id.txtButtonA);
        imgbtn_btnA = (ImageButton) findViewById(R.id.imgButtonA);
        layout = (LinearLayout) findViewById(R.id.linearlayout);

        layout.addView(sbutton.CreateImageButton());
    }
}
