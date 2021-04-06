package ps.foxy.cryptocurrencyrates.LiveData;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import ps.foxy.cryptocurrencyrates.CryptoObjects.ResultAPIModel;
import ps.foxy.cryptocurrencyrates.EasyAccessNames;
import ps.foxy.cryptocurrencyrates.LiveData.LiveDataObjects.Coins;
import ps.foxy.cryptocurrencyrates.LiveData.LiveDataObjects.ListOfCoins;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Request {
// in this interface we will use GET and POST request to retrieve information and live data
   // this will get all the coins
    @GET(EasyAccessNames.GET_COINS)
    Call<ResultAPIModel<ListOfCoins>> getCoins();

    // this will get all coins ordered By an attribute.
    @GET(EasyAccessNames.GET_COINS)
    //@FormUrlEncoded
    Call<ResultAPIModel<ListOfCoins>> getCoinsOrderdBy(@Query("orderBy") String a,@Query("orderDirection")String b,@Query("limit")int c);


}
