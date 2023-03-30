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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;



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


       /* String url = "http://127.0.0.1/visiteurs/" + etMatricule.getText().toString() + "/" + etMDP.getText().toString();

        Response.Listener<JSONObject> ecouteurReponse = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Visiteur unVisiteur = new Visiteur();
                    unVisiteur.setMatricule(response.getString("matricule"));
                    unVisiteur.setNom(response.getString("nom"));
                    unVisiteur.setPrenom(response.getString("prenom"));
                    Session.ouvrir(visiteur);
                    Log.v(TAG, "JSON:OK");

                } catch (JSONException e) {
                    Log.e(TAG, "JSON :" + e.getMessage());
                }
            }
        };
        Response.ErrorListener ecouteurError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Erreur HTTP :" + " " + error.getMessage());
            }
        };

        JsonObjectRequest requete = new JsonObjectRequest(Request.Method.GET, "http://127.0.0.1/visiteurs/" + etMatricule.getText().toString() + "/" + etMDP.getText().toString(), null, ecouteurReponse, ecouteurError);
        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);

*/
        if (visiteur != null) {
            Session.ouvrir(visiteur);
            Intent intentionEnvoyer = new Intent(getApplicationContext(), MenuRvActivity.class);
            startActivity(intentionEnvoyer);

        } else {

            Toast.makeText(this, "Matricule ou mot de passe incorrect. Recommencez...", Toast.LENGTH_LONG).show();

            etMatricule.setText("");
            etMDP.setText("");

        }
    }

    public void annule (View vue){
        etMatricule.setText("");
        etMDP.setText("");
    }
}