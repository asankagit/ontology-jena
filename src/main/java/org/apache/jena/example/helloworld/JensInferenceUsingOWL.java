//package org.apache.jena.example.helloworld;
//
//public class JensInferenceUsingOWL {
//
//}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.jena.example.helloworld;

import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.jena.assembler.JA;//.reasoner;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;
//import static org.apache.jena.vocabulary.JenaModelSpec.reasoner;


public class JensInferenceUsingOWL {
    public static void printStatements(Model m, Resource s, Property p, Resource o) {
    	
        CommandLineTable st = new CommandLineTable();
        st.setShowVerticalLines(true);
        st.setHeaders("Subject", "Predicate", "Object");
                
        for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
            Statement stmt = i.nextStatement();
            st.addRow(stmt.getSubject().toString(), stmt.getPredicate().toString(), stmt.getObject().toString());
        }     
        st.print();
    }
    public static void main(String[] args) {
    	
    	
    	System.out.println("******** JENA REASONING ********************");
        Model schema = FileManager.get().loadModel("data-rule/updatedrules/vehicleOnto.owl");
        Model data = FileManager.get().loadModel("data-rule/updatedrules/datasetSL.rdf");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(schema);
        InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
        
        try {
        System.out.println("\n");
        System.out.println("-------Inferences using GENERIC REASONER -------");
        executeOWLGenericResoner(infmodel);
        
        System.out.println("\n");
        System.out.println("-------Inferences using RULE BASED REASONER -------");
        executeRuleBaseReasoining();
        
        }catch(Exception ex) {
        
        	System.out.println("Couldn't get inferences. Check File paths before proceeding....");
        }
        
    }
    
    public static void executeOWLGenericResoner(InfModel infmodel) 
	{
    	ArrayList<String> subs = getInfSubject();
    	
    	for(int i=0;i<subs.size();i++) {
    		Resource nf = infmodel.getResource(subs.get(i));
    		printStatements(infmodel, nf, null, null);
    	}
    	
    	
//    	Resource nForceAllion = infmodel.getResource("http://www.semanticweb.org/symantic/ontologies/2019/0/vehicledataset/Dilantha_Malagamuwa");
//      Resource nForce = infmodel.getResource("http://www.semanticweb.org/symantic/ontologies/2019/0/vehicledataset/Bugati");
//      printStatements(infmodel, nForceAllion, null, null);
//      printStatements(infmodel, nForce, null, null);
	}
    
    public static void executeRuleBaseReasoining() 
	{
    	
    	//link:https://jena.apache.org/documentation/inference/
    	Model model = ModelFactory.createDefaultModel();
		model.read( "data-rule/updatedrules/datasetUS.rdf");
		
		Reasoner reasoner = new GenericRuleReasoner( Rule.rulesFromURL( "data-rule/updatedrules/rules.txt" ) );
		
		InfModel infModel = ModelFactory.createInfModel( reasoner, model );

		StmtIterator it = infModel.listStatements();
		
		 CommandLineTable st = new CommandLineTable();
	     st.setShowVerticalLines(true);
	     st.setHeaders("Subject", "Predicate", "Object");
		
		while ( it.hasNext() )
		{
			Statement stmt = it.nextStatement();
			
			Resource subject = stmt.getSubject();
			Property predicate = stmt.getPredicate();
			RDFNode object = stmt.getObject();
			st.addRow(subject.toString(),predicate.toString(),object.toString());
		}
        st.print();

	}
    
    public static ArrayList<String> getInfSubject() {
    	
    	ArrayList<String> listOf = new ArrayList<String>();
    	
    	String rules = "[rule1: (?a rdf:type \"http://www.semanticweb.org/symantic/ontologies/2019/0/vehicle#Person\") -> (?a eg:p ?b)]";
    	Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
    	reasoner.setDerivationLogging(true);
    	
    	Model data = FileManager.get().loadModel("data-rule/updatedrules/datasetSL.rdf");
    	InfModel inf = ModelFactory.createInfModel(reasoner, data);
    	
    	PrintWriter out = new PrintWriter(System.out);
    	for (StmtIterator i = inf.listStatements(); i.hasNext(); ) {
    	    Statement s = i.nextStatement();
    	   //System.out.println(s.getSubject());
    	   listOf.add(s.getSubject().toString());
    	}
    	out.flush();
    	
    	return listOf;
    }
    
    
}