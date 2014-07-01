package com.manotaurgames.fortune;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity {

    ImageView cookie = null;
    TextView fortune = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public String readEntireFile(String filename) {
        AssetManager assetManager = getAssets();
        try {

            InputStream stream = assetManager.open(filename);
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);

            return new String(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        cookie = (ImageView) findViewById(R.id.cookie);
        fortune = (TextView) findViewById(R.id.fortune);

        String rawFileContents = readEntireFile("fortunes.txt");
        fortune.setText(rawFileContents);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "I am being destroyed");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_help) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage("No")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
            TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER_HORIZONTAL);
            messageView.setTypeface(Typeface.DEFAULT_BOLD);
            messageView.setTextColor(getResources().getColor(R.color.poople));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openfortune(View view) {
        cookie.setClickable(false);
        cookie.setAlpha(1f);
        cookie.animate().alpha(0f).setDuration(1000).start();

        fortune.setAlpha(0f);
        fortune.animate().alpha(1f).setDuration(1000).start();
    }

    public void closefortune(View view) {
        cookie.animate().alpha(1f).setDuration(1000).start();
        cookie.setClickable(true);
        fortune.animate().alpha(0f).setDuration(1000).start();
    }
}
