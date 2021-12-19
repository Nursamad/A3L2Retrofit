package com.geektech.a3l2retrofit;

import android.app.Application;

import com.geektech.a3l2retrofit.data.remote.HerokuApi;
import com.geektech.a3l2retrofit.data.remote.RetrofitClient;

public class App extends Application {

public  static HerokuApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitClient client = new RetrofitClient();
        api = client.provideApi();

    }
}
