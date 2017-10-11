package com.kekland.enis.NISApi;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Gulnar on 09.10.2017.
 */

public class Requester {
    static AsyncHttpClient client;
    static PersistentCookieStore store;

    public static void Init(Context baseContext) {
        client = new AsyncHttpClient();
        store = new PersistentCookieStore(baseContext);
        client.setCookieStore(store);
    }

    public static void Request(RequestParams params, String URL, final RequestListener listener) {
        listener.onStart();
        client.post("https://nis-api.herokuapp.com/" + URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (!response.getBoolean("success")) {
                        if(response.has("message")) {
                            listener.onFailure(response.getString("message"));
                        }
                        else {
                            listener.onFailure("Error occurred while connecting to server");
                        }
                    }
                    else {
                        listener.onSuccess(response);
                    }
                }
                catch(Exception e) {
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onFailure(int code, Header[] headers, Throwable throwable, JSONObject response) {
                listener.onFailure(throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFailure(responseString);
            }
        });
    }

    public interface RequestListener {
        void onStart();
        void onSuccess(JSONObject data);
        void onFailure(String message);
    }
}
