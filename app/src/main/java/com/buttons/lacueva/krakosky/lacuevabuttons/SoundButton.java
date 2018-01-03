package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SoundButton {

    public enum Color {
        BLUE
    }

    private Context context;
    private String name;
    private int idAudioResource;
    private Color color;

    public SoundButton(Context context, String btnName, int resourceId, Color color) {
        this.context = context;
        this.name = btnName;
        this.idAudioResource = resourceId;
        this.color = color;
    }


    public void push()
    {

    }

    public TextView CreateTextView(SoundButton button)
    {
        TextView txtview = new TextView(context);
        txtview.setText(button.getName());

        return txtview;
    }

    public ImageButton CreateImageButton()
    {
        final ImageButton imgbtn = new ImageButton(context);
        imgbtn.setId(name.hashCode());
        imgbtn.setImageDrawable(R.drawable.ic_launcher_background);
        imgbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgbtn.setPressed(true);
                Toast.makeText(context, "Bing!", Toast.LENGTH_SHORT);
            }
        });

        return imgbtn;
    }

    /************************
     * Getters & Setters
     ************************/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
