package fr.gsb;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


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

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                int numero = response.getJSONObject(i).getInt("pra_num");
                                String nom = response.getJSONObject(i).getString("pra_nom");
                                String prenom = response.getJSONObject(i).getString("pra_prenom");

                                // Ajouter le nom et prénom du praticien au tableau ou à la liste
                                listePraticiens.add(nom + " " + prenom);
                            }

                            // Mettre à jour les données de l'adaptateur avec le tableau ou la liste des praticiens
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listePraticiens);
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

    }


