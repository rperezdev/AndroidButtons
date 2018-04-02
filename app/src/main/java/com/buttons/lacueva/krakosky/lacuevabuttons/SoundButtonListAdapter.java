package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;


public class SoundButtonListAdapter extends ArrayAdapter<SoundButton> {

    private Context mContext;
    private List<SoundButton> mSoundButtons;

    SoundButtonListAdapter(Context context, List<SoundButton> soundButtons) {
        super(context, 0, soundButtons);

        mContext = context;
        mSoundButtons = soundButtons;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        final SoundButton sb = mSoundButtons.get(position);

        if(sb == null) {
            return null;
        }

        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View item = inflater.inflate(R.layout.sample_sound_button_view, parent, false);

        ImageButton btnSound = item.findViewById(R.id.btn_sound);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    MainActivity.PlayAudio(sb.getInputStream());
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });

        TextView txtBtnSound = item.findViewById(R.id.txt_name);
        txtBtnSound.setText(sb.getName());

        return item;
    }

}
