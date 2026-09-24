
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.util.List;


/**
 * Classe ArticleFenetre
 * Définit et ouvre une fenetre qui :
 *    - Permet l'insertion d'un nouvel article dans la table article via
 * la saisie des valeurs de désignation, prix et quantité en stock
 *    - Permet l'affichage de tous les articles une zone de texte
 *    
 *    Pour aller plus loin : 
 *    http://docs.oracle.com/javase/tutorial/uiswing/components/frame.html
 *    http://docs.oracle.com/javase/tutorial/uiswing/components/panel.html
 *    Différents types de composants graphiques sont disponibles
 *    http://docs.oracle.com/javase/tutorial/uiswing/components/componentlist.html
 *    Sans oublier la référence d'ouvrage utilisée dans le cours "programmer avec Java"
 *    
 * @version 1.2
 * */


public class ArticleFenetre extends JFrame implements ActionListener
{
	/**
	 * numero de version pour classe serialisable
	 * Permet d'eviter le warning "The serializable class ArticleFenetre does not declare a static final serialVersionUID field of type long"
	 */
	private static final long serialVersionUID = 1L; 
	
	/**
	 * conteneur : il accueille les differents composants graphiques de ArticleFenetre
	 */
	private JPanel containerPanel;		
	
	/**
	 * zone de texte pour le champ designation
	 */

	private JTextField textFieldDesignation;	
	
	/**	
	 * zone de texte pour le prix unitaire hors taxe
	 * 	     	
	 */
	private JTextField textFieldRef;	
	
	/**	
	 * zone de texte pour la référence de l'article en base 
	 * 	     	
	 */
	private JTextField textFieldPuHt;
	/**
	 * zone de texte pour la quantite en stock
	 */
	private JTextField textFieldQteStock;	 	
	
	/**
	 * label reference
	 */
	private JLabel labelReference;	 			
	
	/**
	 * label designation
	 */
	private JLabel labelDesignation; 		
	
	/**
	 * label prix unitaire hors taxe
	 */
	private JLabel labelPu_ht;	     		
	
	/**
	 * label quantité en stock
	 */
	private JLabel labelQtestock;	 		
	/**
	 * bouton d'envoi de l'article
	 */
	private JButton boutonEnvoi;	 	
	/**
	 *  bouton qui permet d'afficher tous les articles
	 */
	private JButton boutonAffichageTousLesArticles;	

	/**
	 * Button pour afficher juste un article
	 */
	 private JButton boutonAfficherUnArticle;	
	
	/**
	 * Button pour supprimer un article selon ref
	 */
	 private JButton boutonSupprimerUnArticle;	

	/**
	 * Button pour modifier un article selon ref
	 */
	 private JButton boutonModifierUnArticle;	
	
	
	 /**
	 * Zone de texte pour afficher les articles
	 */
	JTextArea zoneTextListArticle;
	/**
	 * Zone de défilement pour la zone de texte
	 */
	JScrollPane zoneDefilement;
	/**
	 * instance de ArticleDAO permettant les accès à la base de données
	 */
	private ArticleDAO monArticleDAO;
	
	/**
	 * Constructeur
	 * Définit la fenêtre et ses composants - affiche la fenêtre
	 */

    public ArticleFenetre()
{
    // On instancie la classe ArticleDAO
    this.monArticleDAO = new ArticleDAO();
    
    // Titre et dimensions
    this.setTitle("Article");
    this.setSize(650, 520);

    // Conteneur principal (vertical)
    containerPanel = new JPanel();
    containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
    containerPanel.setBackground(Color.PINK);

    // Instanciation des composants graphiques
    textFieldDesignation = new JTextField();
    textFieldRef = new JTextField(); 
    textFieldPuHt = new JTextField();
    textFieldQteStock = new JTextField();
    // déclaration des boutons 
    boutonEnvoi = new JButton("envoyer l'ajout d'un article");
    boutonAffichageTousLesArticles = new JButton("afficher tous les articles");
    boutonAfficherUnArticle = new JButton("affiche un article");
	boutonSupprimerUnArticle = new JButton("supprime article");
	boutonModifierUnArticle = new JButton("modifie article");

    ////////// Les Labels  //////////////// 
    labelReference = new JLabel("La Référence");
    labelDesignation = new JLabel("Désignation :");
    labelPu_ht = new JLabel("Prix unitaire HT :");
    labelQtestock = new JLabel("Quantité en stock :");

    zoneTextListArticle = new JTextArea(8, 20);
    zoneTextListArticle.setEditable(false); 
    zoneDefilement = new JScrollPane(zoneTextListArticle); 

    // --- Champs de saisie ---
    containerPanel.add(labelDesignation);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    containerPanel.add(textFieldDesignation);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    containerPanel.add(labelReference);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    containerPanel.add(textFieldRef);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    containerPanel.add(labelPu_ht);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    containerPanel.add(textFieldPuHt);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    containerPanel.add(labelQtestock);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    containerPanel.add(textFieldQteStock);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    // =========================================================================
    // AJOUT DU BOUTON ENVOYER DIRECTEMENT DANS LE CONTENEUR PRINCIPAL
    // =========================================================================
    containerPanel.add(boutonEnvoi);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    // =========================================================================
    // PANNEAU DÉDIÉ POUR LES 4 BOUTONS D'AFFICHAGE CÔTE À CÔTE
    // =========================================================================
    JPanel boutonsPanel = new JPanel(); // besoin d'un nouveau pannel pour le coller dans le principal
    boutonsPanel.setLayout(new BoxLayout(boutonsPanel, BoxLayout.LINE_AXIS)); // cette fois l'Alignement est 
	// horizontal pour fixer les bouttons les uns à coté des autres
    boutonsPanel.setOpaque(false); // Conserve la couleur de fond rose

    boutonsPanel.add(boutonAfficherUnArticle);
    boutonsPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Espace entre les boutons
    boutonsPanel.add(boutonAffichageTousLesArticles);
 	boutonsPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Espace entre les boutons
    boutonsPanel.add(boutonSupprimerUnArticle);
	boutonsPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Espace entre les boutons
	boutonsPanel.add(boutonModifierUnArticle);

    // Ajout du sous-panneau de boutons au panneau principal
    containerPanel.add(boutonsPanel);
    containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    // --- Zone d'affichage (JScrollPane) ---
    containerPanel.add(zoneDefilement);

    // Marge autour de la fenêtre
    containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Écouteurs d'événements
    boutonEnvoi.addActionListener(this);
    boutonAffichageTousLesArticles.addActionListener(this);
    boutonAfficherUnArticle.addActionListener(this);
	boutonSupprimerUnArticle.addActionListener(this);
	boutonModifierUnArticle.addActionListener(this);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(containerPanel);
    this.setVisible(true);
}
	
	/**
	 * Gère les actions réalisées sur les boutons
	 *
	 */
	public void actionPerformed(ActionEvent ae)
	{
		int retour; // code de retour de la classe ArticleDAO
		
		try {
			if(ae.getSource()==boutonEnvoi) // Ajout un article
			{
				//on crée l'objet message
				Article a=new Article(this.textFieldDesignation.getText(), Double.parseDouble(this.textFieldPuHt.getText()), Integer.parseInt(this.textFieldQteStock.getText()));
				// je rajoute juste à la création de l'objet Article la quantité en stock qui est saisie dans le champ texte
				//on demande à la classe de communication d'envoyer l'article dans la table article
				retour = monArticleDAO.ajouter(a);
				// affichage du nombre de lignes ajoutées
				// dans la bdd pour vérification
				System.out.println("" + retour + " ligne ajoutée ");
			}
			else if(ae.getSource()==boutonAffichageTousLesArticles)
			{
				// on efface l'ancien affichage
				zoneTextListArticle.setText("");
				// on demande à la classe ArticleDAO d'ajouter le message
				// dans la base de données
				List<Article> liste = monArticleDAO.getListeArticles();
			
				//on affiche dans la console du client les articles reçus
				for(Article a : liste)
				{
					 zoneTextListArticle.append(a.toString());
				     zoneTextListArticle.append("\n");
					//Pour afficher dans la console : System.out.println(a.toString());	
				}
			}

			else if(ae.getSource()==boutonAfficherUnArticle) // affiche l'article
			{
				// on efface l'ancien affichage
				zoneTextListArticle.setText("");
				// on demande à la classe ArticleDAO d'ajouter le message
				// dans la base de données
				int collecteRef = Integer.parseInt(textFieldRef.getText().trim()); 
				if (collecteRef > 0 ||collecteRef < 50)
					{
						Article unicArticle = monArticleDAO.getArticle( collecteRef);
						//on affiche dans la zone de texte l'article sélectionner 
						zoneTextListArticle.append("\n =========================");
						zoneTextListArticle.append(unicArticle.toString());
						zoneTextListArticle.append("\n =========================");
						//Pour afficher dans la console : System.out.println(a.toString());
					}
				else {		
				
					System.out.println( "la saisie est erronée ou aucun article n'a trouvé avec cette ref");
				}

				}
				else if(ae.getSource()==boutonSupprimerUnArticle)
				{
						zoneTextListArticle.setText("");
						int collecteRef = Integer.parseInt(textFieldRef.getText().trim()); 
						if (collecteRef > 0) // besoin de savoir si une reférence a été saisi
						{
							Article affiche = monArticleDAO.getArticle(collecteRef);
							retour = monArticleDAO.supprimer(collecteRef);
							System.out.print(retour);
							zoneTextListArticle.append(affiche.toString() + " a été supprimé");
						
						}
						else { zoneTextListArticle.append("\n  article non supprimé ");}

				}
		
		
			
			
			else if(ae.getSource()==boutonModifierUnArticle)
				{
					//avant de modifier il faut tester si l'article est en base
					// on s'appuie sur la ref qui si l'est en base retour un Article via la classe Article DAO
					zoneTextListArticle.setText("");

					
					int collecteRef = Integer.parseInt(textFieldRef.getText().trim());
					String designation = (String) textFieldDesignation.getText().trim();
					double prixUnitaire = Double.parseDouble(textFieldPuHt.getText().trim());
					int stock = Integer.parseInt(textFieldQteStock.getText().trim());
					System.out.println(collecteRef);
					Article unicArticle = monArticleDAO.getArticle( collecteRef);

					if ((collecteRef > 0 ||collecteRef < 50) && unicArticle != null) 
						// mon article existe dans la base de données alors on peut le modifier
						{
						Article ArticleAToModify =   new Article(collecteRef, designation, prixUnitaire, stock);
						retour = monArticleDAO.modifyArticle(ArticleAToModify);
						
						//on affiche dans la console du client les articles reçus
						zoneTextListArticle.append(ArticleAToModify.toString());
						zoneTextListArticle.append("\n a été modifié ==========");
						//Pour afficher dans la console : System.out.println(a.toString());
					}
					else
						{
							zoneTextListArticle.append(unicArticle.toString() + " n'a pas changé");
						}
				}
		
			else {
				zoneTextListArticle.append("\n  les opérations demandées n'ont pas aboutie . . .");
			}

	}
			
				catch (Exception e) {
			System.err.println("Veuillez contrôler vos saisies");
		}
		
	}

	
	public static void main(String[] args)
	{
		new ArticleFenetre();
    }

}
