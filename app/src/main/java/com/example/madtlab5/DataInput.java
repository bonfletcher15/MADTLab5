package com.example.madtlab5;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
public class DataInput extends AsyncTask<String, Void, ArrayList<String>> 
{
    private Context context;
    private ListView listView;
    public DataInput(Context context, ListView listView) 
{
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected ArrayList<String> doInBackground(String... urls) 
{
        ArrayList<String> holidaysList = new ArrayList<>();
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            StringBuilder result = new StringBuilder();
            Scanner scanner = new Scanner(stream);
            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine());
            }
            holidaysList = parseJSON(result.toString());
            scanner.close();
            stream.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return holidaysList;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        super.onPostExecute(result);
        ((MainActivity) context).setAdapter(result);
    }

    private ArrayList<String> parseJSON(String jsonData) {
        ArrayList<String> holidaysList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject englandAndWales = jsonObject.getJSONObject("england-and-wales");
            JSONArray events = englandAndWales.getJSONArray("events");

            for (int i = 0; i < events.length(); i++) {
                JSONObject event = events.getJSONObject(i);
                String title = event.getString("title");
                String date = event.getString("date");
                holidaysList.add(title + " - " + date);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return holidaysList;
    }
}