package com.ugb.calculadora;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Detectar_Internet {
    private Context _context;

    public Detectar_Internet(Context _context) {
        this._context = _context;
    }

    public boolean hayConexionInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if( connectivityManager!=null ){
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if(networkInfos!=null){
                for(int i=0; i<networkInfos.length; i++){
                    if(networkInfos[i].getState()==NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
