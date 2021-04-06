package ps.foxy.cryptocurrencyrates;

import android.content.Context;
import android.content.SharedPreferences;

// Here We can Store Data Locally using Shared Preferences as we turn the data to String by using Gson then Store it.
// When recover we use Gson to convert that String back to it's form.
public class LocalSave {
    public void setDataString(String key, String value, Context context){
        SharedPreferences preferences = context.getSharedPreferences("Local",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public String getDataString(String key, Context context){
        SharedPreferences preferences = context.getSharedPreferences("Local",0);
        return preferences.getString(key, "0");
    }
}
