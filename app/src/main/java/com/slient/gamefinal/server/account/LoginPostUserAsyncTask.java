package com.slient.gamefinal.server.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.slient.gamefinal.config.Instance;
import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.models.account.User;
import com.slient.gamefinal.server.ConfigServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by silent on 5/13/2018.
 */
public class LoginPostUserAsyncTask extends AsyncTask<String, Void, User> {
    private Context context;
    private ProgressDialog progressDialog;
    private Map<String, String> arguments;
    private User user;

    public LoginPostUserAsyncTask(Context context, User user) {
        this.context = context;
        this.progressDialog = new ProgressDialog(context);
        this.user = user;
        progressDialog.setMessage("Wait a minute...");
        this.arguments = new HashMap<>();
        this.arguments.put(ConfigServer.ARGU_USERNAME_LOGIN, user.username);
        this.arguments.put(ConfigServer.ARGU_PASSWORD_LOGIN, user.password);
        this.arguments.put(ConfigServer.ARGU_GRANT_TYPE_LOGIN, "password");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected User doInBackground(String... strings) {
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
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String json = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    json += line;
                }
                user.accessToken = parseJsonUserGetAccessToken(json);
                return user;
            }
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

    private String parseJsonUserGetAccessToken(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.getString("access_token");
    }

    @Override
    protected void onPostExecute(User user) {
        progressDialog.dismiss();
        if(user != null){
            Instance.nowUser = user;
            Log.d("TRUNG", user.accessToken);
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
            ((MainActivity) context).replaceGameFragment();
        } else {
            Toast.makeText(context, "There are some errors", Toast.LENGTH_SHORT).show();
        }
    }
}
