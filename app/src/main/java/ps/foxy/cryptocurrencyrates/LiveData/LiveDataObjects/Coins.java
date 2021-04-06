package ps.foxy.cryptocurrencyrates.LiveData.LiveDataObjects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Coins {
    @SerializedName("uuid")
    private String uuid;

    @SerializedName("symbol")
    private String symbol;

    @SerializedName("name")
    private String name;

    @SerializedName("iconUrl")
    private String iconUrl;

    @SerializedName("marketCap")
    private String marketCap;

    @SerializedName("price")
    private String price;

    @SerializedName("rank")
    private String rank;

    @SerializedName("change")
    private String change;

    @SerializedName("24hVolume")
    private String dayVolume;

    @SerializedName("sparkline")
    private List<String> sparkline;

    @SerializedName("color")
    private String color;


//    public Coins() {
//    }
//
//    public Coins(String uuid, String symbol, String name, String iconUrl, String marketCap, String price, String rank, String change, String dayVolume, List<String> sparkline, String color) {
//        this.uuid = uuid;
//        this.symbol = symbol;
//        this.name = name;
//        this.iconUrl = iconUrl;
//        this.marketCap = marketCap;
//        this.price = price;
//        this.rank = rank;
//        this.change = change;
//        this.dayVolume = dayVolume;
//        this.sparkline = sparkline;
//        this.color = color;
//    }
//
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getDayVolume() {
        return dayVolume;
    }

    public void setDayVolume(String dayVolume) {
        this.dayVolume = dayVolume;
    }

    public List<String> getSparkline() {
        return sparkline;
    }

    public void setSparkline(List<String> sparkline) {
        this.sparkline = sparkline;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
