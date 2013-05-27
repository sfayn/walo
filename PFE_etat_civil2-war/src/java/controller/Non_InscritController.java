package controller;

import bean.Acte_Naissance;
import bean.Non_Inscrit;
import controller.util.Helper;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import session.Non_InscritFacade;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRProperties;
import org.ini4j.Wini;
import org.richfaces.model.Filter;

@ManagedBean(name = "non_InscritController")
@SessionScoped
public class Non_InscritController implements Serializable {

    private Non_Inscrit current;
    private DataModel items = null;
    @EJB
    private session.Non_InscritFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Long numActeFilter;
    private Long numActeNaissanceFilter;
    private String anneeFilter;
    private Integer primaryRowCount = 10;
    private int i = 0;
    private String professionAr;
    private String etatFamilleAr;
    private String adresseAr;

    public String getProfessionAr() {
        return professionAr;
    }

    public void setProfessionAr(String professionAr) {
        this.professionAr = professionAr;
    }

    public String getEtatFamilleAr() {
        return etatFamilleAr;
    }

    public void setEtatFamilleAr(String etatFamilleAr) {
        this.etatFamilleAr = etatFamilleAr;
    }

    public String getAdresseAr() {
        return adresseAr;
    }

    public void setAdresseAr(String adresseAr) {
        this.adresseAr = adresseAr;
    } 
    
    

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void GToHAnnee() {
        if (current.getDate_de_naiss_G() != null) {
            current.setDate_de_naiss_H(Helper.dateGrToH(current.getDate_de_naiss_G()));
        }
    }

    public Date getG_to_h() {
        if (current.getDate_de_naiss_G() == null) {
            return null;
        } else {
            current.setDate_de_naiss_H(Helper.dateTimeGrToH(current.getDate_de_naiss_G()));
            return current.getDate_de_naiss_H();
        }

    }

    public void g_to_hplus() {
        if (current.getDate_de_naiss_G() != null) {
            i++;
            current.getDate_de_naiss_G().setDate(current.getDate_de_naiss_G().getDate() + i);
        }
    }

    public void g_to_hmoins() {
        if (current.getDate_de_naiss_G() != null) {
            i--;
            current.getDate_de_naiss_G().setDate(current.getDate_de_naiss_G().getDate() + i);
        }
    }

    public Integer getPrimaryRowCount() {
        return primaryRowCount;
    }

    public void setPrimaryRowCount(Integer primaryRowCount) {
        this.primaryRowCount = primaryRowCount;
    }

    public Long getNumActeNaissanceFilter() {
        return numActeNaissanceFilter;
    }

    public void setNumActeNaissanceFilter(Long numActeNaissanceFilter) {
        this.numActeNaissanceFilter = numActeNaissanceFilter;
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
        return new Filter<Non_Inscrit>() {
            public boolean accept(Non_Inscrit item) {
                Long numActe = getNumActeFilter();
                if (numActe == null || numActe == 0 || numActe.compareTo(item.getNumCertificat().longValue()) >= 0) {
                    return true;
                }
                return false;
            }
        };
    }
    
    
    
    public void pdfVie() throws JRException, IOException {
        List<Non_Inscrit> acts = new ArrayList<Non_Inscrit>();
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        params.put("annee", "" + current.getAnnee());
        File f1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini"));
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        params.put("province", ini.get("commune", "province"));
        params.put("commune", ini.get("commune", "commune"));
        params.put("communeAr", ini.get("commune", "communeAr"));
        params.put("provinceAr", ini.get("commune", "provinceAr"));
        params.put("dateNaissanceGAr", current.isNoMJ() == false ? Helper.dateToStrArG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateNaissanceHAr", current.isNoMJ() == false ? Helper.dateHToStrArH(current.getDate_de_naiss_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
        
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arial.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/ariali.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbi.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbd.ttf"));
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/certificat_Vie.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        //httpServletResponse.setHeader("Content-Disposition", "attachment; filename=MyAwesomeJasperReportDownload.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

    }

    public void pdffiancaille() throws JRException, IOException {
        List<Non_Inscrit> acts = new ArrayList<Non_Inscrit>();
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        params.put("anneeH", "");
        params.put("anneeG", "");
        //Wini ini = new Wini(new File("C:\\jars\\conf.ini"));
        File f1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini"));
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        params.put("province", ini.get("commune", "province"));
        params.put("commune", ini.get("commune", "commune"));
        params.put("communeAr", ini.get("commune", "communeAr"));
        params.put("provinceAr", ini.get("commune", "provinceAr"));
        params.put("annee", current.getAnnee()+"");
        params.put("dateNaissanceGAr", current.isNoMJ() == false ? Helper.dateToStrArG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateNaissanceHAr", current.isNoMJ() == false ? Helper.dateHToStrArH(current.getDate_de_naiss_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
        System.out.println(params.get("dateNaissanceGAr"));
        params.put("professionAr", professionAr);
        params.put("etatFamilleAr", etatFamilleAr);
        params.put("adresseAr", adresseAr);
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arial.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/ariali.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbi.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbd.ttf"));
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/fiancaille.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        //httpServletResponse.setHeader("Content-Disposition", "attachment; filename=MyAwesomeJasperReportDownload.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

    }
    
    
    
    
    
    
    
    

    public Filter<?> getNumActeNaissanceFilterImpl() {
        return new Filter<Non_Inscrit>() {
            public boolean accept(Non_Inscrit item) {
                Long numActeNaiss = getNumActeNaissanceFilter();
                if (numActeNaiss == null || numActeNaiss == 0 || numActeNaiss.compareTo(item.getNumActe().longValue()) >= 0) {
                    return true;
                }
                return false;
            }
        };
    }

    public Filter<?> getAnneeFilterImpl() {
        return new Filter<Non_Inscrit>() {
            public boolean accept(Non_Inscrit item) {
                String Annee = getAnneeFilter();
                if (Annee == null || Annee.length() == 0 || Annee.equals(item.getAnnee())) {
                    return true;
                }
                return false;
            }
        };
    }

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
    public String prepareCreate2() {        
        return "View";
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
            JsfUtil.addSuccessMessage("تم التسجيل بنجاح");
            return prepareView();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("المرجو تصحيح المعلومات");
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
