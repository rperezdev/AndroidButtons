package com.buttons.lacueva.krakosky.lacuevabuttons.exceptions;

/**
 * Created by krakosky on 3/25/18.
 */

public class NoWritableDeviceException extends CopyInputStreamException {

    public NoWritableDeviceException() {
        super(CopyInputStreamException.NO_WRITABLE_INPUT_STREAM);
    }

    private NoWritableDeviceException(String message) {}

}
