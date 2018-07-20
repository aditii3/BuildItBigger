package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.util.Pair;

import com.example.android.mylibrary.JokesActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokeslib.Jokeslib;

import java.io.IOException;


class FetchJokeTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static Jokeslib myApiService = null;
    private Context mContext;
    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;
    private String mResult = null;

    public FetchJokeTask(Context context, ProgressBar progressBar) {
        mContext = context;
        mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected final String doInBackground(Pair<Context, String>... params) {
        if (myApiService == null) {  // Only do this once
            Jokeslib.Builder builder = new Jokeslib.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        try {
            return myApiService.getRandomJoke().execute().getText();
        } catch (IOException e) {
            Log.e(FetchJokeTask.class.getSimpleName(), e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mResult = result;
        // Setting the interstitial ad
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(mContext.getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                }
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                }
                startJokeDisplayActivity();
            }

            @Override
            public void onAdClosed() {
                startJokeDisplayActivity();
            }
        });
        AdRequest ar = new AdRequest
                .Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(ar);
    }

    private void startJokeDisplayActivity() {
        Intent viewJokeIntent = new Intent(mContext, JokesActivity.class);
        viewJokeIntent.putExtra(JokesActivity.INTENT_EXTRA_JOKE, mResult);
        mContext.startActivity(viewJokeIntent);
    }


}

