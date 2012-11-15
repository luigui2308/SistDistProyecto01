package storage;

import beans.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Almacen de datos donde se guardan todos los Tickets
 * @see Ticket
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
public class Tickets extends HashMap<Integer, Ticket>
{
	private static String[][] data = {};

	private static Tickets instance;
	/**
	 * Singleton
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public static Tickets getInstance()
	{
		if (instance == null)
		{
			instance = new Tickets();
		}
		return instance;
	}
	
	/**
	 * Constructor que inicializa el almacen de datos
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	private Tickets()
	{
		for (int a = 0; a < data.length; a++)
		{
			Ticket instance = new Ticket();
			instance.setCode(Integer.parseInt(data[a][0]));
			instance.setLocationType(Integer.parseInt(data[a][1]));
			instance.setEventCode(Integer.parseInt(data[a][2]));
			instance.setCustomerId(data[a][3]);
			instance.setDate(data[a][4]);
			instance.setQuantity(Integer.parseInt(data[a][5]));
			instance.setAmount(Integer.parseInt(data[a][6]));
			super.put(instance.getCode(), instance);
		}
	}
	
	/**
	 * Obtiene todos los tickets que coincidan con un codigo de evento especifico
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public ArrayList<Ticket> getByEventCode(int eventCode)
	{
		ArrayList<Ticket> values = new ArrayList<Ticket>();
		for (Ticket ticket : super.values())
		{
			if(ticket.getEventCode() == eventCode)
			{
				values.add(ticket);
			}
		}
		return values;
	}
	
	/**
	 * Obtiene los tickets que coincidan con un codigo de evento y un tipo de localidad especifica
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public ArrayList<Ticket> findByEventAndLocation(int eventCode, int locationTypeId)
	{
		ArrayList<Ticket> values = new ArrayList<Ticket>();
		for (Ticket ticket : super.values())
		{
			if(ticket.getEventCode() == eventCode && ticket.getLocationType() == locationTypeId)
			{
				values.add(ticket);
			}
		}
		return values;
	}
}