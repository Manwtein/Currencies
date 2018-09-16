package ru.startandroid.currencies.UI.main;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void setListValutesToday(List<Valute> valutes) {
        recyclerView.setVisibility(View.VISIBLE);
        linearLoading.setVisibility(View.GONE);
        recyclerAdapter.setListValutesToday(valutes);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setListValutesYest(List<Valute> valutes) {
        recyclerAdapter.setListValutesYest(valutes);
        recyclerAdapter.notifyDataSetChanged();
    }
}
