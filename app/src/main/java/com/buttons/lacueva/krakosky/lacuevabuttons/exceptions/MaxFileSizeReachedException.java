package com.buttons.lacueva.krakosky.lacuevabuttons.exceptions;

/**
 * Created by krakosky on 3/25/18.
 */

public class MaxFileSizeReachedException extends CopyInputStreamException {

    public MaxFileSizeReachedException() {
        super(CopyInputStreamException.MAX_SIZE_REACHED_STREAM);
    }

    private MaxFileSizeReachedException(String message) {}

}
