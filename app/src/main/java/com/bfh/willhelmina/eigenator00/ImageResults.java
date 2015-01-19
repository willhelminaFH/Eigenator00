package com.bfh.willhelmina.eigenator00;

import android.net.Uri;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Willhelmina on 1/3/2015.
 */
public class ImageResults {

    String query;
    int pageNum;
    int rsz;
    int totalHits;
    int trNum;
    int maxReturn;

    ArrayList<String> imgUrls = new ArrayList<>();
    //maybe put an iterator/iterable some where in here
    AsyncHttpClient client;

    ImageResults(){
        client = new AsyncHttpClient();
        maxReturn = 0;
        pageNum = 0;
        trNum = 100;
        query = "eigenate!";
    }

    String qUrl(String q){
        query = q;
        System.out.println("search URL: https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=" +
                Uri.encode(q) +
                "&start=" + pageNum);

        return "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=" +
                Uri.encode(q) +
                "&start=" + pageNum;
    }

    public ArrayList<String> getImgUrls() {
        return imgUrls;
    }

    public void getUrls(final String q, final ImageResultsCallback callBack){//maybe switch the return val to arraylist
        //final String query = q;// = qUrl(q);
        pageNum = 0;
        imgUrls.clear();
        client.get(qUrl(q), new JsonHttpResponseHandler() {

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

                //ArrayList<String> imgURLs = new ArrayList<String>();
                try {

                    if (response.getJSONObject(dkeyA) != null) {
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
                            imgUrls.add(t);

                        }

                        System.out.println("cursor: " + curObj.names());
                        System.out.println("numResults: " + curObj.get("estimatedResultCount"));
                        System.out.println("moreLinks: " + curObj.get("moreResultsUrl"));

                    /*

                    for (int i = 0; i < pagArray.length(); i++) {
                        System.out.println("pages " + i + ": " + pagArray.get(i));
                        System.out.println("\n");
                    }
                    */
                        //works for now but it keeps ending with exception caught...
                        //so fix the logic
                        if (imgUrls.size() < Integer.parseInt(response.getJSONObject(dkeyA).
                                getJSONObject(ckeyA).getString("estimatedResultCount")) ||
                                imgUrls.size() < 455) {

                            System.out.println("curCount = " + imgUrls.size());
                            System.out.println("getting more");
                            pageNum++;
                            client.get(qUrl(q), this);
                        } else {
                            System.out.println("finished!: logic caught");
                        }
                    } else {
                        System.out.println("Finished!");
                    }
                }catch (JSONException e){
                    System.out.println("finished!: exception caught");
                    callBack.call();
                }

                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
