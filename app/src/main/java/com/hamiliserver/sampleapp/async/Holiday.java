package com.hamiliserver.sampleapp.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hamiliserver.sampleapp.R;
import com.hamiliserver.sampleapp.model.Response;
import com.hamiliserver.sampleapp.util.Util;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Holiday extends AsyncTask<Void, Void, Void> {

    private final Context context;
    private final Date scheduleDate;
    private final String countryCode;
    private Util util;
    private Response response;
    private HolidayCallback holidayCallback;
    private ProgressDialog progressDialog;

    public Holiday(Context context, Date scheduleDate, String countryCode) {
        this.context = context;
        this.scheduleDate = scheduleDate;
        this.countryCode = countryCode;
        //holidayCallback = (HolidayCallback) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, context.getString(R.string.app_name), "getting holidays");
        util = new Util();

    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            SimpleDateFormat year = new SimpleDateFormat("yyyy");

            String holidayApiUrl = context.getString(R.string.publicHolidaysApiUrl);
            holidayApiUrl = holidayApiUrl.replace("{year}", year.format(scheduleDate));
            holidayApiUrl = holidayApiUrl.replace("{countryCode}", countryCode);

            response = util.okHttp(holidayApiUrl, null, null, Util.GET, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();

        Boolean callback = false;

        try {
            holidayCallback = (HolidayCallback) context;
            callback = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response == null) {
            Toast.makeText(context, "Somthing went wrong", Toast.LENGTH_SHORT).show();
        } else {
            if (response.getHttpResponseCode() == HttpURLConnection.HTTP_OK) {
                com.hamiliserver.sampleapp.model.Holiday[] holidaysArray = new Gson().fromJson(response.getResponseJson(), com.hamiliserver.sampleapp.model.Holiday[].class);

                if (callback) {
                    holidayCallback.holidays(Arrays.asList(holidaysArray));
                }

            }
        }
    }
}