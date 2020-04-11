package com.example.covidissue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RetrieveFeedTask asyncTask=new RetrieveFeedTask();
        asyncTask.execute();
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {
        String chandan,loc;
        String[] listItem;
;
        private Exception exception;

        protected String doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://covid-19-coronavirus-statistics.p.rapidapi.com/v1/stats?country=All")
                        .get()
                        .addHeader("x-rapidapi-host", "covid-19-coronavirus-statistics.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "351678b8e4mshc295c7ab1473619p112a29jsndae0a89ef15c")
                        .build();

                Response response = client.newCall(request).execute();
                chandan  = response.body().string(); ;
            } catch (Exception e) {
                this.exception = e;
            }
           return  chandan;
        }

        protected void onPostExecute(String  feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
            try {
                JSONObject json = new JSONObject(chandan);
             //   JSONArray lineItems = json.getJSONArray("covid19Stats");




                ListView textView = findViewById(R.id.txtString);

               // textView.setText(json.getJSONObject("data").getJSONArray("covid19Stats").getString(1));
                JSONArray lineItems = json.getJSONObject("data").getJSONArray("covid19Stats");
                int length = lineItems.length();
                listItem = new String[length];
                for(int i=0;i<length;i++)
                {
                    JSONObject rec = lineItems.getJSONObject(i);
                     //loc = rec.getString("province");
                   // loc=loc.concat(loc);
                    String chandanlal= rec.getString("province");
                    //if(chandanlal != null && !chandanlal.isEmpty())
                    listItem[i] =chandanlal;
                }
                //textView.setText(loc);
                //getString("covid19Stats"));
                 ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,listItem);
                    textView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}