package sio.projet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ControleurVisiteur1 extends AppCompatActivity implements View.OnClickListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiteur1);

        Button confirmerNomPrenom = (Button) findViewById(R.id.confirmNomPrenom);
        Button accueil = (Button) findViewById(R.id.accueil);
        confirmerNomPrenom.setOnClickListener(this);
        accueil.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.confirmNomPrenom:
            EditText edtNom = (EditText) findViewById(R.id.edtNom);
            EditText edtPrenom = (EditText) findViewById(R.id.edtPrenom);
            EditText edtTel = (EditText) findViewById(R.id.edtTelephone);
            String txtTel = edtTel.getText().toString();
            String txtNom = edtNom.getText().toString();
            String txtPrenom = edtPrenom.getText().toString();
            boolean nomIsSafe = securityCheck(txtNom);
            boolean prenomIsSafe = securityCheck(txtPrenom);
            boolean telOk = checkPhone(txtTel);
            if(nomIsSafe && prenomIsSafe && telOk) {
                if (!txtNom.equals("") && !txtPrenom.equals("")) {

                        Intent intentV1 = new Intent(this, ControleurVisiteur2.class);
                        intentV1.putExtra("nom", txtNom);
                        intentV1.putExtra("prenom", txtPrenom);
                        intentV1.putExtra("telephone" , txtTel);
                        edtTel.setText("");
                        edtNom.setText("");
                        edtPrenom.setText("");
                        startActivity(intentV1);

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Informations non valides",
                            Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
            else{
                if(!nomIsSafe || !prenomIsSafe){
                    Toast toastNP = Toast.makeText(getApplicationContext(),
                            "Caractère(s) dangereu(x) détecté(s)",
                            Toast.LENGTH_SHORT);
                    toastNP.show();
                }
                else if(!telOk){
                    Toast toastTel = Toast.makeText(getApplicationContext(),
                            "Veuillez entrer un numéro de téléphone valide",
                            Toast.LENGTH_SHORT);
                    toastTel.show();
                }
            }
            break;
            case R.id.accueil:
                finish();

        }
    }

    private boolean checkPhone(String txtTel) {
        boolean check = true;
        if(txtTel.length() > 0) {
            if (txtTel.charAt(0) != '0') {
                check = false;
            }
        }
        if(txtTel.length() != 10 ){
            check = false;
        }
        return check;
    }

    public boolean securityCheck(String text){
        String[] danger = {"\'","\"","*","&", ";", "!", "=", "+", "{", "}", "(", ")", "@"};
        boolean isSafe = true;
        for (int i = 0; i < text.length(); i++){
            for (int j = 0; j < danger.length; j++){
                String letter = String.valueOf(text.charAt(i));
                if(letter.equals(danger[j])){
                    isSafe = false;
                }
            }
        }
        return isSafe;
    }
}
