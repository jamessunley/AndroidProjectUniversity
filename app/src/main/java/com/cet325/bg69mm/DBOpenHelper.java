package com.cet325.bg69mm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cet325.bg69mm.model.Painting;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jamessunley on 05/01/2018.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String DATABASE_NAME = "museum.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for table and columns
    public static final String TABLE_PAINTING = "painting";
    public static final String PAINTING_ID = "_id";
    public static final String PAINTING_TITLE = "paintingName";
    public static final String PAINTING_ARTIST = "artistName";
    public static final String PAINTING_ROOM = "paintingRoom";
    public static final String PAINTING_DESCRIPTION = "paintingDescription";
    public static final String PAINTING_IMAGE = "IMAGE";
    public static final String PAINTING_YEAR = "paintingYear";
    public static final String PAINTING_RANK = "paintingRank";


    public static final String[] ALL_COLUMNS =
            {PAINTING_ID,PAINTING_ARTIST,PAINTING_TITLE,PAINTING_ROOM,PAINTING_DESCRIPTION,PAINTING_IMAGE,
            PAINTING_YEAR,PAINTING_RANK};

    //Create Table
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_PAINTING + " (" +
                    PAINTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PAINTING_ARTIST + " TEXT, " +
                    PAINTING_TITLE + " TEXT, " +
                    PAINTING_ROOM + " TEXT, " +
                    PAINTING_DESCRIPTION + " TEXT, " +
                    PAINTING_IMAGE + " TEXT, " +
                    PAINTING_YEAR + " INTEGER, " +
                    PAINTING_RANK + " INTEGER" +
                    ")";
    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop the table if it doenst exist
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_PAINTING);
        onCreate(sqLiteDatabase);
    }

    public void addPainting(Painting painting){

        Log.d("addPainting", painting.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        String n = db.getPath();
        Log.d("addPainting", n);

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(PAINTING_ID, painting.getId());//get id
        values.put(PAINTING_ARTIST, painting.getArtistName()); //get artist
        values.put(PAINTING_TITLE, painting.getTitle()); //get title
        values.put(PAINTING_DESCRIPTION, painting.getDescription()); //get description
        values.put(PAINTING_IMAGE, painting.getImage());//get image
        values.put(PAINTING_RANK, painting.getRank());//get Rank
        values.put(PAINTING_ROOM, painting.getRoom());//get room
        values.put(PAINTING_YEAR, painting.getYear());//get year

        // 3. insert
        db.insert(TABLE_PAINTING, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    // Get All Paintings
    public List<Painting> getAllPaintings() {
        List<Painting> paintings = new LinkedList<Painting>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PAINTING;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build painting and add it to list
        Painting painting = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                painting = new Painting();
                painting.setId(0);
                painting.setArtistName(cursor.getString(1));
                painting.setTitle(cursor.getString(2));
                painting.setDescription(cursor.getString(4));
                painting.setImage(cursor.getString(5));
                painting.setRank(cursor.getString(7));
                painting.setRoom(cursor.getString(3));
                painting.setYear(cursor.getString(6));

                // Add painting to paintings
                paintings.add(painting);
            } while (cursor.moveToNext());
        }

        Log.d("getAllPaintings()", paintings.toString());

        cursor.close();
        // return paintings
        return paintings;
    }

    // Deleting single painting
    public void deletePainting(Painting painting) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_PAINTING,
                PAINTING_ID+" = ?",
                new String[] { String.valueOf(painting.getId()) });

        // 3. close
        db.close();

        Log.d("deletePainting", painting.toString());

    }

    public Painting getPaintingByID(int id ){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        String strID = Integer.toString(id);
        // 2. build query
        Cursor cursor =
                db.query(TABLE_PAINTING, // a. table
                        ALL_COLUMNS, // b. column names
                        PAINTING_ID + " = ?", // c. selections
                        new String[] { strID}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        Painting painting = null;
        if (cursor != null && cursor.moveToFirst()) {

            // 4. build book object
            painting = new Painting();
            painting.setId(id);
            painting.setArtistName(cursor.getString(1));
            painting.setTitle(cursor.getString(2));
            painting.setDescription(cursor.getString(4));
            painting.setImage(cursor.getString(5));
            painting.setRank(cursor.getString(7));
            painting.setRoom(cursor.getString(3));
            painting.setYear(cursor.getString(6));

            Log.d("getPainting(" + id + ")", painting.toString());
        }
        cursor.close();
        // 5. return painting
        return painting;

    }

}
