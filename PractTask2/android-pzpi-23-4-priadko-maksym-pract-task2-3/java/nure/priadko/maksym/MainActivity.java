package nure.priadko.maksym;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    Timer timer;
    TextView timerField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void onNextActivityClick(View v) {
        startActivity(new Intent(this, SecondActivity.class));
    }
    public void onIncreaseCounter(View v) {
        TextView count = findViewById(R.id.count);
        Integer previousCounter = Integer.parseInt(count.getText().toString());
        Integer newValue = previousCounter + 1;
        count.setText(newValue.toString());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        TextView count = findViewById(R.id.count);
        TextView timer = findViewById(R.id.timer);
        outState.putString("timer", timer.getText().toString());
        outState.putString("counter", count.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView count = findViewById(R.id.count);
        count.setText(savedInstanceState.getString("counter"));
        TextView timer = findViewById(R.id.timer);
        timer.setText(savedInstanceState.getString("timer"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");

        timerField = findViewById(R.id.timer);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerTick();
            }
        }, 0, 1000L);
    }

    private void timerTick() {
        this.runOnUiThread(doTask);
    }

    private Runnable doTask = new Runnable() {
        @Override
        public void run() {
            Integer prev = Integer.parseInt(timerField.getText().toString());
            Integer newV = prev+1;
            timerField.setText(newV.toString());
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");

        timer.cancel();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }
}