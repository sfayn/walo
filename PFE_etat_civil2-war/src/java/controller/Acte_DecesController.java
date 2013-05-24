package controller;

import bean.Acte_Deces;
import bean.Acte_Naissance;
import bean.Donnees_Marginales_A_D;
import bean.Registre;
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
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRProperties;
import org.ini4j.Wini;
import org.richfaces.model.Filter;
import session.Acte_DecesFacade;

@ManagedBean(name = "acte_DecesController")
@SessionScoped
public class Acte_DecesController implements Serializable {

    private Acte_Deces current;
    private DataModel items = null;
    private boolean render = false;
    @EJB
    private session.Acte_DecesFacade ejbFacade;
    @EJB
    private session.Donnees_Marginales_A_DFacade ejbFacade2;
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
    private int i = 0;
    private int k = 0;
    private Long numActeFilter;
    private String anneeFilter;
    private Integer primaryRowCount = 10;

    public int getK() {
        return k;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
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
        return new Filter<Acte_Deces>() {
            public boolean accept(Acte_Deces item) {
                Long numActe = getNumActeFilter();
                if (numActe == null || numActe == 0 || numActe.compareTo(item.getNumActe().longValue()) >= 0) {
                    return true;
                }
                return false;
            }
        };
    }

    public Filter<?> getAnneeFilterImpl() {
        return new Filter<Acte_Deces>() {
            public boolean accept(Acte_Deces item) {
                String Annee = getAnneeFilter();
                if (Annee == null || Annee.length() == 0 || Annee.equals(item.getRegistre().getAnnee())) {
                    return true;
                }
                return false;
            }
        };
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
        getG_to_h();
    }

    public Date getG_to_hD() {
        if (current.getDateDecesG() == null) {
            return null;
        } else {
            current.setDateDecesH(Helper.dateTimeGrToH(current.getDateDecesG()));
            return current.getDateDecesH();
        }

    }

    public void GToHAnneeD() {
        if (current.getDateDecesG() != null) {
            current.setDateDecesH(Helper.dateGrToH(current.getDateDecesG()));
        }
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

    public void g_to_hDplus() {
        if (current.getDateDecesG() != null) {
            i++;
            current.getDateDecesG().setDate(current.getDateDecesG().getDate() + i);
        }
    }

    public void g_to_hDmoins() {
        if (current.getDateDecesG() != null) {
            i--;
            current.getDateDecesG().setDate(current.getDateDecesG().getDate() + i);
        }
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

    public void PDFextrD() throws JRException, IOException {
        List<Acte_Deces> acts = new ArrayList<Acte_Deces>();
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arial.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/ariali.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbi.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbd.ttf"));
        System.out.println("config " + ini.get("config", "format"));

        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        params.put("numActe", "" + current.getNumActe());
        params.put("anneeH", "" + y.format(current.getDateTah_H()));
        params.put("anneeG", "" + y.format(current.getDateTah_G()));

        params.put("communeAr", ini.get("commune", "communeAr"));
        params.put("provinceAr", ini.get("commune", "provinceAr"));

        params.put("nomAr", current.getNom_Ar());
        params.put("prenomAr", current.getPrenom_Ar());        
        params.put("dateDecesAr", Helper.dateToStrArG(current.getDateDecesG()));        
        params.put("lieuDecesAr", current.getLieuDeces_Ar());
        params.put("lieuNaissanceAr", current.getLieu_de_Naiss_Ar());
        params.put("lieuDecesAr", current.getLieuDeces_Ar());
        params.put("dateNaissanceGAr", current.isNoMJ() == false ? Helper.dateToStrArG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateNaissanceHAr", current.isNoMJ() == false ? Helper.dateHToStrArH(current.getDate_de_naiss_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
        params.put("dateDecesGAr", current.isNoMJD() == false ? Helper.dateToStrArG(current.getDateDecesG()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateDecesHAr", current.isNoMJD() == false ? Helper.dateHToStrArH(current.getDateDecesH()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
        params.put("nationnaliteAr", "مغربية");
        params.put("pereAr", current.getPrenomP_Ar());
        params.put("mereAr", current.getPrenomM_Ar());
        params.put("adresseAr", current.getAdresse_Ar());
        params.put("professionAr", current.getProfession_Ar());

        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/extraitDecesAr.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);

        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        //httpServletResponse.setHeader("Content-Disposition", "attachment; filename=MyAwesomeJasperReportDownload.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

    }
public void PDFIntegD() throws JRException, IOException {
        List<Acte_Deces> acts = new ArrayList<Acte_Deces>();
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arial.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/ariali.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbi.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbd.ttf"));
        System.out.println("config " + ini.get("config", "format"));

        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        params.put("numActe", "" + current.getNumActe());
        params.put("anneeH", "" + y.format(current.getDateTah_H()));
        params.put("anneeG", "" + y.format(current.getDateTah_G()));
        params.put("communeAr", ini.get("commune", "communeAr"));
        params.put("provinceAr", ini.get("commune", "provinceAr"));

        params.put("nomAr", current.getNom_Ar());
        params.put("prenomAr", current.getPrenom_Ar());        
        params.put("dateDecesAr", Helper.dateToStrArG(current.getDateDecesG()));        
        params.put("lieuDecesAr", current.getLieuDeces_Ar());
        params.put("lieuNaissanceAr", current.getLieu_de_Naiss_Ar());
        params.put("lieuDecesAr", current.getLieuDeces_Ar());
        params.put("dateNaissanceGAr", current.isNoMJ() == false ? Helper.dateToStrArG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateNaissanceHAr", current.isNoMJ() == false ? Helper.dateHToStrArH(current.getDate_de_naiss_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
        params.put("dateDecesGAr", current.isNoMJD() == false ? Helper.dateToStrArG(current.getDateDecesG()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateDecesHAr", current.isNoMJD() == false ? Helper.dateHToStrArH(current.getDateDecesH()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
        params.put("nationnaliteAr", "مغربية");
        params.put("pereAr", current.getPrenomP_Ar());
        params.put("mereAr", current.getPrenomM_Ar());
        params.put("adresseAr", current.getAdresse_Ar());
        params.put("professionAr", current.getProfession_Ar());
        params.put("dateTahH", Helper.dateHToStrArH(current.getDateTah_H()));
        params.put("dateTahG", Helper.dateToStrArG(current.getDateTah_G()));

        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/integralDecesAr.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);

        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        //httpServletResponse.setHeader("Content-Disposition", "attachment; filename=MyAwesomeJasperReportDownload.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

    }
    public void changeDonnees_Marginales() {
        Donnees_Marginales_A_D dm = new Donnees_Marginales_A_D();
        current.getDonnees_Marginaless().add(dm);
    }

    public void changeDescDM(Donnees_Marginales_A_D dm) throws UnsupportedEncodingException {
        for (int i = 0; i < current.getDonnees_Marginaless().size(); i++) {
            if (current.getDonnees_Marginaless().get(i) == dm) {
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 1) {
                    current.getDonnees_Marginaless().get(i).setDescAr("تزوج " + current.getNom_Ar() + " " + current.getPrenom_Ar() + " ب    بمقتضى الرسم رقم     بثاريخ                   المخاطب عليه من طرف قاظي المحكمة الإبتدائية ل         حرر بتاريخ   لدينا نحن ضابط الحالة المدنية ");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 2) {
                    current.getDonnees_Marginaless().get(i).setDescAr(current.getNom_Ar() + " " + current.getPrenom_Ar() + "متزوج ب  بناء على شهادة الثبوت الصادرة تحت   المخاطب عليها من طرف قاضي المحكمة الإبتدائية ل   حرر بتاريخ   لدينا نحن ضابط الحالة المدني  ");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 3) {
                    current.getDonnees_Marginaless().get(i).setDescAr("حصل تقارر بالزوجية بين " + current.getNom_Ar() + " " + current.getPrenom_Ar() + " ب   بناء على شهادة التقارر الصادرة تحت  المخاطب عليها من طرف قاضي المحكمة الإبتدائية ل   حرر بتاريخ   لدينا نحن ضابط الحالة المدنية ");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 4) {
                    current.getDonnees_Marginaless().get(i).setDescAr("طلاق " + current.getNom_Ar() + " " + current.getPrenom_Ar() + " من   بناء على الرسم عدد   بتاريخ    المخاطب عليه من طرف قاضي المحكمة الإبتدائية ل   حرر بتاريخ   لدينا نحن ضابط الحالة المدنية  ");

                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 5) {
                    current.getDonnees_Marginaless().get(i).setDescAr("راجع " + current.getNom_Ar() + " " + current.getPrenom_Ar() + " زوجته   بمقتضى الرسم عدد   بتاريخ    المخاطب عليه من طرف قاضي المحكمة الإبتدائية ل   حرر بتاريخ   لدينا نحن ضابط الحالة المدنية  ");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 6) {
                    current.getDonnees_Marginaless().get(i).setDescAr("قام " + current.getNom_Ar() + " " + current.getPrenom_Ar() + " بمراجعة   بمقتضى الرسم عدد   بتاريخ    المخاطب عليه من طرف قاضي المحكمة الإبتدائية ل   حرر بتاريخ   لدينا نحن ضابط الحالة المدنية  ");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 7) {
                    current.getDonnees_Marginaless().get(i).setDescAr("تعوض كلمة         في السطر         ب               لدينا نحن ضابط الحالة المدنية ");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 8) {
                    current.getDonnees_Marginaless().get(i).setDescAr("أصلح      ليصبح     عوض       بمقتضى الحكم عدد       الصادر عن المحكمة الإبتدائية ب        بتاريخ       الموفق ل        و المنقول في سجل الأحكام لسنة         تحت عدد       و حرر بتاريخ      الموفق ل         لدينا نحن ضابط الحالة المدنية ");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 9) {
                    current.getDonnees_Marginaless().get(i).setDescAr(" أذن في تغيير الإسم العائلي لصاحب الرسم ليصبح       عوض " + current.getNom_Ar() + " بموجب المرسوم عدد       الصادر عن الوزير الأول بتاريخ        الموافق لـ  وحرر بتاريخ       الموافق لـ ");
                }

                if (current.getDonnees_Marginaless().get(i).getType().getId() == 10) {
                    current.getDonnees_Marginaless().get(i).setDescAr(" أذن في تغيير الإسم الشخصي الأجنبي  لصاحب الرسم ليصبح       عوض " + current.getPrenom_Ar() + " بموجب المرسوم عدد       الصادر عن الوزير الأول بتاريخ        الموافق لـ  وحرر بتاريخ       الموافق لـ ");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 11) {
                    current.getDonnees_Marginaless().get(i).setDescAr("أدخل الإسم العائلي أو الشخصي        بالأحرف اللاتينية في صلب الرسم، بناء على القرار عدد       الصادر عن وزير الداخلية أو عن عامل عمالة أو إقليم  بتاريخ        الموافق لـ  وحرر بتاريخ       الموافق ل");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 12) {
                    current.getDonnees_Marginaless().get(i).setDescAr("أصلح الإسم العائلي أو الشخصي بالأحرف اللاتينية و ذلك بجعله من  ، بناء على القرار عدد       الصادر عن وزير الداخلية أو عن عامل عمالة أو إقليم  بتاريخ        الموافق لـ  وحرر بتاريخ       الموافق ل");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 13) {
                    current.getDonnees_Marginaless().get(i).setDescAr("اكتسب صاحب الرسم الجنسية المغربية بموجب (وثيقة التجنيس ظهير أو مرسوم أو مقرر لوزير العدل  )      عدد       الصادر عن   بتاريخ        الموافق لـ    وحرر بتاريخ");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 14) {
                    current.getDonnees_Marginaless().get(i).setDescAr("توفي " + current.getNom_Ar() + " " + current.getPrenom_Ar() + "بتاريخ       بجماعة أو مدينة        وقد سجلت وفاته تحت عدد      سنة     وحرر بتاريخ      موافق    لدينا نحن ضابط الحالة المدنية ");
                }
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 15) {
                    current.getDonnees_Marginaless().get(i).setDescAr("توفي " + current.getNom_Ar() + " " + current.getPrenom_Ar() + "سجلت وفاته تحت عدد   سنة    وضعنا هدا البيان بناء على الإعلام  الوارد علينا بتاريخ     موافق    من ضابط الحالة المدنية ل    وحرر بتاريح ");
                }
            }
        }
    }

    public void changeDonnees_MarginalesRemove(Donnees_Marginales_A_D donnee_Marginale) {
        for (int i = 0; i < current.getDonnees_Marginaless().size(); i++) {
            if (current.getDonnees_Marginaless().get(i) == donnee_Marginale) {
                current.getDonnees_Marginaless().remove(i);
            }
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
        if (annee_d == null && current.getRegistre() != null) {
            annee_d = current.getRegistre().getAnnee();
        }
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

    public void GToHAnnee() {
        if (current.getDate_de_naiss_G() != null) {
            current.setDate_de_naiss_H(Helper.dateGrToH(current.getDate_de_naiss_G()));
        }
    }

    public void getG_to_h() {
        if (current.getDate_de_naiss_G() != null) {
            current.setDate_de_naiss_H(Helper.dateGrToH(current.getDate_de_naiss_G()));
        }

    }

    public void setK(int k) {
        this.k = k;
        getG_to_h();
    }

    public void g_to_hplus() {
        if (current.getDate_de_naiss_G() != null) {
            k++;
            Date tmp = new Date(current.getDate_de_naiss_G().getYear(), current.getDate_de_naiss_G().getMonth(), current.getDate_de_naiss_G().getDate());
            tmp.setDate(tmp.getDate() + k);
            current.setDate_de_naiss_H(Helper.dateGrToH(tmp));
        }
    }

    public void g_to_hmoins() {
        if (current.getDate_de_naiss_G() != null) {
            k--;
            Date tmp = new Date(current.getDate_de_naiss_G().getYear(), current.getDate_de_naiss_G().getMonth(), current.getDate_de_naiss_G().getDate());
            tmp.setDate(tmp.getDate() + k);
            current.setDate_de_naiss_H(Helper.dateGrToH(tmp));

        }
    }

    public void load() {
        if (registre != null && numActe != 0) {
            if (!ejbFacade.findActe_Naiss(numActe, registre).isEmpty()) {
                try {
                    Acte_Naissance acteN = ejbFacade.findActe_Naiss(numActe, registre).get(0);
                    current.setPrenom_Fr(acteN.getPrenom_Fr());
                    current.setPrenom_Ar(acteN.getPrenom_Ar());
                    current.setNom_Fr(acteN.getNom_Fr());
                    current.setNom_Ar(acteN.getNom_Ar());
                    current.setLieu_de_Naiss_Ar(acteN.getLieu_de_Naiss_Ar());
                    current.setLieu_de_Naiss_Fr(acteN.getLieu_de_Naiss_Fr());
                    current.setSex(acteN.getSex());
                    current.setDate_de_naiss_G(acteN.getDate_de_naiss_G());
                    current.setDate_de_naiss_H(acteN.getDate_de_naiss_H());
                    current.setPrenomP_Fr(acteN.getPrenomP_Fr());
                    current.setPrenomP_Ar(acteN.getPrenomP_Ar());
                    current.setPrenomP_Fr(acteN.getPrenomM_Fr());
                    current.setPrenomP_Ar(acteN.getPrenomM_Ar());

                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Acte_DecesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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
            current.setNom_Ar(URLEncoder.encode(current.getNom_Ar(), "UTF-8"));
            current.setPrenom_Ar(URLEncoder.encode(current.getPrenom_Ar(), "UTF-8"));
            current.setDeclaration_Ar(URLEncoder.encode(current.getDeclaration_Ar(), "UTF-8"));
            current.setLieu_de_Naiss_Ar(URLEncoder.encode(current.getLieu_de_Naiss_Ar(), "UTF-8"));
            current.setPrenomP_Ar(URLEncoder.encode(current.getPrenomP_Ar(), "UTF-8"));
            current.setPrenomM_Ar(URLEncoder.encode(current.getPrenomM_Ar(), "UTF-8"));
            current.setProfessionM_Ar(URLEncoder.encode(current.getProfessionM_Ar(), "UTF-8"));
            current.setProfessionP_Ar(URLEncoder.encode(current.getProfessionP_Ar(), "UTF-8"));            
            current.setOfficierAr(URLEncoder.encode(current.getOfficierAr(), "UTF-8"));
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
            numActe = 0;
            registre = null;
            current.setCreatedAt(new Date());
            UtilitaireSession us = UtilitaireSession.getInstance();
            current.setCreatedBy((User) us.get("auth"));
            for (Donnees_Marginales_A_D dm : current.getDonnees_Marginaless()) {
                ejbFacade2.create(dm);
            }
            getFacade().create(current);
            for (Donnees_Marginales_A_D dm : current.getDonnees_Marginaless()) {
                dm.setActe(current);
                dm.setDescAr(URLEncoder.encode(dm.getDescAr(), "UTF-8"));
                ejbFacade2.edit(dm);
            }
            JsfUtil.addSuccessMessage("تم التسجيل بنجاح");
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("المرجو تصحيح المعلومات");
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
            encode();
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
