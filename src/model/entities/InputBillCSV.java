package model.entities;

import java.io.Serializable;

public class InputBillCSV implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String Competencia;
	private String BillingTag;
	private String Client;
	private String Agent;
	private String Instance;
	private String Backupset;
	private String Subclient;
	private String StoragePolicy;
	private String Copy;
	private double MediaSize;
	
	
	public InputBillCSV() {
	}


	public InputBillCSV(String competencia, String billingTag, String client, String agent, String instance,
			String backupset, String subclient, String storagePolicy, String copy, double mediaSize) {
		Competencia = competencia;
		BillingTag = billingTag;
		Client = client;
		Agent = agent;
		Instance = instance;
		Backupset = backupset;
		Subclient = subclient;
		StoragePolicy = storagePolicy;
		Copy = copy;
		MediaSize = mediaSize;
	}


	public String getCompetencia() {
		return Competencia;
	}


	public void setCompetencia(String competencia) {
		Competencia = competencia;
	}


	public String getBillingTag() {
		return BillingTag;
	}


	public void setBillingTag(String billingTag) {
		BillingTag = billingTag;
	}


	public String getClient() {
		return Client;
	}


	public void setClient(String client) {
		Client = client;
	}


	public String getAgent() {
		return Agent;
	}


	public void setAgent(String agent) {
		Agent = agent;
	}


	public String getInstance() {
		return Instance;
	}


	public void setInstance(String instance) {
		Instance = instance;
	}


	public String getBackupset() {
		return Backupset;
	}


	public void setBackupset(String backupset) {
		Backupset = backupset;
	}


	public String getSubclient() {
		return Subclient;
	}


	public void setSubclient(String subclient) {
		Subclient = subclient;
	}


	public String getStoragePolicy() {
		return StoragePolicy;
	}


	public void setStoragePolicy(String storagePolicy) {
		StoragePolicy = storagePolicy;
	}


	public String getCopy() {
		return Copy;
	}


	public void setCopy(String copy) {
		Copy = copy;
	}


	public double getMediaSize() {
		return MediaSize;
	}


	public void setMediaSize(double mediaSize) {
		MediaSize = mediaSize;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Competencia == null) ? 0 : Competencia.hashCode());
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
		InputBillCSV other = (InputBillCSV) obj;
		if (Competencia == null) {
			if (other.Competencia != null)
				return false;
		} else if (!Competencia.equals(other.Competencia))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "InputBillCSV [Competencia=" + Competencia + ", BillingTag=" + BillingTag + ", Client=" + Client
				+ ", Agent=" + Agent + ", Instance=" + Instance + ", Backupset=" + Backupset + ", Subclient="
				+ Subclient + ", StoragePolicy=" + StoragePolicy + ", Copy=" + Copy + ", MediaSize=" + MediaSize + "]";
	}
	
	
	
}
