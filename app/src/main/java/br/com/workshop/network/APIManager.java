package br.com.workshop.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {

    private static final String ARE_YOU_APP = "https://private-080037-workshop13.apiary-mock.com/";
    private static OkHttpClient client;

    private static APIManager instance;

    private AppService appService;
    private Context context;

    private APIManager() {

    }

    private void initialize() {

        final Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").setLenient().create();
        final GsonConverterFactory factory = GsonConverterFactory.create(gson);

        client = new OkHttpClient();

        Retrofit workshop = new Retrofit.Builder()
                .baseUrl(ARE_YOU_APP).client(client).addConverterFactory(factory).build();

        appService = workshop.create(AppService.class);

    }

    public static APIManager getInstance() {

        if (instance == null) {
            instance = new APIManager();
        }

        return instance;
    }

    public static AppService getAppService() {
        return getInstance().appService;
    }

    public void setContext(Context context) {
        this.context = context;
        if (appService == null) {
            initialize();
        }
    }
}
