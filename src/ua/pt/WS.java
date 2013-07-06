package ua.pt;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;

import java.util.LinkedList;

/*import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.*;*/


public class WS extends Activity {

    Cursor cursor;
    Adapter db;
    private String local;
    private Location location;
    private double raio;

    /*private static final String SOAP_ACTION = "";
    private static final String NAMESPACE = "http://ws/";
    private static final String URL = "http://192.168.1.125:44802/RestWS/WSService?WDSL";
    private static final String METHOD_FINDDALL = "findRests";*/
 

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.local);

    db = new Adapter(this.getApplicationContext());
    db.open();
    db.deleteAllRests();
    
    local = getIntent().getStringExtra("local");
    raio = getIntent().getDoubleExtra("raio", 0.0);
    location = (Location) getIntent().getParcelableExtra("coordinates");
    double lati = location.getLatitude();
    double longi = location.getLongitude();
    
    LinkedList<Restaurante> lista = new LinkedList<Restaurante>();
    lista.add(new Restaurante("Casa do Gonçalo", "Rua de Anadia 3810 Aveiro", 40.626768, -8.650302, "Tradicional", 5, 1, "20,0+", 3, "912345678\ngmartinsribeiro@ua.pt", "Comida muito saborosa cozinhada pelo residente!"));
    lista.add(new Restaurante("Casa da Isa", "Avenida Mário Sacaramento 3810 Aveiro", 40.628510, -8.648424, "Indiana", 8, 2, "2,5-7,5", 3, "9187654321\nisa.povoa@ua.pt", "Misturas esquesitas com muitas especiarias."));
    lista.add(new Restaurante("Casa do Francisco", "Avenida Lourenço Peixinho 3810 Aveiro", 40.642970, -8.645978, "Fast-Food", 15, 5, "2,5-7,5", 3, "910000002\nfranciscobrito@ua.pt", "Comida congelada aquecida no microondas"));
    lista.add(new Restaurante("Ramona", "Rua Eça de Queiroz 12 3810 Aveiro", 40.638289, -8.651192, "Fast-Food", 5, 1, "2,5-7,5", 1, "234427506", "Hamburguers deliciosos!"));
    lista.add(new Restaurante("Planeta Brasil", "Rua de Anadia 43 Santiago 3810 Aveiro", 40.626226, -8.649277, "Brasileira", 5, 1, "2,5-7,5", 1, "234386289", "Carne em abund�ncia!"));
    lista.add(new Restaurante("Convívio", "Rua Aires Barbosa 3810 Aveiro", 40.634263, -8.648623, "Fast-Food", 5, 1, "2,5-7,5", 1, "9122443545", ""));
    lista.add(new Restaurante("Telepizza", "Rua José Rabumba 7 3810 Aveiro", 40.64047, -8.65523, "Italiana", 8, 2, "7,5-12,5", 1, "234381800", ""));
    lista.add(new Restaurante("Proa", "Rua do Gavito 111 3800 Aveiro", 40.643895, -8.65009, "Tradicional", 25, 6, "7,5-12,5", 1, "234423460", ""));
    lista.add(new Restaurante("Pizaria Mundial", "Rua E�a de Queiroz 12 3810 Aveiro", 40.637906, -8.650914, "Fast-Food", 5, 1, "2,5-7,5", 2, "mail", ""));
    lista.add(new Restaurante("Centro Comercial Fórum", "Rua Batalho de Caçadores 3810 Aveiro", 40.640804, -8.651482, "Fast-Food", 5, 1, "2,5-7,5", 0, "", ""));
    lista.add(new Restaurante("Centro Comercial Glicínias", "Rua D. Manuel Barbuda e Vasconcelos 3810-498 Aveiro", 40.627325, -8.644272, "Fast-Food", 5, 1, "2,5-7,5", 0, "", ""));
    lista.add(new Restaurante("O Cortiço", "Rua Comendador Augusto Martins Pereira 3740 Sever do Vouga", 40.73144, -8.36766, "Tradicional", 5, 1, "7,5-12,5", 0, "", "Grande variedade de pratos com comida � moda de Sever."));
    lista.add(new Restaurante("D. Gonçalo", "Rua Comendador Augusto Martins Pereira 3740 Sever do Vouga", 40.72980, -8.36551, "Italiana", 5, 1, "2,5-7,5", 0, "", "Pratos saborosos e baratos. Recomendo a pizza!"));
    lista.add(new Restaurante("Cantina do Crasto", "Campus de Santiago 3810 Aveiro", 40.624467, -8.657045, "Tradicional", 5, 1, "2,5-7,5", 0, "", ""));
    lista.add(new Restaurante("Cantina Santiago", "Campus de Santiago 3810 Aveiro", 40.630623, -8.659309, "Tradicional", 5, 1, "2,5-7,5", 0, "", ""));
    lista.add(new Restaurante("Pizzarte", "Rua Engenheiro Von Haff 27 3810 Aveiro", 40.643878, -8.645029, "Italiana", 5, 1, "20,0+", 1, "234427103", "Boas pizzas!"));
    
    
    for(int i=0; i<lista.size();i++){
    	if(raio != 0.0 && local.equals("actual")){
    	  	  if(calculaDistancia(lati, longi, lista.get(i).getLat(), lista.get(i).getLon())<=raio){
    	      	  db.inserirR(lista.get(i).getName().toString(), lista.get(i).getAddress().toString(), lista.get(i).getLat(), lista.get(i).getLon(), lista.get(i).getTipoComida().toString(), lista.get(i).getSomaVotos(), lista.get(i).getNumVotos(), lista.get(i).getGamaPreco().toString(), lista.get(i).getTipoContacto(), lista.get(i).getValorContacto().toString(), lista.get(i).getComentario().toString());

    	        }
    	    } 
    	else if(raio == 0.0 && !local.equals("actual")){
    		String[] var = lista.get(i).getAddress().split(" ");
    		for (int j = 0; j < var.length; j++) {
    			if(var[j].equalsIgnoreCase(local)){
    				db.inserirR(lista.get(i).getName().toString(), lista.get(i).getAddress().toString(), lista.get(i).getLat(), lista.get(i).getLon(), lista.get(i).getTipoComida().toString(), lista.get(i).getSomaVotos(), lista.get(i).getNumVotos(), lista.get(i).getGamaPreco().toString(), lista.get(i).getTipoContacto(), lista.get(i).getValorContacto().toString(), lista.get(i).getComentario().toString());
    				break;
    			}
    		}
  	    }
    	else if(raio != 0.0 && !local.equals("actual")){
    		String[] var = lista.get(i).getAddress().split(" ");
    		for (int j = 0; j < var.length; j++) {
    			if((calculaDistancia(lati, longi, lista.get(i).getLat(), lista.get(i).getLon())<=raio) && (var[j].equalsIgnoreCase(local))){
    				db.inserirR(lista.get(i).getName().toString(), lista.get(i).getAddress().toString(), lista.get(i).getLat(), lista.get(i).getLon(), lista.get(i).getTipoComida().toString(), lista.get(i).getSomaVotos(), lista.get(i).getNumVotos(), lista.get(i).getGamaPreco().toString(), lista.get(i).getTipoContacto(), lista.get(i).getValorContacto().toString(), lista.get(i).getComentario().toString());
    				break;
    			}
    		}
  	    }
    	if(raio == 0.0 && local.equals("actual")){
  	      	  db.inserirR(lista.get(i).getName().toString(), lista.get(i).getAddress().toString(), lista.get(i).getLat(), lista.get(i).getLon(), lista.get(i).getTipoComida().toString(), lista.get(i).getSomaVotos(), lista.get(i).getNumVotos(), lista.get(i).getGamaPreco().toString(), lista.get(i).getTipoContacto(), lista.get(i).getValorContacto().toString(), lista.get(i).getComentario().toString());
   
  	    }
    	// Quando nao ha restaurantes na base de dados de acordo com as preferencias do local
    	
    	if (db.getRests0().isEmpty()) {
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(WS.this);
	        builder.setTitle("Aviso")
	        	   .setMessage("Não existem restaurantes no local pretendido e/ou na distância pretendida.")
	        	   .setIcon(android.R.drawable.ic_dialog_alert)
	               .setCancelable(false)
	               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   dialog.cancel();
	                	   Intent w = new Intent(WS.this, Local.class);
	                	   startActivity(w);
	                	   finish();
	                   }
	               });
	        AlertDialog alert = builder.create();
	        alert.show();
    		return;
    		
    	}
    }
    
	
    /*try {
     
      SoapObject request = new SoapObject(NAMESPACE, METHOD_FINDDALL);  
      request.addAttribute("local", local);
      SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
      envelope.setOutputSoapObject(request);

      HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
      httpTransportSE.call(SOAP_ACTION, envelope);

      SoapObject results=(SoapObject)envelope.bodyIn;
      
      for (int i=0; i<results.getPropertyCount();i++){

          SoapObject result = (SoapObject) results.getProperty(i);
          
          String restaurante = result.toString();
          String[] r = restaurante.split("\n");
          
          String name = r[0].toString();
          String address = r[1].toString();
          double lat = Double.parseDouble(r[2].toString());
          double lon = Double.parseDouble(r[3].toString());
          String tipocomida = r[4].toString();       
          long somavotos = Long.parseLong(r[5].toString());
          int numvotos = Integer.parseInt(r[6].toString());
          String gamapreco = r[7].toString();
          int tipocontacto = Integer.parseInt(r[8].toString());
          String valorcontacto = r[9].toString();
          String comentario = r[10].toString();
          
          if(raio != 0.0){
        	  if(calculaDistancia(lati, longi, lat, lon)<raio){
            	  db.inserirR(name, address, lat, lon, tipocomida, somavotos, numvotos, gamapreco, tipocontacto, valorcontacto, comentario);

              }
          }
          else{
        	  db.inserirR(name, address, lat, lon, tipocomida, somavotos, numvotos, gamapreco, tipocontacto, valorcontacto, comentario);
          }
      } 
    } catch (Exception e) {
    	
    	e.printStackTrace();
    }*/

    db.close();
    
    Intent i = new Intent(WS.this, Pesquisar.class);
    
    startActivity(i);
    finish();
	
  }
  
  private double calculaDistancia(double lat_src, double lng_src, double lat_dst, double lng_dst) {
		
	  float[] results = new float[1];
	  
	  Location.distanceBetween(lat_src, lng_src, lat_dst, lng_dst, results);
	  
	  return results[0] / 1000;
  }
  
}