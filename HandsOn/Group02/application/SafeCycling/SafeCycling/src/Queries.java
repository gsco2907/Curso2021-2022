import java.util.ArrayList;
import java.util.HashMap;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;


public class Queries {

	Model model = ModelFactory.createDefaultModel();

	//********AVISO:********
	// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
	//final static String endPointURI = "http://localhost:9090/sparql";

	
	public Queries() {
		model.read("output-datasets-with-links.nt");
		//queryAccidentsFields();
	}
	

	public ArrayList<RDFNode> queryAccidentsFields () {
		ArrayList<RDFNode> accidentsFields = new ArrayList<>();
		String query = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
						"SELECT DISTINCT ?p WHERE {\r\n" + 
						"	?s rdf:type vocab:Accident .\r\n" +
						"	?s ?p ?o.\r\n" +
						"	FILTER (?p != rdf:type) .\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				accidentsFields.add(results.nextSolution().get("p"));    
			}
		}
		finally {
			qexec.close();

		}
		return accidentsFields;
	}
	

	public ArrayList<RDFNode> queryAccidentsFieldsOptions (String vocab) {
		ArrayList<RDFNode> accidentsFieldsOptions = new ArrayList<>();
		String query = "";
		if (vocab.equals("hasIdAccident")) {
			query = 
					"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
							"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
							"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
							"SELECT DISTINCT ?o WHERE {\r\n" + 
							"	?s rdf:type vocab:Accident .\r\n" +
							"	?s vocab:" + vocab + " ?o.\r\n" +
							"}";
		} else {
			query = 
					"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
							"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
							"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
							"SELECT ?o WHERE {\r\n" + 
							"	?s rdf:type vocab:Accident .\r\n" +
							"	?s vocab:" + vocab + " ?o.\r\n" +
							"}";
		}

		System.out.println("�ltima query realizada: \r\n" + query);


		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				accidentsFieldsOptions.add(results.nextSolution().get("o"));    
			}
		}
		finally {
			qexec.close();

		}
		return accidentsFieldsOptions;
	}

	
	public String queryAccidentsId (String id) {
		String res = "";
		String query = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX accident: <http://www.preventbicyleaccidents-app.es/group02/resources/Accident/>\r\n" +
						"SELECT DISTINCT ?p ?o WHERE {\r\n" + 
						"	accident:" + id + " ?p ?o.\r\n" +
						"	FILTER (?p != rdf:type) .\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution aux = results.nextSolution();
				res += aux.get("p").toString().substring(81) + "		" + aux.get("o") + "\r\n";   
			}
		}
		finally {
			qexec.close();
		}
		return res;
	}

	
	public ArrayList<RDFNode> queryVictimasFields () {
		ArrayList<RDFNode> accidentsFields = new ArrayList<>();
		String query = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
						"PREFIX victim: <http://www.preventbicyleaccidents-app.es/group02/resoruces/Person/>" +
						"SELECT DISTINCT ?p WHERE {\r\n" + 
						"	?s rdf:type vocab:Person. \r\n" +
						"	?s ?p ?o.\r\n" +
						"	FILTER (?p != rdf:type) .\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				accidentsFields.add(results.nextSolution().get("p"));    
			}
		}
		finally {
			qexec.close();

		}
		return accidentsFields;
	}

	
	public String queryVictimsFieldsResults (String vocab, String object) {
		String victimsFieldsResults = "";
		String query = "";
		query = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
						"SELECT (COUNT(*) AS ?count) WHERE {\r\n" + 
						"	?s rdf:type vocab:Person .\r\n" +
						"	?s vocab:" + vocab + " \"" + object + "\".\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				victimsFieldsResults = results.nextSolution().get("count").toString();    
			}
		}
		finally {
			qexec.close();

		}
		return victimsFieldsResults;
	}


	public String queryVictimsId (String id) {
		String res = "";
		String query = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX victim: <http://www.preventbicyleaccidents-app.es/group02/resoruces/Person/>\r\n" +
						"SELECT DISTINCT ?p ?o WHERE {\r\n" + 
						"	victim:victima" + id + " ?p ?o.\r\n" +
						"	FILTER (?p != rdf:type) .\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution aux = results.nextSolution();
				res += aux.get("p").toString().substring(81) + "		" + aux.get("o") + "\r\n";   
			}
		}
		finally {
			qexec.close();
		}
		return res;
	}

	
	public ArrayList<RDFNode> queryVictimsFieldsOptions (String vocab) {
		ArrayList<RDFNode> victimsFieldsOptions = new ArrayList<>();
		String query = "";

		query = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
						"SELECT DISTINCT ?o WHERE {\r\n" + 
						"	?s rdf:type vocab:Person .\r\n" +
						"	?s vocab:" + vocab + " ?o.\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				victimsFieldsOptions.add(results.nextSolution().get("o"));    
			}
		}
		finally {
			qexec.close();

		}
		return victimsFieldsOptions;
	}


	public HashMap<String, String> queryDistrictsOptions () {
		HashMap<String, String> accidentsFields = new HashMap<>();
		String query = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
						"SELECT DISTINCT ?o1 ?o2 WHERE {\r\n" + 
						"	?s ?p vocab:District .\r\n" +
						"	?s vocab:hasDistrictName ?o1.\r\n" +
						"	?s vocab:hasCode ?o2.\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution aux = results.nextSolution(); 
				accidentsFields.put(aux.get("o1").toString(), "\"" + aux.get("o2").toString().replace("^^", "\"^^<").replace("int", "int>"));    
			}
		}
		finally {
			qexec.close();

		}
		return accidentsFields;
	}


	public String queryCrossingDistrict (String code) {
		String res = "";
		String query = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
						"SELECT * WHERE {\r\n" + 
						"	?s rdf:type vocab:Crossing. \r\n" +
						"	?s vocab:crossIn ?o.\r\n" +
						"	?o vocab:hasCode "+ code + ".\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				res += results.nextSolution().get("s").toString() + "\r\n";  
			}
		}
		finally {
			qexec.close();

		}
		return res;
	}

	
	public String querySafeAddressDistrict (String code) {
		String res = "";
		String query = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
						"SELECT * WHERE {\r\n" + 
						"	?s rdf:type vocab:Address. \r\n" +
						"	?s vocab:belongsTo ?o.\r\n" +
						"	?o vocab:hasCode "+ code + ".\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				res += results.nextSolution().get("s").toString().replace("http://www.preventbicyleaccidents-app.es/group02/resources/Address/", "").replace("_", " ") + "\r\n";  
			}
		}
		finally {
			qexec.close();

		}
		return res;
	}


	public String queryDistrictImage (String distrito) {
		String res = "";
		String query = 
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" +
						"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
						"PREFIX district: <http://www.preventbicyleaccidents-app.es/group02/resources/District/>\r\n" + 
						"SELECT DISTINCT ?o WHERE { \r\n" + 
						"	?s rdf:type vocab:District. \r\n" +
						"	?s vocab:hasCode "+ distrito + ".\r\n" +
						"	?s owl:sameAs ?o.\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				res += results.nextSolution().get("o").toString();  
			}
		}
		finally {
			qexec.close();

		}
		return res;
	}

	
	public ArrayList<String> queryDistrictMapCrossing (String distrito) {
		ArrayList<String> cruces = new ArrayList<>();
		String query = 
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" +
						"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
						"PREFIX district: <http://www.preventbicyleaccidents-app.es/group02/resources/District/>\r\n" + 
						"SELECT DISTINCT ?o WHERE { \r\n" + 
						"	?s rdf:type vocab:District. \r\n" +
						"	?s vocab:hasCode "+ distrito + ".\r\n" +
						"	?s owl:sameAs ?o.\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution aux = results.nextSolution();
				cruces.add(aux.get("o").toString().replace("https://www.wikidata.org/wiki/", ""));  
			}
		}
		finally {
			qexec.close();

		}

		return cruces;


	}


	public String queryAddressImage (String distrito) {
		String res = "";
		String query = 
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" +
						"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
						"PREFIX district: <http://www.preventbicyleaccidents-app.es/group02/resources/District/>\r\n" + 
						"SELECT DISTINCT ?o WHERE { \r\n" + 
						"	?s rdf:type vocab:District. \r\n" +
						"	?s vocab:hasCode "+ distrito + ".\r\n" +
						"	?s owl:sameAs ?o.\r\n" +
						"}";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				res += results.nextSolution().get("o").toString();  
			}
		}
		finally {
			qexec.close();

		}
		return res;
	}

	
	public ArrayList<String> queryAddressMapCrossing (String distrito) {
		ArrayList<String> address = new ArrayList<>();
		String query = 
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" +
						"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" +
						"PREFIX vocab: <http://www.preventbicyleaccidents-app.es/group02/ontology/bicycletrafficaccident#>\r\n" +
						"PREFIX district: <http://www.preventbicyleaccidents-app.es/group02/resources/District/>\r\n" + 
						"SELECT ?o WHERE { \r\n" + 
						"	?s rdf:type vocab:Address. \r\n" +
						"	?s vocab:belongsTo ?o1.\r\n" +
						"	?o1 vocab:hasCode "+ distrito + ".\r\n" +
						"	?s owl:sameAs ?o.\r\n" +
						"}\r\n" +
						"LIMIT 100";

		System.out.println("�ltima query realizada: \r\n" + query);

		// DESCOMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		//QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointURI, query);

		// COMENTAR EN CASO DE REALIZAR LAS QUERIES A PARTIR DE UNA URI DONDE SE ALOJE NUESTROS DATASETS
		Query q = QueryFactory.create(query);

		QueryExecution qexec = QueryExecutionFactory.create(q, model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution aux = results.nextSolution();
				address.add(aux.get("o").toString().replace("https://wikidata.org/entity/", ""));  
			}
		}
		finally {
			qexec.close();

		}

		return address;	
	}	

}
