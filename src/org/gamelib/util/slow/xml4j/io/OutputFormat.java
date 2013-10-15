/**
 * 
 */
package org.gamelib.util.slow.xml4j.io;

/**
 * @author pwnedary
 *
 */
public class OutputFormat {
	
	public String indent = "	";
	
	public String lineSeperator = "\n";
	
	// TODO add option for "" or ''

	/**
	 * 
	 */
	public OutputFormat() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public OutputFormat(String indent, String lineSeperator) {
		this.indent = indent;
		this.lineSeperator = lineSeperator;
	}

}
