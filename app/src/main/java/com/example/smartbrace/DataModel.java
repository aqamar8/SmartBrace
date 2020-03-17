package com.example.smartbrace;


public class DataModel {

    public Long valueTime;
    public float valueData;

    public DataModel(Long valueTime, float valueData) {
        this.valueTime = valueTime;
        this.valueData = valueData;
    }

    public Long getValueTime() {
        return valueTime;
    }

    public void setValueTime(Long valueTime) {
        this.valueTime = valueTime;
    }

    public float getValueData() {
        return valueData;
    }

    public void setValueData(float valueData) {
        this.valueData = valueData;
    }
}
