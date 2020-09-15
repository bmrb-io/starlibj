
package EDU.bmrb.starlibj.examples;

import EDU.bmrb.starlibj.*;
import java.io.*;

public class search {

    public search () {

    }

    static public void main(String args[]) {
        StarFileNode sfn = null;
        StarParser      myParser = null;
	StarUnparser    myUnparser = null;
	VectorCheckType searchResult;
	int             i;

	try
	{
	    System.out.println("Test of the starlibj library.");
	    System.out.println("Trying to read passthru.str.");
	    
	    myUnparser = new StarUnparser( System.out );
	    
	    myParser = new StarParser( new java.io.FileInputStream("search.str") );
	    // myParser.setVerbose(true);
	    myParser.StarFileNodeParse(myParser);
	    sfn = (StarFileNode) myParser.popResult();
	    System.out.println("=====Parsing Complete.=====");
	    // searching:

	    System.out.println("Searching for where _s1 = 'match val 1'" );
	    searchResult = sfn.searchByTagValue( "_s1", "match val 1" );
	    for( i = 0 ; i < searchResult.size() ; i++ )
	    {
		StarNode result = (StarNode)( searchResult.elementAt(i) );
		System.out.print(
			"Result " +
			(new Integer(i+1)).toString() +
			" : Line " + result.getLineNum() +
			", Col " +  result.getColNum() +
			" : " + result.getClass().getName() +
			" : " );
		myUnparser.setFormatting( false );
		myUnparser.writeOut( (StarNode) searchResult.elementAt(i), 3 );
		System.out.println();
	    }
	    System.out.println("-----Done with search resuls == " );
	}
	catch( ParseException e)
	{
	    System.out.println("Parse Exception: " + e.getMessage() );
	    e.printStackTrace();
	}
	catch( FileNotFoundException e)
	{
	    System.out.println("File Not found exception opening passthru.str" );
	    e.printStackTrace();
	}
    }

}

