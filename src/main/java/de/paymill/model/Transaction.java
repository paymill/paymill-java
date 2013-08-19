/**
 * 
 */
package de.paymill.model;

import java.util.Date;
import java.util.List;

/**
 * @author Daniel Florey
 * 
 */
public class Transaction implements IPaymillObject {
	public enum Status {
		PARTIAL_REFUNDED, REFUNDED, CLOSED, FAILED, PENDING;
	}

	private String id;
	private String description;
	private Integer amount;
	private Integer originAmount;
	private String currency;
	private Status status;
	private String responseCode;
	private Date createdAt;
	private Date updatedAt;
	private Client client;
	private Payment payment;
	private String token;
	private List<Refund> refunds;
	private Preauthorization preauthorization;
	private List<Fee> fees;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * @return the originAmount
	 */
	public Integer getOriginAmount() {
		return originAmount;
	}

	/**
	 * @param originAmount
	 *            the originAmount to set
	 */
	public void setOriginAmount(Integer originAmount) {
		this.originAmount = originAmount;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return response code sett documentation for a list of available response
	 *         codes
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the refunds
	 */
	public List<Refund> getRefunds() {
		return refunds;
	}

	/**
	 * @param refunds
	 *            the refunds to set
	 */
	public void setRefunds(List<Refund> refunds) {
		this.refunds = refunds;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the payment
	 */
	public Payment getPayment() {
		return payment;
	}

	/**
	 * @param payment
	 *            the payment to set
	 */
	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	/**
	 * @return the preauthorization
	 */
	public Preauthorization getPreauthorization() {
		return preauthorization;
	}

	/**
	 * @param preauthorization
	 *            the preauthorization to set
	 */
	public void setPreauthorization(Preauthorization preauthorization) {
		this.preauthorization = preauthorization;
	}

	/**
	 * @return the fees
	 */
	public List<Fee> getFees() {
		return fees;
	}

	/**
	 * @param fees
	 *            the fees to set
	 */
	public void setFees(List<Fee> fees) {
		this.fees = fees;
	}

}
