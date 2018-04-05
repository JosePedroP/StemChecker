package stemcheckerbeans;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.xhtmlrenderer.pdf.ITextRenderer;

@ManagedBean
@RequestScoped
public class StemChecker {
	@ManagedProperty(value="#{sesssionFactoryClass}")
	protected SesssionFactoryClass sesssionFactoryClass;
	
	protected String searchData = "";
	protected boolean input = true;
	protected boolean output = false; //false later
	protected boolean nomatch = false;
	protected boolean badfile = false;
	protected double[] pvalues = new double[]{};
	protected int[] foundLines = new int[]{}; //used to speed process
	protected String[] notFound = new String[]{}; //create in determineAssociations to speed process
	protected CheckMatrix[] checkMatrix = new CheckMatrix[]{};
	protected double[] colorsCount = new double[]{};
	protected int[] colorsCountPerc = new int[]{};
	protected String[] nameOfSetsToUse = new String[]{};
	protected String[] descriptionOfSetsToUse = new String[]{};
	protected String[] idOfSetsToUse = new String[]{};
	protected String[] setSize = new String[]{};
	protected String[] overlaps = new String[]{};
	protected int[] setsToUse = new int[]{}; //create in determineAssociations to speed process
	protected boolean order1 = true;
	protected boolean order2 = true;
	protected boolean order3 = false;
	
	protected boolean cellProliferation = false;
	protected boolean cellCycle = false;

	protected CheckMatrix[] secondCheckMatrix = new CheckMatrix[]{};
	protected double[] secondColorsCount = new double[]{};
	protected int[] secondColorsCountPerc = new int[]{};
	
	protected String[] firstline = new String[]{};
	

	protected double[] secondPvalues = new double[]{};
	protected String[] secondNameOfSetsToUse = new String[]{};
	protected String[] secondDescriptionNameOfSetsToUse = new String[]{};
	protected String[] secondIdOfSetsToUse = new String[]{};
	protected String[] secondSetSize = new String[]{}; 
	protected String[] secondOverlaps = new String[]{};
	protected String[] secondOverlapsGeneSymbol = new String[]{}; 
	
	protected String[][][] sets = null;
	
	protected String organism = "HS";
	
	protected String[] overlapsids = new String[]{};
	protected String[] overlapsGeneSymbol = new String[]{};
	
	protected String[] secondoverlapsids = new String[]{};
	
	protected double[] countCellTypes = new double[]{};
	protected int[] countCellTypesPerc = new int[]{};
	protected int[] trueCountCellTypes = new int[]{};
	protected int[] totalCountCellTypes = new int[]{};
	protected double[] cellTypesPvalues = new double[]{};
	protected String[] cellTypesOverlapsids = new String[]{};
	protected String[] cellTypesOverlapsGeneSymbol = new String[]{};
	
	protected String agreSignatureTypes = "";
	protected String agreTFData = "";
	protected String agreCellTypes = "";

	protected int setNum = 0;
	protected int tfNum = 0;
	
	protected String[] matches = new String[]{};
	protected String[] noMatches = new String[]{};
	protected String[] noSetMatches = new String[]{};
	protected String[] masked = new String[]{};
	
	protected int numallmatches = 0;
	
	public boolean isOrder3() {
		return order3;
	}

	public void setOrder3(boolean order3) {
		this.order3 = order3;
	}
	
	public String getCountCellTypes() {
		
		String res = "";
		
		for(int i=0;i<countCellTypes.length;i++)
		{
			DecimalFormat numberFormat;
			
			if(this.countCellTypes[i]>=0.001) numberFormat = new DecimalFormat("#.###");
			else numberFormat = new DecimalFormat("#.###E0");
			
			String cc = numberFormat.format(this.countCellTypes[i]).replace(',', '.');

			if(this.countCellTypes[i]==0) cc = "0";
			
			
			if(i==0) res += cc;
			else res += "!"+cc;
			
			
//			if(i!=0) res +="!";
//			res += countCellTypes[i];
		}
		
//		System.out.println("PRICE "+res);
		
		return res;
	}

	public void setCountCellTypes(String countCellTypes) {
	}

	public String[] getOverlapsids() {
		return overlapsids;
	}

	public String getAgreCellTypes() {
		return agreCellTypes;
	}

	public void setAgreCellTypes(String agreCellTypes) {
	}

	public void setOverlapsids(String[] overlapsids) {
	}

	public String getOrganism() {
		return organism;
	}
	
	public void setOrganism(String organism) {
		this.organism = organism;
	}
	
	public String[][][] getSetData() { //Must change to a load on demand mode latter
		
		if(this.sets==null)
		{
			Session s = this.sesssionFactoryClass.getSessionFactory().openSession();
		
			DatabaseExtractor de = new DatabaseExtractor();
		
			this.sets = de.getSetData(s);
		}
//		else System.out.println("Already done");
		return sets;
		
//		String[] names = this.globalData.getAltnames();
//		
//		String[][] res = new String[names.length][2];
//		
//		for(int i=0;i<names.length;i++)
//		{
//			res[i][0] = names[i];
//			res[i][1] = "s"+i;
//		}
//		
//		return res;
		
//		return new String[][]{};
	}

	
	public String[][] getSetData2() { //Must change to a load on demand mode latter
		
//		String[] names = this.globalData.getSecondAltnames();
//		
//		String[][] res = new String[names.length][2];
//		
//		for(int i=0;i<names.length;i++)
//		{
//			res[i][0] = names[i];
//			res[i][1] = "t"+i;
//		}
//		
//		return res;

		return new String[][]{};
	}
	
	
	public void setSetData(String[][][] setData) {
	}
	
	
	public void setSetData2(String[][] setData) {
	}

	public String[] getFirstline() {
		return firstline;
	}

	public void setFirstline(String[] firstline) {
		this.firstline = firstline;
	}

	public boolean isOrder1() {
		return order1;
	}

	public void setOrder1(boolean order1) {
		this.order1 = order1;
	}

	public boolean isOrder2() {
		return order2;
	}

	public void setOrder2(boolean order2) {
		this.order2 = order2;
	}

	public String getSetsToUse() {
		
		String res = "";
		
		for(int o=0;o<this.setsToUse.length;o++)
		{
			if(o==0) res += this.setsToUse[o];
			else res += "!"+this.setsToUse[o];
		}
		
		return res;
	}

	public void setSetsToUse(String setsToUse) { //TODO:
		
//		System.out.println("setsToUse "+setsToUse);
		
		if(setsToUse.equals("") || setsToUse==null)
		{
			this.setsToUse = new int[]{};
		}
		else
		{
			String[] mat = setsToUse.split("!");
		
			ArrayList<Integer> temp = new ArrayList<Integer>();
		
			for(int o=0;o<mat.length;o++)
			{
				try{
					int z = new Integer(mat[o]);
					temp.add(z);
				} catch(Exception e)
				{e.printStackTrace();}
			}
		
			this.setsToUse = new int[temp.size()];
		
			for(int o=0;o<temp.size();o++)
			{
				this.setsToUse[o] = temp.get(o).intValue();
			}
		}
		
	}

	public String[] getNotFound() {
		return notFound;
	}

	public String getColorsCount() {
		
		String res = "";
		
		for(int o=0;o<this.colorsCount.length;o++)
		{
			DecimalFormat numberFormat;
			
			if(this.colorsCount[o]>=0.001) numberFormat = new DecimalFormat("#.###");
			else numberFormat = new DecimalFormat("#.###E0");
			
			String cc = numberFormat.format(this.colorsCount[o]).replace(',', '.');

			if(this.colorsCount[o]==0) cc = "0";
			
			if(o==0) res += cc;
			else res += "!"+cc;
			

//			if(o==0) res += this.colorsCount[o];
//			else res += "!"+this.colorsCount[o];
		}
		
		return res;
	}

	public void setColorsCount(String colorsCount) {
	}

	public String getColorsCountPerc() {
		
		String res = "";
		
		for(int o=0;o<this.colorsCountPerc.length;o++)
		{
			if(o==0) res += this.colorsCountPerc[o];
			else res += "!"+this.colorsCountPerc[o];
		}
		
		return res;
	}

	public void setColorsCountPerc(String colorsCount) {
	}

	
	
	
	public String getSecondColorsCountPerc() {
		
		String res = "";
		
		for(int o=0;o<this.secondColorsCountPerc.length;o++)
		{
			if(o==0) res += this.secondColorsCountPerc[o];
			else res += "!"+this.secondColorsCountPerc[o];
		}
		
		return res;
//		return secondColorsCountPerc;
	}

	public void setSecondColorsCountPerc(String secondColorsCountPerc) {
	}
	
	public String getCountCellTypesPerc() {
		
		String res = "";
		
		for(int o=0;o<this.countCellTypesPerc.length;o++)
		{
			
			if(o==0) res += this.countCellTypesPerc[o];
			else res += "!"+this.countCellTypesPerc[o];
		}
		
		return res;
//		return countCellTypesPerc;
	}

	public void setCountCellTypesPerc(String countCellTypesPerc) {
	}

	public void setNotFound(String[] notFound) {
		this.notFound = notFound;
	}

	public CheckMatrix[] getCheckMatrix() {
		return checkMatrix;
	}

	public void setCheckMatrix(CheckMatrix[] checkMatrix) {
		this.checkMatrix = checkMatrix;
	}

	public double[] getPvalues() {
		return pvalues;
	}

	public void setPvalues(double[] pvalues) {
		this.pvalues = pvalues;
	}

	public boolean isInput() {
		return input;
	}

	public void setInput(boolean input) {
		this.input = input;
	}

	public boolean isOutput() {
		return output;
	}

	public void setOutput(boolean output) {
		this.output = output;
	}
	
	public SesssionFactoryClass getSesssionFactoryClass() {
		return sesssionFactoryClass;
	}


	public void setSesssionFactoryClass(SesssionFactoryClass sesssionFactoryClass) {
		this.sesssionFactoryClass = sesssionFactoryClass;
	}


	public String getSearchData() {
		return searchData;
	}
	
	public void setSearchData(String searchData) {
		this.searchData = searchData;
	}
	
	protected String[] breakQuerry(String querry)
	{
		if(querry==null || querry.length()==0)
		{
			return new String[]{};
		}
		
		StringTokenizer st = new StringTokenizer(querry, " \t\n\r\f,");
		String[] res = new String[st.countTokens()];
		
		int u = 0;
		while(st.hasMoreTokens())
		{
			res[u] = st.nextToken();
			u++;
		}
		
		return res;
	}
	
	public String search() {
		
		this.input = false;
		this.output = true;
		
		String[] genes = this.breakQuerry(this.searchData);
	
		return searchPart2(genes);
	}
	
	public String searchPart2(String[] genes) {
		
//		this.input = false;
//		this.output = true;
//		
//		String[] genes = this.breakQuerry(this.searchData);
		
		
		Session s = this.sesssionFactoryClass.getSessionFactory().openSession();
		
		
		DatabaseExtractor de = new DatabaseExtractor();
		
		
//		for(int u =0;u<this.setsToUse.length;u++)
//			System.out.println(this.setsToUse[u]);
		
		String org = null;
		
		if(this.organism.equals("HS")) org = "Homo sapiens";
		else if(this.organism.equals("MM")) org = "Mus musculus";
		
		ArrayList<String> temp = new ArrayList<String>();
		
		if(this.cellCycle) temp.add("Cell Cycle");
		if(this.cellProliferation) temp.add("Cell Proliferation");
		
		
		
		CheckMatrix[][] cmx = de.getSetResultsData(genes, this.setsToUse, s, this.sesssionFactoryClass.getNamesToImages(), org, temp.toArray(new String[]{}));
		
		this.checkMatrix = cmx[0];
		
		this.agreSignatureTypes = de.getAgreSignatureTypes();
		
		this.agreTFData = de.getAgreTFData();
		
		this.agreCellTypes = de.getAgreCellTypes();
		
		this.countCellTypes = de.getCountCellTypes();
		this.countCellTypesPerc = de.getCountCellTypesPerc();
		

		this.trueCountCellTypes = de.getTrueCountCellTypes();
		this.cellTypesPvalues = de.getCellTypesPvalues();
		this.cellTypesOverlapsids = de.getCellTypesOverlapsids();
		this.cellTypesOverlapsGeneSymbol = de.getCellTypesOverlapsGeneSymbol();
		
		this.totalCountCellTypes = de.getTotalCountCellTypes();
		
		this.colorsCount = de.getColorsCount();
		this.colorsCountPerc = de.getColorsCountPerc();
		
		this.pvalues = de.getPvalues();
		this.nameOfSetsToUse = de.getNameOfSetsToUse();
		this.descriptionOfSetsToUse = de.getDescriptionOfSetsToUse();
		this.idOfSetsToUse = de.getIdOfSetsToUse();
		this.setSize = de.getSetSize();
		this.overlaps = de.getOverlaps();
		this.overlapsids = de.getOverlapsids();
		this.overlapsGeneSymbol = de.getOverlapsGeneSymbol();
		
		this.secondCheckMatrix = cmx[1];
		
		this.secondColorsCount = de.getSecondColorsCount();
		this.secondColorsCountPerc = de.getSecondColorsCountPerc();
		
		this.secondPvalues = de.getSecondPvalues();
		this.secondNameOfSetsToUse = de.getSecondNameOfSetsToUse();
		this.secondDescriptionNameOfSetsToUse = de.getSecondDescriptionNameOfSetsToUse();
		this.secondIdOfSetsToUse = de.getSecondIdOfSetsToUse();
		this.secondSetSize = de.getSecondSetSize();
		this.secondOverlaps = de.getSecondOverlaps();
		this.secondoverlapsids = de.getSecondOverlapsids();
		this.secondOverlapsGeneSymbol = de.getSecondOverlapsGeneSymbol();
		
		this.setNum = de.getSetNum();
		this.tfNum = de.getTfNum();
		
		this.checkMatrix = orderAux(this.checkMatrix);
		
		this.secondCheckMatrix = orderAux(this.secondCheckMatrix);
		

		this.matches = de.getMatches();
		this.noMatches = de.getNoMatches();
		this.noSetMatches = de.getNoSetMatches();
		this.masked = de.getMasked();
		
		this.numallmatches = de.getNumallmatches();
		
		if(this.checkMatrix.length==1 && this.secondCheckMatrix.length==1)
		{
			this.input = false;
			this.output = false;
			this.nomatch = true;
		}
		else
		{
			this.input = false;
			this.output = true;
		}
		
//		setResults(genes); //This function doesl the actula search//TODO: ADD HERE
		
		
		
		return null;
	}
	
	
	public String getAgreTFData() {
		return agreTFData;
	}

	public void setAgreTFData(String agreTFData) {
	}

	public String getAgreSignatureTypes() {
		return agreSignatureTypes;
	}

	public void setAgreSignatureTypes(String agreSignatureTypes) {
	}

	protected CheckMatrix[] orderAux(CheckMatrix[] incheckMatrix)
	{
		CheckMatrix[] checkMatrix = incheckMatrix;
		
		if(this.order1)
		{
			AuxSorter[] aux = new AuxSorter[checkMatrix.length-1];
			
			for (int a=1;a<checkMatrix.length;a++)
			{
				aux[a-1] = new AuxSorter(new Integer(a), new Integer(0));
						
				for (int b=1;b<checkMatrix[a].getData().length;b++)
				{
					if(checkMatrix[a].getData()[b][0].equals("1"))
					{
//						//System.out.printlnln(aux[a-1]==null);
						int z = ((Integer)aux[a-1].getC()).intValue()+1;
						aux[a-1].setC(new Integer(z));
					}
					
				}
				
			}
			
			Arrays.sort(aux);
			
			
			CheckMatrix[] tcheckMatrix = new CheckMatrix[checkMatrix.length];
			
			tcheckMatrix[0] = checkMatrix[0];
			
			for (int a=0;a<aux.length;a++)
			{
//				//System.out.printlnln(aux[aux.length-a-1].getA()+" "+aux[aux.length-a-1].getC());
				tcheckMatrix[a+1] = checkMatrix[(Integer)aux[aux.length-a-1].getA()];
			}
			
			checkMatrix = tcheckMatrix;
		}
		else if(this.order3)
		{
			AuxSorter[] aux = new AuxSorter[checkMatrix.length-1];
			
			for (int a=1;a<checkMatrix.length;a++)
			{
				aux[a-1] = new AuxSorter(new Integer(a), checkMatrix[a].getData()[0][0]);
//				System.out.println("ga: "+checkMatrix[a].getData()[0][0]);
			}
			
			Arrays.sort(aux);

			CheckMatrix[] tcheckMatrix = new CheckMatrix[checkMatrix.length];
		
			tcheckMatrix[0] = checkMatrix[0];
			
			for (int a=0;a<aux.length;a++)
			{
//				//System.out.printlnln(aux[aux.length-a-1].getA()+" "+aux[aux.length-a-1].getC());
				tcheckMatrix[a+1] = checkMatrix[(Integer)aux[a].getA()];
			}
			
			checkMatrix = tcheckMatrix;
		}
		
		if(this.order2)
		{
			AuxSorter[] aux = new AuxSorter[checkMatrix[0].getData().length-1];
			
			for(int b=1;b<checkMatrix[0].getData().length;b++) //b data position
			{
				aux[b-1] = new AuxSorter(new Integer(b), new Integer(0));
				
				for(int a=1;a<checkMatrix.length;a++) //a edividual columns
				{
					if(checkMatrix[a].getData()[b][0].equals("1"))
					{
						int z = ((Integer)aux[b-1].getC()).intValue()+1;
						aux[b-1].setC(new Integer(z));
					}
					
				}
			}
			
			Arrays.sort(aux);
			
			
			String[][][] tempc = new String[checkMatrix.length][aux.length][];
			
			for(int b=0;b<aux.length;b++)
			{
				AuxSorter ax = aux[aux.length-b-1];
				
				
				int pos = ((Integer)ax.getA()).intValue();
				
				for(int a=0;a<checkMatrix.length;a++)
				{
					tempc[a][b] = checkMatrix[a].getData()[pos];
					
				}
			}
			
			for(int a=0;a<checkMatrix.length;a++)
			{
				for(int b=1;b<checkMatrix[0].getData().length;b++)
				{
					checkMatrix[a].setData(b, tempc[a][b-1]);
				}
			}
		}
		
		return checkMatrix;
	}
	
	protected int[][] determineAssociations(String[] proteins, String[][] proteinTable)
	{
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] temp = new ArrayList[proteins.length];
		
		ArrayList<Integer> lfound = new ArrayList<Integer>();
		
		int nFoundSize = 0;
		
		for(int i=0;i<proteins.length;i++)
		{
			temp[i] = new ArrayList<Integer>();
			
			boolean uniqueFound = false;
			
			for(int a=0;!uniqueFound && a<proteinTable.length;a++)
			{
				boolean found = false;
				for(int b=0;!found && b<proteinTable[a].length;b++)
				{
					if(proteinTable[a][b].equalsIgnoreCase(proteins[i]))
					{
//						//System.out.println(a+":: "+i+": ");
						
//						for(int m=0;m<proteinTable[a].length;m++)
//							//System.out.println(proteinTable[a][m]+" ");
						
//						//System.out.println("== "+proteins[i]);
//						//System.out.printlnln();
						
						Integer d = new Integer(a);
						temp[i].add(d);
						if(!lfound.contains(d))
						{
							lfound.add(d);
						}
						found = true;
//						if(b==0) uniqueFound = true;
					}
				}
			}
			
			if(temp[i].size()==0) nFoundSize++;
		}
		
		this.notFound = new String[nFoundSize];
		
		nFoundSize = 0;
		
		this.foundLines = new int[lfound.size()];
		
		for(int i=0;i<lfound.size();i++)
			this.foundLines[i] = lfound.get(i).intValue();
		
		int[][] res = new int[proteins.length][];
		
		for(int i=0;i<proteins.length;i++)
		{
			int[] nr = new int[temp[i].size()];
			
			for(int x=0;x<temp[i].size();x++)
			{
				nr[x] = temp[i].get(x).intValue();
			}
			
			res[i] = nr;
			
			if(res[i].length==0)
			{
				this.notFound[nFoundSize] = proteins[i];
				nFoundSize++;
			}
		}
		
		return res;
	}
	
	protected int[][] invertAssociations(int[][] associations)
	{
		int[][] res = new int[this.foundLines.length][];
		
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] temp = new ArrayList[this.foundLines.length];
		
		for(int a=0;a<associations.length;a++)
		{
			for(int b=0;b<associations[a].length;b++)
			{
				boolean stop = false;
				for(int c=0;!stop && c<this.foundLines.length;c++)
				{
					if(this.foundLines[c]==associations[a][b])
					{
						if(temp[c]==null)
						{
							temp[c] = new ArrayList<Integer>();
						}
						
						temp[c].add(new Integer(a));
					}
				}
			}
		}
		
		for(int i=0;i<res.length;i++)
		{
			res[i] = new int[temp[i].size()];
			
			for(int x=0;x<temp[i].size();x++)
			{
				res[i][x] = temp[i].get(x);
			}
		}
		
		return res;
	}
	
	public String[][] getPvalue()
	{
		ArrayList<String[]> temp = new ArrayList<String[]>();
		
		if(this.pvalues==null || this.pvalues.length==0) return new String[][]{};
		
		
//		System.out.println("this.pvalues "+this.pvalues.length);
		
		
		for(int i=0;i<this.nameOfSetsToUse.length;i++)
		{
			if(this.pvalues[i]>-1)
			{
				String[] tres = new String[9];
				
				tres[0] = this.nameOfSetsToUse[i];
				tres[1] = this.setSize[i];
				tres[2] = this.overlaps[i];
				
				double corr = 0;
				
				if(this.pvalues[i]>-1)
				{
					DecimalFormat numberFormat;
					
					if(this.pvalues[i]>=0.001) numberFormat = new DecimalFormat("#.###");
					else numberFormat = new DecimalFormat("#.###E0");
					
					
					if(this.pvalues[i]<1E-16) tres[3] = "<1E-16";
					else tres[3] = numberFormat.format(this.pvalues[i]).replace(',', '.');
					
					
					
					corr = this.pvalues[i]*this.setNum;

					if(corr>1) corr = 1;
					
					if(corr>=0.001) numberFormat = new DecimalFormat("#.###");
					else numberFormat = new DecimalFormat("#.###E0");
					
					if(corr!=0) tres[8] = numberFormat.format(corr).replace(',', '.');
					else tres[8] = "0";
					
					double lime2 = 1E-16*this.setNum;
					
					if(corr<lime2) tres[8] = "<"+lime2;
					
//					if(tres[3].equals("0")) tres[3] = ">"+Double.MIN_VALUE;
//					if(tres[8].equals("0")) tres[8] = ">"+Double.MIN_VALUE;
					
				}
				else tres[3] = "NaN";
				tres[4] = ""+this.pvalues[i];
				tres[5] = this.overlapsids[i];
				tres[6] = this.idOfSetsToUse[i];
				tres[7] = this.descriptionOfSetsToUse[i];
				
//				System.out.println("z "+i+" "+this.pvalues.length+" : "+this.descriptionOfSetsToUse.length);
				
				temp.add(tres);
			}
		}
		

		
		AuxSorter[] aux = new AuxSorter[temp.size()];
		
		for(int i=0;i<temp.size();i++)
		{
			AuxSorter as = new AuxSorter(temp.get(i), new Double(temp.get(i)[4]));
			aux[i] = as;
		}
		
		Arrays.sort(aux);
		
		
		String[][] res = new String[temp.size()][];
		
		for(int i=0;i<temp.size();i++)
		{
//			res[i] = temp.get(i);
			res[i] = (String[])aux[i].getA();
			int z = i+1;
			res[i][4] = ""+z;
		}
		
		return res;
	}
	
	public String getPvalueText()
	{
		String res = "Set Name!Set Size!Overlapping Genes!Pvalue!Adjusted Pvalue!Gene Symbols";
		
		for(int i=0;i<this.nameOfSetsToUse.length;i++)
		{
			if(this.pvalues[i]>-1)
			{
				res += "+"+this.nameOfSetsToUse[i]+"!"+this.setSize[i]+"!"+this.overlaps[i]+"!";
				
				double corr = 0;
				
				DecimalFormat numberFormat;
				
				if(this.pvalues[i]>=0.001) numberFormat = new DecimalFormat("#.###");
				else numberFormat = new DecimalFormat("#.###E0");
					
				String sta;
				
				if(this.pvalues[i]<1E-16) sta = "<1E-16";
				sta = numberFormat.format(this.pvalues[i]).replace(',', '.');

//				if(sta.equals("0")) sta = ">"+Double.MIN_VALUE;;
				
				res += sta+"!";
					
				String sta2 = "0";
					
				corr = this.pvalues[i]*this.setNum;

				if(corr>1) corr = 1;

				if(corr>=0.001) numberFormat = new DecimalFormat("#.###");
				else numberFormat = new DecimalFormat("#.###E0");
					
				if(corr!=0) sta2 = numberFormat.format(corr).replace(',', '.');
				
				double lime2 = 1E-16*this.setNum;
				
				if(corr<lime2) sta2 = "<"+lime2;
				
//				if(sta2.equals("0")) sta2 = ">"+Double.MIN_VALUE;;
					
				res += sta2;
				res += "!"+overlapsGeneSymbol[i];
				
			}
		}
		
//		System.out.println("*** "+res);
		
		return res;
	}
	
	public String[][] getPvalue2()
	{
		ArrayList<String[]> temp = new ArrayList<String[]>();
		
		if(this.secondPvalues==null || this.secondPvalues.length==0) return new String[][]{};
		
		
		for(int i=0;i<this.secondNameOfSetsToUse.length;i++)
		{
			if(this.secondPvalues[i]>-1)
			{
				String[] tres = new String[9];
				
				tres[0] = this.secondNameOfSetsToUse[i];
				tres[1] = this.secondSetSize[i];
				tres[2] = this.secondOverlaps[i];
//				tres[3] = ""+this.secondPvalues[i];
				
				if(this.secondPvalues[i]>-1)
				{
					DecimalFormat numberFormat;
					
					
					if(this.secondPvalues[i]>=0.001) numberFormat = new DecimalFormat("#.###");
					else numberFormat = new DecimalFormat("#.###E0");
					
					if(this.secondPvalues[i]!=0)
						tres[3] = numberFormat.format(this.secondPvalues[i]).replace(',', '.');
					else tres[3] = "0";

					
					double corr = this.secondPvalues[i]*this.tfNum;

					if(corr>1) corr = 1;

					if(corr>=0.001) numberFormat = new DecimalFormat("#.###");
					else numberFormat = new DecimalFormat("#.###E0");
					
					if(corr!=0) tres[8] = numberFormat.format(corr).replace(',', '.');
					else tres[8] = "0";
					

//					if(this.pvalues[i]<1E-16) sta = "<1E-16";
					
					if(this.secondPvalues[i]<1E-16) tres[3] = "<1E-16";
					

					double lime2 = 1E-16*this.tfNum;
					
					
					if(corr<lime2) tres[8] = "<"+lime2;
				}


//				System.out.println(i+" "+this.secondPvalues.length+" : "+this.secondDescriptionNameOfSetsToUse.length);
				
				tres[4] = ""+this.secondPvalues[i];
				
				tres[5] = this.secondoverlapsids[i];
				
				tres[6] = this.secondIdOfSetsToUse[i];
				
				tres[7] = this.secondDescriptionNameOfSetsToUse[i];
				
				
				temp.add(tres);
			}
		}
		
		AuxSorter[] aux = new AuxSorter[temp.size()];
		
		for(int i=0;i<temp.size();i++)
		{
			AuxSorter as = new AuxSorter(temp.get(i), new Double(temp.get(i)[4]));
			aux[i] = as;
		}
		
		Arrays.sort(aux);
		
		String[][] res = new String[temp.size()][];
		
		
		
		for(int i=0;i<temp.size();i++)
		{
			res[i] = (String[])aux[i].getA();
			int z = i+1;
			res[i][4] = ""+z;
		}
		
		
//		for(int i=0;i<temp.size();i++)
//		{
//			res[i] = temp.get(i);
//		}
		
		return res;
	}
	

	public String getPvalueText2()
	{

		String res = "Set Name!Set Size!Overlapping Genes!Pvalue!Adjusted Pvalue!Gene Symbols";
		
		for(int i=0;i<this.secondNameOfSetsToUse.length;i++)
		{
			res += "+"+this.secondNameOfSetsToUse[i]+"!"+this.secondSetSize[i]+"!"+this.secondOverlaps[i];
			
			if(this.secondPvalues[i]>-1)
			{
				DecimalFormat numberFormat;
					
				String sta = "0";
									
				if(this.secondPvalues[i]>=0.001) numberFormat = new DecimalFormat("#.###");
				else numberFormat = new DecimalFormat("#.###E0");
					
				if(this.secondPvalues[i]!=0) sta = numberFormat.format(this.secondPvalues[i]).replace(',', '.');

//				if(sta.equals("0")) sta = ">"+Double.MIN_VALUE;;
				
				if(this.secondPvalues[i]<1E-16) sta = "<1E-16";
				
				res += "!"+sta;
					
				String sta2 = "0";
				
				double corr = this.secondPvalues[i]*this.tfNum;

				if(corr>1) corr = 1;

				if(corr>=0.001) numberFormat = new DecimalFormat("#.###");
				else numberFormat = new DecimalFormat("#.###E0");
				
				if(corr!=0) sta2 = numberFormat.format(corr).replace(',', '.');


				double lime2 = 1E-16*this.tfNum;
				
				
				if(corr<lime2) sta2 = "<"+lime2;
				
//				if(sta2.equals("0")) sta2 = ">"+Double.MIN_VALUE;
				
				res += "!"+sta2;
				
				res += "!"+this.secondOverlapsGeneSymbol[i];
					
			}
		}
		
		
		return res;
	}
	
	public String[][] getPvalue3()
	{
		if(this.cellTypesPvalues==null || this.cellTypesPvalues.length==0) return new String[][]{};
		
		String[] names = new String[]{
			"Embryonic Stem cells",
        	"Neural Stem cells",
        	"Hematopoietic Stem cells",
        	"Mammary Stem cells",
        	"Spermatogonial Stem cells",
        	"Intestinal stem cells",
        	"Induced pluripotent Stem cells",
        	"Mesenchymal Stem cells",
        	"Embryonal carcinoma"
		};
		
		String[][] res = new String[names.length][];
		
		int z = 0;
		for(int i=0;i<names.length;i++)
		{
			res[i] = new String[6];
			
			res[i][0] = names[i];
			res[i][1] = ""+this.totalCountCellTypes[z];
			res[i][2] = ""+this.trueCountCellTypes[z];
			
			
			DecimalFormat numberFormat;
			
			if(this.cellTypesPvalues[z]>=0.001) numberFormat = new DecimalFormat("#.###");
			else numberFormat = new DecimalFormat("#.###E0");
			
			if(this.cellTypesPvalues[z]!=0)
				res[i][3] = numberFormat.format(this.cellTypesPvalues[z]).replace(',', '.');
			else res[i][3] = "0";
			
			
			
			
//			res[i][3] = ""+this.cellTypesPvalues[z];
//			
//			System.out.print(this.cellTypesPvalues[z] + "*"+ names.length+ " = ");
			
			
			double corr = this.cellTypesPvalues[z]*names.length;
			

//			System.out.println(corr);
			
			if(corr>1) corr = 1;
			
			
			if(corr>=0.001) numberFormat = new DecimalFormat("#.###");
			else numberFormat = new DecimalFormat("#.###E0");
			
			if(corr!=0)
				res[i][4] = numberFormat.format(corr).replace(',', '.');
			else res[i][4] = "0";
			

//			if(this.pvalues[i]<1E-16) sta = "<1E-16";
//			if(res[i][3].equals("0")) res[i][3] = ">"+Double.MIN_VALUE;
//			if(res[i][4].equals("0")) res[i][4] = ">"+Double.MIN_VALUE;
			
			if(this.cellTypesPvalues[z]<1E-16) res[i][3] = "<1E-16";
			
			double lime2 = 1E-16*names.length;
			
			if(corr<lime2) res[i][4] =  "<"+lime2;
			
//			res[i][4] = ""+corr;
			
			res[i][5] = this.cellTypesOverlapsids[z];
			
			if(i==0) z++;
			z++;
		}
		
		return res;
	}
	

	
	public String getPvalueText3()
	{

		String res = "Cell Type!Set Size!Overlapping Genes!Pvalue!Adjusted Pvalue!Gene Symbols";
		
		String[] names = new String[]{
			"Embryonic Stem cells",
        	"Neural Stem cells",
        	"Hematopoietic Stem cells",
        	"Mammary Stem cells",
        	"Spermatogonial Stem cells",
        	"Intestinal stem cells",
        	"Induced pluripotent Stem cells",
        	"Mesenchymal Stem cells",
        	"Embryonal carcinoma"
		};
		
		
		int z = 0;
		for(int i=0;i<names.length;i++)
		{
			if(this.trueCountCellTypes[z]>0)
			{
				res += "+"+names[i]+"!"+this.totalCountCellTypes[z]+"!"+this.trueCountCellTypes[z]+"!";
				
				String gaaa = ""+this.cellTypesPvalues[z];
				
				if(this.cellTypesPvalues[z]<1E-16) gaaa = "<1E-16";
				
				res += gaaa;
				
				
				double corr = this.cellTypesPvalues[z]*names.length;
				
				if(corr>1) corr = 1;
				
				double lime2 = 1E-16*names.length;
				
				if(corr<lime2) res += "!<"+lime2;
				else res += "!"+corr;
				
				res += "!"+cellTypesOverlapsGeneSymbol[z];
				
			}
			
			//skips the second (z=1) because it curreponds to a type that was removed
			
			if(i==0) z++;
			z++;
			
		}
		
		return res;
	}
	
	
	
	
	
	public void handleFileUpload(FileUploadEvent event) {
		
		String[] genes = null;
		
		try {
			UploadedFile file = event.getFile();
			
			if(file.getContentType().equals("text/plain"))
			{
				FacesContext context = FacesContext.getCurrentInstance();
				/*String setsToUse = context.getExternalContext().getRequestParameterMap().get("sc:settouse");
				
				System.out.println("-->"+setsToUse+"<--");
				
				String[] mat = setsToUse.split("!");
				
				ArrayList<Integer> tempz = new ArrayList<Integer>();
				
				for(int o=0;o<mat.length;o++)
				{
					try{
						int z = new Integer(mat[o]);
						tempz.add(z);
					} catch(Exception e)
					{e.printStackTrace();}
				}
				
				this.setsToUse = new int[tempz.size()];
				
				for(int o=0;o<tempz.size();o++)
				{
					this.setsToUse[o] = tempz.get(o).intValue();
				}
				
				
				String od1 = context.getExternalContext().getRequestParameterMap().get("sc:ord1");
				
//				//System.out.printlnln("od2 "+od1);
				
				
				String od2 = context.getExternalContext().getRequestParameterMap().get("sc:ord2");
				
//				//System.out.printlnln("od2 "+od2);
				
				this.order1 = od1!=null;
				this.order2 = od2!=null;*/
				
				String decoded = new String(file.getContents(), "UTF-8");
			
				System.out.println("decoded "+decoded);
		
				genes = this.breakQuerry(decoded);
				
				
				this.searchData = "";
				
				for(int i=0;i<genes.length;i++)
				{
					System.out.println(genes[i]);
					if(i>0) this.searchData += " ";
					this.searchData += genes[i];
					
				}
				
//				this.searchPart2(genes);
				

			}
			else
			{
				this.input = false;
				this.badfile = true;
				
			}
		} catch(Exception e)
		{e.printStackTrace();}
		
	}
	
	
	public boolean isBadfile() {
		return badfile;
	}

	public void setBadfile(boolean badfile) {
		this.badfile = badfile;
	}

	public String getPDF()
	{

		FacesContext context = FacesContext.getCurrentInstance();
		String downmatrix = context.getExternalContext().getRequestParameterMap().get("sc:hiddendata");
		
//		//System.out.printlnln(downmatrix);
		
		if(downmatrix==null && downmatrix.equals("")) return null;
		
		String[] dlines = downmatrix.split("@");
		
		if(dlines.length==0) return null;
		
//		for(int i=0;i<dlines.length;i++)
//			//System.out.printlnln(dlines[i]);
		
		StringBuffer buf = new StringBuffer();

		//application/pdf
		
		buf.append("<html>");
		buf.append("<body>");
		buf.append("<table>");

		
		buf.append("<tr>");
		
		
		buf.append("<td>");
		buf.append("</td>");
		
		
		String[] dchar = dlines[0].split("'");
		
		
		for(int b=0;b<dchar.length;b++)
		{
			buf.append("<td>");
			buf.append(dchar[b]);
			buf.append("</td>");
		}
		
		buf.append("</tr>");
		
		for(int a=1;a<dlines.length;a++)
		{
			buf.append("<tr>");
			
			dchar = dlines[a].split("'");
			
			String[] dstart = dchar[0].split(":");
			
			buf.append("<td>");
			
			buf.append(dstart[0]+" <a href='http://www.ncbi.nlm.nih.gov/gene/?term="+
				dstart[1]+"'>("+dstart[1]+")</a>");
			
			buf.append("</td>");
			
			
			for(int b=1;b<dchar.length;b++)
			{
				if(dchar[b].equals("0"))
				{
					buf.append("<td style='color:white;background-color:white'>");
					buf.append("0");
					buf.append("</td>");
				}
				else
				{
					buf.append("<td style='color:black;background-color:black'>");
					buf.append("1");
					buf.append("</td>");
				}
			}
			
			
			buf.append("</tr>");
		}
		
		buf.append("</table>");
		buf.append("</body>");
		buf.append("</html>");
		
		try{
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(buf.toString());
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			renderer.layout();
			renderer.createPDF(os);
			byte[] file = os.toByteArray();
			
			
//			//System.out.printlnln("bytes size: "+file.length);
			
			
			FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    ec.responseReset();
		    ec.setResponseContentType("application/pdf");
		    ec.setResponseContentLength(file.length);
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"tpdf.pdf\"");
		    OutputStream output = ec.getResponseOutputStream();
		    output.write(file);
		    fc.responseComplete();
			
		} catch(Exception e)
		{e.printStackTrace();}
		return null;
	}
	
	public String tableToPDF()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		String downmatrix = context.getExternalContext().getRequestParameterMap().get("sc:toPDF");
		
		StringBuffer buf = new StringBuffer();
		
		buf.append("<html>");
		
		

		
		
		//System.out.printlnln("------------======== "+downmatrix);
		
		
		if(downmatrix!=null && !downmatrix.startsWith("!") && !downmatrix.endsWith("!"))
		{
			
//			System.out.println("»"+downmatrix+"«");
		
			String[] lines = downmatrix.split("!");
			
			String[] line = lines[0].split(";");
			
			
			
			buf.append("<head>");
			
			int h = 3 + (int)(Math.ceil(line.length/5)*3);
			
			double f = 5.5 + Math.ceil((lines.length-1)/2)*0.55;
			
			String[] zemp = line[0].split("images");
			
			buf.append("<style type=\"text/css\">");
			
			//start at 5.5 add 0.75 for each 2
			//1 is 5 start at 2 for each 5 give 3
//			buf.append("@page {size: 18in 6.88in;}");
//			buf.append("@page {size: 2in 6.88in;}");
			buf.append("@page {size: "+h+"in "+f+"in;}");

			
			
			
			buf.append("</style>");
			
			/*
			    <style type="text/css">
    .title {
        color: blue;
        text-decoration: bold;
        text-size: 1em;
    }

    .author {
        color: gray;
    }
    </style>
			
			
			
			buf.append("<link rel='stylesheet' type='text/css' " +
				"href='"+zemp[0]+"css/pdf.css' media='print'/>");
			*/
			
			buf.append("</head>");
			
			buf.append("<body>");
			buf.append("<table>");
			
			
			
			
			buf.append("<tr>");
			
			buf.append("<td></td>");
			
			for(int z=0;z<line.length;z++)
			{
				String[] temp = line[z].split("#");
				
//				System.out.println("PCP METH ANTIDEPRESSION PILLS "+line[z]);
				

				
				
				buf.append("<td>");
				buf.append("<table>");
				

				buf.append("<td style='height: 360px;white-space: nowrap;vertical-align: bottom;'>" +
					"<div><span><img src='"+temp[0]+"'></img></span></div></td>");
				buf.append("<tr></tr>");
				buf.append("<td style='color:#"+temp[1]+";background-color:#"+temp[1]+"'>1</td>");
				
				
				buf.append("</table>");
				buf.append("</td>");
			}
			
			buf.append("</tr>");
			
			
			
			for(int z=1;z<lines.length;z++)
			{
				line = lines[z].split(";");
				
				buf.append("<tr>");
				
				buf.append("<td style='font-size:10px'>");
				
				
				buf.append(line[1]+"<a href='http://www.ncbi.nlm.nih.gov/gene/?term="+line[0]+"'>("+line[0]+")</a>");
				
				buf.append("</td>");
				
				for(int v=2;v<line.length;v++)
				{
//					}
					if(line[v].equals("1"))
					{
						buf.append("<td style='color:#1F1F5C;background-color:#1F1F5C'>");
					}
					else
					{
						buf.append("<td style='color:white;background-color:white'>");
					}
					buf.append(line[v]);
					buf.append("</td>");
				}
				buf.append("</tr>");
			}
			
			
			
			
		}
		else
		{
			buf.append("<body>");
			buf.append("<table>");
		}
		
		buf.append("</table>");
		buf.append("</body>");
		buf.append("</html>");
		
		
		try{
			
			//System.out.printlnln(buf.toString());
			
			
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(buf.toString());
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			renderer.layout();
			renderer.createPDF(os);
			byte[] file = os.toByteArray();
			
			
			FacesContext fc = FacesContext.getCurrentInstance();
		    ExternalContext ec = fc.getExternalContext();
		    ec.responseReset();
		    ec.setResponseContentType("application/pdf");
		    ec.setResponseContentLength(file.length);
		    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"tpdf.pdf\"");
		    OutputStream output = ec.getResponseOutputStream();
		    output.write(file);
		    fc.responseComplete();
			
		} catch(Exception e)
		{e.printStackTrace();}
		
		
		return "";
	}

	public CheckMatrix[] getSecondCheckMatrix() {
		return secondCheckMatrix;
	}

	public void setSecondCheckMatrix(CheckMatrix[] secondCheckMatrix) {
	}
	
	
	public String getSecondColorsCount() {
		
		String res = "";
		
		for(int o=0;o<this.secondColorsCount.length;o++)
		{
			DecimalFormat numberFormat;
			
			if(this.secondColorsCount[o]>=0.001) numberFormat = new DecimalFormat("#.###");
			else numberFormat = new DecimalFormat("#.###E0");
			
			String cc = numberFormat.format(this.secondColorsCount[o]).replace(',', '.');

			
			if(this.secondColorsCount[o]==0) cc = "0";
			
			if(o==0) res += cc;
			else res += "!"+cc;
			
			
			
//			if(o==0) res += this.secondColorsCount[o];
//			else res += "!"+this.secondColorsCount[o];
		}
		
		return res;
	}

	public void setSecondColorsCount(String colorsCount) {
	}

	public String[][][][] getShownames() {
		
		Session s = this.sesssionFactoryClass.getSessionFactory().openSession();
		
		DatabaseExtractor de = new DatabaseExtractor();
		
		
		return de.getShownames(s);
	}

	public void setShownames(String[][][][] shownames) {
	}

	public boolean isNomatch() {
		return nomatch;
	}

	public void setNomatch(boolean nomatch) {
		this.nomatch = nomatch;
	}
	
	public String getTab1()
	{
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
//			FacesContext context = FacesContext.getCurrentInstance();
			String data = fc.getExternalContext().getRequestParameterMap().get("sc:dataToTab");
			
			System.out.println(data);
			
			String res = "";
			
			/*
			 TF Target Genes:BUB1B;701!ORC1L;4998!POU5F1;5460!PPP2R1B;5519!PTPN2;5771!SET;6418!SOX2;6657!SMARCA;56916!NANOG;79923!ABCB7;22!CITED2;10370!TEAD2;8463+Expression Profiles:BUB1B;701!ORC1L;4998!POU5F1;5460!PPP2R1B;5519!PTPN2;5771!SET;6418!SOX2;6657!NANOG;79923!ABCB7;22!TEAD2;8463!MSH6;2956+RNAi Screens:POU5F1;5460!GFER;2671+Literature Curation:POU5F1;5460!SOX2;6657!SMARCA;56916!NANOG;79923+Computationally Derived:BUB1B;701!ORC1L;4998!POU5F1;5460!SET;6418!SOX2;6657!SMARCA;56916!NANOG;79923!MSH6;2956
			*/
			
			
			String[] str1 = data.split("\\+");
			
			
			for(int a=0;a<str1.length;a++)
			{
//				res += str1[a];
				
				String[] str2 = str1[a].split(":");
				
				if(str2.length>1)
				{
					String[] str3 = str2[1].split("!");
				
					for(int b=0;b<str3.length;b++)
					{
						String[] str4 = str3[b].split(";");
						res += "'"+str2[0]+"','"+str4[0]+"','"+str4[1]+"'\n";
					}
				}
			}
			
			byte[] file = res.getBytes();
			
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("text/plain");
			ec.setResponseContentLength(file.length);
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"table.txt\"");
			OutputStream output = ec.getResponseOutputStream();
			output.write(file);
			fc.responseComplete();
			
		} catch(Exception e)
		{e.printStackTrace();}
		
		return null;
		
	}
	
	public String getTab2()
	{
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
//			FacesContext context = FacesContext.getCurrentInstance();
			String data = fc.getExternalContext().getRequestParameterMap().get("sc:dataToTab");
			
			System.out.println("2 "+data);
			
			String res = "";
			
			String[] str1 = data.split("\\+");
			
			for(int a=0;a<str1.length;a++)
			{
//				res += str1[a];
				
				String[] str2 = str1[a].split("!");
				
				for(int b=0;b<str2.length;b++)
				{
					if(b==0) res += "'"+str2[b]+"'";
					else res += ",'"+str2[b]+"'";
				}
				
				res += "\n";
				
			}
			
			byte[] file = res.getBytes();
			
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType("text/plain");
			ec.setResponseContentLength(file.length);
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"table.txt\"");
			OutputStream output = ec.getResponseOutputStream();
			output.write(file);
			fc.responseComplete();
			
		} catch(Exception e)
		{e.printStackTrace();}
		
		return null;
		
	}

	public String[] getMatches() {
		return matches;
	}

	public void setMatches(String[] matches) {
	}

	public String getNoMatches() {
		
		if(noMatches.length==0) return "";
		else
		{
			String res = noMatches[0];
			for(int i=1;i<noMatches.length;i++) res += ", "+noMatches[i];
			return res;
		}
//		return noMatches;
	}

	public void setNoMatches(String noMatches) {
	}



	public int getNumNoMatches() {
		return noMatches.length;
	}

	public void setNumNoMatches(int val) {
	}
	
	
	
	public String getNoSetMatches() {
		
		if(noSetMatches.length==0) return "";
		else
		{
			String res = noSetMatches[0];
			for(int i=1;i<noSetMatches.length;i++) res += ", "+noSetMatches[i];
			return res;
		}
	}

	public String getMasked() {
		
		if(masked.length==0) return "";
		else
		{
			String res = masked[0];
			for(int i=1;i<masked.length;i++) res += ", "+masked[i];
			return res;
		}
	}

	public int getNumMasked() {
		return masked.length;
	}

	public void setNumMasked(int noSetMatches) {
	}
	
	
	public void setMasked(String masked) {
	}

	public void setNoSetMatches(String noSetMatches) {
	}
	
	
	
	public int getNumNoSetMatches() {
		return noSetMatches.length;
	}

	public void setNumNoSetMatches(int noSetMatches) {
	}
	
	

	public int getNmatches() {
		return matches.length;
	}

	public void setNmatches(int bla) {
	}

	public int getSetNum() {
		return setNum;
	}

	public void setSetNum(int setNum) {
	}

	public int getTfNum() {
		return tfNum;
	}

	public void setTfNum(int tfNum) {
	}

	public int getNumallmatches() {
		return numallmatches;
	}

	public void setNumallmatches(int numallmatches) {
	}

	public boolean isCellProliferation() {
		return cellProliferation;
	}

	public void setCellProliferation(boolean cellProliferation) {
		this.cellProliferation = cellProliferation;
	}

	public boolean isCellCycle() {
		return cellCycle;
	}

	public void setCellCycle(boolean cellCycle) {
		this.cellCycle = cellCycle;
	}

}
