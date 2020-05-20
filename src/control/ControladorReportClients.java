/**
 * 
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Modelo;
import model.Persona;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

import view.JIFrameReportClients;

/**
 * Creado el 3 abr. 2019
 * 
 * @author <a href="mailto:joaalsai@ieslavereda.es">Joaquin Vicente Alonso
 *         Saiz</a>
 *
 */
public class ControladorReportClients implements ActionListener {

	private JIFrameReportClients view;
	private Modelo model;

	JasperReport report;
	JasperPrint reportFilled;
	// JasperViewer jasperViewer;
	JRViewer jrViewer;

	private String reportUrl;

	public ControladorReportClients(JIFrameReportClients view, Modelo model) {

		super();
		this.view = view;
		this.model = model;
		iniciar();
	}

	private void iniciar() {

		// Se añade las acciones a los controles del formulario padre
		view.btnTodos.setActionCommand("Todos");
		view.btnCustom.setActionCommand("Custom search");

		// Se pone a escuchar las acciones del usuario
		view.btnTodos.addActionListener(this);
		view.btnCustom.addActionListener(this);

		// Rellenamos los combo
		rellenarComboOrderBy();
		rellenarComboId();

	}

	public void go() {
		try {
			view.setMaximum(true);
			view.setVisible(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Captura en String el comando accionado por el usuario
		String comando = e.getActionCommand();

		/* Acciones del formulario padre */
		if (comando.equals("Todos")) {
			viewAllClients();
		} else if (comando.equals("Custom search")) {
			customSearch();
		}

	}

	private void rellenarComboOrderBy() {

		List<String> campos = model.obtenerAtributosTabla("Cliente");

		for (String campo : campos)
			view.cbOrderBy.addItem(campo);

	}

	private void rellenarComboId() {

		Map<Integer, Persona> campos = model.obtenerPersonas();

		for (Integer id : campos.keySet())
			view.cbId.addItem(id);

	}

	private void viewAllClients() {

		reportUrl = "/reports/report.jasper"; // path of your report source.
		InputStream reportFile = null;
		reportFile = getClass().getResourceAsStream(reportUrl);

		Map<String, Object> parametres = new HashMap<String, Object>();
		parametres.put("wherePara", "ORDER BY fecha_nacimiento ASC");

		try {

			JasperPrint print = JasperFillManager.fillReport(reportFile, parametres, model.getConnection());
			jrViewer = new JRViewer(print);
			jrViewer.setVisible(true);

			view.getContentPane().add(jrViewer);

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void customSearch() {
		reportUrl = "/reports/report.jasper";
		InputStream reportFile = null;
		reportFile = getClass().getResourceAsStream(reportUrl);

		Map<String, Object> parametres = new HashMap<String, Object>();

		String id = view.cbId.getSelectedItem().toString();
		LocalDate from = view.datePickerFrom.getDate();
		LocalDate to = view.datePickerTo.getDate();
		String orderBy = view.cbOrderBy.getSelectedItem().toString();
		String order = view.cbOrder.getSelectedItem().toString();

		String query = "";

		// Comprobamos id
		if (id.equals("All"))
			query += "WHERE id IS NOT NULL";
		else
			query += "WHERE id=" + id;

		// Comprobamos Fecha nacimiento
		if (from != null && to != null)
			query += " AND fecha_nacimiento BETWEEN \"" + java.sql.Date.valueOf(from).toString() + "\"" + " AND \""
					+ java.sql.Date.valueOf(to).toString() + "\"";
		else if (from != null)
			query += " AND fecha_nacimiento > \"" + java.sql.Date.valueOf(from).toString() + "\"";
		else if (to != null)
			query += " AND fecha_nacimiento < \"" + java.sql.Date.valueOf(to).toString() + "\"";

		// Comprobamos el orden
		query += " ORDER BY " + orderBy + " " + ((order.equals("ascendente")) ? "ASC" : "DESC");

		// Establecemos los parametros de la consulta
		parametres.put("wherePara", query);
		// System.out.println(query);

		try {
			JasperPrint print = JasperFillManager.fillReport(reportFile, parametres, model.getConnection());
			jrViewer = new JRViewer(print);
			jrViewer.setVisible(true);
			
			

			view.getContentPane().add(jrViewer);

		} catch (JRException e) {
			e.printStackTrace();
		}
	}

}
