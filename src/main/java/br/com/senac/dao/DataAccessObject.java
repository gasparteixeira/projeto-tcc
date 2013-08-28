/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Gaspar
 */
public abstract class DataAccessObject<T> {

    @PersistenceContext
    private EntityManager em;

    public DataAccessObject() {
    }
    private Class<T> tipo;

    public DataAccessObject(Class<T> tipo) {
        this.tipo = tipo;
    }

    public T create(T t) {
        this.em.persist(t);
        this.em.flush();
        this.em.refresh(t);
        return t;
    }

    public T find(Object id) {
        return this.em.find(this.tipo, id);
    }

    public void delete(Object id) {
        Object ref = this.em.getReference(this.tipo, id);
        this.em.remove(ref);
    }

    public boolean deleteItems(T[] items) {
        for (T item : items) {
            em.remove(em.merge(item));
        }
        return true;
    }

    public T update(T t) {
        return (T) this.em.merge(t);
    }

    public List findWithNamedQuery(java.lang.String namedQueryName) {
        return this.em.createNamedQuery(namedQueryName).getResultList();
    }

    public List findWithNamedQuery(java.lang.String namedQueryName, java.util.Map parameters) {
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    public List findWithNamedQuery(java.lang.String namedQueryName, java.util.Map parameters, int resultLimit) {
        Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
        Query query = this.em.createNamedQuery(namedQueryName);
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        for (Map.Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }
}
