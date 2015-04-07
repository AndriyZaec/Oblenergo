package net.ukr.ifkep.oblenergo.domain;

public class Payments extends DomainType {
	
	private String PaymentDate;
	private String CurPayment;
	private float CurIndex;
	private float LastIndex;
	private float Limit;
	private float ExcessLimit;
	private double Debt;
	private double Price;
	private String Payer;
	
	/**
	 * @return the paymentDate
	 */
	public String getPaymentDate() {
		return PaymentDate;
	}
	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(String paymentDate) {
		PaymentDate = paymentDate;
	}
	/**
	 * @return the curPayment
	 */
	public String getCurPayment() {
		return CurPayment;
	}
	/**
	 * @param curPayment the curPayment to set
	 */
	public void setCurPayment(String curPayment) {
		CurPayment = curPayment;
	}
	/**
	 * @return the curIndex
	 */
	public float getCurIndex() {
		return CurIndex;
	}
	/**
	 * @param curIndex the curIndex to set
	 */
	public void setCurIndex(float curIndex) {
		CurIndex = curIndex;
	}
	/**
	 * @return the lastIndex
	 */
	public float getLastIndex() {
		return LastIndex;
	}
	/**
	 * @param lastIndex the lastIndex to set
	 */
	public void setLastIndex(float lastIndex) {
		LastIndex = lastIndex;
	}
	/**
	 * @return the limit
	 */
	public float getLimit() {
		return Limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(float limit) {
		Limit = limit;
	}
	/**
	 * @return the excessLimit
	 */
	public float getExcessLimit() {
		return ExcessLimit;
	}
	/**
	 * @param excessLimit the excessLimit to set
	 */
	public void setExcessLimit(float excessLimit) {
		ExcessLimit = excessLimit;
	}
	/**
	 * @return the debt
	 */
	public double getDebt() {
		return Debt;
	}
	/**
	 * @param debt the debt to set
	 */
	public void setDebt(double debt) {
		Debt = debt;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return Price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		Price = price;
	}
	/**
	 * @return the payer
	 */
	public String getPayer() {
		return Payer;
	}
	/**
	 * @param payer the payer to set
	 */
	public void setPayer(String payer) {
		Payer = payer;
	}

	

}
