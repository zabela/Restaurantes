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

public class Comentar extends Activity {
    private EditText comment;
    private long id;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.comentar);
        
        id = getIntent().getLongExtra("id", 0);
        
        comment = (EditText) findViewById(R.id.etcomentario);
        
        Button anterior = (Button) findViewById(R.id.comentarAnterior);
        Button submete = (Button) findViewById(R.id.bSubmeter);
        
        anterior.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) { 
						Intent i = new Intent(Comentar.this, Comentarios.class);
						i.putExtra("id", id);
						finish();
					}
				});
        
        comment.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                	// Perform action on key press
                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                	imm.hideSoftInputFromWindow(comment.getWindowToken(), 0);
                  return true;
                }
                return false;
            }
        });
        
        submete.setOnClickListener(
        		new Button.OnClickListener() { 
					public void onClick(View v) { 
						cSubmetido();
					}

					private void cSubmetido() {
						
						if (comment.getText().toString().equals("") || comment.getText().toString().equals(" ")) {
							AlertDialog.Builder builder = new AlertDialog.Builder(Comentar.this);
					        builder.setTitle("Aviso")
					        	   .setMessage("Não é possível introduzir comentários sem conteúdo.")
					        	   .setIcon(android.R.drawable.ic_dialog_alert)
					               .setCancelable(false)
					               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
					                   public void onClick(DialogInterface dialog, int id1) {
					                	   dialog.cancel();
					                   }
					               });
					        AlertDialog alert = builder.create();
					        alert.show();
					        return;
						} 
						
						Adapter myadapter = new Adapter(Comentar.this);
						myadapter.open();
				        
						myadapter.comentar(id, comment.getText().toString());
				        
						myadapter.close();
				        
						AlertDialog.Builder builder = new AlertDialog.Builder(Comentar.this);
				        builder.setTitle("Informação")
				        	   .setMessage("O comentário foi submetido com sucesso.")
				        	   .setIcon(android.R.drawable.ic_dialog_info)
				               .setCancelable(false)
				               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
				                   public void onClick(DialogInterface dialog, int id) {
				                	   dialog.cancel();
				                	   // nao e necessario iniciar uma nova actividade
				                	   finish();
				                   }
				               });
				        AlertDialog alert = builder.create();
				        alert.show();
						
					}
				});
    }
}