package net.ukr.ifkep.oblenergo.dao;

import net.ukr.ifkep.oblenergo.domain.Abonents;

public class test {

	public static void main(String[] args) throws Exception {
		Abonents abn = new Abonents();
		AbonentsDAO dao = new AbonentsDAO();
			
		abn.setSurname("Ћ€шеник");
		abn.setName("¬≥ктор");
		abn.setBirth("04-09-1996");
		abn.setSex("ч");
		abn.setTypeLocality("м≥сто");
		abn.setNameLocality("≤вано-‘ранк≥вськ");
		abn.setAddress("—имоненка 2/19");
		abn.setTelephone("0664463614");
		
		dao.updateAbonent(2,abn);
	}

}
