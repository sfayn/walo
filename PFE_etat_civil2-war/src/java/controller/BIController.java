package controller;

import bean.Acte_Deces;
import bean.Acte_Naissance;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

@ManagedBean(name = "bIController")
@SessionScoped
public class BIController implements Serializable {

    @EJB
    private session.Acte_DecesFacade acte_DecesFacade;
    @EJB
    private session.Acte_NaissanceFacade acte_NaissanceFacade;
    private Integer anneeGeneral = 1990;//Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    private Integer mois = 01;//Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
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
        deces();
    }

    public void naissance() {
        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        SimpleDateFormat m = new SimpleDateFormat("MM");
        Integer tmpAnnee;
        Integer tmpMois;
        Integer tmpMere;
        int i;
        countHommeNaiss = 0;
        countFemmeNaiss = 0;
        params.put("h18", "" + 0);
        params.put("h1819", "" + 0);
        params.put("h2024", "" + 0);
        params.put("h2529", "" + 0);
        params.put("h3034", "" + 0);
        params.put("h3539", "" + 0);
        params.put("h4044", "" + 0);
        params.put("h4549", "" + 0);
        params.put("h50", "" + 0);
        params.put("f18", "" + 0);
        params.put("f1819", "" + 0);
        params.put("f2024", "" + 0);
        params.put("f2529", "" + 0);
        params.put("f3034", "" + 0);
        params.put("f3539", "" + 0);
        params.put("f4044", "" + 0);
        params.put("f4549", "" + 0);
        params.put("f50", "" + 0);
        params.put("countHommeNaiss", "" + 0);
        params.put("countFemmeNaiss", "" + 0);
        params.put("countHommeDec", "" + 0);
        params.put("countFemmeDec", "" + 0);
        for (Iterator it = acte_NaissanceFacade.findAll().iterator(); it.hasNext();) {
            Acte_Naissance acte_Naissance = (Acte_Naissance) it.next();
            tmpAnnee = acte_Naissance.getDateTah_G() != null ? new Integer(y.format(acte_Naissance.getDateTah_G())) : 0;
            tmpMois = acte_Naissance.getDateTah_G() != null ? new Integer(m.format(acte_Naissance.getDateTah_G())) : 0;
            tmpMere = acte_Naissance.getDate_de_naissM_G() != null ? new Integer(y.format(acte_Naissance.getDate_de_naissM_G())) : 0;
            if (tmpAnnee.equals(anneeGeneral) && tmpMois.equals(mois)) {
                if (acte_Naissance.getSex().getLibelleFr().equals("Masculin")) {
                    int age = anneeGeneral.intValue() - tmpMere.intValue();
                    countHommeNaiss++;
                    if (tmpMere.compareTo(0) > 0) {
                        if (age < 18) {
                            i = new Integer(params.get("h18").toString());
                            params.put("h18", "" + (i + 1));
                        } else if (age >= 18 && age <= 19) {
                            i = new Integer(params.get("h1819").toString());
                            params.put("h1819", "" + (i + 1));
                        } else if (age >= 20 && age <= 24) {
                            i = new Integer(params.get("h2024").toString());
                            params.put("h2024", "" + (i + 1));
                        } else if (age >= 25 && age <= 29) {
                            i = new Integer(params.get("h2529").toString());
                            params.put("h2529", "" + (i + 1));
                        } else if (age >= 30 && age <= 34) {
                            i = new Integer(params.get("h3034").toString());
                            params.put("h3034", "" + (i + 1));
                        } else if (age >= 35 && age <= 39) {
                            i = new Integer(params.get("h3539").toString());
                            params.put("h3539", "" + (i + 1));
                        } else if (age >= 40 && age <= 44) {
                            i = new Integer(params.get("h4044").toString());
                            params.put("h4044", "" + (i + 1));
                        } else if (age >= 45 && age <= 49) {
                            i = new Integer(params.get("h4549").toString());
                            params.put("h4549", "" + (i + 1));
                        } else {
                            i = new Integer(params.get("h50").toString());
                            params.put("h50", "" + (i + 1));
                        }
                    }
                } else {
                    int age = anneeGeneral.intValue() - tmpMere.intValue();
                    countFemmeNaiss++;
                    if (tmpMere.compareTo(0) > 0) {
                        if (age < 18) {
                            i = new Integer(params.get("f18").toString());
                            params.put("f18", "" + (i + 1));
                        } else if (age >= 18 && age <= 19) {
                            i = new Integer(params.get("f1819").toString());
                            params.put("f1819", "" + (i + 1));
                        } else if (age >= 20 && age <= 24) {
                            i = new Integer(params.get("f2024").toString());
                            params.put("f2024", "" + (i + 1));
                        } else if (age >= 25 && age <= 29) {
                            i = new Integer(params.get("f2529").toString());
                            params.put("f2529", "" + (i + 1));
                        } else if (age >= 30 && age <= 34) {
                            i = new Integer(params.get("f3034").toString());
                            params.put("f3034", "" + (i + 1));
                        } else if (age >= 35 && age <= 39) {
                            i = new Integer(params.get("f3539").toString());
                            params.put("f3539", "" + (i + 1));
                        } else if (age >= 40 && age <= 44) {
                            i = new Integer(params.get("f4044").toString());
                            params.put("f4044", "" + (i + 1));
                        } else if (age >= 45 && age <= 49) {
                            i = new Integer(params.get("f4549").toString());
                            params.put("f4549", "" + (i + 1));
                        } else {
                            i = new Integer(params.get("f50").toString());
                            params.put("f50", "" + (i + 1));
                        }
                    }
                }
            }
        }
        params.put("countHommeNaiss", "" + countHommeNaiss);
        params.put("countFemmeNaiss", "" + countFemmeNaiss);
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
            tmpNaiss = acte_Deces.getActe_Naissance().getDate_de_naiss_G() != null ? new Integer(m.format(acte_Deces.getActe_Naissance().getDate_de_naiss_G())) : 0;
            if (tmpAnnee.equals(anneeGeneral) && tmpMois.equals(mois)) {
                if (acte_Deces.getActe_Naissance().getSex().getLibelleFr().equals("Masculin")) {
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
    }

    public void DOCX1(ActionEvent actionEvent) throws JRException, IOException {
        table1();
        List items = new ArrayList<Acte_Naissance>();
        items.add(new Acte_Naissance());
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(items);
        InputStream reportSource = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/reports/table1.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, params, beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=table1.docx");
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
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=table2.docx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public List<Integer> getData() {
        /*SimpleDateFormat y = new SimpleDateFormat("yyyy");
         SimpleDateFormat m = new SimpleDateFormat("MM");
         Integer tmpAnnee;
         Integer tmpMois;
         countHommeNaiss = 0;
         countFemmeNaiss = 0;
         countHommeDec = 0;
         countFemmeDec = 0;
         for (Iterator it = acte_NaissanceFacade.findAll().iterator(); it.hasNext();) {
         Acte_Naissance acte_Naissance = (Acte_Naissance) it.next();
         tmpAnnee = acte_Naissance.getDateTah_G() != null ? new Integer(y.format(acte_Naissance.getDateTah_G())) : 0;
         tmpMois = acte_Naissance.getDateTah_G() != null ? new Integer(m.format(acte_Naissance.getDateTah_G())) : 0;
         if (tmpAnnee.equals(anneeGeneral) && tmpMois.equals(mois)) {
         if (acte_Naissance.getSex().getLibelleFr().equals("Masculin")) {
         countHommeNaiss++;
         } else {
         countFemmeNaiss++;
         }
         }
         }*/
        data = new ArrayList<Integer>();
        data.add((int) (Math.random() * 80));
        data.add((int) (Math.random() * 80));
        data.add((int) (Math.random() * 80));
        data.add((int) (Math.random() * 80));
        data.add((int) (Math.random() * 80));
        data.add((int) (Math.random() * 80));
        data.add((int) (Math.random() * 80));
        data.add((int) (Math.random() * 80));
        data.add((int) (Math.random() * 80));
        data.add((int) (Math.random() * 80));
        data.add((int) (Math.random() * 80));
        data.add((int) (Math.random() * 80));
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
