package ru.startandroid.currencies.network;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ServiceGenerator {
    private final String BASE_URL = "http://www.cbr.ru";
    private ApiService apiService;
    private static ServiceGenerator instance = null;

    public static ServiceGenerator getInstance(){
        if (instance == null) instance = new ServiceGenerator();
        return instance;
    }

    private ServiceGenerator() {
        buildRetrofit(BASE_URL);
    }

    private void buildRetrofit(String base_url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
