package com.vts.prog;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Main extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	SpinnerModel modelSpinnerSp, modelSpinnerPv,modelSpinnerKp,modelSpinnerKi,modelSpinnerKd;
	JSpinner spiSp,spiPv,spiKp,spiKi,spiKd;
	JLabel lblOutPid;
	JTextArea txtLog;
	Regulateur PID1;
	int ResultatOutPID;
	String MyLog="";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Gestion de l'application
	 */
	public Main() {
		setTitle("Simu PID v1.01.00 by Vincent TORRES / Licence CC-by-sa");
		setResizable(false);
		
		
		//Creation de la vue
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 577, 611);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExitApplication();
			}
		});
		btnQuitter.setBounds(230, 554, 117, 29);
		contentPane.add(btnQuitter);
		
		JPanel panelInput = new JPanel();
		panelInput.setBounds(6, 6, 248, 149);
		panelInput.setBorder(BorderFactory.createLineBorder(Color.black));
		TitledBorder title = null;
		title = BorderFactory.createTitledBorder("Entrées des valeurs process");
		panelInput.setBorder(title);
		contentPane.add(panelInput);
		panelInput.setLayout(null);
		
		JLabel lblSp = new JLabel("Consigne SP (%):");
		lblSp.setBounds(16, 24, 118, 16);
		panelInput.add(lblSp);
		
		JLabel lblPv = new JLabel("Mesure PV (%):");
		lblPv.setBounds(16, 57, 118, 16);
		panelInput.add(lblPv);
		
		modelSpinnerSp = new SpinnerNumberModel(50, 0, 100, 5);
		spiSp = new JSpinner(modelSpinnerSp);
		spiSp.addChangeListener(new ChangeListener() {

	        @Override
	        public void stateChanged(ChangeEvent e) {
	            ChangeSp();
	        }
	    });
		spiSp.setBounds(136, 19, 79, 26);
		panelInput.add(spiSp);
		
		modelSpinnerPv = new SpinnerNumberModel(50, 0, 100, 5);
		spiPv = new JSpinner(modelSpinnerPv);
		spiPv.addChangeListener(new ChangeListener() {

	        @Override
	        public void stateChanged(ChangeEvent e) {
	            ChangePv();
	        }
	    });
		spiPv.setBounds(136, 52, 79, 26);
		panelInput.add(spiPv);
		
		JPanel panelSettings = new JPanel();
		panelSettings.setBounds(266, 6, 308, 149);
		panelSettings.setBorder(BorderFactory.createLineBorder(Color.black));
		TitledBorder titleSettings = null;
		titleSettings = BorderFactory.createTitledBorder("Entrées des paramètres PID");
		panelSettings.setBorder(titleSettings);
		contentPane.add(panelSettings);
		panelSettings.setLayout(null);
		
		JLabel lblKp = new JLabel("Bande proportionnelle:");
		lblKp.setBounds(9, 24, 161, 16);
		panelSettings.add(lblKp);
		
		JLabel lblKi = new JLabel("Coef intégral Ki:");
		lblKi.setBounds(9, 57, 161, 16);
		panelSettings.add(lblKi);
		
		JLabel lblKd = new JLabel("Coef dérivée Kd:");
		lblKd.setBounds(9, 90, 161, 16);
		panelSettings.add(lblKd);
		
		modelSpinnerKp = new SpinnerNumberModel(1, 1, 300, 1);
		spiKp = new JSpinner(modelSpinnerKp);
		spiKp.addChangeListener(new ChangeListener() {

	        @Override
	        public void stateChanged(ChangeEvent e) {
	            ChangeKp();
	        }
	    });
		spiKp.setBounds(194, 19, 79, 26);
		panelSettings.add(spiKp);
		
		modelSpinnerKi = new SpinnerNumberModel(1, 0, 300, 1);
		spiKi = new JSpinner(modelSpinnerKi);
		spiKi.addChangeListener(new ChangeListener() {

	        @Override
	        public void stateChanged(ChangeEvent e) {
	            ChangeKi();
	        }
	    });
		spiKi.setBounds(194, 52, 79, 26);
		panelSettings.add(spiKi);
		
		modelSpinnerKd = new SpinnerNumberModel(1, 0, 300, 1);
		spiKd = new JSpinner(modelSpinnerKd);
		spiKd.addChangeListener(new ChangeListener() {

	        @Override
	        public void stateChanged(ChangeEvent e) {
	            ChangeKd();
	        }
	    });
		spiKd.setBounds(194, 85, 79, 26);
		panelSettings.add(spiKd);
		
		lblOutPid = new JLabel("New label");
		lblOutPid.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutPid.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblOutPid.setBounds(107, 167, 254, 44);
		contentPane.add(lblOutPid);
		
		JButton btnCalculPid = new JButton("Calcul PID");
		btnCalculPid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculPID();
			}
		});
		btnCalculPid.setBounds(356, 175, 117, 29);
		contentPane.add(btnCalculPid);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 223, 568, 319);
		contentPane.add(scrollPane);
		
		txtLog = new JTextArea();
		txtLog.setEditable(false);
		txtLog.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		scrollPane.setViewportView(txtLog);
		
		//Création instance régulateur
		PID1 = new Regulateur(Integer.parseInt(spiSp.getValue().toString()),Integer.parseInt(spiPv.getValue().toString()),Integer.parseInt(spiKp.getValue().toString()),Integer.parseInt(spiKi.getValue().toString()),Integer.parseInt(spiKd.getValue().toString()));
		//Premier calcul pour initialiser les champs
		calculPID();
	}
	
	/**
	 * Quitter l'application
	 */
	private void ExitApplication() {
		System.exit(1);
	}
	
	/**
	 * Modification de la mesure
	 */
	private void ChangePv() {
		PID1.setPv(Integer.parseInt(spiPv.getValue().toString()));
	}
	
	/**
	 * Modification de la consigne
	 */
	private void ChangeSp() {
		PID1.setSp(Integer.parseInt(spiSp.getValue().toString()));
	}
	
	/**
	 * Modification de la bande proportionnelle
	 */
	private void ChangeKp() {
		PID1.setKp(Integer.parseInt(spiKp.getValue().toString()));
	}
	
	/**
	 * Modification du coefficient intégral
	 */
	private void ChangeKi() {
		PID1.setKi(Integer.parseInt(spiKi.getValue().toString()));
	}
	
	/**
	 * Modification du coefficient dérivée
	 */
	private void ChangeKd() {
		PID1.setKd(Integer.parseInt(spiKd.getValue().toString()));
	}
	
	/**
	 * Calcul d'une sortie PID
	 */
	private void calculPID() {
		ResultatOutPID=PID1.calculOutPID();
		lblOutPid.setText("Out PID: " + String.valueOf(ResultatOutPID) + " %");
		MyLog = MyLog+ PID1.afficheResultat();
		txtLog.setText(MyLog);
	}
}
