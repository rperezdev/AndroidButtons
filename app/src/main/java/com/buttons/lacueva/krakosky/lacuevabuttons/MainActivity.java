package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements CreateButtonFragment.OnNewSoundButtonCreated {

    private static final int REQUEST_CODE_ADD_AUDIO = 1;

    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (ImageButton) findViewById(R.id.imageButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerButtonCreator();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE_ADD_AUDIO && resultCode == Activity.RESULT_OK) {

        }
    }

    @Override
    public void onSoundButtonCreated(SoundButton soundButton)
    {
        
    }

    public void triggerButtonCreator()
    {
        Fragment fCreateButton = new CreateButtonFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.rel_layout, fCreateButton);
        transaction.commit();
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
