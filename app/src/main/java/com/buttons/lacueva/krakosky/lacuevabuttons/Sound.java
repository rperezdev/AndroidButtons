package com.buttons.lacueva.krakosky.lacuevabuttons;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import com.buttons.lacueva.krakosky.lacuevabuttons.MemoryManager.Folder;

public class Sound implements Serializable {

    private Folder mFolder;
    private String mName;
    private FileDescriptor mFD;

    private transient InputStream mIS;


    public Sound(Folder folder, String name) throws IOException {
        setFolder(folder);
        setName(name);
    }


    public String getPath()
    {
        Folder folder;
        String fullPath = null;

        if((folder = getFolder()) != null) {
            fullPath = folder.getPath() + "/" + getName();
        }

        return fullPath;
    }

    private void setPath(Folder folder, String name) throws IOException {

        String fullPath = getPath();

        if(fullPath != null && !fullPath.isEmpty()) {
            //throw new NotValidPathException
            return;
        }

        if(fullPath.equals(getPath())) {
            return;
        }

        setFolder(folder);
        setName(name);

        if(getInputStream() != null) {
            setFolder(null);
            setName(null);
        }
    }


    public InputStream getInputStream() throws IOException {

        if(mIS == null && getPath() != null) {
            setInputStream( MemoryManager.readInputStreamFromPath(getPath()) );
        }

        return mIS;
    }

    private void setInputStream(InputStream is) throws IOException {
        this.mIS = is;

        FileDescriptor fd;

        if(is != null) {
            fd = ((FileInputStream) is).getFD();
        } else {
            fd = null;
        }

        setFD(fd);
    }


    public FileDescriptor getFD() {
        return mFD;
    }

    private void setFD(FileDescriptor fd) {
        this.mFD = fd;
    }


    private String getName() {
        return mName;
    }

    // TODO: change name to rename
    public void setName(String name)
    {
        if(name != getName()) {
            this.mName = name;
        }
    }


    private Folder getFolder() {
        return mFolder;
    }

    // TODO: change name to move ?? and new param: override = (true|false)
    public void setFolder(Folder folder)
    {
        if(folder != getFolder()) {
            this.mFolder = folder;

            // TODO: create a set of exceptions for SoundFiles manipulation.
            // SoundFileExistsInFolderException
            // SoundFileNameEmpty
            //...

            //setPath(getFolder(), getName()); // throw here...
        }
    }
}
