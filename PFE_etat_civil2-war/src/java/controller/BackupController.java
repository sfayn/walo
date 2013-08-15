package controller;

import controller.util.JsfUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@ManagedBean(name = "backupController")
@SessionScoped
public class BackupController implements Serializable {

    public BackupController() {
    }
    String folder = "c:/YOUSSEF/";
    Part file1;

    public String backupDB() {
        try {
            System.out.println("step 1");
            //String folder = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/bak");
            Date d = new Date();
            Backup b = new Backup();
            b.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(d));
            b.setFile(new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(d) + ".bak");
            String path = folder + b.getFile();
            String dbName = "pfe_etat_civil";
            String dbUserName = "root";
            String dbPassword = "";//-ppasswd
            String executeCmd = "mysqldump -u " + dbUserName + "" + dbPassword + " --add-drop-database -B " + dbName + " -r " + path;
            System.out.println(executeCmd);
            Process runtimeProcess;
            System.out.println("step 2");
            addToXML(b);

            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {

                FacesContext fc = FacesContext.getCurrentInstance();
                ExternalContext ec = fc.getExternalContext();
                HttpServletResponse response = (HttpServletResponse) ec.getResponse();
                response.setContentType("text/plain");
                response.addHeader("Content-Disposition", "attachment; filename=\"" + b.getFile() + "\"");
                byte[] buf = new byte[1024];
                File f = new File(path);
                long length = f.length();
                System.out.println("path: " + path);
                System.out.println("size: " + length);
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
                ServletOutputStream out = response.getOutputStream();
                response.setContentLength((int) length);
                while ((in != null) && ((length = in.read(buf)) != -1)) {
                    out.write(buf, 0, (int) length);
                }
                System.out.println("step3");
                JsfUtil.addSuccessMessage("تم النسخ بنجاح");
                in.close();
                out.close();
                System.out.println("Backup created successfully");
                return "index";
            } else {
                System.out.println("Could not create the backup");
                JsfUtil.addErrorMessage("لم يتم النسخ، المرجو المحاولة مرة أخرى");
            }
        } catch (IOException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "#";
    }

    public String downloadFile(Backup b) {
        try {
            //String folder = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/bak");
            System.out.println("file: " + b.getFile());
            String path = folder + b.getFile();
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) ec.getResponse();
            HttpServletRequest request = (HttpServletRequest) ec.getRequest();
            request.getParameter("file");
            response.setContentType("text/plain");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + b.getFile() + "\"");
            byte[] buf = new byte[1024];
            File f = new File(path);
            long length = f.length();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
            ServletOutputStream out = response.getOutputStream();
            response.setContentLength((int) length);
            while ((in != null) && ((length = in.read(buf)) != -1)) {
                out.write(buf, 0, (int) length);
            }
            in.close();
            out.close();
            return "index";
        } catch (IOException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "#";
    }

    public String uploadFile() {
        System.out.println("step1");
        
        System.out.println("step3");
        return "#";
    }

    public boolean restoreDB(Backup b) {

        System.out.println("restore file: " + b.getFile());
        System.out.println("restore date: " + b.getDate());

        String dbUserName = "";
        String dbPassword = "";
        String source = "";
        String[] executeCmd = new String[]{"mysql", "--user=" + dbUserName, "--password=" + dbPassword, "-e", "source " + source};

        Process runtimeProcess;
        try {

            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                System.out.println("Backup restored successfully");
                return true;
            } else {
                System.out.println("Could not restore the backup");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public String removeItem(Backup b) {

        System.out.println("remove file: " + b.getFile());
        System.out.println("remove date: " + b.getDate());
        try {
            File fXmlFile = new File(folder + "backup.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(folder + "backup.xml");

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer tFormer = tFactory.newTransformer();
            //Element element = (Element) doc.getElementsByTagName("backup").item(0);

            NodeList nList = doc.getElementsByTagName("backup");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("element: " + eElement.getElementsByTagName("date").item(0).getTextContent());
                    if (eElement.getElementsByTagName("date").item(0).getTextContent().equals(b.getDate())
                            && eElement.getElementsByTagName("file").item(0).getTextContent().equals(b.getFile())) {
                        System.out.println("found");
                        File f = new File(folder + eElement.getElementsByTagName("file").item(0).getTextContent());
                        f.delete();
                        nNode.getParentNode().removeChild(nNode);
                    }
                }
            }


            //  Remove the node
            //element.getParentNode().removeChild(element);
            //  Normalize the DOM tree to combine all adjacent nodes
            doc.normalize();
            Source source = new DOMSource(doc);
            Result dest = new StreamResult(fXmlFile);
            tFormer.transform(source, dest);
        } catch (TransformerException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    public String zipAll() {
        /*List<Backup> backups = readFromXml();
        byte[] buffer = new byte[1024];

        try {
            File f = new File(folder + "bacups.zip");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream fos = new FileOutputStream(folder + "bacups.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (Iterator<Backup> it = backups.iterator(); it.hasNext();) {
                Backup backup = it.next();
                ZipEntry ze = new ZipEntry(backup.getFile());
                zos.putNextEntry(ze);
                FileInputStream in = new FileInputStream(folder + backup.getFile());

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
                zos.closeEntry();
            }
            //remember close it
            zos.close();
            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
        System.out.println("zip");
        return "index";
    }

    public void addToXML(Backup b) {
        try {
            String filepath = folder + "backup.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);

            // Get the root element
            Node backups = doc.getFirstChild();
            Element backup = doc.createElement("backup");
            Element date = doc.createElement("date");
            date.appendChild(doc.createTextNode(b.getDate()));
            Element file = doc.createElement("file");
            file.appendChild(doc.createTextNode(b.getFile()));
            backup.appendChild(date);
            backup.appendChild(file);
            backups.appendChild(backup);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);

            System.out.println("Done");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    public List<Backup> readFromXml() {
        List<Backup> backups = new ArrayList<Backup>();
        try {
            File fXmlFile = new File(folder + "backup.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);


            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("backup");

            Backup b;
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                b = new Backup();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    b.setDate(eElement.getElementsByTagName("date").item(0).getTextContent());
                    b.setFile(eElement.getElementsByTagName("file").item(0).getTextContent());
                }
                backups.add(b);
            }
        } catch (SAXException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return backups;
    }

    public class Backup {

        String date;
        String file;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }
}
