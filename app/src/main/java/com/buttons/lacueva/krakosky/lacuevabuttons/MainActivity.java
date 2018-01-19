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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements CreateButtonFragment.OnButtonCreatorResult {

    private static final int REQUEST_CODE_ADD_AUDIO = 1;

    private GridView gridSoundButtons;
    private FloatingActionButton button;
    private SoundButtonList buttonList;
    private RelativeLayout layoutFragment;

    private Fragment fCreateButton;
    private SoundButtonListAdapter adapter;

    private boolean isFragmentOnForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridSoundButtons = (GridView) findViewById(R.id.grid_soundbuttons);
        button = (FloatingActionButton) findViewById(R.id.btn_add_soundbutton);
        layoutFragment = (RelativeLayout) findViewById(R.id.layout_fragment_create_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButtonCreator();
            }
        });

        buttonList = new SoundButtonList("Main");

        fCreateButton = new CreateButtonFragment();
        adapter = new SoundButtonListAdapter(this, buttonList);

        setSoundButtonsGrid();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE_ADD_AUDIO && resultCode == Activity.RESULT_OK) {

        }
    }

    @Override
    public void onButtonCreatorResult(final SoundButton soundButton)
    {
        toggleButtonCreator();

        if(soundButton == null)
            return;

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

        if(buttonList.add(soundButton)){
            adapter.notifyDataSetChanged();
        }
    }

    private void setSoundButtonsGrid()
    {
        gridSoundButtons.setAdapter(adapter);
    }

    public void toggleButtonCreator()
    {
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        final Animation openCreator = new TranslateAnimation(
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, isFragmentOnForeground ? 0 : 550f,
                Animation.ABSOLUTE, isFragmentOnForeground ? 550f : 0f
        );

        openCreator.setDuration(500);
        openCreator.setFillAfter(true);

        openCreator.setAnimationListener(new Animation.AnimationListener() {

            private int startVisibility;
            private int endVisibility;

            {
                startVisibility = View.VISIBLE;
                endVisibility = isFragmentOnForeground ? View.GONE : View.VISIBLE;
            }

            @Override public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationStart(Animation animation)
            {
                layoutFragment.setVisibility(startVisibility);

                if(!isFragmentOnForeground) {
                    transaction.replace(R.id.layout_fragment_create_button, fCreateButton);
                    transaction.commit();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                layoutFragment.setVisibility(endVisibility);

                if(isFragmentOnForeground) {
                    transaction.remove(fCreateButton);
                    transaction.commit();
                }

                isFragmentOnForeground ^= true;
            }

        });

        layoutFragment.startAnimation(openCreator);
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
