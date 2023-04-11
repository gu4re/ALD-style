package es.codeurjc.exceptions;

/**
 * Exception thrown when it is impossible to export the
 * Shoes database to a JSON Array
 */
public class UnsupportedExportException extends Exception{
	/**
	 * Basic public constructor to the UnsupportedExportException class
	 */
	public UnsupportedExportException(){
		super();
	}
}
