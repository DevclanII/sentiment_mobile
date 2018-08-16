
package com.example.musa.twisent.ApiResponse;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example implements Serializable
{

    @SerializedName("percentages")
    @Expose
    private Percentages percentages;
    @SerializedName("time")
    @Expose
    private String time;
    private final static long serialVersionUID = -8574966817479614482L;

    public Percentages getPercentages() {
        return percentages;
    }

    public void setPercentages(Percentages percentages) {
        this.percentages = percentages;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
