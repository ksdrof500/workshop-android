package br.com.workshop;

import android.app.Application;

import br.com.workshop.network.APIManager;

public class MainApplication extends Application {
    private static MainApplication instance;

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        APIManager.getInstance().setContext(this);
    }

}
