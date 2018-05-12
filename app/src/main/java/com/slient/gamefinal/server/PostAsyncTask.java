package com.n14dcpt048.moneylover.server;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonWriter;
import android.util.Log;
import android.widget.Toast;

import com.n14dcpt048.moneylover.Instance;
import com.n14dcpt048.moneylover.adapters.MoneyAdapter;
import com.n14dcpt048.moneylover.models.Money;
import com.n14dcpt048.moneylover.utils.JsonParserMoney;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by silent on 4/5/2018.
 */
public class PostAsyncTask extends AsyncTask<String, Void, Integer> {

    private Context context;
    private ProgressDialog progressDialog;
    private  Map<String, String> arguments;
    private Money money;

    public PostAsyncTask(Context context, ProgressDialog progressDialog, Money money) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.money = money;
        arguments = new HashMap<>();
        arguments.put(JsonParserMoney.PARAM_MESSAGE, money.getMessage());
        arguments.put(JsonParserMoney.PARAM_COST, money.getCost() + "");
        arguments.put(JsonParserMoney.PARAM_TYPE, money.getType() + "");
        arguments.put(JsonParserMoney.PARAM_DATE, money.getDateString());
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
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(out);

            // get InputStream Money is returned to set id for filed money

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
    protected void onPostExecute(Integer respone) {
        progressDialog.dismiss();
        if (respone == HttpsURLConnection.HTTP_CREATED) {
            Instance.moneyList.add(money);
            Toast.makeText(context, "Add successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "There are some errors. Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
