package com.hamiliserver.sampleapp.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hamiliserver.sampleapp.ActiveAndroid.Team;
import com.hamiliserver.sampleapp.R;
import com.hamiliserver.sampleapp.model.Response;
import com.hamiliserver.sampleapp.util.Util;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class Employee extends AsyncTask<Void, Void, Void> {

    private final Util util;
    Context context;
    List<Team> teamList;
    private Response response;
    private ProgressDialog progressDialog;
    private String jsonBody = "";

    public Employee(Context context) {
        this.context = context;
        this.util = new Util();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, context.getString(R.string.app_name), "Sending...");
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            teamList = Team.getAll();

            List<com.hamiliserver.sampleapp.model.Team> teamModelList = new ArrayList<>();

            for (Team teams : teamList) {
                com.hamiliserver.sampleapp.model.Team item = new com.hamiliserver.sampleapp.model.Team();
                item.setCountry(teams.getCountry());
                item.setDateEntry(teams.getDateEntry());
                item.setEmployeeId(teams.getEmployeeId());
                item.setName(teams.getName());

                teamModelList.add(item);

            }

            jsonBody = new Gson().toJson(teamModelList);

            response = util.okHttp("", jsonBody, null, Util.POST, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        progressDialog.dismiss();

        Toast.makeText(context, jsonBody, Toast.LENGTH_SHORT).show();

        if (response == null) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        } else {
            if (response.getHttpResponseCode() == HttpURLConnection.HTTP_OK) {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
