package com.burgess.burgessgo.report_viewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.burgess.burgessgo.R;

public class ReportViewerActivity extends AppCompatActivity {
    private static final String TAG = "REPORT_VIEWER";
    public static final String INTENT_EXTRA = "REPORT_URL";

    private String reportUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_viewer);

        Intent intent = getIntent();
        reportUrl = intent.getStringExtra(INTENT_EXTRA);
        reportUrl = reportUrl.substring(1, reportUrl.length()-1);

        WebView webView = findViewById(R.id.report_viewer_web_view);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://portal.burgess-inc.com" + reportUrl);
                ///Report/ReportViewer.aspx?ReportName=SW5zcGVjdGlvblJlcG9ydFJlc2lkZW50aWFsNlNURENvbW1lbnREZWZlY3RQaG90bw==&ReportOutput=QnJvd3Nlcg==&InspectionID=MjYwNzc4Ng==");
    }
}