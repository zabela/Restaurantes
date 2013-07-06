package ua.pt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Contactos extends Activity {
	private Adapter myadapter;
	private TextView telefone1;
	private TextView email1;
	
	private Button ligar;
	private TextView telefone2;
	private TextView email2;
	
	private long id;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contactos);   
        
        myadapter = new Adapter(this.getApplicationContext());
        myadapter.open();

        telefone1 = (TextView) findViewById(R.id.tvtelefone1);
        email1 = (TextView) findViewById(R.id.tvemail1);
        
        telefone2 = (TextView) findViewById(R.id.tvtelefone2);
        email2 = (TextView) findViewById(R.id.tvemail2);
        
        ligar = (Button) findViewById(R.id.bTelefone);
        
        id = getIntent().getLongExtra("id", 0);
        Restaurante r = myadapter.getRestbyID(id);
        
        if (r.getTipoContacto() == 0){
        	telefone2.setText(""); 
        	email1.setText("");
        	email2.setText("");
        	telefone1.setText("");
        	ligar.setBackgroundColor(Color.TRANSPARENT);
        	
        }
        
        if (r.getTipoContacto() == 1){
        	telefone2.setText(r.getValorContacto()); // get do numero de telefone
        	email1.setText("");
        	email2.setText("");
        	
        	ligar.setOnClickListener(
            		new Button.OnClickListener() {
    					public void onClick(View v) {
    						Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telefone2.getText().toString()));
    						startActivity(intent);
    					}
    				});
        }
        
        if (r.getTipoContacto() == 2){
        	telefone1.setText("");
        	telefone2.setText("");
        	ligar.setBackgroundColor(Color.TRANSPARENT);
        	email2.setText(r.getValorContacto());  // get do email
        	
        	Linkify.addLinks(email2, Linkify.WEB_URLS|Linkify.EMAIL_ADDRESSES);
        }
        
        if (r.getTipoContacto() == 3){
        	
        	String []var = r.getValorContacto().split("\n");
        	
        	telefone2.setText(var[0]); // get do numero de telefone
        	email2.setText(var[1]);  // get do email
        	
        	Linkify.addLinks(email2, Linkify.WEB_URLS|Linkify.EMAIL_ADDRESSES);
        	
        	ligar.setOnClickListener(
            		new Button.OnClickListener() {
    					public void onClick(View v) {
    						Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telefone2.getText().toString()));
    						startActivity(intent);
    					}
    				});
        }
        
        Linkify.addLinks(email2, Linkify.WEB_URLS|Linkify.EMAIL_ADDRESSES);
        
        myadapter.close();
        
        Button anterior = (Button) findViewById(R.id.contactosAnterior);
        
        anterior.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						// nao e necessario iniciar uma nova actividade
						finish();
					}
				});
    }
}
