package com.example.nikhil.earthquake;
       import android.os.AsyncTask;

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

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils{
   private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.geojson";
 static HttpURLConnection connection;

    public class earthquakeAsync extends AsyncTask<URL,Void,ArrayList<Earthquake>>{
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
        try {
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            inputStream=httpURLConnection.getInputStream();
            jsonResponse=readfromstream(inputStream);
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
    // Making HTTP request

        /** Sample JSON response for a USGS query */

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
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