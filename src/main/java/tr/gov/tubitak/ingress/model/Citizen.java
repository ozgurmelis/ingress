package tr.gov.tubitak.ingress.model;

import tr.gov.tubitak.ingress.controller.common.enumeration.FormLanguageType;
import tr.gov.tubitak.ingress.controller.common.enumeration.GenderType;

import javax.persistence.*;

/**
 * Created by melis on 06/07/15.
 */
@Entity
public class Citizen {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String surname;

    private String ingressID;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    private FormLanguageType formLanguage;

    private int age;

	public Citizen() {
	}

	public Citizen(String name, String surname, String ingressID, GenderType gender, int age, FormLanguageType formLanguage) {
		this.name = name;
		this.surname = surname;
        this.ingressID = ingressID;
        this.gender = gender;
        this.age = age;
        this.formLanguage = formLanguage;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

    public String getIngressID() { return ingressID;}

    public void setIngressID(String ingressID) {this.ingressID = ingressID;}

    public GenderType getGender() {return gender;}

    public void setGender(GenderType gender) { this.gender = gender;}

    public int getAge() {return age;}

    public void setAge(int age) {this.age = age;}

    public FormLanguageType getFormLanguage() { return formLanguage;}

    public void setFormLanguage(FormLanguageType formLanguage) {this.formLanguage = formLanguage;}

    @Override
	public String toString() {
		return "Citizen [ingress ID=" + ingressID + ", name=" + name + ", surname=" + surname
				+ "]";
	}

}
