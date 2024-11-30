package nure.priadko.maksym;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName();
    Button alert;
    Button progress;
    Button date;
    Button time;
    Button custom;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        alert = findViewById(R.id.alert);
        progress = findViewById(R.id.progress);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        custom = findViewById(R.id.custom);

        alert.setOnClickListener(v ->
                new AlertDialog.Builder(context)
                        .setTitle("Alert dialog")
                        .setMessage("Test")
                        .setPositiveButton("Ok", (dialog, which) -> {
                            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                        })
                        .setNegativeButton("Cancel", ((dialog, which) -> {
                            Toast.makeText(context, "Failure", Toast.LENGTH_LONG).show();
                        }))
                        .create()
                        .show()
        );

        progress.setOnClickListener( v -> {
                    ProgressDialog dialog = new ProgressDialog(context);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("This will never end...");
                    dialog.create();
                    dialog.show();
                }
        );

        date.setOnClickListener(v -> {
                    final Calendar calendar = Calendar.getInstance();
                    new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                        Toast.makeText(context, String.format("year: %d, month: %d, day: %d", year, month, dayOfMonth), Toast.LENGTH_LONG).show();
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                            .show();
                }
        );

        time.setOnClickListener(v -> {
                    final Calendar calendar = Calendar.getInstance();
                    new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                        Toast.makeText(context, String.format("hours: %d, minutes: %d", hourOfDay, minute), Toast.LENGTH_LONG).show();
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
                            .show();
                }
        );

        custom.setOnClickListener(v -> {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_custom);
                    dialog.setCancelable(true);
                    dialog.findViewById(R.id.ok).setOnClickListener(view -> {
                        dialog.dismiss();
                        Toast.makeText(context, "Custom dialog: okay", Toast.LENGTH_LONG).show();
                    });
                    dialog.findViewById(R.id.cancel).setOnClickListener(view -> {
                        dialog.dismiss();
                        Toast.makeText(context, "Custom dialog: cancel", Toast.LENGTH_LONG).show();
                    });
                    dialog.show();
                }
        );

        findViewById(R.id.handler).setOnClickListener(v -> {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Toast.makeText(context, "5 seconds wasted", Toast.LENGTH_LONG).show();
            }, 5000);
        });

        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            list.add("ItemAdapter item with id: ");
        }

        ((RecyclerView) findViewById(R.id.list)).setAdapter(new ItemAdapter(list));


    }
}