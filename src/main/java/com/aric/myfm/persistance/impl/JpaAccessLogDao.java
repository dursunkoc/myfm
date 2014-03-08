package com.aric.myfm.persistance.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.aric.myfm.domain.AccessLog;
import com.aric.myfm.persistance.AccessLogDao;

public class JpaAccessLogDao extends JpaDao<AccessLog, Long> implements AccessLogDao {

	public JpaAccessLogDao() {
		super(AccessLog.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AccessLog> findAll() {

		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<AccessLog> criteriaQuery = builder.createQuery(AccessLog.class);

		Root<AccessLog> root = criteriaQuery.from(AccessLog.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<AccessLog> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
