package controller;

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
import session.Jugement_NaissanceFacade;

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

    
    public void changetext(){
    current.setDescriptionAr("بعد اخبارنا بتاريخ    هجرية موافق   بالحكم تحت عدد:          الصادر من المحكمة الابتدائية   بتاريخ   هجرية موافق  و المتعلق بولادة    ننقل مضمون هدا الحكم   ـــــــــــــــــــــــــــ لهده الاسباب ـــــــــــــــــــــــــــــــ    فقد صدر الحكم انه بتاريخ  هجرية موافق   ميلادية    ولد ب     من ابيه  المولود ب                في           هجرية موافق                ميلادية        المغربي الجنسية حرفته       و من والدته    المولودة ب           في          هجرية موافق         ميلادية جنسيتها مغربية حرفتها               و الساكنان   كما امرت المحكمة بتسجيل مضمون هدا الحكم في سجلات الحالة المدنية للسنة الجارية بالاشارة الموجزة لمضمونه في سجلات السنة التي كان من الواجب ان يسجل فيها قانونيا ـــــــــ  و نقلناه بتاريخ  تاني رجب  سنة   ألف و أربعمائة و أربعة و ثلاثين هجرية موافق ثاني عشر ماي  سنة   ألفين  و ثلاثة عشر ميلادية  لدينا نحن ");
    }
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;       
    }
    
    public String getAnnee_jug_Naiss() {
        return annee_jug_Naiss;
    }
    public Date getG_to_h() {
        if (current.getDate_de_naiss_G() == null) {
            return null;
        } else {
            current.setDate_de_naiss_H(Helper.dateTimeGrToH(current.getDate_de_naiss_G()));
            return current.getDate_de_naiss_H();
        }

    }
    public Date getDeclarationG_to_h() {
        if (current.getDateDeclaration() == null) {
            return null;
        } else {
            current.setDateDeclaration_H(Helper.dateTimeGrToH(current.getDateDeclaration()));
            return current.getDateDeclaration_H();
        }

    }
    public void declarationg_to_hplus() {
        if (current.getDateDeclaration() != null) {
            j++;
            current.getDateDeclaration().setDate(current.getDateDeclaration().getDate() + j);
        }
    }

    public void declarationg_to_hmoins() {
        if (current.getDateDeclaration() != null) {
            j--;
            current.getDateDeclaration().setDate(current.getDateDeclaration().getDate() + j);
        }
    }
    public Date getDistrG_to_h() {
        if (current.getDatesdistr() == null) {
            return null;
        } else {
            current.setDatesdistr_H(Helper.dateTimeGrToH(current.getDatesdistr()));
            return current.getDatesdistr_H();
        }

    }
    public void distrg_to_hplus() {
        if (current.getDatesdistr() != null) {
            l++;
            current.getDatesdistr().setDate(current.getDatesdistr().getDate() + l);
        }
    }

    public void distrg_to_hmoins() {
        if (current.getDatesdistr() != null) {
            l--;
            current.getDatesdistr().setDate(current.getDatesdistr().getDate() + l);
        }
    }
     public Date getJugG_to_h() {
        if (current.getDateJug() == null) {
            return null;
        } else {
            current.setDateJug_H(Helper.dateTimeGrToH(current.getDateJug()));
            return current.getDateJug_H();
        }

    }
    public void jugg_to_hplus() {
        if (current.getDateJug() != null) {
            k++;
            current.getDateJug().setDate(current.getDateJug().getDate() + k);
        }
    }

    public void jugg_to_hmoins() {
        if (current.getDateJug() != null) {
            k--;
            current.getDateJug().setDate(current.getDateJug().getDate() + k);
        }
    }
    
    
    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
         changetext();
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
         changetext();
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
         changetext();
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
