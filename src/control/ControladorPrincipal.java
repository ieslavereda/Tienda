package control;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import configuracion.ConfiguracionSegura;
import control.ControladorReportClients;
import model.Modelo;
import model.Persona;
import view.JFramePrincipal;
import view.JIFrameFormularioUsuario;
import view.JIFrameConfiguracion;
import view.JIFrameFacturas;
import view.JIFrameListado;
import view.JIFrameLogin;
import view.JIFrameProductos;
import view.JIFrameReportClients;
import view.JIFrameReportProducts;
import view.JIFrameCategorias;

public class ControladorPrincipal implements ActionListener, MouseListener {

	private JFramePrincipal view;
	private Modelo model;

	private ControladorReportClients controladorReportClients;
	private ControladorReportProducts controladorReportProducts;

	// Formularios hijos
	JIFrameFormularioUsuario jifFormularioUsuario;
	JIFrameListado jifListado;
	JIFrameConfiguracion jifConfiguracion;
	JIFrameLogin jifLogin;
	JIFrameReportClients jifReportClients;
	JIFrameReportProducts jifReportProducts;
	JIFrameCategorias jifCategorias;
	JIFrameProductos jifProductos;
	JIFrameFacturas jifFacturas;

	// Campos temporales
	File fotoCliente;
	String idCliente;

	public ControladorPrincipal(JFramePrincipal view, Modelo model) {
		super();
		this.view = view;
		this.model = model;
		iniciar();
	}

	public void iniciar() {

		// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dim = new Dimension(800, 600);

		view.setTitle("Tienda IES");
		view.setMaximumSize(dim);
		view.setExtendedState(JFrame.MAXIMIZED_BOTH);
		view.setLocationRelativeTo(null);// centrado en pantalla

		// Se añade las acciones a los controles del formulario padre
		view.btnAddUser.setActionCommand("Abrir Formulario Usuario");
		view.btnListUser.setActionCommand("Listar Usuarios");
		view.btnConfiguracion.setActionCommand("Configuracion");
		view.btnFormLogin.setActionCommand("Abrir Formulario Login");
		view.btnSalir.setActionCommand("Cerrar sesion");
		view.btnReportClients.setActionCommand("Report clients");
		view.btnReportProducts.setActionCommand("Report products");
		view.btnCategories.setActionCommand("Categorias");
		view.btnProducts.setActionCommand("Productos");
		view.btnInvoice.setActionCommand("Invoice");

		// Se pone a escuchar las acciones del usuario
		view.btnAddUser.addActionListener(this);
		view.btnListUser.addActionListener(this);
		view.btnConfiguracion.addActionListener(this);
		view.btnFormLogin.addActionListener(this);
		view.btnSalir.addActionListener(this);
		view.btnReportClients.addActionListener(this);
		view.btnReportProducts.addActionListener(this);
		view.btnCategories.addActionListener(this);
		view.btnProducts.addActionListener(this);
		view.btnInvoice.addActionListener(this);

	}

	public void go() {
		view.setVisible(true);
	}

	private boolean estaAbierto(JInternalFrame o) {
		boolean existe = false;
		JInternalFrame[] frames = view.desktopPane.getAllFrames();
		for (JInternalFrame frame : frames)
			if (frame == o)
				existe = true;

		return existe;
	}

	private void abrirFacturas() {
		if(!estaAbierto(jifFacturas)){
			jifFacturas = new JIFrameFacturas();
			ControladorFacturas cFacturas= new ControladorFacturas(model,jifFacturas);
			view.desktopPane.add(jifFacturas);
			cFacturas.go();
		}
	}

	private void abrirCategorias() {
		if (!estaAbierto(jifCategorias)) {
			jifCategorias = new JIFrameCategorias();
			ControladorCategorias controladorCategorias = new ControladorCategorias(jifCategorias, model);
			view.desktopPane.add(jifCategorias);
			controladorCategorias.go();
		}
	}

	private void abrirProductos() {
		if (!estaAbierto(jifProductos)) {

			jifProductos = new JIFrameProductos();
			view.desktopPane.add(jifProductos);
			ControladorProductos controladorProductos = new ControladorProductos(model, jifProductos);
			controladorProductos.go();
			
		}
	}

	private void editarCliente() {

		formularioAddUser();

		jifFormularioUsuario.btnAdd.setText("Actualizar");
		jifFormularioUsuario.btnAdd.setActionCommand("Actualizar cliente");
		idCliente = jifListado.tableUsuarios.getValueAt(jifListado.tableUsuarios.getSelectedRow(), 0).toString();

		Persona p = model.obtenerCliente(idCliente);

		// Rellenamos los datos con el cliente seleccionado
		jifFormularioUsuario.txtFieldNombre.setText(p.getNombre());
		jifFormularioUsuario.txtFieldApellidos.setText(p.getApellidos());
		jifFormularioUsuario.txtFieldDNI.setText(p.getDNI());
		jifFormularioUsuario.datePicker.setDate(p.getFechaNacimiento());
		if (p.getFoto() != null)
			cargarFotoLabel(p.getFoto());

	}

	private void addUser() {
		String nombre = jifFormularioUsuario.txtFieldNombre.getText();
		String apellidos = jifFormularioUsuario.txtFieldApellidos.getText();
		String DNI = jifFormularioUsuario.txtFieldDNI.getText();
		LocalDate fecha_nacimiento = jifFormularioUsuario.datePicker.getDate();
		Image foto = jifFormularioUsuario.foto;

		model.insertarCliente(new Persona(nombre, apellidos, DNI, fecha_nacimiento, foto), fotoCliente);
		fotoCliente = null;
		jifFormularioUsuario.dispose();
		if (jifListado != null)
			actualizarTablaUsuarios();
	}

	private void actualizarCliente() {

		String nombre = jifFormularioUsuario.txtFieldNombre.getText();
		String apellidos = jifFormularioUsuario.txtFieldApellidos.getText();
		String DNI = jifFormularioUsuario.txtFieldDNI.getText();
		LocalDate fecha_nacimiento = jifFormularioUsuario.datePicker.getDate();
		Image foto = jifFormularioUsuario.foto;

		model.actualizarCliente(idCliente, new Persona(nombre, apellidos, DNI, fecha_nacimiento, foto), fotoCliente);
		fotoCliente = null;
		jifFormularioUsuario.dispose();
		if (jifListado != null)
			actualizarTablaUsuarios();
	}

	private void iniciarSesion() {

		// Habilitamos los botones necesarios
		view.btnAddUser.setEnabled(true);
		view.btnListUser.setEnabled(true);
		view.btnSalir.setEnabled(true);
		view.btnReportClients.setEnabled(true);
		view.btnReportProducts.setEnabled(true);
		view.btnCategories.setEnabled(true);
		view.btnProducts.setEnabled(true);
		view.btnInvoice.setEnabled(true);

		// Deshabilitamos botones necesarios
		view.btnFormLogin.setEnabled(false);
	}

	private void cerrarSesion() {

		// Deshabilitamos botones necesarios
		view.btnAddUser.setEnabled(false);
		view.btnListUser.setEnabled(false);
		view.btnSalir.setEnabled(false);
		view.btnReportClients.setEnabled(false);
		view.btnReportProducts.setEnabled(false);
		view.btnCategories.setEnabled(false);
		view.btnProducts.setEnabled(false);
		view.btnInvoice.setEnabled(false);

		// Habilitamos los botones necesarios
		view.btnFormLogin.setEnabled(true);

		// Cerramos frames internos
		JInternalFrame[] frames = view.desktopPane.getAllFrames();
		for (JInternalFrame frame : frames)
			frame.dispose();

		// Campos temporales
		fotoCliente = null;
		idCliente = null;

	}

	private void validar() {
		String usuario = jifLogin.txtFiledUsuario.getText();
		String password = String.valueOf(jifLogin.passwordField.getPassword());
		if (model.autentifica(usuario, password)) {
			jifLogin.dispose();
			iniciarSesion();
		} else {
			JOptionPane.showMessageDialog(view, "El usuario y/o password no son correctos", "Error",
					JOptionPane.ERROR_MESSAGE, null);
		}

	}

	private void abrirFormularioLogin() {
		if (!estaAbierto(jifLogin)) {
			jifLogin = new JIFrameLogin();
			view.desktopPane.add(jifLogin);

			// Establecemos las acciones de los botones
			jifLogin.btnLogin.setActionCommand("Validar");
			jifLogin.btnLogin.addActionListener(this);

			// Centramos el iframe
			Dimension deskSize = view.desktopPane.getSize();
			Dimension ifSize = jifLogin.getSize();
			jifLogin.setLocation((deskSize.width - ifSize.width) / 2, (deskSize.height - ifSize.height) / 2);

			jifLogin.setVisible(true);
		}
	}

	// Formulario añadir usuario
	private void formularioAddUser() {

		if (!estaAbierto(jifFormularioUsuario)) {

			jifFormularioUsuario = new JIFrameFormularioUsuario();
			this.view.desktopPane.add(jifFormularioUsuario);
			jifFormularioUsuario.setVisible(true);

			try {
				jifFormularioUsuario.setSelected(true);
			} catch (PropertyVetoException e) {

				e.printStackTrace();
			}

			// Establecemos las acciones de los botones
			jifFormularioUsuario.btnAdd.setActionCommand("Add User");
			jifFormularioUsuario.btnCancel.setActionCommand("Add User Cancel");
			jifFormularioUsuario.lblFoto.addMouseListener(this);
			jifFormularioUsuario.btnAdd.addActionListener(this);
			jifFormularioUsuario.btnCancel.addActionListener(this);

		}
	}

	private void formularioListar() {

		if (!estaAbierto(jifListado)) {

			jifListado = new JIFrameListado();
			this.view.desktopPane.add(jifListado);
			jifListado.setVisible(true);

			// Establecemos las acciones de los botones
			jifListado.btnEliminar.setActionCommand("Eliminar Usuario");
			jifListado.btnModificar.setActionCommand("Editar cliente");
			jifListado.btnEliminar.addActionListener(this);
			jifListado.btnModificar.addActionListener(this);

			// Mostramos tabla actualizada;
			actualizarTablaUsuarios();

		}
	}

	private boolean eliminarUsuario() {

		boolean eliminado = false;
		int id;

		int[] filas = jifListado.tableUsuarios.getSelectedRows();

		if (filas == null)
			JOptionPane.showMessageDialog(view, "Debe seleccionar primero un usuario", "Info",
					JOptionPane.INFORMATION_MESSAGE, null);
		else {
			for (int fila : filas) {
				id = (int) jifListado.tableUsuarios.getValueAt(fila, 0);
				eliminado = model.eliminarUsuario(id);
			}
		}

		return eliminado;
	}

	private void formularioConfiguracion() {
		ConfiguracionSegura conf = new ConfiguracionSegura();
		if (!estaAbierto(jifConfiguracion)) {
			jifConfiguracion = new JIFrameConfiguracion();
			this.view.desktopPane.add(jifConfiguracion);
			jifConfiguracion.setVisible(true);

			// Establecemos las acciones de los botones
			jifConfiguracion.btnGuardar.setActionCommand("Guardar configuracion");
			jifConfiguracion.btnGuardar.addActionListener(this);

			// Rellenamos los campos con la configuracion
			jifConfiguracion.txtFieldHost.setText(conf.getHost());
			jifConfiguracion.txtFieldPort.setText(conf.getPort());
			jifConfiguracion.txtFieldDatabase.setText(conf.getDatabase());
			jifConfiguracion.txtFieldUser.setText(conf.getUser());
			jifConfiguracion.passwordField.setText(conf.getPassword());
			try {
				jifConfiguracion.setSelected(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
	}

	private void guardarConfiguracion() {
		ConfiguracionSegura conf = new ConfiguracionSegura();
		conf.setHost(jifConfiguracion.txtFieldHost.getText());
		conf.setPort(jifConfiguracion.txtFieldPort.getText());
		conf.setDatabase(jifConfiguracion.txtFieldDatabase.getText());
		conf.setUser(jifConfiguracion.txtFieldUser.getText());
		conf.setPassword(String.valueOf(jifConfiguracion.passwordField.getPassword()));
		conf.guardar();
		jifConfiguracion.dispose();

	}

	private void actualizarTablaUsuarios() {
		Map<Integer, Persona> respuesta = model.obtenerPersonas();

		DefaultTableModel jtb = new DefaultTableModel();
		jtb.addColumn("id");
		jtb.addColumn("DNI");
		jtb.addColumn("Nombre");
		jtb.addColumn("Apellidos");
		jtb.addColumn("F.Nacmiento");

		for (Integer key : respuesta.keySet()) {

			jtb.addRow(new Object[] { key, respuesta.get(key).getDNI(), respuesta.get(key).getNombre(),
					respuesta.get(key).getApellidos(), respuesta.get(key).getFechaNacimiento().toString() });
		}
		jifListado.tableUsuarios.setModel(jtb);
		jifListado.tableUsuarios.getColumnModel().getColumn(0).setMinWidth(20);
		jifListado.tableUsuarios.getColumnModel().getColumn(0).setMaxWidth(50);
	}

	private void cargarFotoFichero(File f) {

		InputStream inte = null;
		InputStream in = null;

		try {
			byte[] imgFoto = new byte[(int) f.length()];
			inte = new FileInputStream(f);
			inte.read(imgFoto);

			BufferedImage image = null;
			in = new ByteArrayInputStream(imgFoto);

			image = ImageIO.read(in);
			ImageIcon icono = new ImageIcon(image);
			Image imageToResize = icono.getImage();

			jifFormularioUsuario.foto = imageToResize;
			fotoCliente = f;

			cargarFotoLabel(imageToResize);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (inte != null)
				try {
					inte.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
		}
	}

	private void cargarFotoLabel(Image imgFoto) {

		Image nuevaResized = imgFoto.getScaledInstance(jifFormularioUsuario.lblFoto.getWidth(),
				jifFormularioUsuario.lblFoto.getHeight(), java.awt.Image.SCALE_SMOOTH);
		ImageIcon icono = new ImageIcon(nuevaResized);

		jifFormularioUsuario.lblFoto.setIcon(icono);
	}

	private void abrirReportClients() {

		if (!estaAbierto(jifReportClients)) {
			jifReportClients = new JIFrameReportClients();

			controladorReportClients = new ControladorReportClients(jifReportClients, model);
			view.desktopPane.add(jifReportClients);
			controladorReportClients.go();

		}
	}

	private void abrirReportProducts() {

		if (!estaAbierto(jifReportClients)) {
			jifReportProducts = new JIFrameReportProducts();

			controladorReportProducts = new ControladorReportProducts(jifReportProducts, model);
			view.desktopPane.add(jifReportProducts);
			controladorReportProducts.go();

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getComponent() == jifFormularioUsuario.lblFoto) {
			JFileChooser jfc = new JFileChooser();
			int option = jfc.showOpenDialog(view);
			if (option == JFileChooser.APPROVE_OPTION) {
				cargarFotoFichero(jfc.getSelectedFile());
			}

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Captura en String el comando accionado por el usuario
		String comando = e.getActionCommand();

		/* Acciones del formulario padre */
		if (comando.equals("Abrir Formulario Usuario")) {
			formularioAddUser();
		} else if (comando.equals("Add User")) {
			addUser();
		} else if (comando.equals("Add User Cancel")) {
			jifFormularioUsuario.dispose();
		} else if (comando.equals("Listar Usuarios")) {
			formularioListar();
		} else if (comando.equals("Eliminar Usuario")) {
			eliminarUsuario();
			actualizarTablaUsuarios();
		} else if (comando.equals("Configuracion")) {
			formularioConfiguracion();
		} else if (comando.equals("Guardar configuracion")) {
			guardarConfiguracion();
		} else if (comando.equals("Abrir Formulario Login")) {
			abrirFormularioLogin();
		} else if (comando.equals("Validar")) {
			validar();
		} else if (comando.equals("Cerrar sesion")) {
			cerrarSesion();
		} else if (comando.equals("Editar cliente")) {
			editarCliente();
		} else if (comando.equals("Actualizar cliente")) {
			actualizarCliente();
		} else if (comando.equals("Report clients")) {
			abrirReportClients();
		} else if (comando.equals("Report products")) {
			abrirReportProducts();
		} else if (comando.equals("Categorias")) {
			abrirCategorias();
		} else if (comando.equals("Productos")) {
			abrirProductos();
		} else if (comando.equals("Invoice")) {
			abrirFacturas();
		}

	}
}
