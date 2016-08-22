package com.kuma.library.twitterfavo.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.kuma.library.twitterfavo.OnLikeChangedListener;
import com.kuma.library.twitterfavo.TwitterFavoImageButton;
import com.squareup.leakcanary.RefWatcher;

public class ScrollingActivity extends AppCompatActivity {

    TwitterFavoImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.kuma.library.twitterfavo.sample.R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(com.kuma.library.twitterfavo.sample.R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (TwitterFavoImageButton) findViewById(com.kuma.library.twitterfavo.sample.R.id.tw__tweet_like_button);
        button.setOnLikeChangedListener(new OnLikeChangedListener() {
            @Override
            public void onLike() {
                Log.v("Twitter Demo", "Like");
            }

            @Override
            public void onDislike() {
                Log.v("Twitter Demo", "Dislike");
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
