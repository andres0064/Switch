package com.example.demo;

public class PACProcess {
	
	private int id;
	private String nombre;
	private double totalProd;
	private double totalProcess;
	private double sumaProcess;
	
	public PACProcess(int id, String nombre, double totalProd, double totalProcess, double sumaProcess) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.totalProd = totalProd;
		this.totalProcess = totalProcess;
		this.sumaProcess = sumaProcess;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getTotalProd() {
		return totalProd;
	}
	public void setTotalProd(double totalProd) {
		this.totalProd = totalProd;
	}
	public double getTotalProcess() {
		return totalProcess;
	}
	public void setTotalProcess(double totalProcess) {
		this.totalProcess = totalProcess;
	}
	public double getSumaProcess() {
		return sumaProcess;
	}
	public void setSumaProcess(double sumaProcess) {
		this.sumaProcess = sumaProcess;
	}

}
