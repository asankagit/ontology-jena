package org.apache.jena.example.helloworld;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;



import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD;

public class XmlDataWrite {

	public static void main(String[] args) {
		   Model m = ModelFactory.createDefaultModel();
		   
	        m.read("C:/Users/DELL/Desktop/Car.xml", "RDF/XML");
	        String NS="http://www.semanticweb.org/dell/ontologies/2018/11/untitled-ontology-8#Toyota";

	        Resource r = m.createResource(NS+"premeo");//like subject
	        Property p1 = m.createProperty(NS+"lname");
	        Property p2 = m.createProperty(NS+"email");
	        Property p3 = m.createProperty(NS+"fname");
	        Property p4 = m.createProperty(NS+"mfd");
	        m.add(r, RDFS.subClassOf, NS);

	        
	        r.addProperty(p1, "made in Japan", XSDDatatype.XSDstring);
	        r.addProperty(p2, "ab2a@gmail.com", XSDDatatype.XSDstring);
	        r.addProperty(p3, "20187", XSDDatatype.XSDstring);
	        r.addProperty(p4, "2018", XSDDatatype.XSDstring);
	      //   m.write(System.out,"thurtle");
	          try {
				m.write(new FileOutputStream("C:/Users/DELL/Desktop/Car.xml"), "RDF/XML");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//	          RDFDataMgr.write(System.out, m, Lang.TURTLE); 
	          m.remove(m.createResource("C:/Users/DELL/Desktop/Car.xml#Toyota"),
                      VCARD.FN,
                      m.createLiteral("user8"));
	          RDFDataMgr.write(System.out, m, Lang.TTL);//add syblings 
	        
	}
	
	public static void addSubClasss() {
		
	}
}



/*
 * add sub class:https://stackoverflow.com/questions/20222080/rdf-xml-how-to-define-rdfs-classes-subclasses-and-link-them-as-a-type
 * */
