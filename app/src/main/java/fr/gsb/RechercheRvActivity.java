package fr.gsb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import fr.gsb.technique.Session;

public class RechercheRvActivity extends Activity {
    DatePicker date;
    Button rechercher;
    String dateChoisi;
    static final String TAG = "GSB_Recherche_Rv_Activity";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_rv);

        date = findViewById(R.id.date);
        rechercher = findViewById(R.id.rechercher);

        Log.v(TAG, "onCreate :" + "Création de l'activité MenuRV");

    }
    public void rechercher (View vue){

    }
    public void seDeconnecter(View vue){
        Log.v(TAG, "intention :" + "Intention vers MainActivity + deconnection");
        Session.fermer();
        Intent intentionEnvoyer = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentionEnvoyer);
    }

    public void retour(View vue){
        Log.v(TAG, "intention :" + "Intention retour en arrière");
        Intent intentionEnvoyer = new Intent(getApplicationContext(), MenuRvActivity.class);
        startActivity(intentionEnvoyer);
    }

}
