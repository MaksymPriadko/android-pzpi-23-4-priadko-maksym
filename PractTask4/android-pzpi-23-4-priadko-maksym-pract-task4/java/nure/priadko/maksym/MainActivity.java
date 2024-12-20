package nure.priadko.maksym;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText etName, etAge;
    Button btnSavePrefs, btnShowUsers, btnSaveFile, btnReadFile;
    TextView tvResult;
    SharedPreferences sharedPreferences;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        btnSavePrefs = findViewById(R.id.btnSave);
        btnShowUsers = findViewById(R.id.btnShow);
        btnSaveFile = findViewById(R.id.btnWriteFile);
        btnReadFile = findViewById(R.id.btnReadFile);
        tvResult = findViewById(R.id.tvResult);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        dbHelper = new DBHelper(this);

        loadSharedPrefs();

        btnSavePrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToSharedPrefs();
            }
        });

        btnSavePrefs.setOnClickListener(v -> saveToSQLite());
        btnShowUsers.setOnClickListener(v -> displayUsers());

        btnSaveFile.setOnClickListener(v -> writeFile());
        btnReadFile.setOnClickListener(v -> readFile());
    }

    private void saveToSharedPrefs() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", etName.getText().toString());
        editor.putInt("age", Integer.parseInt(etAge.getText().toString()));
        editor.apply();
        loadSharedPrefs();
    }

    private void loadSharedPrefs() {
        String name = sharedPreferences.getString("name", "Немає даних");
        int age = sharedPreferences.getInt("age", 0);
        tvResult.setText("Ім'я: " + name + ", Вік: " + age);
    }

    private void saveToSQLite() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", etName.getText().toString());
        values.put("age", Integer.parseInt(etAge.getText().toString()));

        long result = db.insert("users", null, values);
        if (result != -1) {
            Toast.makeText(this, "Користувача збережено!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void displayUsers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("users", null, null, null, null, null, null);

        StringBuilder data = new StringBuilder();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow("age"));
            data.append("Ім'я: ").append(name).append(", Вік: ").append(age).append("\n");
        }
        tvResult.setText(data.toString());
        cursor.close();
        db.close();
    }

    private void writeFile() {
        String text = etName.getText().toString();
        try (FileOutputStream fos = openFileOutput("user_data.txt", MODE_PRIVATE)) {
            fos.write(text.getBytes());
            Toast.makeText(this, "Дані записано у файл", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        try (FileInputStream fis = openFileInput("user_data.txt")) {
            int c;
            StringBuilder temp = new StringBuilder();
            while ((c = fis.read()) != -1) {
                temp.append((char) c);
            }
            tvResult.setText(temp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}