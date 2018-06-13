package hr.unipu.android.bncalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Klasa CalcActivity predstavlja aktivnost koja postavlja layout odabranog kalkulatora
 * @author Tomislav Anđić
 * @version 2.0
 * * */

public class CalcActivity extends AppCompatActivity {
    /**
     * Atribut bruto označava upravo bruto iznos.
     */
    private Double brutoIznos = 0.0;

    /**
     * Atribut prijenos sprema vrstu obračuna za prijenos vrijednosti u druge prozore.
     */
    private String prijenos = "";

    /**
     * Double varijabla predstavlja odabrani prirez
     */
    Double prirez;

    //lista koja sadrži opcije prireza
    private ArrayList<Serializable> options = new ArrayList<Serializable>();
    Spinner mjestoPrebivalistaCombo;
    EditText iznos;
    RadioGroup groupVrstaIznosa;
    RadioButton neto, bruto;
    CheckBox umirovljenik, obveznik, osiguranik, umjetnik;
    Button obracun;
    TextView umirovljenikText, umjetnikText;
    private static String vrsta = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_au);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
               vrsta = null;
            } else if (extras.containsKey("key1")){
                vrsta = extras.getString("key1");
            } else{
                vrsta = extras.getString("key2");
            }
        } else {
            if (savedInstanceState.containsKey("key1")){
                vrsta = savedInstanceState.getString("key1");
            } else{
                vrsta = savedInstanceState.getString("key2");
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupVrstaIznosa = findViewById(R.id.groupVrstaIznosa);
        neto = findViewById(R.id.neto);
        bruto = findViewById(R.id.bruto);
        bruto.setChecked(true);
        umirovljenik = findViewById(R.id.umirovljenikCheck);
        umirovljenikText = findViewById(R.id.textUmir);
        obveznik = findViewById(R.id.obveznikCheck);
        osiguranik = findViewById(R.id.osiguranikCheck);
        obracun = findViewById(R.id.obracunBtn);
        umjetnik = findViewById(R.id.umjetnikCheck);
        umjetnikText = findViewById(R.id.textUmj);
        obracun.setOnClickListener(((v)-> {
            try {
                handleButtonObracun();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        iznos = findViewById(R.id.iznosText);
        mjestoPrebivalistaCombo = findViewById(R.id.citySpinner);
        options.add("bez prireza");
        mjestoPrebivalistaCombo.setAdapter(adapter);
        if(vrsta.equals("AU")){
            initializeAU();
        }else{
            initializeUOD();
        }
        dohvatiPrireze();
    }

    void initializeUOD(){
        umirovljenik.setVisibility(View.VISIBLE);
        umirovljenikText.setVisibility(View.VISIBLE);
        umjetnik.setVisibility(View.INVISIBLE);
        umjetnikText.setVisibility(View.INVISIBLE);
        this.setTitle("Ugovor o djelu");
    }

    void initializeAU(){
        umirovljenik.setVisibility(View.INVISIBLE);
        umirovljenikText.setVisibility(View.INVISIBLE);
        umjetnik.setVisibility(View.VISIBLE);
        umjetnikText.setVisibility(View.VISIBLE);
        this.setTitle("Autorski ugovor");
    }

    public ArrayList<String> dohvatiPrireze(){
        ArrayList<String> lista = new ArrayList<>();
        String text = "";

        try{
            InputStream is = getAssets().open("prirez.txt");
            BufferedReader buffreader = new BufferedReader(new InputStreamReader(is));

            while((text = buffreader.readLine())!=null){
                lista.add(text);
            }
            buffreader.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        options.addAll(lista);

        return null;
    }

    void handleButtonObracun() throws IOException{

        Double rez = 0.0;
        String mjestoPrireza = "";
        Integer vrstaID = 0;
        String gvs = "";

        if(iznos.getText().length() == 0 || !org.apache.commons.lang3.StringUtils.isNumeric(iznos.getText())){
            Toast toast = Toast.makeText(this, "Unesite broj u polje za iznos!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(groupVrstaIznosa.getCheckedRadioButtonId() != -1) {
            vrstaID = groupVrstaIznosa.getCheckedRadioButtonId();
            RadioButton rb = (RadioButton) findViewById(vrstaID);
            gvs = rb.getText().toString();
        }
        mjestoPrireza = mjestoPrebivalistaCombo.getSelectedItem().toString();

        if(mjestoPrireza.equalsIgnoreCase("bez prireza")){
            prirez = 0d;
        }else {

           prirez = Double.parseDouble(mjestoPrireza.substring((mjestoPrireza.lastIndexOf(".")-2)));
           prirez /= 100;
        }

        switch (gvs){
            case "NETO":
                if(umjetnik.isChecked())
                {
                    FunkcijeObracuna.PreracunBrutoAutorskiUmjetnika(Double.parseDouble(iznos.getText().toString()), prirez);
                    FunkcijeObracuna.ispisAutorskiUmjetnika(prirez, osiguranik.isChecked(), obveznik.isChecked());
                    prijenos = "Autorski ugovor umjetnika";
                    break;

                }else if(vrsta.equals("UOD")) {
                    FunkcijeObracuna.PreracunBrutoUgovorDjelu(Double.parseDouble(iznos.getText().toString()), prirez);
                    FunkcijeObracuna.ispisUgovorDjelu(prirez, osiguranik.isChecked(), obveznik.isChecked());
                    prijenos = "Ugovor o djelu";
                    break;

                }else {
                    FunkcijeObracuna.PreracunBrutoAutorski(Double.parseDouble(iznos.getText().toString()), prirez);

                    FunkcijeObracuna.ispisAutorski(prirez, osiguranik.isChecked(), obveznik.isChecked());
                    prijenos = "Autorski ugovor";
                    break;
                }
            case "BRUTO":
                if(umjetnik.isChecked())
                {
                    FunkcijeObracuna.setBruto(Double.parseDouble(iznos.getText().toString()));
                    FunkcijeObracuna.ispisAutorskiUmjetnika(prirez, osiguranik.isChecked(), obveznik.isChecked());
                    prijenos = "Autorski ugovor umjetnika";
                    break;
                }else if(vrsta.equals("UOD")){
                    FunkcijeObracuna.setBruto(Double.parseDouble(iznos.getText().toString()));
                    FunkcijeObracuna.ispisUgovorDjelu(prirez, osiguranik.isChecked(), obveznik.isChecked());
                    prijenos = "Ugovor o djelu";
                    break;
                }

                else{
                    FunkcijeObracuna.setBruto(Double.parseDouble(iznos.getText().toString()));
                    FunkcijeObracuna.ispisAutorski(prirez, osiguranik.isChecked(), obveznik.isChecked());
                    prijenos = "Autorski ugovor";
                    break;
                }

            }


        brutoIznos = FunkcijeObracuna.getBruto();
        Intent intent = new Intent(CalcActivity.this, ResultActivity.class);
        startActivity(intent);

    }
}
