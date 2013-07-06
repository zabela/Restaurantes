package ua.pt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import java.util.LinkedList;

public class Adapter implements RestInterface{
	
	private Context context = null;
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "restaurantes4";
    private static final String DATABASE_TABLE = "restTable4";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE
            + " (" + Restaurante.KEY_ROWID + " integer primary key autoincrement, "
            + Restaurante.KEY_NAME + " text not null,"
            + Restaurante.KEY_ADDRESS + "  text not null, "
            + Restaurante.KEY_LAT + " text not null,"
            + Restaurante.KEY_LON + " text not null,"
            + Restaurante.KEY_TIPOCOMIDA + "  text, "
            + Restaurante.KEY_SOMAVOTOS + " text,"
            + Restaurante.KEY_NUMVOTOS + " text,"
            + Restaurante.KEY_GAMAPRECO + " text,"
            + Restaurante.KEY_TIPOCONTACTO + " text,"
            + Restaurante.KEY_VALORCONTACTO + " text,"
            + Restaurante.KEY_COMENTARIO + " text);"
            ;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public Adapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }

    public Adapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();

        return this;
    }

    public void close() {
        DBHelper.close();
    }
    
    public Cursor query(String rowId, String selection, String[] selectionArgs, String sortOrder) {


        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        sqlBuilder.setTables(DATABASE_TABLE);

        if (rowId != null) {
            sqlBuilder.appendWhere(
            		Restaurante.KEY_ROWID + " = " + rowId);
        }

        if (sortOrder == null || sortOrder == "") {
            sortOrder = Restaurante.KEY_NAME;
        }

        Cursor c = sqlBuilder.query(
                db,
                CONTACT_SCHEMA,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        return c;
    }
    
    public static final String[] CONTACT_SCHEMA = new String[]{
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
        Restaurante.KEY_COMENTARIO};

    
    public boolean deleteAllRests() {

        db.delete(
                DATABASE_TABLE,
                null,
                null);

        return true;
    }
    
    public long deleteAllRests(String whereClause, String[] whereArgs) {
        return db.delete(
                DATABASE_TABLE,
                whereClause,
                whereArgs);
    }
    
    //---retrieves all the titles---
    public Cursor getAllRests() {
        return db.query(DATABASE_TABLE, CONTACT_SCHEMA,
                null,
                null,
                null,
                null,
                null);
    }
    
    public Restaurante getRestFromCursor(Cursor c) {

    	Restaurante b = new Restaurante(
    			c.getString(Restaurante.NAME_ID),
                c.getString(Restaurante.ADDRESS_ID),
                c.getDouble(Restaurante.LAT_ID),
                c.getDouble(Restaurante.LON_ID),
    			c.getString(Restaurante.TIPOCOMIDA_ID),
    			c.getLong(Restaurante.SOMAVOTOS_ID),
    			c.getInt(Restaurante.NUMVOTOS_ID),
    			c.getString(Restaurante.GAMAPRECO_ID),
    			c.getInt(Restaurante.TIPOCONTACTO_ID),
    			c.getString(Restaurante.VALORCONTACTO_ID),
    			c.getString(Restaurante.COMENTARIO_ID));

        b.setId(c.getInt(Restaurante._ID));
        return b;
    }


	public void classificar(long rowId, int classificacao) {
		
		Cursor c = db.query(DATABASE_TABLE, CONTACT_SCHEMA,
				Restaurante.KEY_ROWID + "=" + rowId,
                null,
                null,
                null,
                null);
		
		c.moveToFirst();
    	long somavotos = c.getLong(Restaurante.SOMAVOTOS_ID);
    	int numvotos = c.getInt(Restaurante.NUMVOTOS_ID);
    	
    	somavotos += classificacao;
    	numvotos++;
    	

		ContentValues values = new ContentValues();
		
		values.put(Restaurante.KEY_SOMAVOTOS, somavotos);
		values.put(Restaurante.KEY_NUMVOTOS, numvotos);
		String whereClause = Restaurante.KEY_ROWID + "=" + rowId;
		
		db.update(DATABASE_TABLE, values, whereClause, null);

	}

	public void comentar(long rowId, String comentario) {
		
		Cursor c = db.query(DATABASE_TABLE, CONTACT_SCHEMA,
				Restaurante.KEY_ROWID + "=" + rowId,
                null,
                null,
                null,
                null);
		
		c.moveToFirst();
    	String comment = c.getString(Restaurante.COMENTARIO_ID);
    	
    	if(comment.equals("")){
    		comment = comentario;
    	}else{
    		comment += "\n" + comentario;
    	}
    	

		ContentValues values = new ContentValues();
		
		values.put(Restaurante.KEY_COMENTARIO, comment);
		String whereClause = Restaurante.KEY_ROWID + "=" + rowId;
		
		db.update(DATABASE_TABLE, values, whereClause, null);
        
	}

	public Restaurante getRestbyName(String nome) {
		
        Cursor mCursor = 
                db.query(true, DATABASE_TABLE, CONTACT_SCHEMA,
                		Restaurante.KEY_NAME + " LIKE " + "'"+nome+"'",
                null,
                null,
                null,
                null,
                null);
        if (mCursor != null) {
            mCursor.moveToFirst();

            return getRestFromCursor(mCursor);
        }
        return null;
	}
	
	public Restaurante getRestbyID(long id) {
		
        Cursor mCursor = 
                db.query(true, DATABASE_TABLE, CONTACT_SCHEMA,
                		Restaurante.KEY_ROWID + "= '" + id + "'",
                null,
                null,
                null,
                null,
                null);
        if (mCursor != null) {
            mCursor.moveToFirst();

            return getRestFromCursor(mCursor);
        }
        return null;
	}

	public LinkedList<Restaurante> getRests0() {
		Cursor c = db.query(DATABASE_TABLE, CONTACT_SCHEMA,
				null,
                null,
                null,
                null,
                null);

        LinkedList<Restaurante> restlist = new LinkedList<Restaurante>();;

        if (c.moveToFirst()) {
            do {
            	Restaurante r = getRestFromCursor(c);
        		restlist.add(r);

            } while (c.moveToNext());
        }

        return restlist;
	}

	public LinkedList<Restaurante> getRests01(String tipocomida) {
    	
        Cursor c = 
                db.query(true, DATABASE_TABLE, CONTACT_SCHEMA,
                		Restaurante.KEY_TIPOCOMIDA + " LIKE '" + tipocomida + "'",
                null,
                null,
                null,
                null,
                null);
        
        LinkedList<Restaurante> restlist = new LinkedList<Restaurante>();
        
        if (c.moveToFirst()) {
            do {
            	Restaurante r = getRestFromCursor(c);
        		restlist.add(r);

            } while (c.moveToNext());
        }

        return restlist;
	}

	public LinkedList<Restaurante> getRests012(String tipocomida, int classificacao) {
		
        Cursor c = 
                db.query(true, DATABASE_TABLE, CONTACT_SCHEMA,
                		Restaurante.KEY_TIPOCOMIDA + " LIKE '" + tipocomida + "'",
                null,
                null,
                null,
                null,
                null);
        
        LinkedList<Restaurante> restlist = new LinkedList<Restaurante>();
        
        if (c.moveToFirst()) {
            do {
            	
            	if((c.getLong(Restaurante.SOMAVOTOS_ID)/c.getInt(Restaurante.NUMVOTOS_ID)>= classificacao) && (c.getLong(Restaurante.SOMAVOTOS_ID)/c.getInt(Restaurante.NUMVOTOS_ID)< classificacao+1))
            	{
            		Restaurante r = getRestFromCursor(c);
            		restlist.add(r);
            	}
 
            } while (c.moveToNext());
        }

        return restlist;
	}

	public LinkedList<Restaurante> getRests0123(String tipocomida, int classificacao, String gama) {
		
		String selectionArg = Restaurante.KEY_TIPOCOMIDA + " LIKE '" + tipocomida + "' and " + Restaurante.KEY_GAMAPRECO + " LIKE '" + gama + "'";
    	
        Cursor c = 
                db.query(true, DATABASE_TABLE, CONTACT_SCHEMA,
                		selectionArg,
                		null,
                null,
                null,
                null,
                null);
        
        LinkedList<Restaurante> restlist = new LinkedList<Restaurante>();;
        
        if (c.moveToFirst()) {
            do {
            	
            	if((c.getLong(Restaurante.SOMAVOTOS_ID)/c.getInt(Restaurante.NUMVOTOS_ID)>= classificacao) && (c.getLong(Restaurante.SOMAVOTOS_ID)/c.getInt(Restaurante.NUMVOTOS_ID)< classificacao+1))
            	{
            		Restaurante r = getRestFromCursor(c);
            		restlist.add(r);
            	}
 
            } while (c.moveToNext());
        }

        return restlist;
	}

	public LinkedList<Restaurante> getRests013(String tipocomida, String gama) {
		
    	String selectionArg = Restaurante.KEY_TIPOCOMIDA + "= '" + tipocomida + "' and " + Restaurante.KEY_GAMAPRECO + "= '" + gama + "'";
    	
        Cursor c = 
                db.query(true, DATABASE_TABLE, CONTACT_SCHEMA,
                		selectionArg,
                		null,
                null,
                null,
                null,
                null);
        
        LinkedList<Restaurante> restlist = new LinkedList<Restaurante>();;
        
        if (c.moveToFirst()) {
            do {
            	Restaurante r = getRestFromCursor(c);
        		restlist.add(r);

            } while (c.moveToNext());
        }

        return restlist;
	}

	public LinkedList<Restaurante> getRests02(int classificacao) {

        Cursor c = 
                db.query(true, DATABASE_TABLE, CONTACT_SCHEMA,
                null,
                null,
                null,
                null,
                null,
                null);
        
        LinkedList<Restaurante> restlist = new LinkedList<Restaurante>();
        
        if (c.moveToFirst()) {
            do {
            	
            	if((c.getLong(Restaurante.SOMAVOTOS_ID)/c.getInt(Restaurante.NUMVOTOS_ID)>= classificacao) && (c.getLong(Restaurante.SOMAVOTOS_ID)/c.getInt(Restaurante.NUMVOTOS_ID)< classificacao+1))
            	{
            		Restaurante r = getRestFromCursor(c);
            		restlist.add(r);
            	}
 
            } while (c.moveToNext());
        }

        return restlist;
	}

	public LinkedList<Restaurante> getRests023(int classificacao, String gama) {
		  	
        Cursor c = 
                db.query(true, DATABASE_TABLE, CONTACT_SCHEMA,
                		Restaurante.KEY_GAMAPRECO + "= '" + gama + "'",
                null,
                null,
                null,
                null,
                null);
        
        LinkedList<Restaurante> restlist = new LinkedList<Restaurante>();;
        
        if (c.moveToFirst()) {
            do {
            	
            	if((c.getLong(Restaurante.SOMAVOTOS_ID)/c.getInt(Restaurante.NUMVOTOS_ID)>= classificacao) && (c.getLong(Restaurante.SOMAVOTOS_ID)/c.getInt(Restaurante.NUMVOTOS_ID)< classificacao+1))
            	{
            		Restaurante r = getRestFromCursor(c);
            		restlist.add(r);
            	}
 
            } while (c.moveToNext());
        }

        return restlist;
	}

	public LinkedList<Restaurante> getRests03(String gama) {
    	
        Cursor c = 
                db.query(true, DATABASE_TABLE, CONTACT_SCHEMA,
                		Restaurante.KEY_GAMAPRECO + "= '" + gama + "'",
                null,
                null,
                null,
                null,
                null);
        
        LinkedList<Restaurante> restlist = new LinkedList<Restaurante>();;
        
        if (c.moveToFirst()) {
            do {
            	Restaurante r = getRestFromCursor(c);
        		restlist.add(r);

            } while (c.moveToNext());
        }

        return restlist;
	}
	
	public long inserirR(String name, String address, double lat, double lon, String tipocomida, long somavotos, int numvotos, String gamapreco, int tipocontacto, String valorcontacto, String comentario){

			ContentValues values = new ContentValues();
			values.put(Restaurante.KEY_NAME, name);
			values.put(Restaurante.KEY_ADDRESS, address);
			values.put(Restaurante.KEY_LAT, lat);
			values.put(Restaurante.KEY_LON, lon);
			values.put(Restaurante.KEY_TIPOCOMIDA, tipocomida);
			values.put(Restaurante.KEY_SOMAVOTOS, somavotos);
			values.put(Restaurante.KEY_NUMVOTOS, numvotos);
			values.put(Restaurante.KEY_GAMAPRECO, gamapreco);
			values.put(Restaurante.KEY_TIPOCONTACTO, tipocontacto);
			values.put(Restaurante.KEY_VALORCONTACTO, valorcontacto);
			values.put(Restaurante.KEY_COMENTARIO, comentario);
			return db.insert(DATABASE_TABLE, null, values);
	}

	public long insertR(String string, String string2, double d, double e) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put(Restaurante.KEY_NAME, string);
		values.put(Restaurante.KEY_ADDRESS, string2);
		values.put(Restaurante.KEY_LAT, d);
		values.put(Restaurante.KEY_LON, e);
		
		return db.insert(DATABASE_TABLE, null, values);
	}

	public long insertRest(ContentValues values) {
		// TODO Auto-generated method stub
		return db.insert(DATABASE_TABLE, null, values);
	}

}
