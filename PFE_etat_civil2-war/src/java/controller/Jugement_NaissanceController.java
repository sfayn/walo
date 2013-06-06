package controller;

import bean.Acte_Naissance;
import bean.Donnees_Marginales;
import bean.Donnees_Marginales_J_N;
import bean.Jugement_Deces;
import bean.Jugement_Naissance;
import bean.Registre;
import bean.User;
import controller.util.Helper;
import controller.util.JsfUtil;
import controller.util.PaginationHelper;
import controller.util.UtilitaireSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import session.Jugement_NaissanceFacade;

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

@ManagedBean(name = "jugement_NaissanceController")
@SessionScoped
public class Jugement_NaissanceController implements Serializable {

    private Jugement_Naissance current;
    private DataModel items = null;
    @EJB
    private session.Jugement_NaissanceFacade ejbFacade;
    @EJB
    private session.Donnees_Marginales_J_NFacade ejbFacade2;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String annee;
    private String annee_jug_Naiss;
    private int numActe;
    private Registre registre;
    private Long numActeFilter;
    private String anneeFilter;
    private Integer primaryRowCount = 10;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private String etatFamilleAr;
    private String adresseAr;
    private String professionAr;

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
    
    

    public void changetext() {
//    current.setDescriptionAr("بعد اخبارنا بتاريخ    هجرية موافق   بالحكم تحت عدد:          الصادر من المحكمة الابتدائية   بتاريخ   هجرية موافق  و المتعلق بولادة    ننقل مضمون هدا الحكم   ـــــــــــــــــــــــــــ لهده الاسباب ـــــــــــــــــــــــــــــــ    فقد صدر الحكم انه بتاريخ  هجرية موافق   ميلادية    ولد ب     من ابيه  المولود ب                في           هجرية موافق                ميلادية        المغربي الجنسية حرفته       و من والدته    المولودة ب           في          هجرية موافق         ميلادية جنسيتها مغربية حرفتها               و الساكنان   كما امرت المحكمة بتسجيل مضمون هدا الحكم في سجلات الحالة المدنية للسنة الجارية بالاشارة الموجزة لمضمونه في سجلات السنة التي كان من الواجب ان يسجل فيها قانونيا ـــــــــ  و نقلناه بتاريخ  تاني رجب  سنة   ألف و أربعمائة و أربعة و ثلاثين هجرية موافق ثاني عشر ماي  سنة   ألفين  و ثلاثة عشر ميلادية  لدينا نحن ");
        current.setDescriptionAr("بعد اخبارنا بتاريخ  " + Helper.dateHToStrArH(current.getDateDeclaration_H()) + " هجرية موافق " + Helper.dateToStrArG(current.getDateDeclaration()) + " بالحكم تحت عدد:          الصادر من المحكمة الابتدائية   بتاريخ " + Helper.dateHToStrArH(current.getDateJug_H()) + " هجرية موافق " + Helper.dateToStrArG(current.getDateJug()) + " و المتعلق بولادة " + current.getNom_Ar() + " " + current.getPrenom_Ar() +" ننقل مضمون هدا الحكم   ـــــــــــــــــــــــــــ لهده الاسباب ـــــــــــــــــــــــــــــــ    فقد صدر الحكم انه بتاريخ " + Helper.dateHToStrArH(current.getDate_de_naiss_H()) + " هجرية موافق " + Helper.dateToStrArG(current.getDate_de_naiss_G()) + " ولد ب " + current.getLieu_de_Naiss_Ar() + " من ابيه " + current.getPrenomP_Ar() + " المولود ب                في           هجرية موافق                ميلادية        المغربي الجنسية حرفته       و من والدته " + current.getPrenomM_Ar() + " المولودة ب           في          هجرية موافق         ميلادية جنسيتها مغربية حرفتها               و الساكنان   كما امرت المحكمة بتسجيل مضمون هدا الحكم في سجلات الحالة المدنية للسنة الجارية بالاشارة الموجزة لمضمونه في سجلات السنة التي كان من الواجب ان يسجل فيها قانونيا ـــــــــ  و نقلناه بتاريخ " + Helper.dateHToStrArH(current.getDatesdistr_H()) + " هجرية موافق " + Helper.dateToStrArG(current.getDatesdistr()));
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
        getG_to_h();
    }

    public String getAnnee_jug_Naiss() {
        return annee_jug_Naiss;
    }

    public void getG_to_h() {
        if (current.getDate_de_naiss_G() != null) {
            current.setDate_de_naiss_H(Helper.dateGrToH(current.getDate_de_naiss_G()));
        }

    }

    public void getDeclarationG_to_h() {
        if (current.getDateDeclaration() != null) {
            current.setDateDeclaration_H(Helper.dateGrToH(current.getDateDeclaration()));
        }

    }

    public void declarationg_to_hplus() {
        if (current.getDateDeclaration() != null) {
            j++;
            Date tmp = new Date(current.getDateDeclaration().getYear(), current.getDateDeclaration().getMonth(), current.getDateDeclaration().getDate());
            tmp.setDate(tmp.getDate() + j);
            current.setDateDeclaration_H(Helper.dateGrToH(tmp));
            changetext();
        }
    }

    public void declarationg_to_hmoins() {
        if (current.getDateDeclaration() != null) {
            j--;
            Date tmp = new Date(current.getDateDeclaration().getYear(), current.getDateDeclaration().getMonth(), current.getDateDeclaration().getDate());
            tmp.setDate(tmp.getDate() + j);
            current.setDateDeclaration_H(Helper.dateGrToH(tmp));
            changetext();
        }
    }

    public void getDistrG_to_h() {
        if (current.getDatesdistr() != null) {
            current.setDatesdistr_H(Helper.dateGrToH(current.getDatesdistr()));
        }

    }

    public void distrg_to_hplus() {
        if (current.getDatesdistr() != null) {
            l++;
            Date tmp = new Date(current.getDatesdistr().getYear(), current.getDatesdistr().getMonth(), current.getDatesdistr().getDate());
            tmp.setDate(tmp.getDate() + l);
            current.setDatesdistr_H(Helper.dateGrToH(tmp));
            changetext();
        }
    }

    public void distrg_to_hmoins() {
        if (current.getDatesdistr() != null) {
            l--;
            Date tmp = new Date(current.getDatesdistr().getYear(), current.getDatesdistr().getMonth(), current.getDatesdistr().getDate());
            tmp.setDate(tmp.getDate() + l);
            current.setDatesdistr_H(Helper.dateGrToH(tmp));
            changetext();
        }
    }

    public void getJugG_to_h() {
        if (current.getDateJug() != null) {
            current.setDateJug_H(Helper.dateGrToH(current.getDateJug()));
        }

    }

    public void jugg_to_hplus() {
        if (current.getDateJug() != null) {
            k++;
            Date tmp = new Date(current.getDateJug().getYear(), current.getDateJug().getMonth(), current.getDateJug().getDate());
            tmp.setDate(tmp.getDate() + k);
            current.setDateJug_H(Helper.dateGrToH(tmp));
            changetext();
        }
    }

    public void jugg_to_hmoins() {
        if (current.getDateJug() != null) {
            k--;
            Date tmp = new Date(current.getDateJug().getYear(), current.getDateJug().getMonth(), current.getDateJug().getDate());
            tmp.setDate(tmp.getDate() + k);
            current.setDateJug_H(Helper.dateGrToH(tmp));
            changetext();
        }
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
        getDeclarationG_to_h();
        changetext();
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
        getJugG_to_h();
        changetext();
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
        getDistrG_to_h();
        changetext();
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

    public void changeDonnees_MarginalesRemove(Donnees_Marginales_J_N donnee_Marginale) {
        for (int i = 0; i < current.getDonnees_Marginaless().size(); i++) {
            if (current.getDonnees_Marginaless().get(i) == donnee_Marginale) {
                current.getDonnees_Marginaless().remove(i);
            }
        }
    }

    public void changeDescDM(Donnees_Marginales_J_N dm) throws UnsupportedEncodingException {
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

    public void changeDonnees_Marginales() {
        Donnees_Marginales_J_N dm = new Donnees_Marginales_J_N();
        current.getDonnees_Marginaless().add(dm);
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

    public void encode() {
        try {
            current.setDescriptionAr(URLEncoder.encode(current.getDescriptionAr(), "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Jugement_DecesController.class.getName()).log(Level.SEVERE, null, ex);
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
            for (Donnees_Marginales_J_N dm : current.getDonnees_Marginaless()) {
                ejbFacade2.create(dm);
            }
            getFacade().create(current);
            for (Donnees_Marginales_J_N dm : current.getDonnees_Marginaless()) {
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
        current = (Jugement_Naissance) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public void GToHAnnee() {
        if (current.getDate_de_naiss_G() != null) {
            current.setDate_de_naiss_H(Helper.dateGrToH(current.getDate_de_naiss_G()));
        }
    }

    public String update() {
        try {
            encode();
            for (Donnees_Marginales_J_N dm : current.getDonnees_Marginaless()) {
                ejbFacade2.create(dm);
            }
            getFacade().edit(current);
            for (Donnees_Marginales_J_N dm : current.getDonnees_Marginaless()) {
                dm.setJugement(current);
                dm.setDescAr(URLEncoder.encode(dm.getDescAr(), "UTF-8"));
                ejbFacade2.edit(dm);
            }
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

    public void PDF() throws JRException, IOException {
        List<Jugement_Naissance> acts = new ArrayList<Jugement_Naissance>();
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
            params.put("anneeH", "" + y.format(current.getDatesdistr_H()));
            params.put("anneeG", "" + y.format(current.getDatesdistr()));

            params.put("province", ini.get("commune", "province"));
            params.put("commune", ini.get("commune", "commune"));
            params.put("communeAr", ini.get("commune", "communeAr"));
            System.out.println("commune: ---> " + ini.get("commune", "communeAr"));
            params.put("provinceAr", ini.get("commune", "provinceAr"));

            params.put("nom", current.getNom_Fr());
            params.put("prenom", current.getPrenom_Fr());
            params.put("lieuNaissance", current.getLieu_de_Naiss_Fr());
            params.put("dateNaissance", current.isNoMJ() == false ? Helper.dateToStrG(current.getDate_de_naiss_G()) : Helper.int2str(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
            params.put("nationnalite", "Marocaine");
            params.put("pere", current.getPrenomP_Fr());
            params.put("mere", current.getPrenomM_Fr());
            params.put("deces", "Néant");

            params.put("nomAr", current.getNom_Ar());
            params.put("prenomAr", current.getPrenom_Ar());
            params.put("lieuNaissanceAr", current.getLieu_de_Naiss_Ar());
            params.put("dateNaissanceAr", current.isNoMJ() == false ? Helper.dateToStrArG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
            params.put("nationnaliteAr", "مغربية");
            params.put("pereAr", current.getPrenomP_Ar());
            params.put("mereAr", current.getPrenomM_Ar());
            params.put("decesAr", "لا شيء");

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
            params.put("anneeH", "" + y.format(current.getDatesdistr_H()));
            params.put("anneeG", "" + y.format(current.getDatesdistr()));

            params.put("communeAr", ini.get("commune", "communeAr"));
            params.put("provinceAr", ini.get("commune", "provinceAr"));

            params.put("nomAr", current.getNom_Ar());
            params.put("prenomAr", current.getPrenom_Ar());
            params.put("lieuNaissanceAr", current.getLieu_de_Naiss_Ar());
            params.put("dateNaissanceGAr", current.isNoMJ() == false ? Helper.dateToStrArG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
            params.put("dateNaissanceHAr", current.isNoMJ() == false ? Helper.dateHToStrArH(current.getDate_de_naiss_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
            params.put("nationnaliteAr", "مغربية");
            params.put("pereAr", current.getPrenomP_Ar());
            params.put("mereAr", current.getPrenomM_Ar());
            params.put("decesAr", "لا شيء");

            InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/extrait-ar.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);

            params = new HashMap();
            params.put("numActe", "" + current.getNumActe());
            params.put("anneeH", "" + y.format(current.getDatesdistr_H()));
            params.put("anneeG", "" + y.format(current.getDatesdistr()));

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
            params.put("deces", "Néant");


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
    public void pdffiancaille() throws JRException, IOException {
        List<Jugement_Naissance> acts = new ArrayList<Jugement_Naissance>();
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        params.put("anneeH", "" + y.format(current.getDatesdistr_H()));
        params.put("anneeG", "" + y.format(current.getDatesdistr()));
        //Wini ini = new Wini(new File("C:\\jars\\conf.ini"));
        File f1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini"));
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        params.put("province", ini.get("commune", "province"));
        params.put("commune", ini.get("commune", "commune"));
        params.put("communeAr", ini.get("commune", "communeAr"));
        params.put("provinceAr", ini.get("commune", "provinceAr"));
        params.put("dateNaissanceGAr", current.isNoMJ() == false ? Helper.dateToStrArG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateNaissanceHAr", current.isNoMJ() == false ? Helper.dateHToStrArH(current.getDate_de_naiss_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
        params.put("professionAr", professionAr);
        params.put("annee", current.getRegistre().getAnnee());
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
public void pdfVie() throws JRException, IOException {
        List<Jugement_Naissance> acts = new ArrayList<Jugement_Naissance>();
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        params.put("anneeH", "" + y.format(current.getDatesdistr_H()));
        params.put("anneeG", "" + y.format(current.getDatesdistr()));
        //Wini ini = new Wini(new File("C:\\jars\\conf.ini"));
        File f1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini"));
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        params.put("province", ini.get("commune", "province"));
        params.put("commune", ini.get("commune", "commune"));
        params.put("communeAr", ini.get("commune", "communeAr"));
        params.put("annee",current.getRegistre().getAnnee());
        params.put("provinceAr", ini.get("commune", "provinceAr"));
        params.put("dateNaissanceGAr", current.isNoMJ() == false ? Helper.dateToStrArG(current.getDate_de_naiss_G()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_G()))));
        params.put("dateNaissanceHAr", current.isNoMJ() == false ? Helper.dateHToStrArH(current.getDate_de_naiss_H()) : "سنة " + Helper.int2strAr(Integer.parseInt(y.format(current.getDate_de_naiss_H()))));
        System.out.println(params.get("dateNaissanceGAr"));

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

    public void integrale() throws JRException, IOException {
        List<Jugement_Naissance> acts = new ArrayList<Jugement_Naissance>();
        acts.add(current);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(acts);
        Map params = new HashMap();
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        params.put("anneeH", "" + y.format(current.getDatesdistr_H()));
        params.put("anneeG", "" + y.format(current.getDatesdistr()));
        //Wini ini = new Wini(new File("C:\\jars\\conf.ini"));
        File f1 = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini"));
        Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
        params.put("province", ini.get("commune", "province"));
        params.put("commune", ini.get("commune", "commune"));
        params.put("communeAr", ini.get("commune", "communeAr"));
        params.put("provinceAr", ini.get("commune", "provinceAr"));
        params.put("dateTahH", Helper.dateHToStrArH(current.getDatesdistr_H()));
        params.put("dateTahG", Helper.dateToStrArG(current.getDatesdistr()));

        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arial.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/ariali.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbi.ttf"));
        JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/arialbd.ttf"));
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/integrale_J_N.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        //httpServletResponse.setHeader("Content-Disposition", "attachment; filename=MyAwesomeJasperReportDownload.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
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
