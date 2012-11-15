package storage;

import beans.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Almacen de datos donde se guardan todos los usuarios
 * @see Usuario
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
public class Users extends HashMap<String, User>
{
	private static String[][] data = 
	{
		{"admin","pass"}
		, {"luis","pass"}
		, {"root","pass"}
		, {"gilberth","pass"}
	};

	private static Users instance;
	/**
	 * Singleton
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public static Users getInstance()
	{
		if (instance == null)
		{
			instance = new Users();
		}
		return instance;
	}
	
	/**
	 * Constructor que inicializa el almacen de datos
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	private Users()
	{
		for (int a = 0; a < data.length; a++)
		{
			User instance = new User();
			instance.setUsername(data[a][0]);
			instance.setPassword(data[a][1]);
			super.put(instance.getUsername(), instance);
		}
	}
}