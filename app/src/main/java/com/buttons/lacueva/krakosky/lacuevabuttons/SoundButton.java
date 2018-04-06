package com.buttons.lacueva.krakosky.lacuevabuttons;

import com.buttons.lacueva.krakosky.lacuevabuttons.exceptions.AttachEmptySoundException;

import java.io.FileDescriptor;
import java.io.FileInputStream;
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
    private FileDescriptor mFD;

    private transient InputStream mIS;
    private transient SoundPlayer player;

    private boolean hasPathChanged = false;


    public SoundButton() throws IOException, AttachEmptySoundException {
        mColor = Color.BLUE;
        mIS = null;

        player = new SoundPlayer(this);
    }

    public SoundButton(InputStream is)  throws IOException, AttachEmptySoundException {
        mColor = Color.BLUE;
        mIS = is;

        player = new SoundPlayer(this);
    }


    public void setFD(FileDescriptor fd)
    {
        mFD = fd;
    }

    public void setFD(InputStream is) throws java.io.IOException
    {
        if(is != null) {
            mFD = ((FileInputStream) is).getFD();
        }
    }

    public FileDescriptor getFD()
    {
        return mFD;
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
