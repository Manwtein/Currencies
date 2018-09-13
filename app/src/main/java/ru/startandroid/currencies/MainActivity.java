package ru.startandroid.currencies;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import ru.startandroid.currencies.adapter.RecyclerAdapter;
import ru.startandroid.currencies.model.Response;
import ru.startandroid.currencies.model.Valute;

public class MainActivity extends AppCompatActivity {

    private List<Valute> listValutesToday;
    private List<Valute> listValutesYest;

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;


    private String TAG = "myLogs";
    private String lastDay;

    private Call<Response> callToday;
    private Call<Response> callYestDay;
    private Response responseDay;
    private Response responseYest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listValutesToday = new ArrayList<>();
        listValutesYest = new ArrayList<>();

        getLastDay();

        initRecyclerView();

        if (savedInstanceState == null) {
            startRequest();
        } else if (savedInstanceState.size() == 0) {
            startRequest();
        } else {
            listValutesToday = savedInstanceState.getParcelableArrayList("myObjects");
            listValutesYest = savedInstanceState.getParcelableArrayList("myObjectsYest");
            recyclerAdapter.setListValutesYest(listValutesYest);
            recyclerAdapter.setListValutesToday(listValutesToday);
        }
    }

    private void startRequest()  {
        Log.i(TAG, "startRequest: ");

            ApiService apiService = ServiceGenerator.createService(ApiService.class);

            callYestDay = apiService.getValutes(lastDay);

            callYestDay
                    .enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call,
                                       @NonNull retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        responseYest = response.body();
                        listValutesYest = responseYest.getValutes();

                        if (listValutesYest == null) return;
                        recyclerAdapter.setListValutesYest(listValutesYest);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {

                }
            });



            callToday = apiService.getValutes(null);
            callToday
                    .enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call,
                                       retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
                        responseDay = response.body();
                        listValutesToday = responseDay.getValutes();

                        if (listValutesToday == null) return;
                        recyclerAdapter.setListValutesToday(listValutesToday);
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {

                }
            });
        }

    private void getLastDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        date.setTime(date.getTime() - 41_400_000L); // Отнимаем от текущего времени 11.5 часов т.к. ЦБ обновляется курс валют в 11:30
        lastDay = sdf.format(date);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        if (listValutesToday.size() == 0
                || listValutesYest.size() == 0){
                callToday.cancel();
                callYestDay.cancel();
        }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (listValutesToday.size() != 0
                && listValutesYest.size() != 0) {
            outState.putParcelableArrayList("myObjects", (ArrayList<Valute>) listValutesToday);
            outState.putParcelableArrayList("myObjectsYest", (ArrayList<Valute>) listValutesYest);
            super.onSaveInstanceState(outState);
        }
    }


    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
    }
}
