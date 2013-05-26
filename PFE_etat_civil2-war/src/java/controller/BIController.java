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
    private Integer anneeGeneral = 1990;//Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    private Integer mois = 01;//Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
    private List<Integer> dataD = new ArrayList<Integer>();
    private List<Integer> dataN = new ArrayList<Integer>();
    private List<Integer> data = new ArrayList<Integer>();
    Integer countHommeNaiss = 0;
    Integer countFemmeNaiss = 0;
    Integer countHommeDec = 0;
    Integer countFemmeDec = 0;
    Map params = new HashMap();

    public Integer getAnneeGeneral() {
        return anneeGeneral;
    }

    public void setAnneeGeneral(Integer anneeGeneral) {
        this.anneeGeneral = anneeGeneral;
    }

    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
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

    public void table1() {
        naissance();
        /*for (Object key : params.keySet()) {
            System.out.println("key: " + key.toString() + ", value: " + params.get(key));
        }*/
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
        params.put("countHommeDec", "" + 0);
        params.put("countFemmeDec", "" + 0);
        
        results = acte_NaissanceFacade.countBySex(anneeGeneral, mois);
        for (Object[] result : results) {
            String sex = ((String) result[1]);
            int count = ((Number) result[0]).intValue();
            if (sex.equals("Masculin")) {
                countHommeNaiss += count;
                params.put("countHommeNaiss", "" + count);
            } else {
                countHommeNaiss += count;
                params.put("countFemmeNaiss", "" + count);
            }
        }
        //params.put("countHommeNaiss", "" + countHommeNaiss);
        //params.put("countFemmeNaiss", "" + countFemmeNaiss);
    }

    public void deces() {
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        Integer tmpAnnee;
        Integer tmpMois;
        Integer tmpNaiss;
        int i;
        countHommeDec = 0;
        countFemmeDec = 0;

        params.put("h1", "" + 0);
        params.put("h14", "" + 0);
        for (int j = 5; j < 80; j += 5) {
            params.put("h" + j + (j + 4), "" + 0);
        }
        params.put("h80", "" + 0);

        params.put("f1", "" + 0);
        params.put("f14", "" + 0);
        for (int j = 5; j < 80; j += 5) {
            params.put("f" + j + (j + 4), "" + 0);
        }
        params.put("f80", "" + 0);


        params.put("countHommeDec", "" + 0);
        params.put("countFemmeDec", "" + 0);

        for (Iterator it = acte_DecesFacade.findAll().iterator(); it.hasNext();) {
            Acte_Deces acte_Deces = (Acte_Deces) it.next();
            tmpAnnee = acte_Deces.getDateTah_G() != null ? new Integer(y.format(acte_Deces.getDateTah_G())) : 0;
            tmpMois = acte_Deces.getDateTah_G() != null ? new Integer(m.format(acte_Deces.getDateTah_G())) : 0;
            tmpNaiss = acte_Deces.getDate_de_naiss_G() != null ? new Integer(m.format(acte_Deces.getDate_de_naiss_G())) : 0;
            if (tmpAnnee.equals(anneeGeneral) && tmpMois.equals(mois)) {
                if (acte_Deces.getSex().getLibelleFr().equals("Masculin")) {
                    countHommeDec++;
                    int age = anneeGeneral.intValue() - tmpNaiss.intValue();
                    if (age < 1) {
                        i = new Integer(params.get("h1").toString());
                        params.put("h1", "" + (i + 1));
                    } else if (age >= 1 && age <= 4) {
                        i = new Integer(params.get("h14").toString());
                        params.put("h14", "" + (i + 1));
                    }
                    for (int j = 5; j < 80; j += 5) {
                        if (age >= j && age <= j + 4) {
                            i = new Integer(params.get("h" + j + (j + 4)).toString());
                            params.put("h" + j + (j + 4), "" + (i + 1));
                        }
                    }
                    if (age >= 80) {
                        i = new Integer(params.get("h80").toString());
                        params.put("h80", "" + (i + 1));
                    }

                } else {
                    countFemmeDec++;
                    int age = anneeGeneral.intValue() - tmpNaiss.intValue();
                    if (age < 1) {
                        i = new Integer(params.get("f1").toString());
                        params.put("f1", "" + (i + 1));
                    } else if (age >= 1 && age <= 4) {
                        i = new Integer(params.get("f14").toString());
                        params.put("f14", "" + (i + 1));
                    }
                    for (int j = 5; j < 80; j += 5) {
                        if (age >= j && age <= j + 4) {
                            i = new Integer(params.get("f" + j + (j + 4)).toString());
                            params.put("f" + j + (j + 4), "" + (i + 1));
                        }
                    }
                    if (age >= 80) {
                        i = new Integer(params.get("f80").toString());
                        params.put("f80", "" + (i + 1));
                    }
                }
            }
        }
        params.put("countHommeDec", "" + countHommeDec);
        params.put("countFemmeDec", "" + countFemmeDec);
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

    public List<Integer> getDataN() {
        data = new ArrayList<Integer>();
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
        return dataN;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
