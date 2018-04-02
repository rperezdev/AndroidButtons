package com.buttons.lacueva.krakosky.lacuevabuttons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.buttons.lacueva.krakosky.lacuevabuttons.exceptions.CopyEmptyNameInputStreamException;
import com.buttons.lacueva.krakosky.lacuevabuttons.exceptions.CopyInputStreamException;
import com.buttons.lacueva.krakosky.lacuevabuttons.exceptions.EmptyInputStreamException;
import com.buttons.lacueva.krakosky.lacuevabuttons.exceptions.MaxFileSizeReachedException;
import com.buttons.lacueva.krakosky.lacuevabuttons.exceptions.NoInputStreamException;
import com.buttons.lacueva.krakosky.lacuevabuttons.exceptions.NoWritableDeviceException;

public class MemoryManager {

    public static final String EXT_DIRECTORY_ROOT = Environment.getExternalStorageDirectory().toString();
    public static final String APP_ROOT_FOLDERNAME = "LaCuevaButtons";
    public static final String APP_SAVES_FOLDERNAME = "saves";
    public static final String APP_SOUNDS_FOLDERNAME = "sounds";
    public static final String APP_ROOT_FULLPATH = EXT_DIRECTORY_ROOT + "/" + APP_ROOT_FOLDERNAME;
    public static final String APP_SAVES_FULLPATH = APP_ROOT_FULLPATH + "/" + APP_SAVES_FOLDERNAME;
    public static final String APP_SOUNDS_FULLPATH = APP_ROOT_FULLPATH + "/" + APP_SOUNDS_FOLDERNAME;


    public static final int BUFFER_SIZE = 1024;                  // In bytes
    public static final int MAX_SOUND_SIZE = 75 * BUFFER_SIZE;   // In bytes

    public enum Path {
        ABSOLUTE,
        RELATIVE
    }

    public enum Folder
    {
        ROOT(APP_ROOT_FULLPATH),
        SAVES(APP_SAVES_FULLPATH),
        SOUNDS(APP_SOUNDS_FULLPATH);

        private String path;

        Folder(String path) {
            this.path = path;
        }

        public String getPath()
        {
            return path;
        }
    }

    public static boolean createFolderInExternalStorage(String folderName)
    {
        if(folderName == null || folderName.isEmpty() || !isExternalMemoryWritable())
            return false;

        File myDir = new File(EXT_DIRECTORY_ROOT, folderName);

        if(!myDir.exists()) {
            myDir.mkdirs();
        } else {
            return false;
        }

        return true;
    }

    public static boolean createFolderInsideProjectRoot(String folderPath, String folderName, Path pathType)
    {
        if(folderName == null || folderName.isEmpty() || !isExternalMemoryWritable())
            return false;

        String folderFullPath;
        File myDir;

        if(pathType.equals(Path.ABSOLUTE)) {
            folderFullPath = folderPath;
        } else {
            folderFullPath = APP_ROOT_FULLPATH + folderPath;
        }

        myDir = new File(folderFullPath, folderName);

        if(!myDir.exists()) {
            myDir.mkdirs();
        } else {
            return false;
        }

        return true;
    }

    public static String copyInputStreamToProjectFolder(InputStream is, String name, Folder folder)
            throws IOException, CopyInputStreamException
    {
        File file;
        FileOutputStream fos = null;

        ArrayList<CopyInputStreamException> exceptionsList = new ArrayList<>();

        if (is == null)
            throw new NoInputStreamException();

        if (name.isEmpty())
            throw new CopyEmptyNameInputStreamException();

        if (!isExternalMemoryWritable())
            throw new NoWritableDeviceException();


        try
        {
            file = new File(folder.getPath() + "/" + name);
            fos = new FileOutputStream(file);

            byte[] buffer = new byte[BUFFER_SIZE];

            int len, totLength = 0;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                if ((totLength += len) > MAX_SOUND_SIZE) {
                    exceptionsList.add(new MaxFileSizeReachedException());
                }
            }

            if (totLength == 0) {
                exceptionsList.add(new EmptyInputStreamException());
            }
        }
        finally
        {
            if(fos != null) {
                fos.close();
            }

            for (CopyInputStreamException ex : exceptionsList) {
                throw ex;
            }
        }


        return folder.getPath() + "/" + name;
    }

    public static InputStream readInputStreamFromPath(String path)
            throws IOException
    {
        if(path == null || path.isEmpty() || !isExternalMemoryReadable())
            return null;

        File file;

        file = new File(path);

        return new FileInputStream(file);
    }

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

    public static <T extends Serializable> void saveSerializableTo(String name, T obj, Folder folder) throws IOException {

        FileOutputStream fos;
        ObjectOutputStream oos;

        fos = new FileOutputStream(folder.getPath() + "/" + name);
        oos = new ObjectOutputStream(fos);

        oos.writeObject(obj);

        oos.close();
        fos.close();
    }

    public static <T extends Serializable> T getSerializableFrom(String name, Folder folder) throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream( folder.getPath() + "/" + name);
        ObjectInputStream ois = new ObjectInputStream(fis);

        T obj = (T)ois.readObject();

        ois.close();
        fis.close();

        return obj;
    }

    public static InputStream uri2InputStream(Context context, Uri uri) throws FileNotFoundException
    {
        if(context == null) {
            Toast.makeText(context, "Context is NULL", Toast.LENGTH_LONG);
        }

        if(uri == null) {
            Toast.makeText(context, "Uri is NULL", Toast.LENGTH_LONG);
        }

        if(context == null || uri == null)
            return null;

        return context.getContentResolver().openInputStream(uri);
    }

    /*
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
*/

}
