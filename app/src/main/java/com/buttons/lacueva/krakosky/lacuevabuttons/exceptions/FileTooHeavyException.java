package com.buttons.lacueva.krakosky.lacuevabuttons.exceptions;

/**
 * Created by krakosky on 3/25/18.
 */

public class FileTooHeavyException extends CreateSoundException {

    public FileTooHeavyException() {
        super(CreateSoundException.FILE_TOO_HEAVY);
    }

    private FileTooHeavyException(String message) {}

}
