package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.media.MediaPlayer;

import com.buttons.lacueva.krakosky.lacuevabuttons.exceptions.AttachEmptySoundException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by krakosky on 4/2/18.
 */

public class SoundPlayer {

    private MediaPlayer mp;

    public SoundPlayer(SoundButton button) throws java.io.IOException, AttachEmptySoundException
    {
        mp = new MediaPlayer();
        InputStream is;
        if((is = button.getInputStream()) != null) {
            mp.setDataSource(((FileInputStream) is).getFD());
        } else {
            throw new AttachEmptySoundException();
        }
    }

    public void play() {
        try {
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        mp.stop();
    }

}
