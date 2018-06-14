package com.example.yeshu.moviemagazine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity {

    ProgressDialog progressDialog;
    ArrayList<JsonDataClass> jsonDataClassArrayList;
    private static final String API_key=BuildConfig.API_KEY;
    String movieURL="http://api.themoviedb.org/3/movie/popular?api_key="+API_key;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfoWiFi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo networkInfoData=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((networkInfoWiFi!=null & networkInfoData!=null)&&(networkInfoWiFi.isConnected() | networkInfoData.isConnected()))
        {
            new MovieDetails().execute();
        }else {
            new AlertDialog.Builder(Home.this).setTitle(R.string.app_name).setMessage(R.string.no_internet)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class MovieDetails extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            jsonDataClassArrayList =new ArrayList<>();
            URL url=HttpResponse.bulid(movieURL);
            String response=null;
            try {
                response=HttpResponse.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONArray jsonArray=jsonObject.getJSONArray("results");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject movieinfo=jsonArray.getJSONObject(i);
                    Log.i("Movie info",movieinfo.toString());
                    JsonDataClass jsonDataClass;
                    int vote_count=movieinfo.getInt("vote_count");
                    String id=movieinfo.getString("id");
                    String title=movieinfo.getString("title");
                    String poster_path=movieinfo.getString("poster_path");
                    String original_language=movieinfo.getString("original_language");
                    String original_title=movieinfo.getString("original_title");
                    String backdrop_path=movieinfo.getString("backdrop_path");
                    String overview=movieinfo.getString("overview");
                    String release_date=movieinfo.getString("release_date");
                    double vote_average=movieinfo.getDouble("vote_average");
                    double popularity=movieinfo.getDouble("popularity");
                    boolean video=movieinfo.getBoolean("video");
                    boolean adult=movieinfo.getBoolean("adult");

                    jsonDataClass=new JsonDataClass(vote_count,id,title,poster_path,original_language,original_title,backdrop_path,overview,release_date,vote_average,popularity,video,adult);
                    jsonDataClassArrayList.add(jsonDataClass);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            recyclerView.setLayoutManager(new GridLayoutManager(Home.this,2));
            recyclerView.setAdapter(new MovieAdapter(Home.this,jsonDataClassArrayList));
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Home.this);
            progressDialog=ProgressDialog.show(Home.this,"Please Wait","Loading...",true);
            progressDialog.setCancelable(false);
        }
    }

    static class HttpResponse{
        final static String image_url="https://image.tmdb.org/t/p/w300";

        static URL bulidImgUrl(String path){
            String urlImage;
            urlImage = image_url+""+path;
            URL url=null;
            try {
                url=new URL(urlImage);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }

        static URL bulid(String img_url){
            Uri uri=Uri.parse(img_url);
            URL url=null;
            try {
                url=new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }
        static String getResponseFromHttpUrl(URL url) throws IOException {
            String responseHttp;
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream=new BufferedInputStream(httpURLConnection.getInputStream());
            responseHttp=streamConversion(inputStream);
            return responseHttp;
        }
        static String streamConversion(InputStream inputStream){
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();
            String string;
            try {
                while ((string=bufferedReader.readLine())!=null){
                    stringBuilder.append(string).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  stringBuilder.toString();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked=item.getItemId();
        if(itemClicked==R.id.popular){
            MovieDetails movieDetails=new MovieDetails();
            movieURL="https://api.themoviedb.org/3/movie/popular?api_key="+API_key;
            movieDetails.execute();
        }
        if (itemClicked == R.id.rating){
            MovieDetails movieDetails=new MovieDetails();
            movieURL="https://api.themoviedb.org/3/movie/top_rated?api_key="+API_key;
            movieDetails.execute();
        }
        return super.onOptionsItemSelected(item);
    }
}
