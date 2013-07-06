package ua.pt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class Sobre extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sobre);
        
        Button anterior = (Button) findViewById(R.id.sobreAnterior);
       
        anterior.setOnClickListener(
        		new Button.OnClickListener() {
					public void onClick(View v) {
						startActivity(new Intent(Sobre.this, Restaurantes.class));
						finish();
					}
				});
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    		startActivity(new Intent(Sobre.this, Restaurantes.class));
			finish();
       	}
    	
       	return super.onKeyDown(keyCode, event);
    }
}
