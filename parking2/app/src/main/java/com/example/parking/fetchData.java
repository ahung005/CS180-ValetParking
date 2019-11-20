package com.example.parking;

import android.os.AsyncTask;

import com.example.parking.HomePage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData  extends AsyncTask<Void, Void, Void> {

    String data_big_springs;
    String data_lot_6;
    String data_lot_24;
    String data_lot_26;
    String data_lot_30;
    String data_lot_32;
    String data_lot_50;

    String spaces_big_springs;
    String spaces_lot_6;
    String spaces_lot_24;
    String spaces_lot_26;
    String spaces_lot_30;
    String spaces_lot_32;
    String spaces_lot_50;


    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL big_springs = new URL("https://streetsoncloud.com/parking/rest/occupancy/id/84?callback=jQuery32108579276927202124_1573854847941&_=1573854848305");
            URL lot_6 = new URL("https://streetsoncloud.com/parking/rest/occupancy/id/496?callback=jQuery32108579276927202124_1573854847943&_=1573854848306");
            URL lot_24 = new URL("https://streetsoncloud.com/parking/rest/occupancy/id/243?callback=jQuery32108579276927202124_1573854847949&_=1573854848307");
            URL lot_26 = new URL("https://streetsoncloud.com/parking/rest/occupancy/id/80?callback=jQuery32108579276927202124_1573854847945&_=1573854848308");
            URL lot_30 = new URL("https://streetsoncloud.com/parking/rest/occupancy/id/82?callback=jQuery32108579276927202124_1573854847947&_=1573854848309");
            URL lot_32 = new URL("https://streetsoncloud.com/parking/rest/occupancy/id/494?callback=jQuery32108579276927202124_1573854847953&_=1573854848310");
            URL lot_50 = new URL("https://streetsoncloud.com/parking/rest/occupancy/id/495?callback=jQuery32108579276927202124_1573854847951&_=1573854848311");

            HttpURLConnection httpURLConnection_big_springs = (HttpURLConnection) big_springs.openConnection();
            InputStream inputStream_big_springs = httpURLConnection_big_springs.getInputStream();
            BufferedReader bufferedReader_big_springs = new BufferedReader(new InputStreamReader(inputStream_big_springs));
            String big_springs_line = "";
            while(big_springs_line != null) {
                big_springs_line = bufferedReader_big_springs.readLine();
                data_big_springs = data_big_springs + big_springs_line;
            }
            data_big_springs = data_big_springs.substring(data_big_springs.indexOf("free_spaces") + 14, data_big_springs.indexOf("\"",data_big_springs.indexOf("free_spaces") + 14));
            spaces_big_springs = data_big_springs.replaceAll("[^0-9]", "");
            spaces_big_springs = spaces_big_springs.replaceAll("\"", "");

            HttpURLConnection httpURLConnection_lot_6 = (HttpURLConnection) lot_6.openConnection();
            InputStream inputStream_lot_6 = httpURLConnection_lot_6.getInputStream();
            BufferedReader bufferedReader_lot_6 = new BufferedReader(new InputStreamReader(inputStream_lot_6));
            String lot_6_line = "";
            while(lot_6_line != null) {
                lot_6_line = bufferedReader_lot_6.readLine();
                data_lot_6 = data_lot_6 + lot_6_line;
            }
            data_lot_6 = data_lot_6.substring(data_lot_6.indexOf("free_spaces") + 14, data_lot_6.indexOf("\"",data_lot_6.indexOf("free_spaces") + 14));
            spaces_lot_6 = data_lot_6.replaceAll("[^0-9]", "");


            HttpURLConnection httpURLConnection_lot_24 = (HttpURLConnection) lot_24.openConnection();
            InputStream inputStream_lot_24 = httpURLConnection_lot_24.getInputStream();
            BufferedReader bufferedReader_lot_24 = new BufferedReader(new InputStreamReader(inputStream_lot_24));
            String lot_24_line = "";
            while(lot_24_line != null) {
                lot_24_line = bufferedReader_lot_24.readLine();
                data_lot_24 = data_lot_24 + lot_24_line;
            }
            data_lot_24 = data_lot_24.substring(data_lot_24.indexOf("free_spaces") + 14, data_lot_24.indexOf("\"",data_lot_24.indexOf("free_spaces") + 14));
            spaces_lot_24 = data_lot_24.replaceAll("[^0-9]", "");

            HttpURLConnection httpURLConnection_lot_26 = (HttpURLConnection) lot_26.openConnection();
            InputStream inputStream_lot_26 = httpURLConnection_lot_26.getInputStream();
            BufferedReader bufferedReader_lot_26 = new BufferedReader(new InputStreamReader(inputStream_lot_26));
            String lot_26_line = "";
            while(lot_26_line != null) {
                lot_26_line = bufferedReader_lot_26.readLine();
                data_lot_26 = data_lot_26 + lot_26_line;
            }
            data_lot_26 = data_lot_26.substring(data_lot_26.indexOf("free_spaces") + 14, data_lot_26.indexOf("\"",data_lot_26.indexOf("free_spaces") + 14));
            spaces_lot_26 = data_lot_26.replaceAll("[^0-9]", "");

            HttpURLConnection httpURLConnection_lot_30 = (HttpURLConnection) lot_30.openConnection();
            InputStream inputStream_lot_30 = httpURLConnection_lot_30.getInputStream();
            BufferedReader bufferedReader_lot_30 = new BufferedReader(new InputStreamReader(inputStream_lot_30));
            String lot_30_line = "";
            while(lot_30_line != null) {
                lot_30_line = bufferedReader_lot_30.readLine();
                data_lot_30 = data_lot_30 + lot_30_line;
            }
            data_lot_30 = data_lot_30.substring(data_lot_30.indexOf("free_spaces") + 14, data_lot_30.indexOf("\"",data_lot_30.indexOf("free_spaces") + 14));
            spaces_lot_30 = data_lot_30.replaceAll("[^0-9]", "");

            HttpURLConnection httpURLConnection_lot_32 = (HttpURLConnection) lot_32.openConnection();
            InputStream inputStream_lot_32 = httpURLConnection_lot_32.getInputStream();
            BufferedReader bufferedReader_lot_32 = new BufferedReader(new InputStreamReader(inputStream_lot_32));
            String lot_32_line = "";
            while(lot_32_line != null) {
                lot_32_line = bufferedReader_lot_32.readLine();
                data_lot_32 = data_lot_32 + lot_32_line;
            }
            data_lot_32 = data_lot_32.substring(data_lot_32.indexOf("free_spaces") + 14, data_lot_32.indexOf("\"",data_lot_32.indexOf("free_spaces") + 14));
            spaces_lot_32 = data_lot_32.replaceAll("[^0-9]", "");

            HttpURLConnection httpURLConnection_lot_50 = (HttpURLConnection) lot_50.openConnection();
            InputStream inputStream_lot_50 = httpURLConnection_lot_50.getInputStream();
            BufferedReader bufferedReader_lot_50 = new BufferedReader(new InputStreamReader(inputStream_lot_50));
            String lot_50_line = "";
            while(lot_50_line != null) {
                lot_50_line = bufferedReader_lot_50.readLine();
                data_lot_50 = data_lot_50 + lot_50_line;
            }
            data_lot_50 = data_lot_50.substring(data_lot_50.indexOf("free_spaces") + 14, data_lot_50.indexOf("\"",data_lot_50.indexOf("free_spaces") + 14));
            spaces_lot_50 = data_lot_50.replaceAll("[^0-9]", "");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //MainActivity.textView6.setText(spaces_big_springs);
        HomePage.textView_big_springs.setText(data_big_springs);
        HomePage.textView_lot_6.setText(data_lot_6);
        HomePage.textView_lot_24.setText(data_lot_24);
        HomePage.textView_lot_26.setText(data_lot_26);
        HomePage.textView_lot_30.setText(data_lot_30);
        HomePage.textView_lot_32.setText(data_lot_32);
        HomePage.textView_lot_50.setText(data_lot_50);
    }

}
