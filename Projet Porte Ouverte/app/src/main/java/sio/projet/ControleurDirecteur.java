package sio.projet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

import plum.widget.ComboDialog;
import plum.widget.MessageDialog;

public class ControleurDirecteur extends AppCompatActivity implements AdapterView.OnItemClickListener,
        View.OnClickListener,
        MessageDialog.OnClickMessageDialogListener,
        ComboDialog.OnClickComboDialogListener,
        AdapterView.OnItemSelectedListener {
    VisiteurArrayAdapter arrayAdapterVisiteur;

    private ListView lv;
    Visiteur visiteur;
    PorteOuverteSQLLite bd = new PorteOuverteSQLLite(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directeur);


        Spinner spinnerEtude = (Spinner) findViewById(R.id.spinner1);
        Spinner spinnerSpe = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapterSpinnerEtude = ArrayAdapter.createFromResource(this,
                R.array.filtreEtude, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterSpinnerSpe = ArrayAdapter.createFromResource(this,
                R.array.filtreSpecialite, android.R.layout.simple_spinner_item);


        spinnerEtude.setAdapter(adapterSpinnerEtude);
        spinnerSpe.setAdapter(adapterSpinnerSpe);



        lv = (ListView) findViewById(R.id.list);
        lv.setOnItemClickListener(this);

        Button bouton = (Button) findViewById(R.id.accueil);
        bouton.setOnClickListener(this);

        spinnerEtude.setOnItemSelectedListener(this);
        spinnerSpe.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        visiteur = (Visiteur) adapterView.getItemAtPosition(i);


        MessageDialog.show(this,
                "Modifier votre avis à propos de : " + visiteur.nom + " " + visiteur.prenom,
                "Appeler","Changer", this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.accueil:
            finish();
            break;
        }
    }

    @Override
    public void onClickMessageDialog(MessageDialog messageDialog, char c) {
        switch (c) {
            case 'G':
                String telToString = Integer.toString(visiteur.telephone);
                String tel = "tel:0" + telToString;
                Intent intentTel = new Intent(Intent.ACTION_DIAL);
                intentTel.setData(Uri.parse(tel));
                startActivity(intentTel);
                break;
            case 'D':
                final CharSequence[] items = {"Neutre","Favorable","Défavorable"};
                final CharSequence[] values = {"1","2","3"};


                ComboDialog cDialog = new ComboDialog( "Avis : ",
                        items,
                        values,
                        null,
                        this );
                cDialog.show();
                cDialog.setOnClickComboDialogListener(this);
                break;
        }
    }

    @Override
    public void onClickComboDialog(ComboDialog comboDialog) {

        String value = (String) comboDialog.value(comboDialog.getIndexSelected());

        switch(value){
            case "1" :
                visiteur.avis = 0;
                bd.updateAvisVisiteur(visiteur);

                break;

            case "2" :
                visiteur.avis = 1;
                bd.updateAvisVisiteur(visiteur);

                break;
            case "3" :
                visiteur.avis = 2;
                bd.updateAvisVisiteur(visiteur);

                break;

        }
        arrayAdapterVisiteur.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        String etude = spinner1.getSelectedItem().toString();
        String specialite = spinner2.getSelectedItem().toString();

                ArrayList<Visiteur> lesVisiteurs = bd.EtudeSpeFiltre(etude, specialite);

        arrayAdapterVisiteur =
                new VisiteurArrayAdapter(this, lesVisiteurs);

        //-- L'adaptateur Sert de source de données au ListView
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(arrayAdapterVisiteur );
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}
