package com.cet325.bg69mm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.cet325.bg69mm.model.Converter;
import com.cet325.bg69mm.utils.PricingUtils;

import org.json.JSONException;
import java.util.ArrayList;
/**
 * Created by jamessunley on 02/01/2018.
 */
public class InfoActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    //create button, spinner and text inputs and textviews
    LinearLayout li;
    Button btnPriceChange;
    EditText priceInputText;
    TextView priceAdult;
    TextView priceStudent;
    Spinner spinnerCurrency;
    RadioButton radio30;
    RadioButton radio20;
    RadioButton radio40;
    RadioButton radio50;
    //create variables
    String currency = "€";
    Double discount = 0.7;
    String currencyCode;
    String input = "10";
    Double priceChange = Double.parseDouble(input);
    ArrayList <Double> conversions = new ArrayList<Double>();
    Double result;
    //create shared preferences
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //identify buttons on create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        li = (LinearLayout) findViewById(R.id.info);

        btnPriceChange = (Button) findViewById(R.id.btnPriceChange);
        btnPriceChange.setOnClickListener(this);

        priceInputText = (EditText) findViewById(R.id.priceInputText);

        priceAdult = (TextView) findViewById(R.id.priceAdultText);
        priceStudent = (TextView) findViewById(R.id.priceStudentText);

        spinnerCurrency = (Spinner) findViewById(R.id.spinnerCurrency);
        spinnerCurrency.setOnItemSelectedListener(this);

        radio20 = (RadioButton) findViewById(R.id.radioButton20);
        radio20.setOnClickListener(this);

        radio30 = (RadioButton) findViewById(R.id.radioButton30);
        radio30.setOnClickListener(this);

        radio40 = (RadioButton) findViewById(R.id.radioButton40);
        radio40.setOnClickListener(this);

        radio50 = (RadioButton) findViewById(R.id.radioButton50);
        radio50.setOnClickListener(this);

        Convert task = new Convert();
        task.execute(new String());

        //populate conversions with the default conversion rates
        setConversions(conversions);
    }

    //create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //identify what is to happen when each option is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
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

    //opens info activity
    public void openInfoActivity(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //opens home activity
    public void openHomeActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //opens paintings activity
    public void openMasterViewActivity(View view){
        Intent intent = new Intent(this, MasterViewActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //customises background
    private void customiseBackground() {

        AlertDialog settingsDialog;

// Strings to Show In Dialog with Radio Buttons
        final CharSequence[] items = {" Red "," Gray "," Blue "};

        // Creating and Building the Dialog
        AlertDialog.Builder customise = new AlertDialog.Builder(this);
        customise.setTitle("Select The Background Colour");
        customise.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
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

                switch(item)
                {
                    case 0:
                        li.setBackgroundColor(Color.RED);
                        Toast.makeText(getApplicationContext(),
                                "Red",Toast.LENGTH_SHORT).show();
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

    //sets conversions with the default conversion rates
    public void setConversions(ArrayList<Double> conversions) {
        this.conversions = conversions;
        Converter converter = new Converter();

        conversions.add(converter.rates.getGBP());
        conversions.add(converter.rates.getUSD());
        conversions.add(converter.rates.getAUD());
        conversions.add(converter.rates.getBGN());
        conversions.add(converter.rates.getBRL());
        conversions.add(converter.rates.getCAD());
        conversions.add(converter.rates.getCHF());
        conversions.add(converter.rates.getCNY());
        conversions.add(converter.rates.getDKK());
        conversions.add(converter.rates.getHKD());
        conversions.add(converter.rates.getHUF());
        conversions.add(converter.rates.getIDR());
        conversions.add(converter.rates.getILS());
        conversions.add(converter.rates.getINR());
        conversions.add(converter.rates.getJPY());
        conversions.add(converter.rates.getMXN());
        conversions.add(converter.rates.getMYR());
        conversions.add(converter.rates.getNOK());
        conversions.add(converter.rates.getNZD());
        conversions.add(converter.rates.getPHP());
        conversions.add(converter.rates.getPLN());
        conversions.add(converter.rates.getRON());
        conversions.add(converter.rates.getRUB());
        conversions.add(converter.rates.getSEK());
        conversions.add(converter.rates.getSGD());
        conversions.add(converter.rates.getTHB());
        conversions.add(converter.rates.getTRY());
        conversions.add(converter.rates.getZAR());
        conversions.add(converter.rates.getCZK());
    }

    //identifies when button is clicked and changes the adult and student price
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnPriceChange) {
            try {

                if(radio20.isChecked()){
                    discount = 0.8;
                    Toast.makeText(getApplicationContext(), "Discount changed to 20%", Toast.LENGTH_SHORT).show();
                }else if(radio30.isChecked()){
                    Toast.makeText(getApplicationContext(), "Discount changed to 30%", Toast.LENGTH_SHORT).show();
                    discount = 0.7;
                }else if(radio40.isChecked()){
                    Toast.makeText(getApplicationContext(), "Discount changed to 40%", Toast.LENGTH_SHORT).show();
                    discount = 0.6;
                }else if(radio50.isChecked()){
                    Toast.makeText(getApplicationContext(), "Discount changed to 50%", Toast.LENGTH_SHORT).show();
                    discount = 0.5;
                }

                String input = priceInputText.getText().toString();
                priceChange = Double.parseDouble(input);

                //calls utility class to change the student price textview
                long spinneridlong = spinnerCurrency.getSelectedItemId();
                int spinnerid = (int) spinneridlong;
                if (spinnerid == 0){
                    priceAdult.setText("Adult: " + currency + priceChange);
                    priceStudent.setText(PricingUtils.StudentText(priceChange, discount, currency));
                }else {
                    priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(spinnerid), currency));
                    priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(spinnerid), currency, discount));
                    Toast.makeText(getApplicationContext(),
                            "Price Updated", Toast.LENGTH_SHORT).show();
                }
            }catch( Exception e){
                //ensures numbers are always added
                priceAdult.setText("Invalid Input");
                priceStudent.setText("Invalid Input");
                Toast.makeText(getApplicationContext(),
                        "Please Enter a valid numeric value", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //identifies item selected from the spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        //Application logic goes here
        if(id==0){
            String input = priceInputText.getText().toString();
            priceChange = Double.parseDouble(input);
            currency = "€";
            priceAdult.setText("Adult: " + currency + priceChange);
            priceStudent.setText(PricingUtils.StudentText(priceChange, discount, currency));
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            Toast.makeText(getApplicationContext(),
                    "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
        }
        //identifies the selected item
        else if(id==1){
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "£";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(0), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(0), currency, discount));
        }
        else if(id==2){
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "$";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(1), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(1), currency, discount));
        }else if(id==3) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "$";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(2), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(2), currency, discount));
        }else if(id==4) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "лв";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(3), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(3), currency, discount));
        }else if(id==5) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "R$";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(4), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(4), currency, discount));
        }else if(id==6) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "$";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(5), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(5), currency, discount));
        }else if(id==7) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "CHF";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(6), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(6), currency, discount));
        }else if(id==8) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "¥";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(7), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(7), currency, discount));
        }else if(id==9) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "kr";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(8), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(8), currency, discount));
        }else if(id==10) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "$";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(9), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(9), currency, discount));
        }else if(id==11) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "Ft";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(10), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(10), currency, discount));
        }else if(id==12) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "Rp";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(11), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(11), currency, discount));
        }else if(id==13) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "₪";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(12), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(12), currency, discount));
        }else if(id==14) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "₹";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(13), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(13), currency, discount));
        }else if(id==15) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "¥";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(14), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(14), currency, discount));
        }else if(id==16) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "$";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(15), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(15), currency, discount));
        }else if(id==17) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "RM";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(16), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(16), currency, discount));
        }else if(id==18) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "kr";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(17), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(17), currency, discount));
        }else if(id==19) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "$";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(18), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(18), currency, discount));
        }else if(id==20) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "₱";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(19), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(19), currency, discount));
        }else if(id==21) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "zł";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(20), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(20), currency, discount));
        }else if(id==22) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "lei";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(21), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(21), currency, discount));
        }else if(id==23) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "py6";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(22), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(22), currency, discount));
        }else if(id==24) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "kr";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(23), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(23), currency, discount));
        }else if(id==25) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "$";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(24), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(24), currency, discount));
        }else if(id==26) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "฿";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(25), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(25), currency, discount));
        }else if(id==27) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "₺";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(26), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(26), currency, discount));
        }else if(id==28) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "R";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(27), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(27), currency, discount));
        }else if(id==29) {
            //identifies the currency code
            currencyCode = spinnerCurrency.getSelectedItem().toString();
            //toasts out the selected currency
            Toast.makeText(getApplicationContext(), "Your Selected Currency is: " + currencyCode, Toast.LENGTH_SHORT).show();
            //takes the user input as the value to convert
            String input = priceInputText.getText().toString();
            //sets currency symbol
            currency = "Kč";
            //sets the adult value
            priceAdult.setText(PricingUtils.SpinnerToDoAdult(input, result, conversions.get(26), currency));
            //sets student value
            priceStudent.setText(PricingUtils.SpinnerToDoStudent(input, result, conversions.get(26), currency, discount));
        }
    }

    @Override
    public void onPause()
    {
        //Save the value of currency
        saveLastKnownCurrency();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        //retrieves values
        priceAdult.setText(retrieveLastKnownAdult());
        priceStudent.setText(retrieveLastKnownStudent());
        super.onResume();
    }

    private void saveLastKnownCurrency() {
        //save last currency
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        //editor.putString("last currency symbol", currency.toString());
        editor.putString("last currency adult", priceAdult.getText().toString());
        editor.putString("last currency student", priceStudent.getText().toString());
        editor.apply();
    }

    //retrieve the last adukt price
    private String retrieveLastKnownAdult() {
        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        return(mSharedPreferences.getString("last currency adult", defaultValue));
    }

    //retrieve the last student price
    private String retrieveLastKnownStudent() {
        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "";
        return(mSharedPreferences.getString("last currency student", defaultValue));
    }

    public void onNothingSelected(AdapterView<?> parent){

    }

    //get converter from the API
    public class Convert extends AsyncTask<String, Void, Converter> {

        //called in the background, puts values in JSON converter
        @Override
        protected Converter doInBackground(String... params) {

            Log.d("data", params[0]);
            Converter converter = new Converter();
            String data = ((new ConverterHTTPClient()).getConverterData(params[0]));
            if (data != null) {
                try {
                    converter = JSONConverterParser.getConverter(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return converter;
            }
            else return null;
        }

        //after execusion the new conversion rates are added the the conversion
        @Override
        protected void onPostExecute(Converter converter) {
            super.onPostExecute(converter);

            if (converter != null){
                conversions.clear();
                conversions.add(converter.rates.getGBP());
                conversions.add(converter.rates.getUSD());
                conversions.add(converter.rates.getAUD());
                conversions.add(converter.rates.getBGN());
                conversions.add(converter.rates.getBRL());
                conversions.add(converter.rates.getCAD());
                conversions.add(converter.rates.getCHF());
                conversions.add(converter.rates.getCNY());
                conversions.add(converter.rates.getDKK());
                conversions.add(converter.rates.getHKD());
                conversions.add(converter.rates.getHUF());
                conversions.add(converter.rates.getIDR());
                conversions.add(converter.rates.getILS());
                conversions.add(converter.rates.getINR());
                conversions.add(converter.rates.getJPY());
                conversions.add(converter.rates.getMXN());
                conversions.add(converter.rates.getMYR());
                conversions.add(converter.rates.getNOK());
                conversions.add(converter.rates.getNZD());
                conversions.add(converter.rates.getPHP());
                conversions.add(converter.rates.getPLN());
                conversions.add(converter.rates.getRON());
                conversions.add(converter.rates.getRUB());
                conversions.add(converter.rates.getSEK());
                conversions.add(converter.rates.getSGD());
                conversions.add(converter.rates.getTHB());
                conversions.add(converter.rates.getTRY());
                conversions.add(converter.rates.getZAR());
                conversions.add(converter.rates.getCZK());
            }
            else {
                Toast.makeText(getApplicationContext(),"Unable to retrieve data",Toast.LENGTH_LONG).show();
            }
        }
    }
}
