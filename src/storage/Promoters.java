package storage;

import beans.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Almacen de datos donde se guardan todos los Promotores
 * @see Promoter
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
public class Promoters extends HashMap<Integer, Promoter>
{
	
	private String[][] data =
	{
		{"1668757130","root","x","x","x","100","root"}
		, {"1753281862","Luis Roldan","Barva de Heredia","22573987","Banco de Costa Rica","10","luis"}
		, {"1948347202","Gilberth Arce","Barva de Heredia","22630736","Banco Nacional","13","gilberth"}
	};
	
	private static Promoters instance;
	/**
	 * Singleton
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public static Promoters getInstance()
	{
		if (instance == null)
		{
			instance = new Promoters();
		}
		return instance;
	}

	/**
	 * Constructor que inicializa el almacen de datos
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	private Promoters()
	{
		for (int a = 0; a < data.length; a++)
		{
			Promoter instance = new Promoter();
			instance.setCode(Integer.parseInt(data[a][0]));
			instance.setName(data[a][1]);
			instance.setAddress(data[a][2]);
			instance.setTelephone(data[a][3]);
			instance.setBank(data[a][4]);
			instance.setCommision(Integer.parseInt(data[a][5]));
			instance.setUsername(data[a][6]);
			super.put(instance.getCode(), instance);
		}
	}
	
	/**
	 * Obtiene un listado de los promotores que coincidan con un nombre de usuario
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public Promoter getByUsername(String username)
	{
		for (Promoter promoter : super.values())
		{
			if (promoter.getUsername().equals(username))
			{
				return promoter;
			}
		}
		return null;
	}
}