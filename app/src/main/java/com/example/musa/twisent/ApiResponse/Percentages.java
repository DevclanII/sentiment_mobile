
package com.example.musa.twisent.ApiResponse;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Percentages implements Serializable
{

    @SerializedName("negative")
    @Expose
    private Double negative;
    @SerializedName("neutral")
    @Expose
    private Double neutral;
    @SerializedName("positive")
    @Expose
    private Double positive;
    private final static long serialVersionUID = -118113267152704070L;

    public Double getNegative() {
        return negative;
    }

    public void setNegative(Double negative) {
        this.negative = negative;
    }

    public Double getNeutral() {
        return neutral;
    }

    public void setNeutral(Double neutral) {
        this.neutral = neutral;
    }

    public Double getPositive() {
        return positive;
    }

    public void setPositive(Double positive) {
        this.positive = positive;
    }

}
