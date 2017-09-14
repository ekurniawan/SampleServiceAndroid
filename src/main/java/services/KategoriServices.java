package services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import models.Kategori;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by erick on 14/09/2017.
 */

public final class KategoriServices {
    private static final String SERVICE_URL = "http://192.168.5.205/";
    private static final OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS).build();
    }

    public static List<Kategori> GetAllKategori() throws IOException {
        List<Kategori> listKategori = new ArrayList<>();
        Request request = new Request.Builder().url(SERVICE_URL+"api/Kategori").build();
        Response response = client.newCall(request).execute();
        String results = response.body().string();

        try{
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Kategori kategori = new Kategori();
                kategori.setKategoriId(jsonObject.getInt("KategoriId"));
                kategori.setNamaKategori(jsonObject.getString("NamaKategori"));
                listKategori.add(kategori);
            }
        }catch(JSONException jEx){
            Log.d("MyError",jEx.getLocalizedMessage());
        }

        return listKategori;
    }

    public static Kategori GetKategoriById(int id) throws IOException{
        Kategori kategori = new Kategori();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SERVICE_URL+"api/Kategori").newBuilder();
        urlBuilder.addPathSegment(String.valueOf(id));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();

        try{
            JSONObject jsonObject = new JSONObject(result);
            if(!jsonObject.isNull("KategoriId")){
                kategori.setKategoriId(jsonObject.getInt("KategoriId"));
                kategori.setNamaKategori(jsonObject.getString("NamaKategori"));
            }
        }catch (JSONException jEx){
            Log.d("MyError",jEx.getLocalizedMessage());
        }
        return kategori;
    }
}
