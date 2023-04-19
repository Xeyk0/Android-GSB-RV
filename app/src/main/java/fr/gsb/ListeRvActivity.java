package fr.gsb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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

public class ListeRvActivity extends AppCompatActivity {
    private static final String TAG = "GSB_Liste_Rv_Activity";
    private static final String API_URL = "http://192.168.167.1:80/rapports/"+ Session.getSession().getLeVisiteur().getMatricule();

    private ListView listeRapport;
    private ArrayAdapter<RapportVisite> adapter;
    private ArrayList<RapportVisite> rapports = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rv);
        listeRapport = findViewById(R.id.liste_rapports);

        String mois = getIntent().getStringExtra("mois");
        String annee = getIntent().getStringExtra("annee");
        String url = API_URL +"/" + mois + "/" + annee ;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                int numero = response.getJSONObject(i).getInt("rap_num");
                                String dateVisite = response.getJSONObject(i).getString("rap_date_visite");
                                String bilan = response.getJSONObject(i).getString("rap_bilan");

                                RapportVisite rapport = new RapportVisite(numero, dateVisite, bilan );
                                rapports.add(rapport);
                            }

                            // Adapter pour afficher les rapports dans le ListView
                            adapter = new ArrayAdapter<>(ListeRvActivity.this, android.R.layout.simple_list_item_1, rapports);
                            listeRapport.setAdapter(adapter);

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

        // Ecouteur de clic sur les éléments du ListView
        listeRapport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Récupérer le rapport de visite sélectionné
                RapportVisite rapport = rapports.get(position);

                // Passer les données individuelles du rapport de visite à l'activité de détail du rapport
                Intent intent = new Intent(ListeRvActivity.this, VisuRvActivity.class);
                intent.putExtra("numero", rapport.getNumero());
                intent.putExtra("dateVisite", rapport.getDateVisite());
                intent.putExtra("bilan", rapport.getBilan());
                startActivity(intent);
            }
        });
    }
    public void retour(View vue){
        Log.v(TAG, "intention :" + "Intention retour en arrière");
        Intent intentionEnvoyer = new Intent(getApplicationContext(), RechercheRvActivity.class);
        startActivity(intentionEnvoyer);
    }
}
