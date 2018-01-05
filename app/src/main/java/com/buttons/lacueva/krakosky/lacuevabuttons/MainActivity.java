package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
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
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_ADD_AUDIO);

        /*
        SoundButton sbutton = new SoundButton(
                MainActivity.this,
                "Sample",
                R.raw.sample,
                SoundButton.Color.BLUE
        );

        txtview_btnA = (TextView) findViewById(R.id.);
        imgbtn_btnA = (ImageButton) findViewById(R.id.imgButtonA);
        layout = (LinearLayout) findViewById(R.id.linearlayout);

        layout.addView(sbutton.CreateImageButton());
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE_ADD_AUDIO && resultCode == Activity.RESULT_OK) {
            /*
            try {
                MemoryManager.addAudioToFolder(new java.io.File(data));
            } catch (IOException e) {
                e.printStackTrace();
            }
            */

            File f = new File("adf");

            Toast.makeText(this, data.getData() + " " + f.exists(), Toast.LENGTH_LONG).show();

            /*
            File f = new File(Environment.getExternalStorageDirectory() + "/LaCuevaButtons", ""+data.hashCode());

            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                OutputStream os = new FileOutputStream(f);

                byte[] buffer = new byte[1024];

                int len = 0;
                while((len = is.read(buffer)) > 0) {
                    os.write(buffer);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

*/


        }
    }
}
