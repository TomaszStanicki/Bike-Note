package com.example.tomik.bikenote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.tomik.bikenote.historyList.HistoryAdapter;
import com.example.tomik.bikenote.model.AutoData;
import com.example.tomik.bikenote.model.TankUpRecord;

import java.util.ArrayList;
import java.util.Date;

/**
 * Okno menu głównego
 */

public class MainMenuActivity extends AppCompatActivity {

    public static final String SPECIAL_DATA = "SPECIAL_DATA";
    public static final int REQUEST_CODE = 12345;
    private Button goToTankFormButton;
    private Button goToNewBikeButton;
    private Spinner autoChooseSpinner;

    private ArrayList<AutoData> autoList;
    private ArrayAdapter<AutoData> arrayAdapter;
    private RecyclerView historyRecyclerView;
    private RecyclerView.Adapter historyAdapter;
    private RecyclerView.LayoutManager historyLayoutManager;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);
        initViews();

    }


    private void initViews() {
        goToTankFormButton = (Button) findViewById(R.id.go_to_tank_form_button);
        goToNewBikeButton = (Button) findViewById(R.id.new_bike_button);
        autoChooseSpinner = (Spinner) findViewById(R.id.auto_choose_spinner);
        historyRecyclerView = (RecyclerView) findViewById(R.id.historyRecyclerView);


        initAutoList();
        initArrayAdapter();
        autoChooseSpinner.setAdapter(arrayAdapter);
        initRecyclerView();

        goToTankFormButton.setOnClickListener( goToTankUpActivity());
        goToNewBikeButton.setOnClickListener(goToNewBikeActivity());
    }

    private void initRecyclerView() {

        historyLayoutManager = new LinearLayoutManager(this);
        historyRecyclerView.setLayoutManager(historyLayoutManager);

        historyRecyclerView.setHasFixedSize(true);
        historyAdapter = new HistoryAdapter(this, getCurrentBike().getTankUpRecord());
        historyRecyclerView.setAdapter(historyAdapter);
    }

    private View.OnClickListener goToNewBikeActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainMenuActivity.this, AddBikeActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        };
    }


    private void initArrayAdapter() {
        arrayAdapter = new ArrayAdapter<AutoData>(this, android.R.layout.simple_spinner_dropdown_item, autoList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void initAutoList() {
        autoList = new ArrayList<AutoData>();
        autoList.add(new AutoData("Diavel", "Ducati", "Czarny"));
        autoList.get(0).getTankUpRecord().add(new TankUpRecord(new Date(), 1, 10, 50));
        autoList.add(new AutoData("R1", "Yamaha", "Czerwony"));
        autoList.add(new AutoData("GSX-R 1000", "Suzuki", "Niebieski"));
    }


    @NonNull
    private AutoData getCurrentBike() {
        return (AutoData) autoChooseSpinner.getSelectedItem();
    }
    @NonNull
    private View.OnClickListener goToTankUpActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, GasTankUpActivity.class);
                intent.putExtra(SPECIAL_DATA, getCurrentBike());
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
        {
            if (requestCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    AutoData newAutoData = (AutoData) data.getExtras().get(AddBikeActivity.AUTO_DATA_NEW_BIKE);
                    Boolean isNewCarMasterCar = (Boolean) data.getExtras().get(AddBikeActivity.IS_NEW_BIKE_MASTER_BIKE);
                    if (isNewCarMasterCar != null && isNewCarMasterCar)
                    {
                        autoList.add(0, newAutoData);
                    } else
                    {

                        autoList.add(newAutoData);
                    }
                }
            }

        }
    }
}
