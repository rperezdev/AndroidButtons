package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.Manifest;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_AUDIO = 1;

    private TextView txtview_btnA;
    private ImageButton imgbtn_btnA;

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String rootDirectory = Environment.getExternalStorageDirectory().toString();

        File myDir = new File(rootDirectory, MemoryManager.APP_FOLDERNAME);
        if(!myDir.exists()) {
            myDir.mkdirs();
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, REQUEST_CODE_ADD_AUDIO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE_ADD_AUDIO && resultCode == Activity.RESULT_OK) {

            InputStream is = null;

            try {
                is = getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

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
}
