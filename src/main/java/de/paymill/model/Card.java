package de.paymill.model;

import java.util.Date;

public class Card {
	private String id;
	private String cardType;
	private String country;
	private Integer expireMonth;
	private Integer expireYear;
	private String cardHolder;
	private String last4;
	private Date createdAt;
	private Date updatedAt;

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
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @param cardType
	 *            the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the expireMonth
	 */
	public Integer getExpireMonth() {
		return expireMonth;
	}

	/**
	 * @param expireMonth
	 *            the expireMonth to set
	 */
	public void setExpireMonth(Integer expireMonth) {
		this.expireMonth = expireMonth;
	}

	/**
	 * @return the expireYear
	 */
	public Integer getExpireYear() {
		return expireYear;
	}

	/**
	 * @param expireYear
	 *            the expireYear to set
	 */
	public void setExpireYear(Integer expireYear) {
		this.expireYear = expireYear;
	}

	/**
	 * @return the cardHolder
	 */
	public String getCardHolder() {
		return cardHolder;
	}

	/**
	 * @param cardHolder
	 *            the cardHolder to set
	 */
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	/**
	 * @return the last4
	 */
	public String getLast4() {
		return last4;
	}

	/**
	 * @param last4
	 *            the last4 to set
	 */
	public void setLast4(String last4) {
		this.last4 = last4;
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
}
