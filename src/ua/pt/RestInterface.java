package ua.pt;

import java.util.List;

import android.content.ContentValues;

public interface RestInterface {
	
	Restaurante getRestbyName(String nome);
    List<Restaurante> getRests0();
    List<Restaurante> getRests01(String tipocomida);
    List<Restaurante> getRests02(int classificacao);
    List<Restaurante> getRests03(String gama);
    List<Restaurante> getRests012(String tipocomida, int classificacao);
    List<Restaurante> getRests013(String tipocomida, String gama);
    List<Restaurante> getRests023(int classificacao, String gama);
    List<Restaurante> getRests0123(String tipocomida, int classificacao, String gama);

    long insertRest(ContentValues values);
    long inserirR(String name, String address, double lat, double lon, String tipocomida, long somavotos, int numvotos, String gamapreco, int tipocontacto, String valorcontacto, String comentario);
    
    void comentar(long rowId, String comentario);
    void classificar(long rowId, int classificacao);

}
