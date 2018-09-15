package ru.startandroid.currencies.UI.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import ru.startandroid.currencies.model.Response;
import ru.startandroid.currencies.model.Valute;
import ru.startandroid.currencies.network.ServiceGenerator;

@InjectViewState
public class MainPresenter
        extends MvpPresenter<MainView>{

    final String TAG = "myLogs";

    private Call<Response> callYestDay;
    private Call<Response> callToday;
    private Response responseDay;
    private Response responseYest;
    private List<Valute> listValutesToday;
    private List<Valute> listValutesYest;

    private String lastDay;


    public MainPresenter() {
        getLastDay();
    }

    private void getLastDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        date.setTime(date.getTime() - 41_400_000L); // Отнимаем от текущего времени 11.5 часов т.к. ЦБ обновляется курс валют в 11:30
        lastDay = sdf.format(date);
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        Log.i(TAG, "attachView: ");
        if (listValutesYest != null
                && listValutesToday != null)
        startSavedInstance();
        else startRequest();
    }

    @Override
    protected void onFirstViewAttach() {
        Log.i(TAG, "onFirstViewAttach: ");
        super.onFirstViewAttach();
        startRequest();
    }

    private void startSavedInstance() {
        getViewState().setListValutesToday(listValutesToday);
        getViewState().setListValutesYest(listValutesYest);
    }

    public void startRequest(){
        Log.i(TAG, "startRequest: ");

        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();

        callYestDay = serviceGenerator.getApiService().getValutes(lastDay);

        callYestDay
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call,
                                           @NonNull retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            responseYest = response.body();
                            listValutesYest = responseYest.getValutes();

                            getViewState().setListValutesYest(listValutesYest);
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });


        callToday = serviceGenerator.getApiService().getValutes(null);
        callToday
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call,
                                           retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            responseDay = response.body();
                            listValutesToday = responseDay.getValutes();

                            getViewState().setListValutesToday(listValutesToday);
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
    }
}
