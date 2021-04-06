package ps.foxy.cryptocurrencyrates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

import static ps.foxy.cryptocurrencyrates.PaginationListener.PAGE_START;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ActivityMainBinding binding;
    private List<Coins> getListOfCoins;
    private Request request;
    private ProgressDialog progressDialog;
    private LocalSave save;
    private CryptoCoinsAdapter adapter;
    private Gson gson;
    private boolean crypto = false, price = false, change = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         binding.swipeRefresh.setOnRefreshListener(MainActivity.this);

        //getListOfCoins = new ArrayList<>();
        progressDialog = new ProgressDialog(MainActivity.this);
        save = new LocalSave();
        gson = new Gson();
        progressDialog.show();
        if(save.getDataString(EasyAccessNames.LISTOFCOINS,MainActivity.this).equals("0")){
            lookUpData();
        }else{
            getListOfCoins=new ArrayList<>();
            getListOfCoins.addAll(gson.fromJson(save.getDataString(EasyAccessNames.LISTOFCOINS,MainActivity.this),new TypeToken<List<Coins>>() {}.getType()));
            setUpRecycleView();
            progressDialog.cancel();
        }
        setUpTextView();
    }

    private void setUpRecycleView() {
        adapter = new CryptoCoinsAdapter(MainActivity.this, getListOfCoins);
        binding.cryptoRecycle.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.cryptoRecycle.setAdapter(adapter);
        binding.cryptoRecycle.addOnScrollListener(new PaginationListener(new LinearLayoutManager(MainActivity.this)) {
            @Override
            protected void loadMoreItems() {
                lookUpData();
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void setUpTextView() {

        binding.sortByChangeImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
        binding.sortByRankImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
        binding.sortByPriceImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);

        binding.sortByRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (crypto == false) {
                    crypto = true;
                    progressDialog.show();
                    binding.sortByRankImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_MARKETCAP, EasyAccessNames.ORDERED_DIRECTION_ASC);
                } else {
                    crypto = false;
                    progressDialog.show();
                    binding.sortByRankImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_MARKETCAP, EasyAccessNames.ORDERED_DIRECTION_DESE);
                }

            }
        });

        binding.sortByPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (price == false) {
                    price = true;
                    progressDialog.show();
                    binding.sortByPriceImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_PRICE, EasyAccessNames.ORDERED_DIRECTION_ASC);
                } else {
                    price = false;
                    progressDialog.show();
                    binding.sortByPriceImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_PRICE, EasyAccessNames.ORDERED_DIRECTION_DESE);
                }
            }
        });

        binding.sortByChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (change == false) {
                    change = true;
                    progressDialog.show();
                    binding.sortByChangeImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_CHANGE, EasyAccessNames.ORDERED_DIRECTION_ASC);
                } else {
                    change = false;
                    progressDialog.show();
                    binding.sortByChangeImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    lookUpDataOrderedBy(EasyAccessNames.ORDERED_BY_CHANGE, EasyAccessNames.ORDERED_DIRECTION_DESE);
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
                getListOfCoins = new ArrayList<>();
                getListOfCoins.addAll(result.data.getCoins());
                Log.e("onResponse1: ", gson.toJson(result.data));
                save.setDataString(EasyAccessNames.LISTOFCOINS, gson.toJson(getListOfCoins), MainActivity.this);
                setUpRecycleView();
                binding.swipeRefresh.setRefreshing(false);
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


    private void lookUpDataOrderedBy(String orderBy, String orderDirection) {
        request = Retrofit_instance.getRetrofitInstance().create(Request.class);
        Call<ResultAPIModel<ListOfCoins>> call = request.getCoinsOrderdBy(orderBy, orderDirection, 50);
        call.enqueue(new Callback<ResultAPIModel<ListOfCoins>>() {
            @Override
            public void onResponse(Call<ResultAPIModel<ListOfCoins>> call, Response<ResultAPIModel<ListOfCoins>> response) {
                ResultAPIModel<ListOfCoins> result = response.body();
                getListOfCoins = new ArrayList<>();
                getListOfCoins.addAll(result.data.getCoins());
                Log.e("onResponse2: ", gson.toJson(result.data));
                setUpRecycleView();
                binding.swipeRefresh.setRefreshing(false);
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

    @Override
    public void onRefresh() {
        lookUpData();
        binding.sortByChangeImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
        binding.sortByRankImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
        binding.sortByPriceImg.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);

    }
}