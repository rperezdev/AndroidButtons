package com.buttons.lacueva.krakosky.lacuevabuttons;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import java.util.ArrayList;
import java.util.List;

public class CreateButtonFragment extends Fragment {

    private class ButtonSoundChecker {

        private Resources mResources;

        public ButtonSoundChecker() {
            mResources = CreateButtonFragment.this.getActivity().getResources();
        }

        public int getSoundButtonStatus(SoundButton sButton)
        {
            int errorNum = 0;

            if(sButton == null || sButton.getName() == null || sButton.getName().isEmpty()) {
                errorNum += CreateButtonError.ERR_NAME_EMPTY;
            } else {

                if(sButton.getName().length() < MIN_NAME_LENGTH)
                    errorNum += CreateButtonError.ERR_NAME_TOO_SHORT;

                if(sButton.getName().length() > MAX_NAME_LENGTH)
                    errorNum += CreateButtonError.ERR_NAME_TOO_LONG;
            }

            if(sButton.getUri() == null || sButton.getUri().isEmpty())
                errorNum += CreateButtonError.ERR_URI_EMPTY;

            return errorNum;
        }

        public String getErrorMessage(int status)
        {
            String message = "";

            List<Integer> msgIds = new ArrayList<Integer>();

            int errId = 1;
            while (status > 0)
            {
                if(status % 2 == 1) {
                    msgIds.add(CreateButtonError.getMessageId(errId));
                }

                status = status >> 1;
                errId = errId << 1;
            }

            for(int i = 0; i < msgIds.size(); i++) {
                String msg = mResources.getString(msgIds.get(i));

                message += msg + ((i == (msgIds.size() - 1)) ? "." : ",");
            }

            return message;
        }

    }

    private static final int REQUEST_CODE_ADD_AUDIO = 1;

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 11;

    private OnNewSoundButtonCreated mCallback = null;

    private ImageButton btn_Sound;
    private EditText edit_Name;
    private ImageButton btn_Save;
    private ImageButton btn_Help;
    private RadioGroup rgroup_Colors;

    private SoundButton sButton;
    private ButtonSoundChecker checker;
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
        btn_Help      =  (ImageButton) fragmentView.findViewById(R.id.btn_help);
        edit_Name     =     (EditText) fragmentView.findViewById(R.id.edit_sound_name);
        rgroup_Colors =   (RadioGroup) fragmentView.findViewById(R.id.radiogroup_colors);

        sButton = new SoundButton();

        btn_Save.setEnabled(false);

        checker = new ButtonSoundChecker();

        //triggerFilePicker();

        setOnNewColorPicked();
        setOnHelpButtonClicked();
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
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                sButton.setName(charSequence.toString());
                btn_Save.setEnabled(checker.getSoundButtonStatus(sButton) == 0);
            }

            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void afterTextChanged(Editable editable) {}

        });
    }

    private void setOnHelpButtonClicked()
    {
        btn_Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = checker.getErrorMessage(checker.getSoundButtonStatus(sButton));

                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
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
