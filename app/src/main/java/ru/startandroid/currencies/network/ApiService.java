package ru.startandroid.currencies.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.startandroid.currencies.model.Response;

public interface ApiService {

    @GET("/scripts/XML_daily.asp")
    Call<Response> getValutes(@Query("date_req") String dateReq);

}
