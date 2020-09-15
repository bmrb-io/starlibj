
package EDU.bmrb.starlibj.examples;

import EDU.bmrb.starlibj.*;
import java.io.*;

public class passthru {

    static boolean demonstrate_unparse = true;
    
    public passthru () {

    }

    static public void main( String args[] ) {


	java.lang.Runtime.getRuntime().gc(); // force garbage collection.
	
	doMain( args );

	java.lang.Runtime.getRuntime().gc(); // force garbage collection.
    }


    static public void doMain(String args[]) {

	StarFileNode             sfnInput = null;
	java.io.FileInputStream  inStream = null;
	StarParser               myParser = null;
	StarUnparser             myUnparser = null;
	String                   parseFname = "";
	java.io.FileOutputStream passout1 = null;

	// For each command-line argument, re-use the parser:
	for( int argIndex = 0 ; argIndex < args.length ; argIndex++ )
	{
	    parseFname = args[ argIndex ];
	    System.out.println("Working on file: " + parseFname );
	    try
	    {
		// read in the starting input file:
		// --------------------------------
		inStream =  new java.io.FileInputStream(parseFname);

		// If the parser has not yet been initialized,
		// then make a new one, else reinitialize the existing
		// parser to use the new input stream:
		if( myParser == null )
		{   myParser = new StarParser( inStream );
		}
		else
		{   myParser.ReInit( inStream );
		}

		passout1 = new java.io.FileOutputStream(parseFname+".1");
	    }
	    catch( FileNotFoundException e)
	    {
		System.out.println("File Not found exception opening passthru.str" );
		e.printStackTrace();
	    }
	    catch( java.io.IOException e )
	    {
		System.out.println("IO exception: " + e.getMessage() );
		e.printStackTrace();
	    }
	    try {
		myParser.StarFileNodeParse(myParser);
	    }
	    catch( ParseException e)
	    {
		System.out.println("Parse Exception: " + e.getMessage() );
		e.printStackTrace();
		sfnInput = (StarFileNode) myParser.popResult();
		myUnparser = new StarUnparser( passout1 );
		myUnparser.setFormatting( true );
		myUnparser.writeOut( sfnInput, 0 );
		return ;
	    }
	    sfnInput = (StarFileNode) myParser.popResult();

	    if( demonstrate_unparse )
	    {
		// Ouptut the version we just read, nicely
		// ----------------------------------------------
		myUnparser = new StarUnparser( passout1 );
		myUnparser.setFormatting( true );
		myUnparser.writeOut( sfnInput, 0 );

		passout1 = null; // Throw it away, finalizer closes file.
		sfnInput = null;
		myUnparser = null;
	    }
	    else
	    {
		System.out.println( "SKIPPING UNPARSE STEP!" );
	    }

	    inStream = null;

	}
	myParser = null;

    }
}

