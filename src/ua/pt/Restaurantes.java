package ua.pt;

import android.app.Activity;import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class Restaurantes extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        
        Button pesquisar = (Button) findViewById(R.id.bPesquisar);
        Button ajuda = (Button) findViewById(R.id.bAjuda);
        Button sobre = (Button) findViewById(R.id.bSobre);
        Button sair = (Button) findViewById(R.id.bSair);
        
        sair.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						AlertDialog.Builder builder = new AlertDialog.Builder(Restaurantes.this);
				        builder.setTitle("Aviso")
				        	   .setMessage("Tem a certeza que deseja abandonar a aplicacção?")
				        	   .setIcon(android.R.drawable.ic_dialog_alert)
				               .setCancelable(false)
				               .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				                   public void onClick(DialogInterface dialog, int id) {
				                        Restaurantes.this.finish();
				                   }
				               })
				               .setNegativeButton("Não", new DialogInterface.OnClickListener() {
				                   public void onClick(DialogInterface dialog, int id) {
				                        dialog.cancel();
				                   }
				               });
				        AlertDialog alert = builder.create();
				        alert.show();
					}
				});
        
        sobre.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(Restaurantes.this, Sobre.class);
						startActivity(i);
						finish();
					}
				});
        
        ajuda.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(Restaurantes.this, Ajuda.class);
						startActivity(i);
						finish();
					}
				});
        
        pesquisar.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(Restaurantes.this , Local.class);
						startActivity(i);
						finish();
					}
				});
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(Restaurantes.this);
	        builder.setTitle("Aviso")
	        	   .setMessage("Tem a certeza que deseja abandonar a aplicação?")
	        	   .setIcon(android.R.drawable.ic_dialog_alert)
	               .setCancelable(false)
	               .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                        Restaurantes.this.finish();
	                   }
	               })
	               .setNegativeButton("Não", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();
	                   }
	               });
	        AlertDialog alert = builder.create();
	        alert.show();
       	}
    	
       	return super.onKeyDown(keyCode, event);
    }
}