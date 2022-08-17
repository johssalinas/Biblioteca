package red.lisgar.biblioteca.login;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreference {
    Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharePreference(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("base_sp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public  void setSharedPreferences(String datoGuardar){
        editor.putString("dato", datoGuardar);
        editor.apply();
    }

    public String getSharePreference(){
        return sharedPreferences.getString("dato", "No se encuentra registrado");
    }
}
