package com.example.tomik.bikenote;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tomik.bikenote.model.AutoData;
import com.example.tomik.bikenote.model.TankUpRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class  GasTankUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{

    public static final String NOWE_TANKOWANIE = "Nowe tankowanie";
    private EditText dateEditText;
    private EditText milagEditText;
    private EditText litersEditText;
    private EditText costEditText;
    private Button confirmButton;
    private AutoData autoData;
    private DateFormat dateFormat;
    private TextView milagEditTextLabel;
    private TextView costEditTextLabel;
    private TextView litersEditTextLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gas_tank_up_layout);
        viewInit();
        setTitle(NOWE_TANKOWANIE);


        obtainExtras();

        getCurrentDate();
        }

    private void viewInit() {
        dateEditText = findViewById(R.id.date);
        milagEditText = findViewById(R.id.milage);
        milagEditTextLabel = (TextView) findViewById(R.id.mileage_label);
        litersEditText = (EditText) findViewById(R.id.liters);
        litersEditTextLabel = (TextView) findViewById(R.id.liters_label);
        costEditText = (EditText) findViewById(R.id.cost);
        costEditTextLabel = (TextView) findViewById(R.id.cost_label);
        confirmButton = (Button) findViewById(R.id.confirm);
        dateEditText.setText(getCurrentDate());

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(GasTankUpActivity.this, GasTankUpActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TankUpRecord tank = new TankUpRecord(getDateEditTextDate(dateEditText), getMileageIntiger(milagEditText), getLitersIntiger(), getCostIntiger(costEditText));
                autoData.getTankUpRecord().add(tank);

            }
        });

        milagEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (hasFocus == false)
                {
                    validationMileage();

                    }
            }
        });

        costEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false)
                {
                    validationCost();

                }

            }
        });

        litersEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false)
                {
                    validationLiters();

                }

            }
        });


    }

    private boolean validationLiters() {
      if (TextUtils.isEmpty(litersEditText.getText().toString())){

          litersEditTextLabel.setText("Litraż musi zostać podany!");
          litersEditTextLabel.setTextColor(getResources().getColor(R.color.red));
          return false;
      }else if(Integer.valueOf(litersEditText.getText().toString()) <= 0){
          milagEditTextLabel.setText("Litraż musi być dodatni!");
          milagEditTextLabel.setTextColor(getResources().getColor(R.color.red));
          return false;
          }
      else{
          litersEditTextLabel.setText(getResources().getString(R.string.added_liters));
          milagEditTextLabel.setTextColor(getResources().getColor(R.color.black));

          return  true;
      }
    }

    private boolean validationCost() {
         if (TextUtils.isEmpty(costEditText.getText().toString())){

             costEditTextLabel.setText("Koszt musi zostać podany");
             costEditTextLabel.setTextColor(getResources().getColor(R.color.red));
             return false;
         }else if(Integer.valueOf(litersEditText.getText().toString()) > 0){
             costEditTextLabel.setText("Koszt musi być dodatni!");
             costEditTextLabel.setTextColor(getResources().getColor(R.color.red));
             return false;

         } else {
             costEditTextLabel.setText(getResources().getString(R.string.tank_cost));
             costEditTextLabel.setTextColor(getResources().getColor(R.color.black));

             return true;
         }



    }

    private boolean validationMileage() {

        if(TextUtils.isEmpty(milagEditText.getText().toString())){
            milagEditTextLabel.setText("Przebieg musi zostać podany!");
            milagEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;

        }
        if(Integer.valueOf(milagEditText.getText().toString()) <= 0){
            milagEditTextLabel.setText("Przebieg musi być dodatni!");
            milagEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;

        }

        int size = autoData.getTankUpRecord().size();
        if (autoData.getTankUpRecord().size() != 0)
        {
            Integer newMileage = Integer.valueOf(milagEditText.getText().toString());
            Integer oldMileage = autoData.getTankUpRecord().get(size - 1).getMileage();
            if (newMileage < oldMileage)
            {
                milagEditTextLabel.setText("Przebieg jest mniejszy niż ostatnio!");
                milagEditTextLabel.setTextColor(getResources().getColor(R.color.red));
                return false;

            } else
            {
                milagEditTextLabel.setText(getResources().getString(R.string.mileage));
                milagEditTextLabel.setTextColor(getResources().getColor(R.color.black));
                return true;
            }

        }


        return false;
    }

    private void obtainExtras() {
        autoData = (AutoData) getIntent().getExtras().getSerializable(MainMenuActivity.SPECIAL_DATA);

        }


    private Date getDateEditTextDate(EditText dateEditText) {

        try {
            return dateFormat.parse(dateEditText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return date;
    }

    private Integer getCostIntiger(EditText costEditText) {
        return Integer.valueOf(costEditText.getText().toString());
    }

    private Integer getLitersIntiger() {
        return Integer.valueOf(litersEditText.getText().toString());
    }

    private Integer getMileageIntiger(EditText milagEditText) {
        return Integer.valueOf(milagEditText.getText().toString());
    }



    private String getCurrentDate() {
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);

        dateEditText.setText(dateFormat.format(calendar.getTime()));

    }
}
