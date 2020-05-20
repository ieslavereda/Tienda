package model;

import java.awt.Image;
import java.io.File;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;

import configuracion.ConfiguracionSegura;

public class Modelo extends DataBase {

	public Modelo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<String> obtenerAtributosTabla(String table) {
		List<String> atributos = new ArrayList<String>();
		ConfiguracionSegura conf = new ConfiguracionSegura();

		// select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME =
		// 'Cliente' AND TABLE_SCHEMA='bbddJava';

		String fields = "COLUMN_NAME";
		String tables = "INFORMATION_SCHEMA.COLUMNS";
		String where = "TABLE_NAME = '" + table + "' AND TABLE_SCHEMA='" + conf.getDatabase() + "'";
		String query = "SELECT " + fields + " FROM " + tables + " WHERE " + where;

		try (Connection con = this.conectar();
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(query);) {

			while (rs.next()) {
				atributos.add(rs.getString("COLUMN_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return atributos;
	}

	public Map<Integer, Categoria> obtenerCategorias() {
		Map<Integer, Categoria> categorias = new LinkedHashMap<Integer, Categoria>();
		Map<Integer, ArrayList<Object>> resultado = selectArray("id,descripcion", "Categoria", "id IS NOT NULL");

		for (Integer key : resultado.keySet()) {
			categorias.put(key, new Categoria(Integer.parseInt(resultado.get(key).get(0).toString()),
					resultado.get(key).get(1).toString()));
		}
		return categorias;
	}

	public boolean insertarProducto(Producto producto) {

		String table = "Articulo";
		String fields = "descArticulo,existencias,pvp,categoriaId";
		String values = "\"" + producto.getDescArticulo() + "\"" + "," + producto.getExistencias() + ","
				+ producto.getPvp() + "," + producto.getCategoriaId();

		return this.insertar(table, fields, values);
	}

	public boolean actualizarProducto(Producto producto) {
		String table = "Articulo";
		String values = "descArticulo=\"" + producto.getDescArticulo() + "\"" + ",existencias="
				+ producto.getExistencias() + ",pvp=" + producto.getPvp() + ",categoriaId=" + producto.getCategoriaId();
		String where = "id=" + producto.getId();

		return this.actualizar(table, values, where);
	}

	public Map<Integer, Producto> obtenerProductos() {

		return obtenerProductos(null, null, null);

	}

	public Map<Integer, Producto> obtenerProductos(String catId, String field, String order) {

		String where;

		if (catId == null)
			where = "categoriaId IS NOT NULL";
		else
			where = "categoriaId=" + catId;

		if (field != null)
			where += " ORDER BY " + field;

		if (order != null)
			where += " " + order;

		Map<Integer, Producto> productos = new LinkedHashMap<Integer, Producto>();
		Map<Integer, ArrayList<Object>> resultado = selectArray("id,descArticulo,existencias,pvp,categoriaId",
				"Articulo", where);
		int id;
		String descArticulo;
		int existencias;
		float pvp;
		int categoriaId;

		for (Integer key : resultado.keySet()) {

			id = Integer.parseInt(resultado.get(key).get(0).toString());
			descArticulo = resultado.get(key).get(1).toString();
			existencias = Integer.parseInt(resultado.get(key).get(2).toString());
			pvp = Float.parseFloat(resultado.get(key).get(3).toString());
			categoriaId = Integer.parseInt(resultado.get(key).get(4).toString());

			productos.put(key, new Producto(id, descArticulo, existencias, pvp, categoriaId));
		}

		return productos;
	}

	public Producto obtenerProducto(String idProducto) {

		Producto producto;
		Map<Integer, ArrayList<Object>> resultado = selectArray("id,descArticulo,existencias,pvp,categoriaId",
				"Articulo", "id=" + idProducto);

		int id = Integer.parseInt(idProducto);
		String descArticulo;
		int existencias;
		float pvp;
		int categoriaId;

		descArticulo = resultado.get(id).get(1).toString();
		existencias = Integer.parseInt(resultado.get(id).get(2).toString());
		pvp = Float.parseFloat(resultado.get(id).get(3).toString());
		categoriaId = Integer.parseInt(resultado.get(id).get(4).toString());

		producto = new Producto(id, descArticulo, existencias, pvp, categoriaId);

		return producto;
	}

	public Categoria obtenerCategoria(String id) {
		Categoria categoria;
		Map<Integer, ArrayList<Object>> resultado = selectArray("id,descripcion", "Categoria", " id=" + id);

		Iterator<Integer> it = resultado.keySet().iterator();
		Integer key = it.next();

		categoria = new Categoria(Integer.parseInt(resultado.get(key).get(0).toString()),
				resultado.get(key).get(1).toString());

		return categoria;
	}

	public boolean insertarCategoria(String descripcion) {

		return insertar("Categoria", "descripcion", "\"" + descripcion + "\"");
	}

	public boolean actualizarCategoria(String descripcion, int id) {
		return actualizar("Categoria", " descripcion=\"" + descripcion + "\"", "id=" + id);
	}

	public boolean insertarPersona(Persona p) {

		String table = "Cliente";
		String fields = "nombre,apellidos,dni,fecha_nacimiento,foto";
		String values = "\"" + p.getNombre() + "\",\"" + p.getApellidos() + "\",\"" + p.getDNI() + "\",\""
				+ (java.sql.Date.valueOf(p.getFechaNacimiento())) + "\"," + p.getFoto();

		return this.insertar(table, fields, values);

	}

	public boolean actualizarCliente(String id, Persona p, File f) {
		Date fecha = Date.valueOf(p.getFechaNacimiento());

		return this.actualizarC(Integer.parseInt(id), p.getNombre(), p.getApellidos(), p.getDNI(), f, fecha);
	}

	public Connection getConnection() {
		return conectar();
	}

	public boolean insertarCliente(Persona p, File foto) {
		String nombre = p.getNombre();
		String apellidos = p.getApellidos();
		String DNI = p.getDNI();

		java.sql.Date fecha = java.sql.Date.valueOf(p.getFechaNacimiento());

		return this.insertarCliente(nombre, apellidos, DNI, foto, fecha);
	}

	public boolean eliminarUsuario(int id) {
		return eliminar("Cliente", "id=" + id);
	}

	public boolean eliminarCategoria(int id) {
		return eliminar("Categoria", "id=" + id);
	}

	public boolean eliminarProducto(int id) {
		return eliminar("Articulo", "id=" + id);
	}

	public boolean autentifica(String login, String password) {

		if (contar(login, password) == 1)
			return true;
		else
			return false;

	}

	public Map<Integer, Persona> obtenerPersonas() {

		Map<Integer, Persona> map = new LinkedHashMap<Integer, Persona>();
		String fields = "id,nombre,apellidos,dni,fecha_nacimiento,foto";
		String tables = "Cliente";
		String where = " id IS NOT NULL ORDER BY id ";
		Map<Integer, Object[]> mapConsulta = this.select(fields, tables, where);

		Persona persona;
		String nombre;
		String apellidos;
		String dni;
		LocalDate fecha_nacimiento;
		Image foto;

		for (Integer key : mapConsulta.keySet()) {

			nombre = mapConsulta.get(key)[1].toString();
			apellidos = mapConsulta.get(key)[2].toString();
			dni = mapConsulta.get(key)[3].toString();
			fecha_nacimiento = java.sql.Date.valueOf(mapConsulta.get(key)[4].toString()).toLocalDate();
			foto = null;

			persona = new Persona(key, nombre, apellidos, dni, fecha_nacimiento, foto);

			map.put(key, persona);
		}

		return map;
	}

	public Persona obtenerCliente(String id) {
		Persona persona = null;

		String nombre;
		String apellidos;
		String dni;
		LocalDate fecha_nacimiento;
		Image foto = null;
		Blob fotoBlob;

		String query = "SELECT id,nombre,apellidos,dni,fecha_nacimiento,foto FROM Cliente WHERE id=" + id;

		try (Connection con = this.conectar();
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(query);

		) {
			byte[] content = null;
			while (rs.next()) {
				nombre = rs.getString(2);
				apellidos = rs.getString(3);
				dni = rs.getString(4);
				fecha_nacimiento = rs.getDate(5).toLocalDate();
				fotoBlob = rs.getBlob(6);
				if (fotoBlob != null) {
					content = fotoBlob.getBytes(1L, (int) fotoBlob.length());
					foto = (new ImageIcon(content)).getImage();
				}
				persona = new Persona(Integer.parseInt(id), nombre, apellidos, dni, fecha_nacimiento, foto);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return persona;
	}

}
