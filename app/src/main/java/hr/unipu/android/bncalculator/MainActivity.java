package hr.unipu.android.bncalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Klasa MainActivity predstavlja aktivnost koja postavlja layout sa odabirima vrsta ugovora
 * @author Tomislav Anđić
 * @version 2.0
 * * */

public class MainActivity extends AppCompatActivity {

    Button btn_uod, btn_au;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("NBKalkulator");
        btn_au = findViewById(R.id.btn_au);
        btn_au.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalcActivity.class);
                intent.putExtra("key1", "AU");
                startActivity(intent);
            }
        }));
        btn_uod = findViewById(R.id.btn_uod);
        btn_uod.setOnClickListener((new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, CalcActivity.class);
                intent.putExtra("key2", "UOD");
                startActivity(intent);
            }
        }));
    }
}
