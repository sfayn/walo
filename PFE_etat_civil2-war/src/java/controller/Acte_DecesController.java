package controller;

import bean.Acte_Deces;
import bean.Acte_Naissance;
import bean.Registre;
import controller.util.Helper;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
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
import session.Acte_DecesFacade;

@ManagedBean(name = "acte_DecesController")
@SessionScoped
public class Acte_DecesController implements Serializable {

    private Acte_Deces current;
    private DataModel items = null;
    @EJB
    private session.Acte_DecesFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String datetasH_Ar;
    private String datetasH_Fr;
    private String datetasM_Ar;
    private String datetasM_Fr;
    private Date g_to_hTah;
    private String annee;
    private String annee_d;
    private int numActe;
    private Registre registre;
    private int l = 0;

    public String getDatetasH_Ar() {
        if (current.getDateTah_H() == null) {
            return current.getDateTah_G() == null ? "" : Helper.dateHToStrArH(current.getDateTah_G());
        } else {
            return current.getDateTah_H() == null ? "" : Helper.dateHToStrArH(current.getDateTah_H());
        }
    }

    public void setDatetasH_Ar(String datetasH_Ar) {
        this.datetasH_Ar = datetasH_Ar;
    }

    public String getDatetasH_Fr() {
        if (current.getDateTah_H() == null) {
            return current.getDateTah_G() == null ? "" : Helper.dateToStrH(current.getDateTah_G());
        } else {
            return current.getDateTah_H() == null ? "" : Helper.dateHToStrH(current.getDateTah_H());
        }

    }

    public void setDatetasH_Fr(String datetasH_Fr) {
        this.datetasH_Fr = datetasH_Fr;
    }

    public String getDatetasM_Ar() {
        return current.getDateTah_G() == null ? "" : Helper.dateToStrArG(current.getDateTah_G());
    }

    public void setDatetasM_Ar(String datetasM_Ar) {
        this.datetasM_Ar = datetasM_Ar;
    }

    public String getDatetasM_Fr() {
        return current.getDateTah_G() == null ? "" : Helper.dateToStrG(current.getDateTah_G());
    }

    public void setDatetasM_Fr(String datetasM_Fr) {
        this.datetasM_Fr = datetasM_Fr;
    }

    public void g_to_hTahplus() {
        if (current.getDateTah_G() != null) {
            l++;
            current.getDateTah_G().setDate(current.getDateTah_G().getDate() + l);
        }
    }

    public Date getG_to_hTah() {
        if (current.getDateTah_G() == null) {
            return null;
        } else {
            current.setDateTah_H(Helper.dateGrToH(current.getDateTah_G()));
            return current.getDateTah_H();
        }
    }

    public void g_to_hTahmoins() {
        if (current.getDateTah_G() != null) {
            l--;
            current.getDateTah_G().setDate(current.getDateTah_G().getDate() + l);
        }
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
        changeDeclaration();
    }

    public void setG_to_hTah(Date g_to_hTah) {
        this.g_to_hTah = g_to_hTah;
    }
public SelectItem[] listSituationFam() {
        List<String> situation = new ArrayList<String>();        
            situation.add("عازب");
            situation.add("متزوج");
            situation.add("مطلق");
            situation.add("أرمل");
        return JsfUtil.getSelectItems(situation, true);
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

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getAnnee_d() {
        return annee_d;
    }

    public void setAnnee_d(String annee_d) {
        this.annee_d = annee_d;
    }

    public SelectItem[] listReg() {
        return JsfUtil.getSelectItems(ejbFacade.findByDate(annee), true);
    }
     public SelectItem[] listReg_dec() {
        return JsfUtil.getSelectItems(ejbFacade.findByDate_Deces(annee_d), true);
    }

    public void changeDeclaration() {

        if (current.isTypeT()) {
            current.setDeclaration_Fr("Sur la base de ce qui est venu dans le numéro du jugement " + Helper.dateToStrH(current.getDateHo()) + " correspondant au " + Helper.dateToStrG(current.getDateHo()) + " dans le dossier numéro   du Tribunal de première instance à ");
            current.setDeclaration_Ar(" بناء على ما جاء في الحكم عدد   الصادر بتاريخ " + Helper.dateToStrArH(current.getDateHo()) + "الموافق ل " + Helper.dateToStrArG(current.getDateHo()) + "  في الملف عدد     عن المحكمة الإبتدائية ب ");
        } else {
            try {
                current.setDeclaration_Ar("test");
            } catch (Exception ex) {
                Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
            }
            current.setDeclaration_Fr("test");
        }
    }

    public boolean findAct() {
        if (registre != null && numActe != 0) {
            if (!ejbFacade.findActe_Naiss(numActe, registre).isEmpty()) {
                current.setActe_Naissance(ejbFacade.findActe_Naiss(numActe, registre).get(0));
                return true;
            } else {
                current.setActe_Naissance(null);
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

    public Acte_DecesController() {
    }

    public Acte_Deces getSelected() {
        if (current == null) {
            current = new Acte_Deces();
            selectedItemIndex = -1;
        }
        return current;
    }

    private Acte_DecesFacade getFacade() {
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
        current = (Acte_Deces) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Acte_Deces();
        selectedItemIndex = -1;
        return "Create";
    }
    
    
    public void encode() {
        try {
            current.setAdresse_Ar(URLEncoder.encode(current.getAdresse_Ar(), "UTF-8"));
            current.setDeclaration_Ar(URLEncoder.encode(current.getDeclaration_Ar(), "UTF-8"));
            current.setSituation_familiale(URLEncoder.encode(current.getSituation_familiale(), "UTF-8"));
            current.setLieuDeces_Ar(URLEncoder.encode(current.getLieuDeces_Ar(), "UTF-8"));
            current.setProfession_Ar(URLEncoder.encode(current.getProfession_Ar(), "UTF-8"));
            
 } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public String create() {
        try {
            encode();
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Acte_DecesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Acte_Deces) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Acte_DecesUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Acte_Deces) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Acte_DecesDeleted"));
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

    @FacesConverter(forClass = Acte_Deces.class)
    public static class Acte_DecesControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            Acte_DecesController controller = (Acte_DecesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "acte_DecesController");
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
            if (object instanceof Acte_Deces) {
                Acte_Deces o = (Acte_Deces) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Acte_Deces.class.getName());
            }
        }
    }
}
