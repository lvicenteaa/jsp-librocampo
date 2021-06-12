package tk.luisalbarracin.librocampo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tk.luisalbarracin.librocampo.dao.PropietarioDao;
import tk.luisalbarracin.librocampo.dao.PropietarioDaoFactory;
import tk.luisalbarracin.librocampo.modelo.Propietario;

/**
 * Servlet implementation class PropietarioServlet
 */
@WebServlet("/")
public class PropietarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PropietarioDao propietarioDao;
	
    /**
     * Default constructor. 
     */
    public PropietarioServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		String type = getServletContext().getInitParameter("type");
		this.propietarioDao = PropietarioDaoFactory.getUsuarioDao(type);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertarPropietario(request, response);
				break;
			case "/delete":
				eliminarPropietario(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				actualizarPropietario(request, response);
				break;
			default:
				listPropietarios(request, response);
				break;

			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer id = Integer.parseInt(request.getParameter("id"));
		
		Propietario propietarioActual = (Propietario) this.propietarioDao.buscar(id);
		
		request.setAttribute("propietario", propietarioActual);
		RequestDispatcher dispatcher = request.getRequestDispatcher("propietario.jsp");
		dispatcher.forward(request, response);
	}

	private void listPropietarios(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		// TODO Auto-generated method stub
		List<Propietario> propietarios = this.propietarioDao.selectAll();
		request.setAttribute("propietarios", propietarios);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("propietariolist.jsp");
		dispatcher.forward(request, response);
	}

	private void actualizarPropietario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		// TODO Auto-generated method stub
		Integer id = Integer.parseInt(request.getParameter("id"));
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String noCedula = request.getParameter("noCedula");
		String telefono = request.getParameter("telefono");
		String expedicionCedula = request.getParameter("expedicionCedula");
		
		Propietario propietario = new Propietario(id, nombre, apellido, noCedula, telefono, expedicionCedula);
		this.propietarioDao.actualizar(propietario);
		
		response.sendRedirect("list");
	}

	private void eliminarPropietario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		// TODO Auto-generated method stub
		Integer id = Integer.parseInt(request.getParameter("id"));
		
		this.propietarioDao.eliminar(id);
		
		response.sendRedirect("list");
	}

	private void insertarPropietario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		// TODO Auto-generated method stub
		
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String coCedula = request.getParameter("noCedula");
		String telefono = request.getParameter("telefono");
		String expedicionCedula = request.getParameter("expedicionCedula");
		
		Propietario propietario = new Propietario(nombre, apellido, coCedula, telefono, expedicionCedula);
		this.propietarioDao.insertar(propietario);
		
		response.sendRedirect("list");
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("propietario.jsp");
		dispatcher.forward(request, response);
	}

}
