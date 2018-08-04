package com.example.musa.twisent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.musa.twisent.ApiCall.ApiCallInterface;
import com.example.musa.twisent.ApiCall.RetrofitBuilder;
import com.example.musa.twisent.twisent.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    SentimentViewAdapter sentimentViewAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView=findViewById(R.id.sentimentList);
        FrameLayout frameLayout=findViewById(R.id.GraphView);
        frameLayout.setVisibility(View.GONE);
         sentimentViewAdapter=new SentimentViewAdapter(this);
         progressBar=findViewById(R.id.progressBar);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(sentimentViewAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
       final SearchView searchActionView = (SearchView) search.getActionView();
        searchActionView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
               String real= query.replaceAll("\\s+","");
                Retrofit retrofit=RetrofitBuilder.retrofitBuilder();
                ApiCallInterface apiCallInterface=retrofit.create(ApiCallInterface.class);
                Call<Example>sentiments =apiCallInterface.mSentiments(real);
                sentiments.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(@NonNull Call<Example> call, @NonNull Response<Example> response) {
                       progressBar.setVisibility(View.GONE);
                        sentimentViewAdapter.setMitems(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<Example> call, @NonNull Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Connect to Internet", Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
