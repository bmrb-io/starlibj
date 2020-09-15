
package EDU.bmrb.starlibj.examples;

import EDU.bmrb.starlibj.*;
import java.io.*;

public class testSearch {

    static private StarParser myParser;  // make it static

    public testSearch ()
    {
	myParser = null;  // Must be sure it starts as null, for the
	                  // logic in doMain to work.  Must also make sure
	                  // that there is only one StarParser instance in
	                  // the whole program.
    }

    static public void main( String args[] )
    {
	String fakeArgs[] = new String[1];

	// This part demonstrates how to have two passes that use
	// the star parser on different files.  Look into
	// "doMain" to see how the creating of the StarParser works.
	System.out.println( "Now doing pass 1..." );
	fakeArgs[0] = new String( "testSearch_pass1.str" );
	doMain( fakeArgs );
	System.out.println( "Now doing pass 2..." );
	fakeArgs[0] = new String( "testSearch_pass2.str" );
	doMain( fakeArgs );

	// Note: you cannot throw away the parser once it is created
	// because it is a static (global) object that lives until
	// the program ends - don't delete it and recreate it.
	// Can't do this: myParser = null;

	// If you uncommment this part, it will produce a runtime
	// error because you cannot make a second parser because
	// the parser is a static class.
	// ------------------------------------------------------
	// StarParser aSecondParser;
	// aSecondParser = new StarParser( System.in );


    }

    static public void doMain(String args[])
    {
	try
	{
	    StarFileNode             fileroot = null;
	    java.io.FileInputStream  inStream = null;
	    String                   parseFname = args[0];
	    
	    inStream =  new java.io.FileInputStream(parseFname);

	    // This is the important part about how to handle
	    // multiple r-uses of StarParser.
	    if( myParser == null )
		myParser = new StarParser( inStream );
	    else
		myParser.ReInit( inStream );

	    myParser.StarFileNodeParse(myParser);
	    fileroot = (StarFileNode) myParser.popResult();

	    VectorCheckType monomeric_saveFrameHits = null;
	    VectorCheckType moleSys_saveFrameHits = null;
	    VectorCheckType natural_saveFrameHits = null;
	    VectorCheckType entryC_saveFrameHits = null;
	    VectorCheckType entryI_saveFrameHits = null; 
	    VectorCheckType saveFrameHits = null;

	    monomeric_saveFrameHits = fileroot.searchForTypeByTagValue(
			Class.forName(StarValidity.pkgName()+".SaveFrameNode"),
			"_Saveframe_category",
			"monomeric_polymer");

	    moleSys_saveFrameHits = fileroot.searchForTypeByTagValue(
			Class.forName(StarValidity.pkgName()+".SaveFrameNode"),
			"_Saveframe_category",
			"molecular_system");
		    
	    natural_saveFrameHits = fileroot.searchForTypeByTagValue(
			Class.forName(StarValidity.pkgName()+".SaveFrameNode"),
			"_Saveframe_category",
			"natural_source");
	    
	    entryI_saveFrameHits = fileroot.searchForTypeByTagValue(
			Class.forName(StarValidity.pkgName()+".SaveFrameNode"),
			"_Saveframe_category",
			"entry_information");
		    
	    entryC_saveFrameHits = fileroot.searchForTypeByTagValue(
			Class.forName(StarValidity.pkgName()+".SaveFrameNode"),
			"_Saveframe_category",
			"entry_citation");

	    saveFrameHits = fileroot.searchForTypeByTagValue(
			Class.forName(StarValidity.pkgName()+".SaveFrameNode"), 
			"_Saveframe_category",    
			"assigned_chemical_shifts");

	   System.out.println("monomeric_saveFrameHits.size()="+monomeric_saveFrameHits.size());

	   System.out.println("saveFrameHits.size()="+saveFrameHits.size());

	   System.out.println("moleSys_saveFrameHits.size()="+moleSys_saveFrameHits.size());

	   System.out.println("natural_saveFrameHits.size()="+natural_saveFrameHits.size());

	   System.out.println("entryI_saveFrameHits.size()="+entryI_saveFrameHits.size());

	   System.out.println("entryC_saveFrameHits.size()="+entryC_saveFrameHits.size());

	    // Commented out: the writing of the file.
	    // java.io.FileOutputStream passout1 =
	    //	    new java.io.FileOutputStream(parseFname+".1");
	    // myUnparser = new StarUnparser( passout1 );
	    // myUnparser.setFormatting( false );
	    // myUnparser.writeOut( fileroot, 0 );
	    // passout1 = null; // Throw it away. The finalizer closes the file.
	    // myUnparser = null;

	    fileroot = null;
	    inStream = null;  // The finalizer will close the file for us.
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
	catch( java.io.IOException e )
	{
	    System.out.println("IO exception: " + e.getMessage() );
	    e.printStackTrace();
	}
	catch( ClassNotFoundException e )
	{
	    String errMessage = e.getMessage();
	    System.out.println(errMessage);
	    e.printStackTrace();
	}

    }
}

