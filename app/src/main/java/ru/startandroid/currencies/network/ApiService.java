package ru.startandroid.currencies.network;

import java.util.Observable;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.startandroid.currencies.model.Response;

public interface ApiService {

    @GET("/scripts/XML_daily.asp")
    Single<Response> getValutes(@Query("date_req") String dateReq);

}
