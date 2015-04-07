package net.ukr.ifkep.oblenergo.dao;

public class AbonentsDAO {
	
	package net.ukr.ifkep.course_project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ukr.ifkep.course_project.domain.Abonents;

public class AbonentDao {

	private static final String INSERT_QUERY = "insert into oblenergo.`абоненти` "
			+ "(`Прізвище`, `Ім'я`, `Дата народження`, `Стать`, `Тип населеного пункту`,`Назва населеного пункту`"
			+ "`Адреса`,`Номер телефону`) values ('?', '?', '?', '?', '?','?','?','?')";
	private static final String UPDATE_QUERY = "update oblenergo.`абоненти` "
			+ "set Прізвище = ?, `Ім'я` = ?, `Дата народження` = ?, Стать = ?, `Тип населеного пункту` = ? "
			+ "`Назва населеного пункту`=?, Адреса=?, `Номер телефону`= ?"
			+ "where `Особовий рахунок` = ?";
	private static final String DELETE_QUERY = "delete from oblenergo.`абоненти` where `Особовий рахунок` = ?";
	private static final String SELECT_QUERY = "select  `Особовий рахунок`, Прізвище, `Ім'я`, `Дата народження`, Стать,"
			+ "`Тип населеного пункту`,	`Назва населеного пункту`,	Адреса,`Номер телефону` "
			+ "from oblenergo.`абоненти` where  `Особовий рахунок` = ?";
	private static final String SELECT_ALL_QUERY = "select  `Особовий рахунок`, Прізвище, `Ім'я`, `Дата народження`, Стать,"
			+ "`Тип населеного пункту`,	`Назва населеного пункту`,	Адреса,`Номер телефону` "
			+ "from oblenergo.`абоненти` ";

	public int insertAbonent(Abonents abonents) throws Exception {

		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
				Statement.RETURN_GENERATED_KEYS);

		try {
			statement.setString(1, abonents.getSurname());
			statement.setString(2, abonents.getName());
			statement.setString(3, abonents.getBirth());
			statement.setString(4, abonents.getSex());
			statement.setString(5, abonents.getTypeLocality());
			statement.setString(6, abonents.getNameLocality());
			statement.setString(7, abonents.getAddress());
			statement.setString(8, abonents.getTelephone());
			
			statement.executeUpdate();

			return DataAccessUtil.getNewRowKey(statement);
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void updateAbonents(Abonents abonents) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);

		try {
			statement.setString(1, abonents.getSurname());
			statement.setString(2, abonents.getName());
			statement.setString(3, abonents.getBirth());
			statement.setString(4, abonents.getSex());
			statement.setString(5, abonents.getTypeLocality());
			statement.setString(6, abonents.getNameLocality());
			statement.setString(7, abonents.getAddress());
			statement.setString(8, abonents.getTelephone());

			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public void deleteGroup(int Id) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

		try {
			statement.setInt(1, Id);
			statement.executeUpdate();
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public Abonents findById(int Id) throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);

		try {
			statement.setInt(1, Id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return getGroupFromRow(rs);
			}
			return null;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	public List<Abonents> findAll() throws Exception {
		Connection connection = DataAccessUtil.createConnection();
		PreparedStatement statement = connection
				.prepareStatement(SELECT_ALL_QUERY);

		try {
			ResultSet rs = statement.executeQuery();
			List<Abonents> result = new ArrayList<Abonents>();
			while (rs.next()) {
				result.add(getGroupFromRow(rs));
			}
			return result;
		} finally {
			DataAccessUtil.close(connection);
		}
	}

	private static Abonents getGroupFromRow(ResultSet rs) throws Exception {
		Abonents Abonent = new Abonents();
		
		Abonent.setId(rs.getInt(1));
		Abonent.setSurname(rs.getString(2));
		Abonent.setName(rs.getString(3));
		Abonent.setGroupType(rs.getInt(4));
		Abonent.setCourse(rs.getInt(5));
		Abonent.setCountStud(rs.getInt(6));

		return group;
	}
}


}
