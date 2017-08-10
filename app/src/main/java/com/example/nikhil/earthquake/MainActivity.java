package com.example.nikhil.earthquake;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       earthquakeAsync task=new earthquakeAsync();
        task.execute();

    }
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";
    static HttpURLConnection connection;

    public class earthquakeAsync extends AsyncTask<URL,Void,ArrayList<Earthquake>> {
        ArrayList<Earthquake> earth;
        @Override
        protected ArrayList<Earthquake> doInBackground(URL... params) {
            URL url=create(USGS_REQUEST_URL);
            String jsonResponse="";
            try {
                jsonResponse=makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            earth=extractEarthquakes(jsonResponse);
            return earth;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
if(earth==null){
    return;
}
     set(earth);
        }
    }

private void set(ArrayList<Earthquake> earthquakes){
    ArrayList<Earthquake> arrayList= new ArrayList<Earthquake>();
    arrayList=earthquakes;
    final Earthaquake_adapter adapter=new Earthaquake_adapter(MainActivity.this,arrayList);
    ListView listView=(ListView)findViewById(R.id.list_view);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Earthquake current=adapter.getItem(position);
            Uri EarthquakeUri=Uri.parse(current.getmUri());
            Intent web=new Intent(Intent.ACTION_VIEW,EarthquakeUri);
            startActivity(web);
        }
    });
}
    private URL create(String str){
        URL url=null;
        try {
            url=new URL(str);
        } catch (MalformedURLException e) {
            return null;
        }
        return url;
    }
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;

        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(String.valueOf(url));
        try {
HttpResponse httpResponse=httpClient.execute(httpGet);
            //httpURLConnection=(HttpURLConnection)url.openConnection();
            //httpURLConnection.setRequestMethod("GET");
            //httpURLConnection.setReadTimeout(10000);
            //httpURLConnection.setConnectTimeout(15000);
            //httpURLConnection.connect();
            //inputStream=httpURLConnection.getInputStream();
            BufferedReader rd=new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer result=new StringBuffer();
            String line="";
            while((line=rd.readLine())!=null){
                result.append(line);
            }
            jsonResponse=result.toString();
            //jsonResponse=readfromstream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readfromstream(InputStream inputStream) throws IOException {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputstreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader=new BufferedReader(inputstreamReader);
            String line=reader.readLine();
            while (line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }
       private ArrayList<Earthquake> extractEarthquakes(String json) {
        ArrayList<Earthquake> arrayList= new ArrayList<Earthquake>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("features");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object=jsonArray.getJSONObject(i);

                JSONObject object1 = object.getJSONObject("properties");
                String mag= object1.getString("mag");
                String place=object1.getString("place");
                long time = Long.parseLong(object1.getString("time"));
                String Url=object1.getString("url");
                arrayList.add(new Earthquake(mag,place,time,Url));
            }
        } catch (JSONException e) {
        }
        return arrayList;
    }
}
