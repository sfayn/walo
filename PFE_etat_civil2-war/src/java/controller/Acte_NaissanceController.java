package controller;

import bean.Acte_Naissance;
import controller.util.Helper;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import java.io.File;
import java.io.IOException;


import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.ini4j.Wini;
import session.Acte_NaissanceFacade;

@ManagedBean(name = "acte_NaissanceController")
@SessionScoped
public class Acte_NaissanceController implements Serializable {

    private Acte_Naissance current;
    private DataModel items = null;
    private String datetasH_Ar;
    private String datetasH_Fr;
    private String datetasM_Ar;
    private String datetasM_Fr;
    private Date datetasH_Obj;

    public void setDatetasH_Obj(Date datetasH_Obj) {
        this.datetasH_Obj = datetasH_Obj;
    }
    @EJB
    private session.Acte_NaissanceFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public String getDatetasH_Ar() {
        current.setDateTah_H(current.getDateTah_G() == null ? null : Helper.dateGrToH(current.getDateTah_G()));
        return current.getDateTah_G() == null ? "null" : Helper.dateToStrArH(current.getDateTah_G());
    }

    public Date getDatetasH_Obj() {
        if (current.getDateTah_G() == null) {
            return null;
        } else {
            current.setDateTah_H(Helper.dateGrToH(current.getDateTah_G()));
            return current.getDateTah_H();
        }
    }

    public void setDatetasH_Ar(String datetasH_Ar) {
        this.datetasH_Ar = datetasH_Ar;
    }

    public String getDatetasH_Fr() {
        return current.getDateTah_G() == null ? "null" : Helper.dateToStrH(current.getDateTah_G());
    }

    public void setDatetasH_Fr(String datetasH_Fr) {
        this.datetasH_Fr = datetasH_Fr;
    }

    public String getDatetasM_Ar() {
        return current.getDateTah_G() == null ? "null" : Helper.dateToStrArG(current.getDateTah_G());
    }

    public void setDatetasM_Ar(String datetasM_Ar) {
        this.datetasM_Ar = datetasM_Ar;
    }

    public String getDatetasM_Fr() {
        return current.getDateTah_G() == null ? "null" : Helper.dateToStrG(current.getDateTah_G());
    }

    public void setDatetasM_Fr(String datetasM_Fr) {
        this.datetasM_Fr = datetasM_Fr;
    }

    public Acte_NaissanceController() {
    }

    public Acte_Naissance getSelected() {
        if (current == null) {
            current = new Acte_Naissance();
            selectedItemIndex = -1;
        }
        return current;
    }

    private Acte_NaissanceFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(getFacade().count()) {
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
        current = (Acte_Naissance) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Acte_Naissance();
        selectedItemIndex = -1;
        recreatePagination();
        recreateModel();
        return "Create";
    }

    public Date g_to_h() {
        System.out.println("haniii"+current.getDate_de_naiss_G());
        return current.getDate_de_naiss_G() == null ? null : Helper.dateGrToH(current.getDate_de_naiss_G());
    }

    public String create() {
        try {
            System.out.println();
            String enc = URLEncoder.encode(current.getNom_Ar(), "UTF-8");
            System.out.println(enc);
            current.setNom_Ar(enc);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Acte_NaissanceCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Acte_Naissance) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Acte_NaissanceUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void check() {
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

    public void PDF() throws JRException, IOException {
        List<Acte_Naissance> acts = new ArrayList<Acte_Naissance>();
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        params.put("nom", current.getNom_Fr());
        params.put("prenom", current.getPrenom_Fr());
        params.put("lieuNaissance", current.getLieu_de_Naiss_Fr());
        params.put("dateNaissance", "Trente Juin Mille neuf cents quatre vignt dix");
        params.put("numActe", "" + current.getNumActe());
        params.put("correspondant", "7 do alhijja Mille quatres cents");
        params.put("nationnalite", "Marocaine");
        params.put("pere", current.getPrenomP_Fr());
        params.put("mere", current.getPrenomM_Fr());
        params.put("deces", "Néant");
        params.put("anneeH", "1410");
        params.put("anneeG", "1990");
        Wini ini = new Wini(new File("/jars/conf/conf.ini"));
        params.put("province", ini.get("commune", "privince"));
        params.put("commune", ini.get("commune", "commune"));
        params.put("officier", "Signé dessous");
        JasperPrint jasperPrint = JasperFillManager.fillReport("c:\\jars\\extrait-fr.jasper", params, beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        //httpServletResponse.setHeader("Content-Disposition", "attachment; filename=MyAwesomeJasperReportDownload.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
    }

    public String destroy() {
        current = (Acte_Naissance) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        return "List";

    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Acte_NaissanceDeleted"));
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

    @FacesConverter(forClass = Acte_Naissance.class)
    public static class Acte_NaissanceControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            Acte_NaissanceController controller = (Acte_NaissanceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "acte_NaissanceController");
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
            if (object instanceof Acte_Naissance) {
                Acte_Naissance o = (Acte_Naissance) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Acte_Naissance.class.getName());
            }
        }
    }
}
