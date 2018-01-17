package com.buttons.lacueva.krakosky.lacuevabuttons;

public class CreateButtonError
{
    private CreateButtonError() {}

    public static final int ERR_NAME_EMPTY = 0x000001;
    public static final int ERR_NAME_TOO_SHORT = 0x000002;
    public static final int ERR_NAME_TOO_LONG = 0x000004;
    public static final int ERR_URI_EMPTY = 0x000008;

    public static final int ERR_NONE_MSG = R.string.err_none;
    public static final int ERR_NAME_EMPTY_MSG = R.string.err_name_empty;
    public static final int ERR_NAME_TOO_SHORT_MSG = R.string.err_name_too_short;
    public static final int ERR_NAME_TOO_LONG_MSG = R.string.err_name_too_long;
    public static final int ERR_URI_EMPTY_MSG = R.string.err_uri_empty;

    public static int getMessageId(int errId)
    {
        switch (errId) {
            case ERR_NAME_EMPTY:
                return ERR_NAME_EMPTY_MSG;
            case  ERR_NAME_TOO_SHORT:
                return ERR_NAME_TOO_SHORT_MSG;
            case  ERR_NAME_TOO_LONG:
                return ERR_NAME_TOO_LONG_MSG;
            case ERR_URI_EMPTY:
                return ERR_URI_EMPTY_MSG;
            default:
                return ERR_NONE_MSG;
        }
    }
}
