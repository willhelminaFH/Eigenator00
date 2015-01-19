package com.bfh.willhelmina.eigenator00;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Eigenate extends Activity {

    EditText eigenQ;
    ImageResults gImgHandler;
    ImageResultsCallback callBack = new ImageResultsCallback(){
        @Override
        public void call(){
            Intent intent = new Intent(Eigenate.this, ProcSketch.class);
            intent.putStringArrayListExtra("com.bfh.willhelmina.eigenator00.imgurls", gImgHandler.getImgUrls());
            System.out.println(intent.getStringArrayListExtra("com.bfh.willhelmina.eigenator00.imgurls").getClass());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eigenate);
        setupShit();
        setupViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.eigenate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupViews(){
        eigenQ  = (EditText) findViewById(R.id.eigenQ);
    }
    public void setupShit(){
        gImgHandler = new ImageResults();
    }

    public void eigenateImgs(View v){
        gImgHandler.getUrls(eigenQ.getText().toString(),
                callBack
                );
    }

    //move this later
    /*
    public void eigenateImgs(View v){
        //the way the API seems to work, you have to keep requesting more JSON objects
        //so having this be a single return seems silly.  what might make more sense
        //is having the part of code that gets the json objects wrapped in another
        //function so it can get called recursively till we get all the results we
        //need
        String s(){ = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=" +
                Uri.encode(eigenQ.getText().toString()+
                "&start="+Uri.encode(pageNum));


        AsyncHttpClient client = new AsyncHttpClient();
        getImageUrls();//wrong way
        public void getImageUrls(String s) {
            client.get(s, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode,
                                      org.apache.http.Header[] headers,
                                      org.json.JSONObject response) {

                    //testCode
                    System.out.println("status " + statusCode);
                    JSONArray names = response.names();//don't think I use this
                    String dkeyA = "responseData";
                    String dkeyB = "results";
                    String ckeyA = "cursor";
                    String ckeyB = "pages";

                    JSONArray respArray;
                    JSONArray pagArray;
                    JSONObject curObj;//probs can cut this out

                    ArrayList<String> imgURLs = new ArrayList<String>();
                    try {

                        respArray = response.getJSONObject(dkeyA).getJSONArray(dkeyB);
                        pagArray =
                                response.getJSONObject(dkeyA).
                                        getJSONObject(ckeyA).
                                        getJSONArray(ckeyB);

                        curObj = response.getJSONObject(dkeyA).getJSONObject(ckeyA);//probs can cut
                        String t;
                        JSONObject tStrings;

                        System.out.println(dkeyA + ": " + response.getJSONObject(dkeyA).names());

                        for (int i = 0; i < respArray.length(); i++) {
                            tStrings = respArray.getJSONObject(i);
                            t = tStrings.getString("url");
                            //System.out.println("names: "+tStrings.names());
                            System.out.println("url# " + i + ": " + t);
                        }

                        System.out.println("cursor: " + curObj.names());
                        System.out.println("numResults: " + curObj.get("estimatedResultCount"));
                        System.out.println("moreLinks: " + curObj.get("moreResultsUrl"));

                        for (int i = 0; i < pagArray.length() || i > 4; i++) {
                            System.out.println("pages " + i + ": " + pagArray.get(i));
                            System.out.println("\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /*
                    try {
                        for (int i = 0; i < names.length(); i++) {
                            System.out.println("name: "+names.getString(i));
                            System.out.println("class: "+names.getClass().toString());
                            System.out.println(dkeyA+": "+response.getJSONObject(dkeyA).names());
                            System.out.println(dkeyB+": "+response.getJSONObject(dkeyA).getJSONArray(dkeyB).toString());

                            System.out.println("");

                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }*/
                    /*
                    try{
                        ArrayList<String> outList = new ArrayList<>();
                        int i = 0;
                        while (response.keys().hasNext()){
                            i++;
                            //System.out.println("getting results: " + i);
                            outList.add(response.keys().next());

                        }
                        System.out.println("returnSize"+outList.size());
                    }catch(Exception e){
                        System.out.println("ERROR");
                        e.printStackTrace();
                    }
                }
            });
        }
    }*/
}
