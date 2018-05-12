package com.slient.gamefinal.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.slient.gamefinal.R;
import com.slient.gamefinal.adapters.ScoreAdapter;
import com.slient.gamefinal.config.Instance;
import com.slient.gamefinal.models.game.Score;
import com.slient.gamefinal.server.ConfigServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by silent on 5/12/2018.
 */
public class HighScoreFragment extends Fragment {

    private Toolbar toolbar;
    private ListView listView;
    private ScoreAdapter adapter;

    public static HighScoreFragment newInstance(){
        HighScoreFragment highScoreFragment = new HighScoreFragment();
        return highScoreFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ScoreAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.highscore_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        toolbar.setTitle("High Score");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        new ScoreGetAsyncTask().execute(ConfigServer.SCORE_URL);
    }

    class ScoreGetAsyncTask extends AsyncTask<String, Void, List<Score>> {

        private ProgressDialog progressDialog;

        public ScoreGetAsyncTask(){
            this.progressDialog = new ProgressDialog(getContext());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected List<Score> doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection = null;
            BufferedReader buffer = null;
            StringBuilder jsonBuilder = new StringBuilder();
            String input;
            try {
                String stringUrl = strings[0]+"?uid="+Instance.nowUser.username;
                url = new URL(stringUrl);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Authorization", "bearer "+ Instance.nowUser.accessToken);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                buffer = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((input = buffer.readLine()) != null){
                    jsonBuilder.append(input);
                }
                if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    return parserScoreJson(jsonBuilder.toString());
                }
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return null;
        }

        private List<Score> parserScoreJson(String json) throws JSONException {
            JSONArray jsonArray = new JSONArray(json);
            List<Score> scores = new ArrayList<>();
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                scores.add(new Score(jsonObject.getInt("score1"), jsonObject.getString("time_date")));
            }
            return scores;
        }

        @Override
        protected void onPostExecute(List<Score> scores) {
            progressDialog.dismiss();
            if(scores != null) {
                adapter.swap(scores);
            } else {
                Toast.makeText(getContext(), "There are some errors", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
