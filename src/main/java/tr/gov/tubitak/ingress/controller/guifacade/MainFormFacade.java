package tr.gov.tubitak.ingress.controller.guifacade;

import tr.gov.tubitak.ingress.controller.common.CommonUtils;
import tr.gov.tubitak.ingress.controller.common.enumeration.FormLanguageType;
import tr.gov.tubitak.ingress.controller.common.enumeration.GenderType;
import tr.gov.tubitak.ingress.controller.persistence.CitizenService;
import tr.gov.tubitak.ingress.model.Citizen;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by melis on 08/07/15.
 */
public class MainFormFacade {
    private static MainFormFacade instance;

    public static MainFormFacade getInstance() {
        if (null == instance) {
            instance = new MainFormFacade();
        }
        return instance;
    }

    public static String addCitizen(String name, String surname, String age, String gender,String language) {
        String ingressId = null;

        GenderType genderType = null;
        if ("FEMALE".equalsIgnoreCase(gender)){
            genderType = GenderType.FEMALE;
        }else{
            genderType = GenderType.MALE;
        }

        FormLanguageType formLanguageType = null;
        if ("TURKISH".equalsIgnoreCase(language)){
            formLanguageType = FormLanguageType.TURKISH;
        }else{
            formLanguageType = FormLanguageType.ENGLISH;
        }

        int ageInt = Integer.parseInt(age);

        try {
            ingressId = CitizenService.getInstance().addCitizen(name, surname,genderType,ageInt,formLanguageType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ingressId;
    }

    public static HashMap<String, String> getCitizen(String ingressID) {
        Citizen foundCitizen = null;
        HashMap<String, String> values = new HashMap<String, String>();
        try {
            foundCitizen = CitizenService.getInstance().getCitizen(ingressID);
            if(null == foundCitizen) throw new IOException();

            values.put("name", foundCitizen.getName());
            values.put("surname", foundCitizen.getSurname());
            values.put("age", String.valueOf(foundCitizen.getAge()));

            if (foundCitizen.getGender().equals(GenderType.FEMALE)){
                values.put("gender", "FEMALE");
            }else{
                values.put("gender", "MALE");
            }

            if (foundCitizen.getFormLanguage().equals(FormLanguageType.TURKISH)){
                values.put("formLanguage", "TURKISH");
            }else{
                values.put("formLanguage", "ENGLISH");
            }

        } catch (IOException e) {
            values.put("error","No result!");
        }

        return values;
    }

    public static String updateCitizen(String ingressID, String name, String surname, String age, String gender, String formLanguage) {

        int ageInt = Integer.parseInt(age);

        GenderType genderType = null;
        if ("FEMALE".equalsIgnoreCase(gender)){
            genderType = GenderType.FEMALE;
        }else{
            genderType = GenderType.MALE;
        }

        FormLanguageType formLanguageType = null;
        if ("TURKISH".equalsIgnoreCase(formLanguage)){
            formLanguageType = FormLanguageType.TURKISH;
        }else{
            formLanguageType = FormLanguageType.ENGLISH;
        }


        try {
            CitizenService.getInstance().updateCitizen(ingressID,name,surname,ageInt,genderType,formLanguageType);
        } catch (IOException e) {
            return "Could not updated!";
        }
        return "The record updated!";
    }

    public static String deleteCitizen(String ingressID) {
        try {
            CitizenService.getInstance().deleteCitizen(ingressID);
        } catch (IOException e) {
            return "Could not deleted!";
        }
        return "The record deleted!";
    }

    public static String downloadReport(String ingressID, String directory, String formLanguage) {
        Citizen foundCitizen = null;
        try {
            foundCitizen = CitizenService.getInstance().getCitizen(ingressID);
        } catch (IOException e) {
            return "The record could not found!";
        }

        if ("TURKISH".equalsIgnoreCase(formLanguage)){
            try {
                CommonUtils.generateTurkishReport(foundCitizen, directory);
            } catch (RuntimeException e) {
                return "The report could not be generated!";
            }
        }else{
            try {
                CommonUtils.generateEnglishReport(foundCitizen, directory);
            } catch (RuntimeException e) {
                return "The report could not be generated!";
            }
        }



        return "The report downloaded!";
    }

    public static String openReport(String ingressID, String formLanguage) {
        Citizen foundCitizen = null;
        try {
            foundCitizen = CitizenService.getInstance().getCitizen(ingressID);
        } catch (IOException e) {
            return "The record could not found!";
        }

        try {
            CommonUtils.openReport(foundCitizen, formLanguage);
        } catch (RuntimeException e) {
            return "The report could not be opened!";
        }

        return "Ok";

    }
}
