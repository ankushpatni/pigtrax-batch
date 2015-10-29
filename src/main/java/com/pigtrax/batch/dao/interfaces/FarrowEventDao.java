package com.pigtrax.batch.dao.interfaces;

import java.util.Date;

public interface FarrowEventDao {
   Integer getFarrowEventId(Integer pigInfoId, Date farrowEventDate);
   
   Integer getFarrowEventId(Integer pigInfoId);
}
