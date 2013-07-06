package ua.pt;

public class Restaurante {

	    public static final int _ID = 0;
	    public static final int NAME_ID = 1;
	    public static final int ADDRESS_ID = 2;
	    public static final int LAT_ID = 3;
	    public static final int LON_ID = 4;
	    public static final int TIPOCOMIDA_ID = 5;
	    public static final int SOMAVOTOS_ID = 6;
	    public static final int NUMVOTOS_ID = 7;
	    public static final int GAMAPRECO_ID = 8;
	    public static final int TIPOCONTACTO_ID = 9;
	    public static final int VALORCONTACTO_ID = 10;
	    public static final int COMENTARIO_ID = 11;
	    public static final String KEY_ROWID = "_id";
	    public static final String KEY_NAME = "name";
	    public static final String KEY_ADDRESS = "address";
	    public static final String KEY_LAT = "lat";
	    public static final String KEY_LON = "lon";
	    public static final String KEY_TIPOCOMIDA = "tipocomida";
	    public static final String KEY_SOMAVOTOS = "somavotos";
	    public static final String KEY_NUMVOTOS = "numvotos";
	    public static final String KEY_GAMAPRECO = "gamapreco";
	    public static final String KEY_TIPOCONTACTO = "tipocontacto";
	    public static final String KEY_VALORCONTACTO = "valorcontacto";
	    public static final String KEY_COMENTARIO = "comentario";
	    public static final String[] specification = new String[]{
	    	Restaurante.KEY_ROWID,
	    	Restaurante.KEY_NAME,
	    	Restaurante.KEY_ADDRESS,
	    	Restaurante.KEY_LAT,
	    	Restaurante.KEY_LON,
	    	Restaurante.KEY_TIPOCOMIDA,
	    	Restaurante.KEY_SOMAVOTOS,
	    	Restaurante.KEY_NUMVOTOS,
	    	Restaurante.KEY_GAMAPRECO,
	    	Restaurante.KEY_TIPOCONTACTO,
	    	Restaurante.KEY_VALORCONTACTO,
	    	Restaurante.KEY_COMENTARIO,
	    	};
	    private String name;
	    private String address;
	    private double lat;
	    private double lon;
	    private String tipocomida;
	    private long somavotos;
	    private int numvotos;
	    private String gamapreco;
	    private int tipocontacto;
	    private String valorcontacto;
	    private String comentario = "";
	    int id;

	    public Restaurante() {
	    	this.name = "";
	        this.address = "";
	        this.lat = 0.0;
	        this.lon = 0.0;
	        this.tipocomida = "";
	        this.somavotos = 0;
	        this.numvotos = 0;
	        this.gamapreco = "";
	        this.tipocontacto = 0;
	        this.valorcontacto = "";
	        this.comentario = "";
	    }

	    public Restaurante(String name, String address, double lat, double lon, String tipocomida, long somavotos, int numvotos, String gamapreco, int tipocontacto, String valorcontacto, String comentario) {
	        this.name = name;
	        this.address = address;
	        this.lat = lat;
	        this.lon = lon;
	        this.tipocomida = tipocomida;
	        this.somavotos = somavotos;
	        this.numvotos = numvotos;
	        this.gamapreco = gamapreco;
	        this.tipocontacto = tipocontacto;
	        this.valorcontacto = valorcontacto;
	        this.comentario += comentario;
	    }

	    public void set(String name, String address, double lat, double lon, String tipocomida, long somavotos, int numvotos, String gamapreco, int tipocontacto, String valorcontacto, String comentario) {
	    	this.name = name;
	        this.address = address;
	        this.lat = lat;
	        this.lon = lon;
	        this.tipocomida = tipocomida;
	        this.somavotos = somavotos;
	        this.numvotos = numvotos;
	        this.gamapreco = gamapreco;
	        this.tipocontacto = tipocontacto;
	        this.valorcontacto = valorcontacto;
	        this.comentario += comentario;

	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public int getId() {
	        return this.id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public double getLat() {
	        return lat;
	    }

	    public void setLat(double lat) {
	        this.lat = lat;
	    }

	    public double getLon() {
	        return lon;
	    }

	    public void setLon(double lon) {
	        this.lon = lon;
	    }
	    
	    public String getTipoComida() {
	        return tipocomida;
	    }

	    public void setTipoComida(String tipocomida) {
	        this.tipocomida = tipocomida;
	    }
	    
	    public long getSomaVotos() {
	        return somavotos;
	    }

	    public void setSomaVotos(long somavotos) {
	        this.somavotos = somavotos;
	    }
	    public int getNumVotos() {
	        return numvotos;
	    }

	    public void setNumVotos(int numvotos) {
	        this.numvotos = numvotos;
	    }
	    public double getClassificacao() {
	        return somavotos/numvotos;
	    }

	    public void setClassificacao(long somavotos) {
	        this.somavotos += somavotos;
	        this.numvotos++;
	    }
	    
	    public String getGamaPreco() {
	        return gamapreco;
	    }

	    public void setGamaPreco(String gamapreco) {
	        this.gamapreco = gamapreco;
	    }
	    
	    public int getTipoContacto() {
	        return tipocontacto;
	    }

	    public void setTipoContacto(int tipocontacto) {
	        this.tipocontacto = tipocontacto;
	    }
	    
	    public String getValorContacto() {
	        return valorcontacto;
	    }

	    public void setValorContacto(String valorcontacto) {
	        this.valorcontacto = valorcontacto;
	    }
	    
	    public String getComentario() {
	        return comentario;
	    }

	    public void setComentario(String comentario) {
	    	
	    	if (this.comentario.equals(""))
	    		this.comentario = comentario;
	    	else
	    		this.comentario += "\n" + comentario;
	    }
	
}

