/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author YOU$$EF
 */
public class TestIni {

    public static void main(String args[]) {
        /*Set s = new HashSet();
         Map m = new HashMap();
         s = ResourceBundle.getBundle("Bundle2").keySet();
         for (Iterator it = s.iterator(); it.hasNext();) {
         String object = it.next().toString();
         m.put(object, ResourceBundle.getBundle("Bundle2").getString(object));
         System.out.println(object+" --> "+ResourceBundle.getBundle("Bundle2").getString(object));
         }*/

        Locale TEST_LOCALE = Locale.FRANCE;
        List<String> words = Arrays.asList(
                "ahmed", "bouchaub", "abed", "bct", "gatp", "youssef", "reda");
        System.out.println(words + " - Original Data");
        Collator collator = Collator.getInstance(TEST_LOCALE);
        collator.setStrength(Collator.PRIMARY);
        Collections.sort(words, collator);
        System.out.println(words.toString() + " " + Collator.PRIMARY);
        
        
        TEST_LOCALE = Locale.forLanguageTag("Ar");
        words = Arrays.asList(
                "خرسوا", "شاطر", "رفعة", "جماهير", "أولئك", "خدعنا", "بنطلون");
        System.out.println(words + " - Original Data");
        collator = Collator.getInstance(TEST_LOCALE);
        collator.setStrength(Collator.PRIMARY);
        Collections.sort(words, collator);
        System.out.println(words.toString() + " " + Collator.PRIMARY);
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
