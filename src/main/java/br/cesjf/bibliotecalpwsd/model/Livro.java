/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dmeireles
 */
@Entity
@Table(name = "Livro")
@XmlRootElement
public class Livro implements Serializable, Comparable<Livro> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "isbn")
    private String isbn;
    @Basic(optional = false)
    @Column(name = "edicao")
    private Integer edicao;
    @Basic(optional = false)
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "capa")
    private String capa;
    @Column(name = "arquivo")
    private String arquivo;
    @ManyToMany
    @JoinTable(name = "LivroAssunto",
            joinColumns = @JoinColumn(name = "idLivro"),
            inverseJoinColumns = @JoinColumn(name = "idAssunto")
    )
    private List<Assunto> assuntoList;
    @ManyToMany
    @JoinTable(name = "AutorLivro",
            joinColumns = @JoinColumn(name = "idLivro"),
            inverseJoinColumns = @JoinColumn(name = "idAutor")
    )
    private List<Autor> autorList;
    @OneToMany(mappedBy = "idLivro")
    private List<Exemplar> exemplarList;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idEditora", referencedColumnName = "id")
    private Editora idEditora;

    public Livro() {
    }

    public Livro(Integer id) {
        this.id = id;
    }

    public Livro(Integer id, String titulo, String isbn, int edicao, Integer ano) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.edicao = edicao;
        this.ano = ano;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getEdicao() {
        return edicao;
    }

    public void setEdicao(Integer edicao) {
        this.edicao = edicao;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public StringBuilder getAssuntos() {
        StringBuilder texto = new StringBuilder();
        for (Assunto a : assuntoList) {
            texto.append(a.getAssunto()).append("; ");
        }
        return texto;
    }

    public StringBuilder getAutores() {
        StringBuilder texto = new StringBuilder();
        for (Autor a : autorList) {
            texto.append(a.getNome()).append("; ");
        }
        return texto;
    }

    @XmlTransient
    public List<Assunto> getAssuntoList() {
        return assuntoList;
    }

    public void setAssuntoList(List<Assunto> assuntoList) {
        this.assuntoList = assuntoList;
    }

    @XmlTransient
    public List<Autor> getAutorList() {
        return autorList;
    }

    public void setAutorList(List<Autor> autorList) {
        this.autorList = autorList;
    }

    @XmlTransient
    public List<Exemplar> getExemplarList() {
        return exemplarList;
    }

    public void setExemplarList(List<Exemplar> exemplarList) {
        this.exemplarList = exemplarList;
    }

    public Editora getIdEditora() {
        return idEditora;
    }

    public void setIdEditora(Editora idEditora) {
        this.idEditora = idEditora;
    }

    public int getQtdeExemplarCircular() {
        int i = 0;
        for (Exemplar e : exemplarList) {
            if (e.getCircular()) {
                i++;
            }
        }
        return i;
    }

    public int getQtdeExemplarNaoCircular() {
        int i = 0;
        for (Exemplar e : exemplarList) {
            if (!e.getCircular()) {
                i++;
            }
        }
        return i;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Livro)) {
            return false;
        }
        Livro other = (Livro) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return titulo;
    }

    @Override
    public int compareTo(Livro livro) {
        return this.titulo.toLowerCase().compareTo(livro.getTitulo().toLowerCase());
    }

}
