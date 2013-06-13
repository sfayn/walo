package bean;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import sun.applet.Main;

/*package bean;*/
/**
 *
 * @author Sfayn
 */
public class Types {


    Map map = new HashMap();

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    
    
    public Map modifi(int a,String b){
        map.put(a, b);
        return map;
    }
    public static void main(String args[]) {
        Types t=new Types();
        System.out.println( t.modifi(1, "1").size());
        System.out.println(t.modifi(2, "2").size());
        System.out.println(t.modifi(3, "3").size());
        System.out.println(t.modifi(4, "4").size());
        System.out.println(t.modifi(6, "6").toString());
    }
}