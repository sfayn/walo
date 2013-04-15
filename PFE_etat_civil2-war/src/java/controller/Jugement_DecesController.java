package controller;

import bean.Jugement_Deces;
import bean.Registre_Deces;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import session.Jugement_DecesFacade;

@ManagedBean(name = "jugement_DecesController")
@SessionScoped
public class Jugement_DecesController implements Serializable {

    private Jugement_Deces current;
    private DataModel items = null;
    @EJB
    private session.Jugement_DecesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String annee;
    private Registre_Deces registre_d;
    private int numActe;
    private String annee_Jug_Dec;

    public int getNumActe() {
        return numActe;
    }

    public void setNumActe(int numActe) {
        this.numActe = numActe;
    }

    public String getAnnee_Jug_Dec() {
        return annee_Jug_Dec;
    }

    public void setAnnee_Jug_Dec(String annee_Jug_Dec) {
        this.annee_Jug_Dec = annee_Jug_Dec;
    }

    public SelectItem[] listReg_Jug_dec() {
        if (annee_Jug_Dec == null && current.getRegistre() != null) {
            annee_Jug_Dec = current.getRegistre().getAnnee();
        }
        return JsfUtil.getSelectItems(ejbFacade.findByDate_Jug_Deces(annee_Jug_Dec), true);
    }

    public Registre_Deces getRegistre_d() {
        return registre_d;
    }

    public void setRegistre_d(Registre_Deces registre_d) {
        this.registre_d = registre_d;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public SelectItem[] listReg() {
        return JsfUtil.getSelectItems(ejbFacade.findByDate(annee), true);
    }

    public boolean findAct() {
        if (registre_d != null && numActe != 0) {
            if (!ejbFacade.findActe_Deces(numActe, registre_d).isEmpty()) {
                current.setActe_deces(ejbFacade.findActe_Deces(numActe, registre_d).get(0));
                return true;
            } else {
                current.setActe_deces(null);
                return false;
            }
        }
        return false;
    }

    public SelectItem[] listannee() {
        List<String> annees = new ArrayList<String>();
        for (int i = 1900; i < 2060; i++) {
            annees.add(i + "");
        }
        return JsfUtil.getSelectItems(annees, true);
    }

    public Jugement_DecesController() {
    }

    public Jugement_Deces getSelected() {
        if (current == null) {
            current = new Jugement_Deces();
            selectedItemIndex = -1;
        }
        return current;
    }

    private Jugement_DecesFacade getFacade() {
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
        current = (Jugement_Deces) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Jugement_Deces();
        selectedItemIndex = -1;
        return "Create";
    }

    public void encode() {
        try {
            current.setDescrAr(URLEncoder.encode(current.getDescrAr(), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Jugement_DecesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String create() {
        try {
            encode();
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Jugement_DecesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Jugement_Deces) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Jugement_DecesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Jugement_Deces) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Jugement_DecesDeleted"));
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

    @FacesConverter(forClass = Jugement_Deces.class)
    public static class Jugement_DecesControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            Jugement_DecesController controller = (Jugement_DecesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "jugement_DecesController");
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
            if (object instanceof Jugement_Deces) {
                Jugement_Deces o = (Jugement_Deces) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Jugement_Deces.class.getName());
            }
        }
    }
}
