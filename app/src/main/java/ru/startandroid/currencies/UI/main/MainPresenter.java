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

    private List<Valute> listValutesToday;
    private List<Valute> listValutesYest;

    private String yesterday;


    public MainPresenter() {
        getYesterday();
    }

    private void getYesterday() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        date.setTime(date.getTime() - 41_400_000L); // Отнимаем от текущего времени 11.5 часов т.к. ЦБ обновляется курс валют в 11:30
        yesterday = sdf.format(date);
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        if (listValutesYest == null
                && listValutesToday == null) startRequest();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void startRequest() {

        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();

        serviceGenerator
                .getApiService()
                .getValutes(yesterday)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call,
                                           @NonNull retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            listValutesYest = response.body().getValutes();

                            getViewState().setListValutesYest(listValutesYest);
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });

        serviceGenerator
                .getApiService()
                .getValutes(null)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call,
                                           retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            listValutesToday = response.body().getValutes();

                            getViewState().setListValutesToday(listValutesToday);
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
    }
}
