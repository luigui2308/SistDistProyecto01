package storage;

import beans.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Almacen de datos donde se guardan todos las Localidades
 * @see Location
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
public class Locations extends HashMap<String, Location>
{
	private static String[][] data =
	{
		{"2","1589262537","5000","100","false"}
		, {"3","1589262537","7500","25","false"}
		, {"4","1589262537","9500","25","true"}
		, {"5","1589262537","15000","25","true"}
		, {"0","586998177","10000","500","false"}
		, {"1","586998177","12500","500","true"}
		, {"2","586998177","20000","200","false"}
		, {"3","586998177","25000","100","true"}
		, {"4","586998177","25000","50","true"}
		, {"5","586998177","50000","20","true"}
		, {"3","1808835074","1000","50","false"}
	};

	private static Locations instance;
	/**
	 * Singleton
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public static Locations getInstance()
	{
		if (instance == null)
		{
			instance = new Locations();
		}
		return instance;
	}
	
	/**
	 * Constructor que inicializa el almacen de datos
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	private Locations() {
		for (int a = 0; a < data.length; a++)
		{
			Location instance = new Location();
			instance.setLocationType(Integer.parseInt(data[a][0]));
			instance.setEventCode(Integer.parseInt(data[a][1]));
			instance.setPrice(Integer.parseInt(data[a][2]));
			instance.setQuantity(Integer.parseInt(data[a][3]));
			instance.setNumbered(Boolean.parseBoolean(data[a][4]));//"true", "false"
			super.put(instance.getLocationType() + "$" + instance.getEventCode(), instance);
		}
	}
	
	/**
	 * Obtiene todas las localidades que coincidan con un codigo de evento
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public ArrayList<Location> getByEventCode(int eventCode)
	{
		ArrayList<Location> values = new ArrayList<Location>();
		for (Location location : super.values())
		{
			if (location.getEventCode() == eventCode)
			{
				values.add(location);
			}
		}
		return values;
	}
}