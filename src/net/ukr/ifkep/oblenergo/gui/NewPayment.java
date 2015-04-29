package net.ukr.ifkep.oblenergo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.MaskFormatter;

import net.ukr.ifkep.oblenergo.gui.MainFonClass;
import net.ukr.ifkep.oblenergo.dao.PaymentDAO;
import net.ukr.ifkep.oblenergo.domain.Payments;

public class NewPayment extends JDialog{
	private static final long serialVersionUID = 1L;
	private Payments pay;

	private JTextField payDate;
	private JTextField curPay;
	private JTextField curIndex;
	private JTextField lastIndex;
	private JComboBox<?> limit;
	private JTextField excLimit;
	private JTextField debt;
	private JTextField price;
	private JLabel JLabel_1 = new JLabel();
	private JLabel JLabel_2 = new JLabel();
	private JLabel JLabel_3 = new JLabel();
	private JLabel JLabel_4 = new JLabel();
	private JLabel JLabel_5 = new JLabel();
	private JLabel JLabel_6 = new JLabel();
	private JLabel JLabel_7 = new JLabel();
	private JLabel JLabel_8 = new JLabel();

	public NewPayment() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			
		}catch(Exception e){
			
		}
		setTitle("Додавання інформації про оплату");
		setSize(600,350);
		setModal(true);
		setResizable(false);

		final JButton cmdSave = new JButton("Зберегти");
		final JButton cmdCancel = new JButton("Відмінити");
		final JButton cmdCalculate = new JButton("Розрахувати");

		cmdSave.setEnabled(false);
		
		try {
			MaskFormatter date = new MaskFormatter("##/##/####");
			payDate = new JFormattedTextField(date);
			} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			MaskFormatter date = new MaskFormatter("##/##/####");
			curPay = new JFormattedTextField(date);
			} catch (ParseException e1) {
			e1.printStackTrace();
		}
		curIndex = new JTextField(15);
		lastIndex= new JTextField(15);
		limit =new JComboBox<Object>(new Object[] { "200","366","422" });
		debt = new JTextField(15);
		excLimit = new JTextField(15);
		price = new JTextField(15);
		
		price.setEnabled(false);
		excLimit.setEnabled(false);
		debt.setForeground(Color.RED);
		
		MainFonClass NewAbonentMainPanel = new MainFonClass("img/newupdatefon.jpg");
		final JPanel fieldsPanel = new JPanel(new GridLayout(8, 2, 2, 2));
		final JPanel fieldsPanelBorder = new JPanel(new FlowLayout(
				FlowLayout.CENTER, 10, 10));
		fieldsPanel.setOpaque(false);
		fieldsPanelBorder.setOpaque(false);
		fieldsPanelBorder.add(fieldsPanel);
		JLabel_1.setText("Дата оплати");
		JLabel_2.setText("Поточна оплата");
		JLabel_3.setText("Поточний показник");
		JLabel_4.setText("Останній показник");
		JLabel_5.setText("Ліміт");
		JLabel_6.setText("Перевикористання ліміту");
		JLabel_7.setText("Борг");
		JLabel_8.setText("До оплати");
		JLabel_8.setForeground(Color.ORANGE);
		JLabel_7.setForeground(Color.ORANGE);
		JLabel_6.setForeground(Color.ORANGE);
		JLabel_5.setForeground(Color.ORANGE);
		JLabel_4.setForeground(Color.ORANGE);
		JLabel_3.setForeground(Color.ORANGE);
		JLabel_2.setForeground(Color.ORANGE);
		JLabel_1.setForeground(Color.ORANGE);
		fieldsPanel.add(JLabel_1);
		fieldsPanel.add(payDate);
		fieldsPanel.add(JLabel_2);
		fieldsPanel.add(curPay);
		fieldsPanel.add(JLabel_3);
		fieldsPanel.add(curIndex);
		fieldsPanel.add(JLabel_4);
		fieldsPanel.add(lastIndex);
		fieldsPanel.add(JLabel_5);
		fieldsPanel.add(limit);
		fieldsPanel.add(JLabel_6);
		fieldsPanel.add(excLimit);
		fieldsPanel.add(JLabel_7);
		fieldsPanel.add(debt);
		fieldsPanel.add(JLabel_8);
		fieldsPanel.add(price);

		final JPanel commandsPanel = new JPanel(new FlowLayout());
		final JPanel commandsPanelBorder = new JPanel(new FlowLayout(
				FlowLayout.CENTER, 0, 0));
		commandsPanelBorder.add(commandsPanel);
		commandsPanel.setOpaque(false);
		commandsPanel.add(cmdSave);
		commandsPanel.add(cmdCancel);
		commandsPanel.add(cmdCalculate);
		commandsPanelBorder.setOpaque(false);
		NewAbonentMainPanel.add(fieldsPanelBorder, BorderLayout.NORTH);
		NewAbonentMainPanel.add(commandsPanelBorder, BorderLayout.SOUTH);
		Container c = getContentPane();
		c.add(NewAbonentMainPanel);
		
		curIndex.addKeyListener(new java.awt.event.KeyAdapter() {
	        public void keyTyped(java.awt.event.KeyEvent e) {
	          char a = e.getKeyChar();
	          if (!Character.isDigit(a)){            
	            e.consume();
	          }
	        }
	      });
		debt.addKeyListener(new java.awt.event.KeyAdapter() {
	        public void keyTyped(java.awt.event.KeyEvent e) {
	          char a = e.getKeyChar();
	          if (!Character.isDigit(a)){            
	            e.consume();
	          }
	        }
	      });
		curIndex.addKeyListener(new java.awt.event.KeyAdapter() {
	        public void keyTyped(java.awt.event.KeyEvent e) {
	          char a = e.getKeyChar();
	          if (!Character.isDigit(a)){            
	            e.consume();
	          }
	        }
	      });
		cmdSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveNewPayment();
			}
		});
		
		cmdCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calculate();
				cmdSave.setEnabled(true);
			}
		});

		cmdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelSave();
			}
		});
	}

	public Payments getPayments() {
		return pay;
	}

	public void Calculate(){
		if(Float.parseFloat(curIndex.getText())!=0 && Float.parseFloat(lastIndex.getText())!=0 ){
			float li,ci,d,pr,sub;
			li=Float.parseFloat(lastIndex.getText());
			ci=Float.parseFloat(curIndex.getText());
			d=Float.parseFloat(debt.getText());
			sub=ci-li;
			if(sub > Float.parseFloat((String) limit.getSelectedItem())){
				excLimit.setText(Float.toString(sub));
				pr=(float) (sub*3.63+d);
				price.setText(Float.toString(pr));
			}
			else{
				excLimit.setText("0");
				pr=(float) (sub*1.63+d);
				price.setText(Float.toString(pr));
			}
		}
		else{
			curIndex.setText("Введіть показники");
			lastIndex.setText("Введіть показники");
		}
	}
	
	public void setPayments(Payments p) {
		this.pay = p;
		payDate.setText(pay.getPaymentDate());
		curPay.setText(pay.getCurPayment());
		curIndex.setText(Double.toString(pay.getCurIndex()));
		lastIndex.setText(Double.toString(pay.getLastIndex()));
		limit.setSelectedItem(pay.getLimit());
		excLimit.setText(Double.toString(pay.getExcessLimit()));
		debt.setText(Double.toString(pay.getDebt()));
		price.setText(Double.toString(pay.getPrice()));
	}

	private void saveNewPayment() {
		try {
			pay.setPaymentDate(payDate.getText());
			pay.setCurPayment(curPay.getText());
			pay.setCurIndex(Float.parseFloat(curIndex.getText()));
			pay.setLastIndex(Float.parseFloat(lastIndex.getText()));
			pay.setLimit(Float.parseFloat(limit.getSelectedItem().toString()));
			pay.setExcessLimit(Float.parseFloat(excLimit.getText()));
			pay.setDebt(Float.parseFloat(debt.getText()));
			pay.setPrice(Float.parseFloat(price.getText()));
			//pay.setPayer();

			if (pay.getId() == null) {
				int newId = new PaymentDAO().insertPayment(pay);
				pay.setId(newId);
			} else {
				new PaymentDAO().updatePayments(pay);
			}
			this.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Помилка при збереженні оплати: " + e.getMessage());
		}
	}

	private void cancelSave() {
		this.setVisible(false);
	}

	
}
