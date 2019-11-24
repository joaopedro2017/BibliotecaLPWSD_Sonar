/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.converter;

import br.cesjf.bibliotecalpwsd.dao.ExemplarDAO;
import br.cesjf.bibliotecalpwsd.model.Exemplar;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Daniel Meireles
 */
@FacesConverter(forClass = Exemplar.class, value = "exemplarConverter")
public class ExemplarConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
        return (id != null && !id.isEmpty())
                ? new ExemplarDAO().buscar(Integer.valueOf(id))
                : id;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
        if (objeto != null) {
            Exemplar exemplar = (Exemplar) objeto;
            return exemplar.getId() != null && exemplar.getId() > 0 ? exemplar.getId().toString() : null;
        }
        return null;
    }
}
