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
import javax.swing.border.Border;


import net.ukr.ifkep.oblenergo.gui.AbonentsTableModel;
import net.ukr.ifkep.oblenergo.dao.AbonentsDAO;
import net.ukr.ifkep.oblenergo.domain.Abonents;

public class MainForm extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 2769668573861082804L;
	private JButton cmdClose;
	private JButton cmdAddAbonent;
	private JButton cmdUpdateAbonent;
	private JButton cmdDeleteAbonent;
	private JButton cmdOpenPayments;
	private JButton cmdPrintAbonent;
	private JButton cmdVillager;
	private JButton cmdTownsman;
	private JLabel jLab;
	private JTable abonentsTable;
	
	
	private NewAbonent newAbonent = new NewAbonent();
	private Villagers villagers = new Villagers();
	private Townsmans townsmans = new Townsmans();
	private JPopupMenu popupMenu = new JPopupMenu();
	
	JButton bnew, bupdate, bremove, bprint, bclose, bstudent, bteacher,
	cmdKmNew, cmdKmUpdate, cmdKmRemove, cmdKmPrint, cmdKmStudent,
	cmdKmTeacher, cmdKmClose;
	
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

	void createMenu() {
		Color colorMenu = (Color.DARK_GRAY);
		Font fontMenu = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		JMenuBar MenuBar = new JMenuBar();

		JMenu mFile = new JMenu("Файл");
		JMenu mInform = new JMenu("Інформація");
		JMenu mAbout = new JMenu("Про нас");
		//MenuBar.add(Box.createHorizontalGlue());
		mFile.setFont(fontMenu);
		mInform.setFont(fontMenu);
		mAbout.setFont(fontMenu);
		MenuBar.setBackground(colorMenu);

		ImageIcon icon = new ImageIcon("img/add.png");
		addAbonent = new JMenuItem("Додати нового абонента", icon);
		addAbonent.setToolTipText("Форма для внесення інформацію про нового абонента");
		addAbonent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		addAbonent.setFont(fontMenu);
		addAbonent.addActionListener(this);
		mFile.add(addAbonent);

		ImageIcon icon3 = new ImageIcon("img/update.gif");
		updateAbonent = new JMenuItem("Редагувати", icon3);
		updateAbonent.setToolTipText("Редагування інформації про нового абонента");
		updateAbonent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.CTRL_MASK));
		updateAbonent.setFont(fontMenu);
		updateAbonent.addActionListener(this);
		mFile.add(updateAbonent);

		ImageIcon icon2 = new ImageIcon("img/del.png");
		removeAbonent = new JMenuItem("Видалення", icon2);
		removeAbonent.setToolTipText("Видаляє інформацію про абонента");
		removeAbonent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.SHIFT_MASK));
		removeAbonent.setFont(fontMenu);
		removeAbonent.addActionListener(this);
		mFile.add(removeAbonent);

		mFile.addSeparator();

		ImageIcon icon7 = new ImageIcon("img/print.gif");
		printAbonent = new JMenuItem("На друк", icon7);
		printAbonent.setToolTipText("Виведення на друк інформації про абонента");
		printAbonent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		printAbonent.setFont(fontMenu);
		printAbonent.addActionListener(this);
		mFile.add(printAbonent);

		mFile.addSeparator();

		ImageIcon icon6 = new ImageIcon("img/openPayment.gif");
		openPayment = new JMenuItem("Інформація про оплату", icon6);
		openPayment.setToolTipText("Інформація про оплату");
		openPayment.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		openPayment.setFont(fontMenu);
		openPayment.addActionListener(this);
		mInform.add(openPayment);

		mInform.addSeparator();


		ImageIcon icon4 = new ImageIcon("img/onclose.gif");
		onClose = new JMenuItem("Вихід", icon4);
		onClose.setToolTipText("Виходить та закриває програму");
		onClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.ALT_MASK));
		onClose.setFont(fontMenu);
		onClose.addActionListener((ActionListener) this);
		mFile.add(onClose);

		MenuBar.add(mFile);
		MenuBar.add(mInform);
		MenuBar.add(mAbout);
		setJMenuBar(MenuBar);

	}

	public MainForm() {
		super();
		setTitle("Головна форма");

		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			
		}catch(Exception e){
			
		}
		
		
		createMenu();

		ImageIcon kmNewicon = new ImageIcon("img/add.png");
		JMenuItem cmdKmNew = new JMenuItem("Додати", kmNewicon);
		cmdKmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		cmdKmNew.addActionListener(this);
		popupMenu.add(cmdKmNew);

		cmdKmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAbonent();
			}
		});

		ImageIcon kmUpdateicon = new ImageIcon("img/update.gif");
		JMenuItem cmdKmUpdate = new JMenuItem("Редагувати", kmUpdateicon);
		cmdKmUpdate.addActionListener(this);
		cmdKmUpdate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.CTRL_MASK));
		popupMenu.add(cmdKmUpdate);

		cmdKmUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAbonent();
			}
		});
		ImageIcon kmRemoveicon = new ImageIcon("img/del.png");
		JMenuItem cmdKmRemove = new JMenuItem("Видалити", kmRemoveicon);
		cmdKmRemove.addActionListener(this);
		cmdKmRemove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.SHIFT_MASK));
		popupMenu.add(cmdKmRemove);

		cmdKmRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAbonent();
			}
		});

		popupMenu.addSeparator();

		ImageIcon kmStudenticon = new ImageIcon("img/openPayment.gif");
		JMenuItem cmdKmStudent = new JMenuItem("Відкрити інформацію про оплату", kmStudenticon);
		cmdKmStudent.addActionListener(this);
		cmdKmStudent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		popupMenu.add(cmdKmStudent);

		cmdKmStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openPayments();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		});


		JToolBar tools = new JToolBar();
		Color ColorBar = Color.GRAY;
		tools.setBackground(ColorBar);
		tools.setFloatable(false);

		bnew = new JButton(new ImageIcon("img/add.png"));
		bnew.setToolTipText("Додати інформацію про абонента");
		tools.add(bnew);

		bnew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAbonent();
			}
		});

		bupdate = new JButton(new ImageIcon("img/upd.png"));
		bupdate.setToolTipText("Редагувати інформацію про абонента");
		tools.add(bupdate);

		bupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAbonent();
			}
		});

		bremove = new JButton(new ImageIcon("img/del.png"));
		bremove.setToolTipText("Видалити інформацію про абонента");
		tools.add(bremove);

		bremove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAbonent();
			}
		});
		bprint = new JButton(new ImageIcon("img/print.png"));
		bprint.setToolTipText("На друк");
		tools.add(bprint);

		bprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printAbonent();
			}
		});

		bstudent = new JButton(new ImageIcon("img/pay.png"));
		bstudent.setToolTipText("Відкрити оплату ");
		tools.add(bstudent);

		bstudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openPayments();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		});


		bclose = new JButton(new ImageIcon("img/exit.png"));
		bclose.setToolTipText("Вийти");
		tools.add(bclose);

		bclose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClose();
			}
		});

		cmdClose = new JButton("Закрити");
		cmdAddAbonent = new JButton("Додати");
		cmdUpdateAbonent = new JButton("Редагувати");
		cmdDeleteAbonent = new JButton("Видалити");
		cmdOpenPayments = new JButton("Оплата");
		cmdPrintAbonent = new JButton("На друк");
		cmdVillager=new JButton("Абоненти з сіл");
		cmdTownsman=new JButton("Абоненти з міст");
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

		//MainFonClass mainPanel = new MainFonClass();
		//mainPanel.add(scrollPane);
		scrollPane.setBackground(Color.BLUE);

		JPanel commandsPanel = new JPanel(new FlowLayout());
		commandsPanel.add(jLab);
		commandsPanel.add(cmdAddAbonent);
		commandsPanel.add(cmdUpdateAbonent);
		commandsPanel.add(cmdDeleteAbonent);
		commandsPanel.add(cmdPrintAbonent);
		commandsPanel.add(cmdVillager);
		commandsPanel.add(cmdTownsman);
		commandsPanel.add(cmdOpenPayments);
		commandsPanel.add(cmdClose);
		//Border northBorder = BorderFactory
				//.createTitledBorder("Список усіх абонентів");
		//commandsPanel.setBorder(northBorder);
		commandsPanel.setOpaque(false);
		//mainPanel.add(commandsPanel);

		// setModal(false);
		getRootPane().setDefaultButton(cmdClose);
		setSize(1300,370 );
		setResizable(true);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().add(tools, BorderLayout.NORTH);
		getContentPane().add(commandsPanel, BorderLayout.SOUTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		abonentsTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.isPopupTrigger())
					popupMenu.show(me.getComponent(), me.getX(), me.getY());
			}

			public void mouseReleased(MouseEvent me) {
				if (me.isPopupTrigger())
					popupMenu.show(me.getComponent(), me.getX(), me.getY());
			}
		});

		cmdAddAbonent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAbonent();
			}
		});

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
		cmdVillager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				villagers.setVisible(true);
			}
		});
		cmdTownsman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				townsmans.setVisible(true);
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
			final List<Abonents> abonent = dao.findAll();

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
		if (JOptionPane.showConfirmDialog(MainForm.this,
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
				JOptionPane.showMessageDialog(MainForm.this, e.getMessage());
			}
		}
	
	}
}

