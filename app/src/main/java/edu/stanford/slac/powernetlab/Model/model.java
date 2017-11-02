package edu.stanford.slac.powernetlab.Model;

import com.google.gson.annotations.SerializedName;

public class model {
    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("type")
    public String type;

    @SerializedName("status")
    public String status;




    @Override
    public String toString(){
        return id + "- Device Name: "+ name + "- Device Type: "+ type + "- Device Status: " + status;
    }




}
