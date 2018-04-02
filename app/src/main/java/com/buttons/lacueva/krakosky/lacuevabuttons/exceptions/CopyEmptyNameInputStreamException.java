package com.buttons.lacueva.krakosky.lacuevabuttons.exceptions;

/**
 * Created by krakosky on 3/25/18.
 */

public class CopyEmptyNameInputStreamException extends CopyInputStreamException {

    public CopyEmptyNameInputStreamException() {
        super(CopyInputStreamException.COPY_EMPTY_NAME_INPUT_STREAM);
    }

    private CopyEmptyNameInputStreamException(String message) {}

}
