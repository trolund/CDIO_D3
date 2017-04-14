package RESTResources;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import data.Connector;
import data.dao.DALException;
import data.dao.SQLOperatorDAO;
import data.dao.SQLRoleDAO;
import data.dto.OperatorDTO;
import data.dto.RoleDTO;
import utils.SecUtils;

@Path("/opr")
public class Operator {

	/*
	 * I stedet for at have en oprDAO i hver metode, hvorfor saa ikke
	 * have en final global oprDAO?
	 */


	@POST
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public String verify(@FormParam("oprId") String oprId, @FormParam("password") String password) {
		SQLOperatorDAO oprDAO = new SQLOperatorDAO(Connector.getInstance());
		SecUtils secUtil = SecUtils.getInstance();

		OperatorDTO oprDTO = null;
		try {
			oprDTO = oprDAO.getOperator(Integer.parseInt(oprId));
		} catch (DALException e) {
			e.printStackTrace();
			return "ID does not exist.";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "Invalid ID.";
		}

		if (secUtil.sha256(password).equals(oprDTO.getOprPassword())) {
			return "Correct password.";
		} else {
			return "Invalid credentials.";
		}
	}
	
	@POST
	@Path("/AddOpr")
	@Produces(MediaType.TEXT_PLAIN)
	public String Addopr(@FormParam("oprId") String oprId, 
			@FormParam("password") String password,
			@FormParam("Ini") String Ini,
			@FormParam("Name") String Name,
			@FormParam("CPR") String Cpr,
			@FormParam("Role1") String Role){
		SQLOperatorDAO oprDAO = new SQLOperatorDAO(Connector.getInstance());
		SQLRoleDAO oprroleDAO = new SQLRoleDAO(Connector.getInstance());
		OperatorDTO oprDTO = new OperatorDTO(Integer.parseInt(oprId), Name, Ini, Cpr, password);
		
			RoleDTO roleDTO = new RoleDTO(Integer.parseInt(oprId), Role);
		
		try {
			oprDAO.createOperator(oprDTO);
			if(roleDTO.getRoleName().equals("None")){
			}
			else{
				oprroleDAO.createRole(roleDTO);
			}
			System.out.println("User - id: " + roleDTO.getOprId() + ", Name: " + roleDTO.getRoleName() + " added!");
			return "User - id: " + roleDTO.getOprId() + ", Name: " + roleDTO.getRoleName() + " added!";
			
		} catch (DALException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return"UPS! - Der skete en fejl";
	}

	@GET
	@Path("/{OprId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getName(@PathParam("OprId") String OprId) {
		SQLOperatorDAO oprDAO = new SQLOperatorDAO(Connector.getInstance());
		
		int Id = Integer.parseInt(OprId);

		List<OperatorDTO> oprList = null;
		try {
			oprList = oprDAO.getOperatorList();
		} catch (DALException e) {
			e.printStackTrace();
		}
		
		for (Iterator iterator = oprList.iterator(); iterator.hasNext();) {
			OperatorDTO operatorDTO = (OperatorDTO) iterator.next();
			if(operatorDTO.getOprId() == Id){
				return operatorDTO.getOprName();
			}
			
		}
		return "Der er ingen bruger med det id.";
	}

	@GET
	@Path("/getOprList")
	@Produces(MediaType.TEXT_HTML)
	public String getopr() {
		SQLOperatorDAO oprDAO = new SQLOperatorDAO(Connector.getInstance());

		StringBuilder returnString = new StringBuilder();

		List<OperatorDTO> oprList = null;
		try {
			oprList = oprDAO.getOperatorList();
		} catch (DALException e) {
			e.printStackTrace();
		}

		for (OperatorDTO oprDTO : oprList) {
			returnString.append("<table>");
			returnString.append("<tr>" + "<td>" + oprDTO.getOprId() + "</td>" + "<td>" + oprDTO.getOprIni() + "</td>" + "<td>" + oprDTO.getOprName() + "</td>" + "<td>" + oprDTO.getOprCpr() + "</td>" + "<td>" + oprDTO.getOprPassword() + "</td>" + "</tr>");
			returnString.append("</table>");
		}
		return returnString.toString();
	}
	
	@GET
	@Path("/getOprRoleList/{OprId}")
	@Produces(MediaType.TEXT_HTML)
	public String getoprRoleList(@PathParam("OprId") String OprId) {
		SQLRoleDAO oprDAO = new SQLRoleDAO(Connector.getInstance());

		StringBuilder returnString = new StringBuilder();
		List<RoleDTO> oprRoleList  = null;
		try {
			oprRoleList = oprDAO.getOprRoles(Integer.parseInt(OprId));
		} catch (DALException e) {
			e.printStackTrace();
		}
		
		for (Iterator iterator = oprRoleList.iterator(); iterator.hasNext();) {
			RoleDTO roleDTO = (RoleDTO) iterator.next();
			if(iterator.hasNext())
				returnString.append(roleDTO.getRoleName() + ", ");
			else
				returnString.append(roleDTO.getRoleName());
		}
		
		return returnString.toString();
	}

}