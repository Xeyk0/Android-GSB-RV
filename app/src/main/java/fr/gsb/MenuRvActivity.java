package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import fr.gsb.MainActivity;

import fr.gsb.technique.Session;

import androidx.appcompat.app.AppCompatActivity;

public class MenuRvActivity extends AppCompatActivity {
    static final String TAG = "GSB_Menu_Rv_Activity";
    TextView identifiant;
    Button consulter;
    Button saisir;

    Button deconnexion;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        identifiant = findViewById(R.id.id);
        identifiant.setText(Session.getSession().getLeVisiteur().getPrenom() + " " + Session.getSession().getLeVisiteur().getNom());
        consulter = findViewById(R.id.consulter);
        saisir = findViewById(R.id.saisir);
        deconnexion = findViewById(R.id.seDeconnecter);

        Log.v(TAG, "onCreate :" + "Création de l'activité MenuRV");

    }

    public void consulter(View vue){

        Log.v(TAG, "intention :" + "Intention vers RechercheRvActivity");
        Intent intentionEnvoyer = new Intent(getApplicationContext(), RechercheRvActivity.class);
        startActivity(intentionEnvoyer);

    }
    public void seDeconnecter(View vue){
        Log.v(TAG, "intention :" + "Intention vers MainActivity + deconnection");
        Session.fermer();
        Intent intentionEnvoyer = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentionEnvoyer);
    }
}
