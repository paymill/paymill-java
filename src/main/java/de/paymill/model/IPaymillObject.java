package de.paymill.model;

public abstract class IPaymillObject {
	
	public abstract String getId();
	public abstract void setId(String id);
	@Override
	public String toString() {
		return getClass().getSimpleName()+"["+(getId()==null?"none":getId())+"]";
	}
}
