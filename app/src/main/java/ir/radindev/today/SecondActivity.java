package ir.radindev.today;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    TextView textViewFortune;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewFortune = findViewById(R.id.textViewFortune);
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewDateTime = findViewById(R.id.textViewDateTime);

        String fortune = getIntent().getStringExtra("RANDOM_FORTUNE");
        if (fortune != null) {

            textViewFortune.setText(fortune);


        }
        textViewFortune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToCopy=textViewFortune.getText().toString();

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                ClipData clip= ClipData.newPlainText("label",textToCopy);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(SecondActivity.this, "متن کپی شد", Toast.LENGTH_SHORT).show();
            }
        });

        // نمایش تاریخ و ساعت شمسی
        //PersianDate pdate = new PersianDate();
        // PersianDateFormat pdformater = new PersianDateFormat("Y/m/d H:i");
        //  String currentDateAndTime = pdformater.format(pdate);
        //textViewDateTime.setText(currentDateAndTime);



    }
}