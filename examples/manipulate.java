
package EDU.bmrb.starlibj.examples;

import EDU.bmrb.starlibj.*;
import java.io.*;

public class manipulate {

    public manipulate () {

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
	    System.out.println("Trying to read manipulate.str.");
	    
	    myUnparser = new StarUnparser( System.out );
	    
	    myParser = new StarParser( new java.io.FileInputStream("manipulate.str") );
	    // myParser.setVerbose(true);
	    myParser.StarFileNodeParse(myParser);
	    sfn = (StarFileNode) myParser.popResult();
	    System.out.println("=====Parsing Complete.=====");
	    // manipulating:
	    // Inserting a new tag between _t1 and _t2 in the first loop:
	    searchResult = sfn.searchForTypeByName(
		    Class.forName( StarValidity.pkgName()+".DataLoopNode"),
		    "_t1" );

	    // Bad practice - assuming it was found - this is a quick & dirty
	    // check program, not a nice complete program.
	    DataLoopNode loopFound =
		(DataLoopNode)(searchResult.elementAt(0));

	    // Again, this is cheating - I am assuming I know the layout
	    // of the file to keep this test brief.  I assume the loop
	    // has three nesting levels here without checking:
	    LoopNameListNode nameLvlZero =
		(LoopNameListNode)(loopFound.getNames().elementAt(0) );
	    LoopNameListNode nameLvlOne =
		(LoopNameListNode)(loopFound.getNames().elementAt(1) );
	    LoopNameListNode nameLvlTwo =
		(LoopNameListNode)(loopFound.getNames().elementAt(2) );

	    // ------ First some insertions: -----
	    // -----------------------------------

	    // Append a new column at the end of the outer nest level:
	    // Use the default pad data (a dot):
	    nameLvlZero.addElement(
		    new DataNameNode( "_inserted_outer_tag_1" ) );

	    // Insert a new column at the end of the outer nest level:
	    // Use a pad value I pass in ('my pad val').
	    nameLvlZero.insertElementAt(
		    new DataNameNode( "_inserted_outer_tag_2" ),
		    0,
		    new DataValueNode( "my pad val", DataValueNode.SINGLE ) );

	    // Insert a new column at the end of the deep inner level:
	    // Use a pad value I pass in ('pad val deep').
	    nameLvlTwo.addElement(
		    new DataNameNode( "_inserted_deep_tag" ),
		    new DataValueNode( "pad val deep", DataValueNode.SINGLE ) );

	    // Insert a new column in the middle of the middle nest level:
	    // Use a pad value I pass in ('pad val deep').
	    nameLvlOne.insertElementAt(
		    new DataNameNode( "_inserted_middle_tag" ),
		    1,
		    new DataValueNode( "______" ) );

	    // Now output the result to standard output:
	    System.out.println( "########### RESULTS AFTER INSERTIONS ##############" );
	    myUnparser = new StarUnparser( System.out );
	    myUnparser.writeOut( sfn, 0 );

	    // ------ Now some removals: ---------
	    // -----------------------------------

	    // Remove the '_t2' tag and its data:
	    // (right now, position 0 is _inserted_outer_tag_2 and
	    // postion 1 is "_t1" and position 2 is "_t2")
	    nameLvlZero.removeElementAt( 2 );

	    // Remove the zero-th name from the 2nd nest level (that's
	    // the '_t6' tag from the example file), and its associated
	    // values from the loop:
	    nameLvlTwo.removeElementAt( 0 );

	    // Now output the result to standard output:
	    System.out.println( "########### RESULTS AFTER REMOVALS ##############" );
	    myUnparser = new StarUnparser( System.out );
	    myUnparser.writeOut( sfn, 0 );

	}
	catch( ParseException e)
	{
	    System.out.println("Parse Exception: " + e.getMessage() );
	    e.printStackTrace();
	}
	catch( FileNotFoundException e)
	{
	    System.out.println("File Not found exception opening manipulate.str" );
	    e.printStackTrace();
	}
	catch( ClassNotFoundException e )
	{
	    System.out.println("Class not found exception:" + e.getMessage() );
	    e.printStackTrace();
	}
    }

}

