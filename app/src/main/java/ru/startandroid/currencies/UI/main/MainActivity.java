package ru.startandroid.currencies.UI.main;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.startandroid.currencies.R;
import ru.startandroid.currencies.adapter.RecyclerAdapter;
import ru.startandroid.currencies.model.Valute;

public class MainActivity
        extends MvpAppCompatActivity
        implements MainView {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayout linearLoading;
    private Button btnRetry;

    @InjectPresenter
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        linearLoading = findViewById(R.id.linearLoading);
        recyclerView = findViewById(R.id.recycler_view);
        btnRetry = findViewById(R.id.btnRetry);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void setListValutesToday(List<Valute> valutes, String dateYest) {
        recyclerView.setVisibility(View.VISIBLE);
        linearLoading.setVisibility(View.GONE);
        recyclerAdapter.setListValutesToday(valutes, dateYest);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setListValutesYest(List<Valute> valutes) {
        recyclerAdapter.setListValutesYest(valutes);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBtnForRetry() {
        linearLoading.setVisibility(View.GONE);
        btnRetry.setVisibility(View.VISIBLE);
        Toast.makeText(this,
                "An error has occurred.\n"
                        + "Try once more",
                Toast.LENGTH_LONG)
                .show();
    }

    public void onClickForRetry(View view) {
        mainPresenter.attachView(this);
        btnRetry.setVisibility(View.GONE);
        linearLoading.setVisibility(View.VISIBLE);
    }
}
