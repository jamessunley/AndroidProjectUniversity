package com.cet325.bg69mm;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MuseumCursorAdapter extends CursorAdapter {
    public MuseumCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(
                R.layout.painting_list_item, viewGroup, false);
    }

    //used to populate the list view
    @Override
    public void bindView(View view, Context context, Cursor cursor) {


                //link strings to database items
        String paintingArtist = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.PAINTING_ARTIST));
        String paintingTitle = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.PAINTING_TITLE));
        String paintingRoom = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.PAINTING_ROOM));
        String paintingDescription = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.PAINTING_DESCRIPTION));
        String paintingImage = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.PAINTING_IMAGE));
        String paintingYear = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.PAINTING_YEAR));
        String paintingRank = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.PAINTING_RANK));

        //set text views to strings created for the list view
        TextView title = (TextView)view.findViewById(R.id.titleTextView);
        TextView artist = (TextView)view.findViewById(R.id.artistTextView);
        ImageView image = (ImageView)view.findViewById(R.id.imageDocIcon);
        TextView year = (TextView)view.findViewById(R.id.yeatTextView);
        title.setText(paintingTitle);
        artist.setText(paintingArtist);
        year.setText(paintingYear);

        int resID = context.getResources().getIdentifier(paintingImage, "drawable", "com.cet325.bg69mm");
        image.setImageResource(resID);
    }

}
