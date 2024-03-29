package edu.stanford.slac.powernetlab.Fragments;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import edu.stanford.slac.powernetlab.Model.PowerConsumption;
import edu.stanford.slac.powernetlab.Model.model;
import edu.stanford.slac.powernetlab.R;
import edu.stanford.slac.powernetlab.Rest.ApiEndpointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DishWasherFragment extends android.support.v4.app.Fragment {
    public static final String BASE_URL = "http://pwrnet-158117.appspot.com/api/v1/";
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    String status = "";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish_washer, container, false);
        requestData(view);
        performBackgroundTask(view);

        // Inflate the layout for this fragment
        return view;
    }

    public void requestData(final View view) {
        final ImageView dish_washer = (ImageView)view.findViewById(R.id.dish_washer);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final ApiEndpointInterface endpointInterface = retrofit.create(ApiEndpointInterface.class);


        Call<model> call = endpointInterface.getData("14/");
        call.enqueue(new Callback<model>() {

            @Override
            public void onResponse(Call<model> call, Response<model> response) {
                final model model = response.body();
                Log.d("model data", model.toString());
                status = model.getStatus();
                dish_washer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click Registered", "Clickity");
                        if(status.equals("ON")){
                            status="OFF";
                            Log.d("Click Registered", "Clickity is ON");
                            v.setBackgroundResource(R.drawable.image_border_off);
                            Call<model> postStatus = endpointInterface.postStatus("14", status);
                            postStatus.enqueue(new Callback<edu.stanford.slac.powernetlab.Model.model>() {
                                @Override
                                public void onResponse(Call<model> call, Response<model> response) {
                                    Log.d("OnResponse", model.getStatus());
                                }

                                @Override
                                public void onFailure(Call<model> call, Throwable t) {
                                    Toast.makeText(getContext(), "Check your Internet connection", Toast.LENGTH_SHORT).show();
                                    Log.d("OnFailure", model.getStatus());

                                }
                            });




                        }
                        else if (status.equals("OFF")){
                            status="ON";
                            Log.d("Click Registered", "Clickity is ON");
                            v.setBackgroundResource(R.drawable.image_border_on);
                            Call<model> postStatus = endpointInterface.postStatus("14", status);
                            postStatus.enqueue(new Callback<edu.stanford.slac.powernetlab.Model.model>() {
                                @Override
                                public void onResponse(Call<model> call, Response<model> response) {
                                    Log.d("OnResponse", model.getStatus());
                                }

                                @Override
                                public void onFailure(Call<model> call, Throwable t) {
                                    Toast.makeText(getContext(), "Check your Internet connection", Toast.LENGTH_SHORT).show();
                                    Log.d("OnFailure", model.getStatus());

                                }
                            });



                        }
                    }
                });

                if(status.equals("ON")){
                    dish_washer.setBackgroundResource(R.drawable.image_border_on);
                }
                else if (status.equals("OFF")){
                    dish_washer.setBackgroundResource(R.drawable.image_border_off);
                }




//                String type = model.getType().toLowerCase();
//                type = Character.toUpperCase(type.charAt(0)) + type.substring(1);
//                Log.d("Appliance Status", status);

//                final Spinner spinner = (Spinner) view.findViewById(R.id.device_status);
//                spinner.getBackground().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
//                final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.appliance_status, android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(adapter);
//
//                String value = spinner.getSelectedItem().toString();
//                int selectionPosition = adapter.getPosition(status);
//                spinner.setSelection(selectionPosition);
//                Log.d("Spinner Value", value);
//
//                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        Log.d("Spinner Position", String.valueOf(position));
//
//                        if (position == 0) {
//                            Log.d("Clicked Item", spinner.getSelectedItem().toString());
//                            Call<model> postStatus = endpointInterface.postStatus("14", spinner.getSelectedItem().toString());
//                            Log.d("Success", postStatus.toString());
//
//                            postStatus.enqueue(new Callback<model>() {
//                                @Override
//                                public void onResponse(Call<model> call, Response<model> response) {
//                                    Log.d("Success", "1");
//
//                                    if (response.isSuccessful()) {
//                                        Log.d("Success", "OFF");
//                                        Toast.makeText(getContext(), "Dish Washer is turned " + response.body().getStatus(), Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<model> call, Throwable t) {
//                                    Toast.makeText(getContext(), "Check your Internet connection", Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//
//                        }
//
//                        if (position == 1) {
//                            Log.d("Clicked Item", spinner.getSelectedItem().toString());
//                            Call<model> postStatus = endpointInterface.postStatus("14", spinner.getSelectedItem().toString());
//                            postStatus.enqueue(new Callback<model>() {
//                                @Override
//                                public void onResponse(Call<model> call, Response<model> response) {
//                                    if (response.isSuccessful()) {
//                                        Log.d("Success", "OFF");
//                                        Toast.makeText(getContext(), "Dish Washer is turned " + response.body().getStatus(), Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<model> call, Throwable t) {
//                                    Toast.makeText(getContext(), "Check your Internet connection", Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//
//
//                        }


                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//                        int selectionPosition = adapter.getPosition(status);
//                        spinner.setSelection(selectionPosition);
//
//                    }
//                });


//                TextView device_name = view.findViewById(R.id.device_name);
//                device_name.setText(type);
//            }

            @Override
            public void onFailure(Call<model> call, Throwable t) {

            }
        });

        Call<PowerConsumption> powerConsumptionCall = endpointInterface.getPowerConsumption("14");
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
                DecimalFormat df = new DecimalFormat("#.#");
                String pcNum2 = df.format(pcNum);

                System.out.println(pcNum2);
                Log.d(powerConsumption, "powerConsumptionRate");
                TextView consumption = (TextView) view.findViewById(R.id.power_consumption);
                consumption.setText(pcNum2);
            }

            @Override
            public void onFailure(Call<PowerConsumption> call, Throwable t) {

            }
        });

    }


    public void performBackgroundTask(final View view) {

        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                final ApiEndpointInterface endpointInterface = retrofit.create(ApiEndpointInterface.class);

                Call<model> call = endpointInterface.getData("14/");
                call.enqueue(new Callback<model>() {

                    @Override
                    public void onResponse(Call<model> call, Response<model> response) {
                        final model model = response.body();
//                        Log.d("Response Status", String.valueOf(response.isSuccessful()));
//                        Log.d("model data", model.toString());
//
                        final String status = model.getStatus();
                        ImageView dish_washer = (ImageView)view.findViewById(R.id.dish_washer);



                        if(status.equals("ON")){
                            dish_washer.setBackgroundResource(R.drawable.image_border_on);
                        }
                        else if (status.equals("OFF")){
                            dish_washer.setBackgroundResource(R.drawable.image_border_off);
                        }
//                        final Spinner spinner = (Spinner) view.findViewById(R.id.device_status);
//                        spinner.getBackground().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
//                        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.appliance_status, android.R.layout.simple_spinner_dropdown_item);
//                        spinner.setAdapter(adapter);
//
//                        String value = spinner.getSelectedItem().toString();
//                        int selectionPosition = adapter.getPosition(status);
//                        spinner.setSelection(selectionPosition);
//                        Log.d("Spinner Value", value);

                    }

                    @Override
                    public void onFailure(Call<model> call, Throwable t) {
                        Toast.makeText(getContext(), "Check your Internet connection", Toast.LENGTH_SHORT).show();


                    }
                });




                Call<PowerConsumption> powerConsumptionCall = endpointInterface.getPowerConsumption("14");
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
                        DecimalFormat df = new DecimalFormat("#.#");
                        String pcNum2 = df.format(pcNum);

                        System.out.println(pcNum2);
                        Log.d(powerConsumption, "powerConsumptionRate");
                        TextView consumption = (TextView) view.findViewById(R.id.power_consumption);
                        consumption.setText(pcNum2);
                    }

                    @Override
                    public void onFailure(Call<PowerConsumption> call, Throwable t) {
                        Toast.makeText(getContext(), "Check your Internet connection", Toast.LENGTH_SHORT).show();


                    }

                });
            }
        }, 0, 10, TimeUnit.SECONDS);


    }

    @Override
    public void onPause() {
        super.onPause();
        scheduler.shutdown();

    }
}
