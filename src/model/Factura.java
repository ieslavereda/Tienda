package model;

import java.time.LocalDate;

public class Factura {

	private int id;
	private LocalDate fechaFactura;
	private float importe;
	private int clienteId;
	
	public Factura(int id, LocalDate fechaFactura, float importe, int clienteId) {
		super();
		this.id = id;
		this.fechaFactura = fechaFactura;
		this.importe = importe;
		this.clienteId = clienteId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the fechaFactura
	 */
	public LocalDate getFechaFactura() {
		return fechaFactura;
	}

	/**
	 * @return the importe
	 */
	public float getImporte() {
		return importe;
	}

	/**
	 * @return the clienteId
	 */
	public int getClienteId() {
		return clienteId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param fechaFactura the fechaFactura to set
	 */
	public void setFechaFactura(LocalDate fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(float importe) {
		this.importe = importe;
	}

	/**
	 * @param clienteId the clienteId to set
	 */
	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}
	
	
	
	
}
