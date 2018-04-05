package stemcheckerbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.hibernate.Session;

@ManagedBean
@RequestScoped
public class OverlapBrowser {
	@ManagedProperty(value="#{sesssionFactoryClass}")
	protected SesssionFactoryClass sesssionFactoryClass;
	
	protected String genes;

	public SesssionFactoryClass getSesssionFactoryClass() {
		return sesssionFactoryClass;
	}

	public void setSesssionFactoryClass(SesssionFactoryClass sesssionFactoryClass) {
		this.sesssionFactoryClass = sesssionFactoryClass;
	}

	public String getGenes() {
		return genes;
	}

	public void setGenes(String genes) {
		this.genes = genes;
		System.out.println();
	}
	
	public String jump(){
		
		System.out.println("---");
		System.out.println(genes);
		System.out.println("+++");
		return "stemoverlap";
	}
	
	public String[][] getData() {
		
		String[][] res = new String[][]{};
		
		if(this.genes!=null && !this.genes.equals(""))
		{
			
			String[] tids = this.genes.split("\\?");
			
			Integer[] ids = new Integer[tids.length];
			
			for(int i=0;i<tids.length;i++)
			{
				Integer ig = -1;
				try{
					Integer igx = new Integer(tids[i]);
					ig = igx;
				} catch(Exception e)
				{e.printStackTrace();}
				
				ids[i] = ig;
				
//				System.out.println(ids[i]);
				
			}
			
			Session s = this.sesssionFactoryClass.getSessionFactory().openSession();
			
			DatabaseExtractor de = new DatabaseExtractor();
			
			res = de.getGeneName(s, ids);
			
		}
		
		return res;
	}
	
	public void setData(String[][] data) {
	}
	
}
