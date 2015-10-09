package tr.gov.tubitak.ingress.controller.common;

import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import tr.gov.tubitak.ingress.model.Citizen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by melis on 07/07/15.
 */
public class CommonUtils {
    public static Image generateBarcode(String ingressID) {
        Barcode128 code128 = new Barcode128();
        code128.setCodeType(Barcode.UPCE);
        code128.setCode(ingressID);

        return code128.createAwtImage(Color.BLACK, Color.WHITE);
    }

    public static String generateTurkishReport(Citizen citizen, String directoryPath) {
        Map<String, Object> parameterValues = fillParameters(citizen);

        JasperPrint turkishReport = getJasperPrint(Constants.TURKISH_TEMPLATE,parameterValues);
        JasperPrint englishReportForTurkish = getJasperPrint(Constants.ENGLISH_TEMPLATE_FOR_TURKISH_SIGNERS,parameterValues);

        java.util.List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        jasperPrintList.add(turkishReport);
        jasperPrintList.add(englishReportForTurkish);

        String fileName = citizen.getIngressID() + "_TR";
        generateReport(jasperPrintList, directoryPath, fileName);

        return fileName;

    }

    public static String generateEnglishReport(Citizen citizen, String directoryPath) {
        Map<String, Object> parameterValues = fillParameters(citizen);

        JasperPrint englishReport = getJasperPrint(Constants.ENGLISH_TEMPLATE,parameterValues);

        java.util.List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
        jasperPrintList.add(englishReport);

        String fileName = citizen.getIngressID() + "_ENG";
        generateReport(jasperPrintList, directoryPath, fileName);

        return fileName;

    }

    public static Map<String, Object> fillParameters(Citizen citizen) {
        Map<String, Object> parameterValues = new HashMap<String, Object>();
        parameterValues.put("paramName", citizen.getName());
        parameterValues.put("paramSurname", citizen.getSurname());
        parameterValues.put("paramAge", citizen.getAge());
        parameterValues.put("paramGender", citizen.getGender().toString());
        if ("FEMALE".equalsIgnoreCase(citizen.getGender().toString())){
            parameterValues.put("paramCinsiyet", "KADIN");
        }else{
            parameterValues.put("paramCinsiyet", "ERKEK");
        }


        Image barcode = CommonUtils.generateBarcode(citizen.getIngressID());
        parameterValues.put("paramBarcode", barcode);
        parameterValues.put("paramIngressID", citizen.getIngressID());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        parameterValues.put("paramDate", dateFormat.format(date).toString());

        BufferedImage image = null;
        try {
            image = ImageIO.read(CommonUtils.class.getResource("/ReportTemplate/ingressLogo.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        parameterValues.put("paramLogo", image );

        return parameterValues;
    }


    public static JasperPrint getJasperPrint(String template, Map<String, Object> parameterValues) throws RuntimeException {
        JasperReport report = null;
        try {
            report = JasperCompileManager.compileReport(CommonUtils.class.getResourceAsStream(template));
        } catch (JRException e) {
            e.printStackTrace();
        }
        JRDataSource source = new JREmptyDataSource();
        try {
            return JasperFillManager.fillReport(report, parameterValues, source);
        } catch (JRException ex) {
            System.err.println("Error " + ex.getMessage() + " " + ex.getLocalizedMessage());
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void generateReport(java.util.List jasperPrintList,String directoryPath,String fileName) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        //Add the list as a Parameter
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
        //this will make a bookmark in the exported PDF for each of the reports
        exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(directoryPath + File.separator + fileName + ".pdf"));
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,"Cp1254");
        try {
            exporter.exportReport();
        } catch (JRException ex) {
            System.err.println("Error " + ex.getMessage() + " " + ex.getLocalizedMessage());
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void openReport(Citizen citizen, String formLanguage) throws RuntimeException {
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        System.out.println("OS current temporary directory is " + tempDir);

        String fileName  = "";
        if ("TURKISH".equalsIgnoreCase(formLanguage)){
            fileName  = generateTurkishReport(citizen,tempDir);
        } else{
            fileName  = generateEnglishReport(citizen,tempDir);
        }
        File file = new File(tempDir + File.separator + fileName + ".pdf");

        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }


    }
}
