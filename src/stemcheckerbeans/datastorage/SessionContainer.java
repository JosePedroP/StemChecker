package stemcheckerbeans.datastorage;

import org.hibernate.Session;

public class SessionContainer {

	
	protected Session s;

	
	public void close() {
		this.s.close();
	}
	
	public int getNumberOfSets() {
		int res = 0;
		
		try{
			res = ((Long)this.s.createQuery("select count(*) from Book").uniqueResult()).intValue();
		} catch(Exception e)
		{e.printStackTrace();}
		
		return res;
	}

	public String getSetName(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getSetNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[][] getSetData(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[][] getProteinTable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColor(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getColors() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setColors(String[] cols) {
		// TODO Auto-generated method stub
		
	}

	public String[][] getDdata() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getAltnames() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAltnames(String[] altnames) {
		// TODO Auto-generated method stub
		
	}

	public String[][] getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isMouse(int setN) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
}
