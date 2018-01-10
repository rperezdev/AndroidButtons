package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_AUDIO = 1;

    private LinearLayout layout;
    private ImageButton button;

    private InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (LinearLayout) findViewById(R.id.linear_layout);
        button = (ImageButton) findViewById(R.id.imageButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerFilePicker();
            }
        });

        is = null;

        String rootDirectory = Environment.getExternalStorageDirectory().toString();

        File myDir = new File(rootDirectory, MemoryManager.APP_FOLDERNAME);
        if(!myDir.exists()) {
            myDir.mkdirs();
        }

        //triggerFilePicker();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE_ADD_AUDIO && resultCode == Activity.RESULT_OK) {

            try {
                is = getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            layout.addView(getImageButton(is));

            Toast.makeText(this, "Sound added", Toast.LENGTH_LONG).show();
        }
    }

    public ImageButton getImageButton(final InputStream istream)
    {
        ImageButton ibutton = new ImageButton(this);
        ibutton.setId(ibutton.hashCode());
        ibutton.setAlpha(0.8f);
        ibutton.setBackgroundColor(Color.TRANSPARENT);
        ibutton.setImageResource(R.drawable.ic_launcher_background);
        ibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayAudio(istream);
            }
        });

        return ibutton;
    }

    public void triggerFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, REQUEST_CODE_ADD_AUDIO);
    }

    public static void PlayAudio(InputStream is)
    {
        if(is == null)
            return;

        MediaPlayer mp = new MediaPlayer();

        FileInputStream fis = (FileInputStream)is;

        try {
            mp.setDataSource(fis.getFD());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mp.start();
    }
}
