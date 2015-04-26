package net.ukr.ifkep.oblenergo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.ukr.ifkep.oblenergo.gui.MainFonClass;
import net.ukr.ifkep.oblenergo.dao.AbonentsDAO;
import net.ukr.ifkep.oblenergo.domain.Abonents;

public class NewAbonent extends JDialog{
	private static final long serialVersionUID = 1L;
	private Abonents abonent;

	private JTextField surname;
	private JTextField name;
	private JTextField birth;
	private JComboBox sex;
	private JComboBox typeLocality;
	private JTextField nameLocality;
	private JTextField adrress;
	private JTextField tel;
	private JLabel JLabel_1 = new JLabel();
	private JLabel JLabel_2 = new JLabel();
	private JLabel JLabel_3 = new JLabel();
	private JLabel JLabel_4 = new JLabel();
	private JLabel JLabel_5 = new JLabel();
	private JLabel JLabel_6 = new JLabel();
	private JLabel JLabel_7 = new JLabel();
	private JLabel JLabel_8 = new JLabel();

	public NewAbonent() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			
		}catch(Exception e){
			
		}
		setTitle("Інформація нового абонента");
		setSize(800,400);
		setModal(true);
		setResizable(false);

		final JButton cmdSave = new JButton("Зберегти");
		final JButton cmdCancel = new JButton("Відмінити");

		surname = new JTextField(15);
		name = new JTextField(15);
		birth = new JTextField(15);
		sex=new JComboBox(new Object[] { "ч","ж" });
		typeLocality=new JComboBox(new Object[] { "місто","село" });
		nameLocality = new JTextField(30);
		adrress=new JTextField(25);
		tel=new JTextField(10);
		
		MainFonClass NewAbonentMainPanel = new MainFonClass("img/newupdatefon.jpg");
		final JPanel fieldsPanel = new JPanel(new GridLayout(8, 2, 2, 2));
		final JPanel fieldsPanelBorder = new JPanel(new FlowLayout(
				FlowLayout.CENTER, 10, 10));
		fieldsPanel.setOpaque(false);
		fieldsPanelBorder.setOpaque(false);
		fieldsPanelBorder.add(fieldsPanel);
		JLabel_1.setText("Прізвище");
		JLabel_2.setText("Ім'я");
		JLabel_3.setText("Дата народження");
		JLabel_4.setText("Стать");
		JLabel_5.setText("Тип населеного пункту");
		JLabel_6.setText("Назва населеного пункту");
		JLabel_7.setText("Адреса");
		JLabel_8.setText("Телефон");
		JLabel_8.setForeground(Color.ORANGE);
		JLabel_7.setForeground(Color.ORANGE);
		JLabel_6.setForeground(Color.ORANGE);
		JLabel_5.setForeground(Color.ORANGE);
		JLabel_4.setForeground(Color.ORANGE);
		JLabel_3.setForeground(Color.ORANGE);
		JLabel_2.setForeground(Color.ORANGE);
		JLabel_1.setForeground(Color.ORANGE);
		fieldsPanel.add(JLabel_1);
		fieldsPanel.add(surname);
		fieldsPanel.add(JLabel_2);
		fieldsPanel.add(name);
		fieldsPanel.add(JLabel_3);
		fieldsPanel.add(birth);
		fieldsPanel.add(JLabel_4);
		fieldsPanel.add(sex);
		fieldsPanel.add(JLabel_5);
		fieldsPanel.add(typeLocality);
		fieldsPanel.add(JLabel_6);
		fieldsPanel.add(nameLocality);
		fieldsPanel.add(JLabel_7);
		fieldsPanel.add(adrress);
		fieldsPanel.add(JLabel_8);
		fieldsPanel.add(tel);

		final JPanel commandsPanel = new JPanel(new FlowLayout());
		final JPanel commandsPanelBorder = new JPanel(new FlowLayout(
				FlowLayout.CENTER, 0, 0));
		commandsPanelBorder.add(commandsPanel);
		commandsPanel.setOpaque(false);
		commandsPanel.add(cmdSave);
		commandsPanel.add(cmdCancel);
		commandsPanelBorder.setOpaque(false);
		NewAbonentMainPanel.add(fieldsPanelBorder, BorderLayout.NORTH);
		NewAbonentMainPanel.add(commandsPanelBorder, BorderLayout.SOUTH);
		Container c = getContentPane();
		c.add(NewAbonentMainPanel);
		cmdSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveNewAbonent();
			}
		});

		cmdCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelSave();
			}
		});
	}

	public Abonents getAbonent() {
		return abonent;
	}

	public void setAbonent(Abonents abn) {
		this.abonent = abn;
		surname.setText(abonent.getSurname());
		name.setText(abonent.getName());
		birth.setText(abonent.getBirth());
		sex.setSelectedItem(abonent.getSex());
		typeLocality.setSelectedItem(abonent.getTypeLocality());
		nameLocality.setText(abonent.getNameLocality());
		adrress.setText(abonent.getAddress());
		tel.setText(abonent.getTelephone());
	}

	private void saveNewAbonent() {
		try {
			abonent.setSurname(surname.getText());
			abonent.setName(name.getText());
			abonent.setBirth(birth.getText());
			abonent.setSex(sex.getSelectedItem().toString());
			abonent.setTypeLocality(typeLocality.getSelectedItem().toString());
			abonent.setNameLocality(nameLocality.getText());
			abonent.setAddress(adrress.getText());
			abonent.setTelephone(tel.getText());
			

			if (abonent.getId() == null) {
				int newId = new AbonentsDAO().insertAbonent(abonent);
				abonent.setId(newId);
			} else {
				new AbonentsDAO().updateAbonent(abonent);
			}
			this.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"Помилка при збереженні студента: " + e.getMessage());
		}
	}

	private void cancelSave() {
		this.setVisible(false);
	}

	
}
