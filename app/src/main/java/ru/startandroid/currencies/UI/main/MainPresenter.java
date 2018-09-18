package ru.startandroid.currencies.UI.main;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import ru.startandroid.currencies.model.Response;
import ru.startandroid.currencies.model.Valute;
import ru.startandroid.currencies.network.ServiceGenerator;

@InjectViewState
public class MainPresenter
        extends MvpPresenter<MainView>{

    private List<Valute> listValutesToday;
    private List<Valute> listValutesYest;

    private final String FORMAT_DATE = "dd.MM.yyyy";
    private String dateTodayString = "";

    private DisposableSingleObserver disposableFirstObs;
    private DisposableSingleObserver disposableSecondObs;

    private String getYesterday() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        Calendar calendar = new GregorianCalendar();

        Date date = null;
        try {
            date = sdf.parse(dateTodayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(calendar.getTime());
        }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        if (listValutesYest == null
                && listValutesToday == null) startRequestFirst();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void startRequestFirst() {
        disposableFirstObs = new DisposableSingleObserver<Response>() {
            @Override
            public void onSuccess(Response response) {
                listValutesToday = response.getValutes();
                dateTodayString = response.getDate();


                startRequestSecond();
            }

            @Override
            public void onError(Throwable e) {
            }
        };

        ServiceGenerator.getInstance()
                .getApiService()
                .getValutes(null)
                .observeOn(AndroidSchedulers.mainThread())
                .retry()
                .subscribe(disposableFirstObs);
    }

    private void startRequestSecond() {
        disposableSecondObs = new DisposableSingleObserver<Response>() {
            @Override
            public void onSuccess(Response response) {
                listValutesYest = response.getValutes();
                getViewState().setListValutesToday(listValutesToday,
                        dateTodayString);
                getViewState().setListValutesYest(listValutesYest);
            }

            @Override
            public void onError(Throwable e) {
            }
        };

        ServiceGenerator.getInstance()
                .getApiService()
                .getValutes(getYesterday())
                .observeOn(AndroidSchedulers.mainThread())
                .retry()
                .subscribe(disposableSecondObs);
    }

    @Override
    public void detachView(MainView view) {
        super.detachView(view);
        if (disposableFirstObs != null)
            disposableFirstObs.dispose();
        if (disposableSecondObs != null)
            disposableSecondObs.dispose();
    }
}
