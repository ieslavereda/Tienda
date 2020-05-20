package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Categoria;
import model.Modelo;
import view.JIFrameCategorias;

/**
 * Creado el 10 abr. 2019
 * @author <a href="mailto:joaalsai@ieslavereda.es">Joaquin Vicente Alonso Saiz</a>
 *
 */
public class ControladorCategorias implements ActionListener,MouseListener {

	private JIFrameCategorias view;
	private Modelo model;
	
	// Variables temporales
	Categoria categoria;
	
	public ControladorCategorias(JIFrameCategorias view, Modelo model) {
		this.model=model;
		this.view=view;
		inicializar();
	}
	
	private void inicializar() {
		
		// Se añade las acciones a los controles del formulario padre
		view.btnEliminar.setActionCommand("Eliminar");
		view.btnActualizar.setActionCommand("Actualizar");
		view.btnAdd.setActionCommand("Add category");
		
		
		// Se pone a escuchar las acciones del usuario
		view.btnEliminar.addActionListener(this);
		view.btnActualizar.addActionListener(this);
		view.btnAdd.addActionListener(this);
		view.tableCategorias.addMouseListener(this);
		
		// Rellenamos la tabla
		actualizarTabla();	
	}
	
	public void go() {
		view.setVisible(true);
	}
	
	private void actualizarTabla() {
		
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("id");
		dtm.addColumn("Descripcion");
		Map<Integer,Categoria> categorias = model.obtenerCategorias();
		for(Integer key : categorias.keySet()) {
			dtm.addRow(new Object[] {categorias.get(key).getId(),categorias.get(key).getDescripcion()});
		}
		
		view.tableCategorias.setModel(dtm);
		//view.tableCategorias.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		view.tableCategorias.getColumnModel().getColumn(0).setMaxWidth(50);
	}
	
	private void actualizarPanelDescripcion(String id) {
		
		categoria = model.obtenerCategoria(id);
		
		view.txtFieldDescripcion.setText(categoria.getDescripcion());
		
	}
	
	private void addCategory() {
		 String descripcion = JOptionPane.showInputDialog(view, "Descripcion de la categoria"); 
		 
		 if(descripcion!=null) {
			 int opcion = JOptionPane.showConfirmDialog(view, "<html><p>Esta seguro de añadir la categoria </br><b>"+descripcion+"</b></html>" , "Confirmacion", JOptionPane.YES_NO_OPTION	,JOptionPane.QUESTION_MESSAGE);
			 if(opcion==JOptionPane.YES_OPTION) {
				if(model.insertarCategoria(descripcion)) {
					JOptionPane.showMessageDialog(view, "Categoria creada.", "Info", JOptionPane.INFORMATION_MESSAGE);
					categoria=null;
					view.txtFieldDescripcion.setText(null);
					actualizarTabla();
				}
			 }
		 }
	}
	private void deleteCategory() {
		
		int[] filas;
		int[] ids;
		boolean eliminado = true;
		
		if(view.tableCategorias.getSelectedRowCount()!=0) {
			
			filas = view.tableCategorias.getSelectedRows();
			ids = new int[filas.length];
			
			for(int i=0;i<filas.length;i++) {
				ids[i] = Integer.parseInt(view.tableCategorias.getValueAt(filas[i],0).toString());
			}
			
			for(int id : ids)
				eliminado = eliminado && model.eliminarCategoria(id);
			
			actualizarTabla();
			
			if(eliminado)
				JOptionPane.showMessageDialog(view, "Se han eliminado correctamente todas las categorias seleccionadas.","Info",JOptionPane.INFORMATION_MESSAGE );
			else
				JOptionPane.showMessageDialog(view, "No se ha podido eliminar todas las categorias seleccionadas. Compruebe que la categoria no tenga asociado ningun producto.","Error",JOptionPane.ERROR_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(view, "Debe seleccionar alguna categoria.","Info",JOptionPane.INFORMATION_MESSAGE );
		}
		
		
	}
	
	private void updateCategory() {
		int opcion = JOptionPane.showConfirmDialog(view, "<html><p>Esta seguro de modificar la categoria </br><b>"+view.txtFieldDescripcion.getText()+"</b></html>" , "Confirmacion", JOptionPane.YES_NO_OPTION	,JOptionPane.QUESTION_MESSAGE);
		 if(opcion==JOptionPane.YES_OPTION) {
			if(model.actualizarCategoria(view.txtFieldDescripcion.getText(), categoria.getId())) {
				JOptionPane.showMessageDialog(view, "Categoria actualizada.", "Info", JOptionPane.INFORMATION_MESSAGE);
				actualizarTabla();
			}
		 }
	}
		
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		String command = arg0.getActionCommand();
		
		if(command.equals("Eliminar")) {
			deleteCategory();
		}else if(command.equals("Add category")) {
			addCategory();
		}else if(command.equals("Actualizar")) {
			updateCategory();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		actualizarPanelDescripcion(view.tableCategorias.getValueAt(view.tableCategorias.getSelectedRow(), 0).toString());
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
