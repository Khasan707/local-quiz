package com.example.quiz;

import android.app.Application;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestLoader {

    private static TestLoader mInstance;
    private Application application;
    private Gson gson;

    private TestLoader(Application application) {
        this.application = application;
        this.gson = new Gson();
    }

    public static TestLoader getInstance(Application application) {
        if (mInstance == null) {
            mInstance = new TestLoader(application);
        }
        return mInstance;
    }

    public Test getTest(int resourceId) {
        String jsonStr = getStringFromJson(resourceId);
        return gson.fromJson(jsonStr, Test.class);
    }

    private String getStringFromJson(int resourceId) {
        InputStream inputStream = application.getResources().openRawResource(resourceId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Toast.makeText(application.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }
        return outputStream.toString();
    }

}
