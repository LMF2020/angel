package com.angel.my.dao;

import com.angel.my.model.TOrderList;
import com.starit.common.dao.hibernate4.HibernateBaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class ITOrderListDao  extends HibernateBaseDaoImpl<TOrderList,String> {
}
