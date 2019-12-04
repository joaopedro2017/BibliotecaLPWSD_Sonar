package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Usuario;
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
public class UsuarioDAO implements Serializable {

    public static final UsuarioDAO USUARIO_DAO = new UsuarioDAO();
    private static final String MENSAGEM = "Não foram encontrados usuarios!";

    public Usuario buscar(int id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.id = :id");
            query.setParameter("id", id);
            Usuario usuario = (Usuario) query.getSingleResult();
            if (usuario != null && usuario.getId() > 0) {
                return usuario;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public Usuario buscar(Usuario u) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.id = :id");
            query.setParameter("id", u.getId());
            Usuario usuario = (Usuario) query.getSingleResult();
            if (usuario != null && usuario.getId() > 0) {
                return usuario;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public Usuario buscar(String u) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario");
            query.setParameter("usuario", u);
            Usuario usuario = (Usuario) query.getSingleResult();
            if (usuario != null && usuario.getId() > 0) {
                return usuario;
            } else {
                Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, MENSAGEM);
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    public List<Usuario> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT u FROM Usuario u");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ArrayList<>();
        }
    }

    public String remover(Usuario usuario) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.remove(usuario);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Usuario removido com sucesso!");
            return "Usuario " + usuario.getNome() + " removido com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return "Não foi possível remover o usuario " + usuario.getNome() + ", pois ele possui reservas ou empréstimos vinculados";
        }
    }

    public String persistir(Usuario usuario) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
            Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, "Usuario salvo com sucesso!");
            return "Usuario " + usuario.getNome() + " salvo com sucesso!";
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            if (e.getMessage().contains("ConstraintViolationException")) {
                return "Não foi possível salvar o usuário " + usuario.getNome() + ", pois o usuário deve ser único";
            }
            return "Não foi possível salvar o usuário " + usuario.getNome() + "!";
        }
    }
}
