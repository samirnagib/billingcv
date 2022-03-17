package model.entities;

import java.io.Serializable;

public class InputBill implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer idInputBill;
	private String ib_ano_mes;
	private Integer id_billTag;
	private Integer id_client;
	private String cv_agent;
	private String cv_instance;
	private String cv_backupset;
	private String cv_subclient;
	private String cv_storagepolicy;
	private String cv_copyname;
	private double cv_mediasize;
	private double ib_taxcalculated;
	
	public InputBill() {
	}

	public InputBill(Integer idInputBill, String ib_ano_mes, Integer id_billTag, Integer id_client, String cv_agent,
			String cv_instance, String cv_backupset, String cv_subclient, String cv_storagepolicy, String cv_copyname,
			double cv_mediasize, double ib_taxcalculated) {
		this.idInputBill = idInputBill;
		this.ib_ano_mes = ib_ano_mes;
		this.id_billTag = id_billTag;
		this.id_client = id_client;
		this.cv_agent = cv_agent;
		this.cv_instance = cv_instance;
		this.cv_backupset = cv_backupset;
		this.cv_subclient = cv_subclient;
		this.cv_storagepolicy = cv_storagepolicy;
		this.cv_copyname = cv_copyname;
		this.cv_mediasize = cv_mediasize;
		this.ib_taxcalculated = ib_taxcalculated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idInputBill == null) ? 0 : idInputBill.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InputBill other = (InputBill) obj;
		if (idInputBill == null) {
			if (other.idInputBill != null)
				return false;
		} else if (!idInputBill.equals(other.idInputBill))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InputBill [idInputBill=" + idInputBill + ", ib_ano_mes=" + ib_ano_mes + ", id_billTag=" + id_billTag
				+ ", id_client=" + id_client + ", cv_agent=" + cv_agent + ", cv_instance=" + cv_instance
				+ ", cv_backupset=" + cv_backupset + ", cv_subclient=" + cv_subclient + ", cv_storagepolicy="
				+ cv_storagepolicy + ", cv_copyname=" + cv_copyname + ", cv_mediasize=" + cv_mediasize
				+ ", ib_taxcalculated=" + ib_taxcalculated + "]";
	}
	
	

}
