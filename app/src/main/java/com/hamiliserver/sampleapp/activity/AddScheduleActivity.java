package com.hamiliserver.sampleapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hamiliserver.sampleapp.ActiveAndroid.Schedule;
import com.hamiliserver.sampleapp.ActiveAndroid.Team;
import com.hamiliserver.sampleapp.R;
import com.hamiliserver.sampleapp.async.Holiday;
import com.hamiliserver.sampleapp.async.HolidayCallback;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddScheduleActivity extends AppCompatActivity implements HolidayCallback {

    private final HashMap<String, String> countryList = new HashMap<>();
    private Context context;
    private Team team;
    private List<Team> teamList;
    private List<Schedule> scheduleList;
    private List<com.hamiliserver.sampleapp.model.Holiday> holidayList;
    private Spinner spinnerTeam;
    private Button btnSelectDate, btnAdd;
    private TextInputEditText txtTitle, txtDescription;
    private Calendar calendar;
    private Date currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        this.setTitle("Add Schedule");

        context = this;

        calendar = Calendar.getInstance();

        spinnerTeam = findViewById(R.id.spinnerTeam);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnAdd = findViewById(R.id.btnAdd);
        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);

        teamList = Team.getAll();
        scheduleList = new ArrayList<>();

        ArrayList<String> teamNamesArrayList = new ArrayList<>();
        for (Team teams : teamList) {
            teamNamesArrayList.add(teams.getName());
        }

        String[] teamNamesArray = teamNamesArrayList.toArray(new String[0]);

        ArrayAdapter<String> teamArray = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, teamNamesArray);
        teamArray.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerTeam.setAdapter(teamArray);
        spinnerTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "select", Toast.LENGTH_SHORT).show();
                scheduleList = Schedule.getByTeamId(teamList.get(position).getId());
                new Holiday(context, new Date(), countryList.get(teamList.get(position).getCountry().toLowerCase())).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        countryList.put("australia", "AU");
        countryList.put("colombia", "CO");
        countryList.put("indonesia", "ID");

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth, 0, 0, 0);
                        new Holiday(context, new Date(calendar.getTimeInMillis()), countryList.get(teamList.get(spinnerTeam.getSelectedItemPosition()).getCountry().toLowerCase())).execute();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean requiredFieldsValid = false;
                Boolean holidayConflict = false;
                Boolean existingSchedule = false;

                List<TextInputEditText> requiredInputs = new ArrayList<>();
                requiredInputs.add(txtTitle);
                requiredInputs.add(txtDescription);
                for (TextInputEditText inputs : requiredInputs) {
                    if (!inputs.getText().toString().isEmpty()) {
                        requiredFieldsValid = true;
                    } else {
                        inputs.setError("This field is required");
                    }
                }

                if (requiredFieldsValid) {
                    calendar.clear(Calendar.ZONE_OFFSET);
                    calendar.clear(Calendar.MILLISECOND);

                    Date selectedDate = new Date(calendar.getTimeInMillis());
                    currentDate = new Date();

                    Calendar holiday = Calendar.getInstance();

                    if (selectedDate.getTime() < currentDate.getTime()) {
                        Toast.makeText(context, "Selected date is invalid", Toast.LENGTH_SHORT).show();
                    } else {
                        for (com.hamiliserver.sampleapp.model.Holiday holidays : holidayList) {
                            holiday.setTime(holidays.getDate());
                            if (calendar.compareTo(holiday) == 0) {
                                AlertDialog alertDialog = new AlertDialog.Builder(context)
                                        .setTitle("Holiday conflict")
                                        .setMessage(MessageFormat.format("The selected date is a holiday: {0}.\nPlease select another date.", holidays.getName()))
                                        .create();
                                alertDialog.show();
                                holidayConflict = true;
                                break;
                            }
                        }
                        for (Schedule schedules : scheduleList) {
                            holiday.setTime(schedules.getScheduleDate());
                            if (calendar.compareTo(holiday) == 0) {

                                AlertDialog alertDialog = new AlertDialog.Builder(context)
                                        .setTitle("Schedule conflict")
                                        .setMessage(MessageFormat.format("The selected date is currently scheduled to: {0}.\nPlease select another date.", schedules.getTitle()))
                                        .create();
                                alertDialog.show();
                                existingSchedule = true;
                                break;

                            }
                        }
                        if (!existingSchedule && !holidayConflict) {
                            try {
                                Schedule schedule = new Schedule();
                                schedule.setTeamId(teamList.get(spinnerTeam.getSelectedItemPosition()).getId());
                                schedule.setScheduleDate(new Date(calendar.getTimeInMillis()));
                                schedule.setTitle(txtTitle.getText().toString());
                                schedule.setDetails(txtDescription.getText().toString());
                                schedule.setDateEntry(new Date());
                                Schedule.insert(schedule);
                                onBackPressed();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

            }
        });

    }

    @Override
    public void holidays(List<com.hamiliserver.sampleapp.model.Holiday> holidayList) {
        this.holidayList = holidayList;
    }
}