package ru.startandroid.currencies;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ServiceGenerator {
    private static final String BASE_URL = "http://www.cbr.ru";
    private static ApiService apiService;
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
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static ApiService getApiService() {
        return apiService;
    }
}
