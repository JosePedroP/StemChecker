package stemcheckerbeans;

import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.hibernate.Session;

@ManagedBean
@RequestScoped
public class StemBrowser {
	@ManagedProperty(value="#{sesssionFactoryClass}")
	protected SesssionFactoryClass sesssionFactoryClass;
	
	protected String name;
	protected String title;
	protected String description;
	protected String sample;
	protected String platform;
	protected String numberOfGenes;
	protected String[] pubmedId;
	protected String[] source;
	protected String[][] setD;
	protected String stemCellType;
	protected String publicationAbstract;
	protected String species;
	protected String derivation;
	
	protected String setId = null;
	
	protected String summary;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
	}

	public SesssionFactoryClass getSesssionFactoryClass() {
		return sesssionFactoryClass;
	}

	public void setSesssionFactoryClass(SesssionFactoryClass sesssionFactoryClass) {
		this.sesssionFactoryClass = sesssionFactoryClass;
	}

	public String getSetId() {
		return setId;
	}

	public void setSetId(String setId) {
		this.setId = setId;
	}

//	public SetCenter getGlobalData() {
//		return globalData;
//	}
//
//	public void setGlobalData(SetCenter globalData) {
//		this.globalData = globalData;
//	}
	
	
	public void goSetData() {
		
		if(this.setId!=null)
		{
			String pos = this.setId.substring(1);
			
			boolean stem = this.setId.startsWith("s");
			
			int poss = -1;
			
			try
			{
				poss = new Integer(pos).intValue();
			} catch (Exception e)
			{e.printStackTrace();}
			
			if(poss==-1) return ;
			
			Session s = this.sesssionFactoryClass.getSessionFactory().openSession();
			
			DatabaseExtractor de = new DatabaseExtractor();
			
			Object[] obs = de.getSingleSetData(s, ""+poss, stem);
			
//			Object[]{name, type, description, sample, platform, species, source, pubmedRefs, proteins};
			
			this.name = (String)obs[0];
			this.description = (String)obs[2];
			this.sample = (String)obs[3];
			this.platform = (String)obs[4];
			this.species = (String)obs[5];
			
			String ts = (String)obs[6];
			
			if(ts==null) this.source = null;
			else if(ts.startsWith("<"))
			{
				ts = ts.substring(1);
				
				this.source = ts.split(";");
			}
			else this.source = new String[]{ts,"x"};
			
			this.pubmedId = (String[])obs[7];
			this.setD = (String[][])obs[8];
			
			
			
			this.stemCellType = (String)obs[9];
			this.publicationAbstract = (String)obs[10];
			this.derivation = (String)obs[11];
			this.title = (String)obs[12];
			
			this.summary = (String)obs[13];
			
			this.numberOfGenes = ""+this.setD.length;
			
			
			AuxSorter[] setsinsample = new AuxSorter[this.setD.length];
			
			for(int v=0;v<this.setD.length;v++)
			{
				setsinsample[v] = new AuxSorter(this.setD[v], this.setD[v][1]);
			}
			
			Arrays.sort(setsinsample);
			
			for(int v=0;v<this.setD.length;v++)
			{
				this.setD[v] = (String[])setsinsample[v].getA();
			}
			
//			protected String numberOfGenes;
//			protected String[] pubmedId;
//			protected String[] source;
//			protected String[][] setD;
			
		}
		
	/*	if(this.setId!=null)
		{
			String pos = this.setId.substring(1);
			
			boolean stem = this.setId.startsWith("s");
			
			int poss = -1;
			
			String[] dtemp = new String[0];
			
			try
			{
				poss = new Integer(pos).intValue();
			} catch (Exception e)
			{e.printStackTrace();}
			
			if(poss==-1) return ;
			
			
			
			if(stem)
			{
				name = this.globalData.getAltnames()[poss];
				
				
				String[][] tsetD = this.globalData.getSetData(poss);
				
				
				setD = new String[tsetD.length][2];
				
				
				for(int k=0;k<tsetD.length;k++)
				{
					if(tsetD[k].length==1)
					{
						setD[k][0] = tsetD[k][0];
						setD[k][1] = tsetD[k][0];
					}
					else
					{
						setD[k][0] = tsetD[k][0];
						setD[k][1] = tsetD[k][1];
					}
				}
				
				dtemp = new String[setD.length];
				
				for(int d=0;d<setD.length;d++)
				{
					dtemp[d] = setD[d][0];
				}
				
				this.title = this.globalData.getDescription()[poss][0];
				this.description = this.globalData.getDescription()[poss][1];
				this.sample = this.globalData.getDescription()[poss][2];
				this.platform = this.globalData.getDescription()[poss][3];
				this.numberOfGenes = this.globalData.getDescription()[poss][4];
				this.pubmedId = this.globalData.getDescription()[poss][5].split(",");
				
				
				String ts = this.globalData.getDescription()[poss][6];
				
				if(ts.startsWith("<"))
				{
					ts = ts.substring(1);
					
					this.source = ts.split(";");
				}
				else this.source = new String[]{ts,"x"};
				
				
				
//				this.source = this.globalData.getDescription()[poss][6];
			}
			else
			{
				name = this.globalData.getSecondAltnames()[poss];
				
				
				
				
				String[][] tsetD = this.globalData.getSecondSetData(poss);
				
				
				setD = new String[tsetD.length][2];
				
				
				for(int k=0;k<tsetD.length;k++)
				{
					if(tsetD[k].length==1)
					{
						setD[k][0] = tsetD[k][0];
						setD[k][1] = tsetD[k][0];
					}
					else
					{
						setD[k][0] = tsetD[k][0];
						setD[k][1] = tsetD[k][1];
					}
				}
				
				
				
				
				
				dtemp = new String[setD.length];
				
				for(int d=0;d<setD.length;d++)
				{
					dtemp[d] = setD[d][0];
				}
				
				this.title = this.globalData.getSecondDescription()[poss][0];
				this.description = this.globalData.getSecondDescription()[poss][1];
				this.sample = this.globalData.getSecondDescription()[poss][2];
				this.platform = this.globalData.getSecondDescription()[poss][3];
				this.numberOfGenes = this.globalData.getSecondDescription()[poss][4];
				this.pubmedId = this.globalData.getSecondDescription()[poss][5].split(",");
				
				
				
				String ts = this.globalData.getSecondDescription()[poss][6];
				
				if(ts.startsWith("<"))
				{
					ts = ts.substring(1);
					
					this.source = ts.split(";");
				}
				else this.source = new String[]{ts,"x"};
				
				
				
//				this.source = this.globalData.getSecondDescription()[poss][6];
				
			}
			
			
		}*/
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
	}

	public String getNumberOfGenes() {
		return numberOfGenes;
	}

	public void setNumberOfGenes(String numberOfGenes) {
	}

	public String[] getPubmedId() {
		return pubmedId;
	}

	public void setPubmedId(String[] pubmedId) {
	}

	public String[] getSource() {
		return source;
	}

	public void setSource(String[] source) {
	}

	public String[][] getSetD() {
		return setD;
	}

	public void setSetD(String[][] setD) {
	}

	public String getStemCellType() {
		return stemCellType;
	}

	public void setStemCellType(String stemCellType) {
	}

	public String getPublicationAbstract() {
		return publicationAbstract;
	}

	public void setPublicationAbstract(String publicationAbstract) {
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
	}

	public String getDerivation() {
		return derivation;
	}

	public void setDerivation(String derivation) {
	}
	
}
