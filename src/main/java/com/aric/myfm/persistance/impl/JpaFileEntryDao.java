package com.aric.myfm.persistance.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.aric.myfm.domain.FileEntry;
import com.aric.myfm.persistance.FileEntryDao;


/**
 * 
 */
public class JpaFileEntryDao extends JpaDao<FileEntry, Long> implements FileEntryDao {

	public JpaFileEntryDao() {

		super(FileEntry.class);
	}


	@Override
	@Transactional(readOnly = true)
	public List<FileEntry> findAll() {

		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<FileEntry> criteriaQuery = builder.createQuery(FileEntry.class);

		Root<FileEntry> root = criteriaQuery.from(FileEntry.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<FileEntry> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
