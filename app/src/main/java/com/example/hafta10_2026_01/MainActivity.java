package com.example.hafta10_2026_01;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    public class myTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strs) {
            // https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty

            try {
                URL url = new URL("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                int status = con.getResponseCode();
                if (status == 200) {
                   // BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    int line;
                    InputStreamReader rd= new InputStreamReader(con.getInputStream());

                    while ((line = rd.read()) != -1) {
                        sb.append((char) line );
                    }
                    rd.close();
                    return sb.toString();
                }

            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }


    public class mySecondTask extends AsyncTask<String, String, HackerNewsItem> {

        @Override
        protected HackerNewsItem doInBackground(String... strs) {
            //https://hacker-news.firebaseio.com/v0/item/192327.json?print=pretty
            try {
                URL url = new URL ("https://hacker-news.firebaseio.com/v0/item/"+strs[0]+".json?print=pretty");
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                int status = con.getResponseCode();
                if (status == 200) {
                    StringBuilder sb= new StringBuilder("");
                    InputStreamReader rd= new InputStreamReader(con.getInputStream());
                    int chr;
                    while((chr=rd.read())!=-1){
                        sb.append((char)chr);
                    }
                    rd.close();
                    JSONObject obj = new JSONObject(sb.toString());
                    HackerNewsItem hn = new HackerNewsItem(
                            obj.getString("title"),
                            obj.getString("url"),
                            obj.getInt("id"),
                            obj.getString("type"));
                    if (hn.getType()=="story" ) {
                        return hn;
                    }else
                            return null;
                    }


                } catch (ProtocolException ex) {
                throw new RuntimeException(ex);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            return null;
        }
    }

    ArrayList<HackerNewsItem> liste= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        myTask mt = new myTask();
        try {
            String result =   mt.execute().get();
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                Log.d("RESULT",ja.getString(i));
                mySecondTask mt2 = new mySecondTask();
                HackerNewsItem hn = mt2.execute(ja.getString(i)).get();
                if (hn!=null) {
                    liste.add(hn);
                }

            }

            Log.d("RESULT",result);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        }
}