package edu.stanford.slac.powernetlab.Model;


import com.google.gson.annotations.SerializedName;

public class PowerConsumption {
    public String getResult() {
        return result;
    }

    @SerializedName("result")
    public String result;

    @Override
    public String toString(){
        return result;
    }


}

