package com.buttons.lacueva.krakosky.lacuevabuttons.exceptions;

/**
 * Created by krakosky on 3/25/18.
 */

public class EmptyInputStreamException extends CopyInputStreamException {

    public EmptyInputStreamException() {
        super(CopyInputStreamException.EMPTY_INPUT_STREAM);
    }

    private EmptyInputStreamException(String message) {}

}
