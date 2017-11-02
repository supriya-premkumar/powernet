package edu.stanford.slac.powernetlab.Rest;

import edu.stanford.slac.powernetlab.Model.model;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndpointInterface {

    @GET("device/{id}")
    Call<model> getData(@Path("id") String id);


}
