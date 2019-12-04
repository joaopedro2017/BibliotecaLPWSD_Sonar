package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.EmprestimoDAO;
import br.cesjf.bibliotecalpwsd.dao.ExemplarDAO;
import br.cesjf.bibliotecalpwsd.dao.ReservaDAO;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import br.cesjf.bibliotecalpwsd.model.Exemplar;
import br.cesjf.bibliotecalpwsd.model.Livro;
import br.cesjf.bibliotecalpwsd.model.Reserva;
import br.cesjf.bibliotecalpwsd.model.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.primefaces.event.SelectEvent;

public abstract class CalculoDisponibilidadeBean {

    List<Exemplar> exemplaresPermitidos;
    List<Usuario> usuariosPermitidos;
    private Livro livro;
    List<Usuario> usuarios;
    int id;
    List<Livro> livros;

    public abstract void calcularExemplaresPermitidos(SelectEvent event);

    public abstract void verificaUsuario(SelectEvent event);

    public List<Exemplar> getExemplaresPermitidos() {
        return exemplaresPermitidos;
    }

    public void setExemplaresPermitidos(List<Exemplar> exemplaresPermitidos) {
        this.exemplaresPermitidos = exemplaresPermitidos;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public List<Usuario> getUsuariosPermitidos() {
        usuariosPermitidos();
        return usuariosPermitidos;
    }

    public void setUsuariosPermitidos(List<Usuario> usuariosPermitidos) {
        this.usuariosPermitidos = usuariosPermitidos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    protected void calcularExemplares(Date data) {
        List<Exemplar> exemplares = new ExemplarDAO().buscarTodas();
        exemplaresPermitidos = new ArrayList<>();

        if (data != null) {
            listarExemplar(exemplares);
            List<Exemplar> lista = new ArrayList<>();
            lista.addAll(exemplaresPermitidos);

            for (Exemplar e : lista) {
                listarEmprestimo(e, data);
                listarReserva(e, data);
            }
        }
    }

    public void listarExemplar(List<Exemplar> exemplares) {
        if (livro == null) {
            for (Exemplar e : exemplares) {
                if (e.getCircular()) {
                    exemplaresPermitidos.add(e);
                }
            }
        } else {
            for (Exemplar e : exemplares) {
                if (Objects.equals(e.getIdLivro().getId(), livro.getId()) && e.getCircular()) {
                    exemplaresPermitidos.add(e);
                }
            }
        }
    }

    public void listarReserva(Exemplar e, Date dataReserva) {
        for (Reserva r : new ReservaDAO().buscarTodas()) {
            if (Objects.equals(r.getIdExemplar().getId(), e.getId())
                    && (!r.getCancelada() && r.getDataReserva().compareTo(dataReserva) <= 0 && r.getDataDevolucaoPrevista().compareTo(dataReserva) >= 0)) {
                exemplaresPermitidos.remove(e);
            }
        }
    }

    public void listarEmprestimo(Exemplar e, Date dataReserva) {
        for (Emprestimo emp : new EmprestimoDAO().buscarTodas()) {
            if (Objects.equals(emp.getIdExemplar().getId(), e.getId())
                    && (dataDevolucaoNula(emp, dataReserva) || dataDevolucaoNaoNula(emp, dataReserva))) {
                exemplaresPermitidos.remove(e);
            }
        }
    }

    private boolean dataDevolucaoNaoNula(Emprestimo emp, Date dataReserva) {
        return emp.getDataDevolucao() != null && emp.getDataEmprestimo().compareTo(dataReserva) <= 0 && emp.getDataDevolucao().compareTo(dataReserva) >= 0;
    }

    private boolean dataDevolucaoNula(Emprestimo emp, Date dataReserva) {
        return emp.getDataDevolucao() == null && emp.getDataEmprestimo().compareTo(dataReserva) <= 0 && emp.getDataDevolucaoPrevista().compareTo(dataReserva) >= 0;
    }

    //Metodo 2
    protected void usuariosPermitidos() {
        usuariosPermitidos = new ArrayList<>();
        calculoUsuarios(usuariosPermitidos, usuarios);
    }

    public void calculoUsuarios(List<Usuario> usuariosPermitidos, List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            List<Emprestimo> emp = new ArrayList<>();
            for (Emprestimo e : u.getEmprestimoList()) {
                if (e.getDataDevolucao() == null) {
                    emp.add(e);
                }
            }
            if (emprestimoAluno(u, emp) || demaisPessoas(u, emp)) {
                usuariosPermitidos.add(u);
            }
        }
    }

    private boolean demaisPessoas(Usuario u, List<Emprestimo> emp) {
        return !u.getTipoTexto().equals("Aluno") && emp.size() < 5;
    }

    private boolean emprestimoAluno(Usuario u, List<Emprestimo> emp) {
        return u.getTipoTexto().equals("Aluno") && emp.size() < 3;
    }

}
