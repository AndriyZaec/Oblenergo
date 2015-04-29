package net.ukr.ifkep.oblenergo.gui;

import java.util.List;

import net.ukr.ifkep.oblenergo.domain.Payments;

import javax.swing.table.AbstractTableModel;

public class PaymentsTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = -2677658636846257452L;

	private String[] columns = new String[] { "№", "Дата оплати","Дата останньої оплати",
			"Поточний показник лічильника", "Останній показник лічильника", "Ліміт споживання", 
			"Перевикористання ліміту","Борг","Сума до оплати" };

	private List<Payments> payments;

	public PaymentsTableModel(List<Payments> payments) {
		this.payments = payments;
	}

	public void addPayment(Payments payment) {
		payments.add(payment);
		fireTableRowsInserted(0,payments.size());
	}

	public Payments getRowPayments(int rowIndex) {
		return payments.get(rowIndex);
	}

	public void removeRow(int rowIndex) {
		payments.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void refreshUpdatedTable() {
		fireTableRowsUpdated(0, payments.size());
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Payments pay = payments.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return Integer.toString(pay.getId());
		case 1:
			return pay.getPaymentDate();
		case 2:
			return pay.getCurPayment();
		case 3:
			return pay.getCurIndex();
		case 4:
			return pay.getLastIndex();
		case 5:
			return pay.getLimit();
		case 6:
			return pay.getExcessLimit();
		case 7:
			return pay.getDebt();
		case 8:
			return pay.getPrice();
		case 9:
			return pay.getPayer();
		}
		return "";
	}

	public int getRowCount() {
		return payments.size();
	}

	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	public int getColumnCount() {
		return columns.length;
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

}
