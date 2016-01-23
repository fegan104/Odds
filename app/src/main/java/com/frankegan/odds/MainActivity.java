package com.frankegan.odds;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {
    NumberPicker hundreds, tens, ones;
    EditText editText;
    private static Integer odds = 0;
    private  static String PREFS_NAME = "DISPLAY_CHECKS";
    private static String SHOW_PICKER = "SHOW_PICKER";
    private static String SHOW_EDITTEXT = "SHOW_EDITTEXT";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences(PREFS_NAME, 0);

        editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    int newVal = Integer.parseInt(s.toString());
                    odds = newVal;
                    setPickers(newVal);
                }
            }
        });

        hundreds = (NumberPicker) findViewById(R.id.picker_hundreds);
        tens = (NumberPicker) findViewById(R.id.picker_tens);
        ones = (NumberPicker) findViewById(R.id.picker_ones);

        hundreds.setMinValue(0);
        hundreds.setMaxValue(9);

        tens.setMinValue(0);
        tens.setMaxValue(9);

        ones.setMinValue(0);
        ones.setMaxValue(9);

        hundreds.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                odds = newVal * 100 + getTens() + getOnes();
            }
        });

        tens.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                odds = getHundreds() + newVal * 10 + getOnes();
            }
        });

        ones.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                odds = getHundreds() + getTens() + newVal;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Odds are 1 in " + odds, Snackbar.LENGTH_LONG).show();
            }
        });
    }

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
            case R.id.textField:
                item.setChecked(true);
                editText.setVisibility(View.VISIBLE);

                hundreds.setVisibility(View.GONE);
                tens.setVisibility(View.GONE);
                ones.setVisibility(View.GONE);
                return true;

            case R.id.numberPicker:
                item.setChecked(true);
                hundreds.setVisibility(View.VISIBLE);
                tens.setVisibility(View.VISIBLE);
                ones.setVisibility(View.VISIBLE);

                editText.setVisibility(View.GONE);
                return true;

            case R.id.show_both:
                item.setChecked(true);
                editText.setVisibility(View.VISIBLE);
                hundreds.setVisibility(View.VISIBLE);
                tens.setVisibility(View.VISIBLE);
                ones.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void setPickers(int newVal){
        hundreds.setValue(newVal / 100);
        tens.setValue((newVal - (newVal / 100)) / 10);
        ones.setValue(newVal - ((newVal - (newVal / 100)) / 10));
    }


    public int getHundreds() {
        return hundreds.getValue() * 100;
    }

    public int getTens(){
        return tens.getValue() * 10;
    }

    public int getOnes(){
        return ones.getValue();
    }

}
