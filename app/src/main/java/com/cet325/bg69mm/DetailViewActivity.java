package com.cet325.bg69mm;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.cet325.bg69mm.model.Painting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jamessunley on 10/01/2018.
 */

public class DetailViewActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Object> {

    //image request
    static final int REQUEST_IMAGE_CAPTURE = 2;

    LinearLayout li;

    //declare layout items
    Painting painting;
    EditText title;
    EditText artist;
    EditText year;
    EditText description;
    EditText room;
    RatingBar rate;
    ImageView image;

    Button add;

    String s;

    //permissions variable
    boolean permissionNeeded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DBOpenHelper db = new DBOpenHelper(this);

        s = getIntent().getStringExtra("EXTRA_PAINTING_ID");
        System.out.println(s);
        int i = Integer.parseInt(s);

        painting = db.getPaintingByID(i);

        //set text views to strings created for the detail view
        title = (EditText) findViewById(R.id.editTextTitle);
        title.setText(painting.getTitle());

        artist = (EditText) findViewById(R.id.editTextArtist);
        artist.setText(painting.getArtistName());

        year = (EditText) findViewById(R.id.editTextYear);
        year.setText(painting.getYear());

        description = (EditText) findViewById(R.id.editTextDescription);
        description.setText(painting.getDescription());

        room = (EditText) findViewById(R.id.editTextRoom);
        room.setText(painting.getRoom());

        rate = (RatingBar) findViewById(R.id.ratingBar);
        float rating = new Float(painting.getRank()).floatValue();
        rate.setRating(rating);

        image = (ImageView) findViewById(R.id.imageDetail);
        int resID = getResources().getIdentifier(painting.getImage(), "drawable", getPackageName());
        image.setImageResource(resID);

        add = (Button) findViewById(R.id.buttonAdd);
        add.setOnClickListener(this);

        // call set edit
        setEdit();
    }

    //set preloaded data to uneditable
    public void setEdit(){
        if (0<=painting.getId() && painting.getId()<=10){

            artist.setEnabled(false);
            title.setEnabled(false);
            room.setEnabled(false);
            year.setEnabled(false);
            description.setEnabled(false);
            //set buttons to the correct settings for this painting
            add.setText("Update Painting");
            permissionNeeded = false;
        }
    }

    //method to update paintings
    public void updatePainting(String paintingTitle,String paintingArtist, String paintingYear,
                               String paintingDescription, String paintingRoom, String paintingRating, String id){
        //create content val and insert values
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.PAINTING_TITLE,paintingTitle);
        values.put(DBOpenHelper.PAINTING_ARTIST,paintingArtist);
        values.put(DBOpenHelper.PAINTING_YEAR,paintingYear);
        values.put(DBOpenHelper.PAINTING_DESCRIPTION,paintingDescription);
        values.put(DBOpenHelper.PAINTING_ROOM,paintingRoom);
        values.put(DBOpenHelper.PAINTING_RANK,paintingRating);

        String where = "_id = " + id;

        //link content val and selection arg to database
        getContentResolver().update(MuseumProvider.CONTENT_URI, values, where, null);
        //restartLoader();
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0,null,this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        //checks for add button being clicked and carries out action
        if (id == R.id.buttonAdd) {

            //checks for permissions to store the image if the painting has been added by the user
            if ( permissionNeeded == true) {
                if (isStoragePermissionGranted()) saveImage(view);
            }
            System.out.println(rate.getRating());
            String rateString = String.valueOf(rate.getRating());

            //call update method to update the database
            updatePainting(title.getText().toString(), artist.getText().toString(),
                    year.getText().toString(), description.getText().toString(), room.getText().toString(), rateString, s);
            Toast.makeText(getApplicationContext(),
                    "Painting Updated", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    //imae captuer method, opens camera and allows photo to be taken
    protected void imageCapture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            if (isCameraPermissionGranted())
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //checks if image capture is correct and calls setPic
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

                setPic();
            }

        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = image.getWidth();
        int targetH = image.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        image.setImageBitmap(bitmap);
    }

    String mCurrentPhotoPath;

    //creates unique filename for image
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (isCameraPermissionGranted())
            //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.cet325.bg69mm.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    //request permissions to use the camera
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
             Toast.makeText(this, "Permission: "+ permissions[0] + " was " + grantResults[0],Toast.LENGTH_LONG).show();
            // resume tasks needing this permission
            if (requestCode == REQUEST_IMAGE_CAPTURE) imageCapture();
        }
    }

    //checks to see if the permission has been granted
    public boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Permission of using Camera is granted",Toast.LENGTH_LONG).show();
                return true;
            } else {

                Toast.makeText(this,"Permission of using Camera is revoked",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Toast.makeText(this,"Permission of using Camera was granted on installation",Toast.LENGTH_LONG).show();
            return true;
        }
    }

    //saves the image
    public void saveImage(View v){

        Bitmap bitmap;
        OutputStream output;

        Toast.makeText(this, "Get the bitmap", Toast.LENGTH_SHORT).show();
        //Retrieve the image from the image view
        //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pyramid);

        image.buildDrawingCache();
        bitmap = image.getDrawingCache();

        //Find the SD card
        File filepath = Environment.getExternalStorageDirectory();

        Toast.makeText(this, "Create folder", Toast.LENGTH_SHORT).show();
        //Create a new folder in the sd card
        File dir = new File(filepath.getAbsolutePath() + "/BG69MM");
        try{
            dir.mkdirs();
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Cannot create folder", Toast.LENGTH_LONG).show();
        }

        //Create filename
        File file = new File(dir, s + ".jpg");

        //Save
        Toast.makeText(this, "Attempt Save", Toast.LENGTH_SHORT).show();
        try{
            output = new FileOutputStream(file);

            //compress image
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, output);
            output.flush();
            output.close();
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    //checks if the storage permission has been granted
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Permission is granted",Toast.LENGTH_LONG).show();
                return true;
            } else {

                Toast.makeText(this,"Permission is revoked",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Toast.makeText(this,"Permission was granted on installation",Toast.LENGTH_LONG).show();
            return true;
        }
    }


    @Override
    public Loader<Object> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object o) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    //creates option menu unique to this page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {

            case R.id.action_delete:
                if (0<=painting.getId() && painting.getId()<=10) {
                    Toast.makeText(getApplicationContext(),
                            "Cannot Delete this painting", Toast.LENGTH_LONG).show();
                }else {
                    getContentResolver().delete(MuseumProvider.CONTENT_URI, "_id = " +
                            s, null);
                    Toast.makeText(getApplicationContext(),
                            "Painting Deleted", Toast.LENGTH_LONG).show();
                    finish();
                }
                return true;
            case R.id.action_camera:
                if (0<=painting.getId() && painting.getId()<=10) {
                    Toast.makeText(getApplicationContext(),
                            "Image cannot be changed for this painting", Toast.LENGTH_LONG).show();
                }else {
//                    imageCapture();
                    dispatchTakePictureIntent();
                }
                return true;

        }
        return false;
    }


}
