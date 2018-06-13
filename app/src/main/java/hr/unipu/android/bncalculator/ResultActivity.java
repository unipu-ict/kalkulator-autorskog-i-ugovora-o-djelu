package hr.unipu.android.bncalculator;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Klasa ResultActivity predstavlja aktivnost koja postavlja layout i na njega izlistava listu rezultata
 * @author Tomislav Anđić
 * @version 2.0
 * * */

public class ResultActivity extends AppCompatActivity {

   // TextView brutoRes, netoRes, izdatakRes, osnovicaRes, prviStupRes, drugiStupRes, brutoDopRes, poreznaOsnRes,
     //       porezRes, prirezRes, ukPorezRes, zdrOsigRes, ukTrosakRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        this.setTitle("Rezultat obračuna");
        initData();


    }

    void initData(){

        ArrayList<Stavka> list = FunkcijeObracuna.getList();
        DecimalFormat df2 = new DecimalFormat(".##");
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearRes);
       LinearLayout linearLayoutN = (LinearLayout) findViewById(R.id.linearResN);

     //     setContentView(linearLayout);
        for(Stavka s : list){
            TextView textView = new TextView(this);
            TextView textViewN = new TextView(this);

            textView.setTextSize(17);
            textViewN.setTextSize(17);

            textView.setTextColor(getResources().getColor(R.color.colorWhite));
            textViewN.setTextColor(getResources().getColor(R.color.colorBlack));

            textView.setPadding(10,40,0,0);
            textViewN.setPadding(10,40,5 ,0);

            textView.setText(s.naziv);
            textViewN.setText(String.format("%.2f", s.iznos)+ " kn ");

            textViewN.setGravity(Gravity.RIGHT);

            linearLayout.addView(textView);
            linearLayoutN.addView(textViewN);

        }
    }
}
