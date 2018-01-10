package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;


public class ButtonAdapter extends BaseAdapter {

    private Context context;

    public ButtonAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Toast.makeText(context, "GETTING VIEW", Toast.LENGTH_LONG).show();

        return null;
    }
}
