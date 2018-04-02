package com.buttons.lacueva.krakosky.lacuevabuttons.exceptions;

/**
 * Created by krakosky on 3/25/18.
 */

public class AttachEmptySoundException extends Exception {

    private static final String errMsg = "Error on attaching an empty input stream to a sound button.";

    public AttachEmptySoundException() {
        super(errMsg);
    }

}
