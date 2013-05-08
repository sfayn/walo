package controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "bIController")
@SessionScoped
public class BIController implements Serializable {

    @EJB
    private session.Acte_DecesFacade acte_DecesFacade;
    @EJB
    private session.Acte_NaissanceFacade acte_NaissanceFacade;
    private Integer anneeGeneral = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    private Integer mois = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
    private List<Integer> data = new ArrayList<Integer>();

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
    
    
    
    public List<Integer> getData() {
        data = new ArrayList<Integer>();
        data.add((int)(Math.random()*80));
        data.add((int)(Math.random()*80));
        data.add((int)(Math.random()*80));
        data.add((int)(Math.random()*80));
        data.add((int)(Math.random()*80));
        data.add((int)(Math.random()*80));
        data.add((int)(Math.random()*80));
        data.add((int)(Math.random()*80));
        data.add((int)(Math.random()*80));
        data.add((int)(Math.random()*80));
        data.add((int)(Math.random()*80));
        data.add((int)(Math.random()*80));
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
    
}
