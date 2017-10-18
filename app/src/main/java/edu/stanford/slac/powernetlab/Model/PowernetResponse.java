package edu.stanford.slac.powernetlab.Model;


import java.util.ArrayList;
import java.util.List;

public class PowernetResponse {
    List<Powernet> powernet;

    //public constructor is necessary for collections
    public PowernetResponse(){
        powernet = new ArrayList<Powernet>();
    }

}
