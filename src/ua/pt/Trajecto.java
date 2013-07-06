package ua.pt;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.android.maps.*;
import com.google.android.maps.MapView.LayoutParams;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Trajecto extends MapActivity {
	private MapView mapView;
	private GeoPoint p;
	private int i;
	
	private List<Overlay> mapOverlays;
	private Drawable drawable1;
	private Drawable drawable2;
	
	private MyItemizedOverlay itemizedOverlay1;
	private MyItemizedOverlay itemizedOverlay2;
	private static String TREASURE_PROXIMITY_ALERT = "com.paad.treasurealert";
	
	private Location dest;
	private long id;
	private Adapter myadapter;
	
	private final LocationListener locationListener = new LocationListener() {
		
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location, dest.getLatitude(), dest.getLongitude());
		}

		public void onProviderDisabled(String provider){
			updateWithNewLocation(null, dest.getLatitude(), dest.getLongitude());
		}
		
		public void onProviderEnabled(String provider){ }
		public void onStatusChanged(String provider, int status, Bundle extras){ }
	};
	
    /** Called when the activity is first created. */
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.trajecto);
        
        id = getIntent().getLongExtra("id", 0);
        
        mapView = (MapView) findViewById(R.id.trajectoMapView);
        
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.trajectoZoom);  
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
        drawable1 = this.getResources().getDrawable(R.drawable.arrow);
        itemizedOverlay1 = new MyItemizedOverlay(drawable1);
        
        drawable2 = this.getResources().getDrawable(R.drawable.flag);
        itemizedOverlay2 = new MyItemizedOverlay(drawable2);
        
        myadapter = new Adapter(this.getApplicationContext());
        myadapter.open();
        
        Restaurante r = myadapter.getRestbyID(id);
        
        dest = new Location("Destino");
        dest.setLatitude(r.getLat());
        dest.setLongitude(r.getLon());

        AddingMarkersD(dest.getLatitude(), dest.getLongitude(), "Destino");
        
        // Creating a proximity alert Broadcast Receiver
		IntentFilter filter = new IntentFilter(TREASURE_PROXIMITY_ALERT);
		registerReceiver(new ProximityIntentReceiver(), filter);
		
        //setProximityAlert(dest.getLatitude(), dest.getLongitude());
		setProximityAlert(dest.getLatitude(), dest.getLongitude());
		
        i = -1;
        
        trajecto(dest.getLatitude(), dest.getLongitude());
        myadapter.close();
        
        Button anterior = (Button) findViewById(R.id.trajetoAnterior);
        
        anterior.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						// nao e necessario iniciar uma nova actividade
						finish();
					}
				});
    }

	private void desenhaTrajecto(double src_lat, double src_long, double dest_lat, double dest_long) {
		
        GeoPoint srcGeoPoint = new GeoPoint((int) (src_lat * 1E6), (int) (src_long * 1E6));
        GeoPoint destGeoPoint = new GeoPoint((int) (dest_lat * 1E6), (int) (dest_long * 1E6));

        DrawPath(srcGeoPoint, destGeoPoint, 999, mapView);

        mapView.getController().animateTo(srcGeoPoint);
        mapView.getController().setZoom(16);
	}
	
	private void setProximityAlert(double lat, double lng) {
		
		String locService = Context.LOCATION_SERVICE; 
		LocationManager locationManager = (LocationManager) getSystemService(locService);
		
		float radius = 100f; // meters
		long expiration = 600000; // do not expire
		
		// USING PROXIMITY ALERTS
		Intent intent = new Intent(TREASURE_PROXIMITY_ALERT);
		PendingIntent proximityIntent = PendingIntent.getBroadcast(this, -1, intent, 0);
	
		locationManager.addProximityAlert(lat, lng, radius, expiration, proximityIntent);

	}
	
	public class ProximityIntentReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive (Context context, Intent intent) {
		
			String key = LocationManager.KEY_PROXIMITY_ENTERING;
			Boolean entering = intent.getBooleanExtra(key, false);
			
			if (entering) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(Trajecto.this);
				
				builder.setTitle("Aviso")
	        	   	.setMessage("Chegou ao seu destino.")
	        	   	.setIcon(android.R.drawable.ic_dialog_alert)
	        	   	.setCancelable(false)
	        	   	.setNeutralButton("OK", new DialogInterface.OnClickListener() {
	        	   		public void onClick(DialogInterface dialog, int id) {
	        	   			dialog.cancel();
		                   }
		               });
		        AlertDialog alert = builder.create();
		        alert.show();
			}
		}
		
	}
	
	public void AddingMarkersO(double lat, double lng, String a) {
 
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
    
        OverlayItem overlayitem = new OverlayItem(p, a, a);
        
        itemizedOverlay1.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay1);
	}
	
	public void AddingMarkersD(double lat, double lng, String a) {
 
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
    
        OverlayItem overlayitem = new OverlayItem(p, a, a);
        
        itemizedOverlay2.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay2);
	}

	public void trajecto(double dest_lat, double dest_lng) {
		
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

		updateWithNewLocation(location, dest_lat, dest_lng);
		
		locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
	}
	
	private void updateWithNewLocation(Location location, double dest_lat, double dest_lng) {
		
		if (location != null) {
			
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			
			if (i == -1) {
				
				AddingMarkersO(lat, lng, "Origem");
				desenhaTrajecto(lat, lng, dest_lat, dest_lng);
				i++;
				
			} else if (i == 0) {
				
				mapOverlays.remove(itemizedOverlay1.createItem(i));
				itemizedOverlay1.remove(i);
	        	AddingMarkersO(lat, lng, "Origem");
	        	desenhaTrajecto(lat, lng, dest_lat, dest_lng);
			}

		}
			
	}

	@Override
	protected boolean isRouteDisplayed() {

		return false;
	}
	
	private void DrawPath(final GeoPoint src,final GeoPoint dest, final int color, final MapView mMapView01) {
		
		// connect to map web service
		StringBuilder urlString = new StringBuilder();
		
		urlString.append("http://maps.google.com/maps?f=d&hl=en");
		urlString.append("&saddr="); //from
		
		urlString.append(Double.toString((double) src.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		
		urlString.append(Double.toString((double) src.getLongitudeE6() / 1.0E6));
		urlString.append("&daddr="); //to
		
		urlString.append(Double.toString((double) dest.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		
		urlString.append( Double.toString((double) dest.getLongitudeE6() / 1.0E6));
		urlString.append("&ie=UTF8&0&om=0&output=kml");
		
		Log.d("xxx", "URL=" + urlString.toString());
		
		// get the kml (XML) doc. And parse it to get the coordinates (direction route).
		Document doc = null;
		
		HttpURLConnection urlConnection = null;
		
		URL url = null;
	
		try {
			
			url = new URL(urlString.toString());
			
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.connect();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(urlConnection.getInputStream());

			if (doc.getElementsByTagName("GeometryCollection").getLength() > 0) {
				
				//String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getNodeName();
				String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue();
	
				Log.d("xxx","path=" + path);
				String[] pairs = path.split(" ");
				String[] lngLat = pairs[0].split(","); // lngLat[0] = longitude lngLat[1]=latitude lngLat[2] = height
				
				// src
				GeoPoint startGP = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6), (int) (Double.parseDouble(lngLat[0]) * 1E6));
				mMapView01.getOverlays().add(new MyOverLay(startGP, startGP, 1));
				GeoPoint gp1;
				GeoPoint gp2 = startGP;
				
				for (int i = 1; i < pairs.length; i++) { // the last one would be crash
					
					lngLat = pairs[i].split(",");
					
					gp1 = gp2;
					
					// watch out! For GeoPoint, first:latitude, second:longitude
					gp2 = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6), (int) (Double.parseDouble(lngLat[0]) * 1E6));

					mMapView01.getOverlays().add(new MyOverLay(gp1, gp2, 2, color));
					Log.d("xxx", "pair:" + pairs[i]);
				}
				
				mMapView01.getOverlays().add(new MyOverLay(dest, dest, 3)); // use the default color
			}
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		
		} catch (SAXException e) {
			
			e.printStackTrace();
			
		}
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
		
		protected void remove(int i) {
			mOverlays.remove(i);
		}

		@Override
		protected OverlayItem createItem(int i) {
			
			return mOverlays.get(i);
		}
	}

	public class MyOverLay extends Overlay {
		private GeoPoint gp1;
		private GeoPoint gp2;
		private int mRadius;
		private int mode;
		private int defaultColor;
		private String text;
		private Bitmap img;

		public MyOverLay(GeoPoint gp1,GeoPoint gp2,int mode) { // GeoPoint is a int. (6E)
		
			this.mRadius = 6;
			this.mode = 0;
			this.text = "";
			this.gp1 = gp1;
			this.gp2 = gp2;
			this.img = null;
			this.mode = mode;
			defaultColor = 999; // no defaultColor
			
		}

		public MyOverLay(GeoPoint gp1,GeoPoint gp2,int mode, int defaultColor) {
			
			this.gp1 = gp1;
			this.gp2 = gp2;
			this.mode = mode;
			this.defaultColor = defaultColor;
		}
		
		public void setText(String t) {
			
			this.text = t;
		}
		
		public void setBitmap(Bitmap bitmap) {
			
			this.img = bitmap;
		}
		
		public int getMode() {
			
			return mode;
		}

		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
			
			Projection projection = mapView.getProjection();
			
			if (shadow == false) {
				
				Paint paint = new Paint();
				
				paint.setAntiAlias(true);
				Point point = new Point();
				projection.toPixels(gp1, point);
				
				// mode = 1&#65306; start
				if (mode == 1) {
					
					if (defaultColor == 999)
						paint.setColor(Color.BLACK);
					
					else
						paint.setColor(defaultColor);
					
					RectF oval = new RectF(point.x - mRadius, point.y - mRadius, point.x + mRadius, point.y + mRadius);
					
					// start point
					canvas.drawOval(oval, paint);
				}
	
				// mode = 2&#65306; path
				else if (mode == 2)
				{
					if (defaultColor == 999)
						paint.setColor(Color.GREEN);
					
					else
						paint.setColor(defaultColor);
	
					Point point2 = new Point();
					
					projection.toPixels(gp2, point2);
					
					paint.setStrokeWidth(5);
					paint.setAlpha(120);
					
					canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);
				}
				
				/* mode = 3&#65306; end */
				else if (mode == 3)
				{
					/* the last path */
					if (defaultColor == 999)
						paint.setColor(Color.BLACK);
	
					else
						paint.setColor(defaultColor);
	
					Point point2 = new Point();
					projection.toPixels(gp2, point2);
					paint.setStrokeWidth(5);
					paint.setAlpha(120);
					
					canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);
					
					RectF oval = new RectF(point2.x - mRadius, point2.y - mRadius, point2.x + mRadius, point2.y + mRadius);
	
					/* end point */
					paint.setAlpha(255);
					canvas.drawOval(oval, paint);
				}
			}
			
			return super.draw(canvas, mapView, shadow, when);
		}

	}

}


