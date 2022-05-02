package sio.projet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonV1=(Button) findViewById(R.id.btnVisiteur1);
        Button buttonDirecteur=(Button) findViewById(R.id.btnDirecteur1);
        Button reset = (Button) findViewById(R.id.resetBDD);
        buttonV1.setOnClickListener(this);
        buttonDirecteur.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnVisiteur1 :
                Intent intentV1 = new Intent(this,ControleurVisiteur1.class);
                startActivity(intentV1);
                break;
            case R.id.btnDirecteur1 :
                Intent intentD = new Intent(this,ControleurDirecteur.class);
                startActivity(intentD);
                break;
            case R.id.resetBDD :
                PorteOuverteSQLLite bd = new PorteOuverteSQLLite(this);
                SQLiteDatabase db = bd.getWritableDatabase();
                int version = db.getVersion();
                bd.onUpgrade(db, version, version+1 );
                Toast toast = Toast.makeText(getApplicationContext(),
                    "Base de données réinitialisée",
                    Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }
}
