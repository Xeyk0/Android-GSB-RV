package fr.gsb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import fr.gsb.entities.RapportVisite;

public class ListeRvActivity extends Activity {
    private static final String TAG = "GSB_Liste_Rv_Activity";
    private static final String API_URL = "";

    private RequestQueue requestQueue;
    private ArrayList<RapportVisite> rapports = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rv);

        // Récupération de la date envoyée par l'intention
        String date = getIntent().getStringExtra("date");

        // Construction de l'URL de l'API avec la date
        String url = API_URL + date;

        // Initialisation de la RequestQueue Volley
        requestQueue = Volley.newRequestQueue(this);

        // Envoi de la requête à l'API
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                // Récupération des données du rapport
                                int numero = response.getJSONObject(i).getInt("rap_num");
                                String dateVisite = response.getJSONObject(i).getString("rap_date_visite");
                                String bilan = response.getJSONObject(i).getString("rap_bilan");
                                String nomPraticien = response.getJSONObject(i).getString("pra_nom");
                                String prenomPraticien = response.getJSONObject(i).getString("pra_prenom");
                                String cpPraticien = response.getJSONObject(i).getString("pra_cp");
                                String villePraticien = response.getJSONObject(i).getString("pra_ville");

                                // Création d'un objet RapportVisite avec ces données
                                RapportVisite rapport = new RapportVisite(numero, dateVisite, bilan,
                                        nomPraticien, prenomPraticien, cpPraticien, villePraticien);

                                // Ajout du rapport à la liste
                                rapports.add(rapport);

                                Log.i(TAG, rapport.toString());
                            }

                            // Redirection vers le menu une fois les rapports chargés
                            Intent intentionEnvoyer = new Intent(getApplicationContext(), MenuRvActivity.class);
                            startActivity(intentionEnvoyer);

                        } catch (JSONException e) {
                            Log.e(TAG, "Erreur de parsing JSON : " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Erreur de requête Volley : " + error.getMessage());
                    }
                });

        // Ajout de la requête à la RequestQueue
        requestQueue.add(request);
    }
}
