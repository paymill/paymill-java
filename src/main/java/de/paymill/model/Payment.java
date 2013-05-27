package de.paymill.model;

import java.util.Date;

public class Payment implements IPaymillObject {
	private String id;
	private Type type;
	private String client;
	private String cardType;
	private String country;
	private Integer expireMonth;
	private Integer expireYear;
	private String cardHolder;
	private String last4;
	private String code;
	private String holder;
	private String account;
	private Date createdAt;
	private Date updatedAt;

	public enum Type {
		CREDITCARD, DEBIT;
	}

	public Payment() {
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public Payment(String id) {
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
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {
		this.type = type;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the holder
	 */
	public String getHolder() {
		return holder;
	}

	/**
	 * @param holder
	 *            the holder to set
	 */
	public void setHolder(String holder) {
		this.holder = holder;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
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
	public String getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(String client) {
		this.client = client;
	}
}
