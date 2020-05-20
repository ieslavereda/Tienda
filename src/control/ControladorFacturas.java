package control;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Categoria;
import model.Factura;
import model.LineaFactura;
import model.Modelo;
import model.Persona;
import model.Producto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import view.JDialogSeleccionCliente;
import view.JDialogSeleccionProducto;
import view.JIFrameFacturas;

public class ControladorFacturas implements ActionListener {

	private Modelo modelo;
	private JIFrameFacturas view;

	// Dialogs
	JDialogSeleccionProducto dialog = null;
	JDialogSeleccionCliente jdSeleccionCliente = null;

	// Variables globales
	private Map<Integer, Persona> clientes;
	private Map<Integer, Factura> facturas;

	// Datos acutales
	Factura factura = null;
	Persona cliente = null;
	Producto producto = null;
	float cantidad;

	ArrayList<Integer> numerosFacturas;
	ListIterator<Integer> iterator;
	boolean next = true;

	public ControladorFacturas(Modelo modelo, JIFrameFacturas jifFacturas) {

		this.modelo = modelo;
		this.view = jifFacturas;
		inicializar();

	}

	private void inicializar() {



	}

	public void go() {
		view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		
	}

	private void abrirJasperReport() {

		String reportUrl = "/reports/reportFactura.jasper";
		InputStream reportFile = null;
		reportFile = getClass().getResourceAsStream(reportUrl);

		Map<String, Object> parametres = new HashMap<String, Object>();

		// Establecemos los parametros de la consulta
		parametres.put("paraWhere", " Factura.id=" + view.txtFieldNumeroFactura.getText());

		try {
			JasperPrint print = JasperFillManager.fillReport(reportFile, parametres, modelo.getConnection());
			JasperViewer jViewer = new JasperViewer(print, false);
			jViewer.setVisible(true);

		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	private void exportToPdf() {

		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF File", "pdf");
		jfc.setFileFilter(filter);
		jfc.setDialogTitle("Exportar facutura a PDF");
		jfc.setCurrentDirectory(null);
		jfc.setSelectedFile(new File("Factura_" + view.txtFieldNumeroFactura.getText()+"_"+view.txtFieldDNI.getText()+".pdf"));
		
		
		if (jfc.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {

			String reportUrl = "/reports/reportFactura.jasper";
			InputStream reportFile = null;
			reportFile = getClass().getResourceAsStream(reportUrl);

			Map<String, Object> parametres = new HashMap<String, Object>();

			// Establecemos los parametros de la consulta
			parametres.put("paraWhere", " Factura.id=" + view.txtFieldNumeroFactura.getText());

			try {
				
				JasperPrint print = JasperFillManager.fillReport(reportFile, parametres, modelo.getConnection());
				JasperExportManager.exportReportToPdfFile(print, jfc.getSelectedFile().getAbsolutePath());
				JOptionPane.showMessageDialog(view, "Documento generado correctamente", "Info", JOptionPane.INFORMATION_MESSAGE, null);

			} catch (JRException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, "Documento no se ha generado\n"+e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, null);
			}
		}
	}

	









	
	

}
