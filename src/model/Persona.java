package model;

import java.awt.Image;
import java.time.LocalDate;

public class Persona {
	

	private int id;
	private String nombre;
	private String apellidos;
	private String DNI;
	private LocalDate fechaNacimiento;
	private Image foto;
	
	public Persona(int id,String nombre, String apellidos,String DNI, LocalDate fechaNacimiento,Image foto) {
		super();

		this.id=id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.DNI = DNI;
		this.fechaNacimiento = fechaNacimiento;
		this.foto = foto;
	}
	public Persona(String nombre, String apellidos,String DNI, LocalDate fechaNacimiento,Image foto) {
		super();

		this.nombre = nombre;
		this.apellidos = apellidos;
		this.DNI = DNI;
		this.fechaNacimiento = fechaNacimiento;
		this.foto = foto;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public Image getFoto() {
		return foto;
	}

	public void setFoto(Image foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNacimiento=" + fechaNacimiento
				+ ", foto=" +  "]\n";
	}
	
	
	
}
