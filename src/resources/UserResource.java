package resources;

import beans.*;
import storage.*;
import java.net.URI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.sun.jersey.api.NotFoundException;

/**
 * Controla los eventos propios para la pantalla de usuarios
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
@Path("/user")
public class UserResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	private static Users users = Users.getInstance();
	private static Promoters promoters = Promoters.getInstance();
	private static RolesByUsers rolesByUsers = RolesByUsers.getInstance();

	/**
	 * Metodo que atiende una solicitud GET y confirma que un usuario tiene un rol especifico
	 * @return RoleByUser relacionado a los parametros establecidos
	 * @see NumberedTicket
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Path("/role")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public RolesByUser confirmRole(@QueryParam("username") String username, @QueryParam("role") int role)
	{
		return rolesByUsers.get(username + "$" + role);
	}

	/**
	 * Metodo que atiende una solicitud GET y verifica que exista un usuario que coincida con los parametros proporcionados
	 * @return Usuario que coincide con el username y el password
	 * @see NumberedTicket
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Path("/credentials")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public User confirmCredentials(@QueryParam("username") String username, @QueryParam("password") String password)
	{
		User user = users.get(username);
		if (user != null && user.getPassword().equals(password))
		{
			return user;
		}
		throw new NotFoundException("Not such object on this server!");
	}
}