package ua.pt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Info extends Activity {
	private TextView tvc1;
	private TextView tvgp1;
	private TextView tvtc1;
	
	private Adapter myadapter;
	private TextView tvnome2;
	private TextView tvmorada2;
	
	private TextView tvc2;
	private TextView tvgp2;
	private TextView tvtc2;
	
	private long id;
	private Restaurante r;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        id = getIntent().getIntExtra("id", 0);
        
        setContentView(R.layout.info);
        
        tvc1 = (TextView) findViewById(R.id.tvc1);
        tvgp1 = (TextView) findViewById(R.id.tvgp1);
        tvtc1 = (TextView) findViewById(R.id.tvtc1);
        
        tvnome2 = (TextView) findViewById(R.id.tvnome2);
        tvmorada2 = (TextView) findViewById(R.id.tvmorada2);
        tvc2 = (TextView) findViewById(R.id.tvc2);
        tvgp2 = (TextView) findViewById(R.id.tvgp2);
        tvtc2 = (TextView) findViewById(R.id.tvtc2);
        
        Button anterior = (Button) findViewById(R.id.infoAnterior);
        Button trajecto = (Button) findViewById(R.id.bTrajecto);
        Button comentarios = (Button) findViewById(R.id.bComentarios);
        Button contactos = (Button) findViewById(R.id.bContactos);
        Button classificar = (Button) findViewById(R.id.bClassificacao);
       
        myadapter = new Adapter(this.getApplicationContext());
        myadapter.open();
        
        r = myadapter.getRestbyID(id);
        
        tvnome2.setText(r.getName());
        tvmorada2.setText(r.getAddress());
       
        if(r.getClassificacao() != 0.0){
        	tvc2.setText(Double.toString(r.getClassificacao()));
        }
        else {
        	tvc2.setText("");
        	tvc1.setText("");
        }
        
        if(r.getGamaPreco() != ""){
        	tvgp2.setText(r.getGamaPreco());
        }
        else {
        	tvgp2.setText("");
        	tvgp1.setText("");
        }
        
        if(r.getTipoComida() != ""){
        	tvtc2.setText(r.getTipoComida());
        }
        else {
        	tvtc2.setText("");
        	tvtc1.setText("");
        }
        
        anterior.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						// nao e necessario iniciar uma nova actividade
						finish();
					}
				});
        
        trajecto.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						
						Intent i = new Intent(Info.this, Trajecto.class);
	                    i.putExtra("id", id); 
						startActivity(i);
						// a actividade nao termina
					}
				});
        
        comentarios.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						
						Intent y = new Intent(Info.this, Comentarios.class);
	                    y.putExtra("id", id); 
						startActivity(y);
						// a actividade nao termina
					}
				});
        
        contactos.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						
						Intent y = new Intent(Info.this, Contactos.class);
	                    y.putExtra("id", id); 
						startActivity(y);
						// a actividade nao termina
					}
				});
        
        classificar.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						
						final CharSequence[] items = {"1", "2", "3", "4", "5"};

						AlertDialog.Builder builder = new AlertDialog.Builder(Info.this);
						builder.setTitle("Escolha uma classifica��o");
						builder.setItems(items, new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog, int item) {
						        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
						        
						        myadapter.open();
						        
						        myadapter.classificar(id, item + 1);
						       
						        dialog.cancel();
						        
						        AlertDialog.Builder b = new AlertDialog.Builder(Info.this);
						        b.setTitle("Informação")
						        	   .setMessage("A classificação foi inserida com sucesso.")
						        	   .setIcon(android.R.drawable.ic_dialog_info)
						               .setCancelable(false)
						               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
						                   public void onClick(DialogInterface dialog, int id) {
						                	   
						                	   tvc2.setText(Double.toString(r.getClassificacao()));
						                	   
						                	   dialog.cancel();
						                   }
						               });
						        
						        AlertDialog a = b.create();
						        a.show();
						        myadapter.close();
						        
						    }
						});
						
						AlertDialog alert = builder.create();
						alert.show();
						
						
					}
				});
        
        myadapter.close();
    }
}
