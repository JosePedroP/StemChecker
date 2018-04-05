package stemcheckerbeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.distribution.HypergeometricDistribution;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import databaseAccess.ProteinAlias;
import databaseAccess.ProteinInStemnessSignatures;
import databaseAccess.ProteinInTf;
import databaseAccess.ProteinList;
import databaseAccess.PubmedRefs;
import databaseAccess.StemnessSignatures;
import databaseAccess.TfPubmedRefs;
import databaseAccess.TfSet;

public class CopyOfDatabaseExtractor {

	//TODO: VERY IMPORTANT MAKE THEM NOT HARDCODADE IN THE FUTURE
//	protected in humanGS = 17959;
//	else populationSize = 24675;
	
	protected int humanGS = 18889;
	protected int mouseGS = 31026;


	
	
	protected double[] colorsCount = new double[]{};
	protected int[] colorsCountPerc = new int[]{};
	protected String[] idOfSetsToUse = new String[]{};
	protected String[] nameOfSetsToUse = new String[]{};
	protected String[] descriptionOfSetsToUse = new String[]{};
	protected String[] setSize = new String[]{};
	protected String[] overlaps = new String[]{};
	protected String[] overlapsids = new String[]{};
	protected String[] overlapsGeneSymbol = new String[]{};
	protected double[] pvalues = new double[]{};
	
	
	protected double[] countCellTypes = new double[]{};
	protected int[] countCellTypesPerc = new int[]{};
	protected int[] totalCountCellTypes = new int[]{};
	protected int[] trueCountCellTypes = new int[]{};
	protected double[] cellTypesPvalues = new double[]{};
	protected String[] cellTypesOverlapsids = new String[]{};
	protected String[] cellTypesOverlapsGeneSymbol = new String[]{};

	protected double[] secondColorsCount = new double[]{};
	protected int[] secondColorsCountPerc = new int[]{};
	protected double[] secondPvalues = new double[]{};
	protected String[] secondIdOfSetsToUse = new String[]{};
	protected String[] secondNameOfSetsToUse = new String[]{};
	protected String[] secondDescriptionNameOfSetsToUse = new String[]{};
	protected String[] secondSetSize = new String[]{}; 
	protected String[] secondOverlaps = new String[]{}; 
	protected String[] secondOverlapsids = new String[]{}; 
	protected String[] secondOverlapsGeneSymbol = new String[]{};
	
	protected String agreSignatureTypes = "";
	protected String agreTFData = "";
	protected String agreCellTypes = "";
	
	protected int setNum = 0;
	protected int tfNum = 0;
	
	protected String[] matches = new String[]{};
	protected String[] noMatches = new String[]{};
	protected String[] noSetMatches = new String[]{};
	

	protected int numallmatches = 0;
	
	public double[] getCountCellTypes() {
		return countCellTypes;
	}

	public int[] getCountCellTypesPerc() {
		return countCellTypesPerc;
	}

	public int[] getSecondColorsCountPerc() {
		return secondColorsCountPerc;
	}

	protected CheckMatrix[][] getSetResultsData(String[] genes, int[] setsToUse, Session s, HashMap<String,String> namesToImages, String species, String[] restrictions) {
		
		CheckMatrix[] checkMatrix = new CheckMatrix[0];
		CheckMatrix[] secondCheckMatrix = new CheckMatrix[0];
		
		
		boolean[] matched1 = new boolean[genes.length];
		boolean[] matched2 = new boolean[genes.length];
		ArrayList<String>[] aliasMatrix = new ArrayList[genes.length];
		
		for(int z=0;z<matched1.length;z++)
		{
			matched1[z] = false;
			matched2[z] = false;
			aliasMatrix[z] = new ArrayList<String>();
		}
		
		
		try {
			Transaction tx = s.beginTransaction();
			
			try {
				
				this.colorsCount = new double[]{0,0,0,0,0};
				
				this.secondColorsCount = new double[]{
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0,
						0
				};
				
				String queryString;
				String queryString2;
				String queryString3;
				String queryString4;
				String queryString4aux1;
				String queryString4aux2;
				String queryString4aux3;
//				String queryString5;
				String queryString6;
				//Homo sapiens
				//Mus musculus
				
				int populationSize = humanGS+mouseGS;
				
				Integer[] restrictedIds = new Integer[]{new Integer(0)};
				
//				restrictedIds[0] = new Integer(1);
//				restrictedIds[0] = new Integer(2);
				
				if(species==null)
				{
//					queryString = "select distinct prot from ProteinList as prot left join prot.proteinAliases as alias " +
//						"where prot.geneSymbol in (:genes) or prot.entrezGeneId in (:genes) or alias.alias in (:genes)" +
//						"order by prot.id";
					
					queryString = "select distinct prot from ProteinList as prot left join prot.proteinAliases as alias " +
						"where " +
						"prot.entrezGeneId in (:genes) " +
						"and " +
						"prot.id not in (:restrictedIds) " +
						"order by prot.id";
					//System.out.println("queryString: "+queryString);
					
					//TODO: Need to fix the querry
					queryString2 = "select ste.type,count(distinct pls) from ProteinInStemnessSignatures as pss " +
						"join pss.stemnessSignatures as ste " +
						"join pss.proteinList as pls " +
						"where pls.id not in (:restrictedIds) " +
						"group by ste.type";
					
					queryString3 = "select ste.type, count(distinct ptf.proteinList) from ProteinInTf as ptf " +
						"join ptf.tfSet as ste " +
						"where ptf.proteinList.id not in (:restrictedIds) " +
						"group by ste.type";
					
					//+++++++++++++++++++++++++++++++ 
					
					queryString4 = "select ste.cellType,count(distinct pss.proteinList) from ProteinInStemnessSignatures as pss " +
						"join pss.stemnessSignatures as ste " +
						"where ste.id in (:setz) " +
						"and pss.proteinList.id not in (:restrictedIds) " +
						"group by ste.cellType";
					
					queryString4aux1 = "select count(distinct pss.proteinList) from ProteinInStemnessSignatures as pss " +
						"join pss.stemnessSignatures as ste " +
						"where (ste.cellType = 'ESC' or ste.cellType = 'ESC/EC') and ste.id in (:setz) " 
						+ "and pss.proteinList.id not in (:restrictedIds)"
						;
					
					queryString4aux2 = "select count(distinct pss.proteinList) from ProteinInStemnessSignatures as pss " +
						"join pss.stemnessSignatures as ste " +
						"where (ste.cellType = 'HSC' or ste.cellType = 'HSC/MSC/NSC') and ste.id in (:setz)" 
						+ " and pss.proteinList.id not in (:restrictedIds)"
						;
					
					queryString4aux3 = "select count(distinct pss.proteinList) from ProteinInStemnessSignatures as pss " +
						"join pss.stemnessSignatures as ste " +
						"where (ste.cellType = 'NSC' or ste.cellType = 'HSC/MSC/NSC') and ste.id in (:setz) " 
						+"and pss.proteinList.id not in (:restrictedIds)"
						;
					
					
					//+++++++++++++++++++++++++++++++
					
					queryString6 = "select count(distinct ts) from TfSet as ts";
				}
				else
				{
					if(species.equals("Homo sapiens")) populationSize = humanGS;
					else populationSize = mouseGS;
					
					
					queryString = "select distinct prot from ProteinList as prot left join prot.proteinAliases as alias " +
						"where (prot.geneSymbol in (:genes) or prot.entrezGeneId in (:genes) or alias.alias in (:genes)) and " +
						"prot.species = '"+species+"' and prot.id not in (:restrictedIds) " +
						"order by prot.id";
					
					queryString2 = "select ste.type,count(distinct pss.proteinList) from ProteinInStemnessSignatures as pss " +
						"join pss.stemnessSignatures as ste where " +
						"ste.species = '"+species+"' and pss.proteinList.id not in (:restrictedIds) " +
						"group by ste.type";
					
					queryString3 = "select ste.type, count(distinct ptf.proteinList) from ProteinInTf as ptf " +
						"join ptf.tfSet as ste where " +
						"ste.species = '"+species+"' and ptf.proteinList.id not in (:restrictedIds) " +
						"group by ste.type";
					
					//+++++++++++++++++++++++++++++++ Needs to take sets to use into account
					
					queryString4 = "select ste.cellType,count(distinct pss.proteinList) from ProteinInStemnessSignatures as pss " +
						"join pss.stemnessSignatures as ste " +
						"where ste.species = '"+species+"' and " +
						"ste.id in (:setz) and pss.proteinList.id not in (:restrictedIds) " +
						"group by ste.cellType";
					
					queryString4aux1 = "select count(distinct pss.proteinList) from ProteinInStemnessSignatures as pss " +
						"join pss.stemnessSignatures as ste " +
						"where (ste.cellType = 'ESC' or ste.cellType = 'ESC/EC') and " +
						"ste.id in (:setz) and pss.proteinList.id not in (:restrictedIds) " +
						"and ste.species = '"+species+"'";
					
					queryString4aux2 = "select count(distinct pss.proteinList) from ProteinInStemnessSignatures as pss " +
						"join pss.stemnessSignatures as ste " +
						"where (ste.cellType = 'HSC' or ste.cellType = 'HSC/MSC/NSC') and " +
						"ste.id in (:setz) and pss.proteinList.id not in (:restrictedIds) " +
						"and ste.species = '"+species+"'";
					
					queryString4aux3 = "select count(distinct pss.proteinList) from ProteinInStemnessSignatures as pss " +
						"join pss.stemnessSignatures as ste " +
						"where (ste.cellType = 'NSC' or ste.cellType = 'HSC/MSC/NSC') and " +
						"ste.id in (:setz) and pss.proteinList.id not in (:restrictedIds) " +
						"and ste.species = '"+species+"'";
					
					
					//+++++++++++++++++++++++++++++++
					
					
					queryString6 = "select count(distinct ts) from TfSet as ts " +
						"where ts.species = '"+species+"'";
				
				}

//				String[] restrictions = new String[]{"Cell Proliferation", "Cell Cycle"};
				
				if(restrictions.length>0)
				{
					String queryStringRestrict = "select distinct aso.proteinList.id from Association as aso " +
						"where aso.type in (:restrictions)";
					
					Query queryObject = s.createQuery(queryStringRestrict).setParameterList("restrictions", restrictions);
					
					List l = queryObject.list();
					
					restrictedIds = new Integer[l.size()]; 
					
					for(int t=0;t<l.size();t++)
					{
						restrictedIds[t] = (Integer)l.get(t);
					}
					

//					System.out.println(queryStringRestrict);
//					System.out.println("FOUND: "+restrictedIds.length);
					
					//TODO: STILL need to separte the maksed ones to show then in a proper list
					if(restrictedIds.length<1) restrictedIds = new Integer[]{new Integer(0)};
				}
				
				
				Query queryObject = s.createQuery(queryString2).setParameterList("restrictedIds", restrictedIds);
//				Query queryObject = s.createQuery(queryString2);
				List l = queryObject.list();
				
				int[] sampleSizes1 = new int[]{0, 0, 0, 0, 0};
				
				for(int t=0;t<l.size();t++)
				{
					Object[] u = (Object[])l.get(t);
					int val = new Integer(u[1].toString()).intValue();
					
					if(u[0].toString().equals("TF Target Genes")) sampleSizes1[0] = val;
					else if(u[0].toString().equals("Expression Profiles")) sampleSizes1[1] = val;
					else if(u[0].toString().equals("RNAi Screens")) sampleSizes1[2] = val;
					else if(u[0].toString().equals("Literature Curation")) sampleSizes1[3] = val;
					else if(u[0].toString().equals("Computationally Derived")) sampleSizes1[4] = val;
					
//					//System.out.println(">>>>"+u[0].toString()+":"+u[1].toString());
				}
				

				
				queryObject = s.createQuery(queryString3).setParameterList("restrictedIds", restrictedIds);
//				queryObject = s.createQuery(queryString3);
				
				l = queryObject.list();
				
				int[] sampleSizes2 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				
				for(int t=0;t<l.size();t++)
				{
					Object[] u = (Object[])l.get(t);
					int val = new Integer(u[1].toString()).intValue();
					
					if(u[0].toString().equals("Nanog")) sampleSizes2[0] = val;
					else if(u[0].toString().equals("Sox2")) sampleSizes2[1] = val;
					else if(u[0].toString().equals("Oct4")) sampleSizes2[2] = val;
					else if(u[0].toString().equals("Ctcf")) sampleSizes2[3] = val;
					else if(u[0].toString().equals("E2f1")) sampleSizes2[4] = val;
					else if(u[0].toString().equals("Esrrb")) sampleSizes2[5] = val;
					else if(u[0].toString().equals("Klf4")) sampleSizes2[6] = val;
					else if(u[0].toString().equals("Mycn")) sampleSizes2[7] = val;
					else if(u[0].toString().equals("Smad1")) sampleSizes2[8] = val;
					else if(u[0].toString().equals("Stat3")) sampleSizes2[9] = val;
					else if(u[0].toString().equals("Suz12")) sampleSizes2[10] = val;
					else if(u[0].toString().equals("Tcfcp21")) sampleSizes2[11] = val;
					else if(u[0].toString().equals("Zfx")) sampleSizes2[12] = val;
					else if(u[0].toString().equals("Myc")) sampleSizes2[13] = val;
					else if(u[0].toString().equals("DMAP1")) sampleSizes2[14] = val;
					else if(u[0].toString().equals("E2F4")) sampleSizes2[15] = val;
					else if(u[0].toString().equals("GCN5")) sampleSizes2[16] = val;
					else if(u[0].toString().equals("MAX")) sampleSizes2[17] = val;
					else if(u[0].toString().equals("TIP60")) sampleSizes2[18] = val;
					else if(u[0].toString().equals("Tcf3")) sampleSizes2[19] = val;
					else if(u[0].toString().equals("Nacc1")) sampleSizes2[20] = val;
					else if(u[0].toString().equals("Nr0b1")) sampleSizes2[21] = val;
					else if(u[0].toString().equals("Zfp281")) sampleSizes2[22] = val;
					else if(u[0].toString().equals("Zfp42")) sampleSizes2[23] = val;
					else if(u[0].toString().equals("Eed")) sampleSizes2[24] = val;
					else if(u[0].toString().equals("Phc1")) sampleSizes2[25] = val;
					else if(u[0].toString().equals("Rnf2")) sampleSizes2[26] = val;
					else if(u[0].toString().equals("Tbx3")) sampleSizes2[27] = val;
					else if(u[0].toString().equals("Smad4")) sampleSizes2[28] = val;
					else if(u[0].toString().equals("Smad5")) sampleSizes2[29] = val;
					else if(u[0].toString().equals("Smad2")) sampleSizes2[30] = val;
					else if(u[0].toString().equals("Smad3")) sampleSizes2[31] = val;
					else if(u[0].toString().equals("Ring1B")) sampleSizes2[32] = val;
					else if(u[0].toString().equals("Ezh2")) sampleSizes2[33] = val;
					else if(u[0].toString().equals("Gata2")) sampleSizes2[34] = val;
					else if(u[0].toString().equals("Meis1")) sampleSizes2[35] = val;
					else if(u[0].toString().equals("Fli1")) sampleSizes2[36] = val;
					else if(u[0].toString().equals("Gfi1b")) sampleSizes2[37] = val;
					else if(u[0].toString().equals("Lmo2")) sampleSizes2[38] = val;
					else if(u[0].toString().equals("Lyl1")) sampleSizes2[39] = val;
					else if(u[0].toString().equals("Pu1")) sampleSizes2[40] = val;

					else if(u[0].toString().equals("Scl")) sampleSizes2[41] = val;
					else if(u[0].toString().equals("Runx1")) sampleSizes2[42] = val;
					else if(u[0].toString().equals("Tcf7")) sampleSizes2[43] = val;
					
					else if(u[0].toString().equals("Brn2")) sampleSizes2[44] = val;
					else if(u[0].toString().equals("Erg")) sampleSizes2[45] = val;
					
//					"Nanog",
//					"Sox2",
//					"Oct4",
//					"Ctcf",
//					"E2f1",
//					"Esrrb",
//					"Klf4",
//					"Mycn",
//					"Smad1",
//					"Stat3",
//					"Suz12",
//					"Tcfcp21",
//					"Zfx",
//					"Myc",
//					"Dmap1",
//					"E2f4",
//					"Gcn5",
//					"Max",
//					"Tip60",
//					"Tcf3",
//					"Nacc1",
//					"Nr0b1",
//					"Zfp281",
//					"Zfp42",
//					"Eed",
//					"Phc1",
//					"Rnf2",
//					"Tbx3",
//					"Smad4",
//					"Smad5",
//					"Smad2",
//					"Smad3",
//					"Ring1B",
//					"Ezh2"
//					else if(u[0].toString().equals("RNAi Screens")) sampleSizes1[2] = val;
//					else if(u[0].toString().equals("Literature Curation")) sampleSizes1[3] = val;
//					else if(u[0].toString().equals("Computationally Derived")) sampleSizes1[4] = val;
					
//					//System.out.println(">>>>"+u[0].toString()+":"+u[1].toString());
				}
				
				Integer[] setz = new Integer[setsToUse.length];
				
				for(int t=0;t<setsToUse.length;t++)
				{
					setz[t] = new Integer(setsToUse[t]);
//					//System.out.println("setz: "+setz[t]);
				}
//				//System.out.println("setz size: "+setz.length);
				
				queryObject = s.createQuery(queryString4).setParameterList("setz", setz).setParameterList("restrictedIds", restrictedIds);
//				queryObject = s.createQuery(queryString4).setParameterList("setz", setz);
				
				l = queryObject.list();

				int[] sampleSizes3 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				
				for(int t=0;t<l.size();t++)
				{
					Object[] u = (Object[])l.get(t);
					if(u[0]!=null)
					{
//						//System.out.println(">>>>"+u[0].toString()+":"+u[1].toString());
						
						int val = new Integer(u[1].toString()).intValue();
						
						if(u[0].toString().equals("ESC")) sampleSizes3[0] = val;
						else if(u[0].toString().equals("SC")) sampleSizes3[1] = val;
						else if(u[0].toString().equals("NSC")) sampleSizes3[2] = val;
						else if(u[0].toString().equals("HSC")) sampleSizes3[3] = val;
						else if(u[0].toString().equals("MaSC")) sampleSizes3[4] = val;
						else if(u[0].toString().equals("SSC")) sampleSizes3[5] = val;
						else if(u[0].toString().equals("ISC")) sampleSizes3[6] = val;
						else if(u[0].toString().equals("IPSC")) sampleSizes3[7] = val;
						else if(u[0].toString().equals("HSC/MSC/NSC")) sampleSizes3[8] = val;
						else if(u[0].toString().equals("ESC/EC")) sampleSizes3[9] = val;
						
					}
				}
				
				queryObject = s.createQuery(queryString4aux1).setParameterList("setz", setz).setParameterList("restrictedIds", restrictedIds);
//				queryObject = s.createQuery(queryString4aux1).setParameterList("setz", setz);
				
				l = queryObject.list();
				
				if(l.size()>0)
				{
					if(l.get(0)!=null)
					{
						Number num = (Number)l.get(0);
						sampleSizes3[0] = num.intValue();
					}
				}
				
				queryObject = s.createQuery(queryString4aux2).setParameterList("setz", setz).setParameterList("restrictedIds", restrictedIds);
//				queryObject = s.createQuery(queryString4aux2).setParameterList("setz", setz);
				
				l = queryObject.list();
				
				if(l.size()>0)
				{
					if(l.get(0)!=null)
					{
						Number num = (Number)l.get(0);
						sampleSizes3[3] = num.intValue();
					}
				}
				
				queryObject = s.createQuery(queryString4aux3).setParameterList("setz", setz).setParameterList("restrictedIds", restrictedIds);
//				queryObject = s.createQuery(queryString4aux3).setParameterList("setz", setz);
				
				l = queryObject.list();
				
				if(l.size()>0)
				{
					if(l.get(0)!=null)
					{
						Number num = (Number)l.get(0);
						sampleSizes3[2] = num.intValue();
					}
				}
				

				
//				queryObject = s.createQuery(queryString5);
//				
//				l = queryObject.list();
//				
//				Number num = (Number)l.get(0);
//				
//				this.setNum = num.intValue();
				
				this.setNum = setsToUse.length;

				
				queryObject = s.createQuery(queryString6);
				
				l = queryObject.list();
				
				Number num = (Number)l.get(0);
				
				this.tfNum = num.intValue();
				
				
				
//				queryObject = s.createQuery(queryString).setParameterList("genes", genes).setParameterList("genez", genes);
				queryObject = s.createQuery(queryString).setParameterList("genes", genes).setParameterList("restrictedIds", restrictedIds);
//				queryObject = s.createQuery(queryString).setParameterList("restrictedIds", restrictedIds);
//				queryObject = s.createQuery(queryString).setParameterList("genes", genes);
				
				l = queryObject.list();
			
				String[] ids = new String[l.size()];
				
				this.numallmatches = l.size();
				
//				AuxSorter[] aux = new AuxSorter[checkMatrix.length-1];
				
				ArrayList<AuxSorter> tempsets = new ArrayList<AuxSorter>();
				ArrayList<Integer> added = new ArrayList<Integer>();
				
				//For celltype
				ArrayList<String>[] foundCellType= new ArrayList[l.size()];
				
				//For stem
				boolean[] found1 = new boolean[l.size()];
				boolean[] found2 = new boolean[l.size()];
				boolean[] found3 = new boolean[l.size()];
				boolean[] found4 = new boolean[l.size()];
				boolean[] found5 = new boolean[l.size()];
				
				
				
				//For TF

				ArrayList<AuxSorter> tftempsets = new ArrayList<AuxSorter>();
				ArrayList<Integer> tfadded = new ArrayList<Integer>();
				
				boolean[] tffound1 = new boolean[l.size()];
				boolean[] tffound2 = new boolean[l.size()];
				boolean[] tffound3 = new boolean[l.size()];
				boolean[] tffound4 = new boolean[l.size()];
				boolean[] tffound5 = new boolean[l.size()];
				boolean[] tffound6 = new boolean[l.size()];
				boolean[] tffound7 = new boolean[l.size()];
				boolean[] tffound8 = new boolean[l.size()];
				boolean[] tffound9 = new boolean[l.size()];
				boolean[] tffound10 = new boolean[l.size()];
				boolean[] tffound11 = new boolean[l.size()];
				boolean[] tffound12 = new boolean[l.size()];
				boolean[] tffound13 = new boolean[l.size()];
				boolean[] tffound14 = new boolean[l.size()];
				boolean[] tffound15 = new boolean[l.size()];
				boolean[] tffound16 = new boolean[l.size()];
				boolean[] tffound17 = new boolean[l.size()];
				boolean[] tffound18 = new boolean[l.size()];
				boolean[] tffound19 = new boolean[l.size()];
				boolean[] tffound20 = new boolean[l.size()];
				boolean[] tffound21 = new boolean[l.size()];
				boolean[] tffound22 = new boolean[l.size()];
				boolean[] tffound23 = new boolean[l.size()];
				boolean[] tffound24 = new boolean[l.size()];
				boolean[] tffound25 = new boolean[l.size()];
				boolean[] tffound26 = new boolean[l.size()];
				boolean[] tffound27 = new boolean[l.size()];
				boolean[] tffound28 = new boolean[l.size()];
				boolean[] tffound29 = new boolean[l.size()];
				boolean[] tffound30 = new boolean[l.size()];
				boolean[] tffound31 = new boolean[l.size()];
				boolean[] tffound32 = new boolean[l.size()];
				boolean[] tffound33 = new boolean[l.size()];
				boolean[] tffound34 = new boolean[l.size()];
				boolean[] tffound35 = new boolean[l.size()];

				boolean[] tffound36 = new boolean[l.size()];
				boolean[] tffound37 = new boolean[l.size()];
				boolean[] tffound38 = new boolean[l.size()];

				boolean[] tffound39 = new boolean[l.size()];
				boolean[] tffound40 = new boolean[l.size()];
				boolean[] tffound41 = new boolean[l.size()];

				boolean[] tffound42 = new boolean[l.size()];
				boolean[] tffound43 = new boolean[l.size()];
				boolean[] tffound44 = new boolean[l.size()];
				
				boolean[] tffound45 = new boolean[l.size()];
				boolean[] tffound46 = new boolean[l.size()];
				
				//First filtering onverts to resutls into the sets to use both stem and TF
				for(int i=0;i<l.size();i++)
				{
					found1[i] = false;
					found2[i] = false;
					found3[i] = false;
					found4[i] = false;
					found5[i] = false;
					
					tffound1[i] = false;
					tffound2[i] = false;
					tffound3[i] = false;
					tffound4[i] =  false;
					tffound5[i] =  false;
					tffound6[i] =  false;
					tffound7[i] =  false;
					tffound8[i] =  false;
					tffound9[i] =  false;
					tffound10[i] =  false;
					tffound11[i] =  false;
					tffound12[i] =  false;
					tffound13[i] =  false;
					tffound14[i] =  false;
					tffound15[i] =  false;
					tffound16[i] =  false;
					tffound17[i] =  false;
					tffound18[i] =  false;
					tffound19[i] =  false;
					tffound20[i] =  false;
					tffound21[i] =  false;
					tffound22[i] =  false;
					tffound23[i] =  false;
					tffound24[i] =  false;
					tffound25[i] =  false;
					tffound26[i] =  false;
					tffound27[i] =  false;
					tffound28[i] =  false;
					tffound29[i] =  false;
					tffound30[i] =  false;
					tffound31[i] =  false;
					tffound32[i] =  false;
					tffound33[i] =  false;
					tffound34[i] =  false;
					tffound35[i] =  false;
					

					tffound36[i] =  false;
					tffound37[i] =  false;
					tffound38[i] =  false;
					

					tffound39[i] =  false;
					tffound40[i] =  false;
					tffound41[i] =  false;
					

					tffound42[i] =  false;
					tffound43[i] =  false;
					tffound44[i] =  false;

					tffound45[i] =  false;
					tffound46[i] =  false;
					
					ProteinList prot = ((ProteinList)l.get(i));
					
					Object[] tempsets1 = prot.getProteinInStemnessSignatureses().toArray();
					
					
					for(int z=0;z<matched2.length;z++)
					{
//						//System.out.println("knifes "+matched2[z]);
						
						if(!matched2[z])
						{
//							//System.out.println(":} "+prot.getGeneSymbol()+" : "+prot.getEntrezGeneId()+" : "+genes[z]);
							if((prot.getGeneSymbol()!= null && prot.getGeneSymbol().equalsIgnoreCase(genes[z])) || prot.getEntrezGeneId().equals(genes[z]))
							{
								matched2[z] = true;
								
							}
							else if(species!=null)
							{
								Object[] aliases = prot.getProteinAliases().toArray();
								
								for(int t=0;t<aliases.length && !matched2[z];t++)
								{
									String alias = ((ProteinAlias)aliases[t]).getAlias();
									
//									//System.out.println(":> "+alias+" : "+genes[z]);
//									//System.out.println(alias.equals(genes[z]));
									
									if(alias.equalsIgnoreCase(genes[z]))
									{
										matched2[z] = true;
										if(prot.getGeneSymbol()!= null && !aliasMatrix[z].contains(prot.getGeneSymbol())) aliasMatrix[z].add(prot.getGeneSymbol().toUpperCase());
										if(!aliasMatrix[z].contains(prot.getEntrezGeneId())) aliasMatrix[z].add(prot.getEntrezGeneId().toUpperCase());
									}
								}
								
							}
							
							
//							aliasMatrix
						}
						
					}
					
					
					for(int z=0;z<tempsets1.length;z++)
					{	//StemnessSignatures
						StemnessSignatures pss = ((ProteinInStemnessSignatures)tempsets1[z]).getStemnessSignatures();
						
						Integer it = new Integer(pss.getId());
						
						AuxSorter ax = new AuxSorter(pss, it);
						
						if(!added.contains(it))
						{
							added.add(it);
							
							boolean go = false;
							
							if(setsToUse==null) go = true;
							
							for(int y=0;!go && y<setsToUse.length;y++)
							{
								if(setsToUse[y]==pss.getId().intValue())
									go = true;
							}
							
							if(go) tempsets.add(ax);
						}
					}
					
					
					Object[] tempsets2 = prot.getProteinInTfs().toArray();
					
					for(int z=0;z<tempsets2.length;z++)
					{
						TfSet pss = ((ProteinInTf)tempsets2[z]).getTfSet();
						
						Integer it = new Integer(pss.getId());
						
						AuxSorter ax = new AuxSorter(pss, it);
						
						if(!tfadded.contains(it))
						{
							tfadded.add(it);
							tftempsets.add(ax);
						}
						
					}
					
					
				}
				
				//Stem
				AuxSorter[] setsinsample = new AuxSorter[tempsets.size()];
				
				int[] found = new int[setsinsample.length];
				ArrayList<Integer>[] foundid = new ArrayList[setsinsample.length];
				ArrayList<String>[] foundidgs = new ArrayList[setsinsample.length];
				
				this.nameOfSetsToUse = new String[setsinsample.length];
				this.descriptionOfSetsToUse = new String[setsinsample.length];
				this.pvalues = new double[setsinsample.length];
				this.setSize = new String[setsinsample.length];
				this.overlaps = new String[setsinsample.length];
				this.overlapsids = new String[setsinsample.length];
				this.overlapsGeneSymbol = new String[setsinsample.length];
				this.idOfSetsToUse = new String[setsinsample.length];
				
				
				for(int i=0;i<setsinsample.length;i++)
				{
					setsinsample[i] = tempsets.get(i);
					found[i] = 0;
					foundid[i] = new ArrayList<Integer>();
					foundidgs[i] = new ArrayList<String>();
					
				}
				
				
				Arrays.sort(setsinsample);
				
				//TF
				AuxSorter[] tfsetsinsample = new AuxSorter[tftempsets.size()];
				
				int[] tffound = new int[tfsetsinsample.length];
				ArrayList<Integer>[] tffoundid = new ArrayList[tfsetsinsample.length];
				ArrayList<String>[] tffoundGS = new ArrayList[tfsetsinsample.length];
				
				this.secondNameOfSetsToUse = new String[tfsetsinsample.length];
				this.secondDescriptionNameOfSetsToUse = new String[tfsetsinsample.length];
				this.secondPvalues = new double[tfsetsinsample.length];
				this.secondSetSize = new String[tfsetsinsample.length];
				this.secondOverlaps = new String[tfsetsinsample.length];
				this.secondOverlapsids = new String[tfsetsinsample.length];
				this.secondOverlapsGeneSymbol = new String[tfsetsinsample.length];
				this.secondIdOfSetsToUse = new String[tfsetsinsample.length];
				
				for(int i=0;i<tfsetsinsample.length;i++)
				{
					tfsetsinsample[i] = tftempsets.get(i);
					tffound[i] = 0;
					tffoundid[i] = new ArrayList<Integer>();
					tffoundGS[i] = new ArrayList<String>();
				}
				

				Arrays.sort(tfsetsinsample);
				
				
//				this.checkMatrix = new CheckMatrix[this.foundLines.length+1];
				
//				AuxSorter[] aux = new AuxSorter[checkMatrix.length-1];
				
				checkMatrix = new CheckMatrix[l.size()+1];
				secondCheckMatrix = new CheckMatrix[l.size()+1];
				
				for(int a=0;a<checkMatrix.length;a++)
				{
					checkMatrix[a] = new CheckMatrix(setsinsample.length+1, ""+a);
					
					for(int b=0;b<checkMatrix[a].getData().length;b++)
					{
//						this.checkMatrix[a][b][3] = ""+a; //row
						checkMatrix[a].getData()[b][2] = ""+b; //clumne
						if(a==0 && b==0)
						{
							checkMatrix[a].getData()[b][0] = "";
							checkMatrix[a].getData()[b][1] = "-1";
							checkMatrix[a].getData()[b][3] = "base"; //0x0 pos
						}
						else if(a==0 && b!=0)
						{							
							StemnessSignatures pss = (StemnessSignatures)setsinsample[b-1].getA();
							
							String color = "#FFFFFF";
							
							if(pss.getType().equals("TF Target Genes"))
							{
								color = "#FF0000";
							}
							else if(pss.getType().equals("Expression Profiles"))
							{
								color = "#0DC4E0";
							}
							else if(pss.getType().equals("RNAi Screens"))
							{
								color = "#75ad3e";
							}
							else if(pss.getType().equals("Literature Curation"))
							{
								color = "#FFFF66";
							}
							else if(pss.getType().equals("Computationally Derived"))
							{
								color = "#220927";
							}
							
							if(!namesToImages.containsKey(pss.getName()))
								//System.out.println(">"+pss.getName()+"<");
							
							checkMatrix[a].getData()[b][0] = namesToImages.get(pss.getName());
							checkMatrix[a].getData()[b][1] = "-2";
							checkMatrix[a].getData()[b][3] = "col"; //columne
							checkMatrix[a].getData()[b][4] = color;
							
						}
						else if(a!=0 && b==0)
						{	
							
							
							ProteinList prot = ((ProteinList)l.get(a-1));
							
							String ex = "";
							
							if(species==null)
							{
								if(prot.getSpecies().equals("Mus musculus")) ex = "Mm ";
								else ex = "Hs ";
							}
							
							String symb = "";
							
							if(prot.getGeneSymbol()!=null) symb = prot.getGeneSymbol();
							
							checkMatrix[a].getData()[b][0] = ex+symb;
							checkMatrix[a].getData()[b][1] = prot.getEntrezGeneId();
							checkMatrix[a].getData()[b][3] = "row"; //row
							checkMatrix[a].setSpecies(prot.getSpecies());
							if(symb.equals("")) checkMatrix[a].setGeneSymbol("NA");
							else checkMatrix[a].setGeneSymbol(symb);
						}
						else
						{ //START NUMBERING HERE
							
							StemnessSignatures pss = (StemnessSignatures)setsinsample[b-1].getA();
							
							ProteinList prot = ((ProteinList)l.get(a-1));
							
							boolean foundit = false;
							
							Object[] lar = prot.getProteinInStemnessSignatureses().toArray();
							
							for(int t=0;!foundit && t<lar.length;t++)
							{
								ProteinInStemnessSignatures ptemp = (ProteinInStemnessSignatures)lar[t];
								
								foundit = ptemp.getStemnessSignatures().getId().equals(pss.getId());
							}
							
//							checkMatrix[a].getData()[b][0] = "0";
							
							if(foundit)
							{
								checkMatrix[a].getData()[b][0] = "1";
								
								/*
								0 ESC 6498 3079
								1 SC 999 262
								2 NSC 2320 117
								3 HSC 2073 210
								4 MaSC
								5 SSC
								6 ISC
								7 IPSC
								8 HSC/MSC/NSC
								9 ESC/EC -> ESC and EC
								 */
								
								if(pss.getCellType()!=null  && !pss.getCellType().equals("PSC"))
								{
									if(foundCellType[a-1]==null)
										foundCellType[a-1] = new ArrayList<String>();
									
									if(pss.getCellType().equals("HSC/MSC/NSC"))
									{
										if(!foundCellType[a-1].contains("HSC"))
										{
											foundCellType[a-1].add("HSC");
										}
										
										if(!foundCellType[a-1].contains("NSC"))
										{
											foundCellType[a-1].add("NSC");
										}
									}
									else if(pss.getCellType().equals("ESC/EC"))
									{
										if(!foundCellType[a-1].contains("ESC"))
										{
											foundCellType[a-1].add("ESC");
										}
									}
									
									
									if(!foundCellType[a-1].contains(pss.getCellType()))
									{
										foundCellType[a-1].add(pss.getCellType());
									}
								}
								
								if(pss.getType().equals("TF Target Genes"))
								{
									found1[a-1] = true;
								}
								else if(pss.getType().equals("Expression Profiles"))
								{
									found2[a-1] = true;
								}
								else if(pss.getType().equals("RNAi Screens"))
								{
									found3[a-1] = true;
								}
								else if(pss.getType().equals("Literature Curation"))
								{
									found4[a-1] = true;
								}
								else if(pss.getType().equals("Computationally Derived"))
								{
									found5[a-1] = true;
								}
								else //System.out.println("a%%%%%% "+pss.getType());
								
								found[b-1]++;
								foundid[b-1].add(prot.getId());
								foundidgs[b-1].add(prot.getGeneSymbol());
								
								
							}
							else checkMatrix[a].getData()[b][0] = "0";
								
							checkMatrix[a].getData()[b][1] = "-1";
							checkMatrix[a].getData()[b][3] = "other"; //other
							checkMatrix[a].setDatabseId(prot.getId().toString());
						}
					}
					
					
					
					
					secondCheckMatrix[a] = new CheckMatrix(tfsetsinsample.length+1, ""+a);
					
					for(int b=0;b<secondCheckMatrix[a].getData().length;b++)
					{
//						this.checkMatrix[a][b][3] = ""+a; //row
						secondCheckMatrix[a].getData()[b][2] = ""+b; //clumne
						if(a==0 && b==0)
						{
							secondCheckMatrix[a].getData()[b][0] = "";
							secondCheckMatrix[a].getData()[b][1] = "-1";
							secondCheckMatrix[a].getData()[b][3] = "base"; //0x0 pos
						}
						else if(a==0 && b!=0)
						{
							
							
							TfSet pss = (TfSet)tfsetsinsample[b-1].getA();
							
							String color = "#FFFFFF";
							
							if(pss.getType().equals("Nanog"))
							{
								color = "#1200D0";
							}
							else if(pss.getType().equals("Sox2"))
							{
								color = "#FFCF17";
							}
							else if(pss.getType().equals("Oct4"))
							{
								color = "#9A9A9A";
							}
							else if(pss.getType().equals("Ctcf"))
							{
								color = "#FFFF66";
							}
							else if(pss.getType().equals("E2f1"))
							{
								color = "#003300";
							}
							else if(pss.getType().equals("Esrrb"))
							{
								color = "#3366CC";
							}
							else if(pss.getType().equals("Klf4"))
							{
								color = "#990033";
							}
							else if(pss.getType().equals("Mycn"))
							{
								color = "#CCFFFF";
							}
							else if(pss.getType().equals("Smad1"))
							{
								color = "#52CC7A";
							}
							else if(pss.getType().equals("Stat3"))
							{
								color = "#220927";
							}
							else if(pss.getType().equals("Suz12"))
							{
								color = "#006666";
							}
							else if(pss.getType().equals("Tcfcp21"))
							{
								color = "#993300";
							}
							else if(pss.getType().equals("Zfx"))
							{
								color = "#999966";
							}
							else if(pss.getType().equals("Myc"))
							{
								color = "#003300";
							}
							else if(pss.getType().equals("DMAP1"))
							{
								color = "#000000";
							}
							else if(pss.getType().equals("E2F4") || pss.getType().equals("E2f4"))
							{
								color = "#CC00FF";
							}
							else if(pss.getType().equals("GCN5"))
							{
								color = "#FF9933";
							}
							else if(pss.getType().equals("MAX"))
							{
								color = "#75A3A3";
							}
							else if(pss.getType().equals("TIP60"))
							{
								color = "#000099";
							}
							else if(pss.getType().equals("Tcf3"))
							{
								color = "#FF5050";
							}
							else if(pss.getType().equals("Nacc1"))
							{
								color = "#E843BF";
							}
							else if(pss.getType().equals("Nr0b1"))
							{
								color = "#220927";
							}
							else if(pss.getType().equals("Zfp281"))
							{
								color = "#5CE62E";
							}
							else if(pss.getType().equals("Zfp42"))
							{
								color = "#FFA3FF";
							}
							else if(pss.getType().equals("Eed"))
							{
								color = "#334C80";
							}
							else if(pss.getType().equals("Phc1"))
							{
								color = "#FF0000";
							}
							else if(pss.getType().equals("Rnf2"))
							{
								color = "#FFCC99";
							}
							else if(pss.getType().equals("Tbx3"))
							{
								color = "#009999";
							}
							else if(pss.getType().equals("Smad4"))
							{
								color = "#005C1F";
							}
							else if(pss.getType().equals("Smad5"))
							{
								color = "#4D8D62";
							}
							else if(pss.getType().equals("Smad2"))
							{
								color = "#8DB69A";
							}
							else if(pss.getType().equals("Smad3"))
							{
								color = "#C6DACC";
							}
							else if(pss.getType().equals("Ring1B"))
							{
								color = "#00FFFF";
							}
							else if(pss.getType().equals("Ezh2"))
							{
								color = "#000080";
							}
							else if(pss.getType().equals("Gata2"))
							{
								color = "#000F8F";
							}
							else if(pss.getType().equals("Meis1"))
							{
								color = "#FAA3FF";
							}
							else if(pss.getType().equals("Fli1"))
							{
								color = "#4D339A";
							}
							else if(pss.getType().equals("Gfi1b"))
							{
								color = "#AAB979";
							}
							
							else if(pss.getType().equals("Lmo2"))
							{
								color = "#FF0AAB";
							}
							else if(pss.getType().equals("Lyl1"))
							{
								color = "#234927";
							}
							else if(pss.getType().equals("Pu1"))
							{
								color = "#BFB978";
							}
							

							
							else if(pss.getType().equals("Scl"))
							{
								color = "#FCCBAB";
							}
							else if(pss.getType().equals("Runx1"))
							{
								color = "#2B4F07";
							}
							else if(pss.getType().equals("Tcf7"))
							{
								color = "#0FB900";
							}
							
							else if(pss.getType().equals("Brn2"))
							{
								color = "#CAA36F";
							}
							else if(pss.getType().equals("Erg"))
							{
								color = "#0FC810";
							}
							
							if(!namesToImages.containsKey(pss.getName()))
								//System.out.println(">"+pss.getName()+"<");
							
							secondCheckMatrix[a].getData()[b][0] = namesToImages.get(pss.getName());
							secondCheckMatrix[a].getData()[b][1] = "-2";
							secondCheckMatrix[a].getData()[b][3] = "col"; //columne
							secondCheckMatrix[a].getData()[b][4] = color;
							
						}
						else if(a!=0 && b==0)
						{	
							
							
							ProteinList prot = ((ProteinList)l.get(a-1));
							
							String ex = "";
							
							if(species==null)
							{
								if(prot.getSpecies().equals("Mus musculus")) ex = "Mm ";
								else ex = "Hs ";
							}
							
							String symb = "";
							
							if(prot.getGeneSymbol()!=null) symb = prot.getGeneSymbol();
							
							secondCheckMatrix[a].getData()[b][0] = ex+symb;
							secondCheckMatrix[a].getData()[b][1] = prot.getEntrezGeneId();
							
							secondCheckMatrix[a].getData()[b][3] = "row"; //row
							secondCheckMatrix[a].setSpecies(prot.getSpecies());
							

							if(symb.equals("")) secondCheckMatrix[a].setGeneSymbol("NA");
							else secondCheckMatrix[a].setGeneSymbol(symb);
							
						}
						else
						{ //START NUMBERING HERE
								
							TfSet pss = (TfSet)tfsetsinsample[b-1].getA();
							
							ProteinList prot = ((ProteinList)l.get(a-1));
							
							boolean foundit = false;
							
							Object[] lar = prot.getProteinInTfs().toArray();
							
							for(int t=0;!foundit && t<lar.length;t++)
							{
								ProteinInTf ptemp = (ProteinInTf)lar[t];
								
								foundit = ptemp.getTfSet().getId().equals(pss.getId());
							}
							
//							secondCheckMatrix[a].getData()[b][0] = "0";
							
							if(foundit)
							{
								secondCheckMatrix[a].getData()[b][0] = "1";
								
								if(pss.getType().equals("Nanog"))
								{
									tffound1[a-1] = true;
								}
								else if(pss.getType().equals("Sox2"))
								{
									tffound2[a-1] = true;
								}
								else if(pss.getType().equals("Oct4"))
								{
									tffound3[a-1] = true;
								}
								else if(pss.getType().equals("Ctcf"))
								{
									tffound4[a-1] = true;
								}
								else if(pss.getType().equals("E2f1"))
								{
									tffound5[a-1] = true;
								}
								else if(pss.getType().equals("Esrrb"))
								{
									tffound6[a-1] = true;
								}
								else if(pss.getType().equals("Klf4"))
								{
									tffound7[a-1] = true;
								}
								else if(pss.getType().equals("Mycn"))
								{
									tffound8[a-1] = true;
								}
								else if(pss.getType().equals("Smad1"))
								{
									tffound9[a-1] = true;
								}
								else if(pss.getType().equals("Stat3"))
								{
									tffound10[a-1] = true;
								}
								else if(pss.getType().equals("Suz12"))
								{
									tffound11[a-1] = true;
								}
								else if(pss.getType().equals("Tcfcp21"))
								{
									tffound12[a-1] = true;
								}
								else if(pss.getType().equals("Zfx"))
								{
									tffound13[a-1] = true;
								}
								else if(pss.getType().equals("Myc"))
								{
									tffound14[a-1] = true;
								}
								else if(pss.getType().equals("DMAP1"))
								{
									tffound15[a-1] = true;
								}
								else if(pss.getType().equals("E2F4") || pss.getType().equals("E2f4"))
								{
									tffound16[a-1] = true;
								}
								else if(pss.getType().equals("GCN5"))
								{
									tffound17[a-1] = true;
								}
								else if(pss.getType().equals("MAX"))
								{
									tffound18[a-1] = true;
								}
								else if(pss.getType().equals("TIP60"))
								{
									tffound19[a-1] = true;
								}
								else if(pss.getType().equals("Tcf3"))
								{
									tffound20[a-1] = true;
								}
								else if(pss.getType().equals("Nacc1"))
								{
									tffound21[a-1] = true;
								}
								else if(pss.getType().equals("Nr0b1"))
								{
									tffound22[a-1] = true;
								}
								else if(pss.getType().equals("Zfp281"))
								{
									tffound23[a-1] = true;
								}
								else if(pss.getType().equals("Zfp42"))
								{
									tffound24[a-1] = true;
								}
								else if(pss.getType().equals("Eed"))
								{
									tffound25[a-1] = true;
								}
								else if(pss.getType().equals("Phc1"))
								{
									tffound26[a-1] = true;
								}
								else if(pss.getType().equals("Rnf2"))
								{
									tffound27[a-1] = true;
								}
								else if(pss.getType().equals("Tbx3"))
								{
									tffound28[a-1] = true;
								}
								else if(pss.getType().equals("Smad4"))
								{
									tffound29[a-1] = true;
								}
								else if(pss.getType().equals("Smad5"))
								{
									tffound30[a-1] = true;
								}
								else if(pss.getType().equals("Smad2"))
								{
									tffound31[a-1] = true;
								}
								else if(pss.getType().equals("Smad3"))
								{
									tffound32[a-1] = true;
								}
								else if(pss.getType().equals("Ring1B"))
								{
									tffound33[a-1] = true;
								}
								else if(pss.getType().equals("Ezh2"))
								{
									tffound34[a-1] = true;
								}
								else if(pss.getType().equals("Gata2"))
								{
									tffound35[a-1] = true;
								}
								else if(pss.getType().equals("Meis1"))
								{
									tffound36[a-1] = true;
								}
								else if(pss.getType().equals("Fli1"))
								{
									tffound37[a-1] = true;
								}
								else if(pss.getType().equals("Gfi1b"))
								{
									tffound38[a-1] = true;
								}
								
								else if(pss.getType().equals("Lmo2"))
								{
									tffound39[a-1] = true;
								}
								else if(pss.getType().equals("Lyl1"))
								{
									tffound40[a-1] = true;
								}
								else if(pss.getType().equals("Pu1"))
								{
									tffound41[a-1] = true;
								}
								
								else if(pss.getType().equals("Scl"))
								{
									tffound42[a-1] = true;
								}
								else if(pss.getType().equals("Runx1"))
								{
									tffound43[a-1] = true;
								}
								else if(pss.getType().equals("Tcf7"))
								{
									tffound44[a-1] = true;
								}
								
								else if(pss.getType().equals("Brn2"))
								{
									tffound45[a-1] = true;
								}
								else if(pss.getType().equals("Erg"))
								{
									tffound46[a-1] = true;
								}
								
								else //System.out.println("b%%%%%% "+pss.getType());
								
								tffound[b-1]++;
								tffoundid[b-1].add(prot.getId());
								tffoundGS[b-1].add(prot.getGeneSymbol());
								
							}
							else secondCheckMatrix[a].getData()[b][0] = "0";
								
							secondCheckMatrix[a].getData()[b][1] = "-1";
							secondCheckMatrix[a].getData()[b][3] = "other"; //other*/
						}
					}
				}
					
				tx.commit();
				
				/*
				0 ESC
				1 SC
				2 NSC
				3 HSC
				4 MaSC
				5 SSC
				6 ISC
				7 IPSC
				8 HSC/MSC/NSC
				9 ESC/EC
				 */
				
				this.countCellTypes = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				String[] tempAgData = new String[]{"", "", "", "", ""};
				
				String[] tempAgCellTypes = new String[]{"", "", "", "", "", "", "", "", "", ""};
				
				this.cellTypesOverlapsids = new String[]{"", "", "", "", "", "", "", "", "", ""};
				this.cellTypesOverlapsGeneSymbol = new String[]{"", "", "", "", "", "", "", "", "", ""};
				
				
				for(int a=0;a<found1.length;a++) 
				{
					if(found1[a])
					{
						this.colorsCount[0]++;
//						//System.out.println("May regreet it:"+checkMatrix[a+1].getData()[0][0]);
						if(tempAgData[0].equals("")) tempAgData[0] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
						else tempAgData[0] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
					}
					if(found2[a])
					{
						this.colorsCount[1]++;
						if(tempAgData[1].equals("")) tempAgData[1] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
						else tempAgData[1] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
					}
					if(found3[a])
					{
						this.colorsCount[2]++;
						if(tempAgData[2].equals("")) tempAgData[2] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
						else tempAgData[2] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
					}
					if(found4[a])
					{
						this.colorsCount[3]++;
						if(tempAgData[3].equals("")) tempAgData[3] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
						else tempAgData[3] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
					}
					if(found5[a])
					{
						this.colorsCount[4]++;
						if(tempAgData[4].equals("")) tempAgData[4] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
						else tempAgData[4] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
					}
					ArrayList<String> cellT = foundCellType[a];
					
					
//					//System.out.println("I was "+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1]);
					
					
					for(int x=0;cellT!=null && x<cellT.size();x++)
					{
						if(cellT.get(x).equals("ESC"))
						{
							int z=0;
							this.countCellTypes[z]++;
							if(tempAgCellTypes[z].equals("")) tempAgCellTypes[z] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							else tempAgCellTypes[z] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							
							if(cellTypesOverlapsids[z].equals(""))
							{
								cellTypesOverlapsGeneSymbol[z] = checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] = checkMatrix[a+1].getDatabseId();
							}
							else
							{
								cellTypesOverlapsGeneSymbol[z] += " "+checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] += "?"+checkMatrix[a+1].getDatabseId();
							}
							
						}
						else if(cellT.get(x).equals("SC")) 
						{
							int z=1;
							this.countCellTypes[z]++;
							if(tempAgCellTypes[z].equals("")) tempAgCellTypes[z] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							else tempAgCellTypes[z] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							
							if(cellTypesOverlapsids[z].equals(""))
							{
								cellTypesOverlapsGeneSymbol[z] = checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] = checkMatrix[a+1].getDatabseId();
							}
							else
							{
								cellTypesOverlapsGeneSymbol[z] += " "+checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] += "?"+checkMatrix[a+1].getDatabseId();
							}
						}
						else if(cellT.get(x).equals("NSC")) 
						{
							int z=2;
							this.countCellTypes[z]++;
							if(tempAgCellTypes[z].equals("")) tempAgCellTypes[z] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							else tempAgCellTypes[z] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							
							if(cellTypesOverlapsids[z].equals(""))
							{
								cellTypesOverlapsGeneSymbol[z] = checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] = checkMatrix[a+1].getDatabseId();
							}
							else
							{
								cellTypesOverlapsGeneSymbol[z] += " "+checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] += "?"+checkMatrix[a+1].getDatabseId();
							}
						}
						else if(cellT.get(x).equals("HSC")) 
						{
							int z=3;
							this.countCellTypes[z]++;
							if(tempAgCellTypes[z].equals("")) tempAgCellTypes[z] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							else tempAgCellTypes[z] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							
							if(cellTypesOverlapsids[z].equals(""))
							{
								cellTypesOverlapsGeneSymbol[z] = checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] = checkMatrix[a+1].getDatabseId();
							}
							else
							{
								cellTypesOverlapsGeneSymbol[z] += " "+checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] += "?"+checkMatrix[a+1].getDatabseId();
							}
						}
						else if(cellT.get(x).equals("MaSC")) 
						{
							int z=4;
							this.countCellTypes[z]++;
							if(tempAgCellTypes[z].equals("")) tempAgCellTypes[z] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							else tempAgCellTypes[z] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							
							if(cellTypesOverlapsids[z].equals(""))
							{
								cellTypesOverlapsGeneSymbol[z] = checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] = checkMatrix[a+1].getDatabseId();
							}
							else
							{
								cellTypesOverlapsGeneSymbol[z] += " "+checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] += "?"+checkMatrix[a+1].getDatabseId();
							}
						}
						else if(cellT.get(x).equals("SSC")) 
						{
							int z=5;
							this.countCellTypes[z]++;
							if(tempAgCellTypes[z].equals("")) tempAgCellTypes[z] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							else tempAgCellTypes[z] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							
							if(cellTypesOverlapsids[z].equals(""))
							{
								cellTypesOverlapsGeneSymbol[z] = checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] = checkMatrix[a+1].getDatabseId();
							}
							else
							{
								cellTypesOverlapsGeneSymbol[z] += " "+checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] += "?"+checkMatrix[a+1].getDatabseId();
							}
						}
						else if(cellT.get(x).equals("ISC")) 
						{
							int z=6;
							this.countCellTypes[z]++;
							if(tempAgCellTypes[z].equals("")) tempAgCellTypes[z] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							else tempAgCellTypes[z] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							
							if(cellTypesOverlapsids[z].equals(""))
							{
								cellTypesOverlapsGeneSymbol[z] = checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] = checkMatrix[a+1].getDatabseId();
							}
							else
							{
								cellTypesOverlapsGeneSymbol[z] += " "+checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] += "?"+checkMatrix[a+1].getDatabseId();
							}
						}
						else if(cellT.get(x).equals("IPSC")) 
						{
							int z=7;
							this.countCellTypes[z]++;
							if(tempAgCellTypes[z].equals("")) tempAgCellTypes[z] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							else tempAgCellTypes[z] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							
							if(cellTypesOverlapsids[z].equals(""))
							{
								cellTypesOverlapsGeneSymbol[z] = checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] = checkMatrix[a+1].getDatabseId();
							}
							else
							{
								cellTypesOverlapsGeneSymbol[z] += " "+checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] += "?"+checkMatrix[a+1].getDatabseId();
							}
						}
						else if(cellT.get(x).equals("HSC/MSC/NSC")) 
						{
							int z=8;
							this.countCellTypes[z]++;
							if(tempAgCellTypes[z].equals("")) tempAgCellTypes[z] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							else tempAgCellTypes[z] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							
							if(cellTypesOverlapsids[z].equals(""))
							{
								cellTypesOverlapsGeneSymbol[z] = checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] = checkMatrix[a+1].getDatabseId();
							}
							else
							{
								cellTypesOverlapsGeneSymbol[z] += " "+checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] += "?"+checkMatrix[a+1].getDatabseId();
							}
						}
						else if(cellT.get(x).equals("ESC/EC")) 
						{
							int z=9;
							this.countCellTypes[z]++;
							if(tempAgCellTypes[z].equals("")) tempAgCellTypes[z] = checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							else tempAgCellTypes[z] += "!"+checkMatrix[a+1].getData()[0][0]+";"+checkMatrix[a+1].getData()[0][1];
							
							if(cellTypesOverlapsids[z].equals(""))
							{
								cellTypesOverlapsGeneSymbol[z] = checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] = checkMatrix[a+1].getDatabseId();
							}
							else
							{
								cellTypesOverlapsGeneSymbol[z] += " "+checkMatrix[a+1].getData()[0][0];
								cellTypesOverlapsids[z] += "?"+checkMatrix[a+1].getDatabseId();
							}
							
						}
					}
					
				}

				this.agreSignatureTypes = "TF Target Genes:"+tempAgData[0]+"+Expression Profiles:"+tempAgData[1]+
					"+RNAi Screens:"+tempAgData[2]+"+Literature Curation:"+tempAgData[3]+
					"+Computationally Derived:"+tempAgData[4];
				
				this.agreCellTypes = 
					"Embryonic Stem cells:"+tempAgCellTypes[0]+
					"+Neural Stem cells:"+tempAgCellTypes[2]+
					"+Hematopoietic Stem cells:"+tempAgCellTypes[3]+
					"+Mammary Stem cells:"+tempAgCellTypes[4]+
					"+Spermatogonial Stem cells:"+tempAgCellTypes[5]+
					"+Intestinal stem cells:"+tempAgCellTypes[6]+
					"+Induced pluripotent Stem cells:"+tempAgCellTypes[7]+
					"+Mesenchymal Stem cells:"+tempAgCellTypes[8]+
					"+Embryonal carcinoma:"+tempAgCellTypes[9];
					
//				//System.out.println(this.agreCellTypes);
				
				
				/*
				for other repalce siz with all
				when human+muse add both
				TODO: inspiration from here
				inchekmatrixbyspecies = checkMatrix.length-1 in this case
				
				
				Hypergeometric HG = new Hypergeometric(siz, genomeSize, inchekmatrixbyspecies);
				*/
				
				int markedItemsCount = checkMatrix.length-1;
				
				int[] sampleSizes = sampleSizes1;
				
				this.colorsCountPerc = new int[this.colorsCount.length]; 
				
				for(int pc=0;pc<sampleSizes.length;pc++)
				{
					double x1 = this.colorsCount[pc];
					x1 = (x1/found1.length)*100;
					this.colorsCountPerc[pc] = (new Double(x1)).intValue();
					
					int sampleSize = sampleSizes[pc];
					
					int x = new Double(this.colorsCount[pc]).intValue();
					if(x>0 && sampleSize>0)
					{
//						//System.out.println("...............................");
//						//System.out.println("sampleSize "+sampleSize);
//						//System.out.println("populationSize "+populationSize);
//						//System.out.println("markedItemsCount "+markedItemsCount);
						
//						Hypergeometric HG = new Hypergeometric(sampleSize, populationSize, markedItemsCount);
						
						HypergeometricDistribution hd = new HypergeometricDistribution(populationSize, markedItemsCount, sampleSize);
						
//						Hypergeometric HG = new Hypergeometric(sampleSize, populationSize, markedItemsCount);
						
//						double a = HG.upperTailProb(x);
//						
//						if(a<Double.MIN_VALUE) a = Double.MIN_VALUE;
//						
//						
//						double b = Math.log10(a);
//						
//						
//						//System.out.println("x "+x);
//						//System.out.println("a "+a);
//						//System.out.println("b "+b);
//						
//						double min = Double.MIN_VALUE;
//						
//						//System.out.println(min);
//						
//						//System.out.println(Math.log10(min));
//						
//						this.colorsCount[pc] = 0-Math.log10(HG.upperTailProb(x));
						
//						double a = HG.upperTailProb(x);
						
						double a = hd.upperCumulativeProbability(x);
						
						//System.out.println("x "+x);
						//System.out.println("a "+a);
						
						
//						double dada = 1E-16;
//						
//						//System.out.println("GAAAAAAAAAAAA "+dada);
						
//						if(a<Double.MIN_VALUE) a = Double.MIN_VALUE;
						
						if(a<1E-16) a = 1E-16;
						
						double b = Math.log10(a);
						//System.out.println("b "+b);
						
						this.colorsCount[pc] = 0-b;
						
					}
					else this.colorsCount[pc] = 0;
				}
				
				//TODO: Cell types here The sampleSizes3 is being badly calculated I must only take into account the genes in the selected sets or do I? Yes I do, I need to change queryString4 to take only those values into account 
				
				sampleSizes = sampleSizes3;
				
				
				
/*
				protected int[] trueCountCellTypes = new int[]{};
				protected double[] cellTypesPvalues = new double[]{};
				protected String[] cellTypesOverlapsids = new String[]{}; 
				*/
				
				
				this.countCellTypesPerc = new int[this.countCellTypes.length];
				
				this.totalCountCellTypes = new int[this.countCellTypes.length];
				this.trueCountCellTypes = new int[this.countCellTypes.length];
				this.cellTypesPvalues = new double[this.countCellTypes.length];
				
				for(int a=0;a<this.countCellTypes.length;a++) 
				{
					double x1 = this.countCellTypes[a];
					x1 = (x1/found1.length)*100;
					this.countCellTypesPerc[a] = (new Double(x1)).intValue();
					
					int sampleSize = sampleSizes[a];
					
					double x = this.countCellTypes[a];
					
					this.totalCountCellTypes[a] = sampleSize;
					this.trueCountCellTypes[a] = new Double(x).intValue();
					
					if(x>0 && sampleSize>0)
					{
//						Hypergeometric HG = new Hypergeometric(sampleSize, populationSize, markedItemsCount);
					
						HypergeometricDistribution hd = new HypergeometricDistribution(populationSize, markedItemsCount, sampleSize);
						
						
//						//System.out.println("----------------------");;
//						
//						//System.out.println("sampleSize "+sampleSize);
//						//System.out.println("populationSize "+populationSize);
//						//System.out.println("markedItemsCount "+markedItemsCount);
//						//System.out.println("X is "+x);
						
						//TEMPORARY FIX;
						if(x>sampleSize) x = sampleSize;
						
//						//System.out.println("----------------------");
						
						
//						this.cellTypesPvalues[a] = HG.upperTailProb(x);
						int ix = new Double(x).intValue();
						
						this.cellTypesPvalues[a] = hd.upperCumulativeProbability(ix);
						
						if(this.cellTypesPvalues[a]<1E-16) this.cellTypesPvalues[a] = 1E-16;
						
						this.countCellTypes[a] = 0-Math.log10(this.cellTypesPvalues[a]);
						
					}
					else
					{
						this.countCellTypes[a] = 0;
						this.cellTypesPvalues[a] = 0;
					}
				}
				
//				for(int a=0;a<this.countCellTypes.length;a++) 
//				{
//					double x = this.countCellTypes[a];
//					x = (x/found1.length)*100;
//
//					this.countCellTypes[a] = (new Double(x)).intValue();
//				}
				
				//TF
				

				tempAgData = new String[secondColorsCount.length];
				
				for(int a=0;a<secondColorsCount.length;a++) tempAgData[a] = "";
				
				for(int a=0;a<tffound1.length;a++) 
				{
					if(tffound1[a])
					{
						int z = 0;
						this.secondColorsCount[z]++;
						
//						//System.out.println("May regreet it:"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound2[a])
					{
						int z = 1;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound3[a])
					{
						int z = 2;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound4[a])
					{
						int z = 3;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound5[a])
					{
						int z = 4;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound6[a])
					{
						int z = 5;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound7[a])
					{
						int z = 6;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound8[a])
					{
						int z = 7;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound9[a])
					{
						int z = 8;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound10[a])
					{
						int z = 9;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound11[a])
					{
						int z = 10;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound12[a])
					{
						int z = 11;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound13[a])
					{
						int z = 12;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound14[a])
					{
						int z = 13;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound15[a])
					{
						int z = 14;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound16[a])
					{
						int z = 15;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound17[a])
					{
						int z = 16;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound18[a])
					{
						int z = 17;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound19[a])
					{
						int z = 18;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound20[a])
					{
						int z = 19;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound21[a])
					{
						int z = 20;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound22[a])
					{
						int z = 21;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound23[a])
					{
						int z = 22;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound24[a])
					{
						int z = 23;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound25[a])
					{
						int z = 24;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound26[a])
					{
						int z = 25;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound27[a])
					{
						int z = 26;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound28[a])
					{
						int z = 27;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound29[a])
					{
						int z = 28;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound30[a])
					{
						int z = 29;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound31[a])
					{
						int z = 30;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound32[a])
					{
						int z = 31;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound33[a])
					{
						int z = 32;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound34[a])
					{
						int z = 33;
						this.secondColorsCount[z]++;
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound35[a])
					{
						int z = 34;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}

					if(tffound36[a])
					{
						int z = 35;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound37[a])
					{
						int z = 36;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound38[a])
					{
						int z = 37;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}

					if(tffound39[a])
					{
						int z = 38;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound40[a])
					{
						int z = 39;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound41[a])
					{
						int z = 40;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					

					if(tffound42[a])
					{
						int z = 41;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound43[a])
					{
						int z = 42;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound44[a])
					{
						int z = 43;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}

					if(tffound45[a])
					{
						int z = 44;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
					if(tffound46[a])
					{
						int z = 45;
						this.secondColorsCount[z]++;
						
//						//System.out.println(tempAgData[z].equals(""));
//						//System.out.println("1> "+secondCheckMatrix[a+1].getData()[0][0]);
						
						if(tempAgData[z].equals("")) tempAgData[z] = secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
						else tempAgData[z] += "!"+secondCheckMatrix[a+1].getData()[0][0]+";"+secondCheckMatrix[a+1].getData()[0][1];
					}
				}
				
				String[] tfNames = {
						"Nanog",
						"Sox2",
						"Oct4",
						"Ctcf",
						"E2f1",
						"Esrrb",
						"Klf4",
						"Mycn",
						"Smad1",
						"Stat3",
						"Suz12",
						"Tcfcp21",
						"Zfx",
						"Myc",
						"Dmap1",
						"E2f4",
						"Gcn5",
						"Max",
						"Tip60",
						"Tcf3",
						"Nacc1",
						"Nr0b1",
						"Zfp281",
						"Zfp42",
						"Eed",
						"Phc1",
						"Rnf2",
						"Tbx3",
						"Smad4",
						"Smad5",
						"Smad2",
						"Smad3",
						"Ring1B",
						"Ezh2",
						"Gata2",
						"Meis1",
						"Fli1",
						"Gfi1b",
						
						"Lmo2",
						"Lyl1",
						"Pu1",
						
						"Scl",
						"Runx1",
						"Tcf7",
						
						"Brn2",
						"Erg"
				};
				
				this.agreTFData = "";
				
//				//System.out.println();
				
				for(int a=0;a<tfNames.length;a++)
				{
					if(a!=0) this.agreTFData += "+";
					this.agreTFData += tfNames[a]+":"+tempAgData[a];
				}

				sampleSizes = sampleSizes2;
				
				this.secondColorsCountPerc = new int[this.secondColorsCount.length];
				
				for(int pc=0;pc<sampleSizes.length;pc++)
				{
					double x1 = this.secondColorsCount[pc];
					x1 = (x1/tffound1.length)*100;
					this.secondColorsCountPerc[pc] = (new Double(x1)).intValue();
					
					int sampleSize = sampleSizes[pc];
					
					int x = new Double(this.secondColorsCount[pc]).intValue();
					if(x>0 && sampleSize>0)
					{
						
//						Hypergeometric HG = new Hypergeometric(sampleSize, populationSize, markedItemsCount);
						
//						Hypergeometric HG = new Hypergeometric(sampleSize, populationSize, markedItemsCount) ;
						
						HypergeometricDistribution hd = new HypergeometricDistribution(populationSize, markedItemsCount, sampleSize);
						
						
//						//System.out.println("----------------------");
//						//System.out.println(tfNames[pc]);
//						
//						//System.out.println("sampleSize "+sampleSize);
//						//System.out.println("populationSize "+populationSize);
//						//System.out.println("markedItemsCount "+markedItemsCount);
//						//System.out.println("X is "+x);
						
//						//TEMPORARY FIX;
						if(x>sampleSize) x = sampleSize;
//						
//						//System.out.println("----------------------");
						
//						double a = HG.upperTailProb(x);
						
						double a = hd.upperCumulativeProbability(x);
						
						
						//System.out.println("a is "+a);
						
//						if(a<1E-16) a = 1E-16;
						
						//System.out.println("a after is "+a);
						//System.out.println("Math.log10(a) after is "+Math.log10(a));
						
						this.secondColorsCount[pc] = 0-Math.log10(a);
//						this.secondColorsCount[pc] = a;
					}
					else this.secondColorsCount[pc] = 0;
				}
				
				//stem
				
				for(int a=0;a<setsinsample.length;a++) 
				{
					StemnessSignatures pss = (StemnessSignatures)setsinsample[a].getA();
					
//					int genomeSize;
					
					int inchekmatrixbyspecies = 0;
					
//					if(pss.getSpecies().equals("Homo sapiens")) genomeSize = humanGS;
//					else genomeSize = mouseGS;
					
					for(int p=1;p<checkMatrix.length;p++)
					{
						if(checkMatrix[p].getSpecies().equals(pss.getSpecies()))
						{
							/*boolean addit = false;
							
//							//System.out.println(checkMatrix[p].getData()[0][0]);
							for(int f=1;f<checkMatrix[p].getData().length && !addit;f++)
							{
//								//System.out.println(checkMatrix[p].getData()[f][0]);
								
								if(checkMatrix[p].getData()[f][0].equals("1"))
								{
									addit = true;
									inchekmatrixbyspecies++;
								}
							}*/
							
							inchekmatrixbyspecies++;
							
						}
					}
					
					
					int siz = pss.getProteinInStemnessSignatureses().size();
					
					this.nameOfSetsToUse[a] = pss.getName(); //TODO: ID GOES HERE
					if(pss.getSummary()!=null) this.descriptionOfSetsToUse[a] = pss.getSummary();
					else this.descriptionOfSetsToUse[a] = null;
					this.setSize[a] = ""+siz;
					this.pvalues[a] = -999;
					this.overlaps[a] = "0";
					this.idOfSetsToUse[a] = pss.getId().toString();
					
					try{
						
						/*
						for other repalce siz with all
						when human+muse add both
						TODO: inspiration from here
						inchekmatrixbyspecies = checkMatrix.length-1 in this case
						
						
						*/
						
						
						
//						Hypergeometric HG = new Hypergeometric(siz, populationSize, inchekmatrixbyspecies);
						
						
//						Hypergeometric HG = new Hypergeometric(sampleSize, populationSize, markedItemsCount) ;
						
						HypergeometricDistribution hd = new HypergeometricDistribution(populationSize, inchekmatrixbyspecies, siz);
						
						
//						this.pvalues[a] = HG.upperTailProb(found[a]);
						
						this.pvalues[a] = hd.upperCumulativeProbability(found[a]);
						
//						//System.out.println("THAT IS "+this.pvalues[a]);
						
//						ContingencyTable2x2 tab = new ContingencyTable2x2(new int[][]{{pa,pb},{pc,pd}});
//						FishersExactTest fish = new FishersExactTest(tab);
//						this.pvalues[a] = fish.getOneTailedSP();
						this.overlaps[a] = ""+found[a];
						
						this.overlapsids[a] = "";
						this.overlapsGeneSymbol[a] = "";
						
						
						for(int v=0;v<foundid[a].size();v++)
						{
							if(v>0)
							{
								this.overlapsids[a] += "?";
								this.overlapsGeneSymbol[a] += " ";
							}
							this.overlapsids[a] += foundid[a].get(v).toString();
							this.overlapsGeneSymbol[a] += foundidgs[a].get(v);
						}
						
					} catch(IllegalArgumentException ie)
					{
						this.pvalues[a] = -999;
					}
					
//					this.pvalues[i] = 0;
//					this.overlaps[i] = 0;
				}
				
				//TF
				
				for(int a=0;a<tfsetsinsample.length;a++) 
				{
					TfSet pss = (TfSet)tfsetsinsample[a].getA();
					
//					int genomeSize;
//					
//					if(pss.getSpecies().equals("Homo sapiens")) genomeSize = humanGS;
//					else genomeSize = mouseGS;
					
					int inchekmatrixbyspecies = 0;
					
					for(int p=1;p<secondCheckMatrix.length;p++)
					{
						if(secondCheckMatrix[p].getSpecies().equals(pss.getSpecies()))
						{
							/*boolean addit = false;
							
//							//System.out.println(checkMatrix[p].getData()[0][0]);
							for(int f=1;f<checkMatrix[p].getData().length && !addit;f++)
							{
//								//System.out.println(checkMatrix[p].getData()[f][0]);
								
								if(checkMatrix[p].getData()[f][0].equals("1"))
								{
									addit = true;
									inchekmatrixbyspecies++;
								}
							}*/
							
							inchekmatrixbyspecies++;
							
						}
					}
					
					
					
					
					
					int siz = pss.getProteinInTfs().size();
					
					this.secondNameOfSetsToUse[a] = pss.getName();
					
					if(pss.getSummary()!=null) this.secondDescriptionNameOfSetsToUse[a] = pss.getSummary();
					else this.secondDescriptionNameOfSetsToUse[a] = "";
					
					this.secondSetSize[a] = ""+siz;
					this.secondPvalues[a] = -999;
					this.secondOverlaps[a] = "0";
					this.secondIdOfSetsToUse[a] = pss.getId().toString();
					
			/*		int pa = tffound[a];
					int pb = siz - tffound[a];
//					int pc = (secondCheckMatrix.length-1) - tffound[a];
					int pc = inchekmatrixbyspecies - tffound[a];
//					int pd = genomeSize - siz - genes.length + tffound[a];
					int pd = genomeSize - siz - pc + tffound[a];
//					int pd = genomeSize - siz + found[a] - pc ; //genes not in the set that */
					
					try{
						
						
//						//System.out.println(".................................");
						
//						Hypergeometric HG = new Hypergeometric(sampleSize, populationSize, markedItemsCount) ;
						
//						//System.out.println("siz "+siz);
//						//System.out.println("populationSize "+populationSize);
//						//System.out.println("inchekmatrixbyspecies "+inchekmatrixbyspecies);
//						
//						//System.out.println("tffound[a] "+tffound[a]);
						
//						Hypergeometric HG = new Hypergeometric(siz, populationSize, inchekmatrixbyspecies);
						
						//Hypergeometric HG = new Hypergeometric(sampleSize, populationSize, markedItemsCount) ;
						
						HypergeometricDistribution hd = new HypergeometricDistribution(populationSize, inchekmatrixbyspecies, siz);
						
//						this.secondPvalues[a] = HG.upperTailProb(tffound[a]);
						this.secondPvalues[a] = hd.upperCumulativeProbability(tffound[a]);
						
//						//System.out.println("this.secondPvalues[a] "+this.secondPvalues[a]);
//						
//						//System.out.println("---------------------------------");
						
						this.secondOverlapsids[a] = "";
						this.secondOverlapsGeneSymbol[a] = "";
						
						
						for(int v=0;v<tffoundid[a].size();v++)
						{
							if(v>0)
							{
								this.secondOverlapsids[a] += "?";
								this.secondOverlapsGeneSymbol[a] += " ";
							}
							this.secondOverlapsids[a] += tffoundid[a].get(v).toString();
							this.secondOverlapsGeneSymbol[a] += tffoundGS[a].get(v);
						}
						
//						ContingencyTable2x2 tab = new ContingencyTable2x2(new int[][]{{pa,pb},{pc,pd}});
//						FishersExactTest fish = new FishersExactTest(tab);
//						this.secondPvalues[a] = fish.getOneTailedSP();
						this.secondOverlaps[a] = ""+tffound[a];
					} catch(IllegalArgumentException ie)
					{
						this.secondPvalues[a] = -999;
					}
					
//					this.pvalues[i] = 0;
//					this.overlaps[i] = 0;
				}
				
//				tres[0] = this.nameOfSetsToUse[i];
//				tres[1] = this.setSize[i];
//				tres[2] = this.overlaps[i];
				

				checkMatrix = trimRows(checkMatrix);

				secondCheckMatrix = trimRows(secondCheckMatrix);
				
				checkForMatches(checkMatrix, secondCheckMatrix, matched1, matched2, aliasMatrix, genes);
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		} catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		return new CheckMatrix[][]{checkMatrix,secondCheckMatrix};
	}
	
	
	public String[] getCellTypesOverlapsGeneSymbol() {
		return cellTypesOverlapsGeneSymbol;
	}

	public int[] getColorsCountPerc() {
		return colorsCountPerc;
	}


	public int getNumallmatches() {
		return numallmatches;
	}


	public int getSetNum() {
		return setNum;
	}


	public int getTfNum() {
		return tfNum;
	}


	public String[] getDescriptionOfSetsToUse() {
		return descriptionOfSetsToUse;
	}


	public String[] getSecondDescriptionNameOfSetsToUse() {
		return secondDescriptionNameOfSetsToUse;
	}


	public String[] getIdOfSetsToUse() {
		return idOfSetsToUse;
	}


	public String getAgreCellTypes() {
		return agreCellTypes;
	}


	public String getAgreTFData() {
		return agreTFData;
	}


	public String getAgreSignatureTypes() {
		return agreSignatureTypes;
	}


	protected CheckMatrix[] trimRows(CheckMatrix[] mat)
	{
		ArrayList<CheckMatrix> temp = new ArrayList<CheckMatrix>();
		
		temp.add(mat[0]);
		
		for(int z=1;z<mat.length;z++)
		{
//			checkMatrix[a].getData()[b][0] = ex+prot.getGeneSymbol();
//			checkMatrix[a].getData()[b][1] = prot.getEntrezGeneId();
			
//			//System.out.println(mat[z].getData()[0][0]+":"+mat[z].getData()[0][1]);
			
			boolean add = false;
			for(int y=1;!add && y<mat[z].getData().length;y++)
			{
//				System.out.print(mat[z].getData()[y][0]+" ");
				
				if(mat[z].getData()[y][0].equals("1")) add = true;
				
			}
			
			if(add)
			{
				temp.add(mat[z]);
			}
			
//			//System.out.println();
		}
		
		CheckMatrix[] res = new CheckMatrix[temp.size()];
		
		for(int z=0;z<temp.size();z++)
		{
			res[z] = temp.get(z);
		}

		
//		//System.out.println("----------");
		return res;
	}


	protected void checkForMatches(CheckMatrix[] mat1, CheckMatrix[] mat2, boolean[] matched1, boolean[] matched2, ArrayList<String>[] aliasMatrix, String[] inputgenes)
	{
		
		for(int z=1;z<mat1.length;z++)
		{
			for(int v=0;v<inputgenes.length;v++)
			{
				if(!matched1[v])
				{
					if(mat1[z].getData()[0][0].equalsIgnoreCase(inputgenes[v]) || mat1[z].getData()[0][1].equalsIgnoreCase(inputgenes[v]))
					{
						matched1[v] = true;
					}
					else if(aliasMatrix[v].contains(mat1[z].getData()[0][0].toUpperCase()) || aliasMatrix[v].contains(mat1[z].getData()[0][1].toUpperCase())) matched1[v] = true;
				}
			}
			
			
			
//			//System.out.println(mat[z].getData()[0][0]+":"+mat[z].getData()[0][1]);
		}
		
//		//System.out.println("----------");
		
		for(int z=1;z<mat2.length;z++)
		{
			for(int v=0;v<inputgenes.length;v++)
			{
				if(!matched1[v])
				{
					if(mat2[z].getData()[0][0].equalsIgnoreCase(inputgenes[v]) || mat2[z].getData()[0][1].equalsIgnoreCase(inputgenes[v]))
					{
						matched1[v] = true;
					}
					else if(aliasMatrix[v].contains(mat2[z].getData()[0][0].toUpperCase()) || aliasMatrix[v].contains(mat2[z].getData()[0][1].toUpperCase())) matched1[v] = true;
				}
				
				
				
				
			}
			
//			//System.out.println(mat[z].getData()[0][0]+":"+mat[z].getData()[0][1]);
		}

		ArrayList<String> matches = new ArrayList<String>();
		ArrayList<String> noSetMatches = new ArrayList<String>();
		ArrayList<String> noMatches = new ArrayList<String>();
		
		for(int z=0;z<matched1.length;z++)
		{
			if(matched1[z])
			{
				matches.add(inputgenes[z]);
//				//System.out.println("a> "+inputgenes[z]);
			}
			else
			{
				if(matched2[z])
				{
					noSetMatches.add(inputgenes[z]);
//					//System.out.println("b> "+inputgenes[z]);
					
				}
				else
				{
					noMatches.add(inputgenes[z]);
//					//System.out.println("c> "+inputgenes[z]);
				}
			}
		}
		
		this.matches = new String[matches.size()];
		
		for(int i=0;i<matches.size();i++)
		{
			this.matches[i] = matches.get(i);
//			//System.out.println("1> "+this.matches[i]);
		}
		
		this.noMatches = new String[noMatches.size()];
		
		for(int i=0;i<noMatches.size();i++)
		{
			this.noMatches[i] = noMatches.get(i);
//			//System.out.println("2> "+this.noMatches[i]);
		}
		
		this.noSetMatches = new String[noSetMatches.size()];
		
		for(int i=0;i<noSetMatches.size();i++)
		{
			this.noSetMatches[i] = noSetMatches.get(i);
//			//System.out.println("3> "+this.noSetMatches[i]);
		}
		
	}
	
	
	public String[] getMatches() {
		return matches;
	}


	public String[] getNoMatches() {
		return noMatches;
	}


	public String[] getNoSetMatches() {
		return noSetMatches;
	}


	public String[][][][] getShownames(Session s) {
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString = "select sig from StemnessSignatures as sig order by sig.id";
				
				Query queryObject = s.createQuery(queryString);
				
				List l = queryObject.list();
				
				
				HashMap<String,ArrayList<StemnessSignatures>> temp = new HashMap<String,ArrayList<StemnessSignatures>>();
				
				
				for(int i=0;i<l.size();i++)
				{
					StemnessSignatures sig = (StemnessSignatures)l.get(i);
					
					String type = sig.getType();
					
					
					if(!temp.containsKey(type))
					{
						ArrayList<StemnessSignatures> lis = new ArrayList<StemnessSignatures>();
						lis.add(sig);
						temp.put(type, lis);
					}
					else
					{
						temp.get(type).add(sig);
					}
				}
				
//				String[] keys = temp.keySet().toArray(new String[]{});
				String[] keys = new String[]{
						"TF Target Genes",
						"Expression Profiles",
						"RNAi Screens",
						"Literature Curation",
						"Computationally Derived"
				};
				
				String[][][][] shownames = new String[keys.length][2][][];
				
				
				for(int i=0;i<keys.length;i++)
				{
					shownames[i][0] = new String[][]{{keys[i]}};

					ArrayList<StemnessSignatures> lis = temp.get(keys[i]);
					
					shownames[i][1] = new String[lis.size()+1][3];
					
					int n = i+1;
					
					shownames[i][1][0]= new String[]{"!", ""+n, keys[i]};
					
					for(int y=0;y<lis.size();y++)
					{
						String md = "m";
						
						if(lis.get(y).getSpecies().equals("Homo sapiens")) md = "h";
						
						shownames[i][1][y+1]= new String[]{lis.get(y).getName(), ""+n, lis.get(y).getId().toString(), md};
					}
					
				}
				
				tx.commit();
				
				return shownames;
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return new String[0][0][0][0];
		
		/*
		
		*/
	}

	public void setShownames(String[][][][] shownames) {
	}

	public double[] getColorsCount() {
		return colorsCount;
	}

	public String[] getNameOfSetsToUse() {
		return nameOfSetsToUse;
	}

	public String[] getSetSize() {
		return setSize;
	}

	public String[] getOverlaps() {
		return overlaps;
	}

	public double[] getPvalues() {
		return pvalues;
	}

	public double[] getSecondColorsCount() {
		return secondColorsCount;
	}

	public double[] getSecondPvalues() {
		return secondPvalues;
	}

	public String[] getSecondNameOfSetsToUse() {
		return secondNameOfSetsToUse;
	}

	public String[] getSecondSetSize() {
		return secondSetSize;
	}

	public String[] getSecondOverlaps() {
		return secondOverlaps;
	}
	
	public String[] getOverlapsids() {
		return overlapsids;
	}


	protected String[][][] getSetData(Session s) {
		String[][][] res = new String[2][][];
		
		try {
			
			res[0] = new String[0][];
			res[1] = new String[0][];
			
			Transaction tx = s.beginTransaction();
			try{ //rember it will retunr and Object[] when selectin multiple objects
				
				HashMap<String,ArrayList<String[]>> temp = new HashMap<String,ArrayList<String[]>>();
				
				String queryString = "select sig.id, sig.name, sig.type, sig.species from StemnessSignatures as sig order by sig.id";
				Query queryObject = s.createQuery(queryString);
				
				List l = queryObject.list();
				
				ArrayList<String> tkeys = new ArrayList<String>();
				
				for(int i=0;i<l.size();i++)
				{
					Object[] ob = (Object[])l.get(i);
					
					String id = ((Integer)ob[0]).toString();
					String name = (String)ob[1];
					String type = (String)ob[2];
					String species = (String)ob[3];
					
//					//System.out.println("ROUND AND ROUND "+type+"\t"+name+"\t"+id);
					
					if(temp.containsKey(type))
					{
						temp.get(type).add(new String[]{name, id, species});
						
					}
					else
					{
						ArrayList<String[]> arr = new ArrayList<String[]>();
						arr.add(new String[]{name, id, species});
						temp.put(type, arr);
						tkeys.add(type);
					}

				}
				
//				String[] keys = temp.keySet().toArray(new String[]{});
				

//				String[] keys = new String[]{"TF Target Genes", "Expression Profiles", "RNAi Screens", "Literature Curation", "Computationaly Derived"};
				
				String[][] stemSets = new String[l.size()+tkeys.size()][]; 
				
				
				
//				Arrays.sort(keys);
				
				int y = 0;
				
				for(int i=0;i<tkeys.size();i++)
				{
					String key = tkeys.get(i);
					
					ArrayList<String[]> kj = temp.get(key); //TODO: Order this
					
					kj = complexOrder(kj);
					
					stemSets[y] = new String[]{key, "ref"};
					y++;
					
					for(int z=0;z<kj.size();z++)
					{
//						//System.out.println("This "+key+"\t"+kj.get(z)[0]+"\t"+kj.get(z)[1]);
						stemSets[y] = kj.get(z);
						y++;
					}
				}
				
				res[0] = stemSets;
				
				//----------------------------------------------------
				
				
				
				temp = new HashMap<String,ArrayList<String[]>>();
				
				queryString = "select sig.id, sig.name, sig.type, sig.species from TfSet as sig order by sig.type";
				queryObject = s.createQuery(queryString);
				
				l = queryObject.list();
				
				tkeys = new ArrayList<String>();
				
				for(int i=0;i<l.size();i++)
				{
					Object[] ob = (Object[])l.get(i);
					
					String id = ((Integer)ob[0]).toString();
					String name = (String)ob[1];
					String type = (String)ob[2];
					
					type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
					
					String species = (String)ob[3];
					
//					//System.out.println("ROUND AND ROUND "+type+"\t"+name+"\t"+id);
					
					if(temp.containsKey(type))
					{
						temp.get(type).add(new String[]{name, id, species});
						
					}
					else
					{
						ArrayList<String[]> arr = new ArrayList<String[]>();
						arr.add(new String[]{name, id, species});
						temp.put(type, arr);
						tkeys.add(type);
					}

				}
				
//				String[] keys = temp.keySet().toArray(new String[]{});
				

//				String[] keys = new String[]{"TF Target Genes", "Expression Profiles", "RNAi Screens", "Literature Curation", "Computationaly Derived"};
				
				String[][] tfSets = new String[l.size()+tkeys.size()][]; 
				
				
//				Arrays.sort(keys);
				
				y = 0;
				
				for(int i=0;i<tkeys.size();i++)
				{
					String key = tkeys.get(i);
					
					ArrayList<String[]> kj = temp.get(key);
					
					kj = complexOrder(kj);
					
					tfSets[y] = new String[]{key, "ref"};
					y++;
					
					for(int z=0;z<kj.size();z++)
					{
//						//System.out.println("This "+key+"\t"+kj.get(z)[0]+"\t"+kj.get(z)[1]);
						tfSets[y] = kj.get(z);
						y++;
					}
				}
				

				res[1] = tfSets;
				
				//----------------------------------------------------				
				
				
				tx.commit();
				
			}catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}
	
	
	protected ArrayList<String[]> complexOrder(ArrayList<String[]> kj) {
		
		if(kj.size()==0) return kj;
		else if(kj.size()==1)
		{
			ArrayList<String[]> res = new ArrayList<String[]>();
			
			res.add(new String[]{kj.get(0)[0], kj.get(0)[1]});
			
			return kj;
		}
		
		ArrayList<AuxSorter> hlis = new ArrayList<AuxSorter>();
		ArrayList<AuxSorter> mlis = new ArrayList<AuxSorter>();
		
		for(int i=0;i<kj.size();i++)
		{
			AuxSorter as = new AuxSorter(new String[]{kj.get(i)[0], kj.get(i)[1]}, kj.get(i)[0]);
			
			if(kj.get(i)[2].equals("Homo sapiens")) hlis.add(as);
			else mlis.add(as);
			
//			//System.out.println(kj.get(i)[0]);
//			//System.out.println(kj.get(i)[1]);
//			//System.out.println(kj.get(i)[2]);
//			//System.out.println("------------------------");
		}
		
		AuxSorter[] ahlis = new AuxSorter[hlis.size()];
		
		for(int i=0;i<hlis.size();i++) ahlis[i] = hlis.get(i);

		Arrays.sort(ahlis);
		
		AuxSorter[] amlis = new AuxSorter[mlis.size()];
		
		for(int i=0;i<mlis.size();i++) amlis[i] = mlis.get(i);
		
		Arrays.sort(amlis);
		
		ArrayList<String[]> res = new ArrayList<String[]>();
				
		for(int f=0;f<ahlis.length;f++) res.add((String[])ahlis[f].getA());
		
		for(int f=0;f<amlis.length;f++) res.add((String[])amlis[f].getA());
		
//		Arrays.sort(setsinsample);
		
		return res;
		
	}
	
	
	protected Object[] getSingleSetData(Session s, String tid, boolean ste) {
		
		Integer id;
		String name = null;
		String type = null;
		String description = null;
		String sample = null;
		String platform = null;
		String species = null;
		String source = null;
		String[] pubmedRefs = new String[0];
		String[][] proteins = new String[0][2];
		String stemCellType = null;
		String publicationAbstract = null;
		String derivation = null;
		String title = null;
		String summary = null;
		
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString;
				
				if(ste) queryString = "select sig from StemnessSignatures as sig where sig.id = '"+tid+"'";
				else queryString = "select sig from TfSet as sig where sig.id = '"+tid+"'";
				
				
//				//System.out.println("Make it thunder "+queryString);
				
				Query queryObject = s.createQuery(queryString);
				
				List l = queryObject.list();
				
				
				
				
				if(l.size()>0)
				{
					
					
					
					if(ste)
					{
						StemnessSignatures ob = (StemnessSignatures)l.get(0);
						
						id = ob.getId();
						name = ob.getName();
						type = ob.getType();
						description = ob.getDescription();
						sample = ob.getSample();
						platform = ob.getPlatform();
						species = ob.getSpecies();
						source = ob.getSource();
						
						stemCellType = ob.getCellType();
						publicationAbstract = ob.getPublicationAbstract();
						derivation = ob.getType();
						title = ob.getPublicationTitle();
						summary = ob.getSummary();
						
						Object[] obs = ob.getPubmedRefses().toArray();
						
						pubmedRefs = new String[obs.length];
						
						for(int y=0;y<obs.length;y++)
						{
							PubmedRefs pr = (PubmedRefs)obs[y];
							
							pubmedRefs[y] = ""+pr.getPubmedid();
						}
						
						
						
						obs = ob.getProteinInStemnessSignatureses().toArray();
						
						proteins = new String[obs.length][2];
						
//						//System.out.println(obs.length);
						
						for(int y=0;y<obs.length;y++)
						{
							ProteinInStemnessSignatures pr = (ProteinInStemnessSignatures)obs[y];
							
							ProteinList pl = pr.getProteinList();
							
							if(pl.getGeneSymbol()==null) proteins[y][1] = pl.getEntrezGeneId();
							else proteins[y][1] = pl.getGeneSymbol();
							
							proteins[y][0] = pl.getEntrezGeneId();
							
//							if(pl.getEntrezGeneId().equals("890"))						
//								//System.out.println(pr.getId()+":"+pl.getGeneSymbol()+":"+pl.getEntrezGeneId());
							
						}
					}
					else
					{
						TfSet ob = (TfSet)l.get(0);
						
						id = ob.getId();
						name = ob.getName();
						type = ob.getType();
						description = ob.getDescription();
						sample = ob.getSample();
						platform = ob.getPlatform();
						species = ob.getSpecies();
						source = ob.getSource();
						
//						stemCellType = ob.getCellType();
//						publicationAbstract = ob.getPublicationAbstract();
						derivation = ob.getType();
//						title = ob.getPublicationTitle();
						summary = ob.getSummary();
						
						
						Object[] obs = ob.getTfPubmedRefses().toArray();
						
						pubmedRefs = new String[obs.length];
						
						for(int y=0;y<obs.length;y++)
						{
							TfPubmedRefs pr = (TfPubmedRefs)obs[y];
							
							pubmedRefs[y] = ""+pr.getPubmedid();
						}
						
						
						obs = ob.getProteinInTfs().toArray();
						
						proteins = new String[obs.length][2];
						
						for(int y=0;y<obs.length;y++)
						{
							ProteinInTf pr = (ProteinInTf)obs[y];
							
							ProteinList pl = pr.getProteinList();
							
							if(pl.getGeneSymbol()==null) proteins[y][1] = pl.getEntrezGeneId();
							else proteins[y][1] = pl.getGeneSymbol();
							
							proteins[y][0] = pl.getEntrezGeneId();
						}
					}
					
					
					
				}
				
				
				tx.commit();
				
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		

		
		return new Object[]{name, type, description, sample, platform, species, source, pubmedRefs, proteins, stemCellType, publicationAbstract, derivation, title, summary};
		
		
		/*
		
		*/
	}


	public String[] getSecondOverlapsids() {
		return secondOverlapsids;
	}
	
	protected String[][] getGeneName(Session s, Integer[] ids) {
		
		String[][] res = new String[][]{};
		
		
		try {
			Transaction tx = s.beginTransaction();
			try {
				
				String queryString = "select distinct prot from ProteinList as prot " +
					"where prot.id in (:ids) order by prot.id";
				
				Query queryObject = s.createQuery(queryString).setParameterList("ids", ids);
				
				List l = queryObject.list();
				
				if(l.size()>0)
				{
					res = new String[l.size()][];
					
					for(int y=0;y<l.size();y++)
					{
						ProteinList pl = (ProteinList)l.get(y);
						res[y] = new String[]{pl.getGeneSymbol(),pl.getEntrezGeneId()};
					}
				}
				
				tx.commit();
				
			} catch(RuntimeException re)
			{
				re.printStackTrace();
				tx.rollback();
			}
			
		}	catch(Exception e)
		{e.printStackTrace();}
		finally
		{s.close();}
		
		return res;
	}


	public String[] getSecondIdOfSetsToUse() {
		return secondIdOfSetsToUse;
	}
	public int[] getTrueCountCellTypes() {
		return trueCountCellTypes;
	}

	public double[] getCellTypesPvalues() {
		return cellTypesPvalues;
	}

	public String[] getCellTypesOverlapsids() {
		return cellTypesOverlapsids;
	}

	public int[] getTotalCountCellTypes() {
		return totalCountCellTypes;
	}

	public String[] getOverlapsGeneSymbol() {
		return overlapsGeneSymbol;
	}

	public String[] getSecondOverlapsGeneSymbol() {
		return secondOverlapsGeneSymbol;
	}
	
}
