
package EDU.bmrb.starlibj.examples;

import EDU.bmrb.starlibj.*;
import java.io.*;

public class change {

    public change () {

    }

    static public void main(String args[]) {
        StarFileNode sfn = null;
        StarParser      myParser = null;
	StarUnparser    myUnparser = null;
	VectorCheckType searchResult;
	int             i;
	String          str = null;

	try
	{
	    System.out.println("Test of the starlibj library.");
	    System.out.println("Trying to read passthru.str.");
	    
	    myUnparser = new StarUnparser( System.out );
	    
	    myParser = new StarParser( new java.io.FileInputStream("change.str") );
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
		if( Class.forName(StarValidity.pkgName()+".DataValueNode").
			isInstance(result) )
		{   str = ( (DataValueNode)result ).getValue();
		}
		else
	        {   str = ( (DataItemNode)result ).getValue();
		}

		// Testing: If changing the string makes the
		// value itself change, then I've got to change
		// some things:
		str.concat( "(EXTRA STUFF)" );

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
	catch( ClassNotFoundException e)
	{
	    System.out.println("Class Not found: " + e.getMessage() );
	    e.printStackTrace();
	}
    }

}

