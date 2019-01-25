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

/**
 *
 * @author Asanka
 */
public class JensInferenceUsingOWL {
    public static void printStatements(Model m, Resource s, Property p, Resource o) {
        for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
        }
    }
    public static void main(String[] args) {
        Model schema = FileManager.get().loadModel("data-rule/updatedrules/telephone.owl");//owlDemoSchema.xml");
        Model data = FileManager.get().loadModel("data-rule/updatedrules/tp.rdf");//owlDemoData.xml");
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(schema);
        InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
        Resource nForceAllion = infmodel.getResource("http://www.semanticweb.org/symantic/ontologies/2019/0/vehicledataset/Dilantha_Malagamuwa");
        Resource nForce = infmodel.getResource("http://www.semanticweb.org/symantic/ontologies/2019/0/vehicledataset/Bugati");
        
        System.out.println("unknownMB *:");
        
        printStatements(infmodel, nForceAllion, null, null);
        
        printStatements(infmodel, nForce, null, null);
        
        executeRuleBaseReasoining();
        
    }
    
    
    public static void executeRuleBaseReasoining() 
	{
    	
    	//link:https://jena.apache.org/documentation/inference/
    	System.out.println("---------- Rule base resoning -----------");
    	Model model = ModelFactory.createDefaultModel();
		model.read( "data-rule/updatedrules/rulebasedInferenceDataSet.rdf" );
//		model.read( "data-rule/updatedrules/tp.rdf" );
		
		Reasoner reasoner = new GenericRuleReasoner( Rule.rulesFromURL( "data-rule/updatedrules/rules.txt" ) );
		
		InfModel infModel = ModelFactory.createInfModel( reasoner, model );

		StmtIterator it = infModel.listStatements();
		
		while ( it.hasNext() )
		{
			Statement stmt = it.nextStatement();
			
			Resource subject = stmt.getSubject();
			Property predicate = stmt.getPredicate();
			RDFNode object = stmt.getObject();

			System.out.println( subject.toString() + " " + predicate.toString() + " " + object.toString() );
		}
	}
    
}