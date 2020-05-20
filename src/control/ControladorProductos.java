package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Categoria;
import model.Modelo;
import model.Producto;
import view.JIFrameProductos;

public class ControladorProductos implements ActionListener, MouseListener {

	private Modelo modelo;
	private JIFrameProductos view;

	// Producto temporal
	Producto producto;
	Map<Integer, Categoria> categorias;
	

	public ControladorProductos(Modelo modelo, JIFrameProductos jifProductos) {

		this.view = jifProductos;
		this.modelo = modelo;
		inicializar();

	}

	private void inicializar() {

		// Establecemos las acciones a los botones
		view.btnAdd.setActionCommand("Add");
		view.btnDelete.setActionCommand("Delete");
		view.btnUpdate.setActionCommand("Update");
		view.comboBoxSeleccionCategoria.setActionCommand("Changed selected category");
		view.comboBoxOrder.setActionCommand("Changed selected order");
		view.comboBoxSorted.setActionCommand("Changed selected sorted");

		// Establecemos el listener a cada componente
		view.btnAdd.addActionListener(this);
		view.btnDelete.addActionListener(this);
		view.btnUpdate.addActionListener(this);
		view.tableProductos.addMouseListener(this);
		view.comboBoxSeleccionCategoria.addActionListener(this);
		view.comboBoxOrder.addActionListener(this);
		view.comboBoxSorted.addActionListener(this);

		// Rellenamos la tabla
		actualizarTabla(view.comboBoxSeleccionCategoria.getSelectedItem(),
				view.comboBoxSorted.getSelectedItem().toString(), view.comboBoxOrder.getSelectedItem().toString());

		// Obtenemos categorias y rellenamos comboBoxSeleccion
		rellenarComboBoxSeleccion();

	}

	public void go() {
		this.view.setVisible(true);
	}

	private void actualizarTabla(Object oId, String field, String order) {

		String id = null;
		Producto producto;

		if (oId instanceof Categoria)
			id = String.valueOf(((Categoria) oId).getId());

		Map<Integer, Producto> productos = modelo.obtenerProductos(id, field, order);

		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("id");
		dtm.addColumn("descripcion");
		dtm.addColumn("Precio");
		dtm.addColumn("Existencias");

		for (Integer key : productos.keySet()) {
			producto = productos.get(key);

			dtm.addRow(new Object[] { producto.getId(), producto.getDescArticulo(), producto.getPvp(),
					producto.getExistencias() });
		}

		// Indicamos la alineacion de las columnas
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		DefaultTableCellRenderer tcr2 = new DefaultTableCellRenderer();

		view.tableProductos.setModel(dtm);
		view.tableProductos.getColumnModel().getColumn(0).setMaxWidth(50);
		view.tableProductos.getColumnModel().getColumn(0).setMinWidth(50);

		// view.tableProductos.getColumnModel().getColumn(1).setMaxWidth(500);

		view.tableProductos.getColumnModel().getColumn(2).setMaxWidth(100);
		view.tableProductos.getColumnModel().getColumn(2).setMinWidth(100);
		tcr2.setHorizontalAlignment(SwingConstants.CENTER);
		view.tableProductos.getColumnModel().getColumn(2).setCellRenderer(tcr);

		view.tableProductos.getColumnModel().getColumn(3).setMaxWidth(100);
		view.tableProductos.getColumnModel().getColumn(3).setMinWidth(100);
		tcr.setHorizontalAlignment(SwingConstants.RIGHT);
		view.tableProductos.getColumnModel().getColumn(3).setCellRenderer(tcr);

	}

	private void rellenarComboBoxSeleccion() {
		categorias = modelo.obtenerCategorias();
		for (Integer key : categorias.keySet()) {
			view.comboBoxSeleccionCategoria.addItem(categorias.get(key));
			view.comboBoxCategoria.addItem(categorias.get(key));
		}
	}

	private void borrarDetalles() {
		view.txtFieldDescripcion.setText(null);
		view.spinnerExistencias.setValue(0);
		view.txtFieldPVP.setText(null);
	}

	private boolean comprobarCampos() {
		boolean correcto = true;
		Float pvp;
		try {
			pvp = Float.parseFloat(view.txtFieldPVP.getText());
			if (pvp < 0) {
				correcto = false;
				JOptionPane.showMessageDialog(view, "El precio no puede ser negativo", "Error",
						JOptionPane.ERROR_MESSAGE);

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "El precio introducido no es correcto.", "Error",
					JOptionPane.ERROR_MESSAGE);
			correcto = false;
		}

		return correcto;
	}

	private void actualizarTabla() {
		actualizarTabla(view.comboBoxSeleccionCategoria.getSelectedItem(),
				view.comboBoxSorted.getSelectedItem().toString(), view.comboBoxOrder.getSelectedItem().toString());
	}

	private void deleteProduct() {
		int[] filas;
		int[] ids;
		boolean eliminados = true;

		if (view.tableProductos.getSelectedRowCount() == 0) {
			JOptionPane.showMessageDialog(view, "Debe seleccionar primero al menos un producto", "Info",
					JOptionPane.INFORMATION_MESSAGE, null);
		} else {
			filas = view.tableProductos.getSelectedRows();
			ids = new int[filas.length];
			for (int i = 0; i < filas.length; i++)
				ids[i] = Integer.parseInt(view.tableProductos.getValueAt(filas[i], 0).toString());

			for (int id : ids)
				eliminados &= modelo.eliminarProducto(id);
			if (eliminados)
				JOptionPane.showMessageDialog(view, "Productos eliminados correctamente", "Info",
						JOptionPane.INFORMATION_MESSAGE, null);
			else
				JOptionPane.showMessageDialog(view,
						"No se han podido elminar correctamente todos los productos seleccionados", "Error",
						JOptionPane.ERROR_MESSAGE, null);
			actualizarTabla(view.comboBoxSeleccionCategoria.getSelectedItem(),
					view.comboBoxSorted.getSelectedItem().toString(), view.comboBoxOrder.getSelectedItem().toString());
		}

	}

	private void updateProduct() {
		if (view.txtFieldDescripcion.getText().isEmpty() || view.txtFieldPVP.getText().isEmpty()
				|| view.comboBoxCategoria.getSelectedItem() == null) {

			JOptionPane.showMessageDialog(view, "Hay campos sin rellenar.", "Error", JOptionPane.ERROR_MESSAGE, null);

		} else {
			int opcion = JOptionPane.showConfirmDialog(view, "Esta usted seguro de modificar el producto",
					"Confirmacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (opcion == JOptionPane.YES_OPTION) {

				if (comprobarCampos()) {

					producto.setDescArticulo(view.txtFieldDescripcion.getText());
					producto.setExistencias(Integer.parseInt(view.spinnerExistencias.getValue().toString()));
					producto.setPvp(Float.parseFloat(view.txtFieldPVP.getText()));
					producto.setCategoriaId(((Categoria) view.comboBoxCategoria.getSelectedItem()).getId());

					if (modelo.actualizarProducto(producto)) {
						JOptionPane.showMessageDialog(view, "Producto introducido correctamente", "Info",
								JOptionPane.INFORMATION_MESSAGE, null);
						actualizarTabla(view.comboBoxSeleccionCategoria.getSelectedItem(),
								view.comboBoxSorted.getSelectedItem().toString(),
								view.comboBoxOrder.getSelectedItem().toString());
						borrarDetalles();
					} else {
						JOptionPane.showMessageDialog(view, "Ha habido un error.El producto no ha podido introducirse",
								"Info", JOptionPane.ERROR_MESSAGE, null);
					}
				}
			}

		}

	}

	private void addProduct() {

		if (view.txtFieldDescripcion.getText().isEmpty() || view.txtFieldPVP.getText().isEmpty()
				|| view.comboBoxCategoria.getSelectedItem() == null) {

			JOptionPane.showMessageDialog(view, "Hay campos sin rellenar.", "Error", JOptionPane.ERROR_MESSAGE, null);

		} else {

			int opcion = JOptionPane.showConfirmDialog(view, "Esta usted seguro de añadir el producto", "Confirmacion",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (opcion == JOptionPane.YES_OPTION) {

				if (comprobarCampos()) {

					producto = new Producto(0, view.txtFieldDescripcion.getText(),
							Integer.parseInt(view.spinnerExistencias.getValue().toString()),
							Float.parseFloat(view.txtFieldPVP.getText()),
							((Categoria) view.comboBoxCategoria.getSelectedItem()).getId());
					if (modelo.insertarProducto(producto)) {
						JOptionPane.showMessageDialog(view, "Producto introducido correctamente", "Info",
								JOptionPane.INFORMATION_MESSAGE, null);
						actualizarTabla(view.comboBoxSeleccionCategoria.getSelectedItem(),
								view.comboBoxSorted.getSelectedItem().toString(),
								view.comboBoxOrder.getSelectedItem().toString());
						borrarDetalles();
					} else {
						JOptionPane.showMessageDialog(view, "Ha habido un error.El producto no ha podido introducirse",
								"Info", JOptionPane.ERROR_MESSAGE, null);
					}
				}
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();

		if (command.equals("Changed selected category")) {
			actualizarTabla();
		} else if (command.equals("Add")) {
			addProduct();
		} else if (command.equals("Delete")) {
			deleteProduct();
		} else if (command.equals("Update")) {
			updateProduct();
		} else if (command.equals("Changed selected order")) {
			actualizarTabla();
		} else if (command.equals("Changed selected sorted")) {
			actualizarTabla();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if (e.getComponent() == view.tableProductos) {
			producto = modelo.obtenerProducto(
					view.tableProductos.getValueAt(view.tableProductos.getSelectedRow(), 0).toString());
			view.comboBoxCategoria.setSelectedItem(categorias.get(producto.getCategoriaId()));
			view.txtFieldPVP.setText(String.valueOf(producto.getPvp()));
			view.txtFieldDescripcion.setText(producto.getDescArticulo());
			view.spinnerExistencias.setValue(producto.getExistencias());
		}
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
