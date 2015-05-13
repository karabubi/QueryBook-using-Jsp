package loginServlet;

import java.io.IOException;
import java.io.PrintWriter;


import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

@WebServlet("/loginServlet")
public
        class LoginServlet extends HttpServlet {
         //--------------------------------------------------

         protected
                 void doPost ( HttpServletRequest request,
                               HttpServletResponse response ) throws ServletException, IOException {
                  response.setContentType ("text/html");
                  // read form fields
                  String authorname = request.getParameter ("auther");


                  //  if(request.getParameter("authorname")!=null) 
                  //  { 
                  System.out.println ("auther: " + authorname);


                  // do some processing here...

                  // get response writer
                  PrintWriter writer = response.getWriter ();

                  // build HTML code






                  //  -------------------------------

//	Scanner keyboard = new Scanner(System.in);
//		String name = keyboard.nextLine();
//		System.out.println("name: " + name);
//		
                  //resp.getWriter 




                  String s2 = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> \n"
                              + "PREFIX dbpprop: <http://dbpedia.org/property/> \n"
                              + "PREFIX  g:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
                              + "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                              + "PREFIX  ontology: <http://dbpedia.org/ontology/>\n"
                              + "\n"
                              + //John Hebeler /Agatha Christie/James Hendler
                                                  
"SELECT ?Book ?name " +
"WHERE { " +
"  ?Book a dbpedia-owl:Book . " +
"  ?Book dbpprop:author ?author . " +
"  ?author dbpprop:name ?name " +
"  FILTER regex(?name, \"" + authorname + "\", \"i\") " +"}";

                 // System.out.println ("debug s2: >>>>>\n" + s2 + "\n<<<<<<");

                  Query query = QueryFactory.create (s2); //s2 = the query above
                  QueryExecution qExe = QueryExecutionFactory.sparqlService ("http://dbpedia.org/sparql", query);
                  ResultSet results = qExe.execSelect ();
                  writer.println ("<html><head><center><h1> Books Link</h1></center></head><body style=\"background: #E6E6E6\">"
                                  + "<h3> Author Name: " + authorname + "<br/><ol>");
                  if (!results.hasNext ())
                           writer.println ("<h3><font color= 'red'>The Author " + authorname + " has No Books </font></h3>" );
                  while (results.hasNext ()) {
                           QuerySolution s = results.next ();
                           String book = s.get ("Book").toString ();
                           writer.println ("<li>" + s.get ("name").toString () + "  - <a href=" + book + "> " + book + "</a></li>");
                           
                  }

                  // return response
                  writer.println ("</ol></body></html>");
                  //------------------------------



         }
}