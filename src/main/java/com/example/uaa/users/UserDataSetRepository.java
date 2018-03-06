package com.example.uaa.users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Service
public class UserDataSetRepository {

    @PersistenceContext
    EntityManager em;

    public List<UserDataSet> getDataSets(String userName) {
        Query q = em.createQuery("select d from UserDataSet d where d.userName = :userName");
        return q.setParameter("userName", userName).getResultList();
    }

    public UserDataSet getUserDataSet(long id) {
        return em.find(UserDataSet.class, id);
    }

    public UserDataSet getUserDataSet(String userName, String dataSetId) {
        Query q = em.createQuery("select d from UserDataSet d where d.userName = :userName and d.dataSetId = :dataSetId");
        return (UserDataSet) q.setParameter("userName", userName).setParameter("dataSetId", dataSetId).getSingleResult();
    }

    @Transactional
    public UserDataSet add(UserDataSet userDataSet) {
        userDataSet.setCreateTime(new Date(System.currentTimeMillis()));
        em.persist(userDataSet);
        return userDataSet;
    }

    @Transactional
    public void delete(UserDataSet userDataSet) {
        em.remove(getUserDataSet(userDataSet.getUserName(), userDataSet.getDataSetId()));
    }
}
