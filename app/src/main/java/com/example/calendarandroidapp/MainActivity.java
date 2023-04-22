package com.example.calendarandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView selectedDateTextView;
    private EditText rotationEditText;
    private Button generateButton;
    private static final String TAG = "check";
    private String dateString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        rotationEditText = findViewById(R.id.rotationEditText);
        generateButton = findViewById(R.id.generateButton);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Get the selected date
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                // Format the date as a string
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                dateString = dateFormat.format(selectedDate.getTime());

                // Show the selected date
                selectedDateTextView.setText("Selected date: " + dateString);

            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rotationInput = rotationEditText.getText().toString();

                // Split string into char
                String[] rotationInputArr = rotationInput.split("");

                // Convert each string to int and store in an integer array
                int[] rotationInputArray = new int[rotationInputArr.length];
                for (int i = 0; i < rotationInputArr.length; i++) {
                    rotationInputArray[i] = Integer.parseInt(rotationInputArr[i]);
                }
                Log.d(TAG, "array: " + Arrays.toString(rotationInputArray));

                // Change color in the Calendar
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    try {
                        calendar.setTime(dateFormat.parse(dateString));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    int numDays = rotationInputArray[0];
                    int colorIndex = 1;
                    for (int i = 0; i < numDays; i++) {
                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                            numDays++;
                        } else {
                            if (colorIndex % 2 == 0) {
                                calendarView.setDateTextAppearance(R.style.BlueDate);
                            } else {
                                calendarView.setDateTextAppearance(R.style.YellowDate);
                            }
                            colorIndex++;
                        }
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
            }
        });
    }
}
