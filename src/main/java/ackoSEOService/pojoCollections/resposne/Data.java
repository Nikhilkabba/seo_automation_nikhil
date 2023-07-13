package ackoSEOService.pojoCollections.resposne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
	
	private String AuthMode ;
	private String bankID ;
	private String checkSumVal ;
	private String mandateReqDoc ;
	private String merchantID ;
	
	
	
	public String getAuthMode() {
		return AuthMode;
	}
	public void setAuthMode(String AuthMode) {
		this.AuthMode = AuthMode;
	}
	public String getBankID() {
		return bankID;
	}
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	public String getCheckSumVal() {
		return checkSumVal;
	}
	public void setCheckSumVal(String checkSumVal) {
		this.checkSumVal = checkSumVal;
	}
	public String getMandateReqDoc() {
		return mandateReqDoc;
	}
	public void setMandateReqDoc(String mandateReqDoc) {
		this.mandateReqDoc = mandateReqDoc;
	}
	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	


	
	
	
	

	
	
	
	

}
