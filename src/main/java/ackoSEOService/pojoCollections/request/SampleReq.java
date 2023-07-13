package ackoSEOService.pojoCollections.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleReq {
	
	private String merchantRequestId ;
	private String clientRequestId ;
	private String merchantAccessKey ;
	private String source ;
	private String billingCycle ;
	private String billingInterval ;
	private String paymentStartDate ;
	private String paymentEndDate ;
	private String amount ;
	private String setupAmount ;
	private String setupCurrency ;
	private String customerName ;
	private String customerMobNo ;
	private String customerEmail ;
	private String customerAccountNo ;
	private String customerAccountType ;
	private String bankCode ;
	private String verificationMode;
	private PgParams pgParams;
	
	public String getMerchantRequestId() {
		return merchantRequestId;
	}
	public void setMerchantRequestId(String merchantRequestId) {
		this.merchantRequestId = merchantRequestId;
	}
	public String getClientRequestId() {
		return clientRequestId;
	}
	public void setClientRequestId(String clientRequestId) {
		this.clientRequestId = clientRequestId;
	}
	public String getMerchantAccessKey() {
		return merchantAccessKey;
	}
	public void setMerchantAccessKey(String merchantAccessKey) {
		this.merchantAccessKey = merchantAccessKey;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}
	public String getBillingInterval() {
		return billingInterval;
	}
	public void setBillingInterval(String billingInterval) {
		this.billingInterval = billingInterval;
	}
	public String getPaymentStartDate() {
		return paymentStartDate;
	}
	public void setPaymentStartDate(String paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}
	public String getPaymentEndDate() {
		return paymentEndDate;
	}
	public void setPaymentEndDate(String paymentEndDate) {
		this.paymentEndDate = paymentEndDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSetupAmount() {
		return setupAmount;
	}
	public void setSetupAmount(String setupAmount) {
		this.setupAmount = setupAmount;
	}
	public String getSetupCurrency() {
		return setupCurrency;
	}
	public void setSetupCurrency(String setupCurrency) {
		this.setupCurrency = setupCurrency;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerMobNo() {
		return customerMobNo;
	}
	public void setCustomerMobNo(String customerMobNo) {
		this.customerMobNo = customerMobNo;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerAccountNo() {
		return customerAccountNo;
	}
	public void setCustomerAccountNo(String customerAccountNo) {
		this.customerAccountNo = customerAccountNo;
	}
	public String getCustomerAccountType() {
		return customerAccountType;
	}
	public void setCustomerAccountType(String customerAccountType) {
		this.customerAccountType = customerAccountType;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getVerificationMode() {
		return verificationMode;
	}
	public void setVerificationMode(String verificationMode) {
		this.verificationMode = verificationMode;
	}
	public PgParams getPgParams() {
		return pgParams;
	}
	public void setPgParams(PgParams pgParams) {
		this.pgParams = pgParams;
	}

	
	

	
	
}
