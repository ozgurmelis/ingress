import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tr.gov.tubitak.ingress.controller.common.CommonUtils;
import tr.gov.tubitak.ingress.controller.common.enumeration.FormLanguageType;
import tr.gov.tubitak.ingress.controller.common.enumeration.GenderType;
import tr.gov.tubitak.ingress.controller.persistence.CitizenService;
import tr.gov.tubitak.ingress.model.Citizen;
import tr.gov.tubitak.ingress.model.dao.CitizenDao;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by melis on 06/07/15.
 */
public class IngressTests {
    public static CitizenDao citizenDao;
    public static ClassPathXmlApplicationContext context;

    @BeforeClass
    public static void firstSetUp() {

        context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        citizenDao = context.getBean(CitizenDao.class);
    }

    @Test
    public void testCreateCitizen() {
        String name = "MelisOzgur";
        String surName = "CetinkayaDemir";
        String ingressId = "1";

        Citizen citizen = new Citizen(name, surName, ingressId, GenderType.FEMALE, 28, FormLanguageType.TURKISH);

        citizenDao.save(citizen);

        Citizen citizen2 = citizenDao.findByIngressID(ingressId).get(0);
        System.out.println("Citizen Added! Ingress ID " + citizen2.getIngressID());
    }

    @Test
    public void testUpdateCitizen() {
        String surName = "CetinkayaDemir";
        Citizen citizen = citizenDao.findBySurname(surName).get(0);

        // Update Person
        citizen.setSurname("Cetinkaya");
        citizenDao.save(citizen);

        System.out.println("Citizen Updated!");
    }

    @Test
    public void testCitizenService() {
        String name = "Emre";
        String surName = "Demir";

        String ingressId = "";
        try {
            ingressId = CitizenService.getInstance().addCitizen(name, surName,GenderType.MALE,29,FormLanguageType.TURKISH);
            Citizen citizen2 = CitizenService.getInstance().getCitizen(ingressId);
            System.out.println("Citizen Added Using Service! Ingress ID " + citizen2.getIngressID());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testBarcodeGeneration() {
        // step 1
        Document document = new Document(new Rectangle(340, 842));
        // step 2
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream("Deneme.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // step 3
        document.open();
        // step 4
        PdfContentByte cb = writer.getDirectContent();

        String code = "12345678909";
        Barcode128 code128 = new Barcode128();
        code128.setCodeType(Barcode.UPCE);
        code128.setCode(code);

        com.itextpdf.text.Image image = code128.createImageWithBarcode(cb, null, null);

        try {
            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    @Test
    public void testJasperReportsGeneration() {
        //ReportTemplate/ingressReportTemplate.jrxml
        FileOutputStream fos=null;
        ObjectOutputStream oos=null;
        try {
            Map<String, Object> parameterValues = new HashMap<String, Object>();
            parameterValues.put("paramName","Melis");
            parameterValues.put("paramSurname", "Demir");
            parameterValues.put("paramGender", GenderType.FEMALE.toString());
            parameterValues.put("paramAge", 28);
            parameterValues.put("paramBarcode","EMPTY");
            parameterValues.put("paramDate","06/10/2015");

            JRDataSource source = new JREmptyDataSource();
            JasperPrint jasperPrint=JasperFillManager.fillReport("ReportTemplate/ingressReportTemplate.jasper",parameterValues,source);
            JRExporter exporter=new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File("Deneme.pdf"));
            exporter.exportReport();
            System.out.print("ok");
        }
        catch (  JRException ex) {
            System.err.println("Error " + ex.getMessage() + " "+ ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    @Test
    public void testJasperReportsGenerationWithBarcode() {
        FileOutputStream fos=null;
        ObjectOutputStream oos=null;
        try {
            Map<String, Object> parameterValues = new HashMap<String, Object>();
            parameterValues.put("paramName","Melis");
            parameterValues.put("paramSurname", "Demir");
            parameterValues.put("paramAge",28);
            parameterValues.put("paramGender",GenderType.FEMALE.toString());
            parameterValues.put("paramDate","06/10/2015");

            Image barcode = CommonUtils.generateBarcode("12345678909");
            parameterValues.put("paramBarcode",barcode);
            parameterValues.put("paramIngressID","12345678909");

            JRDataSource source = new JREmptyDataSource();
            JasperPrint jasperPrint=JasperFillManager.fillReport("ReportTemplate/ingressReportTemplate.jasper",parameterValues,source);
            JRExporter exporter=new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File("Deneme.pdf"));
            exporter.exportReport();
        }
        catch (  JRException ex) {
            System.err.println("Error " + ex.getMessage() + " "+ ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }


    @AfterClass
    public static void lastTearDown() {
        context.close();

    }

}
