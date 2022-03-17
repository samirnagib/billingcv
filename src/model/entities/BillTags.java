package model.entities;

import java.io.Serializable;

public class BillTags implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer idbillTag;
	private String billtagName;
	private Double billPriceTB;
	
	
	public BillTags() {
	
	}


	public BillTags(Integer idbillTag, String billtagName, Double billPriceTB) {
		this.idbillTag = idbillTag;
		this.billtagName = billtagName;
		this.billPriceTB = billPriceTB;
	}


	public Integer getIdbillTag() {
		return idbillTag;
	}


	public void setIdbillTag(Integer idbillTag) {
		this.idbillTag = idbillTag;
	}


	public String getBilltagName() {
		return billtagName;
	}


	public void setBilltagName(String billtagName) {
		this.billtagName = billtagName;
	}


	public Double getBillPriceTB() {
		return billPriceTB;
	}


	public void setBillPriceTB(Double billPriceTB) {
		this.billPriceTB = billPriceTB;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idbillTag == null) ? 0 : idbillTag.hashCode());
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
		BillTags other = (BillTags) obj;
		if (idbillTag == null) {
			if (other.idbillTag != null)
				return false;
		} else if (!idbillTag.equals(other.idbillTag))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "billTAGS [idbillTag=" + idbillTag + ", billtagName=" + billtagName + ", billPriceTB=" + billPriceTB
				+ "]";
	}
	
	
	

}
