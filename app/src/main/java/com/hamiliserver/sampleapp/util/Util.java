package com.hamiliserver.sampleapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hamiliserver.sampleapp.model.Response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Util {

    public static final Integer GET = 0;
    public static final Integer POST = 1;
    public static List<Integer> validMethodList = new ArrayList<>();

    public Util() {
        validMethodList.add(GET);
        validMethodList.add(POST);
    }

    public Response okHttp(String url, String param, HashMap<String, String> hashParam, int mode, File file) throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = new Response();
        RequestBody requestBody = null;

        if (validMethodList.contains(mode)) {
            try {
                if (mode == Util.POST) {
                    if (file == null) {
                        requestBody = RequestBody.create(param, MediaType.parse("application/json"));
                    }
                }
                if (file != null) {

                    RequestBody requestBodyImage = RequestBody.create(compressImage(file), MediaType.parse("image/jpeg"));

                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    builder.addFormDataPart("file", file.getName(), requestBodyImage);
                    for (Map.Entry data : hashParam.entrySet()) {
                        builder.addFormDataPart(data.getKey().toString(), data.getValue().toString());
                    }

                    MultipartBody multipartBody = builder.build();

                    requestBody = multipartBody;

                }
                Request request = mode == Util.POST ? new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build() : new Request.Builder()
                        .url(url)
                        .get()
                        .build();

                OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                        .connectTimeout(2880, TimeUnit.MILLISECONDS);

                okHttpClient = clientBuilder.build();

                okhttp3.Response httpResponse = okHttpClient
                        .newCall(request)
                        .execute();
                response.setHttpResponseCode(httpResponse.code());
                response.setResponseJson(httpResponse.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return response;
    }

    private File compressImage(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile() + "-out");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsoluteFile() + "", options);

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();

        return new File(file.getAbsoluteFile() + "-out");
    }
}