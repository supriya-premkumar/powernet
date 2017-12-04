package edu.stanford.slac.powernetlab.Model;

import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.stanford.slac.powernetlab.R;
import edu.stanford.slac.powernetlab.Rest.ApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PowernetResponse {
    public static final String BASE_URL = "http://pwrnet-158117.appspot.com/api/v1/";



public void getApiData(){
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    ApiEndpointInterface endpointInterface = retrofit.create(ApiEndpointInterface.class);
    Call<model> call = endpointInterface.getData("10/");
    call.enqueue(new Callback<model>() {

        @Override
        public void onResponse(Call<model> call, Response<model> response) {
            model model = response.body();
            Log.d("Entered Fragment", model.toString());

            String status = model.getStatus();
            String type = model.getType();
            Log.d("Entered Fragment", status);



        }

        @Override
        public void onFailure(Call<model> call, Throwable t) {

        }
    });




}


}
