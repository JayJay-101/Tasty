package com.hfad.tasty;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("raw/35S5jnKX")
    abstract public Call<Food> getFoods();
}
//jiEvzswz old link tthfANrq