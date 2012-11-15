package storage;

import beans.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Almacen de datos donde se guardan todos los Clientes
 * @see Customer
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
public class Customers extends HashMap<String, Customer>
{
	private static String[][] data = {};
	
	private static Customers instance;
	/**
	 * Singleton
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public static Customers getInstance()
	{
		if (instance == null)
		{
			instance = new Customers();
		}
		return instance;
	}
	
	/**
	 * Constructor que inicializa el almacen de datos
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	private Customers()
	{
		for (int a = 0; a < data.length; a++)
		{
			Customer instance = new Customer();
			instance.setId(data[a][0]);
			instance.setName(data[a][1]);
			instance.setAddress(data[a][2]);
			instance.setTelephone(data[a][3]);
			instance.setCardnumber(data[a][4]);
			instance.setCardType(data[a][5]);
			super.put(instance.getId(), instance);
		}
	}
}