package com.company;

import java.util.HashMap;

/**
 * Created by abendlied on 5/1/17.
 */
public class OmiIntent {

    private String name;
    private HashMap<String, String> slots;


    public OmiIntent(String name){
        this.name = name;
        this.slots = new HashMap<String, String>();
    }

    public OmiIntent(String name, HashMap<String, String> slots){
        this.name = name;
        this.slots = slots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getSlots() {
        return slots;
    }

    public void setSlots(HashMap<String, String> slots) {
        this.slots = slots;
    }

    public String getSlot(String key){
        return slots.get(key);
    }
}
