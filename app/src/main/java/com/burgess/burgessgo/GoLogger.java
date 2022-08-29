package com.burgess.burgessgo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class GoLogger {
    private static final String TAG = "GO_LOGGER";

    private static GoLogger instance;
    private static Context ctx;
    private static File logFile;

    private GoLogger(Context context) {
        ctx = context;
        logFile = new File(ctx.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "GoLogFile.txt");
        if (logFile.lastModified() > 0 && !DateUtils.isToday(logFile.lastModified())); {
            logFile.delete();
        }
    }

    public static synchronized GoLogger getInstance(Context context) {
        if (instance == null) {
            instance = new GoLogger(context);
        }
        return instance;
    }

    public static synchronized GoLogger getInstance() {
        if (instance == null) {
            throw new IllegalStateException(GoLogger.class.getSimpleName() + " is not initialized, call getInstance(Context context) first");
        }
        return instance;
    }

    public static void createLogFile(File logFile) {
        try {
            File newLogFile = new File(logFile.getAbsolutePath());
            newLogFile.createNewFile();
            Log.i(TAG, "New file created.");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static void log(char flag, String tag, String message) {
        switch(flag) {
            case 'E':
                Log.e(tag, message);
                break;
            case 'I':
                Log.i(tag, message);
                break;
            default:
                Log.d(tag, message);
                break;
        }
        try {
            if (!logFile.exists()) {
                createLogFile(logFile);
            } else {
                FileWriter writer = new FileWriter(logFile, true);
                writer.append(String.format("%s - %s - %s - %s\n", LocalDateTime.now().toString(), flag, tag, message));
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static Intent sendLogFile(int securityUserId, String versionName) {
        Uri logFileUri = FileProvider.getUriForFile(ctx, "com.burgess.burgessgo", logFile);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("vnd.android.cursor.dir/email");
        String[] to = {"jwilliams@burgess-inc.com", "rsandlin@burgess-inc.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(logFileUri)));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Activity log from BurgessGo for User " + securityUserId);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "BurgessGo version: " + versionName);
        return emailIntent;
    }
}
