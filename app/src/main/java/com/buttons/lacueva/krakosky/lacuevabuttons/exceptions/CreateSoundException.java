package com.buttons.lacueva.krakosky.lacuevabuttons.exceptions;

/**
 * Created by krakosky on 3/25/18.
 */

public class CreateSoundException extends Exception {

    protected static final String BUTTON_SAVED_INCORRECTLY = "Error on creating the button sound.";
    protected static final String FILE_TOO_HEAVY = "The file weights too much.";
    protected static final String FILE_NOT_CORRECTLY_SAVED = "The file was not correctly saved.";

    public CreateSoundException() {
        super(CreateSoundException.BUTTON_SAVED_INCORRECTLY);
    }

    public CreateSoundException(String message) {
        super(message);
    }

}
