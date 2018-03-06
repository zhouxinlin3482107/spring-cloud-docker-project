package com.example.uaa.users;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class UserAuthoritiesRepository {

    @PersistenceContext
    EntityManager em;

    public List<UserAuthorities> getAuthorities(String userName) {
        Query q = em.createQuery("select d from UserAuthorities d where d.userName = :userName");
        return q.setParameter("userName", userName).getResultList();
    }
}
