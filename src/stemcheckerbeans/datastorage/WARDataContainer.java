package stemcheckerbeans.datastorage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class WARDataContainer extends AbstractDataContainer implements Serializable {

	private static final long serialVersionUID = 6726991297732254144L;

	private String[] names;
	private String[] altnames;
	private String[][][] ids;
	private String[][] proteinTable;
	private String[] hexcolor;
	private String[][] ddata;
	private String[][] description;
	private int[] mouse;
	
	//#FF0000 Target Genes, #0DC4E0 Expression Profiles, #75ad3e RNAi Screens
	
	public WARDataContainer(String[] names, String[] file, String plist, String[] hexcolor, String[] altnames, String description) throws Exception
	{
		this.altnames = altnames;
		
		this.hexcolor = hexcolor;
		
		this.ddata = new String[this.hexcolor.length][];
		
		ArrayList<Integer> tmouse = new ArrayList<Integer>();
		
		for(int i=0;i<this.ddata.length;i++)
		{
			this.ddata[i] = new String[]{names[i],hexcolor[i]};

        	
        	if(names[i].startsWith("Mm"))
        	{
        		tmouse.add(new Integer(i));
        		//System.out.println.println("Mouse "+i);
        	}
        	
		}
		
		this.mouse = new int[tmouse.size()];
		
		for(int i=0;i<tmouse.size();i++)
		{
			this.mouse[i] = tmouse.get(i);
		}
		
		FacesContext facesContext = FacesContext.getCurrentInstance();  
        ExternalContext externalContext = facesContext.getExternalContext();
        
        InputStream ins = externalContext.getResourceAsStream( "/data/"+plist);
        BufferedReader br = new BufferedReader(new InputStreamReader(ins));
        
        ArrayList<String[]> temp = new ArrayList<String[]>();
        
        while(br.ready())
		{
			String line = br.readLine();
			StringTokenizer st = new StringTokenizer(line, "\t");
			
			String[] ldata = new String[st.countTokens()];
			
			int u = 0;
			
			while(st.hasMoreTokens())
			{
				ldata[u] = st.nextToken();
				u++;
			}
			
			temp.add(ldata);
		}
		
		this.proteinTable = new String[temp.size()][];
		
		for(int i=0;i<temp.size();i++)
		{
			this.proteinTable[i] = temp.get(i);
		}
		
		br.close();
        
        this.names = names;
		this.ids = new String[names.length][][];
        
        for(int i=0;i<names.length;i++)
        {
        	System.out.println(file[i]);
        	
        	ins = externalContext.getResourceAsStream( "/data/"+file[i]);
            br = new BufferedReader(new InputStreamReader(ins));
			
			ArrayList<String[]> tempStore = new ArrayList<String[]>();
			
			while(br.ready())
			{
				String line = br.readLine();
				
				if(line.length()>0)
				{
					StringTokenizer st = new StringTokenizer(line, "\t");
					
					String[] ts = new String[st.countTokens()];
					
					int u = 0;
					
					while(st.hasMoreTokens())
					{
						ts[u] = st.nextToken();
						u++;
					}
					
					tempStore.add(ts);
				}
			}
			
			br.close();
			
			ids[i] = new String[tempStore.size()][];
			
			for(int z=0;z<tempStore.size();z++)
			{
				ids[i][z] = tempStore.get(z);
			}
        }
        
        
        ins = externalContext.getResourceAsStream( "/data/"+description);
        br = new BufferedReader(new InputStreamReader(ins));
        
        
        this.description = new String[names.length][7];
        
        int it = 0;
        
        while(br.ready())
		{
        	String title = br.readLine();
        	String descript = br.readLine();
        	String sample = br.readLine();
        	String platform = br.readLine();
        	String numberOfGenes = br.readLine();
        	String pubmedId = br.readLine();
        	String source = br.readLine();
        	
        	this.description[it][0] = title;
        	this.description[it][1] = descript;
        	this.description[it][2] = sample;
        	this.description[it][3] = platform;
        	this.description[it][4] = numberOfGenes;
        	this.description[it][5] = pubmedId;
        	this.description[it][6] = source;
        	
        	it++;
		}
        
        br.close();
         
        
	}
	
	
	
	public WARDataContainer(String[] names, String[] file, String[] hexcolor, String[] altnames, String description) throws Exception
	{
		this.altnames = altnames;
		
		this.hexcolor = hexcolor;
		
		this.ddata = new String[this.hexcolor.length][];
		
		
		ArrayList<Integer> tmouse = new ArrayList<Integer>();
		
		for(int i=0;i<this.ddata.length;i++)
		{
			this.ddata[i] = new String[]{names[i],hexcolor[i]};
			
			if(names[i].startsWith("Mm"))
        	{
        		tmouse.add(new Integer(i));
        		//System.out.println.println("Mouse "+i);
        	}
		}
		
		
		this.mouse = new int[tmouse.size()];
		
		for(int i=0;i<tmouse.size();i++)
		{
			this.mouse[i] = tmouse.get(i);
		}
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance();  
        ExternalContext externalContext = facesContext.getExternalContext();
     
		
		this.proteinTable = null;
		
        this.names = names;
		this.ids = new String[names.length][][];
        
        for(int i=0;i<names.length;i++)
        {
        	InputStream ins = externalContext.getResourceAsStream( "/data/"+file[i]);
        	
        	//System.out.println.println(file[i]);
        	//System.out.println.print("IS NULL? ");
        	//System.out.println.println(ins==null);
        	
        	BufferedReader br = new BufferedReader(new InputStreamReader(ins));
			
			ArrayList<String[]> tempStore = new ArrayList<String[]>();
			
			while(br.ready())
			{
				String line = br.readLine();
				
				if(line.length()>0)
				{
					StringTokenizer st = new StringTokenizer(line, "\t");
					
					String[] ts = new String[st.countTokens()];
					
					int u = 0;
					
					while(st.hasMoreTokens())
					{
						ts[u] = st.nextToken();
						u++;
					}
					
					tempStore.add(ts);
				}
			}
			
			br.close();
			
			ids[i] = new String[tempStore.size()][];
			
			for(int z=0;z<tempStore.size();z++)
			{
				ids[i][z] = tempStore.get(z);
			}
        }
        
        
        InputStream ins = externalContext.getResourceAsStream( "/data/"+description);
        BufferedReader br = new BufferedReader(new InputStreamReader(ins));
        
        
        this.description = new String[names.length][7];
        
        int it = 0;
        
        while(br.ready())
		{
        	String title = br.readLine();
        	String descript = br.readLine();
        	String sample = br.readLine();
        	String platform = br.readLine();
        	String numberOfGenes = br.readLine();
        	String pubmedId = br.readLine();
        	String source = br.readLine();
        	
        	this.description[it][0] = title;
        	this.description[it][1] = descript;
        	this.description[it][2] = sample;
        	this.description[it][3] = platform;
        	this.description[it][4] = numberOfGenes;
        	this.description[it][5] = pubmedId;
        	this.description[it][6] = source;
        	
        	it++;
		}
        
        br.close();
        
	}
	
	
	
	public int getNumberOfSets() {
		return names.length;
	}

	public String getSetName(int i) {
		return names[i];
	}

	public String[][] getSetData(int i) {
		return ids[i];
	}

	public String[][] getProteinTable() {
		return this.proteinTable;
	}

	public String[] getSetNames() {
		return names;
	}

	public String getColor(int i) {
		return this.hexcolor[i];
	}

	public String[] getColors() {
		return this.hexcolor;
	}

	public void setColors(String[] cols) {
		
	}

	public String[][] getDdata() {
		return ddata;
	}

	public void setDdata(String[][] ddata) {
		this.ddata = ddata;
	}

	public String[] getAltnames() {
		return altnames;
	}

	public void setAltnames(String[] altnames) {
	}

	public String[][] getDescription() {
		return description;
	}

	public void setDescription(String[][] description) {
		this.description = description;
	}
	
	public boolean isMouse(int setN){
		boolean res = false;
		
		for(int i=0;!res && i<this.mouse.length;i++)
		{
			res = (this.mouse[i]==setN);
		}
		
		return res;
	}
	
}
