package com.cet325.bg69mm;

/**
 * Created by jamessunley on 12/01/2018.
 */

import java.io.File;

abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
