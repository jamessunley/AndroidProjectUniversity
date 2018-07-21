package com.cet325.bg69mm;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by jamessunley on 05/01/2018.
 */

public class MasterViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener, AdapterView.OnClickListener{
    LinearLayout li;
    private CursorAdapter cursorAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        cursorAdapter = new MuseumCursorAdapter(this, null, 0);
        li = (LinearLayout) findViewById(R.id.master);

        //sets the list view from the adapter
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        list.setAdapter(cursorAdapter);

        getLoaderManager().initLoader(0, null, this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(MasterViewActivity.this);
                View getEmpIdView = li.inflate(R.layout.dialog_get_painting, null);

                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(MasterViewActivity.this);
                // set in the alertdialog builder
                alertDialogBuilder.setView(getEmpIdView);

                final EditText titleInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogTitleInput);
                final EditText artistInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogArtistInput);
                final EditText desctiptionInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogDescriptionInput);
                final EditText yearInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogYearInput);
                final EditText roomInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogRoomInput);
                // set dialog message

                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                String title = titleInput.getText().toString();
                                if (title.matches("")){
                                    Toast.makeText(getApplicationContext(),
                                            "Title not entered. Painting not added", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                String artist = artistInput.getText().toString();
                                if (artist.matches("")){
                                    Toast.makeText(getApplicationContext(),
                                            "Artist not entered. Painting not added", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                String description = desctiptionInput.getText().toString();
                                if (description.matches("")){
                                   description = "unknown";
                                }
                                String year = yearInput.getText().toString();
                                if (year.matches("")){
                                    Toast.makeText(getApplicationContext(),
                                            "Year not entered. Painting not added", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                String room = roomInput.getText().toString();
                                if (room.matches("")){
                                    room = "unknown";
                                }

                                insertPainting(title, artist, year, description, room, "0");
                                restartLoader();
                            }
                        }).create()
                        .show();
            }
        });
    }

    //adds and managers the options menu in the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_info:
                openInfoActivity(item.getActionView());
                return true;
            case R.id.action_info_menu:
                openInfoActivity(item.getActionView());
                return true;
            case R.id.action_home_menu:
                openHomeActivity(item.getActionView());
                return true;
            case R.id.action_painting_menu:
                openMasterViewActivity(item.getActionView());
                return true;
            case R.id.action_settings:
                customiseBackground();
                return true;
            case R.id.action_settings_menu:
                customiseBackground();
                return true;

        }
        return false;
    }

    public void openInfoActivity(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openHomeActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openMasterViewActivity(View view){
        Intent intent = new Intent(this, MasterViewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void customiseBackground() {

        AlertDialog settingsDialog;

// Strings to Show In Dialog with Radio Buttons
        final CharSequence[] items = {" Red ", " Grey ", " Blue "};

        // Creating and Building the Dialog
        AlertDialog.Builder customise = new AlertDialog.Builder(this);
        customise.setTitle("Select The Background Colour");
        customise.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Application logic here
            }
        });
        customise.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(),
                        "Colour Changed", Toast.LENGTH_LONG).show();

            }
        });
        customise.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:
                        li.setBackgroundColor(Color.RED);
                        Toast.makeText(getApplicationContext(),
                                "Red", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        li.setBackgroundColor(Color.GRAY);
                        Toast.makeText(getApplicationContext(),
                                "Gray", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        li.setBackgroundColor(Color.BLUE);
                        Toast.makeText(getApplicationContext(),
                                "Blue", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        settingsDialog = customise.create();
        settingsDialog.show();

    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, MuseumProvider.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    //managers the onclick for the floating button
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println(id);
        String stID = String.valueOf(id);

        Intent intent = new Intent(getBaseContext(), DetailViewActivity.class);
        intent.putExtra("EXTRA_PAINTING_ID", stID);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();


    }

    //inserts new painting added by the user into the database
    private void insertPainting(String paintingTitle,String paintingArtist, String paintingYear,
                               String paintingDescription, String paintingRoom, String paintingRating) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.PAINTING_TITLE,paintingTitle);
        values.put(DBOpenHelper.PAINTING_ARTIST,paintingArtist);
        values.put(DBOpenHelper.PAINTING_YEAR,paintingYear);
        values.put(DBOpenHelper.PAINTING_DESCRIPTION,paintingDescription);
        values.put(DBOpenHelper.PAINTING_ROOM,paintingRoom);
        values.put(DBOpenHelper.PAINTING_RANK,paintingRating);
        values.put(DBOpenHelper.PAINTING_IMAGE,"ic_action_new_picture");
        Uri paintingUri  = getContentResolver().insert(MuseumProvider.CONTENT_URI,values);
        Toast.makeText(this,"Painting Added " + paintingTitle,Toast.LENGTH_LONG).show();
    }

}