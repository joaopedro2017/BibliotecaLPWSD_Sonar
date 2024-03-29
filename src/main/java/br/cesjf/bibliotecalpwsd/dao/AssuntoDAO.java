/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Assunto;
import br.cesjf.bibliotecalpwsd.util.PersistenceUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author dmeireles
 */
public class AssuntoDAO implements Serializable {

    public static final AssuntoDAO ASSUNTO_DAO = new AssuntoDAO();
    private static final String MENSAGEM = "Não foram encontrados assuntos!";

    public Assunto buscar(int id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT a FROM Assunto a WHERE a.id = :id");
            query.setParameter("id", id);
            Assunto assunto = (Assunto) query.getSingleResult();
            if (assunto != null && assunto.getId() > 0) {
                return assunto;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public Assunto buscar(Assunto a) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT a FROM Assunto a WHERE a.id = :id");
            query.setParameter("id", a.getId());
            Assunto assunto = (Assunto) query.getSingleResult();
            if (assunto != null && assunto.getId() > 0) {
                return assunto;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public List<Assunto> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT a FROM Assunto a");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ArrayList<>();
        }
    }

    public String remover(Assunto assunto) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            assunto = em.merge(assunto);
            em.remove(assunto);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Assunto removido com sucesso!");
            return "Assunto " + assunto.getAssunto() + " removido com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return "Não foi possível remover o assunto " + assunto.getAssunto() + " , pois está vinculado a um ou mais livros";
        }
    }

    public String persistir(Assunto assunto) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            assunto = em.merge(assunto);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Assunto salvo com sucesso!");
            return "Assunto " + assunto.getAssunto() + " salvo com sucesso!";
        } catch (Exception e) {
            if (e.getMessage().contains("ConstraintViolationException")) {
                return "Não foi possível salvar o assunto " + assunto.getAssunto() + ", pois o assunto deve ser único";
            }
            return "Não foi possível salvar o assunto" + assunto.getAssunto() + "!";
        }
    }
}
