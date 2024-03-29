/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Autor;
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
public class AutorDAO implements Serializable {

    public static final AutorDAO AUTOR_DAO = new AutorDAO();
    private static final String MENSAGEM = "Não foram encontrados autores!";

    public Autor buscar(int id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT a FROM Autor a WHERE a.id = :id");
            query.setParameter("id", id);
            Autor autor = (Autor) query.getSingleResult();
            if (autor != null && autor.getId() > 0) {
                return autor;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public Autor buscar(Autor a) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT a FROM Autor a WHERE a.id = :id");
            query.setParameter("id", a.getId());
            Autor autor = (Autor) query.getSingleResult();
            if (autor != null && autor.getId() > 0) {
                return autor;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public List<Autor> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT a FROM Autor a");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ArrayList<>();
        }
    }

    public String remover(Autor autor) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            autor = em.merge(autor);
            em.remove(autor);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Autor removido com sucesso!");
            return "Autor " + autor.getNome() + " removido com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return "Não foi possível remover o autor " + autor.getNome() + ", pois está vinculado a um ou mais livros";
        }
    }

    public String persistir(Autor autor) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            autor = em.merge(autor);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Autor salvo com sucesso!");
            return "Autor " + autor.getNome() + " salvo com sucesso!";
        } catch (Exception e) {
            if (e.getMessage().contains("ConstraintViolationException")) {
                return "Não foi possível salvar o autor " + autor.getNome() + ", pois o nome deve ser único";
            }
            return "Não foi possível salvar o autor " + autor.getNome() + "!";
        }
    }    

}
