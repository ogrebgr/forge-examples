package com.bolyartech.forge.android.examples.simple.units.file_download;

import java.io.File;


/**
 * Created by ogre on 2015-11-08 18:35
 */
public interface Res_FileDownload {
    enum State {
        IDLE,
        DOWNLOADING,
        DOWNLOAD_OK,
        DOWNLOAD_FAIL
    }


    State getState();
    void download();
    void reset();
    String getDownloadedFilePath();
    void abortDownload();
}
