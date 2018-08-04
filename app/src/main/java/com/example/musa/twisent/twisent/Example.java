
package com.example.musa.twisent.twisent;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example implements Serializable, Parcelable
{

    @SerializedName("Sentiment")
    @Expose
    private Sentiment sentiment;
    @SerializedName("Tweettime")
    @Expose
    private Tweettime tweettime;
    public final static Creator<Example> CREATOR = new Creator<Example>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Example createFromParcel(Parcel in) {
            return new Example(in);
        }

        public Example[] newArray(int size) {
            return (new Example[size]);
        }

    }
    ;
    private final static long serialVersionUID = -1447430141274938859L;

    protected Example(Parcel in) {
        this.sentiment = ((Sentiment) in.readValue((Sentiment.class.getClassLoader())));
        this.tweettime = ((Tweettime) in.readValue((Tweettime.class.getClassLoader())));
    }

    public Example() {
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public Tweettime getTweettime() {
        return tweettime;
    }

    public void setTweettime(Tweettime tweettime) {
        this.tweettime = tweettime;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sentiment);
        dest.writeValue(tweettime);
    }

    public int describeContents() {
        return  0;
    }

}
