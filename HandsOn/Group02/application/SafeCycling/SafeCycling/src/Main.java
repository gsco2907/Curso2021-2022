import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.JPanel;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.apache.jena.rdf.model.RDFNode;
import org.eclipse.rdf4j.query.resultio.sparqljson.SPARQLResultsJSONWriter;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Main {

	private Queries q;

	private JFrame jFrame;
	
	private JLabel lblLogo;

	private JPanel mapView;
	private JPanel imageView;
	private JFXPanel jfxPanel;
	private JFXPanel jfxPanel1;
	private WebView webViewMap;
	private WebView webViewImage;
	private String URL;

	private JMenuBar menuBar;
	private JMenu menuOption;
	private JMenuItem menuOption1;
	private JMenu menuHelp;
	private JMenuItem menuInfo;

	private JLabel lblTipo;
	private JComboBox typeBox;
	private Object [] type = {"Accidents", "Dangerous crosses", "Safe routes"};
	private JComboBox fAccidentsFieldsBox;
	private Object[] fAccidentsFields;
	private JComboBox fAccidentsIdBox;
	private Object[] fAccidentsId;
	private JComboBox fVictimasFieldsBox;
	private Object [] fVictimasFields;
	private JComboBox fVictimsIdBox;
	private Object[] fVictimsId;
	private JComboBox fVictimasFieldRolBox;
	private Object [] fVictimasFieldRol;
	private JComboBox fVictimasFieldRangeBox;
	private Object [] fVictimasFieldRange;
	private JComboBox fVictimasFieldGenderBox;
	private Object [] fVictimasFieldGender;
	private JComboBox fVictimasFieldInjuryBox;
	private Object [] fVictimasFieldInjury;
	private JComboBox fDistritosBox;
	private Object [] fDistritos = {"Distrito"};
	private JComboBox fDistritosResultsBox;
	private Object [] fDistritosResults;

	JButton btnSearch;
	private JTextArea textArea;
	private JScrollPane scrollPane;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application and queries controller.
	 */
	public Main() {
		this.q = new Queries();
		initialize();
	}

	
	public void actionPerformed(ActionEvent arg0) {

	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// Inicializaci�n a pantalla completa
		jFrame = new JFrame();
		jFrame.setBounds(100, 100, 1920, 1080);
		jFrame.getContentPane().setBackground(Color.WHITE);
		jFrame.getContentPane().setLayout(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setTitle("SafeCycling");
		jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jFrame.setVisible(true);
		
		File f = new File("logo.png");
		lblLogo = new JLabel(new ImageIcon(f.getName()));
		lblLogo.setBounds(725, 40, 470, 216);
		jFrame.getContentPane().add(lblLogo);

		resultPanel();

		menuBar();

		typeSearch();

		searchButton();

	}
	

	// ******************************************************B�SQUEDAS Y RESULTADOS******************************************************
	private void searchButton() {
		btnSearch = new JButton("Buscar");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeBox.getSelectedItem().toString().equals("Accidents")) {
					if(fAccidentsFieldsBox.isVisible() && (fAccidentsIdBox==null || !fAccidentsIdBox.isVisible()) && (fVictimasFieldsBox==null || !fVictimasFieldsBox.isVisible())){ // A�adir has place
						ArrayList<String> aux1 = new ArrayList<>();
						ArrayList<String> aux2 = new ArrayList<>();
						String res = "";
						if (!fAccidentsFieldsBox.getSelectedItem().toString().equals("hasIdAccident") && (fVictimasFieldsBox==null || !fVictimasFieldsBox.isVisible())) {
							aux1 = fromRDFNodeToArrayString(q.queryAccidentsFieldsOptions("hasIdAccident"));
							res += "hasIdAccident";
						}
						if (fAccidentsFieldsBox.getSelectedItem().toString().equals("hasPlace")) {
							aux2 = fromRDFNodeToArrayStringHasPlace(q.queryAccidentsFieldsOptions(fAccidentsFieldsBox.getSelectedItem().toString()));
						}else {
							aux2 = fromRDFNodeToArrayString(q.queryAccidentsFieldsOptions(fAccidentsFieldsBox.getSelectedItem().toString()));
						}
						res += "		" + fAccidentsFieldsBox.getSelectedItem().toString() + "\r\n\r\n";
						for(int i = 0; i < aux1.size(); i++) {
							res += aux1.get(i) + "		" + aux2.get(i) + "\r\n";
						}
						textArea.setText(res);
					} else if (fAccidentsFieldsBox.isVisible() && fAccidentsIdBox.isVisible() && fAccidentsIdBox.isVisible()){
						String res = "DETALLES DEL ACCIDENTE CON ID: " + fAccidentsIdBox.getSelectedItem().toString() + "\r\n\r\n";
						res += q.queryAccidentsId(fAccidentsIdBox.getSelectedItem().toString());
						textArea.setText(res);
					} else if (fAccidentsFieldsBox.isVisible() && fVictimasFieldsBox.isVisible() && fVictimsIdBox.isVisible()){
						String res = "DETALLES DE LA VICTIMA CON ID: " + fVictimsIdBox.getSelectedItem().toString() + "\r\n\r\n";
						res += q.queryVictimsId(fVictimsIdBox.getSelectedItem().toString());
						textArea.setText(res);
					} else if (fAccidentsFieldsBox.isVisible() && fVictimasFieldsBox.isVisible() && fVictimasFieldRangeBox.isVisible()) {
						String res = "El n�mero de v�ctimas con este filtro es de: " + q.queryVictimsFieldsResults(fVictimasFieldsBox.getSelectedItem().toString(), fVictimasFieldRangeBox.getSelectedItem().toString()).replace("^^http://www.w3.org/2001/XMLSchema#integer", "");
						textArea.setText(res);
					}else if (fAccidentsFieldsBox.isVisible() && fVictimasFieldsBox.isVisible() && fVictimasFieldGenderBox.isVisible()) {
						String res = "El n�mero de v�ctimas con este filtro es de: " + q.queryVictimsFieldsResults(fVictimasFieldsBox.getSelectedItem().toString(), fVictimasFieldGenderBox.getSelectedItem().toString()).replace("^^http://www.w3.org/2001/XMLSchema#integer", "");
						textArea.setText(res);
					} else if (fAccidentsFieldsBox.isVisible() && fVictimasFieldsBox.isVisible() && fVictimasFieldInjuryBox.isVisible()) {
						String res = "El n�mero de v�ctimas con este filtro es de: " + q.queryVictimsFieldsResults(fVictimasFieldsBox.getSelectedItem().toString(), fVictimasFieldInjuryBox.getSelectedItem().toString()).replace("^^http://www.w3.org/2001/XMLSchema#integer", "");
						textArea.setText(res);
					} else if (fAccidentsFieldsBox.isVisible() && fVictimasFieldsBox.isVisible() && fVictimasFieldRolBox.isVisible()) {
						String res = "El n�mero de v�ctimas con este filtro es de: " + q.queryVictimsFieldsResults(fVictimasFieldsBox.getSelectedItem().toString(), fVictimasFieldRolBox.getSelectedItem().toString()).replace("^^http://www.w3.org/2001/XMLSchema#integer", "");
						textArea.setText(res);
					}
				} else if (typeBox.getSelectedItem().toString().equals("Dangerous crosses")) {
					HashMap<String, String> auxMap = q.queryDistrictsOptions();
					String code = auxMap.get(fDistritosResultsBox.getSelectedItem().toString().replace(" ", "_"));
					// TestArea
					String res = "CRUCES PERTENECIENTES AL DISTRITO " + fDistritosResultsBox.getSelectedItem().toString() + "\r\n\r\n";
					res += q.queryCrossingDistrict(code);
					textArea.setText(res);

					// Imagen
					String wikimage = q.queryDistrictImage(code);
					String [] aux = wikimage.split("/");
					wikimage = aux[4];
					queryImagenDistrito(wikimage);
					
					// Mapa
					ArrayList<String> cruces = q.queryDistrictMapCrossing(code);
					searchMap(cruces);
					if (imageView != null) {
						imageView.setVisible(true);
					}
					if (mapView != null) {
						mapView.setVisible(true);
					}


				} else if (typeBox.getSelectedItem().toString().equals("Safe routes")) {
					HashMap<String, String> auxMap = q.queryDistrictsOptions();
					String code = auxMap.get(fDistritosResultsBox.getSelectedItem().toString().replace(" ", "_"));

					// TestArea
					String res = "CALLES SEGURAS PERTENECIENTES AL DISTRITO " + fDistritosResultsBox.getSelectedItem().toString() + "\r\n\r\n";
					res += q.querySafeAddressDistrict(code);
					textArea.setText(res);

					// Imagen
					String wikimage = q.queryAddressImage(code);
					String [] aux = wikimage.split("/");
					wikimage = aux[4];
					queryImagenDistrito(wikimage);
					
					// Mapa
					ArrayList<String> cruces = q.queryAddressMapCrossing(code);
					searchMap(cruces);
					if (imageView != null) {
						imageView.setVisible(true);
					}
					if (mapView != null) {
						mapView.setVisible(true);
					}
				}

			}
		});
		btnSearch.setBounds(436, 205, 97, 27);
		jFrame.getContentPane().add(btnSearch);
		btnSearch.setVisible(false);
	}
	
	
	private void searchMap(ArrayList<String> recurso) {
		// Panel para poder ver el mapa generado por la consulta
		mapView = new JPanel();
		mapView.setBackground(Color.WHITE);
		mapView.setBounds(1090, 415, 800, 605);
		jFrame.getContentPane().add(mapView);

		jfxPanel = new JFXPanel();
		mapView.add(jfxPanel);
		mapView.setVisible(false);

		Platform.runLater(() -> {
			webViewMap = new WebView();
			jfxPanel.setScene(new Scene(webViewMap));
			String querySelect = 
					"#defaultView:Map\r\n" +
					"SELECT * WHERE {\r\n";
			
			
			for(int i = 0; i < recurso.size(); i++) { 
				if(!recurso.get(i).isEmpty()) {
					querySelect += "	wd:" + recurso.get(i) + " wdt:P625 ?geo" + i + ".\r\n";
				}
			}
			
			querySelect += "}";
			
			System.out.println("QUERY MAPA (WIKIDATA):\r\n" + querySelect);
			
			querySelect = querySelect.replaceAll(" ", "%20");
			String queryEncoded = null;
			try {
				queryEncoded = URLEncoder.encode(
						querySelect, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			queryEncoded = queryEncoded.replaceAll("%25", "%");
			queryEncoded = queryEncoded.replaceAll("\\*", "%2a");
			String page = "https://query.wikidata.org/embed.html#" + queryEncoded;
			webViewMap.getEngine().load(page);
		});
	}


	private void queryImagenDistrito(String distrito) {
		String sparqlEndpoint = "https://query.wikidata.org/sparql";
		SPARQLRepository repo = new SPARQLRepository(sparqlEndpoint);

		String userAgent = "Wikidata RDF4J Java Example/0.1 (https://query.wikidata.org/)";
		repo.setAdditionalHttpHeaders( Collections.singletonMap("User-Agent", userAgent ) );

		String querySelect = 
				"SELECT distinct ?image WHERE {\r\n" + 
				"	wd:" + distrito + " wdt:P18 ?image\r\n" + 
				"}";
		
		System.out.println("QUERY IMAGEN DISTRIO (WIKIDATA):\r\n" + querySelect);

		try{
			BufferedWriter ps = new BufferedWriter(new FileWriter("wikidata.json"));
			ps.write("");
			repo.getConnection().prepareTupleQuery(querySelect).evaluate(new SPARQLResultsJSONWriter(ps));
			ps.close();
		} catch ( Exception exception ) {
			exception.printStackTrace();
		}

		String link = "";
		try (BufferedReader br = new BufferedReader(new FileReader("wikidata.json"))) {
			while ((link = br.readLine()) != null) {
				if(link.contains("value")) {
					link = link.replace("\"value\" : ", "");
					link = link.replace("\"", "");
					String[] aux = link.split("/");
					link = aux[aux.length-1];
					link = link.replace("%20", "_");
					URL = link;
				}

			}

		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		imageView = new JPanel();
		imageView.setBackground(Color.WHITE);
		imageView.setBounds(1090, 124, 800, 278);
		jFrame.getContentPane().add(imageView);

		jfxPanel1 = new JFXPanel();
		imageView.add(jfxPanel1);
		imageView.setVisible(false);

		Platform.runLater(() -> {
			webViewImage = new WebView();
			jfxPanel1.setScene(new Scene(webViewImage));
			URL = "https://commons.wikimedia.org/w/index.php?title=Special:Redirect/file/" + URL;
			webViewImage.getEngine().load(URL);
			webViewImage.setZoom(0.5);
		});

	}
	
	
	private void resultPanel() {
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 415, 1878, 605);
		jFrame.getContentPane().add(scrollPane);
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setBackground(Color.LIGHT_GRAY);
	}


	// ******************************************************FTILRO GENERAL******************************************************	
	private void typeSearch() {
		lblTipo = new JLabel("Choose your type search:");
		lblTipo.setBounds(12, 210, 163, 16);
		jFrame.getContentPane().add(lblTipo);

		typeBox = new JComboBox(type);
		typeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if (fAccidentsFieldsBox != null) {
					fAccidentsFieldsBox.setVisible(false);
				}
				if (fAccidentsIdBox != null) {
					fAccidentsIdBox.setVisible(false);
				}
				if (fVictimasFieldsBox != null) {
					fVictimasFieldsBox.setVisible(false);
				}
				if (fVictimsIdBox != null) {
					fVictimsIdBox.setVisible(false);		
				}
				if (fVictimasFieldRangeBox != null) {
					fVictimasFieldRangeBox.setVisible(false);
				}
				if (fVictimasFieldGenderBox != null) {
					fVictimasFieldGenderBox.setVisible(false);
				}
				if (fVictimasFieldInjuryBox != null) {
					fVictimasFieldInjuryBox.setVisible(false);
				}
				if (fVictimasFieldRolBox != null) {
					fVictimasFieldRolBox.setVisible(false);
				}
				if (fDistritosBox != null) {
					fDistritosBox.setVisible(false);
				}
				if (fDistritosResultsBox != null) {
					fDistritosResultsBox.setVisible(false);
				}
				filtrar(String.valueOf(typeBox.getSelectedItem()));
				btnSearch.setVisible(true);
			}
		});
		typeBox.setBounds(205, 203, 170, 30);
		jFrame.getContentPane().add(typeBox);
	}

	
	private void filtrar(String tipo) {
		switch(tipo){
		case "Accidents":
			scrollPane.setBounds(12, 415, 1878, 605);
			lblLogo.setBounds(725, 40, 470, 216);
			fAccidentsSearch();
			if (imageView != null) {
				imageView.setVisible(false);
			}
			if (mapView != null) {
				mapView.setVisible(false);
			}
			if (fAccidentsFieldsBox != null) {
				fAccidentsFieldsBox.setVisible(true);
			}
			if (fAccidentsIdBox != null) {
				fAccidentsIdBox.setVisible(false);
			}
			if (fVictimasFieldsBox != null) {
				fVictimasFieldsBox.setVisible(false);
			}
			if (fVictimsIdBox != null) {
				fVictimsIdBox.setVisible(false);		
			}
			if (fVictimasFieldRangeBox != null) {
				fVictimasFieldRangeBox.setVisible(false);
			}
			if (fVictimasFieldGenderBox != null) {
				fVictimasFieldGenderBox.setVisible(false);
			}
			if (fVictimasFieldInjuryBox != null) {
				fVictimasFieldInjuryBox.setVisible(false);
			}
			if (fVictimasFieldRolBox != null) {
				fVictimasFieldRolBox.setVisible(false);
			}
			if (fDistritosBox != null) {
				fDistritosBox.setVisible(false);
			}
			if (fDistritosResultsBox != null) {
				fDistritosResultsBox.setVisible(false);
			}
			break;
		case "Dangerous crosses":
			scrollPane.setBounds(12, 415, 1048, 605);
			lblLogo.setBounds(606, 40, 470, 216);
			fDangerousAndSafeSearch();
			if (fAccidentsFieldsBox != null) {
				fAccidentsFieldsBox.setVisible(false);
			}
			if (fAccidentsIdBox != null) {
				fAccidentsIdBox.setVisible(false);
			}
			if (fVictimasFieldsBox != null) {
				fVictimasFieldsBox.setVisible(false);
			}
			if (fVictimsIdBox != null) {
				fVictimsIdBox.setVisible(false);		
			}
			if (fVictimasFieldRangeBox != null) {
				fVictimasFieldRangeBox.setVisible(false);

			}
			if (fVictimasFieldGenderBox != null) {
				fVictimasFieldGenderBox.setVisible(false);

			}
			if (fVictimasFieldInjuryBox != null) {
				fVictimasFieldInjuryBox.setVisible(false);
			}
			if (fVictimasFieldRolBox != null) {
				fVictimasFieldRolBox.setVisible(false);
			}
			if (fDistritosBox != null) {
				fDistritosBox.setVisible(true);
			}			
			fDistritosOptionsSearch();
			break;
		case "Safe routes":
			scrollPane.setBounds(12, 415, 1048, 605);
			lblLogo.setBounds(606, 40, 470, 216);
			fDangerousAndSafeSearch();
			if (fAccidentsFieldsBox != null) {
				fAccidentsFieldsBox.setVisible(false);
			}
			if (fAccidentsIdBox != null) {
				fAccidentsIdBox.setVisible(false);
			}
			if (fVictimasFieldsBox != null) {
				fVictimasFieldsBox.setVisible(false);
			}
			if (fVictimsIdBox != null) {
				fVictimsIdBox.setVisible(false);		
			}
			if (fVictimasFieldRangeBox != null) {
				fVictimasFieldRangeBox.setVisible(false);

			}
			if (fVictimasFieldGenderBox != null) {
				fVictimasFieldGenderBox.setVisible(false);

			}
			if (fVictimasFieldInjuryBox != null) {
				fVictimasFieldInjuryBox.setVisible(false);
			}
			if (fVictimasFieldRolBox != null) {
				fVictimasFieldRolBox.setVisible(false);
			}
			if (fDistritosBox != null) {
				fDistritosBox.setVisible(true);
			}
			fDistritosOptionsSearch();
			break;
		}
	}


	// ******************************************************FILTRO ACCIDENTES******************************************************
	private void fAccidentsSearch() {
		if (fDistritosBox != null) {
			fDistritosBox.setVisible(false);
		}
		fAccidentsFields = fromRDFNodeToStringAO(q.queryAccidentsFields());
		fAccidentsFieldsBox = new JComboBox(fAccidentsFields);
		fAccidentsIdBox = new JComboBox();
		fAccidentsFieldsBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch(fAccidentsFieldsBox.getSelectedItem().toString()) {
				case "hasIdAccident":
					fAccidentsIdSearch();
					if (fAccidentsIdBox != null) {
						fAccidentsIdBox.setVisible(true);
					}
					if (fVictimasFieldsBox != null) {
						fVictimasFieldsBox.setVisible(false);
					}
					if (fVictimsIdBox != null) {
						fVictimsIdBox.setVisible(false);		
					}
					if (fVictimasFieldRangeBox != null) {
						fVictimasFieldRangeBox.setVisible(false);

					}
					if (fVictimasFieldGenderBox != null) {
						fVictimasFieldGenderBox.setVisible(false);

					}
					if (fVictimasFieldInjuryBox != null) {
						fVictimasFieldInjuryBox.setVisible(false);
					}
					if (fVictimasFieldRolBox != null) {
						fVictimasFieldRolBox.setVisible(false);
					}
					break;
				case "hasVictim":
					fAccidentsFieldsOptionsSearch();
					if (fAccidentsIdBox != null) {
						fAccidentsIdBox.setVisible(false);
					}
					if (fVictimasFieldsBox != null) {
						fVictimasFieldsBox.setVisible(true);
					}
					if (fVictimsIdBox != null) {
						fVictimsIdBox.setVisible(false);		
					}
					if (fVictimasFieldRangeBox != null) {
						fVictimasFieldRangeBox.setVisible(false);

					}
					if (fVictimasFieldGenderBox != null) {
						fVictimasFieldGenderBox.setVisible(false);

					}
					if (fVictimasFieldInjuryBox != null) {
						fVictimasFieldInjuryBox.setVisible(false);
					}
					if (fVictimasFieldRolBox != null) {
						fVictimasFieldRolBox.setVisible(false);
					}
					break;

				case "hasPlace":
					if (fAccidentsIdBox != null) {
						fAccidentsIdBox.setVisible(false);
					}
					if (fVictimasFieldsBox != null) {
						fVictimasFieldsBox.setVisible(false);
					}
					if (fVictimsIdBox != null) {
						fVictimsIdBox.setVisible(false);		
					}
					if (fVictimasFieldRangeBox != null) {
						fVictimasFieldRangeBox.setVisible(false);

					}
					if (fVictimasFieldGenderBox != null) {
						fVictimasFieldGenderBox.setVisible(false);

					}
					if (fVictimasFieldInjuryBox != null) {
						fVictimasFieldInjuryBox.setVisible(false);
					}
					if (fVictimasFieldRolBox != null) {
						fVictimasFieldRolBox.setVisible(false);
					}
					break;
				default:
					if (fAccidentsIdBox != null) {
						fAccidentsIdBox.setVisible(false);
					}
					if (fVictimasFieldsBox != null) {
						fVictimasFieldsBox.setVisible(false);
					}
					if (fVictimsIdBox != null) {
						fVictimsIdBox.setVisible(false);		
					}
					if (fVictimasFieldRangeBox != null) {
						fVictimasFieldRangeBox.setVisible(false);

					}
					if (fVictimasFieldGenderBox != null) {
						fVictimasFieldGenderBox.setVisible(false);

					}
					if (fVictimasFieldInjuryBox != null) {
						fVictimasFieldInjuryBox.setVisible(false);
					}
					if (fVictimasFieldRolBox != null) {
						fVictimasFieldRolBox.setVisible(false);
					}
				}
			}
		});
		fAccidentsFieldsBox.setBounds(205, 246, 170, 30);
		jFrame.getContentPane().add(fAccidentsFieldsBox);
		fAccidentsFieldsBox.setVisible(false);
	}

	private void fAccidentsIdSearch() {
		fAccidentsId = fromRDFNodeToStringAId(q.queryAccidentsFieldsOptions("hasIdAccident"));
		fAccidentsIdBox = new JComboBox(fAccidentsId);
		fAccidentsIdBox.setBounds(205, 289, 170, 30);
		jFrame.getContentPane().add(fAccidentsIdBox);
		fAccidentsIdBox.setVisible(false);
	}


	// ******************************************************FILTRO V�CTIMAS******************************************************
	private void fAccidentsFieldsOptionsSearch() {
		fVictimasFields = fromRDFNodeToStringAO(q.queryVictimasFields());
		fVictimasFieldsBox = new JComboBox(fVictimasFields);
		fVictimsIdSearch();
		fVictimasFieldRangeSearch();
		fVictimasFieldGenderSearch();
		fVictimasFieldInjurySearch();
		fVictimasFieldRolSearch();
		fVictimasFieldsBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch(fVictimasFieldsBox.getSelectedItem().toString()) {
				case "hasIdVictim":
					fVictimsIdBox.setVisible(true);		
					fVictimasFieldRangeBox.setVisible(false);
					fVictimasFieldGenderBox.setVisible(false);
					fVictimasFieldInjuryBox.setVisible(false);
					fVictimasFieldRolBox.setVisible(false);
					break;
				case "hasRangeAge":
					fVictimsIdBox.setVisible(false);		
					fVictimasFieldRangeBox.setVisible(true);
					fVictimasFieldGenderBox.setVisible(false);
					fVictimasFieldInjuryBox.setVisible(false);
					fVictimasFieldRolBox.setVisible(false);
					break;
				case "hasGender":
					fVictimsIdBox.setVisible(false);		
					fVictimasFieldRangeBox.setVisible(false);
					fVictimasFieldGenderBox.setVisible(true);
					fVictimasFieldInjuryBox.setVisible(false);
					fVictimasFieldRolBox.setVisible(false);
					break;
				case "gradeOfInjury":
					fVictimsIdBox.setVisible(false);		
					fVictimasFieldRangeBox.setVisible(false);
					fVictimasFieldGenderBox.setVisible(false);
					fVictimasFieldInjuryBox.setVisible(true);
					fVictimasFieldRolBox.setVisible(false);
					break;
				case "hasRole":
					fVictimsIdBox.setVisible(false);		
					fVictimasFieldRangeBox.setVisible(false);
					fVictimasFieldGenderBox.setVisible(false);
					fVictimasFieldInjuryBox.setVisible(false);
					fVictimasFieldRolBox.setVisible(true);
					break;
				}
			}
		});
		fVictimasFieldsBox.setBounds(205, 289, 170, 30);
		jFrame.getContentPane().add(fVictimasFieldsBox);
		fVictimasFieldsBox.setVisible(false);
	}
	
	
	private void fVictimsIdSearch() {
		fVictimsId = fromRDFNodeToStringAId(q.queryVictimsFieldsOptions("hasIdVictim"));
		fVictimsIdBox = new JComboBox(fVictimsId);
		fVictimsIdBox.setBounds(205, 332, 170, 30);
		jFrame.getContentPane().add(fVictimsIdBox);
		fVictimsIdBox.setVisible(false);
	}
	
	
	private void fVictimasFieldRangeSearch() {
		fVictimasFieldRange = fromRDFNodeToStringAId(q.queryVictimsFieldsOptions("hasRangeAge"));
		fVictimasFieldRangeBox = new JComboBox(fVictimasFieldRange);
		fVictimasFieldRangeBox.setBounds(205, 332, 170, 30);
		jFrame.getContentPane().add(fVictimasFieldRangeBox);
		fVictimasFieldRangeBox.setVisible(false);
	}
	

	private void fVictimasFieldRolSearch() {
		fVictimasFieldRol = fromRDFNodeToStringAId(q.queryVictimsFieldsOptions("hasRole"));
		fVictimasFieldRolBox = new JComboBox(fVictimasFieldRol);
		fVictimasFieldRolBox.setBounds(205, 332, 170, 30);
		jFrame.getContentPane().add(fVictimasFieldRolBox);
		fVictimasFieldRolBox.setVisible(false);
	}



	private void fVictimasFieldGenderSearch() {
		fVictimasFieldGender = fromRDFNodeToStringAId(q.queryVictimsFieldsOptions("hasGender"));
		fVictimasFieldGenderBox = new JComboBox(fVictimasFieldGender);
		fVictimasFieldGenderBox.setBounds(205, 332, 170, 30);
		jFrame.getContentPane().add(fVictimasFieldGenderBox);
		fVictimasFieldGenderBox.setVisible(false);
	}

	
	private void fVictimasFieldInjurySearch() {
		fVictimasFieldInjury = fromRDFNodeToStringAId(q.queryVictimsFieldsOptions("gradeOfInjury"));
		fVictimasFieldInjuryBox = new JComboBox(fVictimasFieldInjury);
		fVictimasFieldInjuryBox.setBounds(205, 332, 170, 30);
		jFrame.getContentPane().add(fVictimasFieldInjuryBox);
		fVictimasFieldInjuryBox.setVisible(false);
	}

	
	// ******************************************************FILTRO DISTRITOS******************************************************
	private void fDangerousAndSafeSearch() {
		if(fAccidentsFieldsBox != null) {
			fAccidentsFieldsBox.setVisible(false);
		}
		fDistritosBox = new JComboBox(fDistritos);
		fDistritosBox.setBounds(205, 246, 170, 30);
		jFrame.getContentPane().add(fDistritosBox);
		fDistritosBox.setVisible(false);
	}

	
	private void fDistritosOptionsSearch() {
		if(fAccidentsFieldsBox != null) {
			fAccidentsFieldsBox.setVisible(false);
		}		
		fDistritosResults = fromRDFNodeToStringDO(q.queryDistrictsOptions().keySet());
		fDistritosResultsBox = new JComboBox(fDistritosResults);
		fDistritosResultsBox.setBounds(205, 289, 170, 30);
		jFrame.getContentPane().add(fDistritosResultsBox);
		fDistritosResultsBox.setVisible(true);
	}
	
	
	// ******************************************************MEN� PROGRAMA******************************************************
	private void menuBar() {
		// Inicializamos los componentes del men�
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1902, 26);
		jFrame.getContentPane().add(menuBar);

		menuOption = new JMenu("Options");
		menuBar.add(menuOption);

		menuOption1 = new JMenuItem("Empty");
		menuOption.add(menuOption1);

		menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);

		menuInfo = new JMenuItem("About the app");
		menuInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String message = "This is the app that will make your cycling experience more pleasurable, thank you for using our app.\r\n" + 
						"\r\n" + 
						"You will not have to worry about searching for the best and safest routes in the city of Madrid. \r\n" + 
						"\r\n" + 
						"With the click of a button, you will be able to see all the accidents that have occurred in the city, as well as the dangerous crossings and the safest routes to ride your bicycle.\r\n "+
						"In addition, when looking for the dangerous crossings and the safest routes the app will show you a map of the district as well as an image of it.\r\n" + 
						"On the other hand, when searching for accidents the app will show you all the details of these incidents like the victim and direction in which it occurred.\r\n" + 
						"\r\n" + 
						"The dataset that we have used are licensed under the license CC BY-NC-SA 4.0 Internacional. There are some conditions for its reuse, like:\r\n" + 
						"- The documents can be reused for commercial and non-commercial purposes.\r\n" + 
						"- The source of the documents must be cited.\r\n" + 
						"- It�s forbidden to change the meaning of the information of the classes/attributes.\r\n" +
						"\r\n" + 
						"Remember that it can be used both as a local output and as a URI to perform the queries. It is only necessary to follow the indications of the comments of the Queries.class in the project. \r\n" +
						"We hope you can enjoy it, time to ride your bike!\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"Made with <3 by: \r\n" + 
						"\r\n" + 
						"        Jos� Luis Comino Aparicio\r\n" + 
						"        Jorge Cordob�s Delgado \r\n" + 
						"        Joaqu�n Marti Oria \r\n" + 
						"        Licia Dulce Ruiz Centner";
				JOptionPane.showMessageDialog(jFrame, message,"How to use SafeCycling",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menuHelp.add(menuInfo);
	}
	
	
	// ******************************************************CONVERSORES******************************************************
	private Object[] fromRDFNodeToStringAO(ArrayList<RDFNode> list) {
		Object[] res = null;
		ArrayList<String> aux = new ArrayList<>();
		for (RDFNode e : list) {
			String a = e.toString().substring(81);
			aux.add(a.toString());
		}
		res = aux.toArray();
		return res;
	}

	
	private Object[] fromRDFNodeToStringDO(Set<String> set) {
		Object[] res = null;
		ArrayList<String> aux = new ArrayList<>();
		for (String e : set) {
			String a = e.toString().replace("_", " ");
			aux.add(a.toString());
		}
		res = aux.toArray();
		return res;
	}


	private Object[] fromRDFNodeToStringAId(ArrayList<RDFNode> list) {
		Object[] res = null;
		ArrayList<String> aux = new ArrayList<>();
		for (RDFNode e : list) {
			aux.add(e.toString());
		}
		res = aux.toArray();
		return res;
	}
	

	private ArrayList<String> fromRDFNodeToArrayString(ArrayList<RDFNode> list) {
		ArrayList<String> res = new ArrayList<>();
		for (RDFNode e : list) {
			res.add(e.toString());
		}
		return res;
	}
	
	
	private ArrayList<String> fromRDFNodeToArrayStringHasPlace(ArrayList<RDFNode> list) {
		ArrayList<String> res = new ArrayList<>();
		for (RDFNode e : list) {
			res.add(e.toString().replace("http://www.preventbicyleaccidents-app.es/group02/resources/Address/CALLE_", "").replace("_", " "));
		}
		return res;
	}
	
}
