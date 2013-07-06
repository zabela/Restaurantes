package ua.pt;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Categoria extends Activity {
	private Button ok;
	private Button principal;
	private Button anterior;
	
	private CheckBox tc2;
	private CheckBox gp2;
	private CheckBox c2;
	
	private Spinner tc;
	private Spinner gp;
	private Spinner c;
	
	private String tipo;
	private String gama;
	private String clas;
	
	private String[] tipo_comida;
	private String[] gama_preco;
	private String[] classificacao;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.categoria);
        
        Botoes();
        
        tipo_comida = getResources().getStringArray(R.array.tipo_comida);
    	gama_preco = getResources().getStringArray(R.array.gama_preco);
    	classificacao = getResources().getStringArray(R.array.classificacao);
        
        Filtros();
    }
    
	private void Filtros() {
		// TODO Auto-generated method stub
		
		final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinnerlayout_l, tipo_comida);
		final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinnerlayout_l, gama_preco);
		final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinnerlayout_l, classificacao);
		
		final ArrayAdapter<String> adapterA = new ArrayAdapter<String>(this, R.layout.spinnerlayout_d, tipo_comida);
		final ArrayAdapter<String> adapterB = new ArrayAdapter<String>(this, R.layout.spinnerlayout_d, gama_preco);
		final ArrayAdapter<String> adapterC = new ArrayAdapter<String>(this, R.layout.spinnerlayout_d, classificacao);
		
		tc = (Spinner) findViewById(R.id.categoriaSpinner1);
		gp = (Spinner) findViewById(R.id.categoriaSpinner2);
		c = (Spinner) findViewById(R.id.categoriaSpinner3);
			
		tc.setAdapter(adapter1);
		gp.setAdapter(adapter2);
		c.setAdapter(adapter3);
		
		tc.setEnabled(false);
		gp.setEnabled(false);
		c.setEnabled(false);
		
		tc2 = (CheckBox) findViewById(R.id.cbTipoDeComida);
        gp2 = (CheckBox) findViewById(R.id.cbGamaDePrecos);
        c2 = (CheckBox) findViewById(R.id.cbClassificacao);
		
        tc2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (tc2.isChecked()) {
					
					tc.setEnabled(true);
					tc.setAdapter(adapterA);
				}
				else {
					tc.setAdapter(adapter1);
					tc.setEnabled(false);
				}
			}
		});
		
        gp2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (gp2.isChecked()) {
					
					gp.setEnabled(true);
					gp.setAdapter(adapterB);
				}
				else {
					gp.setAdapter(adapter2);
					gp.setEnabled(false);
				}
			}
		});
        
        c2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (c2.isChecked()) {
					
					c.setEnabled(true);
					c.setAdapter(adapterC);
				}
				else {
					c.setAdapter(adapter3);
					c.setEnabled(false);
				}
			}
		});
	}
	
	private void Botoes() {
		// TODO Auto-generated method stub
		
		principal = (Button) findViewById(R.id.categoriaCasa);
        anterior = (Button) findViewById(R.id.categoriaAnterior);
        ok = (Button) findViewById(R.id.categoriaOk);
        
        principal.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						startActivity(new Intent(Categoria.this, Restaurantes.class));
						finish();
					}
				});
        
        anterior.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						
						Intent i = new Intent(Categoria.this, Pesquisar.class);
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
		
		tipo = "";
		gama = "";
		clas = "";
		
		if (!tc2.isChecked() && !gp2.isChecked() && !c2.isChecked()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(Categoria.this);
	        builder.setTitle("Aviso")
	        	   .setMessage("Nenhum dos filtros se encontra seleccionado. Deseja continuar mesmo assim?")
	        	   .setIcon(android.R.drawable.ic_dialog_alert)
	               .setCancelable(false)
	               .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   	
	               			Intent i = new Intent(Categoria.this, Mapa.class);
	               		
	               			i.putExtra("tipo", tipo);
	               			i.putExtra("gama", gama);
	               			i.putExtra("clas", clas);
	               			i.putExtra("pesquisa", 'c');
	               		
	               			startActivity(i);
	               			finish();	
	                   }
	               })
	               .setNegativeButton("Não", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();
	                   }
	               });
	        AlertDialog alert = builder.create();
	        alert.show();
	        return;
		}
		
		if (tc2.isChecked()) 
			tipo = tc.getSelectedItem().toString();
		
		if (gp2.isChecked())
			gama = gp.getSelectedItem().toString();
		
		if (c2.isChecked())
			clas = c.getSelectedItem().toString();
		
		Intent i = new Intent(Categoria.this, Mapa.class);
		
		i.putExtra("tipo", tipo);
		i.putExtra("gama", gama);
		i.putExtra("clas", clas);
		i.putExtra("pesquisa", 'c');
		
		startActivity(i);
		finish();		
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			
			Intent i = new Intent(Categoria.this, Pesquisar.class);
			startActivity(i);
			finish();
       	}
    	
       	return super.onKeyDown(keyCode, event);
    }
}
