package fr.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.gsb.entities.Visiteur;
import fr.gsb.modeles.ModeleGsb;

public class MainActivity extends AppCompatActivity {
    EditText etMatricule;
    EditText etMDP;
    ModeleGsb modele = new ModeleGsb();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMatricule = (EditText) findViewById(R.id.etMatricule);
        etMDP = (EditText) findViewById(R.id.etMDP);
    }

    public void valide (View vue){

            Visiteur visiteur= modele.seConnecter(etMatricule.getText().toString(), etMDP.getText().toString());

            if (visiteur!= null){
                Toast.makeText(this,"Connexion réussie. Bienvenue "+ visiteur.getPrenom()+" "+visiteur.getNom(),Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"Échec à la tentative. Recommencez...",Toast.LENGTH_LONG).show();
                etMatricule.setText("");
                etMDP.setText("");
            }
    }


        public void annule (View vue){
        etMatricule.setText("");
        etMDP.setText("");
    }
}