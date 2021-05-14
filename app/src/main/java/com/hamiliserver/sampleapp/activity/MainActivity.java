package com.hamiliserver.sampleapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hamiliserver.sampleapp.ActiveAndroid.Schedule;
import com.hamiliserver.sampleapp.ActiveAndroid.Team;
import com.hamiliserver.sampleapp.R;
import com.hamiliserver.sampleapp.async.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final HashMap<String, String> countryList = new HashMap<>();
    private Team team;
    private List<Team> teamList;
    private List<Schedule> scheduleList;
    private Context context;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton fab, fab1;
    private TextView lblNoSchedulePlaceholder;
    private MyAdapter myAdapter;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        populateSchedule();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lblNoSchedulePlaceholder = findViewById(R.id.lblNoSchedulePlaceholder);

        context = this;

        teamList = Team.getAll();

        if (teamList.isEmpty()) {
            populateTeam();
        }
        countryList.put("australia", "AU");
        countryList.put("colombia", "CO");
        countryList.put("indonesia", "ID");

        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        recyclerView = findViewById(R.id.recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddScheduleActivity.class);
                startActivity(intent);
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Employee(context).execute();
            }
        });

        myAdapter = new MyAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        populateSchedule();

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(myAdapter);

    }

    private void populateTeam() {

        List<Team> teamList = new ArrayList<>();

        /*

            Australia John 101
            Australia Chris 102
            Australia Jenny 103
            Indonesia Larissa 201
            Indonesia Marius 202
            Indonesia Rina 203
            Colombia Julian 301

         */

        team = new Team();
        team.setCountry("Australia");
        team.setName("John");
        team.setEmployeeId("101");
        teamList.add(team);

        team = new Team();
        team.setCountry("Australia");
        team.setName("Chris");
        team.setEmployeeId("102");
        teamList.add(team);

        team = new Team();
        team.setCountry("Australia");
        team.setName("Jenny");
        team.setEmployeeId("103");
        teamList.add(team);

        team = new Team();
        team.setCountry("Indonesia");
        team.setName("Larissa");
        team.setEmployeeId("201");
        teamList.add(team);

        team = new Team();
        team.setCountry("Indonesia");
        team.setName("Marius");
        team.setEmployeeId("202");
        teamList.add(team);

        team = new Team();
        team.setCountry("Indonesia");
        team.setName("Rina");
        team.setEmployeeId("203");
        teamList.add(team);

        team = new Team();
        team.setCountry("Colombia");
        team.setName("Julian");
        team.setEmployeeId("301");
        teamList.add(team);

        Team.insert(teamList);

    }

    private void populateSchedule() {
        scheduleList = Schedule.get();

        if (scheduleList.isEmpty()) {
            lblNoSchedulePlaceholder.setVisibility(View.VISIBLE);
        }
        myAdapter.notifyDataSetChanged();
    }

    private class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.schedule_recyclerview_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            holder.lblTitle.setText(scheduleList.get(position).getTitle());
            holder.lblSchedule.setText(sdf.format(scheduleList.get(position).getScheduleDate()));
            holder.lblDetails.setText(scheduleList.get(position).getDetails());
        }

        @Override
        public int getItemCount() {
            return scheduleList.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView lblTitle, lblSchedule, lblDetails;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTitle = itemView.findViewById(R.id.lblTitle);
            lblSchedule = itemView.findViewById(R.id.lblSchedule);
            lblDetails = itemView.findViewById(R.id.lblDetails);


        }
    }
}