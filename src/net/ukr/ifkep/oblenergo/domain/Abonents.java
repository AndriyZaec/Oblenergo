package net.ukr.ifkep.oblenergo.domain;

public class Abonents extends DomainType {
	
	private String Surname;
	private String Name;
	private String Birth;
	private String Sex;
	private String TypeLocality;
	private String NameLocality;
	private String Address;
	private String Telephone;
	
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return Surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		Surname = surname;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return the birth
	 */
	public String getBirth() {
		return Birth;
	}
	/**
	 * @param birth the birth to set
	 */
	public void setBirth(String birth) {
		Birth = birth;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return Sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		Sex = sex;
	}
	/**
	 * @return the typeLocality
	 */
	public String getTypeLocality() {
		return TypeLocality;
	}
	/**
	 * @param typeLocality the typeLocality to set
	 */
	public void setTypeLocality(String typeLocality) {
		TypeLocality = typeLocality;
	}
	/**
	 * @return the nameLocality
	 */
	public String getNameLocality() {
		return NameLocality;
	}
	/**
	 * @param nameLocality the nameLocality to set
	 */
	public void setNameLocality(String nameLocality) {
		NameLocality = nameLocality;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return Address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		Address = address;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return Telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		Telephone = telephone;
	}

}
