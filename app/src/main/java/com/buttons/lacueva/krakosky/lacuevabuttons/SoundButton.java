package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class SoundButton implements Serializable {

    public enum Color {
        RED,
        BLUE,
        GREEN,
        ORANGE,
        YELLOW
    }

    private String mName;
    private Color mColor;
    private String mPath;

    private transient InputStream mIS;

    private boolean hasPathChanged = false;


    public SoundButton()
    {
        mColor = Color.BLUE;
        mIS = null;
    }

    public SoundButton(InputStream is)
    {
        mColor = Color.BLUE;
        mIS = is;
    }

    public void setVolatileInputStream(InputStream is)
    {
        if(mPath == null || mPath.isEmpty())
        {
            mIS = is;
        }
    }

    public InputStream getInputStream() throws IOException
    {
        if(mIS == null || hasPathChanged)
        {
            mIS = MemoryManager.readInputStreamFromPath(mPath);

            hasPathChanged = false;
        }

        return mIS;
    }

    @Override
    public String toString()
    {
        return mColor + "  " + mName + "  " + mPath;
    }
    /************************
     * Getters & Setters
     ************************/

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path)
    {
        String aux = this.mPath;

        this.mPath = path;

        if(aux != null && !aux.equals(mPath))
        {
            hasPathChanged = true;
        }
    }

    public Color getColor() {
        return mColor;
    }

    public void setColor(Color color) {
        this.mColor = color;
    }

}
