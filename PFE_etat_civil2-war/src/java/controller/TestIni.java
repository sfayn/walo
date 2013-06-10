/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 *
 * @author YOU$$EF
 */
public class TestIni {

    public static void main(String args[]) {
        Set s = new HashSet();
        Map m = new HashMap();
        s = ResourceBundle.getBundle("Bundle2").keySet();
        for (Iterator it = s.iterator(); it.hasNext();) {
            String object = it.next().toString();
            m.put(object, ResourceBundle.getBundle("Bundle2").getString(object));
            System.out.println(object+" --> "+ResourceBundle.getBundle("Bundle2").getString(object));
        }
        
        
        
        //System.out.println(ResourceBundle.getBundle("Bundle2").getKeys().nextElement());



        /*try {
         Wini ini = new Wini(new File("C:\\jars\\conf.ini"));
         System.out.println("province: ---> " + ini.get("commune", "province"));
         System.out.println("commune: ---> " + ini.get("commune", "commune"));
         System.out.println("commune Ar: ---> " + ini.get("commune", "communeAr"));
         System.out.println("province Ar: ---> " + ini.get("commune", "provinceAr"));
         } catch (FileNotFoundException ex) {
         Logger.getLogger(TestIni.class.getName()).log(Level.SEVERE, null, ex);
         } catch (InvalidFileFormatException ex) {
         Logger.getLogger(TestIni.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
         Logger.getLogger(TestIni.class.getName()).log(Level.SEVERE, null, ex);
         }*/

    }
}
