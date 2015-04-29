package net.ukr.ifkep.oblenergo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ukr.ifkep.oblenergo.domain.Payments;

public class PaymentDAO {
	
	private static final String INSERT_QUERY = "insert into oblenergo.`оплата`"
			+ "(`Дата оплати`, `Дата останньої оплати`, `Поточний показник лічильника`, `Останній показник лічильника`, "
			+ "`Ліміт споживання`,`Перевикористання ліміту`,"
			+ "`Борг`,`Сума до оплати`,`Платник`) values (?, ?, ?, ?, ?, ?, ?, ? ,?)";
	private static final String UPDATE_QUERY = "update oblenergo.`оплата` "
			+ "set `Дата оплати` = ?, `Дата останньої оплати` = ?, `Поточний показник лічильника` = ?, `Останній показник лічильника` = ?, `Ліміт споживання` = ?, "
			+ "`Перевикористання ліміту`= ?, `Борг`= ?, `Сума до оплати`= ?, `Платник` = ? "
			+ "where  id = ?";
	private static final String DELETE_QUERY = "delete from oblenergo.`оплата` where  `id` = ?";
	private static final String SELECT_QUERY = "select   `id`, `Дата оплати`, `Дата останньої оплати`, `Поточний показник лічильника`, `Останній показник лічильника`,"
			+ "`Ліміт споживання`,	`Перевикористання ліміту`,	`Борг`,`Сума до оплати`,`Платник` "
			+ "from oblenergo.`оплата` where   `id` = ?";
	private static final String SELECT_ALL_QUERY = "select   `id`, `Дата оплати`, `Дата останньої оплати`, `Поточний показник лічильника`, `Останній показник лічильника`,"
			+ "`Ліміт споживання`,	`Перевикористання ліміту`,	`Борг`,`Сума до оплати`,CONCAT(`Прізвище`,' ',`Ім'я`)  "
			+ "from oblenergo.`оплата`,oblenergo.`абоненти` where `оплата`.`Платник` = `абоненти`.`Особовий рахунок` ";
	private static final String SELECT_BY_ABONENT_QUERY = "select   `id`, `Дата оплати`, `Дата останньої оплати`, `Поточний показник лічильника`, `Останній показник лічильника`,"
			+ "`Ліміт споживання`,	`Перевикористання ліміту`,	`Борг`,`Сума до оплати`,CONCAT(`Прізвище`,' ',`Ім'я`) "
			+ "from oblenergo.`оплата`,oblenergo.`абоненти` where `Платник` = ? and `оплата`.`Платник` = `абоненти`.`Особовий рахунок`";
	

	public int insertPayment(Payments payment) throws Exception {

		Connection connection = AccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
				Statement.RETURN_GENERATED_KEYS);

		try {
			statement.setString(1, payment.getPaymentDate());
			statement.setString(2, payment.getCurPayment());
			statement.setFloat(3, payment.getCurIndex());
			statement.setFloat(4, payment.getLastIndex());
			statement.setFloat(5, payment.getLimit());
			statement.setFloat(6, payment.getExcessLimit());
			statement.setDouble(7, payment.getDebt());
			statement.setDouble(8, payment.getPrice());
			statement.setInt(9, payment.getPayer());
			
			statement.executeUpdate();

			return AccessUtil.getNewRowKey(statement);
		} finally {
			AccessUtil.close(connection);
		}
	}

	public void updatePayments(Payments payment) throws Exception {
		Connection connection = AccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);

		try {
			statement.setString(1, payment.getPaymentDate());
			statement.setString(2, payment.getCurPayment());
			statement.setFloat(3, payment.getCurIndex());
			statement.setFloat(4, payment.getLastIndex());
			statement.setFloat(5, payment.getLimit());
			statement.setFloat(6, payment.getExcessLimit());
			statement.setDouble(7, payment.getDebt());
			statement.setDouble(8, payment.getPrice());
			statement.setInt(9, payment.getPayer());
			statement.setInt(10, payment.getId());

			statement.executeUpdate();
		} finally {
			AccessUtil.close(connection);
		}
	}

	public void deletePayment(int Id) throws Exception {
		Connection connection = AccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

		try {
			statement.setInt(1, Id);
			statement.executeUpdate();
		} finally {
			AccessUtil.close(connection);
		}
	}

	public Payments findById(int Id) throws Exception {
		Connection connection = AccessUtil.createConnection();
		PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);

		try {
			statement.setInt(1, Id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return getPaymentFromRow(rs);
			}
			return null;
		} finally {
			AccessUtil.close(connection);
		}
	}

	public List<Payments> findAll() throws Exception {
		Connection connection = AccessUtil.createConnection();
		PreparedStatement statement = connection
				.prepareStatement(SELECT_ALL_QUERY);

		try {
			ResultSet rs = statement.executeQuery();
			List<Payments> result = new ArrayList<Payments>();
			while (rs.next()) {
				result.add(getPaymentFromRow(rs));
			}
			return result;
		} finally {
			AccessUtil.close(connection);
		}
	}
	public List<Payments> findByAbonent(int abonentId) throws Exception {
		Connection connection = AccessUtil.createConnection();
		PreparedStatement statement = connection
				.prepareStatement(SELECT_BY_ABONENT_QUERY);

		try {
			statement.setInt(1, abonentId);
			ResultSet rs = statement.executeQuery();
			List<Payments> result = new ArrayList<Payments>();
			while (rs.next()) {
				result.add(getPaymentFromRow(rs));
			}
			return result;
		} finally {
			AccessUtil.close(connection);
		}
	}

	private static Payments getPaymentFromRow(ResultSet rs) throws Exception {
		Payments Payment = new Payments();
		
		Payment.setId(rs.getInt(1));
		Payment.setPaymentDate(rs.getString(2));
		Payment.setCurPayment(rs.getString(3));
		Payment.setCurIndex(rs.getFloat(4));
		Payment.setLastIndex(rs.getFloat(5));
		Payment.setLimit(rs.getFloat(6));
		Payment.setExcessLimit(rs.getFloat(7));
		Payment.setDebt(rs.getDouble(8));
		Payment.setPrice(rs.getDouble(9));
		Payment.setPayer(rs.getInt(9));

		return Payment;
	}

}
