package com.example.madtlab5;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
        ArrayList<String> currencyList = new ArrayList<>();
        try
        {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            StringBuilder result = new StringBuilder();
            while (scanner.hasNextLine())
            {
                result.append(scanner.nextLine());
            }
            Parser parser = new Parser();
            currencyList = parser.parseXML(result.toString());
            scanner.close();
            stream.close();
            connection.disconnect();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return currencyList;
    }
    @Override
    protected void onPostExecute(ArrayList<String> result)
    {
        super.onPostExecute(result);
        ((MainActivity) context).setAdapter(result);
    }
}
