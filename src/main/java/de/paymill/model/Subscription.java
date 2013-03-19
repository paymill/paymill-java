package de.paymill.model;

import java.util.Date;

public class Subscription implements IPaymillObject {
	private String id;
	private Offer offer;
	private Client client;
	private Payment payment;
	private Boolean cancelAtPeriodEnd;
	private Date trialStart;
	private Date trialEnd;
	private Date canceledAt;
	private Date createdAt;
	private Date updatedAt;
	private Date nextCaptureAt;	

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
	 * @return the offer
	 */
	public Offer getOffer() {
		return offer;
	}


	/**
	 * @param offer
	 *            the offer to set
	 */
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	/**
	 * @return the cancelAtPeriodEnd
	 */
	public Boolean getCancelAtPeriodEnd() {
		return cancelAtPeriodEnd;
	}

	/**
	 * @param cancelAtPeriodEnd
	 *            the cancelAtPeriodEnd to set
	 */
	public void setCancelAtPeriodEnd(Boolean cancelAtPeriodEnd) {
		this.cancelAtPeriodEnd = cancelAtPeriodEnd;
	}

	/**
	 * @return the trialStart
	 */
	public Date getTrialStart() {
		return trialStart;
	}

	/**
	 * @param trialStart
	 *            the trialStart to set
	 */
	public void setTrialStart(Date trialStart) {
		this.trialStart = trialStart;
	}

	/**
	 * @return the trialEnd
	 */
	public Date getTrialEnd() {
		return trialEnd;
	}

	/**
	 * @param trialEnd
	 *            the trialEnd to set
	 */
	public void setTrialEnd(Date trialEnd) {
		this.trialEnd = trialEnd;
	}

	/**
	 * @return the canceledAt
	 */
	public Date getCanceledAt() {
		return canceledAt;
	}

	/**
	 * @param canceledAt
	 *            the canceledAt to set
	 */
	public void setCanceledAt(Date canceledAt) {
		this.canceledAt = canceledAt;
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

	public Date getNextCaptureAt() {
		return nextCaptureAt;
	}

	public void setNextCaptureAt(Date nextCaptureAt) {
		this.nextCaptureAt = nextCaptureAt;
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
	 * @return the payment
	 */
	public Payment getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
