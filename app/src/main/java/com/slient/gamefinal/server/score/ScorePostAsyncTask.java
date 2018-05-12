package com.slient.gamefinal.server.score;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.slient.gamefinal.config.Instance;
import com.slient.gamefinal.models.game.Score;
import com.slient.gamefinal.server.ConfigServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by silent on 5/13/2018.
 */
public class ScorePostAsyncTask extends AsyncTask<String, Void, Integer> {
    private Context context;
    private ProgressDialog progressDialog;
    private Map<String, String> arguments;

    public ScorePostAsyncTask(Context context, Score score) {
        this.context = context;
        this.progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Uploading...");
        this.arguments = new HashMap<>();
        this.arguments.put(ConfigServer.ARGU_USERNAME_SCORE, Instance.nowUser.username);
        this.arguments.put(ConfigServer.ARGU_SCORE_SCORE, String.valueOf(score.score));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        this.arguments.put(ConfigServer.ARGU_DATETIME_SCORE, simpleDateFormat.format(score.dateTime));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        URL url;
        HttpURLConnection httpURLConnection = null;

        try {
            url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            StringBuilder sj = new StringBuilder();
            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                sj.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
            }
            byte[] out = sj.toString().getBytes();

            httpURLConnection.setFixedLengthStreamingMode(out.length);
            httpURLConnection.setRequestProperty("Authorization", "bearer "+ Instance.nowUser.accessToken);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(out);
            return httpURLConnection.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return HttpsURLConnection.HTTP_INTERNAL_ERROR;
    }

    @Override
    protected void onPostExecute(Integer stt) {
        progressDialog.dismiss();
        if(stt == HttpURLConnection.HTTP_OK){
            Log.d("TRUNG", "Uploading score successful");
        } else {
            Log.d("TRUNG", "Uploading score ERROR "+stt);
        }
    }
}
