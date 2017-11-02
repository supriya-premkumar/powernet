package edu.stanford.slac.powernetlab.Fragments;


import android.app.Fragment;
import android.graphics.PorterDuff;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import edu.stanford.slac.powernetlab.Model.PowerConsumption;
import edu.stanford.slac.powernetlab.Model.model;
import edu.stanford.slac.powernetlab.R;
import edu.stanford.slac.powernetlab.Rest.ApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RefrigeratorFragment extends android.support.v4.app.Fragment {
    public static final String BASE_URL = "http://pwrnet-158117.appspot.com/api/v1/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_refrigerator, container, false);
//        Log.d("Device Status",device_status.toString());
//        device_status.setText(status);


        requestData(view);


        // Inflate the layout for this fragment
        return view;
    }

    public void requestData(final View view) {
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
//                Log.d("Entered Fragment", model.toString());

                String status = model.getStatus();
                String type = model.getType().toLowerCase();
                type = Character.toUpperCase(type.charAt(0)) + type.substring(1);
                Log.d("Entered Fragment", status);

                Spinner spinner = (Spinner) view.findViewById(R.id.device_status);
                spinner.getBackground().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.appliance_status, android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                String value = spinner.getSelectedItem().toString();
                Log.d("Spinner Vlue", value);


//                TextView device_status = view.findViewById(R.id.device_status);
                TextView device_name = view.findViewById(R.id.device_name);
//                System.out.println(device_status);
                device_name.setText(type);
//                device_status.setText(status);


            }

            @Override
            public void onFailure(Call<model> call, Throwable t) {

            }
        });

        Call<PowerConsumption> powerConsumptionCall = endpointInterface.getPowerConsumption("5");
        powerConsumptionCall.enqueue(new Callback<PowerConsumption>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<PowerConsumption> call, Response<PowerConsumption> response) {
                int code = response.code();
                Log.d("PC Response code", String.valueOf(code));

                PowerConsumption pc = response.body();
                Log.d(pc.toString(), "PC");
                String powerConsumption = pc.getResult();
                float pcNum = Float.parseFloat(powerConsumption);
                DecimalFormat df = new DecimalFormat("#.####");
                String pcNum2 = df.format(pcNum);

                System.out.println(pcNum2);
                Log.d(powerConsumption, "powerConsumptionRate");
                TextView consumption = (TextView)view.findViewById(R.id.power_consumption);
                consumption.setText(pcNum2);
            }

            @Override
            public void onFailure(Call<PowerConsumption> call, Throwable t) {

            }
        });


    }
}
