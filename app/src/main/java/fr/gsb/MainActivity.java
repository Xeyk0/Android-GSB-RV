package fr.gsb;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.gsb.entities.Visiteur;
import fr.gsb.modeles.ModeleGsb;
import fr.gsb.technique.Session;

public class MainActivity extends AppCompatActivity {
    EditText etMatricule;
    EditText etMDP;
    ModeleGsb modele = new ModeleGsb();
    static final String TAG = "GSB_MAIN_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMatricule = (EditText) findViewById(R.id.etMatricule);
        etMDP = (EditText) findViewById(R.id.etMDP);
        Log.v(TAG, "onCreate :" + "L'activité principale a été créée");

    }

    public void valide(View vue) {
        String url = "http://192.168.167.1:80/visiteurs/"+etMatricule.getText().toString()+"/"+etMDP.getText().toString();

       Response.Listener<JSONObject> ecouteurReponse = new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               try {
                   Visiteur visiteur = new Visiteur();
                   visiteur.setMatricule(response.getString("vis_matricule"));
                   visiteur.setNom(response.getString("vis_nom"));
                   visiteur.setPrenom(response.getString("vis_prenom"));
                   Session.ouvrir(visiteur);

                   Intent intentionEnvoyer = new Intent(getApplicationContext(), MenuRvActivity.class);
                   startActivity(intentionEnvoyer);
                   Log.i(TAG,"Connexion établie");

               } catch (JSONException e) {
                   Log.e(TAG,"ERREUR JSON"+e.getMessage());

               }
           }
       };


        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"ERREUR HTTP :"+error.getMessage());
                etMatricule.setText("");
                etMDP.setText("");

            }

        } ;


        JsonObjectRequest requete = new JsonObjectRequest(Request.Method.GET,url,null,ecouteurReponse,ecouteurErreur);
        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);

/*
       Visiteur visiteur = modele.seConnecter(etMatricule.getText().toString(), etMDP.getText().toString());

        if (visiteur != null) {
            Session.ouvrir(visiteur);
            Intent intentionEnvoyer = new Intent(getApplicationContext(), MenuRvActivity.class);
            startActivity(intentionEnvoyer);
        } else {

            Toast.makeText(this, "Matricule ou mot de passe incorrect. Recommencez...", Toast.LENGTH_LONG).show();

            etMatricule.setText("");
            etMDP.setText("");

        }
        */
    }


    public void annule(View vue) {
        etMatricule.setText("");
        etMDP.setText("");
    }



}