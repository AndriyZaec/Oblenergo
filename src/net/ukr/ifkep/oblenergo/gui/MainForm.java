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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableModel;

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
	private JButton cmdDebtors;
	private JLabel jLab;
	private JTable abonentsTable;
	
	
	private NewAbonent newAbonent = new NewAbonent();
	private Villagers villagers = new Villagers();
	private Townsmans townsmans = new Townsmans();
	private DebtorsForm debtors = new DebtorsForm();
	private JPopupMenu popupMenu = new JPopupMenu();
	
	JButton bnew, bupdate, bremove, bprint,bexcel, bclose, bpayment,
	cmdKmNew, cmdKmUpdate, cmdKmRemove, cmdKmPrint, cmdKmPayment,
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
		}else if (e.getSource() == openVillagers){
			openVillagers();
		}else if (e.getSource() == openTowmsmans){
			openTownsmans();
		}else if (e.getSource() == openDebeters){
			openDebtors();
		}else if (e.getSource() == onClose) {
			onClose();
		}
	}

	JMenuItem addAbonent, updateAbonent, removeAbonent, printAbonent, openPayment, openVillagers, openTowmsmans,openDebeters
	,onClose;

	void createMenu() {
		Color colorMenu = (Color.LIGHT_GRAY);
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			
		}catch(Exception e){
			
		}
		Font fontMenu = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		JMenuBar MenuBar = new JMenuBar();

		JMenu mFile = new JMenu("Файл");
		JMenu mInform = new JMenu("Інформація");
		JMenu mAbout = new JMenu("Про нас");
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

		ImageIcon icon3 = new ImageIcon("img/upd.png");
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

		ImageIcon icon7 = new ImageIcon("img/print.png");
		printAbonent = new JMenuItem("На друк", icon7);
		printAbonent.setToolTipText("Виведення на друк інформації про абонента");
		printAbonent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		printAbonent.setFont(fontMenu);
		printAbonent.addActionListener(this);
		mFile.add(printAbonent);

		mFile.addSeparator();

		ImageIcon icon6 = new ImageIcon("img/pay.png");
		openPayment = new JMenuItem("Інформація про оплату", icon6);
		openPayment.setToolTipText("Інформація про оплату");
		openPayment.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		openPayment.setFont(fontMenu);
		openPayment.addActionListener(this);
		mInform.add(openPayment);

		mInform.addSeparator();
		
		ImageIcon icon8 = new ImageIcon("img/village.png");
		openVillagers = new JMenuItem("Абоненти з сіл",icon8);
		openVillagers.setToolTipText("Інформація про абонентів з села");
		openVillagers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.CTRL_MASK));
		openVillagers.setFont(fontMenu);
		openVillagers.addActionListener(this);
		mInform.add(openVillagers);
		
		ImageIcon icon9 = new ImageIcon("img/town.png");
		openTowmsmans = new JMenuItem("Абоненти з міст",icon9);
		openTowmsmans.setToolTipText("Інформація про абонентів з міста");
		openTowmsmans.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				ActionEvent.CTRL_MASK));
		openTowmsmans.setFont(fontMenu);
		openTowmsmans.addActionListener(this);
		mInform.add(openTowmsmans);
		
		mInform.addSeparator();
		
		ImageIcon icon10 = new ImageIcon("img/debt.png");
		openDebeters = new JMenuItem("Боржники",icon10);
		openDebeters.setToolTipText("Інформація про абонентів які заборгували");
		openDebeters.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.CTRL_MASK));
		openDebeters.setFont(fontMenu);
		openDebeters.addActionListener(this);
		mInform.add(openDebeters);

		ImageIcon icon4 = new ImageIcon("img/exit.png");
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
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			
		}catch(Exception e){
			
		}
		setTitle("Головна форма");
		
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

		ImageIcon kmUpdateicon = new ImageIcon("img/upd.png");
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

		ImageIcon kmPaymenticon = new ImageIcon("img/pay.png");
		JMenuItem cmdKmPayment = new JMenuItem("Відкрити інформацію про оплату", kmPaymenticon);
		cmdKmPayment.addActionListener(this);
		cmdKmPayment.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		popupMenu.add(cmdKmPayment);

		cmdKmPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openPayments();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		});


		JToolBar tools = new JToolBar();
		Color ColorBar = Color.LIGHT_GRAY;
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
		
		bexcel= new JButton(new ImageIcon("img/excel.png"));
		bexcel.setToolTipText("Конвертація в Excel");
		tools.add(bexcel);
		
		bexcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				convExcel();
				if (new File("Files/Abonents.xls").exists()==true) {
		        	JOptionPane.showMessageDialog(MainForm.this, "Excel таблиця створена");
		        }
		        else if(new File("Files/Abonents.xls").exists()==false){
		        	JOptionPane.showMessageDialog(MainForm.this, "Excel таблиця не створена");
		        }
			}
		});
		
		
		bpayment = new JButton(new ImageIcon("img/pay.png"));
		bpayment.setToolTipText("Відкрити оплату ");
		tools.add(bpayment);

		bpayment.addActionListener(new ActionListener() {
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
		cmdDebtors = new JButton("Боржники");
		jLab = new JLabel();

		
		abn = getTableModel();
		abonentsTable = new JTable(abn);
		abonentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		abonentsTable.setPreferredScrollableViewportSize(new Dimension(100, 180));
		abonentsTable.getColumnModel().getColumn(0).setMinWidth(30);
		abonentsTable.getColumnModel().getColumn(0).setMaxWidth(120);
		abonentsTable.getColumnModel().getColumn(1).setMinWidth(60);
		abonentsTable.getColumnModel().getColumn(2).setMinWidth(10);
		abonentsTable.getColumnModel().getColumn(3).setMinWidth(60);
		abonentsTable.getColumnModel().getColumn(4).setMinWidth(10);
		abonentsTable.getColumnModel().getColumn(4).setMaxWidth(50);
		abonentsTable.getColumnModel().getColumn(5).setMinWidth(60);
		abonentsTable.getColumnModel().getColumn(6).setMinWidth(60);
		abonentsTable.getColumnModel().getColumn(7).setMinWidth(60);
		abonentsTable.setRowHeight(20);
		Font FontGrid = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		abonentsTable.setFont(FontGrid);

		JScrollPane scrollPane = new JScrollPane(abonentsTable);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		JPanel commandsPanel = new JPanel(new FlowLayout());
		commandsPanel.add(jLab);
		commandsPanel.add(cmdAddAbonent);
		commandsPanel.add(cmdUpdateAbonent);
		commandsPanel.add(cmdDeleteAbonent);
		commandsPanel.add(cmdPrintAbonent);
		commandsPanel.add(cmdVillager);
		commandsPanel.add(cmdTownsman);
		commandsPanel.add(cmdDebtors);
		commandsPanel.add(cmdOpenPayments);
		commandsPanel.add(cmdClose);
		commandsPanel.setOpaque(false);
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
				openVillagers();
			}
		});
		cmdTownsman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openTownsmans();
			}
		});
		
		cmdDebtors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDebtors();
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
	}

	private void openVillagers(){
		villagers.setVisible(true);
	}
	
	private void openTownsmans(){
		townsmans.setVisible(true);
	}
	
	private void openDebtors(){
		debtors.setVisible(true);
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
			MessageFormat headerFormat = new MessageFormat("Абоненти{0}");
			MessageFormat footerFormat = new MessageFormat("- {0} -");
			abonentsTable.print(JTable.PrintMode.FIT_WIDTH, headerFormat,
					footerFormat);
		} catch (PrinterException pe) {
			System.err.println("Помилка при друці: "
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
	
	private void convExcel(){
		try{
		        TableModel model = abonentsTable.getModel();
		        FileWriter excel = new FileWriter("Files/Abonents.xls");

		        for(int i = 0; i < model.getColumnCount(); i++){
		            excel.write(model.getColumnName(i) + "\t");
		        }

		        excel.write("\n");

		        for(int i=0; i< model.getRowCount(); i++) {
		            for(int j=0; j < model.getColumnCount(); j++) {
		                excel.write(model.getValueAt(i,j).toString()+"\t");
		            }
		            excel.write("\n");
		        }

		        excel.close();
		       

		    }catch(IOException ex){ System.out.println(ex); }
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

