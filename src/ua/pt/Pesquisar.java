package ua.pt;

import android.app.Activity;
import android.content.Intent;
//import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class Pesquisar extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pesquisar);
        
        Button principal = (Button) findViewById(R.id.pesquisarCasa);
        Button anterior = (Button) findViewById(R.id.pesquisarAnterior);
        Button porNome = (Button) findViewById(R.id.bPorNome);
        Button porCat = (Button) findViewById(R.id.bPorCategoria);
        
        principal.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(Pesquisar.this, Restaurantes.class);
						startActivity(i);
						finish();
					}
				});
        
        anterior.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(Pesquisar.this, Local.class);
						startActivity(i);
						finish();
					}
				});
        
        porNome.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(Pesquisar.this, Nome.class);
						startActivity(i);
						finish();
					}
				});
        
        porCat.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(Pesquisar.this, Categoria.class);
						startActivity(i);
						finish();
					}
				});
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    		Intent i = new Intent(Pesquisar.this, Local.class);
			startActivity(i);
			finish();
       	}
    	
       	return super.onKeyDown(keyCode, event);
    }
}
