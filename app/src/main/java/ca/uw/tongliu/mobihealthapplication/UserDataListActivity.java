package ca.uw.tongliu.mobihealthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class UserDataListActivity extends AppCompatActivity {

    ListView user_data_listView;
    View mProgressView;
    TextView mFetchDataView;
    JSONObject ret_data_obj;
    String ret_data_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_list);
        mFetchDataView = findViewById(R.id.progressView);
        mProgressView = findViewById(R.id.communication_progress);
        user_data_listView = (ListView)findViewById(R.id.dataListView);

        ArrayList<String> contentList = new ArrayList<>();
        contentList.add("item1");
        contentList.add("item2");
        contentList.add("item3");
        contentList.add("item4");
        contentList.add("item5");
        contentList.add("item6");


        ArrayAdapter array_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contentList);
        
        user_data_listView.setAdapter(array_adapter);

    }



}
