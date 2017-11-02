package edu.stanford.slac.powernetlab.Model;

import com.google.gson.annotations.SerializedName;

public class model {

    @SerializedName("device_name")
    public String device_name;

    @SerializedName("device_status")
    public String device_status;

    @SerializedName("device_load")
    public String device_load;

    @SerializedName("device_id")
    public int device_id;


    @Override
    public String toString(){
        return device_id + "- Device Name: "+ device_name + "- Device Status: "+ device_status + "- Device Load: " + device_load;
    }




}
