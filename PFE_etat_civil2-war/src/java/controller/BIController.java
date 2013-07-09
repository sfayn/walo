package controller;

import bean.Acte_Deces;
import bean.Acte_Naissance;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import org.ini4j.Wini;

@ManagedBean(name = "bIController")
@SessionScoped
public class BIController implements Serializable {

    @EJB
    private session.Acte_DecesFacade acte_DecesFacade;
    @EJB
    private session.Acte_NaissanceFacade acte_NaissanceFacade;
    @EJB
    private session.Jugement_NaissanceFacade jugement_NaissanceFacade;
    @EJB
    private session.Jugement_DecesFacade jugement_DecesFacade;
    private Integer anneeGeneral = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    private Integer anneeGeneralTri = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    private Integer fromYear = 1990;
    private Integer toYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    private Integer mois = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
    private Integer triMois = 2;
    private List<Integer> dataD = new ArrayList<Integer>();
    private List<Integer> dataN = new ArrayList<Integer>();
    /*
     * count d'un mois
     */
    Integer countHommeNaiss = 0;
    Integer countFemmeNaiss = 0;
    Integer countNaiss = 0;
    Integer countJugNaiss = 0;
    Integer countHommeDec = 0;
    Integer countFemmeDec = 0;
    Integer countDec = 0;
    Integer countJugDec = 0;
    /*
     * count de trois mois
     */
    Integer countTriNaiss = 0;
    Integer countTriJugNaiss = 0;
    Integer countTriDec = 0;
    Integer countTriJugDec = 0;
    Map params = new HashMap();

    public Integer getFromYear() {
        return fromYear;
    }

    public void setFromYear(Integer fromYear) {
        this.fromYear = fromYear;
    }

    public Integer getToYear() {
        return toYear;
    }

    public void setToYear(Integer toYear) {
        this.toYear = toYear;
    }

    public Integer getAnneeGeneral() {
        return anneeGeneral;
    }

    public void setAnneeGeneral(Integer anneeGeneral) {
        this.anneeGeneral = anneeGeneral;
    }

    public Integer getAnneeGeneralTri() {
        return anneeGeneralTri;
    }

    public void setAnneeGeneralTri(Integer anneeGeneralTri) {
        this.anneeGeneralTri = anneeGeneralTri;
    }

    public Integer getCountJugNaiss() {
        countJugNaiss = jugement_NaissanceFacade.countByMonth(anneeGeneral, mois);
        return countJugNaiss;
    }

    public void setCountJugNaiss(Integer countJugNaiss) {
        this.countJugNaiss = countJugNaiss;
    }

    public Integer getCountJugDec() {
        countJugDec = jugement_DecesFacade.countByMonth(anneeGeneral, mois);
        return countJugDec;
    }

    public void setCountJugDec(Integer countJugDec) {
        this.countJugDec = countJugDec;
    }

    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Integer getTriMois() {
        return triMois;
    }

    public void setTriMois(Integer triMois) {
        this.triMois = triMois;
    }

    public Integer getCountHommeNaiss() {
        return countHommeNaiss;
    }

    public void setCountHommeNaiss(Integer countHommeNaiss) {
        this.countHommeNaiss = countHommeNaiss;
    }

    public Integer getCountFemmeNaiss() {
        return countFemmeNaiss;
    }

    public void setCountFemmeNaiss(Integer countFemmeNaiss) {
        this.countFemmeNaiss = countFemmeNaiss;
    }

    public Integer getCountHommeDec() {
        return countHommeDec;
    }

    public void setCountHommeDec(Integer countHommeDec) {
        this.countHommeDec = countHommeDec;
    }

    public Integer getCountFemmeDec() {
        return countFemmeDec;
    }

    public void setCountFemmeDec(Integer countFemmeDec) {
        this.countFemmeDec = countFemmeDec;
    }

    public Integer getCountTriNaiss() {
        return countTriNaiss;
    }

    public void setCountTriNaiss(Integer countTriNaiss) {
        this.countTriNaiss = countTriNaiss;
    }

    public Integer getCountTriJugNaiss() {
        return countTriJugNaiss;
    }

    public void setCountTriJugNaiss(Integer countTriJugNaiss) {
        this.countTriJugNaiss = countTriJugNaiss;
    }

    public Integer getCountTriDec() {
        return countTriDec;
    }

    public void setCountTriDec(Integer countTriDec) {
        this.countTriDec = countTriDec;
    }

    public Integer getCountTriJugDec() {
        return countTriJugDec;
    }

    public void setCountTriJugDec(Integer countTriJugDec) {
        this.countTriJugDec = countTriJugDec;
    }

    public void table1() {
        naissance();
        deces();
    }

    public void table2() {
        naissanceTri();
        //deces();
    }

    public void naissance() {
        countHommeNaiss = 0;
        countFemmeNaiss = 0;
        params = new HashMap();
        params.put("h18", "0");
        params.put("f18", "0");
        params.put("h1819", "0");
        params.put("f1819", "0");
        for (int i = 20; i < 50; i += 5) {
            params.put("h" + i + "" + (i + 4), "0");
            params.put("f" + i + "" + (i + 4), "0");
        }
        params.put("h50", "0");
        params.put("f50", "0");
        List<Object[]> results = acte_NaissanceFacade.countByAgeMere(anneeGeneral, mois, 0, 17);
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            System.out.println("18 --> sex: " + sex + ", count: " + count);
            if (sex.equals("Masculin")) {
                System.out.println("h18");
                params.put("h18", "" + count);
            } else {
                System.out.println("f18");
                params.put("f18", "" + count);
            }
        }
        results = acte_NaissanceFacade.countByAgeMere(anneeGeneral, mois, 18, 19);
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            System.out.println("1819 --> sex: " + sex + ", count: " + count);
            if (sex.equals("Masculin")) {
                System.out.println("h1819");
                params.put("h1819", "" + count);
            } else {
                System.out.println("f1819");
                params.put("f1819", "" + count);
            }
        }

        for (int i = 20; i < 50; i += 5) {
            results = acte_NaissanceFacade.countByAgeMere(anneeGeneral, mois, i, i + 4);
            for (Object[] result : results) {
                String sex = ((String) result[1]);
                int count = ((Number) result[0]).intValue();
                System.out.println(i + "" + (i + 4) + " --> sex: " + sex + ", count: " + count);
                if (sex.equals("Masculin")) {
                    System.out.println("h" + i + "" + (i + 4));
                    params.put("h" + i + "" + (i + 4), "" + count);
                } else {
                    System.out.println("f" + i + "" + (i + 4));
                    params.put("f" + i + "" + (i + 4), "" + count);
                }
            }
        }

        results = acte_NaissanceFacade.countByAgeMere(anneeGeneral, mois, 50, 1900);
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            System.out.println("50 --> sex: " + sex + ", count: " + count);
            if (sex.equals("Masculin")) {
                System.out.println("h50");
                params.put("h50", "" + count);
            } else {
                System.out.println("h50");
                params.put("f50", "" + count);
            }
        }

        params.put("countHommeNaiss", "0");
        params.put("countFemmeNaiss", "0");

        results = acte_NaissanceFacade.countBySex(anneeGeneral, mois);
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            if (sex.equals("Masculin")) {
                countHommeNaiss += count;
                params.put("countHommeNaiss", "" + count);
            } else {
                countFemmeNaiss += count;
                params.put("countFemmeNaiss", "" + count);
            }
        }
    }

    public void naissanceTri() {
        if (triMois == 1) {
            countTriNaiss = acte_NaissanceFacade.countByTriMonths(anneeGeneralTri, 1, 2, 3);
            countTriDec = acte_DecesFacade.countByTriMonths(anneeGeneralTri, 1, 2, 3);
            countTriJugNaiss = jugement_NaissanceFacade.countByTriMonths(anneeGeneralTri, 1, 2, 3);
            countTriJugDec = jugement_DecesFacade.countByTriMonths(anneeGeneralTri, 1, 2, 3);
            System.out.println("countTriNaiss: " + countTriNaiss);
            System.out.println("countTriDec: " + countTriDec);
            System.out.println("countTriJugNaiss: " + countTriJugNaiss);
            System.out.println("countTriJugDec: " + countTriJugDec);
        }
        if (triMois == 2) {
            countTriNaiss = acte_NaissanceFacade.countByTriMonths(anneeGeneralTri, 4, 5, 6);
            countTriDec = acte_DecesFacade.countByTriMonths(anneeGeneralTri, 4, 5, 6);
            countTriJugNaiss = jugement_NaissanceFacade.countByTriMonths(anneeGeneralTri, 4, 5, 6);
            countTriJugDec = jugement_DecesFacade.countByTriMonths(anneeGeneralTri, 4, 5, 6);
            System.out.println("countTriNaiss: " + countTriNaiss);
            System.out.println("countTriDec: " + countTriDec);
            System.out.println("countTriJugNaiss: " + countTriJugNaiss);
            System.out.println("countTriJugDec: " + countTriJugDec);
        }
        if (triMois == 3) {
            countTriNaiss = acte_NaissanceFacade.countByTriMonths(anneeGeneralTri, 7, 8, 9);
            countTriDec = acte_DecesFacade.countByTriMonths(anneeGeneralTri, 7, 8, 9);
            countTriJugNaiss = jugement_NaissanceFacade.countByTriMonths(anneeGeneralTri, 7, 8, 9);
            countTriJugDec = jugement_DecesFacade.countByTriMonths(anneeGeneralTri, 7, 8, 9);
            System.out.println("countTriNaiss: " + countTriNaiss);
            System.out.println("countTriDec: " + countTriDec);
            System.out.println("countTriJugNaiss: " + countTriJugNaiss);
            System.out.println("countTriJugDec: " + countTriJugDec);
        }
        if (triMois == 4) {
            countTriNaiss = acte_NaissanceFacade.countByTriMonths(anneeGeneralTri, 10, 11, 12);
            countTriDec = acte_DecesFacade.countByTriMonths(anneeGeneralTri, 10, 11, 12);
            countTriJugNaiss = jugement_NaissanceFacade.countByTriMonths(anneeGeneralTri, 10, 11, 12);
            countTriJugDec = jugement_DecesFacade.countByTriMonths(anneeGeneralTri, 10, 11, 12);
            System.out.println("countTriNaiss: " + countTriNaiss);
            System.out.println("countTriDec: " + countTriDec);
            System.out.println("countTriJugNaiss: " + countTriJugNaiss);
            System.out.println("countTriJugDec: " + countTriJugDec);
        }
    }

    public void deces() {
        countHommeDec = 0;
        countFemmeDec = 0;
        params.put("countHommeDec", "" + 0);
        params.put("countFemmeDec", "" + 0);

        params.put("h1", "0");
        params.put("h14", "0");
        for (int j = 5; j < 80; j += 5) {
            params.put("h" + j + (j + 4), "0");
        }
        params.put("h80", "0");

        params.put("f1", "0");
        params.put("f14", "0");
        for (int j = 5; j < 80; j += 5) {
            params.put("f" + j + (j + 4), "0");
        }
        params.put("f80", "0");
        params.put("countHommeDec", "" + 0);
        params.put("countFemmeDec", "" + 0);

        List<Object[]> results = acte_DecesFacade.countByAge(anneeGeneral, mois, 0, 1);
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            if (sex.equals("Masculin")) {
                params.put("h1", "" + count);
            } else {
                params.put("f1", "" + count);
            }
        }

        results = acte_DecesFacade.countByAge(anneeGeneral, mois, 2, 4);
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            if (sex.equals("Masculin")) {
                params.put("h14", "" + count);
            } else {
                params.put("f14", "" + count);
            }
        }

        for (int i = 5; i < 80; i += 5) {
            results = acte_DecesFacade.countByAge(anneeGeneral, mois, i, i + (i + 4));
            for (Object[] result : results) {
                String sex = ((String) result[1]);
                int count = ((Number) result[0]).intValue();
                if (sex.equals("Masculin")) {
                    params.put("h" + i + "" + (i + 4), "" + count);
                } else {
                    params.put("f" + i + "" + (i + 4), "" + count);
                }
            }
        }

        results = acte_DecesFacade.countByAge(anneeGeneral, mois, 80, 4000);
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            if (sex.equals("Masculin")) {
                params.put("h80", "" + count);
            } else {
                params.put("f80", "" + count);
            }
        }

        results = acte_DecesFacade.countBySex(anneeGeneral, mois);
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            if (sex.equals("Masculin")) {
                countHommeDec += count;
                params.put("countHommeDec", "" + count);
            } else {
                countFemmeDec += count;
                params.put("countFemmeDec", "" + count);
            }
        }

        try {
            Wini ini = new Wini(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/reports/conf.ini")));
            params.put("communeAr", ini.get("commune", "communeAr"));
            params.put("provinceAr", ini.get("commune", "provinceAr"));
            params.put("regionAr", ini.get("commune", "regionAr"));
        } catch (IOException ex) {
            Logger.getLogger(BIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] month = {
            "يناير",
            "فبراير",
            "مارس",
            "ابريل",
            "ماي",
            "يونيو",
            "يوليوز",
            "غشت",
            "شتنبر",
            "أكتوبر",
            "نونبر",
            "دجنبر"
        };
        params.put("annee", "" + anneeGeneral);
        params.put("mois", month[this.mois - 1]);
    }

    public void DOCX1(ActionEvent actionEvent) throws JRException, IOException {
        table1();
        List items = new ArrayList<Acte_Naissance>();
        items.add(new Acte_Naissance());
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(items);
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/table1.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String file = new SimpleDateFormat("yyyyMMdd").format(new Date());
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=table1-" + file + ".docx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void DOCX2(ActionEvent actionEvent) throws JRException, IOException {
        deces();
        List items = new ArrayList<Acte_Naissance>();
        items.add(new Acte_Naissance());
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(items);
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/table2.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String file = new SimpleDateFormat("yyyyMMdd").format(new Date());
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=table2-" + file + ".docx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void DOCX3(ActionEvent actionEvent) throws JRException, IOException {
        List items = new ArrayList<Acte_Naissance>();
        items.add(new Acte_Naissance());
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(items);
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/table3.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String file = new SimpleDateFormat("yyyyMMdd").format(new Date());
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=table3-" + file + ".docx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void DOCX45(ActionEvent actionEvent) throws JRException, IOException {
        List items = new ArrayList<Acte_Naissance>();
        items.add(new Acte_Naissance());
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(items);
        JRBeanCollectionDataSource beanCollectionDataSource2 = new JRBeanCollectionDataSource(items);
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/table45.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
        
        JasperPrint jasperPrint2;
            InputStream reportSource2 = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/table45-2.jasper");
            jasperPrint2 = JasperFillManager.fillReport(reportSource2, params, beanCollectionDataSource2);
            List pages = jasperPrint2.getPages();
            for (Iterator i = pages.iterator(); i.hasNext();) {
                JRPrintPage jRPrintPage = (JRPrintPage) i.next();
                jasperPrint.addPage(jRPrintPage);
            }
        
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String file = new SimpleDateFormat("yyyyMMdd").format(new Date());
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=table45-" + file + ".docx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public List<Integer> getDataN() {
        dataD = new ArrayList<Integer>();
        dataN = new ArrayList<Integer>();
        List<Object[]> results = acte_NaissanceFacade.countByDate(anneeGeneral);
        System.out.println("year: " + anneeGeneral);
        for (int i = 1; i < 13; i++) {
            boolean flag = false;
            for (Object[] result : results) {
                int month = ((Number) result[1]).intValue();
                if (month == i) {
                    dataN.add(((Number) result[0]).intValue());
                    flag = true;
                    System.out.println("Mois: " + month + ", count: " + ((Number) result[0]).intValue());
                }
            }
            if (!flag) {
                dataN.add(0);
            }
        }
        acte_NaissanceFacade.countByYearsMonths(1979, 1983);
        return dataN;
    }

    public List<Integer> getDataD() {
        dataD = new ArrayList<Integer>();
        List<Object[]> results = acte_DecesFacade.countByDate(anneeGeneral);
        System.out.println("year: " + anneeGeneral);
        for (int i = 1; i < 13; i++) {
            boolean flag = false;
            for (Object[] result : results) {
                int month = ((Number) result[1]).intValue();
                if (month == i) {
                    dataD.add(((Number) result[0]).intValue());
                    flag = true;
                    System.out.println("Mois: " + month + ", count: " + ((Number) result[0]).intValue());
                }
            }
            if (!flag) {
                dataD.add(0);
            }
        }
        return dataD;
    }

    private String[] rtn = {"", ""};
    private String[] rtnd = {"", ""};
    private String renderGraph = "mois";

    public String getRenderGraph() {
        return renderGraph;
    }

    public void setRenderGraph(String renderGraph) {
        this.renderGraph = renderGraph;
    }
    
    public String[] getRtn() {
        return rtn;
    }

    public void setRtn(String[] rtn) {
        this.rtn = rtn;
    }
    public String[] getRtnd() {
        return rtnd;
    }

    public void setRtnd(String[] rtnd) {
        this.rtnd = rtnd;
    }
    
    public void getDataYears() {
        
        List<Object[]> results = acte_NaissanceFacade.countByYearsMonths(fromYear, toYear);
        int tmp = 1;
        String s = "[";
        String t = "[";
        for (int i = fromYear; i <= toYear; i++) {
            //[[month,count],[month,count],]
            for (int j = 1; j <= 12; j++) {
                int y = i;
                int c = 0;
                int m = j;
                for (Object[] result : results) {
                    int year = ((Number) result[0]).intValue();
                    int count = ((Number) result[1]).intValue();
                    int month = ((Number) result[2]).intValue();
                    if (year == i && month == j) {
                        y = year;
                        c = count;
                        m = month;
                    }
                }
                s += "[" + tmp + "," + c + "],";
                if(m<10){
                    t += "[" + tmp + ",'0" + m + "/" + y + "'],";
                }else{
                    t += "[" + tmp + ",'" + m + "/" + y + "'],";
                }
                tmp++;
            }
        }
        s += "]";
        t += "]";
        rtn[0] = s;
        rtn[1] = t;
        getDataYearsD();
    }
    
    public void getDataYearsD() {
        
        List<Object[]> results = acte_DecesFacade.countByYearsMonths(fromYear, toYear);
        int tmp = 1;
        String s = "[";
        String t = "[";
        for (int i = fromYear; i <= toYear; i++) {
            //[[month,count],[month,count],]
            for (int j = 1; j <= 12; j++) {
                int y = i;
                int c = 0;
                int m = j;
                for (Object[] result : results) {
                    int year = ((Number) result[0]).intValue();
                    int count = ((Number) result[1]).intValue();
                    int month = ((Number) result[2]).intValue();
                    if (year == i && month == j) {
                        y = year;
                        c = count;
                        m = month;
                    }
                }
                s += "[" + tmp + "," + c + "],";
                if(m<10){
                    t += "[" + tmp + ",'0" + m + "/" + y + "'],";
                }else{
                    t += "[" + tmp + ",'" + m + "/" + y + "'],";
                }
                tmp++;
            }
        }
        s += "]";
        t += "]";
        rtnd[0] = s;
        rtnd[1] = t;
        
    }
}
