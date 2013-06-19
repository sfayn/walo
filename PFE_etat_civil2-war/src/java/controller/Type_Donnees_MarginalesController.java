package controller;

import bean.Attr;
import bean.Type_Donnees_Marginales;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import session.Type_Donnees_MarginalesFacade;

@ManagedBean(name = "type_Donnees_MarginalesController")
@SessionScoped
public class Type_Donnees_MarginalesController implements Serializable {

    private Type_Donnees_Marginales current;
    private DataModel items = null;
    @EJB
    private session.Type_Donnees_MarginalesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    List<Attr> attrs;
    Map m = new HashMap();

    public List<Attr> getAttrs() {
        return attrs;
    }

    public Map getM() {
        return m;
    }

    public void setM(Map m) {
        this.m = m;
    }

    public void setAttrs(List<Attr> attrs) {
        this.attrs = attrs;
    }

    public void changeDonnees_Marginales() {
        Attr a = new Attr();
        attrs.add(a);
    }
    public void changeDonnees_MarginalesRemove(Attr attr) {
        for (int i = 0; i < attrs.size(); i++) {
            if (attrs.get(i) == attr) {
                attrs.remove(i);
            }
        }
    }
   

    public Type_Donnees_MarginalesController() {
        Set s = new HashSet();
        s = ResourceBundle.getBundle("attrs").keySet();
        for (Iterator it = s.iterator(); it.hasNext();) {
            String object = it.next().toString();
            m.put(object, ResourceBundle.getBundle("attrs").getString(object));
        }
        attrs = new ArrayList<Attr>();
    }
    

    public Type_Donnees_Marginales getSelected() {
        if (current == null) {
            current = new Type_Donnees_Marginales();
            selectedItemIndex = -1;
        }
        return current;
    }

    public SelectItem[] getListAttrs() {
        return JsfUtil.getSelectItems(attrs, true);
    }

    private Type_Donnees_MarginalesFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Type_Donnees_Marginales) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Type_Donnees_Marginales();
        selectedItemIndex = -1;
        attrs.clear();
        return "Create";
    }

    public String create() {
        try {
            if (!attrs.isEmpty()) {
                for (int i = 0; i < attrs.size(); i++) {
                    if (current.getAttrs().equals("")) {
                        current.setAttrs(attrs.get(i).getLibelle() + ":");
                    } else {
                        current.setAttrs(current.getAttrs() + attrs.get(i).getLibelle() + ":");
                    }
                }
            }
            getFacade().create(current);
            attrs.clear();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Type_Donnees_MarginalesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        try {
            attrs.clear();
            current = (Type_Donnees_Marginales) getItems().getRowData();
            selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
            if (!current.getAttrs().isEmpty()) {
                String[] arr = current.getAttrs().split(":");
                for (int i = 0; i < arr.length; i++) {
                    attrs.add(new Attr(arr[i]));
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Type_Donnees_MarginalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Edit";
    }

    public String update() {
        try {
            if (!attrs.isEmpty()) {
                current.setAttrs("");
                for (int i = 0; i < attrs.size(); i++) {
                    if (current.getAttrs().equals("")) {
                        current.setAttrs(attrs.get(i).getLibelle() + ":");
                    } else {
                        current.setAttrs(current.getAttrs() + attrs.get(i).getLibelle() + ":");
                    }
                }
            }
            getFacade().edit(current);
            attrs.clear();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Type_Donnees_MarginalesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Type_Donnees_Marginales) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Type_Donnees_MarginalesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Type_Donnees_Marginales.class)
    public static class Type_Donnees_MarginalesControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            Type_Donnees_MarginalesController controller = (Type_Donnees_MarginalesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "type_Donnees_MarginalesController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Type_Donnees_Marginales) {
                Type_Donnees_Marginales o = (Type_Donnees_Marginales) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Type_Donnees_Marginales.class.getName());
            }
        }
    }
}
