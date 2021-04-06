package ps.foxy.cryptocurrencyrates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ps.foxy.cryptocurrencyrates.CryptoAdapter.CryptoCoinsAdapter;
import ps.foxy.cryptocurrencyrates.CryptoObjects.ResultAPIModel;
import ps.foxy.cryptocurrencyrates.LiveData.LiveDataObjects.Coins;
import ps.foxy.cryptocurrencyrates.LiveData.LiveDataObjects.ListOfCoins;
import ps.foxy.cryptocurrencyrates.LiveData.Request;
import ps.foxy.cryptocurrencyrates.LiveData.Retrofit_instance;
import ps.foxy.cryptocurrencyrates.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<Coins> getListOfCoins;
    private Request request;
    private ProgressDialog progressDialog;
    private LocalSave save;
    private CryptoCoinsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      //  getListOfCoins = new ArrayList<>();
        progressDialog = new ProgressDialog(MainActivity.this);
        save = new LocalSave();
        progressDialog.show();
        lookUpData();
        setUpTextView();
    }

    private void setUpRecycleView(){
        adapter = new CryptoCoinsAdapter(MainActivity.this, getListOfCoins);
        binding.cryptoRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.cryptoRecycle.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setUpTextView(){
     binding.sortByRank.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(binding.sortByRankImg.getBackground() == getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24)){
                 progressDialog.show();
                 binding.sortByRankImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                 lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_TOP,EasyAccessNames.ORDERED_DIRECTION_ASC);
             }else {
                 progressDialog.show();
                 binding.sortByRankImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                 lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_TOP,EasyAccessNames.ORDERED_DIRECTION_DESE);
             }

         }
     });

     binding.sortByPrice.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             if(binding.sortByPriceImg.getBackground() == getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24)){
                 progressDialog.show();
                 binding.sortByPriceImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                 lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_PRICE,EasyAccessNames.ORDERED_DIRECTION_ASC);
             }else {
                 progressDialog.show();
                 binding.sortByPriceImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                 lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_PRICE,EasyAccessNames.ORDERED_DIRECTION_DESE);
             }
         }
     });

     binding.sortByChange.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(binding.sortByChangeImg.getBackground() == getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24)){
                 progressDialog.show();
                 binding.sortByChangeImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                 lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_CHANGE,EasyAccessNames.ORDERED_DIRECTION_ASC);
             }else {
                 progressDialog.show();
                 binding.sortByChangeImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                 lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_CHANGE,EasyAccessNames.ORDERED_DIRECTION_DESE);
             }
         }

     });

    }

    private void lookUpData() {
        request = Retrofit_instance.getRetrofitInstance().create(Request.class);
        Call<ResultAPIModel<ListOfCoins>> call = request.getCoins();
        call.enqueue(new Callback<ResultAPIModel<ListOfCoins>>() {
            @Override
            public void onResponse(Call<ResultAPIModel<ListOfCoins>> call, Response<ResultAPIModel<ListOfCoins>> response) {
                ResultAPIModel<ListOfCoins> result = response.body();
                Gson gson = new Gson();
                getListOfCoins= new ArrayList<>();
                getListOfCoins.addAll(result.data.getCoins());
                Log.e("onResponse1: ", gson.toJson(result.data));
                save.setDataString(EasyAccessNames.LISTOFCOINS, gson.toJson(getListOfCoins), MainActivity.this);
                setUpRecycleView();
                progressDialog.cancel();
            }

            @Override
            public void onFailure(Call<ResultAPIModel<ListOfCoins>> call, Throwable t) {
                t.printStackTrace();
                progressDialog.cancel();
                if (t instanceof UnknownHostException || t instanceof NoRouteToHostException) {
                    Log.e("Error", t.getMessage());
                }
            }
        });
    }


    private void lookUpDataOrderedBy(String orderBy,String orderDirection) {
        request = Retrofit_instance.getRetrofitInstance().create(Request.class);
        Call<ResultAPIModel<ListOfCoins>> call = request.getCoinsOrderdBy(orderBy,orderDirection,50);
        call.enqueue(new Callback<ResultAPIModel<ListOfCoins>>() {
            @Override
            public void onResponse(Call<ResultAPIModel<ListOfCoins>> call, Response<ResultAPIModel<ListOfCoins>> response) {
                ResultAPIModel<ListOfCoins> result = response.body();
                Gson gson = new Gson();
                getListOfCoins= new ArrayList<>();
                getListOfCoins.addAll(result.data.getCoins());
                Log.e("onResponse2: ", gson.toJson(result.data));
                setUpRecycleView();
                progressDialog.cancel();
            }

            @Override
            public void onFailure(Call<ResultAPIModel<ListOfCoins>> call, Throwable t) {
                t.printStackTrace();
                progressDialog.cancel();
                if (t instanceof UnknownHostException || t instanceof NoRouteToHostException) {
                    Log.e("Error", t.getMessage());
                }
            }
        });
    }
}