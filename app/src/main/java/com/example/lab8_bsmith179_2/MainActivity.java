package com.example.lab8_bsmith179_2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.CheckBox;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private TextView reservation;

    private final Calendar c = Calendar.getInstance();
    private final DateFormat Date = DateFormat.getDateInstance();
    private HashMap<CheckBox, Integer> checkBoxValues = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        reservation = findViewById(R.id.tvReservation);
        Button btDate = findViewById(R.id.btDate);

        CheckBox option1 = findViewById(R.id.option1);
        CheckBox option2 = findViewById(R.id.option2);
        CheckBox option3 = findViewById(R.id.option3);
        CheckBox option4 = findViewById(R.id.option4);

        checkBoxValues = new HashMap<>();
        checkBoxValues.put(option1, 700);
        checkBoxValues.put(option2, 1800);
        checkBoxValues.put(option3, 9000);
        checkBoxValues.put(option4, 15000);

        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //Calculates the cost of all checked boxes
                int total = 0;
                for (CheckBox cb : checkBoxValues.keySet()) {
                    if (cb.isChecked()) {
                        total += checkBoxValues.get(cb);
                    }
                }

                String baseText = "Your battle on " + Date.format(c.getTime()) +  " has been scheduled. Your total cost is P";
                String totalText = String.valueOf(total);

                SpannableStringBuilder ssb = new SpannableStringBuilder(baseText + totalText);

                //For the pokedollars sign
                int dollarIndex = baseText.indexOf("P");
                ssb.setSpan(new DoubleStrikeSpan(2.0f, 0.1f,  0.7f), dollarIndex, dollarIndex + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                reservation.setText(ssb);
            }
        };

        btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, d, c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}