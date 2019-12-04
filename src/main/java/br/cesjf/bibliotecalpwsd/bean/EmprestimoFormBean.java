/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.EmprestimoDAO;
import br.cesjf.bibliotecalpwsd.dao.LivroDAO;
import br.cesjf.bibliotecalpwsd.dao.UsuarioDAO;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class EmprestimoFormBean extends CalculoDisponibilidadeBean implements Serializable, ICrudBean {

    private static final long serialVersionUID = 1L;
    private Emprestimo emprestimo;

    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (id > 0) {
            emprestimo = new EmprestimoDAO().buscar(id);
        } else {
            emprestimo = new Emprestimo();
        }
        livros = new LivroDAO().buscarTodas();
        usuarios = new UsuarioDAO().buscarTodas();
    }

    //Métodos dos botões 
    @Override
    public void record(ActionEvent actionEvent) {
        emprestimo.calculaDevolucaoPrevista();
        msgScreen(new EmprestimoDAO().persistir(emprestimo));
    }

    @Override
    public void exclude(ActionEvent actionEvent) {
        msgScreen(new EmprestimoDAO().remover(emprestimo));
    }

    //getters and setters
    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public void clear() {
        emprestimo = new Emprestimo();
    }

    public boolean isNew() {
        return emprestimo == null || emprestimo.getId() == null || emprestimo.getId() == 0;
    }

    public void msgScreen(String msg) {
        if (msg.contains("Não")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }

    @Override
    public void verificaUsuario(SelectEvent event) {
        usuariosPermitidos();
        if (!usuariosPermitidos.contains(emprestimo.getIdUsuario())) {
            msgScreen("Não permitido. Usuário com alguma pendência.");
        }
    }

    @Override
    public void calcularExemplaresPermitidos(SelectEvent event) {
        Date dataEmprestimo = emprestimo.getDataEmprestimo();
        calcularExemplares(dataEmprestimo);
        if (emprestimo.getIdExemplar() != null) {
            exemplaresPermitidos.add(emprestimo.getIdExemplar());
        }
    }
}
