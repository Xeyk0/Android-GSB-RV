package fr.gsb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import fr.gsb.technique.Session;

public class ListeRvActivity extends Activity {
    private static final String TAG = "GSB_Liste_Rv_Activity";
    private static final String API_URL = "http://192.168.216.1:80/rapports/"+ Session.getSession().getLeVisiteur().getMatricule();

    TextView rapportVisite;

    private RequestQueue requestQueue;
    private ArrayList<RapportVisite> rapports = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rv);
        rapportVisite  = findViewById(R.id.rapportVisite);
        String mois = getIntent().getStringExtra("mois");
        String annee = getIntent().getStringExtra("annee");
        String url = API_URL +"/" + mois + "/" + annee ;

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            StringBuilder listeRapportVisite = new StringBuilder();
                            for (int i = 0; i < response.length(); i++) {
                                int numero = response.getJSONObject(i).getInt("rap_num");
                                String dateVisite = response.getJSONObject(i).getString("rap_date_visite");
                                String bilan = response.getJSONObject(i).getString("rap_bilan");
                                String nomPraticien = response.getJSONObject(i).getString("pra_nom");
                                String prenomPraticien = response.getJSONObject(i).getString("pra_prenom");
                                String cpPraticien = response.getJSONObject(i).getString("pra_cp");
                                String villePraticien = response.getJSONObject(i).getString("pra_ville");

                                listeRapportVisite.append("Numéro de rapport : ").append(numero).append("\n");
                                listeRapportVisite.append("Date de visite : ").append(dateVisite).append("\n");
                                listeRapportVisite.append("Bilan : ").append(bilan).append("\n");
                                listeRapportVisite.append("Nom du praticien : ").append(nomPraticien).append("\n");
                                listeRapportVisite.append("Prénom du praticien : ").append(prenomPraticien).append("\n");
                                listeRapportVisite.append("Code postal du praticien : ").append(cpPraticien).append("\n");
                                listeRapportVisite.append("Ville du praticien : ").append(villePraticien).append("\n\n");


                                rapportVisite.setText(listeRapportVisite);

                                Log.i(TAG, listeRapportVisite.toString());

                            }

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
