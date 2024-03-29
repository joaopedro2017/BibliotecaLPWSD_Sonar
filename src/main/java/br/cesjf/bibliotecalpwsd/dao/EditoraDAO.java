/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Editora;
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
public class EditoraDAO implements Serializable {

    public static final EditoraDAO EDITORA_DAO = new EditoraDAO();
    private static final String MENSAGEM = "Não foram encontradas editoras!";

    public Editora buscar(int id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Editora e WHERE e.id = :id");
            query.setParameter("id", id);
            Editora editora = (Editora) query.getSingleResult();
            if (editora != null && editora.getId() > 0) {
                return editora;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public Editora buscar(Editora ed) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Editora e WHERE e.id = :id");
            query.setParameter("id", ed.getId());
            Editora editora = (Editora) query.getSingleResult();
            if (editora != null && editora.getId() > 0) {
                return editora;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public List<Editora> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Editora e");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ArrayList<>();
        }
    }

    public String remover(Editora editora) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            editora = em.merge(editora);
            em.remove(editora);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
            return "Editora " + editora.getNome() + " removida com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return "Não foi possível remover a editora " + editora.getNome() + "!";
        }
    }

    public String persistir(Editora editora) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            editora = em.merge(editora);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Editora salva com sucesso!");
            return "Editora " + editora.getNome() + " salva com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            if (e.getMessage().contains("ConstraintViolationException")) {
                return "Não foi possível salvar a editora " + editora.getNome() + ", pois o nome deve ser único";
            }
            return "Não foi possível salvar a editora " + editora.getNome() + "!";
        }
    }   

}
