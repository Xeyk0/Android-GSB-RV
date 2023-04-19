package fr.gsb;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.gsb.entities.RapportVisite;

public class VisuRvActivity extends AppCompatActivity {

    private TextView textViewNumVisite;
    private TextView textViewDateVisite;
    private TextView textViewPraticienVisite;
    private TextView textViewBilan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_rv);

        // Initialisation des TextView avec les vues correspondantes du fichier XML
        textViewNumVisite = findViewById(R.id.textViewNumVisite);
        textViewDateVisite = findViewById(R.id.textViewDateVisite);

        textViewBilan = findViewById(R.id.textViewBilan);

        // Récupération du rapport de visite depuis l'intent
        Intent intent = getIntent();
        RapportVisite rapport = intent.getParcelableExtra("rapport");

        // Vérification de nullité de l'objet rapport
        if (rapport != null) {
            // Mise à jour des TextView avec les données du rapport de visite
            textViewNumVisite.setText("Numéro de visite : " + rapport.getNumero());
            textViewDateVisite.setText("Date de visite : " + rapport.getDateVisite());
            textViewBilan.setText("Bilan : " + rapport.getBilan());
        }

        System.out.println(rapport);
    }
}
