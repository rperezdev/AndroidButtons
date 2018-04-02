package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements CreateButtonFragment.OnButtonCreatorResult {

    public static final int DURATION_MILLIS = 500;
    public static final int BUTTON_CREATOR_HEIGHT = 550;

    private GridView gridSoundButtons;
    private FloatingActionButton floatingButton;
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
        floatingButton =   (FloatingActionButton) findViewById(R.id.btn_add_soundbutton);
        layoutFragment =   (RelativeLayout) findViewById(R.id.layout_fragment_create_button);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButtonCreator();
            }
        });

        try {
            buttonList = MemoryManager.getSerializableFrom("mainlist", MemoryManager.Folder.SAVES);
        } catch (IOException e) {
            Toast.makeText(this, "You have 0 buttons!!", Toast.LENGTH_SHORT).show();
        }
        catch (ClassNotFoundException e) {
            Toast.makeText(this, "ClassNotFound", Toast.LENGTH_SHORT).show();
        }

        if(buttonList == null) {
            buttonList = new SoundButtonList("Main");
        }

        fCreateButton = new CreateButtonFragment();
        adapter = new SoundButtonListAdapter(this, buttonList);

        setSoundButtonsGrid();

        createBasicFolders();
    }

    private void createBasicFolders()
    {
        MemoryManager.createFolderInsideProjectRoot("/", MemoryManager.APP_SAVES_FOLDERNAME, MemoryManager.Path.RELATIVE);
        MemoryManager.createFolderInsideProjectRoot("/", MemoryManager.APP_SOUNDS_FOLDERNAME, MemoryManager.Path.RELATIVE);
    }

    @Override
    public void onButtonCreatorResult(final SoundButton soundButton)
    {
        toggleButtonCreator();

        if(soundButton == null) {
            Toast.makeText(this, "Error: sound button null", Toast.LENGTH_LONG);
            return;
        }

        ImageButton btnSound = new ImageButton(this);
        btnSound.setImageResource(R.drawable.ic_launcher_background);
        btnSound.setBackgroundColor(Color.BLUE);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    PlayAudio(soundButton.getInputStream());
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                    e.printStackTrace();
                }
            }
        });

        if(buttonList.add(soundButton)){
            adapter.notifyDataSetChanged();
        }

        try {
            MemoryManager.saveSerializableTo("mainlist", buttonList, MemoryManager.Folder.SAVES);
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Animation.ABSOLUTE, isFragmentOnForeground ? 0 : BUTTON_CREATOR_HEIGHT,
                Animation.ABSOLUTE, isFragmentOnForeground ? BUTTON_CREATOR_HEIGHT : 0f
        );

        openCreator.setDuration(DURATION_MILLIS);
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

        hideKeyboard(this);
        layoutFragment.startAnimation(openCreator);
    }

    private void hideKeyboard(Activity act)
    {
        View curView = act.getCurrentFocus();
        if(curView != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(curView.getWindowToken(), 0);
        }
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
