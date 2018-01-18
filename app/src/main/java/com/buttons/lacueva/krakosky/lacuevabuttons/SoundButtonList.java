package com.buttons.lacueva.krakosky.lacuevabuttons;


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SoundButtonList extends AbstractList<SoundButton> {

    private String mName;

    private List<SoundButton> buttons = new ArrayList<SoundButton>();

    public SoundButtonList() {
        mName = null;
    }

    public SoundButtonList(String name) {
        mName = name;
    }

    @Override
    public SoundButton get(int i)
    {
        return buttons.get(i);
    }

    @Override
    public int size()
    {
        return buttons.size();
    }

    @Override
    public boolean add(SoundButton soundButton)
    {
        if(buttons.contains(soundButton)) {
            return false;
        }

        buttons.add(soundButton);
        return true;
    }

    public boolean remove(SoundButton soundButton)
    {
        int i = 0;
        for(SoundButton s : buttons) {
            if(s.equals(soundButton)) {
                buttons.remove(i);
                return true;
            }
            i++;
        }

        return false;
    }

    public boolean remove(String name)
    {
        if(name == null || name.isEmpty())
            return false;

        int i = 0;
        for(SoundButton s : buttons) {
            if(name.equals(s.getName())) {
                buttons.remove(i);
                return true;
            }
            i++;
        }

        return false;
    }

    public boolean existsName(String name)
    {
        if(name == null)
            return false;

        for(SoundButton s : buttons) {
            if(name.equals(s.getName())) {
                return true;
            }
        }

        return false;
    }


    /* ********************************
     *  GETTERS & SETTERS
     **********************************/

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

}
