package controller;

import bean.Non_Inscrit;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import session.Non_InscritFacade;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

@ManagedBean(name = "non_InscritController")
@SessionScoped
public class Non_InscritController implements Serializable {

    private Non_Inscrit current;
    private DataModel items = null;
    @EJB
    private session.Non_InscritFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public Non_InscritController() {
    }

    public Non_Inscrit getSelected() {
        if (current == null) {
            current = new Non_Inscrit();
            selectedItemIndex = -1;
        }
        return current;
    }

    private Non_InscritFacade getFacade() {
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
        current = (Non_Inscrit) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Non_Inscrit();
        selectedItemIndex = -1;
        return "Create";
    }

    public void encode() {
        try {
            current.setNom_Ar(URLEncoder.encode(current.getNom_Ar(), "UTF-8"));
            current.setPrenom_Ar(URLEncoder.encode(current.getPrenom_Ar(), "UTF-8"));
            current.setLieu_de_Naiss_Ar(URLEncoder.encode(current.getLieu_de_Naiss_Ar(), "UTF-8"));
            current.setPrenomP_Ar(URLEncoder.encode(current.getPrenomP_Ar(), "UTF-8"));
            current.setPrenomM_Ar(URLEncoder.encode(current.getPrenomM_Ar(), "UTF-8"));
            current.setProfession_Ar(URLEncoder.encode(current.getProfession_Ar(), "UTF-8"));
            current.setAddressePa_Ar(URLEncoder.encode(current.getAddressePa_Ar(), "UTF-8"));
            current.setDonnee_Marginal(URLEncoder.encode(current.getDonnee_Marginal(), "UTF-8"));
            current.setBureau_E_C(URLEncoder.encode(current.getBureau_E_C(), "UTF-8"));
            current.setOfficier_E_C(URLEncoder.encode(current.getOfficier_E_C(), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String create() {
        try {
            encode();
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Non_InscritCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Non_Inscrit) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            encode();
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Non_InscritUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Non_Inscrit) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Non_InscritDeleted"));
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

    @FacesConverter(forClass = Non_Inscrit.class)
    public static class Non_InscritControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            Non_InscritController controller = (Non_InscritController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "non_InscritController");
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
            if (object instanceof Non_Inscrit) {
                Non_Inscrit o = (Non_Inscrit) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Non_Inscrit.class.getName());
            }
        }
    }
}
