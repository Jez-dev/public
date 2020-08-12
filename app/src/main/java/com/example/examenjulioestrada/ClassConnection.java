package com.example.examenjulioestrada;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClassConnection extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection httpURLConnection = null;
        URL url = null;
        try{
            url = new URL(strings[0]);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            int code = httpURLConnection.getResponseCode();
            if( code == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String linea ="";
                StringBuffer stringBuffer = new StringBuffer();
                while((linea = bufferedReader.readLine())!=null){
                    stringBuffer.append(linea);
                }
                return stringBuffer.toString();
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
