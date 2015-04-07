package net.ukr.ifkep.oblenergo.dao;

import net.ukr.ifkep.oblenergo.domain.Abonents;

public class test {

	public static void main(String[] args) throws Exception {
		AbonentsDAO dao = new AbonentsDAO();
		Abonents abn = new Abonents();
		
		abn.setSurname("Ляшеник");
		abn.setName("Віктор");
		abn.setBirth("04.09.1996");
		abn.setSex("ч");
		abn.setTypeLocality("місто");
		abn.setNameLocality("Івано-Франківськ");
		abn.setAddress("Вороного 4/8");
		abn.setTelephone("0664463614");
		
		dao.insertAbonent(abn);
	}

}
