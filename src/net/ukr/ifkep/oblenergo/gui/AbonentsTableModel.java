package net.ukr.ifkep.oblenergo.gui;

import java.util.List;

import net.ukr.ifkep.oblenergo.domain.Abonents;

import javax.swing.table.AbstractTableModel;

public class AbonentsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -2677658636846257452L;

	private String[] columns = new String[] { "Особовий рахунок", "Прізвище",
			"Дата Народження", "Стать", "Тип населеного пункту", "Назва населеного пункту",
			"Адреса","Телефон" };

	private List<Abonents> abonents;

	public AbonentsTableModel(List<Abonents> abonents) {
		this.abonents = abonents;
	}

	public void addAbonent(Abonents abonent) {
		abonents.add(abonent);
		fireTableRowsInserted(0,abonents.size());
	}

	public Abonents getRowAbonent(int rowIndex) {
		return abonents.get(rowIndex);
	}

	public void removeRow(int rowIndex) {
		abonents.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public void refreshUpdatedTable() {
		fireTableRowsUpdated(0, abonents.size());
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Abonents abn = abonents.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return Integer.toString(abn.getId());
		case 1:
			return abn.getSurname();
		case 2:
			return abn.getName();
		case 3:
			return abn.getBirth();
		case 4:
			return abn.getSex();
		case 5:
			return abn.getTypeLocality();
		case 6:
			return abn.getNameLocality();
		case 7:
			return abn.getAddress();
		case 8:
			return abn.getTelephone();
		}
		return "";
	}

	public int getRowCount() {
		return abonents.size();
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
