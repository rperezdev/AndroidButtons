package com.buttons.lacueva.krakosky.lacuevabuttons.exceptions;

/**
 * Created by krakosky on 3/25/18.
 */

public class SaveSoundException extends CreateSoundException {

    public SaveSoundException() {
        super(CreateSoundException.FILE_NOT_CORRECTLY_SAVED);
    }

    private SaveSoundException(String message) {}

}
