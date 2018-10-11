package com.d.music.component.greendao.dao;

import android.database.sqlite.SQLiteDatabase;

import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "CUSTOM_MUSIC16".
 */
public class CustomMusic16Dao extends AbstractMusicDao {

    public static final String TABLENAME = "CUSTOM_MUSIC16";

    /**
     * Properties of entity MusicModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Url = CommonProperties.Url;
        public final static Property Type = CommonProperties.Type;
        public final static Property Seq = CommonProperties.Seq;
        public final static Property SongId = CommonProperties.SongId;
        public final static Property SongName = CommonProperties.SongName;
        public final static Property ArtistId = CommonProperties.ArtistId;
        public final static Property ArtistName = CommonProperties.ArtistName;
        public final static Property AlbumId = CommonProperties.AlbumId;
        public final static Property AlbumName = CommonProperties.AlbumName;
        public final static Property AlbumUrl = CommonProperties.AlbumUrl;
        public final static Property LrcName = CommonProperties.LrcName;
        public final static Property LrcUrl = CommonProperties.LrcUrl;
        public final static Property FileDuration = CommonProperties.FileDuration;
        public final static Property FileSize = CommonProperties.FileSize;
        public final static Property FilePostfix = CommonProperties.FilePostfix;
        public final static Property FileFolder = CommonProperties.FileFolder;
        public final static Property IsCollected = CommonProperties.IsCollected;
        public final static Property TimeStamp = CommonProperties.TimeStamp;
    };

    public CustomMusic16Dao(DaoConfig config) {
        super(config);
    }

    public CustomMusic16Dao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CUSTOM_MUSIC16\" (" + //
                "\"URL\" TEXT PRIMARY KEY NOT NULL ," + // 0: url
                "\"TYPE\" INTEGER," + // 1: type
                "\"SEQ\" INTEGER," + // 2: seq
                "\"SONG_ID\" TEXT," + // 3: songId
                "\"SONG_NAME\" TEXT," + // 4: songName
                "\"ARTIST_ID\" TEXT," + // 5: artistId
                "\"ARTIST_NAME\" TEXT," + // 6: artistName
                "\"ALBUM_ID\" TEXT," + // 7: albumId
                "\"ALBUM_NAME\" TEXT," + // 8: albumName
                "\"ALBUM_URL\" TEXT," + // 9: albumUrl
                "\"LRC_NAME\" TEXT," + // 10: lrcName
                "\"LRC_URL\" TEXT," + // 11: lrcUrl
                "\"FILE_DURATION\" INTEGER," + // 12: fileDuration
                "\"FILE_SIZE\" INTEGER," + // 13: fileSize
                "\"FILE_POSTFIX\" TEXT," + // 14: filePostfix
                "\"FILE_FOLDER\" TEXT," + // 15: fileFolder
                "\"IS_COLLECTED\" INTEGER," + // 16: isCollected
                "\"TIME_STAMP\" INTEGER);"); // 17: timeStamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CUSTOM_MUSIC16\"";
        db.execSQL(sql);
    }
}