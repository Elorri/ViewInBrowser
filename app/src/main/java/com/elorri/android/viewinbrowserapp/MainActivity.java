package com.elorri.android.viewinbrowserapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openBrowser(View view) {
        File file=generateNoteOnSD(getApplicationContext(), "helloworld.html", "<html>Hello " +
                "World</html>");

        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/html")
                .setHtmlText("<html>Hello World</html>")
                .setChooserTitle("View In Browser")
                .setStream(Uri.fromFile(file))
                .createChooserIntent();
        shareIntent.setAction(Intent.ACTION_VIEW);
        shareIntent.setType("text/html");

        shareIntent.setData(Uri.fromFile(file));
        try {
            Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "" + file.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "" + Uri.fromFile(file));
        Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "" + shareIntent.toUri(0));
        Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "" + shareIntent.toUri(Intent.URI_INTENT_SCHEME));
        Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "" + shareIntent.toUri(Intent.URI_ANDROID_APP_SCHEME));
        //shareIntent.setData(Uri.parse(shareIntent.toUri(0)));
        try {
            shareIntent.setData(Uri.parse(file.toURI().toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // shareIntent.setData(Uri.parse("http://www.example.com"));
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //shareIntent.setPackage("com.android.chrome");

        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(shareIntent);
        }

    }


    public File  generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(getCacheDir(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            return gpxfile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
