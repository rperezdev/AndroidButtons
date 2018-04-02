package com.buttons.lacueva.krakosky.lacuevabuttons.exceptions;

/**
 * Created by krakosky on 3/25/18.
 */

public class CopyInputStreamException extends Exception {

    protected static final String COPY_INPUT_STREAM = "Error on copying an input stream to the project folder";
    protected static final String COPY_NO_INPUT_STREAM = "Cannot copy a null input stream";
    protected static final String COPY_EMPTY_NAME_INPUT_STREAM = "Input stream has no name";
    protected static final String NO_WRITABLE_INPUT_STREAM = "The device is not writable";
    protected static final String MAX_SIZE_REACHED_STREAM = "The input stream has exceeded the maximum size.";
    protected static final String EMPTY_INPUT_STREAM = "The input stream is empty.";


    public CopyInputStreamException() {
        super(COPY_INPUT_STREAM);
    }

    public CopyInputStreamException(String message) {
        super(COPY_INPUT_STREAM + ": " + message);
    }

}
