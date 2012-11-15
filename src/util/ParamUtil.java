package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase de utilidades para la gestion del querystring de una pagina
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
public class ParamUtil
{
	/**
	 * Ayuda a analizar un querystring y dejarlo partido en un mapa de key values
	 * @param paramString querystring a parsear
	 * @returns mapa de keyvalues resultado de parsear el querystring
	 * @author Unknown
	 */
	public static Map<String,String> parse(String paramString)
	{
		Map<String,String> params = new HashMap<String,String>();
		for(String param : paramString.split("&"))
		{
			String[] key_value = param.split("=");
			params.put(key_value[0], key_value[1]);
		}
		return params;
	}
}