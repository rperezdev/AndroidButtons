package com.buttons.lacueva.krakosky.lacuevabuttons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class MemoryManager {

    public static final String APP_FOLDERNAME = "LaCuevaButtons";
    public static final String APP_FULLPATH = Environment.getExternalStorageDirectory() + APP_FOLDERNAME;

    public static boolean isExternalMemoryWritable()
    {
        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }

        return false;
    }

    public static boolean isExternalMemoryReadable()
    {
        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            return true;
        }

        return false;
    }


    public static void createFolderOnExternalMemory(String folderName)
    {
        if(isExternalMemoryWritable()) {
            File f = new File(Environment.getExternalStorageDirectory(), folderName);
            if(!f.exists()) {
                f.mkdirs();
            }
        }
    }

    public static void addAudioToFolder(File audiofile) throws IOException
    {
        if(isExternalMemoryWritable()) {
            File dstfile = new File(APP_FULLPATH, ""+audiofile.hashCode());

            InputStream is = null;
            OutputStream os = null;

            try {
                is = new FileInputStream(audiofile);
                os = new FileOutputStream(dstfile);

                byte[] buffer = new byte[1024];

                int len;
                while((len = is.read(buffer)) > 0) {
                    os.write(buffer);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                is.close();
                os.close();
            }

        }
    }

}
