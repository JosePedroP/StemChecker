function startup()
{
	current = new Array();
	
	empty2 = true;
	empty3 = true;
	empty4 = true;
	
//	alert("wa"); 
	
	var params = document.URL.split("?");
//	alert(params); 
	
	params = params[1];
//	alert(params); 
	
	var p = params.split("=");
	
	var lis = p[1];
	
//	alert(lis); 
	
	
	document.getElementById("sc:hiddenPos").value = lis;

//	alert("did it"); 
	document.getElementById('sc:update1').click();
	
//	document.getElementById("form:hiddenProteinsName").value = lis;
//	
//	document.getElementById('form:update1').click();
	
//	http://localhost:8080/unihi/unihiSearchResult.jsf?prot1=GADD45A&prot2=PARK2&prot3=SNCA
}