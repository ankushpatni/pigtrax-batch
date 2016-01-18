package com.pigtrax.batch.beans;


public class RoomPK {
	
	
	public String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RoomPK)
		{
			RoomPK pk =  (RoomPK)obj;
			return this.getId().equals(pk.getId());
		}
		return false;
	}
	

}
