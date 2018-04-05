package stemcheckerbeans.datastorage;

/**
 * A class that defines an object that assesses and/or gene set data used by the website.
 * This objects have two kinds of data associated: a list of protein sets and a master protein list.
 * 
 * @author José Pedro
 * @version     %I%, %G%
 */
public abstract class AbstractDataContainer {

	/**
	 * Returns the number of sets.
	 * @return the number of sets
	 */
	abstract public int getNumberOfSets();
	
	/**
	 * Returns the name of one set.
	 * @param i Set's number
	 * @return Name of the set
	 */
	abstract public String getSetName(int i);
	
	/**
	 * Returns an array with the names of all sets, names are ordered by the set's number. 
	 * @return Sets' names
	 */
	abstract public String[] getSetNames();
	
	/**
	 * Returns one set as a String matrix.
	 * In the matrix each row corresponds to one set entry and contains all alternative names associated with it.
	 * @param i Set's number
	 * @return Matrix corresponding to set
	 */
	abstract public String[][] getSetData(int i);
	
	/**
	 * Returns the protein master list as a matrix.
	 * In this matrix each row corresponds to a protein whose elements corresponds to an alternative identifier to the same protein, the first entry is always the entrez id.
	 * @return the protein master list.
	 */
	abstract public String[][] getProteinTable();
	
	
	abstract public String getColor(int i);
	
	abstract public String[] getColors();
	
	abstract public void setColors(String[] cols);
	
	abstract public String[][] getDdata();
	
	abstract public String[] getAltnames();

	abstract public void setAltnames(String[] altnames);
	
	abstract public String[][] getDescription();
	
	abstract public boolean isMouse(int setN);
}
