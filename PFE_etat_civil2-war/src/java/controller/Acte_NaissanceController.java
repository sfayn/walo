package controller;

import bean.Acte_Naissance;
import bean.Attr;
import bean.Donnees_Marginales;
import bean.Type_Donnees_Marginales;
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
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
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
import net.sf.jasperreports.governors.MaxPagesGovernor;
import org.ini4j.Wini;
import org.richfaces.model.Filter;
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
    private Long numActeFilter;
    private Long anneeFilter;
    private Integer primaryRowCount = 10;
    private String etatFamilleAr;
    private String adresseAr;
    private String professionAr;
    private boolean test = false;
    Map<Attr, Value> attrs;
    Map<Donnees_Marginales, Map> m = new HashMap<Donnees_Marginales, Map>();

    public Map<Donnees_Marginales, Map> getM() {
        return m;
    }

    public void setM(Map<Donnees_Marginales, Map> m) {
        this.m = m;
    }

    public String nomAtrr(String attr) {
        return ResourceBundle.getBundle("attrs").getString(attr);
    }

    public Map<Attr, Value> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<Attr, Value> attrs) {
        this.attrs = attrs;
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

    public String getProfessionAr() {
        return professionAr;
    }

    public void setProfessionAr(String professionAr) {
        this.professionAr = professionAr;
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

    public Long getAnneeFilter() {
        return anneeFilter;
    }

    public void setAnneeFilter(Long anneeFilter) {
        this.anneeFilter = anneeFilter;
    }

    public void numActeFilterImpl() {
        if ((numActeFilter != null && numActeFilter > 0) || (anneeFilter != null && anneeFilter > 0)) {
            test = true;
        } else {
            test = false;
        }
        System.out.println(test);
        this.destroyModel();
    }

    public Acte_NaissanceController() {
    }

    public void changeDonnees_Marginales() {
        Donnees_Marginales dm = new Donnees_Marginales();
        current.getDonnees_Marginaless().add(dm);


    }

    public void chargerAttr(Donnees_Marginales dm) throws UnsupportedEncodingException {
        attrs = new HashMap<Attr, Value>();
        dm.setDescAr(dm.getType().getDescrAr());
        dm.setDescFr(dm.getType().getDescrFr());
        if (!dm.getType().getAttrs().isEmpty()) {
            String[] arr = dm.getType().getAttrs().split(":");
            for (int i = 0; i < arr.length; i++) {
                Attr attr = new Attr(arr[i]);
                attrs.put(attr, new Value(""));
            }

            m.put(dm, attrs);

        }
    }

    public void changeDescDM(Donnees_Marginales dm) throws UnsupportedEncodingException {
        chargerAttr(dm);
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

    public void GToHAnnee() {
        if (current.getDate_de_naiss_G() != null) {
            System.out.println("DateGr: "+current.getDate_de_naiss_G());
            current.setDate_de_naiss_H(Helper.dateGrToH(current.getDate_de_naiss_G()));
        }
    }

    public void GToHAnneeP() {
        if (current.getDate_de_naissP_G() != null) {
            current.setDate_de_naissP_H(Helper.dateGrToH(current.getDate_de_naissP_G()));
            
        }
    }

    public void GToHAnneeM() {
        if (current.getDate_de_naissM_G() != null) {
            current.setDate_de_naissM_H(Helper.dateGrToH(current.getDate_de_naissM_G()));
        }
    }

    public void changeDonnees_MarginalesRemove(Donnees_Marginales donnee_Marginale) {
        for (int i = 0; i < current.getDonnees_Marginaless().size(); i++) {
            if (current.getDonnees_Marginaless().get(i) == donnee_Marginale) {
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
        if (annee == null && current.getRegistre() != null) {
            annee = current.getRegistre().getAnnee();
        }
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
        getG_to_hTah();
        changeDeclaration();
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
        getG_to_hM();
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
        getG_to_h();
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
        getG_to_hP();
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

    public void getG_to_h() {
        if (current.getDate_de_naiss_G() != null) {
            current.setDate_de_naiss_H(Helper.dateGrToH(current.getDate_de_naiss_G()));
        }
    }

    public void getG_to_hP() {
        if (current.getDate_de_naissP_G() != null) {
            current.setDate_de_naissP_H(Helper.dateGrToH(current.getDate_de_naissP_G()));
        }

    }

    public void getG_to_hTah() {
        if (current.getDateTah_G() != null) {
            current.setDateTah_H(Helper.dateGrToH(current.getDateTah_G()));
        }
    }

    public void getG_to_hM() {
        if (current.getDate_de_naissM_G() != null) {
            current.setDate_de_naissM_H(Helper.dateGrToH(current.getDate_de_naissM_G()));
        }
    }

    public void g_to_hTahplus() {
        if (current.getDateTah_G() != null) {
            l++;
            Date tmp = new Date(current.getDateTah_G().getYear(), current.getDateTah_G().getMonth(), current.getDateTah_G().getDate());
            tmp.setDate(tmp.getDate() + l);
            current.setDateTah_H(Helper.dateGrToH(tmp));
        }

    }

    public void g_to_hTahmoins() {
        if (current.getDateTah_G() != null) {
            l--;
            Date tmp = new Date(current.getDateTah_G().getYear(), current.getDateTah_G().getMonth(), current.getDateTah_G().getDate());
            tmp.setDate(tmp.getDate() + l);
            current.setDateTah_H(Helper.dateGrToH(tmp));
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

    public void g_to_hPplus() {
        if (current.getDate_de_naissP_G() != null) {
            j++;
            Date tmp = new Date(current.getDate_de_naissP_G().getYear(), current.getDate_de_naissP_G().getMonth(), current.getDate_de_naissP_G().getDate());
            tmp.setDate(tmp.getDate() + j);
            current.setDate_de_naissP_H(Helper.dateGrToH(tmp));
        }
    }

    public void g_to_hPmoins() {
        if (current.getDate_de_naissP_G() != null) {
            j--;
            Date tmp = new Date(current.getDate_de_naissP_G().getYear(), current.getDate_de_naissP_G().getMonth(), current.getDate_de_naissP_G().getDate());
            tmp.setDate(tmp.getDate() + j);
            current.setDate_de_naissP_H(Helper.dateGrToH(tmp));
        }
    }

    public void g_to_hMplus() {
        if (current.getDate_de_naissM_G() != null) {
            k++;
            Date tmp = new Date(current.getDate_de_naissM_G().getYear(), current.getDate_de_naissM_G().getMonth(), current.getDate_de_naissM_G().getDate());
            tmp.setDate(tmp.getDate() + k);
            current.setDate_de_naissM_H(Helper.dateGrToH(tmp));
        }
    }

    public void g_to_hMmoins() {
        if (current.getDate_de_naissM_G() != null) {
            k--;
            Date tmp = new Date(current.getDate_de_naissM_G().getYear(), current.getDate_de_naissM_G().getMonth(), current.getDate_de_naissM_G().getDate());
            tmp.setDate(tmp.getDate() + k);
            current.setDate_de_naissM_H(Helper.dateGrToH(tmp));
        }
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
            current.setLieu_de_NaissM_Ar(URLEncoder.encode(current.getLieu_de_NaissM_Ar(), "UTF-8"));
            current.setLieu_de_NaissP_Ar(URLEncoder.encode(current.getLieu_de_NaissP_Ar(), "UTF-8"));
            current.setNationalteM_Ar(URLEncoder.encode(current.getNationalteM_Ar(), "UTF-8"));
            current.setNationalteP_Ar(URLEncoder.encode(current.getNationalteP_Ar(), "UTF-8"));
            current.setAddressePa_Ar(URLEncoder.encode(current.getAddressePa_Ar(), "UTF-8"));
            current.setOfficierAr(URLEncoder.encode(current.getOfficierAr(), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String prepareEdit() {
        current = (Acte_Naissance) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        m.clear();
        if (!current.getDonnees_Marginaless().isEmpty()) {
            for (Donnees_Marginales dm : current.getDonnees_Marginaless()) {
                try {
                    Map<Attr, Value> map = new HashMap<Attr, Value>();
                    if (!dm.getAttrValues().isEmpty()) {
                        String[] pairs = dm.getAttrValues().split("&");
                        for (int i = 0; i < pairs.length; i++) {
                            String pair = pairs[i];
                            String[] keyValue = pair.split("=");
                            map.put(new Attr(keyValue[0]), new Value(keyValue[1]));
                        }
                        m.put(dm, map);
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return "Edit";
    }

    public String prepareEdit2() {
        m.clear();
        if (!current.getDonnees_Marginaless().isEmpty()) {
            for (Donnees_Marginales dm : current.getDonnees_Marginaless()) {
                try {
                    Map<Attr, Value> map = new HashMap<Attr, Value>();
                    if (!dm.getAttrValues().isEmpty()) {
                        String[] pairs = dm.getAttrValues().split("&");
                        for (int i = 0; i < pairs.length; i++) {
                            String pair = pairs[i];
                            String[] keyValue = pair.split("=");
                            map.put(new Attr(keyValue[0]), new Value(keyValue[1]));
                        }
                        m.put(dm, map);
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return "Edit";
    }

    public String create() {
        try {
            encode();
            current.setCreatedAt(new Date());
            UtilitaireSession us = UtilitaireSession.getInstance();
            current.setCreatedBy((User) us.get("auth"));
            for (Donnees_Marginales dm : current.getDonnees_Marginaless()) {
                if (!dm.getType().getAttrs().equals("")) {
                    dm.setAttrValues("");
                    Map mp = m.get(dm);
                    Set listKeys = mp.keySet();  // Obtenir la liste des clés
                    Iterator iterateur = listKeys.iterator();
                    while (iterateur.hasNext()) {
                        Object key = iterateur.next();
                        if (dm.getAttrValues().equals("")) {
                            dm.setAttrValues(key + "=" + mp.get(key) + "&");
                        } else {
                            dm.setAttrValues(dm.getAttrValues() + key + "=" + mp.get(key) + "&");
                        }
                    }
                }
                ejbFacade2.create(dm);                
            }
            getFacade().create(current);
            System.out.println("Année: "+current.getDate_de_naiss_G());
            for (Donnees_Marginales dm : current.getDonnees_Marginaless()) {
                dm.setActe(current);
                dm.setDescAr(URLEncoder.encode(dm.getDescAr(), "UTF-8"));
                dm.setAttrValues(URLEncoder.encode(dm.getAttrValues(), "UTF-8"));
                ejbFacade2.edit(dm);
            }
            JsfUtil.addSuccessMessage("تم التسجيل بنجاح");
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("المرجو تصحيح المعلومات");
            return null;
        }
    }
    public String update() {
        try {
            encode();
            for (Donnees_Marginales dm : current.getDonnees_Marginaless()) {
                if (!dm.getType().getAttrs().equals("")) {
                    dm.setAttrValues("");
                    Map mp = m.get(dm);
                    Set listKeys = mp.keySet();
                    Iterator iterateur = listKeys.iterator();
                    while (iterateur.hasNext()) {
                        Object key = iterateur.next();
                        if (dm.getAttrValues().equals("")) {
                            dm.setAttrValues(key + "=" + mp.get(key) + "&");
                        } else {
                            dm.setAttrValues(dm.getAttrValues() + key + "=" + mp.get(key) + "&");
                        }
                    }
                }
                ejbFacade2.create(dm);
            }
            getFacade().edit(current);
            for (Donnees_Marginales dm : current.getDonnees_Marginaless()) {
                dm.setActe(current);
                dm.setDescAr(URLEncoder.encode(dm.getDescAr(), "UTF-8"));
                dm.setAttrValues(URLEncoder.encode(dm.getAttrValues(), "UTF-8"));
                ejbFacade2.edit(dm);
            }
            JsfUtil.addSuccessMessage("تم التسجيل بنجاح");
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("المرجو تصحيح المعلومات");
            return null;
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

    public void changeDecesP() {

        if (current.isDecesP()) {
            current.setLieu_de_NaissP_Ar("متوفي");
            current.setLieu_de_NaissP_Fr("décédé");
            current.setNationalteP_Ar("متوفي");
            current.setNationalteP_Fr("décédé");
            current.setProfessionP_Ar("متوفي");
            current.setProfessionP_Fr("décédé");
            current.setDate_de_naissP_G(null);
            current.setDate_de_naissP_H(null);
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
            current.setDate_de_naissM_G(null);
            current.setDate_de_naissM_H(null);
        } else {
            current.setLieu_de_NaissM_Ar("");
            current.setLieu_de_NaissM_Fr("");
            current.setNationalteM_Ar("");
            current.setNationalteM_Fr("");
            current.setProfessionM_Ar("");
            current.setProfessionM_Fr("");
        }
    }

    public Object traitMen(String attr) {
        Object res = "";
        try {
            if (!current.getDonnees_Marginaless().isEmpty()) {
                for (Donnees_Marginales dm : current.getDonnees_Marginaless()) {
                    Map<Attr, Value> map = new HashMap<Attr, Value>();
                    if (!dm.getAttrValues().isEmpty()) {
                        String[] pairs = dm.getAttrValues().split("&");
                        for (int i = 0; i < pairs.length; i++) {
                            String pair = pairs[i];
                            String[] keyValue = pair.split("=");
                            if (keyValue[0].equals(attr)) {
                                return keyValue[1];
                            }
                        }
                    }
                }
            }
            res = showFields(current, attr);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public Date stringToDate(String dateS) {
        String[] pair2 = dateS.split("-");
        Date date = new Date(Integer.parseInt(pair2[2])-1900, Integer.parseInt(pair2[1]), Integer.parseInt(pair2[0]));
        return date;
    }

    public Object showFields(Object o, String attr) throws IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = o.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(attr)) {
                try {
                    field.setAccessible(true);
                    if (attr.equals("date_de_naiss_G")) {
                        Date res = (Date) field.get(o);
                        return res;
                    } else {
                        String res = (String) field.get(o);
                        return URLDecoder.decode(res, "UTF-8");
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Acte_NaissanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public void PDF() throws JRException, IOException {
        List<Acte_Naissance> acts = new ArrayList<Acte_Naissance>();
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
        if (!ini.get("config", "format").equals("2")) {
            SimpleDateFormat y = new SimpleDateFormat("yyyy");
            params.put("numActe", "" + current.getNumActe());
            params.put("anneeH", "" + y.format(current.getDateTah_H()));
            params.put("anneeG", "" + y.format(current.getDateTah_G()));
            params.put("province", ini.get("commune", "province"));
            params.put("commune", ini.get("commune", "commune"));
            params.put("communeAr", ini.get("commune", "communeAr"));
            params.put("provinceAr", ini.get("commune", "provinceAr"));
            params.put("nom", traitMen("nom_Fr"));
            params.put("prenom", traitMen("prenom_Fr"));
            params.put("lieuNaissance", traitMen("lieu_de_Naiss_Fr"));
            params.put("dateNaissance", (traitMen("date_de_naiss_G") instanceof String) ? Helper.dateToStrG(stringToDate((String)traitMen("date_de_naiss_G"))) : current.isNoMJ() == false ? Helper.dateToStrG((Date) traitMen("date_de_naiss_G")) : "سنة " + Helper.int2str(Integer.parseInt(y.format((Date) traitMen("date_de_naiss_G")))));
           // params.put("dateNaissance", current.isNoMJ() == false ? Helper.dateToStrG(current.getDate_de_naiss_G()) : Helper.int2str(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
            params.put("nationnalite", "Marocaine");
            params.put("pere", traitMen("prenomP_Fr"));
            params.put("mere", traitMen("prenomM_Fr"));
            params.put("nomAr", traitMen("nom_Ar"));
            params.put("prenomAr", traitMen("prenom_Ar"));
            params.put("lieuNaissanceAr", traitMen("lieu_de_Naiss_Ar"));
            params.put("dateNaissanceAr", (traitMen("date_de_naiss_G") instanceof String) ? Helper.dateToStrArG(stringToDate((String)traitMen("date_de_naiss_G"))) : current.isNoMJ() == false ? Helper.dateToStrArG((Date) traitMen("date_de_naiss_G")) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format((Date) traitMen("date_de_naiss_G")))));
            //params.put("dateNaissanceAr", current.isNoMJ() == false ? Helper.dateToStrArG(((Date) traitMen("date_de_naiss_G"))) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(((Date)traitMen("date_de_naiss_G"))))));
            params.put("nationnaliteAr", "مغربية");
            params.put("pereAr", traitMen("prenomP_Ar"));
            params.put("mereAr", traitMen("prenomM_Ar"));
            boolean trouve = false;
            for (int i = 0; i < current.getDonnees_Marginaless().size(); i++) {
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 14 || current.getDonnees_Marginaless().get(i).getType().getId() == 15) {
                    trouve = true;
                    break;
                }
            }
            if (trouve) {
                if (current.getSex().getId() == 1) {
                    params.put("decesAr", "متوفى");
                    params.put("deces", "Décédé");
                } else {
                    params.put("decesAr", "متوفية");
                    params.put("deces", "Décédée");
                }

            } else {
                params.put("decesAr", "لا شيء");
                params.put("deces", "Néant");
            }
            InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/extrait.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setContentType("application/pdf");
            //httpServletResponse.setHeader("Content-Disposition", "attachment; filename=MyAwesomeJasperReportDownload.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        } else {
            SimpleDateFormat y = new SimpleDateFormat("yyyy");
            params.put("numActe", "" + current.getNumActe());
            params.put("anneeH", "" + y.format(current.getDateTah_H()));
            params.put("anneeG", "" + y.format(current.getDateTah_G()));
            params.put("communeAr", ini.get("commune", "communeAr"));
            params.put("provinceAr", ini.get("commune", "provinceAr"));
            params.put("nomAr", current.getNom_Ar());
            params.put("prenomAr", current.getPrenom_Ar());
            params.put("lieuNaissanceAr", current.getLieu_de_Naiss_Ar());
            params.put("dateNaissanceGAr", current.isNoMJ() == false ? Helper.dateToStrArG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
            params.put("dateNaissanceHAr", current.isNoMJ() == false ? Helper.dateHToStrArH(current.getDate_de_naiss_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
            params.put("nationnaliteAr", "مغربية");
            params.put("officierAr", current.getOfficierAr());
            params.put("pereAr", current.getPrenomP_Ar());
            params.put("mereAr", current.getPrenomM_Ar());
            boolean trouve = false;
            for (int i = 0; i < current.getDonnees_Marginaless().size(); i++) {
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 14 || current.getDonnees_Marginaless().get(i).getType().getId() == 15) {
                    trouve = true;
                    break;
                }
            }
            if (trouve) {
                if (current.getSex().getId() == 1) {
                    params.put("decesAr", "متوفى");
                } else {
                    params.put("decesAr", "متوفية");
                }

            } else {
                params.put("decesAr", "لا شيء");
            }

            InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/extrait-ar.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);

            params = new HashMap();
            params.put("numActe", "" + current.getNumActe());
            params.put("anneeH", "" + y.format(current.getDateTah_H()));
            params.put("anneeG", "" + y.format(current.getDateTah_G()));
            params.put("province", ini.get("commune", "province"));
            params.put("commune", ini.get("commune", "commune"));
            params.put("nom", current.getNom_Fr());
            params.put("prenom", current.getPrenom_Fr());
            params.put("lieuNaissance", current.getLieu_de_Naiss_Fr());
            params.put("dateNaissance", current.isNoMJ() == false ? Helper.dateToStrG(current.getDate_de_naiss_G()) : Helper.int2str(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
            params.put("correspondant", current.isNoMJ() == false ? Helper.dateHToStrH(current.getDate_de_naiss_H()) : Helper.int2str(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
            params.put("nationnalite", "Marocaine");
            params.put("pere", current.getPrenomP_Fr());
            params.put("mere", current.getPrenomM_Fr());
            params.put("officierFr", current.getOfficierFr());

            boolean trouve2 = false;
            for (int i = 0; i < current.getDonnees_Marginaless().size(); i++) {
                if (current.getDonnees_Marginaless().get(i).getType().getId() == 14 || current.getDonnees_Marginaless().get(i).getType().getId() == 15) {
                    trouve = true;
                    break;
                }
            }
            if (trouve2) {
                if (current.getSex().getId() == 1) {
                    params.put("deces", "Décédé");
                } else {
                    params.put("deces", "Décédée");
                }

            } else {
                params.put("deces", "Néant");
            }

            JasperPrint jasperPrint2;
            InputStream reportSource2 = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/extrait-fr.jasper");
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
    }

    public void integrale() throws JRException, IOException {
        List<Acte_Naissance> acts = new ArrayList<Acte_Naissance>();
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        params.put("anneeH", "" + y.format(current.getDateTah_H()));
        params.put("anneeG", "" + y.format(current.getDateTah_G()));
        //Wini ini = new Wini(new File("C:\\jars\\conf.ini"));
        File f1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini"));
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        params.put("province", ini.get("commune", "province"));
        params.put("commune", ini.get("commune", "commune"));
        params.put("communeAr", ini.get("commune", "communeAr"));
        params.put("provinceAr", ini.get("commune", "provinceAr"));
        params.put("dateNaissanceGAr", current.isNoMJ() == false ? Helper.dateToStrArG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateNaissanceHAr", current.isNoMJ() == false ? Helper.dateHToStrArH(current.getDate_de_naiss_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
        params.put("dateNaissanceGArP", current.isNoMJP() == false ? Helper.dateToStrArG(current.getDate_de_naissP_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naissP_G()))));
        params.put("dateNaissanceHArP", current.isNoMJP() == false ? Helper.dateHToStrArH(current.getDate_de_naissP_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naissP_H()))));
        params.put("dateNaissanceGArM", current.isNoMJM() == false ? Helper.dateToStrArG(current.getDate_de_naissM_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naissM_G()))));
        params.put("dateNaissanceHArM", current.isNoMJM() == false ? Helper.dateHToStrArH(current.getDate_de_naissM_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naissM_H()))));
        params.put("dateTahH", Helper.dateHToStrArH(current.getDateTah_H()));
        params.put("dateTahG", Helper.dateToStrArG(current.getDateTah_G()));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arial.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/ariali.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbi.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbd.ttf"));
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/integrale.jasper");
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

    public void pdfVie() throws JRException, IOException {
        List<Acte_Naissance> acts = new ArrayList<Acte_Naissance>();
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        params.put("annee", "" + current.getRegistre().getAnnee());
        //Wini ini = new Wini(new File("C:\\jars\\conf.ini"));
        File f1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini"));
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        params.put("province", ini.get("commune", "province"));
        params.put("commune", ini.get("commune", "commune"));
        params.put("communeAr", ini.get("commune", "communeAr"));
        params.put("provinceAr", ini.get("commune", "provinceAr"));
        params.put("dateNaissanceGAr", current.isNoMJ() == false ? Helper.dateToStrArG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateNaissanceHAr", current.isNoMJ() == false ? Helper.dateHToStrArH(current.getDate_de_naiss_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));

        if (current.isDecesP()) {
            params.put("dateNaissanceGArP", "متوفي");
            params.put("dateNaissanceHArP", "متوفي");
        } else {
            params.put("dateNaissanceGArP", current.isNoMJP() == false ? Helper.dateToStrArG(current.getDate_de_naissP_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naissP_G()))));
            params.put("dateNaissanceHArP", current.isNoMJP() == false ? Helper.dateHToStrArH(current.getDate_de_naissP_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naissP_H()))));
        }
        if (current.isDecesM()) {
            params.put("dateNaissanceGArM", "متوفية");
            params.put("dateNaissanceHArM", "متوفية");
        } else {
            params.put("dateNaissanceGArM", current.isNoMJM() == false ? Helper.dateToStrArG(current.getDate_de_naissM_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naissM_G()))));
            params.put("dateNaissanceHArM", current.isNoMJM() == false ? Helper.dateHToStrArH(current.getDate_de_naissM_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naissM_H()))));
        }
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
        List<Acte_Naissance> acts = new ArrayList<Acte_Naissance>();
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        params.put("anneeH", "" + y.format(current.getDateTah_H()));
        params.put("anneeG", "" + y.format(current.getDateTah_G()));
        //Wini ini = new Wini(new File("C:\\jars\\conf.ini"));
        File f1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini"));
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        params.put("province", ini.get("commune", "province"));
        params.put("commune", ini.get("commune", "commune"));
        params.put("communeAr", ini.get("commune", "communeAr"));
        params.put("provinceAr", ini.get("commune", "provinceAr"));
        params.put("annee", current.getRegistre().getAnnee());
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
            pagination = new PaginationHelper(primaryRowCount) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    if (test) {
                        Long a = new Long(0);
                        Long b = new Long(0);
                        if (anneeFilter != null && anneeFilter > 0) {
                            b = anneeFilter;
                        }
                        if (numActeFilter != null && numActeFilter > 0) {
                            a = numActeFilter;
                        }

                        return new ListDataModel(getFacade().findByAnnee(a, b));
                    } else {
                        return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                    }
                }
            };
        }
        return pagination;
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    public void destroyModel() {
        items = null;
        System.out.println("destroyModel");
        pagination = null;
        items = getPagination().createPageDataModel();
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
