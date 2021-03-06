package controller;

import bean.Acte_Deces;
import bean.Acte_Naissance;
import bean.Donnees_Marginales_J_D;
import bean.Jugement_Deces;
import bean.Registre_Deces;
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
import session.Jugement_DecesFacade;

@ManagedBean(name = "jugement_DecesController")
@SessionScoped
public class Jugement_DecesController implements Serializable {

    private Jugement_Deces current;
    private DataModel items = null;
    @EJB
    private session.Jugement_DecesFacade ejbFacade;
    @EJB
    private session.Donnees_Marginales_J_DFacade ejbFacade2;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String annee;
    private Registre_Deces registre_d;
    private int numActe;
    private String annee_Jug_Dec;
    private Long numActeFilter;
    private String anneeFilter;
    private Integer primaryRowCount = 10;
    private int i = 0;
    private int j = 0;
    private int l = 0;

    public int getL() {
        return l;
    }

    public void getDistrG_to_h() {
        if (current.getDateTah_G() != null) {
            current.setDateTah_H(Helper.dateGrToH(current.getDateTah_G()));
        }

    }

    public void setL(int l) {
        this.l = l;
        getDistrG_to_h();
        changeText();
    }

    public void distrg_to_hplus() {
        if (current.getDateTah_G() != null) {
            l++;
            Date tmp = new Date(current.getDateTah_G().getYear(), current.getDateTah_G().getMonth(), current.getDateTah_G().getDate());
            tmp.setDate(tmp.getDate() + l);
            current.setDateTah_H(Helper.dateGrToH(tmp));
            changeText();
        }
    }

    public void distrg_to_hmoins() {
        if (current.getDateTah_G() != null) {
            l--;
            Date tmp = new Date(current.getDateTah_G().getYear(), current.getDateTah_G().getMonth(), current.getDateTah_G().getDate());
            tmp.setDate(tmp.getDate() + l);
            current.setDateTah_H(Helper.dateGrToH(tmp));
            changeText();
        }
    }
    public void changeText(){
        current.setDescrAr("test");
    }

    public Integer getPrimaryRowCount() {
        return primaryRowCount;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setPrimaryRowCount(Integer primaryRowCount) {
        this.primaryRowCount = primaryRowCount;
    }

    public Long getNumActeFilter() {
        return numActeFilter;
    }

    public void changeDescDM(Donnees_Marginales_J_D dm) throws UnsupportedEncodingException {
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
        return new Filter<Jugement_Deces>() {
            public boolean accept(Jugement_Deces item) {
                Long numActe = getNumActeFilter();
                if (numActe == null || numActe == 0 || numActe.compareTo(item.getNumActe().longValue()) >= 0) {
                    return true;
                }
                return false;
            }
        };
    }

    public Filter<?> getAnneeFilterImpl() {
        return new Filter<Jugement_Deces>() {
            public boolean accept(Jugement_Deces item) {
                String Annee = getAnneeFilter();
                if (Annee == null || Annee.length() == 0 || Annee.equals(item.getRegistre().getAnnee())) {
                    return true;
                }
                return false;
            }
        };
    }

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

    public SelectItem[] listannee() {
        List<String> annees = new ArrayList<String>();
        for (int i = 1900; i < 2060; i++) {
            annees.add(i + "");
        }
        return JsfUtil.getSelectItems(annees, true);
    }

    public Jugement_DecesController() {
    }

    public void GToHAnnee() {
        if (current.getDate_de_naiss_G() != null) {
            current.setDate_de_naiss_H(Helper.dateGrToH(current.getDate_de_naiss_G()));
        }
    }

    public void setI(int i) {
        this.i = i;
        getG_to_h();
    }

    public void setJ(int j) {
        this.j = j;
        getG_to_hD();
    }

    public void changeDonnees_MarginalesRemove(Donnees_Marginales_J_D donnee_Marginale) {
        for (int i = 0; i < current.getDonnees_Marginaless().size(); i++) {
            if (current.getDonnees_Marginaless().get(i) == donnee_Marginale) {
                current.getDonnees_Marginaless().remove(i);
            }
        }
    }

    public void changeDonnees_Marginales() {
        Donnees_Marginales_J_D dm = new Donnees_Marginales_J_D();
        current.getDonnees_Marginaless().add(dm);
    }

    public void getG_to_h() {
        if (current.getDate_de_naiss_G() != null) {
            current.setDate_de_naiss_H(Helper.dateGrToH(current.getDate_de_naiss_G()));
        }

    }

    public void getG_to_hD() {
        if (current.getDateDecesG() != null) {
            current.setDateDecesH(Helper.dateGrToH(current.getDateDecesG()));
        }

    }

    public void g_to_hDplus() {
        if (current.getDateDecesG() != null) {
            j++;
            Date tmp = new Date(current.getDateDecesG().getYear(), current.getDateDecesG().getMonth(), current.getDateDecesG().getDate());
            tmp.setDate(tmp.getDate() + j);
            current.setDateDecesH(Helper.dateGrToH(tmp));
        }
    }

    public void g_to_hDmoins() {
        if (current.getDateDecesG() != null) {
            j--;
            Date tmp = new Date(current.getDateDecesG().getYear(), current.getDateDecesG().getMonth(), current.getDateDecesG().getDate());
            tmp.setDate(tmp.getDate() + j);
            current.setDateDecesH(Helper.dateGrToH(tmp));

        }
    }

    public void g_to_hplus() {
        if (current.getDate_de_naiss_G() != null) {
            i++;
            Date tmp = new Date(current.getDate_de_naiss_G().getYear(), current.getDate_de_naiss_G().getMonth(), current.getDate_de_naiss_G().getDate());
            tmp.setDate(tmp.getDate() + i);
            current.setDate_de_naiss_H(Helper.dateGrToH(tmp));
        }
    }

    public void g_to_hmoins() {
        if (current.getDate_de_naiss_G() != null) {
            i--;
            Date tmp = new Date(current.getDate_de_naiss_G().getYear(), current.getDate_de_naiss_G().getMonth(), current.getDate_de_naiss_G().getDate());
            tmp.setDate(tmp.getDate() + i);
            current.setDate_de_naiss_H(Helper.dateGrToH(tmp));

        }
    }

    public void GToHAnneeD() {
        if (current.getDateDecesG() != null) {
            current.setDateDecesH(Helper.dateGrToH(current.getDateDecesG()));
        }
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
    public void PDFextrD() throws JRException, IOException {
        List<Jugement_Deces> acts = new ArrayList<Jugement_Deces>();
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        JRBeanCollectionDataSource beanCollectionDataSource2 = new JRBeanCollectionDataSource(acts);
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
        params.put("officierAr", current.getOfficierAr());
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/extraitDecesAr.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);

        
        
        params = new HashMap();
        params.put("numActe", "" + current.getNumActe());
        params.put("anneeH", "" + y.format(current.getDateTah_H()));
        params.put("anneeG", "" + y.format(current.getDateTah_G()));

        params.put("communeFr", ini.get("commune", "commune"));
        params.put("provinceFr", ini.get("commune", "province"));

        params.put("nomFr", current.getNom_Fr());
        params.put("prenomFr", current.getPrenom_Fr());
        params.put("lieuDecesFr", current.getLieuDeces_Fr());
        params.put("lieuNaissanceFr", current.getLieu_de_Naiss_Fr());
        params.put("lieuDecesFr", current.getLieuDeces_Fr());
        params.put("dateNaissanceGFr", current.isNoMJ() == false ? Helper.dateToStrG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2str(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateNaissanceHFr", current.isNoMJ() == false ? Helper.dateHToStrH(current.getDate_de_naiss_H()) : "سنة " + Helper.int2str(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
        params.put("dateDecesGFr", current.isNoMJD() == false ? Helper.dateToStrG(current.getDateDecesG()) :  Helper.int2str(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateDecesHFr", current.isNoMJD() == false ? Helper.dateHToStrH(current.getDateDecesH()) : Helper.int2str(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
        params.put("nationnaliteFr", "Marocaine");
        params.put("pereFr", current.getPrenomP_Fr());
        params.put("mereFr", current.getPrenomM_Fr());
        params.put("adresseFr", current.getAdresse_Fr());
        params.put("professionFr", current.getProfession_Fr());
        params.put("officierFr", current.getOfficierFr());
        JasperPrint jasperPrint2;
        InputStream reportSource2 = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/extraitDecesFr.jasper");
        jasperPrint2 = JasperFillManager.fillReport(reportSource2, params, beanCollectionDataSource2);
        List pages = jasperPrint2.getPages();
        for (Iterator i = pages.iterator(); i.hasNext();) {
            System.out.println("+page");
            JRPrintPage jRPrintPage = (JRPrintPage) i.next();
            jasperPrint.addPage(jRPrintPage);
        }
        
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        //httpServletResponse.setHeader("Content-Disposition", "attachment; filename=MyAwesomeJasperReportDownload.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

    }

    public void pdfIntegD() throws JRException, IOException {
        List<Jugement_Deces> acts = new ArrayList<Jugement_Deces>();
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
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/integrale_J_D.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
        java.util.List pages = jasperPrint.getPages();
        for (Iterator<java.util.List> i = pages.iterator(); i.hasNext();) {
            JRPrintPage page = (JRPrintPage) i.next();
            if (page.getElements().size() == 0) {
                i.remove();
            }
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        //httpServletResponse.setHeader("Content-Disposition", "attachment; filename=MyAwesomeJasperReportDownload.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
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
            numActe = 0;
            registre_d = null;
            current.setCreatedAt(new Date());
            UtilitaireSession us = UtilitaireSession.getInstance();
            current.setCreatedBy((User) us.get("auth"));
            for (Donnees_Marginales_J_D dm : current.getDonnees_Marginaless()) {
                ejbFacade2.create(dm);
            }
            getFacade().create(current);
            for (Donnees_Marginales_J_D dm : current.getDonnees_Marginaless()) {
                dm.setJugement(current);
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
        current = (Jugement_Deces) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            encode();
            for (Donnees_Marginales_J_D dm : current.getDonnees_Marginaless()) {
                ejbFacade2.create(dm);
            }
            getFacade().edit(current);
            for (Donnees_Marginales_J_D dm : current.getDonnees_Marginaless()) {
                dm.setJugement(current);
                dm.setDescAr(URLEncoder.encode(dm.getDescAr(), "UTF-8"));
                ejbFacade2.edit(dm);
            }
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
