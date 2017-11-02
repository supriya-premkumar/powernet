package edu.stanford.slac.powernetlab.Rest;

import edu.stanford.slac.powernetlab.Model.PowerConsumption;
import edu.stanford.slac.powernetlab.Model.model;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpointInterface {

    @GET("device/{id}")
    Call<model> getData(@Path("id") String id);

    @GET("rms/consumption/")
    Call<PowerConsumption> getPowerConsumption(@Query("id") String id);

    @POST("device/{id}")
    Call<model> changeStatus(@Path("id") String id, @Part("status") String status);


}
