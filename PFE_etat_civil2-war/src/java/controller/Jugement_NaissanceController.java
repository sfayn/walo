package controller;

import bean.Jugement_Deces;
import bean.Jugement_Naissance;
import bean.Registre;
import bean.User;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import controller.util.UtilitaireSession;
import session.Jugement_NaissanceFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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
import org.richfaces.model.Filter;

@ManagedBean(name = "jugement_NaissanceController")
@SessionScoped
public class Jugement_NaissanceController implements Serializable {

    private Jugement_Naissance current;
    private DataModel items = null;
    @EJB
    private session.Jugement_NaissanceFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String annee;
    private String annee_jug_Naiss;
    private int numActe;
    private Registre registre;
    private Long numActeFilter;
    private String anneeFilter;
    private Integer primaryRowCount = 10;

    public String getAnnee_jug_Naiss() {
        return annee_jug_Naiss;
    }

    public void setAnnee_jug_Naiss(String annee_jug_Naiss) {
        this.annee_jug_Naiss = annee_jug_Naiss;
    }

    
    public Integer getPrimaryRowCount() {
        return primaryRowCount;
    }

    public void setPrimaryRowCount(Integer primaryRowCount) {
        this.primaryRowCount = primaryRowCount;
    }

    public Long getNumActeFilter() {
        return numActeFilter;
    }

    public void setNumActeFilter(Long numActeFilter) {
        this.numActeFilter = numActeFilter;
    }

    public String getAnneeFilter() {
        return anneeFilter;
    }

    public void setAnneeFilter(String anneeFilter) {
        this.anneeFilter = anneeFilter;
    }

    public Filter<?> getNumActeFilterImpl() {
        return new Filter<Jugement_Naissance>() {
            public boolean accept(Jugement_Naissance item) {
                Long numActe = getNumActeFilter();
                if (numActe == null || numActe == 0 || numActe.compareTo(item.getNumActe().longValue()) >= 0) {
                    return true;
                }
                return false;
            }
        };
    }

    public Filter<?> getAnneeFilterImpl() {
        return new Filter<Jugement_Naissance>() {
            public boolean accept(Jugement_Naissance item) {
                String Annee = getAnneeFilter();
                if (Annee == null || Annee.length() == 0 || Annee.equals(item.getRegistre().getAnnee())) {
                    return true;
                }
                return false;
            }
        };
    }

    public void check() {
        UtilitaireSession us = UtilitaireSession.getInstance();

        if (((User) us.get("auth")).getRole().getLibelle().equals("User")) {
            return;
        }
        if (current.isChecked()) {
            current.setChecked(false);
        } else {
            current.setChecked(true);
        }
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Acte_NaissanceUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

        }
    }

    public int getNumActe() {
        return numActe;
    }

    public void setNumActe(int numActe) {
        this.numActe = numActe;
    }

    public Registre getRegistre() {
        return registre;
    }

    public void setRegistre(Registre registre) {
        this.registre = registre;
    }

    public Jugement_NaissanceController() {
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public boolean findAct() {
        if (registre != null && numActe != 0) {
            if (!ejbFacade.findActe_Naissance(numActe, registre).isEmpty()) {
                current.setActe_Naissance(ejbFacade.findActe_Naissance(numActe, registre).get(0));
                return true;
            } else {
                current.setActe_Naissance(null);
                return false;
            }
        }
        return false;
    }
    public SelectItem[] listReg_Jug_Naiss() {
        if (annee_jug_Naiss == null && current.getRegistre() != null) {
            annee_jug_Naiss = current.getRegistre().getAnnee();
        }
        return JsfUtil.getSelectItems(ejbFacade.findByDate_Jug_Naissance(annee_jug_Naiss), true);
    }

    public SelectItem[] listReg() {
        return JsfUtil.getSelectItems(ejbFacade.findByDate(annee), true);
    }

    public SelectItem[] listannee() {
        List<String> annees = new ArrayList<String>();
        for (int i = 1900; i < 2060; i++) {
            annees.add(i + "");
        }
        return JsfUtil.getSelectItems(annees, true);
    }

    public Jugement_Naissance getSelected() {
        if (current == null) {
            current = new Jugement_Naissance();
            selectedItemIndex = -1;
        }
        return current;
    }

    private Jugement_NaissanceFacade getFacade() {
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
        current = (Jugement_Naissance) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Jugement_Naissance();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            numActe = 0;
            registre = null;
            current.setCreatedAt(new Date());
            UtilitaireSession us = UtilitaireSession.getInstance();
            current.setCreatedBy((User) us.get("auth"));
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Jugement_NaissanceCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Jugement_Naissance) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Jugement_NaissanceUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Jugement_Naissance) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Jugement_NaissanceDeleted"));
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

    @FacesConverter(forClass = Jugement_Naissance.class)
    public static class Jugement_NaissanceControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            Jugement_NaissanceController controller = (Jugement_NaissanceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "jugement_NaissanceController");
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
            if (object instanceof Jugement_Naissance) {
                Jugement_Naissance o = (Jugement_Naissance) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Jugement_Naissance.class.getName());
            }
        }
    }
}
