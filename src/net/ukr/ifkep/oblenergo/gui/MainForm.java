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
			addAbonent();
		} else if (e.getSource() == updateGroup) {
			updateAbonent();
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

		JMenu mFile = new JMenu("����");
		JMenu mInform = new JMenu("����������");
		JMenu mAbout = new JMenu("��� ��������");
		// MenuBar.add(Box.createHorizontalGlue());
		mFile.setFont(fontMenu);
		mInform.setFont(fontMenu);
		mAbout.setFont(fontMenu);
		MenuBar.setBackground(colorMenu);

		ImageIcon icon = new ImageIcon("img/new.gif");
		addGroup = new JMenuItem("������ �����", icon);
		addGroup.setToolTipText("�������� ����� ����� �� ����");
		addGroup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		addGroup.setFont(fontMenu);
		addGroup.addActionListener(this);
		mFile.add(addGroup);

		ImageIcon icon3 = new ImageIcon("img/update.gif");
		updateGroup = new JMenuItem("������ ����", icon3);
		updateGroup.setToolTipText("������ ���� � ��� ��������� �����");
		updateGroup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.CTRL_MASK));
		updateGroup.setFont(fontMenu);
		updateGroup.addActionListener(this);
		mFile.add(updateGroup);

		ImageIcon icon2 = new ImageIcon("img/remote.gif");
		removeGroup = new JMenuItem("�������� �����", icon2);
		removeGroup.setToolTipText("�������� ����� � ���");
		removeGroup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.SHIFT_MASK));
		removeGroup.setFont(fontMenu);
		removeGroup.addActionListener(this);
		mFile.add(removeGroup);

		mFile.addSeparator();

		ImageIcon icon7 = new ImageIcon("img/print.gif");
		printGroup = new JMenuItem("������� �� ����", icon7);
		printGroup.setToolTipText("������������ ���������� � �������");
		printGroup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		printGroup.setFont(fontMenu);
		printGroup.addActionListener(this);
		mFile.add(printGroup);

		mFile.addSeparator();

		ImageIcon icon6 = new ImageIcon("img/openstudent.gif");
		openStudent = new JMenuItem("�������� �����", icon6);
		openStudent
				.setToolTipText("�������� ���������� ��� �������� ������ �����");
		openStudent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		openStudent.setFont(fontMenu);
		openStudent.addActionListener(this);
		mInform.add(openStudent);

		mInform.addSeparator();

		ImageIcon icon5 = new ImageIcon("img/openteacher.gif");
		openTeacher = new JMenuItem("���������", icon5);
		openTeacher.setToolTipText("�������� ���������� ��� ���������� �����");
		openTeacher.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				ActionEvent.CTRL_MASK));
		openTeacher.setFont(fontMenu);
		openTeacher.addActionListener((ActionListener) this);
		mInform.add(openTeacher);

		ImageIcon icon4 = new ImageIcon("img/onclose.gif");
		onClose = new JMenuItem("������� ��������", icon4);
		onClose.setToolTipText("��������� ������ � ���������");
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
		setTitle("�������� ����� ������� ����������� �������");

		createMenu();

		ImageIcon kmNewicon = new ImageIcon("img/new.gif");
		JMenuItem cmdKmNew = new JMenuItem("����� �����", kmNewicon);
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
		JMenuItem cmdKmUpdate = new JMenuItem("���������� �����", kmUpdateicon);
		cmdKmUpdate.addActionListener(this);
		cmdKmUpdate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.CTRL_MASK));
		popupMenu.add(cmdKmUpdate);

		cmdKmUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAbonent();
			}
		});
		ImageIcon kmRemoveicon = new ImageIcon("img/remote.gif");
		JMenuItem cmdKmRemove = new JMenuItem("�������� �����", kmRemoveicon);
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
		JMenuItem cmdKmStudent = new JMenuItem("�������� �����", kmStudenticon);
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
		bnew.setToolTipText("����� �����");
		tools.add(bnew);

		bnew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAbonent();
			}
		});

		bupdate = new JButton(new ImageIcon("img/update.gif"));
		bupdate.setToolTipText("���������� �����");
		tools.add(bupdate);

		bupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAbonent();
			}
		});

		bremove = new JButton(new ImageIcon("img/remote.gif"));
		bremove.setToolTipText("�������� �����");
		tools.add(bremove);

		bremove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeGroup();
			}
		});
		bprint = new JButton(new ImageIcon("img/print.gif"));
		bprint.setToolTipText("������������ ����������");
		tools.add(bprint);

		bprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printGroup();
			}
		});

		bstudent = new JButton(new ImageIcon("img/openstudent.gif"));
		bstudent.setToolTipText("�������� �����");
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
		bclose.setToolTipText("������� ��������");
		tools.add(bclose);

		bclose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClose();
			}
		});

		cmdClose = new JButton("�������");
		cmdAddAbonent = new JButton("������");
		cmdUpdateAbonent = new JButton("����������");
		cmdDeleteAbonent = new JButton("��������");
		cmdOpenPayments = new JButton("������");
		cmdPrintAbonent = new JButton("������� �� ����");
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
				.createTitledBorder("�������� ������");
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
					"������� ��� ��������� ������� ����: " + e.getMessage());
		}
		return new AbonentsTableModel(new ArrayList<Abonents>(0));
	}

	private void printGroup() {
		try {
			MessageFormat headerFormat = new MessageFormat("������� {0}");
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			abonentsTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat,
					footerFormat);
		} catch (PrinterException pe) {
			System.err.println("��������� ������������ �������� �� ������: "
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
		AbonentsPaymentForm AbonentsPaymentForm = new AbonentsPaymentForm();
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

		Abonents abonent = abn.getRowAbonent(index);
		if (abonent != null) {
			newAbonent.setAbonent(abonent);
			abn.refreshUpdatedTable();
		}
	}

	private void removeGroup() {
		if (JOptionPane.showConfirmDialog(MainForm.this,
				"�� ������ �������� ���������� ��� ��������� �����?",
				"��������� ����� ��� �����", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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

