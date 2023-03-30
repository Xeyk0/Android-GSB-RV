package fr.gsb;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    public void valide(View vue) throws UnsupportedEncodingException {

        Visiteur visiteur = modele.seConnecter(etMatricule.getText().toString(), etMDP.getText().toString());

       /* String matricule= URLEncoder.encode("a131","UTF-8");
        String url = String.format("http://127.0.0.1:5000/%s/%s");

        Response.Listener<String> ecouteurReponse = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        };
        Response.ErrorListener ecouteurErreur= new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GSB-RV","ERREUR HTTP :" +error.getMessage());
            }
        };
        StringRequest requete = new StringRequest( Request.Method.GET,url,ecouteurReponse,ecouteurErreur);

        RequestQueue fileRequetes= Volley.newRequestQueue(this);
        fileRequetes.add(requete);
*/
        if (visiteur != null) {
            Session.ouvrir(visiteur);
            Toast.makeText(this, "Connexion réussie. Bienvenue " + visiteur.getPrenom() + " " + visiteur.getNom(), Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(this, "Matricule ou mot de passe incorrect. Recommencez...", Toast.LENGTH_LONG).show();

            etMatricule.setText("");
            etMDP.setText("");

        }
    }


    public void annule(View vue) {
        etMatricule.setText("");
        etMDP.setText("");
    }



}