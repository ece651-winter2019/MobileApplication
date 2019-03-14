package ca.uw.tongliu.mobihealthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class UserDataQueryActivity extends AppCompatActivity {
    private View mProgressView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_query);

        mTextView = findViewById(R.id.textView2);
        mProgressView = findViewById(R.id.progressBar);

        FetchUserData();
    }

    private FetchUserDataTask mFetchDataTask;
    String ret_data_string = "";
    JSONObject ret_data_obj = null;

    public void FetchUserData(){            // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        showProgress(false);
        HttpComm http_comm = new HttpComm(
                "GET"
                ,null
        );
        http_comm.setUrlResource("api/patientrecords");
        mFetchDataTask = new FetchUserDataTask(http_comm);
        mFetchDataTask.execute();
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class FetchUserDataTask extends AsyncTask<String, Void, String> {
        HttpComm fetch_data_http_comm;


        public FetchUserDataTask(HttpComm http_comm) {
            fetch_data_http_comm = http_comm;
        }

        @Override
        protected String doInBackground(String... params) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    ret_data_string = fetch_data_http_comm.httpAPI();
                    return ret_data_string;
                } catch (JSONException e) {
                    return e.toString();
                }
            } catch (IOException e) {
                return e.toString();
            }

            // TODO: register the new account here.
            //return true;
        }

        @Override
        protected void onPostExecute(String ret_msg) {
            mFetchDataTask = null;
            try {
                mTextView.setText(ret_msg);
                ret_data_obj = new JSONObject(ret_msg);
                saveDataToLocalFile(ret_data_obj);
                finish();
            }
            catch (JSONException je){
            }
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mFetchDataTask = null;
        }

    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mTextView.setVisibility(show ? View.GONE : View.VISIBLE);
            mTextView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mTextView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mTextView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void saveDataToLocalFile(JSONObject jsonData)
    {
        String filename = "bpData";
        String fileContents = jsonData.toString();
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }



}
