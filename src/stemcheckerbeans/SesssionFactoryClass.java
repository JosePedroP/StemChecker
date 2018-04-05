package stemcheckerbeans;

import java.util.HashMap;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

@ManagedBean(eager=true)
@ApplicationScoped
public class SesssionFactoryClass {
	
	protected SessionFactory sessionFactory = null;
	protected HashMap<String,String> namesToImages;
	
	
	
	public SesssionFactoryClass()
	{
		super();
		
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
//			configuration.buildSessionFactory(serviceRegistry);
			this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch(Exception e)
		{e.printStackTrace();}
		
		String[] images = {
			"s1.png",
			"s2.png",
			"s3.png",
			"s4.png",
			"s5.png",
			"s6.png",
			"s7.png",
			"s8.png",
			"s9.png",
			"s10.png",
			"s11.png",
			"s12.png",
			"s13.png",
			"s14.png",
			"s15.png",
			"s16.png",
			"s17.png",
			"s18.png",
			"s19.png",
			"s20.png",
			"s21.png",
			"s22.png",
			"s23.png",
			"s24.png",
			"s25.png",
			"s26.png",
			"s27.png",
			"s28.png",
			"s29.png",
			"s30.png",
			"s31.png",
			"s32.png",
			"s33.png",
			"s34.png",
			"s35.png",
			"s36.png",
			"s37.png",
			"s38.png",
			"s39.png",
			"s40.png",
			"s41.png",
			"s42.png",
			"s43.png",
			"s44.png",
			"s45.png",
			"s46.png",
			"s47.png",
			"s48.png",
			"s49.png",
			"s50.png",
			"s51.png",
			"s52.png",
			"s53.png",

			"t1.png",
			"t2.png",
			"t3.png",
			"t4.png",
			"t5.png",
			"t6.png",
			"t7.png",
			"t8.png",
			"t9.png",
			"t10.png",
			"t11.png",
			"t12.png",
			"t13.png",
			"t14.png",
			"t15.png",
			"t16.png",
			"t17.png",
			"t18.png",
			"t19.png",
			"t20.png",
			"t21.png",
			"t22.png",
			"t23.png",
			"t24.png",
			"t25.png",
			"t26.png",
			"t27.png",
			"t28.png",
			"t29.png",
			"t30.png",
			"t31.png",
			"t32.png",
			"t33.png",
			"t34.png",
			"t35.png",
			"t36.png",
			"t37.png",
			"t38.png",
			"t39.png",
			"t40.png",
			"t41.png",
			"t42.png",
			"t43.png",
			"t44.png",
			"t45.png",
			"t46.png",
			"t47.png",
			"t48.png",
			"t49.png",
			"t50.png",
			"t51.png",
			"t52.png",
			"t53.png",
			"t54.png",
			"t55.png",
			"t56.png",
			"t57.png",
			"t58.png",
			"t59.png",
			"t60.png",
			"t61.png",
			"t62.png",
			"t63.png",
			"t64.png",
			"t65.png",
			"t66.png",
			"t67.png",
			"t68.png",
			"t69.png",
			"t70.png",
			"t71.png",
			"t72.png",
			"t73.png",
			"t74.png",
			"t75.png",
			"t76.png",
			"t77.png",
			"t78.png",
			"t79.png",
			
			"s64.png",
			
			"s65.png"
		
		};
		
		
		String[] names = {
			"Hs_ESC_OCT4_targets_Boyer",
			"Hs_ESC_NANOG_targets_Boyer",
			"Hs_ESC_SOX2_targets_Boyer",
			"Hs_ESC_Assou",
			"Hs_SC_Palmer",
			"Mm_ESC_Ivanova",
			"Mm_NSC_Ivanova",
			"Mm_HSC_Ivanova",
			"Mm_ESC_Ramalho",
			"Mm_NSC_Ramalho",
			"Mm_HSC_Ramalho",
			"Mm_ESC_Fortunel",
			"Mm_NSC_Fortunel",
			"Mm_ESC_Gaspar",
			"Hs_ESC_Bhattacharya",
			"Hs_MaSC_Pece",
			"Mm_SC_Wong",
			"Hs_ESC_Wong",
			"Mm_SSC_Kokkinaki",
			"Hs_SC_Shats",
			"Hs_iPSC_Shats",
			"Hs_HSC/MSC/NSC_Huang",
			"Hs_HSC_Toren",
			"Hs_ESC/EC_Sperger",
			"Hs_ESC_Chia",
			"Mm_ESC_Ding",
			"Mm_ESC_Hu",
			"Mm_ESC_Wang",
			"KEGG",
			"REACTOME",
			"PluriNetWork",
			"Plurinet",
			"GeneCards",
			"Hs_HSC_Novershtern",
			"Mm_NSC_Parker",
			"Mm_NSC_D'Amour",
			"Hs_EC_Skotheim",
			"HSC-Explorer",
			"Hs_HSC_Huang",
			"Hs_MSC_Huang",
			"Hs_NSC_Huang",
			"Hs_HSC/MSC/NSC/ESC_Huang",
			"Hs_ESC_Sato",
			"Mm_ISC_Munoz",
			"Mm_ESC_Glover",
			"Hs_ESC_Skottman",
			"Mm_ESC_Yang",
			"Mm_HSC_Runx1_Wu",
			"Mm_HSC_Tcf7_Wu",
			"Mm_Sox2_Lodato1",
			"Mm_Sox2_Lodato",
			"Mm_Brn2_Lodato1",
			"Mm_Erg_Wilson",
			
			"Hs_NANOG_Boyer1",
			"Mm_Nanog_Chen",
			"Mm_Nanog_Loh",
			"Mm_Nanog_Marson",
			"Mm_Nanog_Mathur",
			"Mm_Nanog_Cole",
			"Mm_Nanog_Kim1",
			"Hs_SOX2_Boyer1",
			"Mm_Sox2_Chen",
			"Mm_Sox2_Marson",
			"Mm_Sox2_Liu",
			"Mm_Sox2_Kim1",
			"Hs_OCT4_Boyer1",
			"Mm_Oct4_Chen",
			"Mm_Oct4_Loh",
			"Mm_Oct4_Marson",
			"Mm_Oct4_Mathur",
			"Mm_Oct4_Cole",
			"Mm_Oct4_Kim1",
			"Mm_Ctcf_Chen",
			"Mm_E2f1_Chen",
			"Mm_Esrrb_Chen",
			"Mm_Klf4_Chen",
			"Mm_Klf4_Kim1",
			"Mm_Klf4_Liu",
			"Mm_Mycn_Chen",
			"Mm_Smad1_Chen",
			"Mm_Smad1_Fei",
			"Mm_Stat3_Chen",
			"Mm_Stat3_Kidder",
			"Mm_Suz12_Chen",
			"Mm_Suz12_Marson",
			"Mm_Suz12_Boyer2",
			"Hs_SUZ12_Lee",
			"Mm_Suz12_Ku",
			"Mm_Tcfcp2l1_Chen",
			"Mm_Zfx_Chen",
			"Mm_Myc_Chen",
			"Mm_Myc_Kim2",
			"Mm_Myc_Kim1",
			"Mm_Myc_Liu",
			"Mm_Myc_Kidder",
			"Mm_Dmap1_Kim2",
			"Mm_E2f4_Kim2",
			"Hs_E2F4_Boyer1",
			"Mm_Gcn5_Kim2",
			"Mm_Max_Kim2",
			"Mm_Tip60_Kim2",
			"Mm_Tcf3_Marson",
			"Mm_Tcf3_Cole",
			"Mm_Tcf3_Tam",
			"Mm_Nacc1_Kim1",
			"Mm_Nr0b1_Kim1",
			"Mm_Zfp281_Kim1",
			"Mm_Zfp42_Kim1",
			"Mm_Eed_Boyer2",
			"Mm_Phc1_Boyer2",
			"Mm_Rnf2_Boyer2",
			"Mm_Tbx3_Han",
			"Mm_Smad4_Fei",
			"Hs_SMAD4_Kim",
			"Mm_Smad5_Fei",
			"Hs_SMAD2_Kim",
			"Hs_SMAD2_Brown",
			"Hs_SMAD3_Kim",
			"Hs_SMAD3_Brown",
			"Mm_Ring1B_Ku",
			"Mm_Ezh2_Ku",
			"Mm_Gata2_Wilson",
			"Mm_Meis1_Wilson",
			"Mm_Fli1_Wilson",
			"Mm_Gfi1b_Wilson",
			"Mm_Lmo2_Wilson",
			"Mm_Lyl1_Wilson",
			"Mm_Pu1_Wilson",
			"Mm_Scl_Wilson",
			"Mm_Runx1_Wilson",
			"Mm_Runx1_Wu",
			"Mm_Tcf7_Wu",
			
			"Mm_DSC_Noh",
			"Mm_RPC_Fortunel"

			
		};
		
		this.namesToImages = new HashMap<String,String> ();
			
		for(int i=0;i<names.length;i++)
		{
			this.namesToImages.put(names[i], images[i]);
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public HashMap<String, String> getNamesToImages() {
		return namesToImages;
	}
	
}
