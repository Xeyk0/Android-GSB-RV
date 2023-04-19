package fr.gsb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.gsb.entities.Praticien;
import fr.gsb.entities.RapportVisite;
import fr.gsb.entities.Visiteur;
import fr.gsb.technique.Session;

public class SaisieRvActivity extends AppCompatActivity {

    static final String TAG = "GSB_Menu_Rv_Activity";
    TextView bilan;
    EditText rapBilan;
    DatePicker dateSaisie;
    Spinner praticiens;
    ArrayList<String> listePraticiens = new ArrayList<>();
    String matriculeVisiteur = Session.getSession().getLeVisiteur().getMatricule();
    private static final String url = "http://192.168.167.1:80/praticiens";

    private RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_rv);

        bilan = findViewById(R.id.bilan);
        rapBilan = findViewById(R.id.rapBilan);
        dateSaisie = findViewById(R.id.dateSaisie);
        praticiens = findViewById(R.id.listePraticien);
        requestQueue = Volley.newRequestQueue(this);
        System.out.println(matriculeVisiteur);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                int numero = response.getJSONObject(i).getInt("pra_num");
                                String nom = response.getJSONObject(i).getString("pra_nom");
                                String prenom = response.getJSONObject(i).getString("pra_prenom");

                                String praticien = numero + " " + nom + " " + prenom;
                                listePraticiens.add(praticien);
                            }

                            // Mettre à jour les données de l'adaptateur avec le tableau ou la liste des praticiens
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(SaisieRvActivity.this, android.R.layout.simple_spinner_dropdown_item, listePraticiens);
                            praticiens.setAdapter(adapter);

                        } catch (JSONException e) {
                            Log.e(TAG, "Erreur JSON : " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Erreur de requête Volley : " + error.getMessage());
                    }
                });

        requestQueue.add(request);



    }

    public void valider(View vue) {

        int day = dateSaisie.getDayOfMonth();
        int month = dateSaisie.getMonth() + 1;
        int year = dateSaisie.getYear();
        String date = String.format("%04d-%02d-%02d", year, month, day);

        String bilanText = rapBilan.getText().toString();
        String praticienText = praticiens.getSelectedItem().toString();
        String numPraticien = praticienText.split(" ")[0];
        ;

        // Créer un objet JSON pour les données du rapport
        JSONObject rapportJson = new JSONObject();
        try {
            rapportJson.put("matricule",matriculeVisiteur);
            rapportJson.put("bilan", bilanText);
            rapportJson.put("visite", date);
            rapportJson.put("praticien", numPraticien);
        } catch (JSONException e) {
            Log.e(TAG, "Erreur JSON : " + e.getMessage());
        }

        // Envoyer la requête POST à l'API REST
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://192.168.167.1:80/rapports", rapportJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Traitement de la réponse de l'API REST en cas de succès
                        Log.d(TAG, "Réponse de l'API REST : " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Traitement de l'erreur de la requête Volley
                        Log.e(TAG, "Erreur de requête Volley : " + error.getMessage());
                    }
                });

        // Ajouter la requête à la file d'attente de Volley
        requestQueue.add(request);

        Log.v(TAG, "intention :" + "Intention retour en arrière");
        Toast.makeText(this, "Rapport envoyer", Toast.LENGTH_LONG).show();
        Intent intentionEnvoyer = new Intent(getApplicationContext(), MenuRvActivity.class);
        startActivity(intentionEnvoyer);
    }
}
