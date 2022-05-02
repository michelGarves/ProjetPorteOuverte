package sio.projet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class VisiteurArrayAdapter extends ArrayAdapter<Visiteur> {
    //ArrayList<Visiteur> lesVisiteurs;

    public VisiteurArrayAdapter(@NonNull Context context, ArrayList<Visiteur> list) {
        super(context, 0, list);
        //lesVisiteurs = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       // Visiteur visiteur = lesVisiteurs.get(position);
        Visiteur visiteur = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.visiteur_item,
                    parent,
                    false);
        }
        ImageView imgVisiteur = (ImageView) convertView.findViewById(R.id.imgViewVisiteur);
        switch(visiteur.avis){
            case 0 :
                imgVisiteur.setImageResource(R.drawable.neutre);
                break;

            case 1 :
                imgVisiteur.setImageResource(R.drawable.favorable);
                break;

            case 2 :
                imgVisiteur.setImageResource(R.drawable.defavorable);
                break;
        }

        TextView txtNom = (TextView) convertView.findViewById(R.id.txtNom);
        TextView txtPrenom = (TextView) convertView.findViewById(R.id.txtPrenom);
        TextView txtEtude = (TextView) convertView.findViewById(R.id.txtEtude);
        TextView txtSpecialite = (TextView) convertView.findViewById(R.id.txtSpecialite);

        txtNom.setText(visiteur.nom);
        txtPrenom.setText(visiteur.prenom);
        txtEtude.setText(visiteur.baccalaureat);
        txtSpecialite.setText(visiteur.specialite);
        return convertView;
    }

}
