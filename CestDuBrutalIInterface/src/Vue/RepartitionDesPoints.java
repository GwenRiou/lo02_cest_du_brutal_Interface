package Vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

//import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Canvas;
import javax.swing.JLabel;
import java.awt.Label;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Model.Etudiant;
import Model.Joueur;

import javax.swing.border.CompoundBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.TextField;
import javax.swing.JTextField;
import java.awt.Choice;
import java.awt.List;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class RepartitionDesPoints extends JFrame {

	// les composants de la vue
	private JPanel contentPane;
	private TextField configPersonnage = new TextField();
	private TextField force;
	private TextField dexterite;
	private TextField resistance;
	private TextField constitution;
	private TextField initiative;
	private TextField pointsDistribuer;
	private JTextField  nom;
	private Choice programme;

	// le joueur
	private Joueur joueur= new Joueur(0);
	
	public RepartitionDesPoints(Joueur joueur) {
		this.joueur = joueur;
		this.initFenetre();
	}

	public void initFenetre() {
		// ++++++++++++++++++++++++++++++++++++++ Panneau principal +++++++++++++++++++++++++++++++++++++++++++
		setForeground(Color.BLACK);
		setTitle("Configuration Equipe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder());
		contentPane.setBackground(Color.CYAN);
		setContentPane(contentPane);
		contentPane.setLayout(null); // politique de placement des composants dans la fenêtre
		setBounds(100, 100, 1130, 650);
		// +++++++++++++++++++++++++++++++++++++ config personnage  ++++++++++++++++++++++++++++++++++++++++++++
		configPersonnage.setBackground(Color.YELLOW);
		configPersonnage.setForeground(Color.BLACK);
		configPersonnage.setFont(new Font("Tahoma", Font.ITALIC, 20));
		configPersonnage.setBounds(426, 331, 360, 34);
		contentPane.add(configPersonnage);
		// +++++++++++++++++++++++++++++++++++++ Maitre Gobi  +++++++++++++++++++++++++++++++++++++++++++++++++
		// Panel du maitre Gobi
		JPanel panelMaitre = new JPanel();
		panelMaitre.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panelMaitre.setBackground(Color.CYAN);
		panelMaitre.setBounds(79, 102, 61, 122);
		panelMaitre.setLayout(new GridLayout(1, 1, 0, 0)); // politique de placement des composants dans ce panel
		JButton jb1 = new JButton(); // pour représenter un personnage, utilisation d'un JButton
		panelMaitre.add(jb1);
		jb1.setForeground(Color.CYAN);
		Image img1 = new ImageIcon("ressources\\maitre.png").getImage();
		jb1.setIcon(new ImageIcon(img1)); // habillage du JButon
		jb1.addActionListener(new MonEcouteurEvenements(joueur.getStudentList(), new String("Capitaine Gobi 0"),0));
		contentPane.add(panelMaitre);
		// Etiquette Capitaine Gobi
		JLabel lblNewLabel = new JLabel("Capitaine Gobi");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(51, 75, 128, 24);
		contentPane.add(lblNewLabel);
		// +++++++++++++++++++++++++++++++++++++ Les élites +++++++++++++++++++++++++++++++++++++++++++++++++++
		// Idem pour les élites
		JPanel panelElite = new JPanel();
		panelElite.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panelElite.setBackground(Color.CYAN);
		panelElite.setBounds(354, 102, 274, 122);
		panelElite.setLayout(new GridLayout(1, 4, 0, 0));
		Image img2 = new ImageIcon("ressources\\elite.png").getImage();
		JButton[] jb = new JButton[4];
		for (int i = 0; i < 4; i++) {
			jb[i] = new JButton();
			jb[i].setIcon(new ImageIcon(img2));
			panelElite.add(jb[i]);
			jb[i].addActionListener(new MonEcouteurEvenements(joueur.getStudentList(), new String("Elite " + (i+1)),i+1));
		}
		contentPane.add(panelElite);
		// Etiquette Les Elites
		JLabel lblNewLabel_1 = new JLabel("Les Elites");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(449, 77, 83, 21);
		contentPane.add(lblNewLabel_1);
		// +++++++++++++++++++++++++++++++++++++ Les étudiants +++++++++++++++++++++++++++++++++++++++++++++++++
		// Idem pour les étudiants de base
		JPanel panelEtu = new JPanel();
		panelEtu.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panelEtu.setBackground(Color.CYAN);
		panelEtu.setBounds(830, 102, 274, 411);
		panelEtu.setLayout(new GridLayout(4, 4, 2, 0));
		contentPane.add(panelEtu);
		// Etiquette Les étudiants de base
		JLabel lblNewLabel_2 = new JLabel("Les Etudiants de base");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(885, 76, 192, 20);
		contentPane.add(lblNewLabel_2);
		Image img3 = new ImageIcon("ressources\\etudiant.png").getImage();
		JButton[] jb2 = new JButton[15];
		for (int k = 0; k < 15; k++) {
			jb2[k] = new JButton();
			jb2[k].setIcon(new ImageIcon(img3));
			panelEtu.add(jb2[k]);
			jb2[k].addActionListener(	new MonEcouteurEvenements(joueur.getStudentList(), new String("Etudiant " + (k+5)),k+5));
		}
		// +++++++++++++++++++++++++++++++++++++++++ Joueur +++++++++++++++++++++++++++++++++++++++++++++++++++
		JLabel lblNewLabel_3 = new JLabel("Joueur");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_3.setBounds(254, 24, 76, 37);
		contentPane.add(lblNewLabel_3);
		nom = new JTextField();	  
		nom.setText("Entrer nom");
		nom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Nom du Joueur : " +nom.getText());
				joueur.setUserName(nom.getText());
			}
		});
		nom.setFont(new Font("Tahoma", Font.PLAIN, 24));
		nom.setBounds(336, 24, 152, 37);
		contentPane.add(nom);
		// +++++++++++++++++++++++++++++++++++++++++ Programme  ++++++++++++++++++++++++++++++++++++++++++++++++
		JLabel lblNewLabel_13 = new JLabel("Programme");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_13.setBounds(562, 24, 130, 37);
		contentPane.add(lblNewLabel_13);
		programme = new Choice();
		programme.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = programme.getItem(programme.getSelectedIndex());
				System.out.println(str);
				joueur.setProgramme(str);
			}
		});
		programme.setFont(new Font("Tahoma", Font.PLAIN, 24));
		programme.setBounds(698, 27, 72, 34);
		programme.add("ISI");
		programme.add("GM");
		programme.add("A2I");
		programme.add("RT");
		programme.add("MTE");
		programme.add("GI");
		programme.add("MM");
		contentPane.add(programme);
		// ++++++++++++++++++++++++++++++++++++++++++ Configuration des personnages ++++++++++++++++++++++++++++++++
		// Compteur des points à distribuer
		JLabel lblNewLabel_4 = new JLabel("Points à distribuer");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_4.setBounds(72, 309, 192, 26);
		contentPane.add(lblNewLabel_4);
		pointsDistribuer = new TextField();
		pointsDistribuer.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pointsDistribuer.setText(Integer.toString(joueur.getPoints())); 
		pointsDistribuer.setBounds(297, 304, 61, 37);
		contentPane.add(pointsDistribuer);

		// Force
		JLabel lblNewLabel_5 = new JLabel("Force");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_5.setBounds(193, 389, 61, 37);
		contentPane.add(lblNewLabel_5);
		force = new TextField();
		force.setFont(new Font("Tahoma", Font.PLAIN, 24));
		force.setText("0");
		force.setBounds(297, 389, 61, 37);
		contentPane.add(force);

		// Dextérité
		JLabel lblNewLabel_6 = new JLabel("Dextérité");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_6.setBounds(159, 432, 105, 37);
		contentPane.add(lblNewLabel_6);
		dexterite = new TextField();
		dexterite.setFont(new Font("Tahoma", Font.PLAIN, 24));
		dexterite.setText("0");
		dexterite.setBounds(297, 432, 61, 37);
		contentPane.add(dexterite);

		// Résistance
		JLabel lblNewLabel_7 = new JLabel("Résistance");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_7.setBounds(147, 479, 117, 37);
		contentPane.add(lblNewLabel_7);
		resistance = new TextField();
		resistance.setFont(new Font("Tahoma", Font.PLAIN, 24));
		resistance.setText("0");
		resistance.setBounds(297, 475, 61, 37);
		contentPane.add(resistance);

		// Constitution
		JLabel lblNewLabel_8 = new JLabel("Constitution");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_8.setBounds(136, 522, 128, 37);
		contentPane.add(lblNewLabel_8);
		constitution = new TextField();
		constitution.setFont(new Font("Tahoma", Font.PLAIN, 24));
		constitution.setText("0");
		constitution.setBounds(297, 518, 61, 37);
		contentPane.add(constitution);

		// Initiative
		JLabel lblNewLabel_9 = new JLabel("Initiative");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_9.setBounds(171, 569, 93, 29);
		contentPane.add(lblNewLabel_9);
		initiative = new TextField();
		initiative.setFont(new Font("Tahoma", Font.PLAIN, 24));
		initiative.setText("0");
		initiative.setBounds(297, 561, 61, 37);
		contentPane.add(initiative);




		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//on recupère l'etudiant
				String key = configPersonnage.getText();
				int id = Integer.parseInt(key.substring(key.lastIndexOf(" ")+1));		
				//On modifier l'etudiant
				Etudiant comb = joueur.getStudent(id);
				joueur.modifyCharacteristicsGui(comb, Integer.parseInt(force.getText()), Integer.parseInt(dexterite.getText()),Integer.parseInt(resistance.getText()),Integer.parseInt(constitution.getText()), Integer.parseInt(initiative.getText()));
				//On met a jour l'affichage des points
				pointsDistribuer.setText(Integer.toString(joueur.getPoints())); 
				System.out.println(comb);
			}
		});
		ok.setBounds(711, 545, 59, 45);
		contentPane.add(ok);
		ok.setFont(new Font("Tahoma", Font.PLAIN, 18));

		// ++++++++++++++++++++++++++++++++++++++++++ Valider configuration équipe ++++++++++++++++++++++++++++++
		JButton validation = new JButton("VALIDER");
		validation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Affichage des caracteristiques du joueur.
				System.out.println(joueur);
				//Affichage des etudiants du joueur
				joueur.displayAllStudent();
				// fermer la fenetre graphique				
				dispose();
			}
		});
		validation.setFont(new Font("Tahoma", Font.PLAIN, 24));
		validation.setBounds(830, 523, 270, 83);
		contentPane.add(validation);
		// ++++++++++++++++++++++++++++++++++++++++++ Habillage ++++++++++++++++++++++++++++++++++++++++++++
		// panneau configuration
		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panel.setBounds(51, 345, 769, 261);
		contentPane.add(panel);
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// classe locale pour écouter les composants associés aux combattants
	final class MonEcouteurEvenements implements ActionListener {
		private ArrayList<Etudiant> liste;
		private String key;
		private int id;

		public MonEcouteurEvenements(ArrayList<Etudiant> liste, String key,int id) {
			this.liste = liste;
			this.key = key;
			this.id= id;
		}
		
		public void actionPerformed(ActionEvent e) {
			Etudiant comb = liste.get(id);
			configPersonnage.setText(key);
			force.setText(Integer.toString(comb.getForce()));
			dexterite.setText(Integer.toString(comb.getDexterite()));
			resistance.setText(Integer.toString(comb.getResistance()));
			constitution.setText(Integer.toString(comb.getConstitution()));
			initiative.setText(Integer.toString(comb.getInitiative()));
		}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}
