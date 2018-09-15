package ru.startandroid.currencies.UI.main;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @InjectPresenter
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        RecyclerAdapter.OnValuteClickListener onValuteClickListener;
        onValuteClickListener = new RecyclerAdapter
                .OnValuteClickListener() {
            @Override
            public void onValuteClick(Valute valute) {
                Toast.makeText(MainActivity.this,
                        "Valute = " + valute.getCharCode(),
                        Toast.LENGTH_LONG).show();
            }
        };

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(onValuteClickListener);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void setListValutesToday(List<Valute> valutes) {
        recyclerAdapter.setListValutesToday(valutes);
    }

    @Override
    public void setListValutesYest(List<Valute> valutes) {
        recyclerAdapter.setListValutesYest(valutes);
    }
}
