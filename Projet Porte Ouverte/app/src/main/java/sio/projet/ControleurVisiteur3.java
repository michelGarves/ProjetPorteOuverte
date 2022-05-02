package sio.projet;

import android.content.Intent;
import android.os.Bundle;
import plum.widget.ComboDialog;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ControleurVisiteur3 extends AppCompatActivity implements View.OnClickListener, ComboDialog.OnClickComboDialogListener{

    String valueSpe = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiteur3);
        TextView txtViewSpe = (TextView)findViewById(R.id.spe);
        final CharSequence[] items = {"Commerce international",
                "Commerce internet",
                "Finance"};
        final CharSequence[] values = {"1","2","3"};

        ComboDialog comboEtude = new ComboDialog( "Choisir votre spécialisation",
                items,
                values,
                txtViewSpe,
                this );
        comboEtude.setOnClickComboDialogListener(this);
        Button bouton = (Button) findViewById(R.id.confirmSpe);
        Button retour = (Button) findViewById(R.id.retour);
        retour.setOnClickListener(this);
        bouton.setOnClickListener(this);
    }



    @Override
    public void onClickComboDialog(ComboDialog comboDialog) {
        valueSpe = (String) comboDialog.item(comboDialog.getIndexSelected());
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.confirmSpe:
            if (!valueSpe.equals("")) {
                Visiteur newVisiteur = new Visiteur();
                newVisiteur.nom = getIntent().getStringExtra("nom");
                newVisiteur.prenom = getIntent().getStringExtra("prenom");
                newVisiteur.baccalaureat = getIntent().getStringExtra("etude");
                newVisiteur.etablissement = getIntent().getStringExtra("info");
                newVisiteur.specialite = valueSpe;
                newVisiteur.telephone = Integer.parseInt(getIntent().getStringExtra("telephone"));
                newVisiteur.avis = 0;

                PorteOuverteSQLLite bd = new PorteOuverteSQLLite(this);

                bd.putVisiteur(newVisiteur);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Visiteur " + newVisiteur.nom + " " + newVisiteur.prenom + " ajouté(e) avec succès" ,
                        Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Veuillez remplir correctement le(s) champ(s) ",
                        Toast.LENGTH_SHORT);
                toast.show();

            }
            break;
            case R.id.retour:
                finish();
                break;
        }

    }
}
