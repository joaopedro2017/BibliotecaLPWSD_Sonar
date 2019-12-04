/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Reserva;
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
public class ReservaDAO implements Serializable {

    public static final ReservaDAO RESERVA_DAO = new ReservaDAO();
    private static final String MENSAGEM = "Não foram encontradas reservas!";

    public Reserva buscar(int id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT r FROM Reserva r WHERE r.id = :id");
            query.setParameter("id", id);
            Reserva reserva = (Reserva) query.getSingleResult();
            if (reserva != null && reserva.getId() > 0) {
                return reserva;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public Reserva buscar(Reserva r) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT r FROM Reserva r WHERE r.id = :id");
            query.setParameter("id", r.getId());
            Reserva reserva = (Reserva) query.getSingleResult();
            if (reserva != null && reserva.getId() > 0) {
                return reserva;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public List<Reserva> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT r FROM Reserva r");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ArrayList<>();
        }
    }

    public String remover(Reserva reserva) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            reserva = em.merge(reserva);
            em.remove(reserva);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Reserva removida com sucesso!");
            return "Reserva " + reserva.getId() + " removida com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return "Não foi possível remover a reserva " + reserva.getId() + "!";
        }
    }

    public String persistir(Reserva reserva) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            reserva = em.merge(reserva);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Reserva salva com sucesso!");
            return "Reserva " + reserva.getId() + " salva com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return "Não foi possível salvar a reserva " + reserva.getId() + "!";
        }
    }   

}
