/**
 * 
 */
package org.gamelib;

import java.applet.Applet;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.SwingUtilities;

/**
 * @author pwnedary
 *
 */
public class AppletLoader extends Applet {

	private static final long serialVersionUID = -7902332885993278513L;

	/**
	 * @throws HeadlessException
	 */
	public AppletLoader() throws HeadlessException {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init() {
		final String  inputStr = getParameter("paramStr");        
        final int inputInt = Integer.parseInt(getParameter("paramInt"));
        final String inputOutsideJNLPFile = getParameter("paramOutsideJNLPFile");

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    //createGUI(inputStr, inputInt, inputOutsideJNLPFile);
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't successfully complete");
        }
		/*EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
			}
		});*/
	}

}
