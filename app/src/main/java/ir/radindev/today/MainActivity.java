package ir.radindev.today;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    TextInputEditText editTextUsername;
    MaterialButton buttonSubmit;
    MaterialButton buttonFingerprint;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_B));


        editTextUsername = findViewById(R.id.editTextUsername);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonFingerprint = findViewById(R.id.buttonFingerprint);


//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        Glide.with(this)
//                .load("https://bandmusic.ir/wp-content/uploads/2023/07/photo_%DB%B2%DB%B0%DB%B2%DB%B1-%DB%B0%DB%B6-%DB%B0%DB%B5_%DB%B0%DB%B5-%DB%B1%DB%B8-%DB%B4%DB%B2.jpg")
//                .placeholder(R.drawable.image)
//                .centerCrop()
//                .into(imageView);

        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "خطا در احراز هویت: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "احراز هویت موفقیت‌آمیز بود!", Toast.LENGTH_SHORT).show();

                // انتخاب تصادفی یک فال
                String[] fortunes = getResources().getStringArray(R.array.fortunes);
                String randomFortune = fortunes[new Random().nextInt(fortunes.length)];

                // ارسال فال به اکتیویتی دوم
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("RANDOM_FORTUNE", randomFortune);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "احراز هویت ناموفق بود. دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("احراز هویت با اثر انگشت")
                .setSubtitle("لطفاً اثر انگشت خود را اسکن کنید")
                .setNegativeButtonText("لغو")
                .build();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                if (username.isEmpty()) {
                    Toast.makeText(MainActivity.this, "نام کاربری را وارد نکردی", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "نام کاربری وارد شد. حالا دکمه اسکن اثر انگشت را فشار دهید.", Toast.LENGTH_SHORT).show();
                    buttonFingerprint.setVisibility(View.VISIBLE);


                }
            }
        });

        buttonFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });


    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_aboutUs) {
            startActivity(new Intent(getApplicationContext(), aboutUS.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
