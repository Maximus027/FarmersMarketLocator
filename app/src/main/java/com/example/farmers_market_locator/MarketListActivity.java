package com.example.farmers_market_locator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import com.example.farmers_market_locator.RecyclerView.MarketAdapter;
import com.example.farmers_market_locator.Retrofit.APIService;
import com.example.farmers_market_locator.Retrofit.MainJSONObjects;
import com.example.farmers_market_locator.Retrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxrosado on 2/1/17.
 */

public class MarketListActivity extends AppCompatActivity {
    private APIService apiService;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        recyclerView = (RecyclerView) findViewById(R.id.market_rv);

        Intent intent = getIntent();
        String borough = intent.getStringExtra(getResources().getString(R.string.chosen_borough_key));
        Log.d("Borough", "onCreate:" + borough);

        apiService = RetrofitBuilder.makeService();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<MainJSONObjects[]> mainPOJOCall = apiService.pojoGetter(borough);
        mainPOJOCall.enqueue(new Callback<MainJSONObjects[]>() {
            @Override
            public void onResponse(Call<MainJSONObjects[]> call, Response<MainJSONObjects[]> response) {

                MarketAdapter adapter = new MarketAdapter(response.body());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<MainJSONObjects[]> call, Throwable t) {
                    //// TODO: 2/3/17 toast call failure to user
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_icon, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
