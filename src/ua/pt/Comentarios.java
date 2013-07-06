package ua.pt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Comentarios extends Activity {
    /** Called when the activity is first created. */
	private Adapter myadapter;
	private TextView comentarios;
	private long id;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.comentarios);
        
        comentarios = (TextView) findViewById(R.id.tvcomentarios);
        
        myadapter = new Adapter(this.getApplicationContext());
        myadapter.open();
        
        id = getIntent().getLongExtra("id", 0);
        Restaurante r = myadapter.getRestbyID(id);
        
        String var = r.getComentario();
        String []c = var.split("\n");
        String comments = "";
        
        if (c[0].equals("") && c.length == 1)
        	comments = "";
        
        else{
        	
	        for (int i = 0; i < c.length; i++) {
	        	
	        	comments = comments + "Comentário " + (i + 1) + ": " + c[i] + "\n\n";
	        	
	        }
        }
        comentarios.setText(comments);
        
        Button anterior = (Button) findViewById(R.id.comentariosAnterior);
        Button comentar = (Button) findViewById(R.id.bComentar);
        
        myadapter.close();
        
        anterior.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						// n�o � necess�rio iniciar uma nova actividade
						finish();
					}
				});
        
        comentar.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						Intent i = new Intent(Comentarios.this, Comentar.class);
						i.putExtra("id", id);
						startActivity(i);
						finish();
					}
				});
        
    }
}
