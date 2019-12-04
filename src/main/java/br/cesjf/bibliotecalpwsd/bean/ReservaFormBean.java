/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.LivroDAO;
import br.cesjf.bibliotecalpwsd.dao.ReservaDAO;
import br.cesjf.bibliotecalpwsd.dao.UsuarioDAO;
import br.cesjf.bibliotecalpwsd.model.Reserva;
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
public class ReservaFormBean extends CalculoDisponibilidadeBean implements Serializable, ICrudBean {

    private static final long serialVersionUID = 1L;
    private Reserva reserva;

    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (id > 0) {
            reserva = new ReservaDAO().buscar(id);
        } else {
            reserva = new Reserva();
        }
        livros = new LivroDAO().buscarTodas();
        usuarios = new UsuarioDAO().buscarTodas();
    }

    //Métodos dos botões 
    @Override
    public void record(ActionEvent actionEvent) {
        reserva.calculaDevolucaoPrevista();
        msgScreen(new ReservaDAO().persistir(reserva));
    }

    @Override
    public void exclude(ActionEvent actionEvent) {
        msgScreen(new ReservaDAO().remover(reserva));
    }

    //getters and setters
    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public void clear() {
        reserva = new Reserva();
    }

    public boolean isNew() {
        return reserva == null || reserva.getId() == null || reserva.getId() == 0;
    }

    @Override
    public void verificaUsuario(SelectEvent event) {
        usuariosPermitidos();
        if (!usuariosPermitidos.contains(reserva.getIdUsuario())) {
            msgScreen("Não permitido. Usuário com alguma pendência.");
        }
    }

    @Override
    public void calcularExemplaresPermitidos(SelectEvent event) {
        Date dataReserva = reserva.getDataReserva();
        calcularExemplares(dataReserva);
        if (reserva.getIdExemplar() != null) {
            exemplaresPermitidos.add(reserva.getIdExemplar());
        }
    }

    public void msgScreen(String msg) {
        if (msg.contains("Não")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }

}
