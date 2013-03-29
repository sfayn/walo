/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

/**
 *
 * @author YOU$$EF
 */
public class TestIni {

    public static void main(String args[]) {
        try {
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
        }

    }
}
