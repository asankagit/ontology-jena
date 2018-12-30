package org.apache.jena.example.helloworld;
//https://tutorial-academy.com/jena-reasoning-with-rules/

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

public class JenaReasoningWithRules
{
	public static void main(String[] args) 
	{
		Model model = ModelFactory.createDefaultModel();
		model.read( "data-rule/dataset.n3" );
		
		Reasoner reasoner = new GenericRuleReasoner( Rule.rulesFromURL( "data-rule/rules.txt" ) );
		
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
