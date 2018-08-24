package com.example.musa.twisent;

import android.app.SearchManager;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ProgressBar;

import android.widget.Toast;

import com.example.musa.twisent.ApiCall.ApiCallInterface;
import com.example.musa.twisent.ApiCall.RetrofitBuilder;
import com.example.musa.twisent.ApiResponse.Example;
import com.example.musa.twisent.ApiResponse.Percentages;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    ProgressBar progressBar;
    static PieChart chart;
    public int numberOfTweets;
    public static String Sentiments[]={"Negative","Positive","Neutral"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         progressBar=findViewById(R.id.progressBar);
         chart=findViewById(R.id.pieChart);
         setupSharedPreferences();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        MenuItem search = menu.findItem(R.id.action_search);
       final SearchView searchActionView = (SearchView) search.getActionView();
        assert searchManager != null;
        searchActionView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchActionView.setIconifiedByDefault(true);
        searchActionView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.bringToFront();
               final String Query = query.replaceAll("\\s","");
                Retrofit retrofit=RetrofitBuilder.retrofitBuilder();
                ApiCallInterface apiCallInterface=retrofit.create(ApiCallInterface.class);
                Call<Example>sentiments =apiCallInterface.mSentiments(Query,String.valueOf(numberOfTweets));
                sentiments.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(@NonNull Call<Example> call, @NonNull Response<Example> response) {
                        if (response.isSuccessful()) {
                            Percentages percentages = response.body().getPercentages();
                            setupPieChart(Query, percentages);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Example> call, @NonNull Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "Connect to Internet", Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                chart.animateY(1000);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem =item.getItemId();
        switch(selectedItem){
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public static void setupPieChart(String Name,Percentages percentages){
        List<PieEntry> pieEntries=new ArrayList<>();
        pieEntries.add(new PieEntry(percentages.getNegative().floatValue(),Sentiments[0]));
        pieEntries.add(new PieEntry(percentages.getPositive().floatValue(),Sentiments[1]));
        pieEntries.add(new PieEntry(percentages.getNeutral().floatValue(),Sentiments[2]));

        PieDataSet dataSet=new PieDataSet(pieEntries,"Sentimental Analysis for "+Name);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data =new PieData(dataSet);

        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getResources().getString(R.string.pref_sentiment))){
            numberOfTweets=Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_sentiment),String.valueOf(100)));
        }


    }
    private void setupSharedPreferences(){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        numberOfTweets=Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_sentiment),String.valueOf(100)));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

}
