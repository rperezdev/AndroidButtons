package com.buttons.lacueva.krakosky.lacuevabuttons;

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
    private String mUri;
    private Color mColor;

    public SoundButton()
    {
        mColor = Color.BLUE;
    }

    public SoundButton(String name)
    {
        mName = name;
        mColor = Color.BLUE;
    }

    public SoundButton(String name, Color color)
    {
        mName = name;
        mColor = color;
    }

    public SoundButton(String name, Color color, String uri)
    {
        mName = name;
        mColor = color;
        mUri = uri;
    }


    @Override
    public String toString()
    {
        return mColor + "  " + mName + "  " + mUri;
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

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        this.mUri = uri;
    }

    public Color getColor() {
        return mColor;
    }

    public void setColor(Color color) {
        this.mColor = color;
    }

}
