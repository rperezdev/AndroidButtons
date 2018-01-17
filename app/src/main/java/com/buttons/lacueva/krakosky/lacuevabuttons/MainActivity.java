package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CreateButtonFragment.OnNewSoundButtonCreated {

    private static final int REQUEST_CODE_ADD_AUDIO = 1;

    private GridView gridSoundButtons;
    private FloatingActionButton button;
    private List<SoundButton> soundbuttons = new ArrayList<>();

    private SoundButtonListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridSoundButtons = (GridView) findViewById(R.id.grid_soundbuttons);
        button = (FloatingActionButton) findViewById(R.id.btn_add_soundbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerButtonCreator();
            }
        });

        adapter = new SoundButtonListAdapter(this, soundbuttons);

        setSoundButtonsGrid();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE_ADD_AUDIO && resultCode == Activity.RESULT_OK) {

        }
    }

    @Override
    public void onSoundButtonCreated(final SoundButton soundButton)
    {
        ImageButton btnSound = new ImageButton(this);
        btnSound.setImageResource(R.drawable.ic_launcher_background);
        btnSound.setBackgroundColor(Color.BLUE);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlayAudio(soundButton.getInputStream(MainActivity.this));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        soundbuttons.add(soundButton);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();

        button.hide();
    }

    @Override
    public void onResume() {
        super.onResume();

        button.show();
    }

    private void setSoundButtonsGrid()
    {
        gridSoundButtons.setAdapter(adapter);
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
