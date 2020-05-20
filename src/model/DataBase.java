package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import configuracion.ConfiguracionSegura;

public class DataBase {

	protected DataBase() {
		super();
	}

	protected Connection conectar() {

		Connection conexion = null;
		ConfiguracionSegura conf = new ConfiguracionSegura();
		Properties prop = new Properties();
		prop.setProperty("user", conf.getUser());
		prop.setProperty("password", conf.getPassword());
		prop.setProperty("serverTimezone", "UTC");
		// prop.setProperty("noAccessToProcedureBodies","true");

		// System.out.println("jdbc:mysql://" + conf.getHost() + ":" +
		// conf.getPort() + "/" + conf.getDatabase() + " User:" + conf.getUser()
		// + " pass:" + conf.getPassword());
		try {
			conexion = DriverManager.getConnection(
					"jdbc:mysql://" + conf.getHost() + ":" + conf.getPort() + "/" + conf.getDatabase(), prop);

			// if (conexion != null) System.out.println("Conexión realizada con
			// éxito.");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en la conexion.\nRevise los parametros.", "Error",
					JOptionPane.ERROR_MESSAGE, null);
			// System.out.println("FALLOOOOO EXCEPCION!!!");
			e.printStackTrace();
		}
		return conexion;
	}

	protected boolean insertar(String table, String fields, String values) {

		String sql = " INSERT INTO " + table + " ( " + fields + " ) VALUES ( " + values + " ) ";
		// System.out.println(sql);

		try (Connection con = this.conectar(); Statement stm = con.createStatement();) {

			stm.execute(sql);
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	protected boolean eliminar(String table, String where) {
		if (where != null) {
			String sql = "DELETE FROM " + table + " WHERE " + where;
			// System.out.println(sql);
			try (Connection con = this.conectar(); Statement stm = con.createStatement();) {

				stm.execute(sql);
				return true;

			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

		} else
			return false;
	}

	public Map<Integer, ArrayList<Object>> selectArray(String fields, String tables, String where) {

		Map<Integer, ArrayList<Object>> resultado = new LinkedHashMap<Integer, ArrayList<Object>>();
		ArrayList<Object> lista;

		String query = "SELECT " + fields + " FROM " + tables;
		if (where != null)
			query += " WHERE " + where;

		// System.out.println(query);

		try (Connection con = conectar();
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(query)) {
			int numColumnas = rs.getMetaData().getColumnCount();

			while (rs.next()) {
				lista = new ArrayList<Object>();
				for (int i = 1; i <= numColumnas; i++)
					lista.add(rs.getObject(i));

				resultado.put(rs.getInt("id"), lista);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return resultado;
	}

	protected Map<Integer, Object[]> select(String fields, String tables, String where) {
		Map<Integer, Object[]> resultado = new LinkedHashMap<Integer, Object[]>();
		Object[] objeto;

		String query = "SELECT " + fields + " FROM " + tables;

		if (where != null)
			query += " WHERE " + where;

		// System.out.println(query);

		try (Connection con = this.conectar();
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(query);

		) {
			int numFields = rs.getMetaData().getColumnCount();

			while (rs.next()) {

				objeto = new Object[numFields];
				for (int j = 2; j <= numFields; j++)
					objeto[j - 1] = rs.getObject(j);

				resultado.put(rs.getInt("id"), objeto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultado;
	}

	protected boolean actualizar(String table, String values, String where) {
		String sql = " UPDATE " + table + " SET " + values + " WHERE " + where;
		// System.out.println(sql);

		try (Connection con = this.conectar(); Statement stm = con.createStatement();

		) {
			stm.execute(sql);
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	protected boolean actualizarC(int id, String nombre, String apellidos, String dni, File file,
			Date fecha_nacimiento) {
		String sql;
		if (file != null) {
			sql = "UPDATE Cliente SET nombre=?,apellidos=?,dni=?,fecha_nacimiento=?,foto=? WHERE id=? ";
		} else {
			sql = "UPDATE Cliente SET nombre=?,apellidos=?,dni=?,fecha_nacimiento=? WHERE id=? ";
		}
		FileInputStream fis = null;

		try (Connection con = this.conectar(); PreparedStatement pstm = con.prepareStatement(sql);

		) {

			if (file != null)
				fis = new FileInputStream(file);
			pstm.setString(1, nombre);
			pstm.setString(2, apellidos);
			pstm.setString(3, dni);
			pstm.setDate(4, fecha_nacimiento);
			if (file == null) {
				pstm.setInt(5, id);
			} else {
				pstm.setBinaryStream(5, fis, (int) file.length());
				pstm.setInt(6, id);
			}

			pstm.execute();

			return true;
		} catch (SQLException | IOException sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	protected boolean insertarCliente(String nombre, String apellidos, String dni, File file, Date fecha_nacimiento) {

		String sql = " INSERT INTO Cliente (nombre,apellidos,dni,foto,fecha_nacimiento) VALUES (?,?,?,?,?) ";
		FileInputStream fis = null;

		try (Connection con = this.conectar(); PreparedStatement pstm = con.prepareStatement(sql);

		) {

			if (file != null)
				fis = new FileInputStream(file);
			pstm.setString(1, nombre);
			pstm.setString(2, apellidos);
			pstm.setString(3, dni);
			if (file == null)
				pstm.setBinaryStream(4, null);
			else
				pstm.setBinaryStream(4, fis, (int) file.length());
			pstm.setDate(5, fecha_nacimiento);

			pstm.execute();

			return true;
		} catch (SQLException | IOException sqle) {
			sqle.printStackTrace();
			return false;
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	protected int crearFac(int codCliente) throws SQLException {

		int numFactura = 0;

		String sql = "{call crear_factura( ? , ? )}";

		try (Connection con = conectar(); CallableStatement callableStatement = con.prepareCall(sql)) {

			callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStatement.setInt(2, codCliente);

			callableStatement.execute();

			numFactura = callableStatement.getInt(1);

		}
		return numFactura;
	}

	
	protected int contar(String login, String password) {
		
		// SQL injection
		// 222') or login="pepe" and password<>sha1('222

		int cantidad = 0;
		String sql = "SELECT COUNT(*) FROM Usuario WHERE login=\"" + login + "\" AND password=sha1('" + password + "')";
		// System.out.println(sql);
		try (Connection con = conectar(); Statement stm = con.createStatement();) {

			ResultSet rs = stm.executeQuery(sql);

			rs.next();
			cantidad = rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cantidad;
	}

}
