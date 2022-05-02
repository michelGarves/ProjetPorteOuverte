package sio.projet;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PorteOuverteSQLLite extends SQLiteOpenHelper{
    //--- Base de données

    private static String DATABASE_NAME = "PorteOuverte.db";
    private static int DATABASE_VERSION = 1;

    SQLiteDatabase db = null;
    //--- Table Visiteur

    private static String TABLE_VISITEUR = "visiteur";

    private static String COLUMN_ID = "id";
    private static String COLUMN_NOM = "nom";
    private static String COLUMN_PRENOM = "prenom";
    private static String COLUMN_BACCALAUREAT = "baccalaureat";
    private static String COLUMN_ETABLISSEMENT = "etablissement";
    private static String COLUMN_SPECIALITE = "specialite";
    private static String COLUMN_TELEPHONE = "telephone";
    private static String COLUMN_AVIS = "avis";

    private static String COLUMNS_DEFINITION =
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOM + " TEXT,"
                    + COLUMN_PRENOM + " TEXT,"
                    + COLUMN_BACCALAUREAT + " TEXT,"
                    + COLUMN_ETABLISSEMENT + " TEXT,"
                    + COLUMN_SPECIALITE + " TEXT,"
                    + COLUMN_TELEPHONE + " TEXT,"
                    + COLUMN_AVIS + " INTEGER";


    /*
     * constructeur : création ou déclaration de la base de données
     *
     */
    public PorteOuverteSQLLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * Appelé lorsque la base est créée pour la première fois
     *
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //--- create table Visiteur ---
        String sqlcreate = "create table " + TABLE_VISITEUR
                + " (" + COLUMNS_DEFINITION + ");";

        db.execSQL (sqlcreate);
    }

    /*
     * Appelé lorsque la base a besoin d'être modifiée
     * Il suffit de modifier DATABASE_VERSION !
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITEUR + ";");
        onCreate(db);
    }

    /*
     * retourne un ArrayList contenant les données de la table Visiteur
     */
    public ArrayList <Visiteur> getListeVisiteur(){

        ArrayList<Visiteur> ar = new ArrayList<Visiteur>();

        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("getVisiteur","db ok");
        String columns[]={COLUMN_ID, COLUMN_NOM, COLUMN_PRENOM, COLUMN_BACCALAUREAT,
                COLUMN_ETABLISSEMENT, COLUMN_SPECIALITE, COLUMN_TELEPHONE, COLUMN_AVIS
        };
        Cursor cursor = db.query(TABLE_VISITEUR, columns ,null,null,null,null,COLUMN_NOM);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Visiteur visiteur = new Visiteur();

            visiteur.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            visiteur.nom = cursor.getString(cursor.getColumnIndex(COLUMN_NOM));
            visiteur.prenom = cursor.getString(cursor.getColumnIndex(COLUMN_PRENOM));
            visiteur.baccalaureat = cursor.getString(cursor.getColumnIndex(COLUMN_BACCALAUREAT));
            visiteur.etablissement = cursor.getString(cursor.getColumnIndex(COLUMN_ETABLISSEMENT));
            visiteur.specialite = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIALITE));
            visiteur.telephone = cursor.getInt(cursor.getColumnIndex(COLUMN_TELEPHONE));
            visiteur.avis = cursor.getInt(cursor.getColumnIndex(COLUMN_AVIS));

            ar.add(visiteur);

            cursor.moveToNext();
        }

        return ar;
    }

    /*
     * Ajoute un visiteur
     * retourne son identifiant
     */
    public long putVisiteur( Visiteur visiteur){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM, visiteur.nom);
        values.put(COLUMN_PRENOM, visiteur.prenom);
        values.put(COLUMN_BACCALAUREAT, visiteur.baccalaureat);
        values.put(COLUMN_ETABLISSEMENT, visiteur.etablissement);
        values.put(COLUMN_SPECIALITE, visiteur.specialite);
        values.put(COLUMN_TELEPHONE, visiteur.telephone);
        values.put(COLUMN_AVIS, visiteur.avis);
        SQLiteDatabase db = this.getWritableDatabase();
        long insertId = db.insert(TABLE_VISITEUR, null,values);

        return insertId;
    }

    public long updateAvisVisiteur(Visiteur visiteur) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM, visiteur.nom);
        values.put(COLUMN_PRENOM, visiteur.prenom);
        values.put(COLUMN_BACCALAUREAT, visiteur.baccalaureat);
        values.put(COLUMN_ETABLISSEMENT, visiteur.etablissement);
        values.put(COLUMN_SPECIALITE, visiteur.specialite);
        values.put(COLUMN_TELEPHONE, visiteur.telephone);
        values.put(COLUMN_AVIS, visiteur.avis);
        SQLiteDatabase db = this.getWritableDatabase();
        long updateId = db.update(TABLE_VISITEUR, values, COLUMN_ID + "=" + visiteur.id, null);

        return updateId;
    }

    public ArrayList<Visiteur> EtudeSpeFiltre(String etude, String specialite ){
        ArrayList<Visiteur> ar = new ArrayList<Visiteur>();

        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("getVisiteur","db ok");
        String columns[]={COLUMN_ID, COLUMN_NOM, COLUMN_PRENOM, COLUMN_BACCALAUREAT,
                COLUMN_ETABLISSEMENT, COLUMN_SPECIALITE, COLUMN_TELEPHONE, COLUMN_AVIS
        };
        Cursor cursor = db.query(TABLE_VISITEUR, columns ,null,null,null,null,COLUMN_NOM);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Visiteur visiteur = new Visiteur();

            visiteur.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            visiteur.nom = cursor.getString(cursor.getColumnIndex(COLUMN_NOM));
            visiteur.prenom = cursor.getString(cursor.getColumnIndex(COLUMN_PRENOM));
            visiteur.baccalaureat = cursor.getString(cursor.getColumnIndex(COLUMN_BACCALAUREAT));
            visiteur.etablissement = cursor.getString(cursor.getColumnIndex(COLUMN_ETABLISSEMENT));
            visiteur.specialite = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIALITE));
            visiteur.telephone = cursor.getInt(cursor.getColumnIndex(COLUMN_TELEPHONE));
            visiteur.avis = cursor.getInt(cursor.getColumnIndex(COLUMN_AVIS));
            if( (visiteur.specialite.equals(specialite) && visiteur.baccalaureat.equals(etude)) ||
                    (etude.equals("Tous") && specialite.equals("Tous")) ||
                    (etude.equals("Tous") && visiteur.specialite.equals(specialite)) ||
                    (visiteur.baccalaureat.equals(etude) && specialite.equals("Tous"))
            ) {
                ar.add(visiteur);
            }

            cursor.moveToNext();
        }

        return ar;
    }

}