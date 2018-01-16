package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CreateButtonFragment extends Fragment {

    private static final int REQUEST_CODE_ADD_AUDIO = 1;

    private OnNewSoundButtonCreated mCallback = null;

    private ImageButton btn_Sound;
    private EditText edit_Name;
    private ImageButton btn_Save;
    private RadioGroup rgroup_Colors;

    private SoundButton sButton;
    private InputStream is;

    public interface OnNewSoundButtonCreated {
        void onSoundButtonCreated(SoundButton soundButton);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_create_button, container, false);

        btn_Sound     =  (ImageButton) fragmentView.findViewById(R.id.btn_play);
        btn_Save      =  (ImageButton) fragmentView.findViewById(R.id.btn_save);
        edit_Name     =     (EditText) fragmentView.findViewById(R.id.edit_sound_name);
        rgroup_Colors =   (RadioGroup) fragmentView.findViewById(R.id.radiogroup_colors);

        sButton = new SoundButton();

        triggerFilePicker();

        setOnNewColorPicked();
        setOnSaveButtonClicked();
        setOnNameChanged();

        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCallback = (OnNewSoundButtonCreated) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallback = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE_ADD_AUDIO && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
        }

        if(requestCode == REQUEST_CODE_ADD_AUDIO && resultCode == Activity.RESULT_OK) {

            sButton.setUri(data.getData().toString());

            try {
                is = getActivity().getContentResolver().openInputStream(Uri.parse(sButton.getUri()));
            } catch (FileNotFoundException e) {
                Toast.makeText(getActivity(), "Fail: "+sButton.getUri(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            btn_Sound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PlayAudio(is);
                }
            });

            Toast.makeText(getActivity(), "Sound added", Toast.LENGTH_LONG).show();
        }
    }

    private void setOnNewColorPicked()
    {
        rgroup_Colors.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                sButton.setColor(parseRadioColor(checkedId));
            }
        });
    }

    private void setOnNameChanged()
    {
        edit_Name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sButton.setName(charSequence.toString());
            }

            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void afterTextChanged(Editable editable) {}

        });
    }

    private void setOnSaveButtonClicked()
    {
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonSound();
            }
        });
    }

    private void triggerFilePicker()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, REQUEST_CODE_ADD_AUDIO);
    }

    private void saveButtonSound()
    {
        mCallback.onSoundButtonCreated(sButton);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.commit();
    }

    public static void PlayAudio(InputStream is)
    {
        if(is == null)
            return;

        MediaPlayer mp = new MediaPlayer();

        FileInputStream fis = (FileInputStream)is;

        try {
            mp.setDataSource(fis.getFD());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mp.start();
    }

    private SoundButton.Color parseRadioColor(int idRadio)
    {
        switch (idRadio) {
            case R.id.radio_color_red:
                return SoundButton.Color.RED;
            case R.id.radio_color_blue:
                return SoundButton.Color.BLUE;
            case R.id.radio_color_green:
                return SoundButton.Color.GREEN;
            case R.id.radio_color_orange:
                return SoundButton.Color.ORANGE;
            case R.id.radio_color_yellow:
                return SoundButton.Color.YELLOW;
            default:
                return SoundButton.Color.BLUE;
        }
    }
}
