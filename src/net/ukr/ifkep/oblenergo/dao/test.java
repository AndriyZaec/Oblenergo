package net.ukr.ifkep.oblenergo.dao;

import net.ukr.ifkep.oblenergo.domain.Abonents;

public class test {

	public static void main(String[] args) throws Exception {
		Abonents abn = new Abonents();
		AbonentsDAO dao = new AbonentsDAO();
			
		abn.setSurname("�������");
		abn.setName("³����");
		abn.setBirth("04-09-1996");
		abn.setSex("�");
		abn.setTypeLocality("����");
		abn.setNameLocality("�����-���������");
		abn.setAddress("��������� 2/19");
		abn.setTelephone("0664463614");
		
		dao.updateAbonent(2,abn);
	}

}
