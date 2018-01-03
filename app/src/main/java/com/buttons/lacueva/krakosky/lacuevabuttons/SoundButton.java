package com.buttons.lacueva.krakosky.lacuevabuttons;

public class SoundButton {

    private String name;
    private String idClip;

    public SoundButton(String btnName, String resourceId) {
        this.name = btnName;
        this.idClip = resourceId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdClip() {
        return idClip;
    }

    public void setIdClip(String idClip) {
        this.idClip = idClip;
    }
}
