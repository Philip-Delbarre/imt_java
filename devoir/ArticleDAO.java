import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'acces aux donnees contenues dans la table article
 * 
 * @version 1.1
 */
public class ArticleDAO {

	/**
	 * Parametres de connexion a la base de donnees oracle URL, LOGIN et PASS sont
	 * des constantes
	 */
	final static String URL = "jdbc:mysql://localhost:3306/gs";
	final static String LOGIN = "root";
	final static String PASS = "";

	/**
	 * Constructeur de la classe
	 * 
	 */
	public ArticleDAO() {
		// chargement du pilote de bases de donnees... a noter qu'il n'est plus obligatoire de le faire explicitement 
		// car il est automatiquement charge		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.err.println(
					"Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
		}

	}

	/**
	 * Permet d'ajouter un article dans la table article la reference de l'article
	 * est produite automatiquement par la base de donnees en utilisant l'auto increment
	 * Le mode est auto-commit par defaut : chaque insertion est validee
	 * 
	 * @param nouvArticle l'article a ajouter
	 * @return le nombre de ligne ajoutees dans la table
	 */
	public int ajouter(Article nouvArticle) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;

		// connexion a la base de donnees
		try {

			// tentative de connexion
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			// preparation de l'instruction SQL, chaque ? represente une valeur
			// communiquer dans l'insertion
			// les getters permettent de recuperer les valeurs des attributs souhaites de
			// nouvArticle
			ps = con.prepareStatement(
					"INSERT INTO article (art_designation, art_pu_ht, art_qte_stock) VALUES (?, ?, ?)");
			ps.setString(1, nouvArticle.getDesignation());
			ps.setDouble(2, nouvArticle.getPuHt());
			ps.setInt(3, nouvArticle.getQteStock());

			// Execution de la requete
			retour = ps.executeUpdate();

		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du preparedStatement et de la connexion
			try {
				if (ps != null)
					ps.close();
			} catch (Exception t) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception t) {
			}
		}
		return retour;

	}

	public int modifyArticle (Article articleToChange) {
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;

		// connexion a la base de donnees
		try {

			// tentative de connexion
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			// preparation de l'instruction SQL, chaque ? represente une valeur
			// communiquer dans l'insertion
			// les getters permettent de recuperer les valeurs des attributs souhaites de
			// nouvArticle
			ps = con.prepareStatement(
					"UPDATE article SET art_designation = ?, art_pu_ht = ?, art_qte_stock = ? WHERE art_reference = ?");
			ps.setString(1, articleToChange.getDesignation());
			ps.setDouble(2, articleToChange.getPuHt());
			ps.setInt(3, 	articleToChange.getQteStock());
			ps.setInt(4, 	articleToChange.getReference());
			// Execution de la requete
			retour = ps.executeUpdate();

		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du preparedStatement et de la connexion
			try {
				if (ps != null)
					ps.close();
			} catch (Exception t) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception t) {
			}
		}
		return retour;

	}


	public int supprimer (int delArticleRef) // on lui passe juste une référence à supprimer
	
	{
		Connection con = null;
		PreparedStatement ps = null;
		int retour = 0;

		// connexion a la base de donnees
		try {

			// tentative de connexion
			con = DriverManager.getConnection(URL, LOGIN, PASS);
			// preparation de l'instruction SQL, chaque ? represente une valeur
			// communiquer dans l'insertion
			// les getters permettent de recuperer les valeurs des attributs souhaites de
			// nouvArticle
			ps = con.prepareStatement(
					"DELETE FROM article WHERE art_reference = ?");
		
			ps.setInt(1, delArticleRef);

			// Execution de la requete
			retour = ps.executeUpdate();
			System.out.println(retour);

		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du preparedStatement et de la connexion
			try {
				if (ps != null)
					ps.close();
			} catch (Exception t) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception t) {
			}
		}
		return retour;

	}

	/**
	 * Permet de recuperer un article a partir de sa reference
	 * 
	 * @param reference la reference de l'article a recuperer
	 * @return l'article
	 * @return null si aucun article ne correspond a cette reference
	 */
	public Article getArticle(int reference) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Article retour = null;

		// connexion a la base de donnees
		try {

			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM article WHERE art_reference = ?");
			ps.setInt(1, reference);

			// on execute la requete
			// rs contient un pointeur situe juste avant la premiere ligne retournee
			rs = ps.executeQuery();
			// passe a la premiere (et unique) ligne retournee
			if (rs.next())
				retour = new Article(rs.getInt("art_reference"), rs.getString("art_designation"),
						rs.getDouble("art_pu_ht"), rs.getInt("art_qte_stock"));

		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du ResultSet, du PreparedStatement et de la Connexion
			try {
				if (rs != null)
					rs.close();
			} catch (Exception t) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception t) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception t) {
			}
		}
		return retour;

	}

	/**
	 * Permet de recuperer tous les articles stockes dans la table article
	 * 
	 * @return une ArrayList d'Articles
	 */
	public List<Article> getListeArticles() {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Article> retour = new ArrayList<Article>();

		// connexion a la base de donnees
		try {

			con = DriverManager.getConnection(URL, LOGIN, PASS);
			ps = con.prepareStatement("SELECT * FROM article");

			// on execute la requete
			rs = ps.executeQuery();
			// on parcourt les lignes du resultat
			while (rs.next())
				retour.add(new Article(rs.getInt("art_reference"), rs.getString("art_designation"),
						rs.getDouble("art_pu_ht"), rs.getInt("art_qte_stock")));

		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			// fermeture du rs, du preparedStatement et de la connexion
			try {
				if (rs != null)
					rs.close();
			} catch (Exception t) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception t) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception t) {
			}
		}
		return retour;

	}

	// main permettant de tester la classe
	public static void main(String[] args) throws SQLException {

		ArticleDAO articleDAO = new ArticleDAO();
		// test de la methode ajouter
		Article a = new Article("Set de 2 raquettes de ping-pong", 149.9, 10);
		int retour = articleDAO.ajouter(a);
		System.out.println("retour: " + retour + " lignes ajoutees");


		// test de la methode getArticle
		System.out.println("***Test de la méthode getArticle de ArticleDAO***");
		Article a2 = articleDAO.getArticle(1);
		System.out.println(a2);

		// test de la methode getListeArticles
		System.out.println("***Test de la méthode getListeArticles de ArticleDAO***");
		List<Article> liste = articleDAO.getListeArticles();
		// System.out.println(liste);
		for (Article art : liste) {
			System.out.println(art.toString());
		}

	}
}
