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
	
	private BillTags billtag;
	private Client client;
	
	public InputBill() {
	}

	public InputBill(Integer idInputBill, String ib_ano_mes, Integer id_billTag, Integer id_client, String cv_agent,
			String cv_instance, String cv_backupset, String cv_subclient, String cv_storagepolicy, String cv_copyname,
			double cv_mediasize, double ib_taxcalculated, BillTags billtag, Client client) {
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
		this.billtag = billtag;
		this.client = client;
	}

	public Integer getIdInputBill() {
		return idInputBill;
	}

	public void setIdInputBill(Integer idInputBill) {
		this.idInputBill = idInputBill;
	}

	public String getIb_ano_mes() {
		return ib_ano_mes;
	}

	public void setIb_ano_mes(String ib_ano_mes) {
		this.ib_ano_mes = ib_ano_mes;
	}

	public Integer getId_billTag() {
		return id_billTag;
	}

	public void setId_billTag(Integer id_billTag) {
		this.id_billTag = id_billTag;
	}

	public Integer getId_client() {
		return id_client;
	}

	public void setId_client(Integer id_client) {
		this.id_client = id_client;
	}

	public String getCv_agent() {
		return cv_agent;
	}

	public void setCv_agent(String cv_agent) {
		this.cv_agent = cv_agent;
	}

	public String getCv_instance() {
		return cv_instance;
	}

	public void setCv_instance(String cv_instance) {
		this.cv_instance = cv_instance;
	}

	public String getCv_backupset() {
		return cv_backupset;
	}

	public void setCv_backupset(String cv_backupset) {
		this.cv_backupset = cv_backupset;
	}

	public String getCv_subclient() {
		return cv_subclient;
	}

	public void setCv_subclient(String cv_subclient) {
		this.cv_subclient = cv_subclient;
	}

	public String getCv_storagepolicy() {
		return cv_storagepolicy;
	}

	public void setCv_storagepolicy(String cv_storagepolicy) {
		this.cv_storagepolicy = cv_storagepolicy;
	}

	public String getCv_copyname() {
		return cv_copyname;
	}

	public void setCv_copyname(String cv_copyname) {
		this.cv_copyname = cv_copyname;
	}

	public double getCv_mediasize() {
		return cv_mediasize;
	}

	public void setCv_mediasize(double cv_mediasize) {
		this.cv_mediasize = cv_mediasize;
	}

	public double getIb_taxcalculated() {
		return ib_taxcalculated;
	}

	public void setIb_taxcalculated(double ib_taxcalculated) {
		this.ib_taxcalculated = ib_taxcalculated;
	}

	public BillTags getBilltag() {
		return billtag;
	}

	public void setBilltag(BillTags billtag) {
		this.billtag = billtag;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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
				+ ", ib_taxcalculated=" + ib_taxcalculated + ", billtag=" + billtag + ", client=" + client + "]";
	}

	

}
