package ua.pt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.android.maps.*;
import com.google.android.maps.MapView.LayoutParams;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Mapa extends MapActivity {
	private MapView mapView;
	private MapController mc;
	private Adapter myadapter;
	private char c;
	private String tipo;
	
	/*private Location location;
	private String local;
	private double raio;*/
	
	private String gama;
	private String clas;
	private String nome;

	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private MyItemizedOverlay itemizedOverlay;
	
	private Drawable drawableML;
	private MyItemizedOverlay itemizedOverlayML;
	
    /** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.mapa);
        
        mapView = (MapView) findViewById(R.id.mapaMapView);
        
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.mapaZoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        
        mapView.setSatellite(true);
        mapView.setStreetView(true);
        mapView.setTraffic(true);
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.rbicon);
        itemizedOverlay = new MyItemizedOverlay(drawable);

        drawableML = this.getResources().getDrawable(R.drawable.arrow);
        itemizedOverlayML = new MyItemizedOverlay(drawableML);
        
        c = getIntent().getCharExtra("pesquisa", 'a');
        
        LinkedList<Restaurante> lista = new LinkedList<Restaurante>();; 
        Restaurante rest = null;
        myadapter = new Adapter(this.getApplicationContext());
        myadapter.open();
        
        nome = "";
        tipo = "";
        gama = "";
        clas = "";
        
        /****** IN�CIO NOME ******/
        if (c == 'n') {
        	
        	nome = (String) getIntent().getCharSequenceExtra("nome");
        	
        	rest = myadapter.getRestbyName(nome);
        	
        	if (rest == null) {
        		
        		AlertDialog.Builder builder = new AlertDialog.Builder(Mapa.this);
		        builder.setTitle("Aviso")
		        	   .setMessage("Não existem restaurantes com esse nome.")
		        	   .setIcon(android.R.drawable.ic_dialog_alert)
		               .setCancelable(false)
		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   dialog.cancel();
		                	   
		                   }
		               });
		        AlertDialog alert = builder.create();
		        alert.show();
        		
        		Intent i = new Intent(Mapa.this, Nome.class);
        		startActivity(i);
				finish();
        	}
        	
        	AddingMarkers(rest.getLat(), rest.getLon(), Integer.toString(rest.getId()));
        	
        }/****** FIM NOME ******/
        
        /****** IN�CIO CATEGORIA ******/
        else if (c == 'c') {
        	
        	tipo = (String) getIntent().getCharSequenceExtra("tipo");
        	gama = (String) getIntent().getCharSequenceExtra("gama");
        	clas = (String) getIntent().getCharSequenceExtra("clas");
        	
    		if (tipo.equals("") && gama.equals("") && clas.equals("")){
    			lista = myadapter.getRests0();
    			
    			
    			if (lista.size() == 0) {
    				
    				AlertDialog.Builder builder = new AlertDialog.Builder(Mapa.this);
    		        builder.setTitle("Aviso")
    		        	   .setMessage("Não existem restaurantes com os filtros pretendidos.")
    		        	   .setIcon(android.R.drawable.ic_dialog_alert)
    		               .setCancelable(false)
    		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		                   public void onClick(DialogInterface dialog, int id) {
    		                	   dialog.cancel();
    		                	   Intent i = new Intent(Mapa.this, Categoria.class);
    		               			startActivity(i);
    		               			finish();
    		                   }
    		               });
    		        AlertDialog alert = builder.create();
    		        alert.show();
            		
            		
    			}
    			
    			for(int i=0; i < lista.size(); i++){
    				Restaurante r = lista.get(i);
    				AddingMarkers(r.getLat(), r.getLon(), Integer.toString(r.getId()));
    			}
    		}
    			
    		
    		if (!tipo.equals("") && gama.equals("") && clas.equals("")){
    			lista = myadapter.getRests01(tipo);
    			
    			if (lista.size() == 0) {
    				
    				AlertDialog.Builder builder = new AlertDialog.Builder(Mapa.this);
    		        builder.setTitle("Aviso")
    		        	   .setMessage("Não existem restaurantes com os filtros pretendidos.")
    		        	   .setIcon(android.R.drawable.ic_dialog_alert)
    		               .setCancelable(false)
    		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		                   public void onClick(DialogInterface dialog, int id) {
    		                	   dialog.cancel();
    		                	   Intent i = new Intent(Mapa.this, Categoria.class);
    		               			startActivity(i);
    		               			finish();
    		                   }
    		               });
    		        AlertDialog alert = builder.create();
    		        alert.show();
            		
            		
    			}
    			
    			for(int i=0; i < lista.size(); i++){
    				Restaurante r = lista.get(i);
    				AddingMarkers(r.getLat(), r.getLon(), Integer.toString(r.getId()));
    			}
    		}
    			
    		
    		if (tipo.equals("") && gama.equals("") && !clas.equals("")){
    			lista = myadapter.getRests02(Integer.parseInt(clas));
    			
    			if (lista.size() == 0) {
    				
    				AlertDialog.Builder builder = new AlertDialog.Builder(Mapa.this);
    		        builder.setTitle("Aviso")
    		        	   .setMessage("Não existem restaurantes com os filtros pretendidos.")
    		        	   .setIcon(android.R.drawable.ic_dialog_alert)
    		               .setCancelable(false)
    		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		                   public void onClick(DialogInterface dialog, int id) {
    		                	dialog.cancel();
    		                	Intent i = new Intent(Mapa.this, Categoria.class);
    		               		startActivity(i);
    		               		finish();
    		                   }
    		               });
    		        AlertDialog alert = builder.create();
    		        alert.show();
            		
            		
    			}
    			
    			for(int i=0; i < lista.size(); i++){
    				Restaurante r = lista.get(i);
    				AddingMarkers(r.getLat(), r.getLon(), Integer.toString(r.getId()));
    			}
    		}
    			
    		
    		if (tipo.equals("") && !gama.equals("") && clas.equals("")){
    			lista = myadapter.getRests03(gama);
    			
    			if (lista.size() == 0) {
    				
    				AlertDialog.Builder builder = new AlertDialog.Builder(Mapa.this);
    		        builder.setTitle("Aviso")
    		        	   .setMessage("Não existem restaurantes com os filtros pretendidos.")
    		        	   .setIcon(android.R.drawable.ic_dialog_alert)
    		               .setCancelable(false)
    		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		                   public void onClick(DialogInterface dialog, int id) {
    		                	   dialog.cancel();
    		                	   Intent i = new Intent(Mapa.this, Categoria.class);
    		                	   startActivity(i);
    		                	   finish();
    		                   }
    		               });
    		        AlertDialog alert = builder.create();
    		        alert.show();
            		
            		
    			}
    			
    			for(int i=0; i < lista.size(); i++){
    				Restaurante r = lista.get(i);
    				AddingMarkers(r.getLat(), r.getLon(), Integer.toString(r.getId()));
    			}
    		}
    			
    			
    		if (!tipo.equals("") && gama.equals("") && !clas.equals("")){
    			lista = myadapter.getRests012(tipo, Integer.parseInt(clas));
    			
    			if (lista.size() == 0) {
    				
    				AlertDialog.Builder builder = new AlertDialog.Builder(Mapa.this);
    		        builder.setTitle("Aviso")
    		        	   .setMessage("Não existem restaurantes com os filtros pretendidos.")
    		        	   .setIcon(android.R.drawable.ic_dialog_alert)
    		               .setCancelable(false)
    		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		                   public void onClick(DialogInterface dialog, int id) {
    		                	   dialog.cancel();
    		                	   Intent i = new Intent(Mapa.this, Categoria.class);
    		               			startActivity(i);
    		               			finish();
    		                   }
    		               });
    		        AlertDialog alert = builder.create();
    		        alert.show();
            		
            		
    			}
    			
    			for(int i=0; i < lista.size(); i++){
    				Restaurante r = lista.get(i);
    				AddingMarkers(r.getLat(), r.getLon(), Integer.toString(r.getId()));
    			}
    		}
    			
    		
    		if (!tipo.equals("") && !gama.equals("") && clas.equals("")){
    			lista = myadapter.getRests013(tipo, gama);
    			
    			if (lista.size() == 0) {
    				
    				AlertDialog.Builder builder = new AlertDialog.Builder(Mapa.this);
    		        builder.setTitle("Aviso")
    		        	   .setMessage("Não existem restaurantes com os filtros pretendidos.")
    		        	   .setIcon(android.R.drawable.ic_dialog_alert)
    		               .setCancelable(false)
    		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		                   public void onClick(DialogInterface dialog, int id) {
    		                	   dialog.cancel();
    		                	   Intent i = new Intent(Mapa.this, Categoria.class);
    		               			startActivity(i);
    		               			finish();
    		                   }
    		               });
    		        AlertDialog alert = builder.create();
    		        alert.show();
            		
            		
    			}
    			
    			for(int i=0; i < lista.size(); i++){
    				Restaurante r = lista.get(i);
    				AddingMarkers(r.getLat(), r.getLon(), Integer.toString(r.getId()));
    			}
    		}
    			
    		
    		if (tipo.equals("") && !gama.equals("") && !clas.equals("")){
    			lista = myadapter.getRests023(Integer.parseInt(clas), gama);
    			
    			if (lista.size() == 0) {
    				
    				AlertDialog.Builder builder = new AlertDialog.Builder(Mapa.this);
    		        builder.setTitle("Aviso")
    		        	   .setMessage("Não existem restaurantes com os filtros pretendidos.")
    		        	   .setIcon(android.R.drawable.ic_dialog_alert)
    		               .setCancelable(false)
    		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		                   public void onClick(DialogInterface dialog, int id) {
    		                	   dialog.cancel();
    		                	   Intent i = new Intent(Mapa.this, Categoria.class);
    		               			startActivity(i);
    		               			finish();
    		                   }
    		               });
    		        AlertDialog alert = builder.create();
    		        alert.show();
            		
            		
    			}
    			
    			for(int i=0; i < lista.size(); i++){
    				Restaurante r = lista.get(i);
    				AddingMarkers(r.getLat(), r.getLon(), Integer.toString(r.getId()));
    			}
    		}
    			
    		
    		if (!tipo.equals("") && !gama.equals("") && !clas.equals("")){
    			lista = myadapter.getRests0123(tipo, Integer.parseInt(clas), gama);
    			
    			if (lista.size() == 0) {
    				
    				AlertDialog.Builder builder = new AlertDialog.Builder(Mapa.this);
    		        builder.setTitle("Aviso")
    		        	   .setMessage("Não existem restaurantes com os filtros pretendidos.")
    		        	   .setIcon(android.R.drawable.ic_dialog_alert)
    		               .setCancelable(false)
    		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
    		                   public void onClick(DialogInterface dialog, int id) {
    		                	   dialog.cancel();
    		                	   Intent i = new Intent(Mapa.this, Categoria.class);
    		               			startActivity(i);
    		               			finish();
    		                   }
    		               });
    		        AlertDialog alert = builder.create();
    		        alert.show();
            		
            		
    			}
    			
    			for(int i=0; i < lista.size(); i++){
    				Restaurante r = lista.get(i);
    				AddingMarkers(r.getLat(), r.getLon(), Integer.toString(r.getId()));
    			}
    		}

        }/****** FIM CATEGORIA ******/

        //displayParticularLocation(40.6411848, -8.6536169);
        //AddingMarkers(40.633139, -8.660215, "IEETA");
        //AddingMarkers(40.629552, -8.655640, "Complexo");
        //AddingMarkers(40.6411848, -8.6536169, "Aveiro");
        
        myLocation();
        
        Button anterior = (Button) findViewById(R.id.mapaAnterior);
        
        myadapter.close();
        
        anterior.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						
						Intent i = null;
						
						if (c == 'n') 
							i = new Intent(Mapa.this, Nome.class);
	
						else if (c == 'c') 
							i = new Intent(Mapa.this, Categoria.class);
							
						startActivity(i);
						finish();
					}
				});
        
    }

	@Override
	protected boolean isRouteDisplayed() {
		
		return false;
	}
	
	public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {
		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

		public MyItemizedOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
			
		}

		protected void addOverlay(OverlayItem overlay) {
			
			mOverlays.add(overlay);
		    populate();
		}
		
		@Override
		public int size() {
			
			return mOverlays.size();
		}

		@Override
		protected OverlayItem createItem(int i) {
			
			return mOverlays.get(i);
		}
		
		protected void remove(int i) {
			mOverlays.remove(i);
		}
		
		@Override
        public boolean onTap(int index) {
			
			myadapter = new Adapter(getBaseContext());
	        myadapter.open();
	        
			LinkedList<Restaurante> listaR = myadapter.getRests0();
            //cursor.moveToFirst();
            //startManagingCursor(cursor);

			for(int i=0; i<listaR.size(); i++){
				int _id = listaR.get(i).getId();
				String id = String.valueOf(_id);
				
				if ((mOverlays.get(index).getTitle()).compareToIgnoreCase(id) == 0) {

                	Intent y = new Intent(Mapa.this, Info.class);
                    y.putExtra("id", _id);
                
                    startActivity(y);
                    i++;

                } else {
                	//Intent y = new Intent(Mapa.this, Info.class);
                	//y.putExtra("id", _id);
                    
                    //startActivity(y);
                    i++;
                }
				
			}
			
            myadapter.close();
			return true;
        }
		
	}
	
	public void displayParticularLocation(double lat, double lng) {
		
		mc = mapView.getController();
 
        GeoPoint p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
 
        mc.animateTo(p);
        mc.setZoom(15); 
        mapView.invalidate();

	}
	
	public void AddingMarkers(double lat, double lng, String a) {
 
		GeoPoint p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
    
        OverlayItem overlayitem = new OverlayItem(p, a, a);
        
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
	}
	
	public void myLocation() {
		
		// finding my location
		String context = Context.LOCATION_SERVICE;
		LocationManager locationManager = (LocationManager) getSystemService(context);
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		
		String provider = locationManager.getBestProvider(criteria, true);
		//String provider = LocationManager.GPS_PROVIDER;
		
		// selecting a location provider
		Location location =	locationManager.getLastKnownLocation(provider);
		
		updateWithNewLocation(location);
		
	}
	
	private void updateWithNewLocation(Location location) {
		
		if (location != null) {
			
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			
			displayParticularLocation(lat, lng);
		
			GeoPoint p = new GeoPoint(
		            (int) (lat * 1E6), 
		            (int) (lng * 1E6));
		    
	        OverlayItem overlayitem = new OverlayItem(p, "Local Actual", "Local Actual");
	        
	        itemizedOverlayML.addOverlay(overlayitem);
	        mapOverlays.add(itemizedOverlayML);
		}
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if ((keyCode == KeyEvent.KEYCODE_BACK)) {

			Intent i = null;
			
			if (c == 'n') 
				i = new Intent(Mapa.this, Nome.class);

			else if (c == 'c') 
				i = new Intent(Mapa.this, Categoria.class);
				
			startActivity(i);
			finish();
       	}
    	
       	return super.onKeyDown(keyCode, event);
    }
    
    
}
