package ru.startandroid.currencies.UI.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    private final String FORMAT_DATE = "dd.MM.yyyy";

    private String getYesterday() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        Calendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if (day == Calendar.SUNDAY
                || day == Calendar.SATURDAY) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY); }
        else if (day == Calendar.MONDAY)
            if (calendar.get(Calendar.HOUR_OF_DAY) < 14) {
                    calendar.add(Calendar.WEEK_OF_MONTH, -1);
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY); }
        else if (calendar.get(Calendar.HOUR_OF_DAY) < 14) {
                calendar.add(Calendar.DAY_OF_MONTH, -1); }

        return sdf.format(calendar.getTime());
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
                .getValutes(getYesterday())
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
