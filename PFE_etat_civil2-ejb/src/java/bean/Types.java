package bean;

import sun.applet.Main;

/*package bean;*/
/**
 *
 * @author Sfayn
 */
public enum Types {

    numActe("رقم الرسم"),
    nom_Ar("الإسم العائلي"),
    nom_Fr("الإسم العائلي بالفرنسية"),
    prenom_Ar("الإسم الشخصي"),
    prenom_Fr("الإسم الشخصي بالفرنسية"),
    lieu_de_Naiss_Fr("مكان الإزدياد بالفرنسية"),
    lieu_de_Naiss_Ar("مكان الإزدياد"),
    noMJ("اليوم و الشهر"),
    prenomP_Ar("إسم الأب"),
    prenomP_Fr("إسم الأب بالفرنسية"),
    professionP_Ar("مهنة الأب"),
    professionP_Fr("مهنة الأب بالفرنسية"),
    lieu_de_NaissP_Fr("مكان إزدياد الأب بالفرنسية"),
    lieu_de_NaissP_Ar("مكان إزدياد الأب"),
    nationalteP_Fr("جنسية الأب بالفرنسية"),
    nationalteP_Ar("جنسية الأب"),
    noMJP("اليوم و الشهر للأب"),
    prenomM_Ar("إسم الأم"),
    prenomM_Fr("إسم الأم بالفرنسية"),
    professionM_Ar("مهنة الأم "),
    professionM_Fr("مهنة الأم بالفرنسية "),
    lieu_de_NaissM_Fr("مكان إزدياد الأم بالفرنسية"),
    lieu_de_NaissM_Ar("مكان إزدياد الأم"),
    nationalteM_Fr("جنسية الأم"),
    nationalteM_Ar("جنسيةالأم بالفرنسية"),
    noMJM("اليوم و الشهر الأم"),
    addressePa_Ar("عنوان الوالدين"),
    addressePa_Fr("عنوان الوالدين بالفرنسية"),
    sex("الجنس");
    private String name = "";
    private boolean flag = false;

    //Constructeur
    Types(String name, boolean flag) {
        this.name = name;
        this.flag = flag;
    }

    Types(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public static void main(String args[]) {
        int i = 0;
        Types[] resources = Types.values();
        int valuesNumber = resources.length;
        Types.nom_Ar.setFlag(true);
        for (Types type : resources) {           
            System.out.println(type.getName() + " " + type.isFlag());

        }
    }
}