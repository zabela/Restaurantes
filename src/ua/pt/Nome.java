package ua.pt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
 
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class Nome extends Activity {
	private Button ok;
	private Button principal;
	private Button anterior;
	
	private EditText nome;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.nome);
        
        Botoes();
        
        nome = (EditText) findViewById(R.id.etNome);
        
        nome.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                	// Perform action on key press
                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                	imm.hideSoftInputFromWindow(nome.getWindowToken(), 0);
                  return true;
                }
                return false;
            }
        });
    }
	
	private void Botoes() {
		// TODO Auto-generated method stub
		
		principal = (Button) findViewById(R.id.nomeCasa);
        anterior = (Button) findViewById(R.id.nomeAnterior);
        ok = (Button) findViewById(R.id.nomeOk);
        
        principal.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						startActivity(new Intent(Nome.this, Restaurantes.class));
						finish();
					}
		});
        
        anterior.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						
						Intent i = new Intent(Nome.this, Pesquisar.class);
						
						startActivity(i);
						finish();
					}
		});
        
        ok.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						nextActivity();
					}
        		});
	}
	
	private void nextActivity() {
		// TODO Auto-generated method stub
		
		if (nome.getText().toString().equals(" ") || nome.getText().toString().equals("")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(Nome.this);
	        builder.setTitle("Aviso")
	        	   .setMessage("É necessário introduzir um nome.")
	        	   .setIcon(android.R.drawable.ic_dialog_alert)
	               .setCancelable(false)
	               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   dialog.cancel();
	                   }
	               });
	        AlertDialog alert = builder.create();
	        alert.show();
	        return;
		}
		
		Intent i = new Intent(Nome.this, Mapa.class);
		
		i.putExtra("nome", nome.getText().toString());
		i.putExtra("pesquisa", 'n');
		
		startActivity(i);
		finish();
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			
			Intent i = new Intent(Nome.this, Pesquisar.class);
			startActivity(i);
			finish();
       	}
    	
       	return super.onKeyDown(keyCode, event);
    }
}
