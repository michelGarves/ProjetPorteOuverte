package sio.projet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import plum.widget.ComboDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class ControleurVisiteur2 extends AppCompatActivity implements View.OnClickListener, ComboDialog.OnClickComboDialogListener {
    String valueEtude = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiteur2);

        TextView txtViewEtude = (TextView)findViewById(R.id.etude);

        final CharSequence[] items = {"Bac général",
                "Bac technologique",
                "Bac professionnel",
                "Université",
                "Autre"};
        final CharSequence[] values = {"1","2","3","4","5"};

        ComboDialog comboEtude = new ComboDialog( "Choisir vos études",
                items,
                values,
                txtViewEtude,
                this );
        Button bouton = (Button) findViewById(R.id.confirmEtude);
        Button retour = (Button) findViewById(R.id.retour);
        bouton.setOnClickListener(this);
        retour.setOnClickListener(this);
        comboEtude.setOnClickComboDialogListener(this);

    }

    @Override
    public void onClickComboDialog(ComboDialog comboDialog) {
        String value = (String) comboDialog.value( comboDialog.getIndexSelected());
        EditText edtInfo = (EditText) findViewById(R.id.info);
        switch(value){
            case "1":
                edtInfo.setHint("Nom établissement");
                valueEtude ="Bac général";
                break;

            case "2":
                edtInfo.setHint("Nom établissement");
                valueEtude ="Bac technologique";
                break;

            case "3":
                edtInfo.setHint("Nom établissement");
                valueEtude ="Bac professionnel";
                break;

            case "4":
                edtInfo.setHint("Nom établissement");
                valueEtude ="Université";
                break;

            case "5":
                edtInfo.setHint("Autre à préciser");
                valueEtude = "Autre";
                break;
        }
    }

    @Override
    public void onClick(View view) {
        EditText edtInfo = (EditText) findViewById(R.id.info);
        String nom = getIntent().getStringExtra("nom");
        String prenom = getIntent().getStringExtra("prenom");
        String tel = getIntent().getStringExtra("telephone");
        String info = edtInfo.getText().toString();
        boolean isInfoSafe = securityCheck(info);
        switch(view.getId()) {
            case R.id.confirmEtude:
                if(isInfoSafe) {
                    if (!valueEtude.equals("") && !edtInfo.getText().toString().equals("")) {
                        Intent intentV2 = new Intent(this, ControleurVisiteur3.class);
                        intentV2.putExtra("nom", nom);
                        intentV2.putExtra("prenom", prenom);
                        intentV2.putExtra("telephone", tel);
                        intentV2.putExtra("etude", valueEtude);
                        intentV2.putExtra("info", info);
                        startActivity(intentV2);
                        finish();

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Information(s) non valide(s)",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Caractère(s) dangereu(x) detecté(s)",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            break;
            case R.id.retour:
                finish();
                break;
        }
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
