package net.ukr.ifkep.oblenergo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import net.ukr.ifkep.oblenergo.dao.PaymentDAO;
import net.ukr.ifkep.oblenergo.domain.Abonents;
import net.ukr.ifkep.oblenergo.domain.Payments;

public class AbonentsPaymentForm extends JDialog implements ActionListener {

	private static final long serialVersionUID = -6237172566791582262L;
	private JButton cmdClose;
	private JButton cmdaddPayment;
	private JButton cmdupdatePayment;
	private JButton cmdDeleteStudent;
	private JButton cmdprintPayment;
	private JTable paymentTable;

	private NewPayment newPay = new NewPayment();
	JPopupMenu popupMenu = new JPopupMenu();

	JButton bnew, bupdate, bremove, bprint, bclose, cmdKmNew, cmdKmUpdate,
			cmdKmRemove, cmdKmPrint, cmdKmClose;

	private PaymentsTableModel  ptm;

	private Abonents abonent;

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addPayment) {
			addPayment();
		} else if (e.getSource() == updatePayment) {
			updatePayment();
		} else if (e.getSource() == removePayment) {
			removePayment();
		} else if (e.getSource() == printPayment) {
			printPayment();
		} else if (e.getSource() == onClose) {
			onClose();
		}
	}

	JMenuItem addPayment, updatePayment, removePayment, printPayment, onClose;

	void createMenu() {
		Color colorMenu = (Color.ORANGE);
		Font fontMenu = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		JMenuBar MenuBar = new JMenuBar();

		JMenu mFile = new JMenu("Файл");
		mFile.setFont(fontMenu);
		MenuBar.setBackground(colorMenu);

		ImageIcon icon = new ImageIcon("img/new.gif");
		addPayment = new JMenuItem("Додати студента", icon);
		addPayment.setToolTipText("Добавити новий запис до бази");
		addPayment.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		addPayment.setFont(fontMenu);
		addPayment.addActionListener(this);
		mFile.add(addPayment);

		ImageIcon icon3 = new ImageIcon("img/update.gif");
		updatePayment = new JMenuItem("Внести зміни", icon3);
		updatePayment.setToolTipText("Внести зміни у вже створений запис");
		updatePayment.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.CTRL_MASK));
		updatePayment.setFont(fontMenu);
		updatePayment.addActionListener(this);
		mFile.add(updatePayment);

		ImageIcon icon2 = new ImageIcon("img/remote.gif");
		removePayment = new JMenuItem("Видалити студента", icon2);
		removePayment.setToolTipText("Видалити запис у базі");
		removePayment.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.SHIFT_MASK));
		removePayment.setFont(fontMenu);
		removePayment.addActionListener(this);
		mFile.add(removePayment);

		mFile.addSeparator();

		ImageIcon icon7 = new ImageIcon("img/print.gif");
		printPayment = new JMenuItem("Вивести на друк", icon7);
		printPayment.setToolTipText("Роздрукувати інформацію з таблиці");
		printPayment.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		printPayment.setFont(fontMenu);
		printPayment.addActionListener(this);
		mFile.add(printPayment);

		mFile.addSeparator();

		ImageIcon icon4 = new ImageIcon("img/onclose.gif");
		onClose = new JMenuItem("Закрити вікно", icon4);
		onClose.setToolTipText("Перехід до головної форми");
		onClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.ALT_MASK));
		onClose.setFont(fontMenu);
		onClose.addActionListener(this);
		mFile.add(onClose);

		MenuBar.add(mFile);
		setJMenuBar(MenuBar);

	}

	public AbonentsPaymentForm(Abonents a) throws IOException {
		this.abonent = a;
		setTitle("Оплата абонента " + a.getSurname()+" "+a.getName());

		createMenu();
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			
		}catch(Exception e){
			
		}

		ImageIcon kmNewicon = new ImageIcon("img/new.gif");
		JMenuItem cmdKmNew = new JMenuItem("Новий запис", kmNewicon);
		cmdKmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		cmdKmNew.addActionListener(this);
		popupMenu.add(cmdKmNew);

		cmdKmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPayment();
			}
		});

		ImageIcon kmUpdateicon = new ImageIcon("img/update.gif");
		JMenuItem cmdKmUpdate = new JMenuItem("Редагувати запис", kmUpdateicon);
		cmdKmUpdate.addActionListener(this);
		cmdKmUpdate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.CTRL_MASK));
		popupMenu.add(cmdKmUpdate);

		cmdKmUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePayment();
			}
		});
		popupMenu.addSeparator();
		ImageIcon kmRemoveicon = new ImageIcon("img/remote.gif");
		JMenuItem cmdKmRemove = new JMenuItem("Видалити запис", kmRemoveicon);
		cmdKmRemove.addActionListener(this);
		cmdKmRemove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.SHIFT_MASK));
		popupMenu.add(cmdKmRemove);

		cmdKmRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePayment();
			}
		});

		JToolBar tools = new JToolBar();
		Color ColorBar = Color.GRAY;
		tools.setBackground(ColorBar);

		bnew = new JButton(new ImageIcon("img/new.gif"));
		bnew.setToolTipText("Новий запис");
		tools.add(bnew);

		bnew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPayment();
			}
		});

		bupdate = new JButton(new ImageIcon("img/update.gif"));
		bupdate.setToolTipText("Редагувати запис");
		tools.add(bupdate);

		bupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePayment();
			}
		});

		bremove = new JButton(new ImageIcon("img/remote.gif"));
		bremove.setToolTipText("Видалити запис");
		tools.add(bremove);

		bremove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePayment();
			}
		});
		bprint = new JButton(new ImageIcon("img/print.gif"));
		bprint.setToolTipText("Роздрукувати інформацію");
		tools.add(bprint);

		bprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printPayment();
			}
		});

		bclose = new JButton(new ImageIcon("img/onclose.gif"));
		bclose.setToolTipText("Закрити програму");
		tools.add(bclose);

		bclose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClose();
			}
		});

		cmdClose = new JButton("Закрити");
		cmdaddPayment = new JButton("Додати");
		cmdupdatePayment = new JButton("Редагувати");
		cmdDeleteStudent = new JButton("Видалити");
		cmdprintPayment = new JButton("Вивести на друк");
		
		ptm = getTableModel(a);
		paymentTable = new JTable(ptm);
		paymentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		paymentTable
				.setPreferredScrollableViewportSize(new Dimension(920, 180));
		paymentTable.getColumnModel().getColumn(0).setMinWidth(10);
		paymentTable.getColumnModel().getColumn(1).setMinWidth(30);
		paymentTable.getColumnModel().getColumn(2).setMinWidth(40);
		paymentTable.getColumnModel().getColumn(3).setMinWidth(40);
		paymentTable.getColumnModel().getColumn(4).setMinWidth(20);
		paymentTable.getColumnModel().getColumn(5).setMinWidth(20);
		paymentTable.getColumnModel().getColumn(6).setMinWidth(20);
		paymentTable.getColumnModel().getColumn(7).setMinWidth(20);
		paymentTable.getColumnModel().getColumn(8).setMinWidth(40);
		paymentTable.setGridColor(Color.ORANGE);
		paymentTable.setRowHeight(20);
		Font FontGrid = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		paymentTable.setFont(FontGrid);
		
		JScrollPane scrollPane = new JScrollPane(paymentTable);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		
		MainFonClass mainPanel = new MainFonClass("img/pay.jpg");
		mainPanel.add(scrollPane);
		
		JPanel commandsPanel = new JPanel(new FlowLayout());
		commandsPanel.add(cmdaddPayment);
		commandsPanel.add(cmdupdatePayment);
		commandsPanel.add(cmdDeleteStudent);
		commandsPanel.add(cmdprintPayment);
		commandsPanel.add(cmdClose);
		commandsPanel.setOpaque(false);
		mainPanel.add(commandsPanel);

		getRootPane().setDefaultButton(cmdClose);
		setSize(950, 350);
		setResizable(false);
		getContentPane().add(tools, BorderLayout.NORTH);
		getContentPane().add(mainPanel, BorderLayout.CENTER);

		paymentTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.isPopupTrigger())
					popupMenu.show(me.getComponent(), me.getX(), me.getY());
			}

			public void mouseReleased(MouseEvent me) {
				if (me.isPopupTrigger())
					popupMenu.show(me.getComponent(), me.getX(), me.getY());
			}
		});

		cmdaddPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPayment();
			}
		});

		cmdupdatePayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePayment();
			}
		});

		cmdDeleteStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePayment();
			}
		});
		cmdprintPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printPayment();
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

		mainPanel.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClose();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private PaymentsTableModel getTableModel(Abonents a) {
		try {
			PaymentDAO dao = new PaymentDAO();
			final List<Payments> payments = dao.findByAbonent(a.getId());

			return new PaymentsTableModel(payments);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(
					this,
					"Помилка при заповненні таблиці оплати: "
							+ e.getMessage());
		}
		return new PaymentsTableModel(new ArrayList<Payments>(0));
	}

	private void onClose() {
		dispose();
	}

	private void addPayment() {
		Payments payment = new Payments();
		payment.setPayer(this.abonent.getId());
		newPay.setPayments(payment);
		newPay.setVisible(true);
		if (newPay.getPayments().getId() != null) {
			ptm.addPayment(newPay.getPayments());
		}
	}

	private void updatePayment() {
		int index = paymentTable.getSelectedRow();
		if (index == -1)
			return;

		Payments pay = ptm.getRowPayments(index);
		if (pay != null) {
			newPay.setPayments(pay);
			newPay.setVisible(true);
			ptm.refreshUpdatedTable();
		}
	}

	private void removePayment() {
		if (JOptionPane.showConfirmDialog(AbonentsPaymentForm.this,
				"Ви хочете видалити інформацію про оплату?",
				"Видалення запису про оплату", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			int index = paymentTable.getSelectedRow();
			if (index == -1)
				return;

			try {
				Payments pay = ptm.getRowPayments(index);
				if (pay != null) {
					PaymentDAO dao = new PaymentDAO();
					dao.deletePayment(pay.getId());
					ptm.removeRow(index);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(AbonentsPaymentForm.this,
						e.getMessage());
			}
		}
	}

	private void printPayment() {
		try {
			MessageFormat headerFormat = new MessageFormat("Сторінка {0}");
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			paymentTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat,
					footerFormat);
		} catch (PrinterException pe) {
			System.err.println("Неможливо роздрукувати документ по причині: "
					+ pe.getMessage());
		}
	}
	
}
