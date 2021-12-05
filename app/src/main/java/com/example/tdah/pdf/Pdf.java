package com.example.tdah.pdf;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pdf extends AsyncTask<String,Void, InputStream> {
    PDFView pdfView;
     ProgressBar progressBar;

    public Pdf(PDFView pdfView, ProgressBar progressBar) {
        this.pdfView = pdfView;
        this.progressBar = progressBar;
    }

    @Override
    protected InputStream doInBackground(String... strings) {
        InputStream inputStream = null;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if(urlConnection.getResponseCode() == 200)
                inputStream = new BufferedInputStream(urlConnection.getInputStream());

        }catch (IOException e){
            return  null;
        }
        return inputStream;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);
        pdfView.fromStream(inputStream).load();
        progressBar.setVisibility(View.GONE);

    }
}
