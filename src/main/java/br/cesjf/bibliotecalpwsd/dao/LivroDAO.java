/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Livro;
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
public class LivroDAO implements Serializable {

    public static final LivroDAO LIVRO_DAO = new LivroDAO();
    private static final String MENSAGEM = "Não foram encontrados livros!";

    public Livro buscar(int id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Livro l WHERE l.id = :id");
            query.setParameter("id", id);
            Livro livro = (Livro) query.getSingleResult();
            if (livro != null && livro.getId() > 0) {
                return livro;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public List<Livro> buscar(String titulo) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Livro l WHERE l.titulo LIKE :titulo");
            query.setParameter("titulo", "%" + titulo + "%");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public Livro buscar(Livro l) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Livro l WHERE l.id = :id");
            query.setParameter("id", l.getId());
            Livro livro = (Livro) query.getSingleResult();
            if (livro != null && livro.getId() > 0) {
                return livro;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public List<Livro> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT l FROM Livro l");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ArrayList<>();
        }
    }

    public String remover(Livro livro) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            livro = em.merge(livro);
            em.remove(livro);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Livro removido com sucesso!");
            return "Livro " + livro.getTitulo() + " removido com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return "Não foi possível remover o livro " + livro.getTitulo() + ", pois está vinculado a um ou mais exemplares";
        }
    }

    public String persistir(Livro livro) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            livro = em.merge(livro);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Livro salvo com sucesso!");
            return "Livro " + livro.getTitulo() + " salvo com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            if (e.getMessage().contains("ConstraintViolationException")) {
                return "Não foi possível salvar o livro " + livro.getTitulo() + ", pois o título deve ser único";
            }
            return "Não foi possível salvar o livro " + livro.getTitulo() + "!";
        }
    }
}
