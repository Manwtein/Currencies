package ru.startandroid.currencies.UI.main;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.startandroid.currencies.model.Valute;

public interface MainView
        extends MvpView{

    void setListValutesToday(List<Valute> valutes);

    void setListValutesYest(List<Valute> valutes);
}
