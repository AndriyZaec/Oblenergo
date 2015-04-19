package net.ukr.ifkep.course_project.gui;

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

	private JButton cmdClose;
	private JButton cmdAddStudent;
	private JButton cmdUpdateStudent;
	private JButton cmdDeleteStudent;
	private JButton cmdPrintStudent;
	private JTable studentsTable;

	private NewStudent newStudent = new NewStudent();
	JPopupMenu popupMenu = new JPopupMenu();

	JButton bnew, bupdate, bremove, bprint, bclose, cmdKmNew, cmdKmUpdate,
			cmdKmRemove, cmdKmPrint, cmdKmClose;

	private StudentsTableModel studentsTableModel;

	private Group groups;

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addStudent) {
			addStudent();
		} else if (e.getSource() == updateStudent) {
			updateStudent();
		} else if (e.getSource() == removeStudent) {
			removeStudent();
		} else if (e.getSource() == printStudent) {
			printStudent();
		} else if (e.getSource() == onClose) {
			onClose();
		}
	}

	JMenuItem addStudent, updateStudent, removeStudent, printStudent, onClose;

	void createMenu() {
		Color colorMenu = (Color.ORANGE);
		Font fontMenu = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		JMenuBar MenuBar = new JMenuBar();

		JMenu mFile = new JMenu("Файл");
		mFile.setFont(fontMenu);
		MenuBar.setBackground(colorMenu);

		ImageIcon icon = new ImageIcon("img/new.gif");
		addStudent = new JMenuItem("Додати студента", icon);
		addStudent.setToolTipText("Добавити новий запис до бази");
		addStudent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		addStudent.setFont(fontMenu);
		addStudent.addActionListener(this);
		mFile.add(addStudent);

		ImageIcon icon3 = new ImageIcon("img/update.gif");
		updateStudent = new JMenuItem("Внести зміни", icon3);
		updateStudent.setToolTipText("Внести зміни у вже створений запис");
		updateStudent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.CTRL_MASK));
		updateStudent.setFont(fontMenu);
		updateStudent.addActionListener(this);
		mFile.add(updateStudent);

		ImageIcon icon2 = new ImageIcon("img/remote.gif");
		removeStudent = new JMenuItem("Видалити студента", icon2);
		removeStudent.setToolTipText("Видалити запис у базі");
		removeStudent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.SHIFT_MASK));
		removeStudent.setFont(fontMenu);
		removeStudent.addActionListener(this);
		mFile.add(removeStudent);

		mFile.addSeparator();

		ImageIcon icon7 = new ImageIcon("img/print.gif");
		printStudent = new JMenuItem("Вивести на друк", icon7);
		printStudent.setToolTipText("Роздрукувати інформацію з таблиці");
		printStudent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		printStudent.setFont(fontMenu);
		printStudent.addActionListener(this);
		mFile.add(printStudent);

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

	public GroupStudentForm(Group g) throws IOException {
		this.groups = g;
		setTitle("Студенти групи  " + g.getGroupName());

		createMenu();

		ImageIcon kmNewicon = new ImageIcon("img/new.gif");
		JMenuItem cmdKmNew = new JMenuItem("Новий запис", kmNewicon);
		cmdKmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		cmdKmNew.addActionListener(this);
		popupMenu.add(cmdKmNew);

		cmdKmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addStudent();
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
				updateStudent();
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
				removeStudent();
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
				addStudent();
			}
		});

		bupdate = new JButton(new ImageIcon("img/update.gif"));
		bupdate.setToolTipText("Редагувати запис");
		tools.add(bupdate);

		bupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateStudent();
			}
		});

		bremove = new JButton(new ImageIcon("img/remote.gif"));
		bremove.setToolTipText("Видалити запис");
		tools.add(bremove);

		bremove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeStudent();
			}
		});
		bprint = new JButton(new ImageIcon("img/print.gif"));
		bprint.setToolTipText("Роздрукувати інформацію");
		tools.add(bprint);

		bprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printStudent();
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
		cmdAddStudent = new JButton("Додати");
		cmdUpdateStudent = new JButton("Редагувати");
		cmdDeleteStudent = new JButton("Видалити");
		cmdPrintStudent = new JButton("Вивести на друк");
		studentsTableModel = getTableModel(g);
		studentsTable = new JTable(studentsTableModel);
		studentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		studentsTable
				.setPreferredScrollableViewportSize(new Dimension(920, 180));
		studentsTable.getColumnModel().getColumn(0).setMinWidth(200);
		studentsTable.getColumnModel().getColumn(1).setMinWidth(80);
		studentsTable.getColumnModel().getColumn(2).setMinWidth(60);
		studentsTable.getColumnModel().getColumn(3).setMinWidth(60);
		studentsTable.getColumnModel().getColumn(4).setMinWidth(240);
		studentsTable.getColumnModel().getColumn(5).setMinWidth(100);
		studentsTable.setGridColor(Color.ORANGE);
		studentsTable.setRowHeight(20);
		Font FontGrid = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		studentsTable.setFont(FontGrid);
		JScrollPane scrollPane = new JScrollPane(studentsTable);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		MainFonClass mainPanel = new MainFonClass();
		mainPanel.add(scrollPane);
		JPanel commandsPanel = new JPanel(new FlowLayout());
		commandsPanel.add(cmdAddStudent);
		commandsPanel.add(cmdUpdateStudent);
		commandsPanel.add(cmdDeleteStudent);
		commandsPanel.add(cmdPrintStudent);
		commandsPanel.add(cmdClose);
		commandsPanel.setOpaque(false);
		mainPanel.add(commandsPanel);

		getRootPane().setDefaultButton(cmdClose);
		setSize(950, 350);
		setResizable(false);
		getContentPane().add(tools, BorderLayout.NORTH);
		getContentPane().add(mainPanel, BorderLayout.CENTER);

		studentsTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if (me.isPopupTrigger())
					popupMenu.show(me.getComponent(), me.getX(), me.getY());
			}

			public void mouseReleased(MouseEvent me) {
				if (me.isPopupTrigger())
					popupMenu.show(me.getComponent(), me.getX(), me.getY());
			}
		});

		cmdAddStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addStudent();
			}
		});

		cmdUpdateStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateStudent();
			}
		});

		cmdDeleteStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeStudent();
			}
		});
		cmdPrintStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printStudent();
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

	private StudentsTableModel getTableModel(Group g) {
		try {
			StudentDao dao = new StudentDao();
			final List<Student> students = dao.findByGroup(g.getId());

			return new StudentsTableModel(students);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(
					this,
					"Помилка при заповненні таблиці студентів: "
							+ e.getMessage());
		}
		return new StudentsTableModel(new ArrayList<Student>(0));
	}

	private void onClose() {
		dispose();
	}

	private void addStudent() {
		Student student = new Student();
		student.setGroupId(this.groups.getId());
		newStudent.setStudent(student);
		newStudent.setVisible(true);
		if (newStudent.getStudent().getId() != null) {
			studentsTableModel.addStudent(newStudent.getStudent());
		}
	}

	private void updateStudent() {
		int index = studentsTable.getSelectedRow();
		if (index == -1)
			return;

		Student student = studentsTableModel.getRowStudent(index);
		if (student != null) {
			newStudent.setStudent(student);
			newStudent.setVisible(true);
			studentsTableModel.refreshUpdatedTable();
		}
	}

	private void removeStudent() {
		if (JOptionPane.showConfirmDialog(GroupStudentForm.this,
				"Ви хочете видалити інформацію про студента?",
				"Видалення запису про студента", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			int index = studentsTable.getSelectedRow();
			if (index == -1)
				return;

			try {
				Student g = studentsTableModel.getRowStudent(index);
				if (g != null) {
					StudentDao dao = new StudentDao();
					dao.deleteStudent(g.getId());
					studentsTableModel.removeRow(index);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(GroupStudentForm.this,
						e.getMessage());
			}
		}
	}

	private void printStudent() {
		try {
			MessageFormat headerFormat = new MessageFormat("Сторінка {0}");
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			studentsTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat,
					footerFormat);
		} catch (PrinterException pe) {
			System.err.println("Неможливо роздрукувати документ по причині: "
					+ pe.getMessage());
		}
	}
	
}
