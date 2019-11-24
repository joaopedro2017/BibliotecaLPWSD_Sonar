/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Logger;
import org.hibernate.Session;

/**
 *
 * @author dmeireles
 */
public class PersistenceUtil {

    private static final String PERSISTENCE_UNIT_NAME = "BibliotecaLPWSD";
    private static EntityManagerFactory factory;
    private static ThreadLocal<EntityManager> manager = new ThreadLocal<>();
    private static Session session;

    static {
        if (factory == null) {
            try {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            } catch (Throwable e) {
                Logger.getLogger(e.getMessage());
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    public static EntityManager getEntityManager() {
        EntityManager em = manager.get();
        if (em == null) {
            em = factory.createEntityManager();
            manager.set(em);
        }
        return em;
    }

    public static void closeEntityManager() {
        EntityManager em = manager.get();

        if (em != null) {
            em.close();
        }
        manager.set(null);
    }

    public static Session getSession() {
        if (session == null) {
            session = (Session) getEntityManager().getDelegate();
        }
        return session;
    }

}
