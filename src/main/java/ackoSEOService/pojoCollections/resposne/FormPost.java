package ackoSEOService.pojoCollections.resposne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FormPost {
	
	private String issuerUrl ;
	private Data data;
	
	public String getIssuerUrl() {
		return issuerUrl;
	}
	public void setIssuerUrl(String issuerUrl) {
		this.issuerUrl = issuerUrl;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}


}
