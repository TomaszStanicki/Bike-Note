package com.example.tomik.bikenote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.tomik.bikenote.model.AutoData;

public class AddBikeActivity extends AppCompatActivity {

    public static final String AUTO_DATA_NEW_BIKE = "AUTO_DATA_NEW_BIKE";
    public static final String IS_NEW_BIKE_MASTER_BIKE = "IS_NEW_BIKE_MASTER_BIKE";

    private EditText makeEditText;
    private EditText modelEditText;
    private EditText colorEditText;
    private Switch isMasterBikeSwitch;
    private Button confirmButton;

    /**
     * Okno dodania nowego motocykla
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bike_layout);

        makeEditText = (EditText) findViewById(R.id.make_edittext);
        modelEditText = (EditText) findViewById(R.id.model_edittext);
        colorEditText = (EditText) findViewById(R.id.color_edittext);

        isMasterBikeSwitch = (Switch) findViewById(R.id.master_bike_switch);
        confirmButton = (Button) findViewById(R.id.confirm);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AutoData autoData = new AutoData(modelEditText.getText().toString(), makeEditText.getText().toString(), colorEditText. getText().toString());
                Boolean isMasterBike = isMasterBikeSwitch.isChecked();
                Intent intent = new Intent();
                intent.putExtra(AUTO_DATA_NEW_BIKE,autoData);
                intent.putExtra(IS_NEW_BIKE_MASTER_BIKE, isMasterBike);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
