package ps.foxy.cryptocurrencyrates.CryptoAdapter;

import android.app.Activity;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.request.RequestOptions;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

import ps.foxy.cryptocurrencyrates.CryptoObjects.Utilities;
import ps.foxy.cryptocurrencyrates.LiveData.LiveDataObjects.Coins;
import ps.foxy.cryptocurrencyrates.R;

public class CryptoCoinsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<Coins> coinsList;
    private RequestBuilder<PictureDrawable> requestBuilder;
    static final long MILLION = 1000000L;
    static final long BILLION = 1000000000L;
    static final long TRILLION = 1000000000000L;

    public CryptoCoinsAdapter(Activity activity, List<Coins> coinsList) {
        this.activity = activity;
        this.coinsList = coinsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity)
                .inflate(R.layout.currency_recycle, parent, false);
        return new CryptoCoinsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CryptoCoinsAdapter.MyViewHolder myViewHolder = (CryptoCoinsAdapter.MyViewHolder) holder;

        myViewHolder.cryptoNumber.setText(coinsList.get(position).getRank());
        myViewHolder.cryptoName.setText(coinsList.get(position).getName());
        myViewHolder.cryptoShortName.setText(coinsList.get(position).getSymbol());
        Uri myUri = Uri.parse(coinsList.get(position).getIconUrl());
        GlideToVectorYou.justLoadImage(activity,myUri, myViewHolder.cryptoImage);

        if (coinsList.get(position).getPrice() != null) {
            myViewHolder.currentPrice.setText("$" + " " + setUpDecimalFormat(coinsList.get(position).getPrice()));
        }

        if (coinsList.get(position).getMarketCap() != null) {
            myViewHolder.TotalPrice.setText(truncateNumber(Double.parseDouble(coinsList.get(position).getMarketCap())));
        }

        if (coinsList.get(position).getChange() != null) {
            myViewHolder.ratePerDay.setText(setUpDecimalFormat(coinsList.get(position).getChange()) + " " + "%");
        }


    }

    public static String setUpDecimalFormat(String decimal) {
        double result = Double.parseDouble(decimal);
        //result= result/60000;
        return new DecimalFormat("##.##").format(result);

    }

    public static String truncateNumber(double x) {
        if (x < MILLION) {
            return setUpDecimalFormat(String.valueOf(x));
        } else if (x < BILLION) {
            return setUpDecimalFormat(String.valueOf((x / MILLION))) + "M";
        } else if (x < TRILLION) {
            return setUpDecimalFormat(String.valueOf((x / BILLION))) + "B";

        } else {
            return setUpDecimalFormat(String.valueOf((x / TRILLION))) + "T";

        }

    }

    @Override
    public int getItemCount() {
        return coinsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView cryptoNumber, cryptoName, cryptoShortName, currentPrice, TotalPrice, ratePerDay;
        private ImageView cryptoImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cryptoNumber = itemView.findViewById(R.id.cryptoNumber);
            cryptoName = itemView.findViewById(R.id.cryptoName);
            cryptoShortName = itemView.findViewById(R.id.cryptoShortName);
            currentPrice = itemView.findViewById(R.id.currentPrice);
            TotalPrice = itemView.findViewById(R.id.TotalPrice);
            ratePerDay = itemView.findViewById(R.id.ratePerDay);
            cryptoImage = itemView.findViewById(R.id.cryptoImage);


        }
    }

}
