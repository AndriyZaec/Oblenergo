package net.ukr.ifkep.oblenergo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


import net.ukr.ifkep.oblenergo.gui.AbonentsTableModel;
import net.ukr.ifkep.oblenergo.dao.AbonentsDAO;
import net.ukr.ifkep.oblenergo.domain.Abonents;

public class DebtorsForm extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 2769668573861082804L;
	private JButton cmdClose;
	private JButton cmdUpdateAbonent;
	private JButton cmdDeleteAbonent;
	private JButton cmdOpenPayments;
	private JButton cmdPrintAbonent;
	private JLabel jLab;
	private JTable abonentsTable;
	
	
	private NewAbonent newAbonent = new NewAbonent();;

	
	
	private AbonentsTableModel abn;
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addAbonent) {
			addAbonent();
		} else if (e.getSource() == updateAbonent) {
			updateAbonent();
		} else if (e.getSource() == removeAbonent) {
			removeAbonent();
		} else if (e.getSource() == printAbonent) {
			printAbonent();
		} else if (e.getSource() == openPayment) {
			try {
				openPayments();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == onClose) {
			onClose();
		}
	}

	JMenuItem addAbonent, updateAbonent, removeAbonent, printAbonent, openPayment,
			openTeacher, onClose;


	public DebtorsForm() {
		super();
		setTitle("Форма боржників");

		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			
		}catch(Exception e){
			
		}


		cmdClose = new JButton("Закрити");
		cmdUpdateAbonent = new JButton("Редагувати");
		cmdDeleteAbonent = new JButton("Видалити");
		cmdOpenPayments = new JButton("Оплата");
		cmdPrintAbonent = new JButton("На друк");
		jLab = new JLabel();

		
		abn = getTableModel();
		abonentsTable = new JTable(abn);
		abonentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		abonentsTable.setPreferredScrollableViewportSize(new Dimension(100, 180));
		abonentsTable.getColumnModel().getColumn(0).setMinWidth(10);
		abonentsTable.getColumnModel().getColumn(1).setMinWidth(60);
		abonentsTable.getColumnModel().getColumn(2).setMinWidth(10);
		abonentsTable.getColumnModel().getColumn(3).setMinWidth(60);
		abonentsTable.getColumnModel().getColumn(4).setMinWidth(60);
		abonentsTable.getColumnModel().getColumn(5).setMinWidth(60);
		abonentsTable.getColumnModel().getColumn(6).setMinWidth(60);
		abonentsTable.getColumnModel().getColumn(7).setMinWidth(60);
		abonentsTable.setGridColor(Color.ORANGE);
		abonentsTable.setRowHeight(20);
		Font FontGrid = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		abonentsTable.setFont(FontGrid);

		JScrollPane scrollPane = new JScrollPane(abonentsTable);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		MainFonClass mainPanel = new MainFonClass("img/pay.jpg");
		mainPanel.add(scrollPane);
		scrollPane.setBackground(Color.BLUE);

		JPanel commandsPanel = new JPanel(new FlowLayout());
		commandsPanel.add(jLab);
		commandsPanel.add(cmdUpdateAbonent);
		commandsPanel.add(cmdDeleteAbonent);
		commandsPanel.add(cmdPrintAbonent);
		commandsPanel.add(cmdOpenPayments);
		commandsPanel.add(cmdClose);
		commandsPanel.setOpaque(false);
		getRootPane().setDefaultButton(cmdClose);
		setSize(1300,370 );
		setResizable(true);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().add(commandsPanel, BorderLayout.SOUTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);


		cmdUpdateAbonent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAbonent();
			}
		});

		cmdDeleteAbonent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAbonent();
			}
		});

		cmdPrintAbonent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printAbonent();
			}
		});

		cmdOpenPayments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openPayments();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		});

		cmdClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClose();
			}
		});

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onClose();
			}
		});
//
//		mainPanel.registerKeyboardAction(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				onClose();
//			}
//		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
//				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private AbonentsTableModel getTableModel() {
		try {
			AbonentsDAO dao = new AbonentsDAO();
			final List<Abonents> abonent = dao.findDebtors();

			return new AbonentsTableModel(abonent);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Проблеми з завантаженням таблиці: " + e.getMessage());
		}
		return new AbonentsTableModel(new ArrayList<Abonents>(0));
	}
	

	private void printAbonent() {
		try {
			MessageFormat headerFormat = new MessageFormat("пїЅпїЅпїЅпїЅпїЅпїЅпїЅ {0}");
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			abonentsTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat,
					footerFormat);
		} catch (PrinterException pe) {
			System.err.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ: "
					+ pe.getMessage());
		}
	}

	private void openPayments() throws Exception {
		int index = abonentsTable.getSelectedRow();
		if (index == -1)
			return;

		Integer id = Integer.parseInt((String) (abonentsTable.getValueAt(index, 0)));
		AbonentsDAO dao = new AbonentsDAO();
		Abonents abonent = dao.findById(id);
		AbonentsPaymentForm AbonentsPaymentForm = new AbonentsPaymentForm(abonent);
		AbonentsPaymentForm.setVisible(true);
	}

	private void onClose() {
		dispose();
	}

	private void addAbonent() {
		newAbonent.setAbonent(new Abonents());
		newAbonent.setVisible(true);
		if (newAbonent.getAbonent().getId() != null) {
			abn.addAbonent(newAbonent.getAbonent());
		}
	}

	private void updateAbonent() {
		int index = abonentsTable.getSelectedRow();
		if (index == -1)
			return;

		Abonents a = abn.getRowAbonent(index);
		if (a != null) {
			newAbonent.setAbonent(a);
			newAbonent.setVisible(true);
			abn.refreshUpdatedTable();
		}
	}
	

	private void removeAbonent() {
		if (JOptionPane.showConfirmDialog(DebtorsForm.this,
				"Ви впевнені, що хочете видалити запис?",
				"Запис видалено", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			int index = abonentsTable.getSelectedRow();
			if (index == -1)
				return;

			try {
				Abonents abonent = abn.getRowAbonent(index);
				if (abonent != null) {

					AbonentsDAO dao = new AbonentsDAO();
					dao.deleteGroup(abonent.getId());
					abn.removeRow(index);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DebtorsForm.this, e.getMessage());
			}
		}
	
	}
}

