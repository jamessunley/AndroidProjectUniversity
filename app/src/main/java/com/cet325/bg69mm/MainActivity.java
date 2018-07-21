package com.cet325.bg69mm;

/**
 * Created by jamessunley on 30/12/2018.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cet325.bg69mm.model.Painting;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    LinearLayout li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        li = (LinearLayout) findViewById(R.id.home);
    }

    @Override
    public void onStart(){

        super.onStart();

        DBOpenHelper db = new DBOpenHelper(this);

        List<Painting> list = db.getAllPaintings();

        if (list.isEmpty()){

            // add Art
            // add the initial database entries
            //this is the preset data
            db.addPainting(new Painting(1, "Leonardo Da Vinci", "Moana Lisa", "1", "The Famous Moana Lisa", "moana_lisa", "5", "1503"));
            db.addPainting(new Painting(2, "Leonardo Da Vinci", "The Virgin of the Rocks", "21", "Description","virgin", "0", "1483"));
            db.addPainting(new Painting(3, "Johannes Vermeer", "The Lacemaker", "12", "Description","lacemaker", "0", "1670"));
            db.addPainting(new Painting(4, "Leonardo Da Vinci", "St. John the Baptist", "1", "Description","john", "0", "1513"));
            db.addPainting(new Painting(5, "Painting by Eugène Delacroix", "Liberty Leading the People", "12", "Description","liberty", "0", "1830"));
            db.addPainting(new Painting(6, "Caravaggio", "Death of the Virgin", "7", "Description","death", "0", "1606"));
            db.addPainting(new Painting(7, "Théodore Géricault", "The Raft of the Medusa", "2", "Description","raft", "0", "1819"));
            db.addPainting(new Painting(8, "Paolo Veronese", "The Wedding at Cana", "4", "Description","wedding", "0", "1563"));
            db.addPainting(new Painting(9, "Caravaggio", "The Fortune Teller", "6", "Description","fortune", "0", "1594"));
            db.addPainting(new Painting(10, "Jacques-Louis David", "Oath of the Horatii", "1", "Description","oath", "0", "1784"));
             // get all paintings
            db.getAllPaintings();
        }
    }

    //create the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //options menu actions on clicks
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

    //open info activity
    public void openInfoActivity(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //open home activity
    public void openHomeActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //lopen painting activity
    public void openMasterViewActivity(View view){
        Intent intent = new Intent(this, MasterViewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //customise the background
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
}