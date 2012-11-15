package beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa un ticket numerado para el sistema, estos estan asociados a los tickets, un ticket puede
 * tener tantos tickets numerados como sea posible (limitado solo por la cantidad de entradas descritas
 * en la localidad a la cual esta relacionada el ticket) para tener tickets numerados, el ticket debe estar
 * marcado como numerado
 * @see Ticket
 * @see Location
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
@XmlRootElement
public class NumberedTicket
{
	private int locationNumber;
	private int ticketCode;
	
	/**
	 * Constructor sin parametros que ayuda a la creacion de objetos vacios
	 * Estos son funcionales cuando no se cuenta con toda la informacion para la creacion del objeto
	 * o se quiere llenar la informacion poco a poco
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public NumberedTicket()
	{
	}
	
	/**
	 * Constructor con parametros que ayuda a la creacion de objetos llenos
	 * Estos son funcionales cuando se cuenta con toda la informacion para la creacion del objeto
	 * y/o no se quiere guardar una referencia hacia un objeto
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public NumberedTicket(int locationNumber, int ticketCode)
	{
		this.locationNumber=locationNumber;
		this.ticketCode=ticketCode;
	}
	
	/**
	 * Metodo que que establece el valor de una propiedad determinada (set, mutador) segun un valor pasado por parametro
	 * Ayuda a la encapsulacion de los datos y si se tiene codigo que se ejecuta despues de establecer una propiedad
	 * @param locationNumber nuevo valor del atributo
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public void setLocationNumber(int locationNumber)
	{
		this.locationNumber=locationNumber;
	}
	
	/**
	 * Metodo que que obtiene el valor de una propiedad determinada (get, accesor) segun el valor de la propiedad
	 * Ayuda a la encapsulacion de los datos y si se tiene codigo que se ejecuta despues de obtener una propiedad
	 * @return valor del atributo asociado a este accesor
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public int getLocationNumber()
	{
		return this.locationNumber;
	}
	
	/**
	 * Metodo que que establece el valor de una propiedad determinada (set, mutador) segun un valor pasado por parametro
	 * Ayuda a la encapsulacion de los datos y si se tiene codigo que se ejecuta despues de establecer una propiedad
	 * @param ticketCode nuevo valor del atributo
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public void setTicketCode(int ticketCode)
	{
		this.ticketCode=ticketCode;
	}
	
	/**
	 * Metodo que que obtiene el valor de una propiedad determinada (get, accesor) segun el valor de la propiedad
	 * Ayuda a la encapsulacion de los datos y si se tiene codigo que se ejecuta despues de obtener una propiedad
	 * @return valor del atributo asociado a este accesor
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public int getTicketCode()
	{
		return this.ticketCode;
	}
}