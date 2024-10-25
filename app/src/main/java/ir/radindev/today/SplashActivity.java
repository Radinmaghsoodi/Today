package ir.radindev.today;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

// مخفی کردن نوار فعالیت
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // انتقال به اکتیویتی اصلی بعد از ۵ ثانیه
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000); // ۵۰۰۰ میلی‌ثانیه = ۵ ثانیه
    }
}