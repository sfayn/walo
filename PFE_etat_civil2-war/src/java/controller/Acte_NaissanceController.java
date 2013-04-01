package controller;

import bean.Acte_Naissance;
import bean.Donnees_Marginales;
import bean.User;
import controller.util.Helper;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import controller.util.UtilitaireSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRProperties;
import org.apache.batik.util.io.UTF8Decoder;
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
    private Date g_to_h;
    private Date g_to_hTah;
    private Date g_to_hP;
    private Date g_to_hM;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    public String annee;
    public int numReg;
    @EJB
    private session.Acte_NaissanceFacade ejbFacade;
    @EJB
    private session.Donnees_MarginalesFacade ejbFacade2;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    

    public void changeDonnees_Marginales() {
            Donnees_Marginales dm = new Donnees_Marginales();            
            current.getDonnees_Marginaless().add(dm);
    }
    public void changeDonnees_MarginalesRemove(Donnees_Marginales donnee_Marginale){
        for (int i = 0; i < current.getDonnees_Marginaless().size(); i++) {
            if(current.getDonnees_Marginaless().get(i) ==donnee_Marginale){
                current.getDonnees_Marginaless().remove(i);
            }
        }
    }
    public int getNumReg() {
        return numReg;
    }

    public void setNumReg(int numReg) {
        this.numReg = numReg;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public SelectItem[] listannee() {
        List<String> annees = new ArrayList<String>();
        for (int i = 1900; i < 2060; i++) {
            annees.add(i + "");
        }
        return JsfUtil.getSelectItems(annees, true);
    }

    public SelectItem[] listReg() {
        return JsfUtil.getSelectItems(ejbFacade.findByDate(annee), true);
    }

    public void setG_to_hTah(Date g_to_hTah) {
        this.g_to_hTah = g_to_hTah;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
        changeDeclaration();
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setG_to_hM(Date g_to_hM) {
        this.g_to_hM = g_to_hM;
    }

    public void setG_to_hP(Date g_to_hP) {
        this.g_to_hP = g_to_hP;
    }

    public void setG_to_h(Date g_to_h) {
        this.g_to_h = g_to_h;
    }

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

    public Acte_NaissanceController() {
    }

    public void changeDeclaration() {

        if (current.isTypeT()) {
            current.setDeclaration_Fr("Sur la base de ce qui est venu dans le numéro du jugement " + Helper.dateToStrH(current.getDateHo()) + " correspondant au " + Helper.dateToStrG(current.getDateHo()) + " dans le dossier numéro   du Tribunal de première instance à ");
            current.setDeclaration_Ar(" بناء على ما جاء في الحكم عدد   الصادر بتاريخ " + Helper.dateToStrArH(current.getDateHo()) + "الموافق ل " + Helper.dateToStrArG(current.getDateHo()) + "  في الملف عدد     عن المحكمة الإبتدائية ب ");
        } else {
            try {
                current.setDeclaration_Ar(" حسب ما صرح به والده السيد " + current.getPrenomP_Ar() + " تحت عدد " + current.getNumActe() + " جنسيته " + current.getNationalteP_Ar() + " حرفته " + current.getProfessionP_Ar() + " و الساكن ب " + current.getAddressePa_Ar());
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
            }
            current.setDeclaration_Fr("Selon la déclaration du père " + current.getPrenomP_Fr() + " sous numéro " + current.getNumActe() + " sa nationalité est " + current.getNationalteP_Fr() + " sa fonction est " + current.getProfessionP_Fr() + " residant à " + current.getAddressePa_Fr());
        }
    }

    public Acte_Naissance getSelected() {
        recreateModel();
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
            pagination = new PaginationHelper(4000) {
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

    public Date getG_to_h() {
        if (current.getDate_de_naiss_G() == null) {
            return null;
        } else {
            current.setDate_de_naiss_H(Helper.dateTimeGrToH(current.getDate_de_naiss_G()));
            return current.getDate_de_naiss_H();
        }

    }

    public Date getG_to_hP() {
        if (current.getDate_de_naissP_G() == null) {
            return null;
        } else {
            current.setDate_de_naissP_H(Helper.dateGrToH(current.getDate_de_naissP_G()));
            return current.getDate_de_naissP_H();
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

    public Date getG_to_hM() {
        if (current.getDate_de_naissM_G() == null) {
            return null;
        } else {
            current.setDate_de_naissM_H(Helper.dateGrToH(current.getDate_de_naissM_G()));
            return current.getDate_de_naissM_H();
        }

    }

    public void g_to_hTahplus() {
        if (current.getDateTah_G() != null) {
            l++;
            current.getDateTah_G().setDate(current.getDateTah_G().getDate() + l);
        }
    }

    public void g_to_hTahmoins() {
        if (current.getDateTah_G() != null) {
            l--;
            current.getDateTah_G().setDate(current.getDateTah_G().getDate() + l);
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

    public void g_to_hPplus() {
        if (current.getDate_de_naissP_G() != null) {
            j++;
            current.getDate_de_naissP_G().setDate(current.getDate_de_naissP_G().getDate() + j);
        }
    }

    public void g_to_hPmoins() {
        if (current.getDate_de_naissP_G() != null) {
            j--;
            current.getDate_de_naissP_G().setDate(current.getDate_de_naissP_G().getDate() + j);
        }
    }

    public void g_to_hMplus() {
        if (current.getDate_de_naissM_G() != null) {
            k++;
            current.getDate_de_naissM_G().setDate(current.getDate_de_naissM_G().getDate() + k);
        }
    }

    public void g_to_hMmoins() {
        if (current.getDate_de_naissM_G() != null) {
            k--;
            current.getDate_de_naissM_G().setDate(current.getDate_de_naissM_G().getDate() + k);
        }
    }

    public void encode() {
        try {
            current.setNom_Ar(URLEncoder.encode(current.getNom_Ar(), "UTF-8"));
            current.setPrenom_Ar(URLEncoder.encode(current.getPrenom_Ar(), "UTF-8"));
            current.setDeclaration_Ar(URLEncoder.encode(current.getDeclaration_Ar(), "UTF-8"));
            current.setLieu_de_Naiss_Ar(URLEncoder.encode(current.getLieu_de_Naiss_Ar(), "UTF-8"));
            current.setProfession_Ar(URLEncoder.encode(current.getProfession_Ar(), "UTF-8"));
            current.setPrenomP_Ar(URLEncoder.encode(current.getPrenomP_Ar(), "UTF-8"));
            current.setPrenomM_Ar(URLEncoder.encode(current.getPrenomM_Ar(), "UTF-8"));
            current.setProfessionM_Ar(URLEncoder.encode(current.getProfessionM_Ar(), "UTF-8"));
            current.setProfessionP_Ar(URLEncoder.encode(current.getProfessionP_Ar(), "UTF-8"));
            current.setLieu_de_NaissM_Ar(URLEncoder.encode(current.getLieu_de_NaissM_Ar(), "UTF-8"));
            current.setLieu_de_NaissP_Ar(URLEncoder.encode(current.getLieu_de_NaissP_Ar(), "UTF-8"));
            current.setNationalteM_Ar(URLEncoder.encode(current.getNationalteM_Ar(), "UTF-8"));
            current.setNationalteP_Ar(URLEncoder.encode(current.getNationalteP_Ar(), "UTF-8"));
            current.setAddressePa_Ar(URLEncoder.encode(current.getAddressePa_Ar(), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String create() {
        try {
            encode();
            current.setCreatedAt(new Date());
            UtilitaireSession us = UtilitaireSession.getInstance();
            current.setCreatedBy((User) us.get("auth"));
            for(Donnees_Marginales dm : current.getDonnees_Marginaless()) {
                ejbFacade2.create(dm);
            }
            getFacade().create(current);
            for(Donnees_Marginales dm : current.getDonnees_Marginaless()){
            dm.setActe(current);
            dm.setDescAr(URLEncoder.encode(dm.getDescAr(), "UTF-8"));
            ejbFacade2.edit(dm);
            }
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
            encode();
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

    public void changeDecesP() {

        if (current.isDecesP()) {
            current.setLieu_de_NaissP_Ar("متوفي");
            current.setLieu_de_NaissP_Fr("décédé");
            current.setNationalteP_Ar("متوفي");
            current.setNationalteP_Fr("décédé");
            current.setProfessionP_Ar("متوفي");
            current.setProfessionP_Fr("décédé");
        } else {
            current.setLieu_de_NaissP_Ar("");
            current.setLieu_de_NaissP_Fr("");
            current.setNationalteP_Ar("");
            current.setNationalteP_Fr("");
            current.setProfessionP_Ar("");
            current.setProfessionP_Fr("");
        }
    }

    public void changeDecesM() {

        if (current.isDecesM()) {
            current.setLieu_de_NaissM_Ar("متوفية");
            current.setLieu_de_NaissM_Fr("décédée");
            current.setNationalteM_Ar("متوفية");
            current.setNationalteM_Fr("décédée");
            current.setProfessionM_Ar("متوفية");
            current.setProfessionM_Fr("décédée");
        } else {
            current.setLieu_de_NaissM_Ar("");
            current.setLieu_de_NaissM_Fr("");
            current.setNationalteM_Ar("");
            current.setNationalteM_Fr("");
            current.setProfessionM_Ar("");
            current.setProfessionM_Fr("");
        }
    }

    public void PDF() throws JRException, IOException {
        List<Acte_Naissance> acts = new ArrayList<Acte_Naissance>();
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        params.put("numActe", "" + current.getNumActe());
        params.put("anneeH", y.format(current.getDateTah_H()));
        params.put("anneeG", y.format(current.getDateTah_G()));
        //Wini ini = new Wini(new File("C:\\jars\\conf.ini"));
        File f1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")); 
        System.out.println("length "+f1.length());
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini"));
        System.out.println(""+ini.size());
        params.put("province", ini.get("commune", "province"));
        params.put("commune", ini.get("commune", "commune"));
        params.put("communeAr", ini.get("commune", "communeAr"));
        System.out.println("commune: ---> " + ini.get("commune", "communeAr"));
        params.put("provinceAr", ini.get("commune", "provinceAr"));

        params.put("nom", current.getNom_Fr());
        params.put("prenom", current.getPrenom_Fr());
        params.put("lieuNaissance", current.getLieu_de_Naiss_Fr());
        params.put("dateNaissance", Helper.dateToStrG(current.getDate_de_naiss_G()));
        params.put("nationnalite", "Marocaine");
        params.put("pere", current.getPrenomP_Fr());
        params.put("mere", current.getPrenomM_Fr());
        params.put("deces", "Néant");

        params.put("nomAr", current.getNom_Ar());
        params.put("prenomAr", current.getPrenom_Ar());
        params.put("lieuNaissanceAr", current.getLieu_de_Naiss_Ar());
        params.put("dateNaissanceAr", Helper.dateToStrArG(current.getDate_de_naiss_G()));
        params.put("nationnaliteAr", "مغربية");
        params.put("pereAr", current.getPrenomP_Ar());
        params.put("mereAr", current.getPrenomM_Ar());
        params.put("decesAr", "لا شيء");

        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arial.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/ariali.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbi.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbd.ttf"));
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/extrait.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
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
