package net.ukr.ifkep.oblenergo.dao;

public class test {

	public static void main(String[] args) {
		AbonentsDAO dao = new AbonentsDAO();
		
		try {
			dao.findById(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
