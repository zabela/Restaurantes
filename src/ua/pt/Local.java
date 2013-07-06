package ua.pt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Local extends Activity {
	private RadioButton actual;
	private RadioButton outro;
	private CheckBox raio;
	
	private Location location;
	private EditText raio2;
	private EditText outro2;
	
	private double r;
	private String l;
	private Button anterior;
	
	private Button ok;
	private String address;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.local);
        
        OutrosBs();
        Botoes();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    		startActivity(new Intent(Local.this, Restaurantes.class));
			finish();
       	}
    	
       	return super.onKeyDown(keyCode, event);
    }
    
	private void OutrosBs() {
		
		actual = (RadioButton) findViewById(R.id.rbActual);
		outro = (RadioButton) findViewById(R.id.rbOutro);
		
		outro2 = (EditText) findViewById(R.id.etOutro);
		raio2 = (EditText) findViewById(R.id.etRaio);
			
		raio = (CheckBox) findViewById(R.id.cbRaio);
		
		actual.toggle();
		outro2.setEnabled(false);
		raio2.setEnabled(false);
		
		outro.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				if (outro.isChecked())
					outro2.setEnabled(true);
				else
					outro2.setEnabled(false);
			}
			
		});
		
		outro2.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                	// Perform action on key press
                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                	imm.hideSoftInputFromWindow(outro2.getWindowToken(), 0);
                  return true;
                }
                return false;
            }
        });
		
		raio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				if (raio.isChecked())
					raio2.setEnabled(true);
				else
					raio2.setEnabled(false);
			}
			
		});
		
		raio2.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                	// Perform action on key press
                	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                	imm.hideSoftInputFromWindow(raio2.getWindowToken(), 0);
                  return true;
                }
                return false;
            }
        });

	}

	private void Botoes() {
		
        anterior = (Button) findViewById(R.id.localAnterior);
        ok = (Button) findViewById(R.id.localOk);
        
        anterior.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						startActivity(new Intent(Local.this, Restaurantes.class));
						finish();
					}
				});
        
        ok.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						int a = nextActivity();
						
						if (a == 5) {	
							
							if (l.equals("actual")) {
								myLocation();
							}
							else {
								findLocation(l);
							}
							
							if (location == null) {
								
								AlertDialog.Builder builder = new AlertDialog.Builder(Local.this);
							        builder.setTitle("Aviso")
							        	   .setMessage("Não é possível localizar o local pretendido.")
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

						    ProgressDialog dialog = ProgressDialog.show(Local.this, "A carregar dados.", "Por favor espere...", true);
							
							//****************Chamada do web service**************/
							Intent y = new Intent(Local.this, WS.class);
							
							if (l.equals("actual")) {
								
						    	findLocation();
						    	l = address;
						    }
							
							y.putExtra("local", l);
							y.putExtra("raio", r);
							y.putExtra("coordinates", location);
							
							startActivity(y);
							//dialog.cancel();
							finish();
							
						}
						else if (a < 5)
							return;
					}
        		});
					
	}
	
	private int nextActivity() {
		
		if (outro.isChecked() && (outro2.getText().toString().equals(" ") || outro2.getText().toString().equals(""))) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(Local.this);
	        builder.setTitle("Aviso")
	        	   .setMessage("É necessário introduzir um local.")
	        	   .setIcon(android.R.drawable.ic_dialog_alert)
	               .setCancelable(false)
	               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   dialog.cancel();
	                   }
	               });
	        AlertDialog alert = builder.create();
	        alert.show();
	        return 1;
		}
		
		if (raio.isChecked() && (raio2.getText().toString().equals(" ") || raio2.getText().toString().equals(""))) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(Local.this);
	        builder.setTitle("Aviso")
	        	   .setMessage("É necessário introduzir um raio.")
	        	   .setIcon(android.R.drawable.ic_dialog_alert)
	               .setCancelable(false)
	               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   dialog.cancel();
	                   }
	               });
	        AlertDialog alert = builder.create();
	        alert.show();
	        return 2;
		}
		
		r = 0.0;
		l = "actual";
		
		if (raio.isChecked()) {
			String teste = raio2.getText().toString();
			
			try {
				
				r = Double.parseDouble(teste);
				
			}catch (NumberFormatException e) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(Local.this);
		        builder.setTitle("Aviso")
		        	   .setMessage("O raio deverá encontrar-se na forma algébrica.")
		        	   .setIcon(android.R.drawable.ic_dialog_alert)
		               .setCancelable(false)
		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   dialog.cancel();
		                   }
		               });
		        AlertDialog alert = builder.create();
		        alert.show();
		        return 3;
			}
			
			if (r < 0.0 || r > 20.0) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(Local.this);
		        builder.setTitle("Aviso")
		        	   .setMessage("O raio deverá tomar valores entre 0.1 e 20.0 km.")
		        	   .setIcon(android.R.drawable.ic_dialog_alert)
		               .setCancelable(false)
		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   dialog.cancel();
		                   }
		               });
		        AlertDialog alert = builder.create();
		        alert.show();
		        return 3;
			}
				
		}
		
		if (outro.isChecked()) {
			l = outro2.getText().toString();
			
			if (l.length() < 2) {
				AlertDialog.Builder builder = new AlertDialog.Builder(Local.this);
		        builder.setTitle("Aviso")
		        	   .setMessage("O local inserido não é aceitável.")
		        	   .setIcon(android.R.drawable.ic_dialog_alert)
		               .setCancelable(false)
		               .setNeutralButton("OK", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   dialog.cancel();
		                   }
		               });
		        AlertDialog alert = builder.create();
		        alert.show();
		        return 4;
			}
		}
		
		return 5;
	}
	
	public void myLocation() {
		
		// finding my location
		String context = Context.LOCATION_SERVICE;
		LocationManager locationManager = (LocationManager) getSystemService(context);
		
		/*Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		
		String provider = locationManager.getBestProvider(criteria, true);*/
		String provider = LocationManager.GPS_PROVIDER;
		
		// selecting a location provider
		Location local = locationManager.getLastKnownLocation(provider);
		
		updateWithNewLocation(local);
	}
	
	private void updateWithNewLocation(Location local) {
		
		if (local != null) {
		
			location = new Location("actual");
			location.setLatitude(local.getLatitude());
			location.setLongitude(local.getLongitude());
		    
		}
		else
			location = null;
	}
	
	public void findLocation(String streetAddress) {
		
		Geocoder fwdGeocoder = new Geocoder(this, Locale.getDefault());
		
		List<Address> locations = null;
		
		try {
			
			locations = fwdGeocoder.getFromLocationName(streetAddress, 10);
			
		} catch (IOException e) {}
		
		if (locations != null) {
			
			if (!locations.isEmpty()) {
				location = new Location("outro");
				location.setLatitude(locations.get(0).getLatitude());
				location.setLongitude(locations.get(0).getLongitude());
			}
			else
				location = null;
		}
		else
			location = null;
	}
	
	public void findLocation() {
		
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		List<Address> addresses = null;
		
		Geocoder gc = new Geocoder(this, Locale.getDefault());
		
		try {
			
			addresses = gc.getFromLocation(latitude, longitude, 10);
			
		} catch (IOException e) {}
		
		address = addresses.get(0).getLocality();
		
	}
	
}
