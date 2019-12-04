package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Usuario;
import br.cesjf.bibliotecalpwsd.util.PersistenceUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dmeireles
 */
public class LoginDAO implements Serializable {

    public static final LoginDAO LOGIN_DAO = new LoginDAO();

    public static Boolean login(String usuario, String senha) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.senha = :senha");
        query.setParameter("usuario", usuario);
        query.setParameter("senha", senha);
        List<Usuario> usuarios = query.getResultList();

        String msg = usuarios.isEmpty() ? "Login efetuado com sucesso!" : "Usuário ou senha inválidos!";
        Logger.getLogger(PersistenceUtil.class.getName()).log(Level.INFO, msg);

        return !usuarios.isEmpty();       
    }

}
