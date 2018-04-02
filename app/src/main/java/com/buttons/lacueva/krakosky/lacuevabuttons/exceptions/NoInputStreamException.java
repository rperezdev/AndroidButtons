package com.buttons.lacueva.krakosky.lacuevabuttons.exceptions;

/**
 * Created by krakosky on 3/25/18.
 */

public class NoInputStreamException extends CopyInputStreamException {

    public NoInputStreamException() {
        super(CopyInputStreamException.COPY_NO_INPUT_STREAM);
    }

    private NoInputStreamException(String message) {}

}
