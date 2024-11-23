package nure.priadko.maksym;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private boolean isLargeText = false;
    private boolean isColorChanged = false;
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

        textView = findViewById(R.id.textView);
    }

    public void onChangeTextClick(View view) {
        textView.setText("Текст змінено!");
    }

    public void onShowMessageClick(View view) {
        Toast.makeText(this, "Це повідомлення!", Toast.LENGTH_SHORT).show();
    }

    public void onButton1Click(View view) {
        if (isLargeText) {
            textView.setTextSize(18);
        } else {
            textView.setTextSize(24);
        }
        isLargeText = !isLargeText;
    }
    public void onButton2Click(View view) {
        if (isColorChanged) {
            textView.setTextColor(Color.BLACK);
        } else {
            textView.setTextColor(Color.BLUE);
        }
        isColorChanged = !isColorChanged;
    }

}