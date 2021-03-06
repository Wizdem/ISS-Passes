package com.vinay.wizdem.spacestationfinder.presenter;

import android.util.Log;

import com.vinay.wizdem.spacestationfinder.model.flyby.FlyBy;
import com.vinay.wizdem.spacestationfinder.rest.ApiClient;
import com.vinay.wizdem.spacestationfinder.rest.ApiInterface;
import com.vinay.wizdem.spacestationfinder.view.MainView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vinay_1 on 1/6/2018.
 * Presenter Class for MainActivity
 * supports view decision making with the help of PresenterLifeCycle call backs (didn't used in this instance)
 * Communicate with model and call rest api for use current location flyby
 *
 */

public class MainPresenter implements PresenterLifeCycle {

    private MainView view;

    public MainPresenter(MainView view){
        this.view = view;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
    // requesting the current location flyby date and time by passing user current geo coordinates
    public void onFlybyRequest(double lat, double lon, double alt){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FlyBy> call = apiInterface.getFlyByList(lat,lon,alt);
        call.enqueue(new Callback<FlyBy>() {
            @Override
            public void onResponse(Call<FlyBy> call, Response<FlyBy> respons) {
                    try{
                        List<com.vinay.wizdem.spacestationfinder.model.flyby.Response> model = respons.body().getResponse();
                        view.displayList(model);
                    }catch (Exception e){
                        Log.e("FLYBY", String.valueOf(e));
                    }

            }

            @Override
            public void onFailure(Call<FlyBy> call, Throwable t) {
                view.onFailureRestRequest();
            }
        });
    }
}
