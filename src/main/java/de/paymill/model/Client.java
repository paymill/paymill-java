package de.paymill.model;

import java.util.Date;
import java.util.List;

public class Client implements IPaymillObject {
	private String id;
	private String description;
	private String email;
	private Date createdAt;
	private Date updatedAt;
	private List<Payment> payment;
	private List<Subscription> subscription;

	public Client() {
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public Client(String id) {
		this.id = id;
	}

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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the payments
	 */
	public List<Payment> getPayment() {
		return payment;
	}

	/**
	 * @param payments
	 *            the payments to set
	 */
	public void setPayment(List<Payment> payments) {
		this.payment = payments;
	}

	/**
	 * @return the subscription
	 */
	public List<Subscription> getSubscription() {
		return subscription;
	}

	/**
	 * @param subscription
	 *            the subscription to set
	 */
	public void setSubscription(List<Subscription> subscription) {
		this.subscription = subscription;
	}
}
