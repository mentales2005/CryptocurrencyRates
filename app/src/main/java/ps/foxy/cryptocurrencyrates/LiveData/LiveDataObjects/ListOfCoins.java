package ps.foxy.cryptocurrencyrates.LiveData.LiveDataObjects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListOfCoins {

    @SerializedName("coins")
    private List<Coins> coins;

    public List<Coins> getCoins() {
        return coins;
    }

    public void setCoins(List<Coins> coins) {
        this.coins = coins;
    }


}
