package ru.startandroid.currencies.UI.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.startandroid.currencies.model.Valute;

public interface MainView
        extends MvpView{

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setListValutesToday(List<Valute> valutes, String dateYest);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setListValutesYest(List<Valute> valutes);
}
