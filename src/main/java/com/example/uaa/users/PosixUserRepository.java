package com.example.uaa.users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class PosixUserRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public PosixUser add(String userName) {
        PosixUser posixUser = new PosixUser();
        posixUser.setUserName(userName);
        return add(posixUser);
    }

    private PosixUser add(PosixUser user) {
        em.persist(user);
        return user;
    }

    public PosixUser get(String userName) {
        Query q = em.createQuery("select u from PosixUser u where u.userName = :userName");
        return (PosixUser) q.setParameter("userName", userName).setMaxResults(1).getSingleResult();
    }
    
}
