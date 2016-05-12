package tr.gov.tubitak.ingress.controller.persistence;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import tr.gov.tubitak.ingress.controller.common.Constants;
import tr.gov.tubitak.ingress.controller.common.enumeration.FormLanguageType;
import tr.gov.tubitak.ingress.controller.common.enumeration.GenderType;
import tr.gov.tubitak.ingress.model.Citizen;
import tr.gov.tubitak.ingress.model.UsedIdPool;
import tr.gov.tubitak.ingress.model.dao.CitizenDao;
import tr.gov.tubitak.ingress.model.dao.UsedIdPoolDao;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by melis on 06/07/15.
 */
public class CitizenService {

    private static CitizenService instance;

    public static CitizenDao citizenDao;
    public static UsedIdPoolDao usedIdPoolDao;
    public static ClassPathXmlApplicationContext context;

    public static CitizenService getInstance() {
        if (null == instance) {
            instance = new CitizenService();

            context = new ClassPathXmlApplicationContext(
                    "applicationContext.xml");

            citizenDao = context.getBean(CitizenDao.class);

            usedIdPoolDao = context.getBean(UsedIdPoolDao.class);
        }
        return instance;
    }

    public String addCitizen(String name, String surname, GenderType gender, int age, FormLanguageType language) throws IOException {
        String generatedIngressId = createIngressID();

        Citizen citizen = new Citizen(name, surname, generatedIngressId,gender,age,language);

        citizenDao.save(citizen);

        Citizen citizen2 = citizenDao.findByIngressID(generatedIngressId).get(0);
        System.out.println("Citizen Added! Ingress ID " + generatedIngressId);

        return citizen2.getIngressID();
    }

    public Citizen getCitizen(String ingressId) throws IOException {
        Citizen citizen = null;

        List<Citizen> foundList = citizenDao.findByIngressID(ingressId);
        if(null != foundList && foundList.size()>0){
            citizen = foundList.get(0);
        }

        return citizen;
    }

    public void updateCitizen(String ingressId,String newName,String newSurname, int newAge, GenderType newGender, FormLanguageType newLanguage) throws IOException {
        Citizen citizen = getCitizen(ingressId);

        if(null == citizen) throw new IOException();

        // Update Person
        citizen.setName(newName);
        citizen.setSurname(newSurname);
        citizen.setAge(newAge);
        citizen.setGender(newGender);
        citizen.setFormLanguage(newLanguage);
        citizenDao.save(citizen);

        System.out.println("Citizen Updated!");
    }

    public void deleteCitizen(String ingressId) throws IOException {
        Citizen citizen = getCitizen(ingressId);

        if(null == citizen) throw new IOException();

        citizenDao.delete(citizen);

        List<UsedIdPool> iDs = usedIdPoolDao.findByIngressID(Long.parseLong(ingressId));
        if (1 == iDs.size()){
            usedIdPoolDao.delete(iDs.get(0));
        }

        System.out.println("Citizen Deleted!");
    }

    private String createIngressID() {

        Random random = new Random();

        boolean isUsedBefore = true;
        long number = 0;
        while (isUsedBefore) {
            number = Constants.MIN_VALUE_FOR_ID+((long)(random.nextDouble()*(Constants.MAX_VALUE_FOR_ID-Constants.MIN_VALUE_FOR_ID)));
            System.out.println(number);
            isUsedBefore = isGeneratedIdUsedBefore(number);
        }

        UsedIdPool usedIdPool = new UsedIdPool(number);
        usedIdPoolDao.save(usedIdPool);

        return String.valueOf(number);
    }

    private boolean isGeneratedIdUsedBefore(long generatedId) {
        List<UsedIdPool> iDs = usedIdPoolDao.findByIngressID(generatedId);
        if (1 == iDs.size()) return true;
        return false;

    }

}
