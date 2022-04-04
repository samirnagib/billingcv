package model.entities;

public class InputBillResult {

	private String Competencia;
	private String BillingTag;
	private String Client;
	private String Agent;
	private String Instance;
	private String Backupset;
	private String Subclient;
	private String StoragePolicy;
	private String Copy;
	private double febackupsize;
	private double fearchivesize;
	private double primaryappsize;
	private double protectedappsize;
	private double MediaSize;
	private double CustoTotal;
	
	
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
	public double getFebackupsize() {
		return febackupsize;
	}
	public void setFebackupsize(double febackupsize) {
		this.febackupsize = febackupsize;
	}
	public double getFearchivesize() {
		return fearchivesize;
	}
	public void setFearchivesize(double fearchivesize) {
		this.fearchivesize = fearchivesize;
	}
	public double getPrimaryappsize() {
		return primaryappsize;
	}
	public void setPrimaryappsize(double primaryappsize) {
		this.primaryappsize = primaryappsize;
	}
	public double getProtectedappsize() {
		return protectedappsize;
	}
	public void setProtectedappsize(double protectedappsize) {
		this.protectedappsize = protectedappsize;
	}
	public double getMediaSize() {
		return MediaSize;
	}
	public void setMediaSize(double mediaSize) {
		MediaSize = mediaSize;
	}
	public double getCustoTotal() {
		return CustoTotal;
	}
	public void setCustoTotal(double custoTotal) {
		CustoTotal = custoTotal;
	}
	public InputBillResult() {
	}
	public InputBillResult(String competencia, String billingTag, String client, String agent, String instance,
			String backupset, String subclient, String storagePolicy, String copy, double febackupsize,
			double fearchivesize, double primaryappsize, double protectedappsize, double mediaSize, double custoTotal) {
		Competencia = competencia;
		BillingTag = billingTag;
		Client = client;
		Agent = agent;
		Instance = instance;
		Backupset = backupset;
		Subclient = subclient;
		StoragePolicy = storagePolicy;
		Copy = copy;
		this.febackupsize = febackupsize;
		this.fearchivesize = fearchivesize;
		this.primaryappsize = primaryappsize;
		this.protectedappsize = protectedappsize;
		MediaSize = mediaSize;
		CustoTotal = custoTotal;
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
		InputBillResult other = (InputBillResult) obj;
		if (Competencia == null) {
			if (other.Competencia != null)
				return false;
		} else if (!Competencia.equals(other.Competencia))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InputBillResult [Competencia=" + Competencia + ", BillingTag=" + BillingTag + ", Client=" + Client
				+ ", Agent=" + Agent + ", Instance=" + Instance + ", Backupset=" + Backupset + ", Subclient="
				+ Subclient + ", StoragePolicy=" + StoragePolicy + ", Copy=" + Copy + ", febackupsize=" + febackupsize
				+ ", fearchivesize=" + fearchivesize + ", primaryappsize=" + primaryappsize + ", protectedappsize="
				+ protectedappsize + ", MediaSize=" + MediaSize + ", CustoTotal=" + CustoTotal + "]";
	}

	
	
}
