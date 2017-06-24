package com.salmat.jbm.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

public class HolidayDate extends Date implements UserType {

	@Override
	public Object assemble(Serializable cached, Object arg1)
			throws HibernateException {
		// TODO Auto-generated method stub
		return cached;
	}

	@Override
	public Object deepCopy(Object obj) throws HibernateException {
		// TODO Auto-generated method stub
		Date sourceDate = (Date) obj;  
        Date targetDate = new Date(sourceDate.getTime());             
        return targetDate;  
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return (Serializable) arg0;
	}

	@Override
	public boolean equals(Object obj1, Object obj2) throws HibernateException {
		// TODO Auto-generated method stub
		return ObjectUtils.equals(obj1, obj2);
	}

	@Override
	public int hashCode(Object obj) throws HibernateException {
		// TODO Auto-generated method stub
		assert (obj != null);
        return obj.hashCode();
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet arg0, String[] arg1, Object arg2)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement arg0, Object arg1, int arg2)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		// TODO Auto-generated method stub
		return original;
	}

	@Override
	public Class<Date> returnedClass() {
		// TODO Auto-generated method stub
		return Date.class;
	}

	@Override
	public int[] sqlTypes() {
		// TODO Auto-generated method stub
		return new int[] { Types.DATE };
	}

}
