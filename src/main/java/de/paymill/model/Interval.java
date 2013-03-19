package de.paymill.model;

public class Interval {
	public enum Unit {
		DAY, WEEK, MONTH, YEAR
	}

	private Integer interval;
	private Unit unit;

	/**
	 * @return the interval
	 */
	public Integer getInterval() {
		return interval;
	}

	/**
	 * @param interval
	 *            the interval to set
	 */
	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	/**
	 * @return the unit
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * @param unit
	 *            the unit to set
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		return interval + " " + unit;
	}
}