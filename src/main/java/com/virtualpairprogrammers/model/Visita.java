package com.virtualpairprogrammers.model;

import java.util.Date;

public class Visita {
	
    private int id_visita;
    private int id_paziente;
    private int id_dottore;
    private Date data_visita;
    private int stato;
    private String descrizione;
    private String diagnosi;

    public Visita() {}
    
    public Visita(int id_visita, int id_paziente, int id_dottore, Date data_visita, int stato, String descrizione, String diagnosi) {
        this.id_visita = id_visita;
        this.id_paziente = id_paziente;
        this.id_dottore = id_dottore;
        this.data_visita = data_visita;
        this.stato = stato;
        this.descrizione = descrizione;
        this.diagnosi = diagnosi;
    }

	public int getId_visita() {
		return id_visita;
	}

	public void setId_visita(int id_visita) {
		this.id_visita = id_visita;
	}

	public int getId_paziente() {
		return id_paziente;
	}

	public void setId_paziente(int id_paziente) {
		this.id_paziente = id_paziente;
	}

	public int getId_dottore() {
		return id_dottore;
	}

	public void setId_dottore(int id_dottore) {
		this.id_dottore = id_dottore;
	}

	public Date getData_visita() {
		return data_visita;
	}

	public void setData_visita(Date data_visita) {
		this.data_visita = data_visita;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDiagnosi() {
		return diagnosi;
	}

	public void setDiagnosi(String diagnosi) {
		this.diagnosi = diagnosi;
	}

    
}
