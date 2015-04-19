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

import net.ukr.ifkep.course_project.gui.GroupsTableModel;
import net.ukr.ifkep.oblenergo.dao.AbonentsDAO;
import net.ukr.ifkep.oblenergo.domain.Abonents;

public class MainForm extends JFrame implements ActionListener {
	private JButton cmdClose;
	private JButton cmdAddAbonent;
	private JButton cmdUpdateAbonent;
	private JButton cmdDeleteAbonent;
	private JButton cmdOpenPayments;
	private JButton cmdPrintAbonent;
	private JLabel jLab;
	private JTable abonentsTable;
	
	private NewAbonent newAbonent = new NewAbonent();
	JPopupMenu popupMenu = new JPopupMenu();
	
	JButton bnew, bupdate, bremove, bprint, bclose, bstudent, bteacher,
	cmdKmNew, cmdKmUpdate, cmdKmRemove, cmdKmPrint, cmdKmStudent,
	cmdKmTeacher, cmdKmClose;
	
	private AbonentsTableModel abn;
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addGroup) {
			addGroup();
		} else if (e.getSource() == updateGroup) {
			updateGroup();
		} else if (e.getSource() == removeGroup) {
			removeGroup();
		} else if (e.getSource() == printGroup) {
			printGroup();
		} else if (e.getSource() == openStudent) {
			try {
				openPayments();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == onClose) {
			onClose();
		}
	}

	JMenuItem addGroup, updateGroup, removeGroup, printGroup, openStudent,
			openTeacher, onClose;

	void createMenu() {
		Color colorMenu = (Color.ORANGE);
		Font fontMenu = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		JMenuBar MenuBar = new JMenuBar();

		JMenu mFile = new JMenu("Файл");
		JMenu mInform = new JMenu("Інформація");
		JMenu mAbout = new JMenu("Про програму");
		// MenuBar.add(Box.createHorizontalGlue());
		mFile.setFont(fontMenu);
		mInform.setFont(fontMenu);
		mAbout.setFont(fontMenu);
		MenuBar.setBackground(colorMenu);

		ImageIcon icon = new ImageIcon("img/new.gif");
		addGroup = new JMenuItem("Додати групу", icon);
		addGroup.setToolTipText("Добавити новий запис до бази");
		addGroup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		addGroup.setFont(fontMenu);
		addGroup.addActionListener(this);
		mFile.add(addGroup);

		ImageIcon icon3 = new ImageIcon("img/update.gif");
		updateGroup = new JMenuItem("Внести зміни", icon3);
		updateGroup.setToolTipText("Внести зміни у вже створений запис");
		updateGroup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.CTRL_MASK));
		updateGroup.setFont(fontMenu);
		updateGroup.addActionListener(this);
		mFile.add(updateGroup);

		ImageIcon icon2 = new ImageIcon("img/remote.gif");
		removeGroup = new JMenuItem("Видалити групу", icon2);
		removeGroup.setToolTipText("Видалити запис у базі");
		removeGroup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.SHIFT_MASK));
		removeGroup.setFont(fontMenu);
		removeGroup.addActionListener(this);
		mFile.add(removeGroup);

		mFile.addSeparator();

		ImageIcon icon7 = new ImageIcon("img/print.gif");
		printGroup = new JMenuItem("Вивести на друк", icon7);
		printGroup.setToolTipText("Роздрукувати інформацію з таблиці");
		printGroup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		printGroup.setFont(fontMenu);
		printGroup.addActionListener(this);
		mFile.add(printGroup);

		mFile.addSeparator();

		ImageIcon icon6 = new ImageIcon("img/openstudent.gif");
		openStudent = new JMenuItem("Студенти групи", icon6);
		openStudent
				.setToolTipText("Одержати інформацію про студентів обраної групи");
		openStudent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		openStudent.setFont(fontMenu);
		openStudent.addActionListener(this);
		mInform.add(openStudent);

		mInform.addSeparator();

		ImageIcon icon5 = new ImageIcon("img/openteacher.gif");
		openTeacher = new JMenuItem("Викладачі", icon5);
		openTeacher.setToolTipText("Одержати інформацію про викладачів групи");
		openTeacher.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				ActionEvent.CTRL_MASK));
		openTeacher.setFont(fontMenu);
		openTeacher.addActionListener((ActionListener) this);
		mInform.add(openTeacher);

		ImageIcon icon4 = new ImageIcon("img/onclose.gif");
		onClose = new JMenuItem("Закрити програму", icon4);
		onClose.setToolTipText("Завершити роботу з програмою");
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
		setTitle("Навчальні групи Коледжу електронних приладів");

		createMenu();

		ImageIcon kmNewicon = new ImageIcon("img/new.gif");
		JMenuItem cmdKmNew = new JMenuItem("Новий запис", kmNewicon);
		cmdKmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		cmdKmNew.addActionListener(this);
		popupMenu.add(cmdKmNew);

		cmdKmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addGroup();
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
				updateGroup();
			}
		});
		ImageIcon kmRemoveicon = new ImageIcon("img/remote.gif");
		JMenuItem cmdKmRemove = new JMenuItem("Видалити запис", kmRemoveicon);
		cmdKmRemove.addActionListener(this);
		cmdKmRemove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.SHIFT_MASK));
		popupMenu.add(cmdKmRemove);

		cmdKmRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeGroup();
			}
		});

		popupMenu.addSeparator();

		ImageIcon kmStudenticon = new ImageIcon("img/openstudent.gif");
		JMenuItem cmdKmStudent = new JMenuItem("Студенти групи", kmStudenticon);
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

		bnew = new JButton(new ImageIcon("img/new.gif"));
		bnew.setToolTipText("Новий запис");
		tools.add(bnew);

		bnew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addGroup();
			}
		});

		bupdate = new JButton(new ImageIcon("img/update.gif"));
		bupdate.setToolTipText("Редагувати запис");
		tools.add(bupdate);

		bupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateGroup();
			}
		});

		bremove = new JButton(new ImageIcon("img/remote.gif"));
		bremove.setToolTipText("Видалити запис");
		tools.add(bremove);

		bremove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeGroup();
			}
		});
		bprint = new JButton(new ImageIcon("img/print.gif"));
		bprint.setToolTipText("Роздрукувати інформацію");
		tools.add(bprint);

		bprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printGroup();
			}
		});

		bstudent = new JButton(new ImageIcon("img/openstudent.gif"));
		bstudent.setToolTipText("Студенти групи");
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


		bclose = new JButton(new ImageIcon("img/onclose.gif"));
		bclose.setToolTipText("Закрити програму");
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
		cmdPrintAbonent = new JButton("Вивести на друк");
		jLab = new JLabel();

		abn = getTableModel();
		abonentsTable = new JTable(abn);
		abonentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		abonentsTable.setPreferredScrollableViewportSize(new Dimension(880, 180));
		abonentsTable.getColumnModel().getColumn(0).setMinWidth(40);
		abonentsTable.getColumnModel().getColumn(1).setMinWidth(120);
		abonentsTable.getColumnModel().getColumn(2).setMinWidth(300);
		abonentsTable.getColumnModel().getColumn(3).setMinWidth(100);
		abonentsTable.getColumnModel().getColumn(4).setMinWidth(80);
		abonentsTable.getColumnModel().getColumn(5).setMinWidth(160);
		abonentsTable.setGridColor(Color.ORANGE);
		abonentsTable.setRowHeight(20);
		Font FontGrid = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		abonentsTable.setFont(FontGrid);

		JScrollPane scrollPane = new JScrollPane(abonentsTable);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		MainFonClass mainPanel = new MainFonClass();
		mainPanel.add(scrollPane);

		JPanel commandsPanel = new JPanel(new FlowLayout());
		commandsPanel.add(jLab);
		commandsPanel.add(cmdAddAbonent);
		commandsPanel.add(cmdUpdateAbonent);
		commandsPanel.add(cmdDeleteAbonent);
		commandsPanel.add(cmdPrintAbonent);

		commandsPanel.add(cmdOpenPayments);
		commandsPanel.add(cmdClose);
		Border northBorder = BorderFactory
				.createTitledBorder("Командна панель");
		commandsPanel.setBorder(northBorder);
		commandsPanel.setOpaque(false);
		mainPanel.add(commandsPanel);

		// setModal(false);
		getRootPane().setDefaultButton(cmdClose);
		setSize(900, 380);
		setResizable(false);
		getContentPane().add(tools, BorderLayout.NORTH);
		getContentPane().add(mainPanel, BorderLayout.CENTER);

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
				addGroup();
			}
		});

		cmdUpdateAbonent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateGroup();
			}
		});

		cmdDeleteAbonent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeGroup();
			}
		});

		cmdPrintAbonent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printGroup();
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

		mainPanel.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClose();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private AbonentsTableModel getTableModel() {
		try {
			AbonentsDAO dao = new AbonentsDAO();
			final List<Abonents> abonent = dao.findAll();

			return new AbonentsTableModel(abonent);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Помилка при заповненні таблиці груп: " + e.getMessage());
		}
		return new AbonentsTableModel(new ArrayList<Abonents>(0));
	}

	private void printGroup() {
		try {
			MessageFormat headerFormat = new MessageFormat("Сторінка {0}");
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			abonentsTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat,
					footerFormat);
		} catch (PrinterException pe) {
			System.err.println("Неможливо роздрукувати документ по причині: "
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
		AbonentsPaymentForm groupStudentForm = new AbonentsPaymentForm();
		groupStudentForm.setVisible(true);
	}

	private void onClose() {
		dispose();
	}

	private void addGroup() {
		newAbonent.setAbonent(new Abonents());
		newAbonent.setVisible(true);
		if (newAbonent.getAbonent().getId() != null) {
			abn.addAbonent(newAbonent.getAbonent());
		}
	}

	private void updateGroup() {
		int index = groupsTable.getSelectedRow();
		if (index == -1)
			return;

		Group group = groupsTableModel.getRowGroup(index);
		if (group != null) {
			newGroup.setGroup(group);
			newGroup.setVisible(true);
			groupsTableModel.refreshUpdatedTable();
		}
	}

	private void removeGroup() {
		if (JOptionPane.showConfirmDialog(MainForm.this,
				"Ви хочете видалити інформацію про навчальну групу?",
				"Видалення даних про групу", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			int index = groupsTable.getSelectedRow();
			if (index == -1)
				return;

			try {
				Group g = groupsTableModel.getRowGroup(index);
				if (g != null) {

					GroupDao dao = new GroupDao();
					dao.deleteGroup(g.getId());
					groupsTableModel.removeRow(index);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(MainForm.this, e.getMessage());
			}
		}
	
	}
}

