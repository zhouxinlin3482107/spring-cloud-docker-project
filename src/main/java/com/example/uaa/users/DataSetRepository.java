package com.example.uaa.users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class DataSetRepository {

    @PersistenceContext
    EntityManager em;

    public List<DataSet> list() {
        Query q = em.createQuery("select d from DataSet d");
        return q.getResultList();
    }

    @Transactional
    public DataSet add(DataSet dataSet) {
        em.persist(dataSet);
        return dataSet;
    }
}
