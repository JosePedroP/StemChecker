var myRadarChart;
var myRadarChart2;
var myRadarChart3;
var alreadyFound = [];
var spec = "h";
var per1 = false;
var per2 = false;
var per3 = false;
var checked1 = [];
var checked2 = [];

function presearchf()
{
	
	$ = jQuery;
	
	$("#loading-image").show();
	$("#part1").hide();
	$("#part2").hide();
	$("#part3").hide();
	
}

function possearchf()
{
	$ = jQuery;
	$("#loading-image").hide();
}

function presearch()
{

//	alert("PRE");
	$ = jQuery;
	
	var paras = "";
	
	$("input").each(function() {
		var id = $(this).attr("id");
		
		if(id!=null && id!=undefined && id.charAt(0)=='i')
		{
			if($(this)[0].checked)
			{
				var pos = id.slice(1, id.length);
				if(paras=="") paras = pos;
				else paras += "!"+pos;
			}
		}
		
	});

//	alert(paras);
	
	$("#sc\\:settouse").val(paras);

//	alert(paras);
//	alert($("#sc\\:settouse").val);
	
	$("#loading-image").show();
	$("#part1").hide();
	$("#part2").hide();
	$("#part3").hide();
	
}

function presearchfile()
{

//	alert("PRE");
	$ = jQuery;
	
	var paras = "";
	
	$("input").each(function() {
		var id = $(this).attr("id");
		
		if(id!=null && id!=undefined && id.charAt(0)=='i')
		{
			if($(this)[0].checked)
			{
				var pos = id.slice(1, id.length);
				if(paras=="") paras = pos;
				else paras += "!"+pos;
			}
		}
		
	});

//	alert(paras);
	
	$("#sc\\:settouse").val(paras);
//
////	alert(paras);
////	alert($("#sc\\:settouse").val);
//	
//	$("#loading-image").show();
//	$("#part1").hide();
//	$("#part2").hide();
//	$("#part3").hide();
	
}

function presearchfile2()
{
	$("#loading-image").show();
	$("#part1").hide();
	$("#part2").hide();
	$("#part3").hide();
	
}

function postsearch()
{
	checked1 = [];
	checked2 = [];
	
	$ = jQuery;

	$("#loading-image").hide();
	
	//sc:printb
	
	$("sc:printb").hide();
	
	var tab= document.getElementById('checkerboard');
	
	sorttable.makeSortable(tab);
		
	tab= document.getElementById('checkerboard2');
	
	sorttable.makeSortable(tab);
	
	tab = document.getElementById('dataT1');
	
	sorttable.makeSortable(tab);
	
	tab = document.getElementById('dataT2');
	
	sorttable.makeSortable(tab);
	
	tab = document.getElementById('dataT3');
	
	sorttable.makeSortable(tab);
	
	radar3();
}

function preprinttable()
{
	
	var firstline = "";
	var resttline = "";
	var tab= document.getElementById('checkerboard');
	
//	alert("F");
	
	var params = document.URL.split("/");
	
	var root = "";
	
	for(var i=0;i<params.length-1;i++)
	{
		if(i>0) root += "/"+params[i];
		else root = params[i];
	}
	
//	alert(root);
	
	for (var i = 0, row; row = tab.rows[i]; i++)
	{
		if(i==0)
		{
			for (var j = 1, col; col = row.cells[j]; j++) {
				var w1 = col.innerHTML.split("<span>");
				var w2 = w1[1].split("</span>");
				
				var wz = w2[0].split("./");
				
				var ws = root+"/"+wz[1].split(".png")[0]+".png";
				
//				alert(w2[0]);
//				alert(ws);
				var w3 = w2[1].split("color:")[4].split(";")[0];
//				alert(w3);

				if(firstline=="") firstline = ws+w3;
				else firstline += ";"+ws+w3;
			}
		}
		else
		{
			for (var j = 0, col; col = row.cells[j]; j++) {
				
				if(j==0)
				{
//					var w1 = col.innerHTML.split("</a> ")[1].split(" <a")[0];
					
					var w1 = col.innerHTML.split("</a> ")[1].split(" <a");
					
					var w2 = w1[0];
					

					var w3 = w1[1].split(">(")[1].split(")<")[0];
					
//					alert(w1[0]);
//					alert(w1[1]);
//					alert(w3);
					if(resttline!="") resttline += "!";
//					resttline += w1;
					resttline += w3+";"+w2;
				}
				else
				{
					var w1 = col.innerHTML;
//					alert(w1);
					resttline += ";"+w1;
				}
				
			}
		}
		
		
	}
	
	var finaltable = firstline+"!"+resttline;
	
//	alert(firstline);
//	alert("s");
//	alert(resttline);
//	alert("e");
//	alert(finaltable);
	
	document.getElementById("sc:toPDF").value = finaltable;
	
//	alert("done");
	
//	alert(document.getElementById("sc:toPDF").value);
}


function preprinttable2()
{
	
	var firstline = "";
	var resttline = "";
	var tab= document.getElementById('checkerboard2');
	
//	alert("F");
	
	var params = document.URL.split("/");
	
	var root = "";
	
	for(var i=0;i<params.length-1;i++)
	{
		if(i>0) root += "/"+params[i];
		else root = params[i];
	}
	
//	alert(root);
	
	for (var i = 0, row; row = tab.rows[i]; i++)
	{
		if(i==0)
		{
			for (var j = 1, col; col = row.cells[j]; j++) {
				var w1 = col.innerHTML.split("<span>");
				var w2 = w1[1].split("</span>");
				
				var wz = w2[0].split("./");
				
				var ws = root+"/"+wz[1].split(".png")[0]+".png";
				
//				alert(w2[0]);
//				alert(ws);
				var w3 = w2[1].split("color:")[4].split(";")[0];
//				alert(w3);

				if(firstline=="") firstline = ws+w3;
				else firstline += ";"+ws+w3;
			}
		}
		else
		{
			for (var j = 0, col; col = row.cells[j]; j++) {
				
				if(j==0)
				{
//					var w1 = col.innerHTML.split("</a> ")[1].split(" <a")[0];
					
					var w1 = col.innerHTML.split("</a> ")[1].split(" <a");
					
					var w2 = w1[0];
					

					var w3 = w1[1].split(">(")[1].split(")<")[0];
					
//					alert(w1[0]);
//					alert(w1[1]);
//					alert(w3);
					if(resttline!="") resttline += "!";
//					resttline += w1;
					resttline += w3+";"+w2;
				}
				else
				{
					var w1 = col.innerHTML;
//					alert(w1);
					resttline += ";"+w1;
				}
				
			}
		}
		
		
	}
	
	var finaltable = firstline+"!"+resttline;
	
//	alert(firstline);
//	alert("s");
//	alert(resttline);
//	alert("e");
//	alert(finaltable);
	
	document.getElementById("sc:toPDF").value = finaltable;
	
//	alert("done");
	
//	alert(document.getElementById("sc:toPDF").value);
}


function removeTablePart(id)
{
//	alert(id);
	
	$ = jQuery;
	$("."+id).remove();
}

function doPrint()
{
	document.getElementById('sc:printb').click();
}

function doPrint2()
{
	document.getElementById('sc:printb2').click();
}

function radar()
{
	$ = jQuery;
	var ctx = $("#myChart").get(0).getContext("2d");
	

//	alert(document.getElementById("sc:agrSig").value.split("!"));
	
	
	var rbg = document.getElementById("sc:rbg").value.split("!");

//	alert("TF Target Genes: "+rbg[0]+" Expression Profiles: "+rbg[1]+" RNAi Screens: "+rbg[2]+" Literature Curation: "+rbg[3]+" Computationally Derived: "+rbg[4]);
	
	var data = {
		    labels: ["TF Target Genes", "Expression Profiles", "RNAi Screens", "Literature Curation", "Computationally Derived"],
		    datasets: [
		        {
		            label: "",
		            fillColor: "rgba(0,0,128,0.2)",
		            strokeColor: "rgba(0,0,128,1)",
		            pointColor: "rgba(0,0,128,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: [rbg[0], rbg[1], rbg[2], rbg[3], rbg[4]]
		        }
		    ]
		};

//	myRadarChart = new Chart(ctx).Radar(data, {responsive: true, scaleOverride: true, scaleSteps: 10, scaleStepWidth: 10, scaleStartValue: 0, angleLineColor: "rgba(0,0,0,.3)"});
	myRadarChart = new Chart(ctx).Radar(data, {responsive: true, angleLineColor: "rgba(0,0,0,.3)"});
//	radar2();
}


function radarp()
{
	$ = jQuery;
	var ctx = $("#myChart").get(0).getContext("2d");
	

//	alert(document.getElementById("sc:agrSig").value.split("!"));
	
	
	var rbg = document.getElementById("sc:rbg4").value.split("!");

	//"TF Target Genes", "Expression Profiles", "RNAi Screens", "Literature Curation", "Computationally Derived"
	//[rbg[0], rbg[1], rbg[2], rbg[3], rbg[4]]
	
	
	var data = {
		    labels: ["TF Target Genes", "Expression Profiles", "RNAi Screens", "Literature Curation", "Computationally Derived"],
		    datasets: [
		        {
		            label: "",
		            fillColor: "rgba(0,0,128,0.2)",
		            strokeColor: "rgba(0,0,128,1)",
		            pointColor: "rgba(0,0,128,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: [rbg[0], rbg[1], rbg[2], rbg[3], rbg[4]]
		        }
		    ]
		};

	myRadarChart = new Chart(ctx).Radar(data, {responsive: true, scaleOverride: true, scaleSteps: 10, scaleStepWidth: 10, scaleStartValue: 0, angleLineColor: "rgba(0,0,0,.3)"});
//	myRadarChart = new Chart(ctx).Radar(data, {responsive: true, angleLineColor: "rgba(0,0,0,.3)"});
//	radar2();
}

function radar2()
{
	$ = jQuery;
	var ctx = $("#myChart2").get(0).getContext("2d");
	
	var rbg = document.getElementById("sc:rbg2").value.split("!");

	/*
	 				
	 				"Ctcf", "Dmap1", "E2f1", "E2f4", "Eed", "Esrrb", "Ezh2 "Gcn5", "Klf4", "Max", "Myc", "Mycn", "Nacc1", "Nr0b1", "Oct4", "Phc1", 
	 				"Ring1B", "Rnf2", "Smad1", "Smad2", "Smad3", "Smad4", "Smad5", "Sox2", "Stat3", "Suz12", "Tbx3", "Tcf3", "Tcfcp21", "Tip60", 
	 				"Zfp42", "Zfp281", "Zfx", Nanog",
	 				
					 "Nanog" 0 x
		             "Sox2 1",1 x
		             "Oct4 2",2 x
		             "Ctcf",3 x
		             "E2f1",4 x
		             "Esrrb",5 x
		             "Klf4",6 x
		             "Mycn",7 x
		             "Smad1",8 x
		             "Stat3",9 x
		             "Suz12",10 x
		             "Tcfcp21",11 x
		             "Zfx",12 x
		             "Myc",13 x
		             "Dmap1",14 x
		             "E2f4",15 x
		             "Gcn5",16 x
		             "Max",17 x
		             "Tip60",18 x
		             "Tcf3",19 x
		             "Nacc1",20 x
		             "Nr0b1",21 x
		             "Zfp281",22 x
		             "Zfp42",23 x
		             "Eed",24 x
		             "Phc1",25 x
		             "Rnf2",26 x
		             "Tbx3",27 x
		             "Smad4",28 x
		             "Smad5",29 x
		             "Smad2",30 x
		             "Smad3",31 x
		             "Ring1B",32 x
		             "Ezh2"33 x
	
					
	
	*/
	
	var data = {
		    labels: [
		             "Nanog",
		             "Oct4",
		             "Sox2",
		             "Ctcf",
		             "Dmap1", 
		             "E2f1",
		             "E2f4",
		             "Eed",
		             "Esrrb",
		             "Ezh2",
		             "Gcn5",
		             "Klf4",
		             "Max",
		             "Myc",
		             "Mycn",
		             "Nacc1",
		             "Nr0b1",
		             "Phc1",
		             "Ring1B",
		             "Rnf2",
		             "Smad1",
		             "Smad2",
		             "Smad3",
		             "Smad4",
		             "Smad5",
		             "Stat3",
		             "Suz12",
		             "Tbx3",
		             "Tcf3",
		             "Tcfcp21",
		             "Tip60",
		             "Zfp42",
		             "Zfp281",
		             "Zfx",
		             "Gata2",
		             "Meis1",
		             "Fli1",
		             "Gfi1b",
		             "Lmo2",
		             "Lyl1",
		             "Pu1",
		             "Scl",
		             "Runx1",
		             "Tcf7"
		             ],
		    datasets: [
		        {
		            label: "",
		            fillColor: "rgba(255,0,0,0.2)",
		            strokeColor: "rgba(255,0,0,1)",
		            pointColor: "rgba(255,0,0,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: [
		                   rbg[0],
		                   rbg[2],
		                   rbg[1],
		                   rbg[3],
		                   rbg[14],
		                   rbg[4],
		                   rbg[15],
		                   rbg[24],
		                   rbg[5],
		                   rbg[33],
		                   rbg[16],
		                   rbg[6],
		                   rbg[17],
		                   rbg[13],
		                   rbg[7],
		                   rbg[20],
		                   rbg[21],
		                   rbg[25],
		                   rbg[32],
		                   rbg[26],
		                   rbg[8],
		                   rbg[30],
		                   rbg[31],
		                   rbg[28],
		                   rbg[29],
		                   rbg[9],
		                   rbg[10],
		                   rbg[27],
		                   rbg[19],
		                   rbg[11],
		                   rbg[18],
		                   rbg[23],
		                   rbg[22],
		                   rbg[12],
		                   rbg[34],
		                   rbg[35],
		                   rbg[36],
		                   rbg[37],
		                   rbg[38],
		                   rbg[39],
		                   rbg[40],
		                   rbg[41],
		                   rbg[42],
		                   rbg[43]
		               ]
		        }
		    ]
		};
	
//	myRadarChart2 = new Chart(ctx).Radar(data, {responsive: true, scaleOverride: true, scaleSteps: 10, scaleStepWidth: 10, scaleStartValue: 0, angleLineColor: "rgba(0,0,0,.3)"});
	myRadarChart2 = new Chart(ctx).Radar(data, {responsive: true, angleLineColor: "rgba(0,0,0,.3)"});
	
//	radar3();
}

function radar2p()
{
	$ = jQuery;
	var ctx = $("#myChart2").get(0).getContext("2d");
	
	var rbg = document.getElementById("sc:rbg5").value.split("!");

	var data = {
		    labels: [
		             "Nanog",
		             "Oct4",
		             "Sox2",
		             "Ctcf",
		             "Dmap1", 
		             "E2f1",
		             "E2f4",
		             "Eed",
		             "Esrrb",
		             "Ezh2",
		             "Gcn5",
		             "Klf4",
		             "Max",
		             "Myc",
		             "Mycn",
		             "Nacc1",
		             "Nr0b1",
		             "Phc1",
		             "Ring1B",
		             "Rnf2",
		             "Smad1",
		             "Smad2",
		             "Smad3",
		             "Smad4",
		             "Smad5",
		             "Stat3",
		             "Suz12",
		             "Tbx3",
		             "Tcf3",
		             "Tcfcp21",
		             "Tip60",
		             "Zfp42",
		             "Zfp281",
		             "Zfx",
		             "Gata2",
		             "Meis1",
		             "Fli1",
		             "Gfi1b",
		             "Lmo2",
		             "Lyl1",
		             "Pu1",
		             "Scl",
		             "Runx1",
		             "Tcf7"
		             ],
		    datasets: [
		        {
		            label: "",
		            fillColor: "rgba(255,0,0,0.2)",
		            strokeColor: "rgba(255,0,0,1)",
		            pointColor: "rgba(255,0,0,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: [
		                   rbg[0],
		                   rbg[2],
		                   rbg[1],
		                   rbg[3],
		                   rbg[14],
		                   rbg[4],
		                   rbg[15],
		                   rbg[24],
		                   rbg[5],
		                   rbg[33],
		                   rbg[16],
		                   rbg[6],
		                   rbg[17],
		                   rbg[13],
		                   rbg[7],
		                   rbg[20],
		                   rbg[21],
		                   rbg[25],
		                   rbg[32],
		                   rbg[26],
		                   rbg[8],
		                   rbg[30],
		                   rbg[31],
		                   rbg[28],
		                   rbg[29],
		                   rbg[9],
		                   rbg[10],
		                   rbg[27],
		                   rbg[19],
		                   rbg[11],
		                   rbg[18],
		                   rbg[23],
		                   rbg[22],
		                   rbg[12],
		                   rbg[34],
		                   rbg[35],
		                   rbg[36],
		                   rbg[37],
		                   rbg[38],
		                   rbg[39],
		                   rbg[40],
		                   rbg[41],
		                   rbg[42],
		                   rbg[43]
		               ]
		        }
		    ]
		};
	
	myRadarChart2 = new Chart(ctx).Radar(data, {responsive: true, scaleOverride: true, scaleSteps: 10, scaleStepWidth: 10, scaleStartValue: 0, angleLineColor: "rgba(0,0,0,.3)"});
//	myRadarChart2 = new Chart(ctx).Radar(data, {responsive: true, angleLineColor: "rgba(0,0,0,.3)"});
	
//	radar3();
}

function radar3()
{
	$ = jQuery;
	var ctx = $("#myChart3").get(0).getContext("2d");

	var rbg = document.getElementById("sc:rbg3").value.split("!");
	
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
	9 ESC/EC -> ESC and EC
	 */
	
	
	var data = {
			labels: [
		             "Embryonic Stem cells",
		             "Neural Stem cells",
		             "Hematopoietic Stem cells",
		             "Mammary Stem cells",
		             "Spermatogonial Stem cells",
		             "Intestinal stem cells",
		             "Induced pluripotent Stem cells",
		             "Mesenchymal Stem cells",
		             "Embryonal carcinoma"
		             ],
		    datasets: [
		        {
		            label: "",
		            fillColor: "rgba(0,128,0,0.2)",
		            strokeColor: "rgba(0,128,0,1)",
		            pointColor: "rgba(0,128,0,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: [
		                   rbg[0],
		                   rbg[2],
		                   rbg[3],
		                   rbg[4],
		                   rbg[5],
		                   rbg[6],
		                   rbg[7],
		                   rbg[8],
		                   rbg[9]
		               ]
		        }
		    ]
		};
	
	
//	myRadarChart3 = new Chart(ctx).Radar(data, {responsive: true, scaleOverride: true, scaleSteps: 10, scaleStepWidth: 10, scaleStartValue: 0, angleLineColor: "rgba(0,0,0,.3)"});
	myRadarChart3 = new Chart(ctx).Radar(data, {responsive: true, angleLineColor: "rgba(0,0,0,.3)"});
	
}

function radar3p()
{
	$ = jQuery;
	var ctx = $("#myChart3").get(0).getContext("2d");

	var rbg = document.getElementById("sc:rbg6").value.split("!");
	
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
	9 ESC/EC -> ESC and EC
	 */
	
	
	var data = {
			labels: [
		             "Embryonic Stem cells",
		             "Neural Stem cells",
		             "Hematopoietic Stem cells",
		             "Mammary Stem cells",
		             "Spermatogonial Stem cells",
		             "Intestinal stem cells",
		             "Induced pluripotent Stem cells",
		             "Mesenchymal Stem cells",
		             "Embryonal carcinoma"
		             ],
		    datasets: [
		        {
		            label: "",
		            fillColor: "rgba(0,128,0,0.2)",
		            strokeColor: "rgba(0,128,0,1)",
		            pointColor: "rgba(0,128,0,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: [
		                   rbg[0],
		                   rbg[2],
		                   rbg[3],
		                   rbg[4],
		                   rbg[5],
		                   rbg[6],
		                   rbg[7],
		                   rbg[8],
		                   rbg[9]
		               ]
		        }
		    ]
		};
	
	
	myRadarChart3 = new Chart(ctx).Radar(data, {responsive: true, scaleOverride: true, scaleSteps: 10, scaleStepWidth: 10, scaleStartValue: 0, angleLineColor: "rgba(0,0,0,.3)"});
//	myRadarChart3 = new Chart(ctx).Radar(data, {responsive: true, angleLineColor: "rgba(0,0,0,.3)"});
	
}


function doughnut()
{
	$ = jQuery;
	var ctx = $("#myChart3").get(0).getContext("2d");
	ctx.canvas.width = 200;
	ctx.canvas.height = 200;

	/*
	0 ESC
	1 SC
	2 NSC
	3 HSC //Green
	4 MaSC
	5 SSC
	6 ISC
	7 IPSC
	8 HSC/MSC/NSC
	9 ESC/EC -> ESC and EC
	10 PSC
	 */
	
	alert(document.getElementById("sc:rbg3").value);
	
	/*var rbg = document.getElementById("sc:rbg3").value.split("!");
	

	
	var data = [
	            {
	            	value: parseInt(rbg[0]),
	                color:"#F7464A",
	                highlight: "#FF5A5E",
	                label: "ESC"
	            },
	            {
	                value: parseInt(rbg[1]),
	                color: "#46BFBD",
	                highlight: "#5AD3D1",
	                label: "SC"
	            },
	            {
	                value: parseInt(rbg[2]),
	                color: "#FDB45C",
	                highlight: "#FFC870",
	                label: "NSC"
	            },
	            {
	                value: parseInt(rbg[3]),
	                color: "#00FF00",
	                highlight: "#8EFF8E",
	                label: "HSC"
	            },
	            {
	                value: parseInt(rbg[4]),
	                color: "#041DFD",
	                highlight: "#8B97FF",
	                label: "MaSC"
	            },
	            {
	                value: parseInt(rbg[5]),
	                color: "#FD08DD",
	                highlight: "#FF6EEB",
	                label: "SSC"
	            },
	            {
	                value: parseInt(rbg[6]),
	                color: "#B26D05",
	                highlight: "#EBB86C",
	                label: "ISC"
	            },
	            {
	                value: parseInt(rbg[7]),
	                color: "#C1BDB6",
	                highlight: "#E9E4DC",
	                label: "IPSC"
	            },
	            {
	                value: parseInt(rbg[8]),
	                color: "#FF8D8D",
	                highlight: "#FFCBCB",
	                label: "HSC/MSC/NSC"
	            },
	            {
	                value: parseInt(rbg[9]),
	                color: "#330033",
	                highlight: "#813281",
	                label: "ESC/EC"
	            },
	            {
	                value: parseInt(rbg[10]),
	                color: "#FF0000",
	                highlight: "#FF2121",
	                label: "PSC"
	            }
	          ];
	
	
	//Original
//	myDoughnut = new Chart(ctx).Doughnut(data, {
//	    animateScale: true,
//	    legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>",
//	    
//	});
	
	myDoughnut = new Chart(ctx).Doughnut(data, {
	    onAnimationComplete: function() {
	    	ctx.fillStyle = 'black';
//	        ctx.fillText(data[1].value + "%", 100 - 20, 100, 200);
	        ctx.fillText("Cell Types", 100 - 20, 100, 200);
	    },
	    showTooltips: true
	});
	
	
	ctx.canvas.onmouseleave = function(evt){
		ctx.fillStyle = 'black';
		ctx.font = '10px sans-serif';
		ctx.textAlign = 'start';
		ctx.fillText("Cell Types", 100 - 20, 100, 200);
//	    var activePoints = myDoughnutChart.getSegmentsAtEvent(evt);
	    // => activePoints is an array of segments on the canvas that are at the same position as the click event.
	};
	
//	myDoughnut = new Chart(ctx).Doughnut(data,{
//	     animation:true,
//	     responsive: true,
//	     showTooltips: true,
//	     percentageInnerCutout : 70,
//	     segmentShowStroke : false,
//	     onAnimationComplete: function() {
//
//	     var canvasWidthvar = $('#chart-area').width();
//	     var canvasHeight = $('#chart-area').height();
//	     //this constant base on canvasHeight / 2.8em
//	     var constant = 114;
//	     var fontsize = (canvasHeight/constant).toFixed(2);
//	     ctx.font=fontsize +"em Verdana";
//	     ctx.textBaseline="middle"; 
//	     var total = 0;
//	     $.each(data,function() {
//	       total += parseInt(this.value,10);
//	   });
//	  var tpercentage = ((data[0].value/total)*100).toFixed(2)+"%";
//	  var textWidth = ctx.measureText(tpercentage).width;
//
//	   var txtPosx = Math.round((canvasWidthvar - textWidth)/2);
//	    ctx.fillText(tpercentage, txtPosx, canvasHeight/2);
//	  }
//	 });
//	
		
//	document.getElementById('leg').innerHTML=myDoughnut.generateLegend();
	
//	leg
//	
//	alert(myDoughnut.generateLegend());
		//new Chart(ctx).Radar(data, {responsive: true, scaleOverride: true, scaleSteps: 10, scaleStepWidth: 10, scaleStartValue: 0, angleLineColor: "rgba(0,0,0,.3)"});
	*/
}

function checkClass(chkclass)
{
	$ = jQuery;
//	alert(chkclass+":"+$("#"+chkclass).is(':checked'));
	
	var b = $("#"+chkclass).is(':checked');
	
//	$("."+chkclass).attr('checked', b);
	
	if(spec=="a")
	{
		$(".m"+chkclass).attr('checked', b);
		$(".h"+chkclass).attr('checked', b);
	}
	else if(spec=="m")
	{
		$(".m"+chkclass).attr('checked', b);
	}
	else if(spec=="h")
	{
		$(".h"+chkclass).attr('checked', b);
	}
	
}

function downloadgraph()
{
	$ = jQuery;
	var url = myRadarChart.toBase64Image().replace(/^data:image\/[^;]/, 'data:application/octet-stream');
	$("#downitgoes").attr("href",url);
//	$("#downitgoes").click();
	document.getElementById('downitgoes').click();

}

function downloadgraph2()
{
//	var url = myRadarChart2.toBase64Image().replace(/^data:image\/[^;]/, 'data:application/octet-stream');
//	window.open(url);
	
	$ = jQuery;
	var url = myRadarChart2.toBase64Image().replace(/^data:image\/[^;]/, 'data:application/octet-stream');
	$("#downitgoes").attr("href",url);
//	$("#downitgoes").click();
	document.getElementById('downitgoes').click();
}

function downloadgraph3()
{
//	var url = myRadarChart2.toBase64Image().replace(/^data:image\/[^;]/, 'data:application/octet-stream');
//	window.open(url);
	
	$ = jQuery;
	var url = myRadarChart3.toBase64Image().replace(/^data:image\/[^;]/, 'data:application/octet-stream');
	
//	alert(url);
	$("#downitgoes").attr("href",url);
//	$("#downitgoes").click();
	document.getElementById('downitgoes').click();
}

function disalbeIfEmpty()
{
	document.getElementById('sna').disabled = (document.getElementById('sc:searchText').value=="");
	
	if(!document.getElementById('sna').disabled) document.getElementById("sna").style.color = "red";
	else document.getElementById("sna").style.color = "";
}

function enableIt()
{
	document.getElementById('sna').disabled = false;
	document.getElementById("sna").style.color = "red";
}

function genesPresent(gen)
{
//	alert(gen);
	document.getElementById('sc:over').value = gen;
	
	document.getElementById('sc:overlink').click();
}

function diso1()
{
//	alert(document.getElementById("sc:ord1").checked);
	
	if(document.getElementById("sc:ord3").checked)
	{
		document.getElementById("sc:ord1").checked = false;
		document.getElementById('sc:ord1').disabled = true;
	}
	else document.getElementById('sc:ord1').disabled = false;
}

function deiso3()
{
	if(document.getElementById("sc:selct:2").checked)
	{
		document.getElementById('sc:ord3').disabled = false;
		setAll()
	}
	else
	{
		document.getElementById("sc:ord3").checked = false;
		document.getElementById('sc:ord3').disabled = true;
		document.getElementById('sc:ord1').disabled = false;
		if(document.getElementById("sc:selct:0").checked) setHuman();
		else setMouse();
	}
}

function diso3()
{
	document.getElementById("sc:ord3").checked = false;
	document.getElementById('sc:ord3').disabled = true;
}

function eiso3()
{
	document.getElementById('sc:ord3').disabled = false;
}

function setHuman()
{
	spec = "h";
	
	$ = jQuery;
//	alert(chkclass+":"+$("#"+chkclass).is(':checked'));
	
	
//	var b = $("#chk1").is(':checked');
//	
//	alert(b);
//	
//	if(b) alert("strong better");
//	else alert("faster harder");
	
	$(".hchk1").removeAttr('disabled');
	$(".hchk2").removeAttr('disabled');
	$(".hchk3").removeAttr('disabled');
	$(".hchk4").removeAttr('disabled');
	$(".hchk5").removeAttr('disabled');

	if($("#chk1").is(':checked')) $(".hchk1").attr('checked', 'checked');
	if($("#chk2").is(':checked')) $(".hchk2").attr('checked', 'checked');
	if($("#chk3").is(':checked')) $(".hchk3").attr('checked', 'checked');
	if($("#chk4").is(':checked')) $(".hchk4").attr('checked', 'checked');
	if($("#chk5").is(':checked')) $(".hchk5").attr('checked', 'checked');
	
	$(".mchk1").attr('disabled', 'disabled');
	$(".mchk2").attr('disabled', 'disabled');
	$(".mchk3").attr('disabled', 'disabled');
	$(".mchk4").attr('disabled', 'disabled');
	$(".mchk5").attr('disabled', 'disabled');
	
	$(".mchk1").removeAttr('checked');
	$(".mchk2").removeAttr('checked');
	$(".mchk3").removeAttr('checked');
	$(".mchk4").removeAttr('checked');
	$(".mchk5").removeAttr('checked');
}

function setMouse()
{
	$ = jQuery;
	spec = "m";

	$(".hchk1").attr('disabled', 'disabled');
	$(".hchk2").attr('disabled', 'disabled');
	$(".hchk3").attr('disabled', 'disabled');
	$(".hchk4").attr('disabled', 'disabled');
	$(".hchk5").attr('disabled', 'disabled');

	if($("#chk1").is(':checked')) $(".mchk1").attr('checked', 'checked');
	if($("#chk2").is(':checked')) $(".mchk2").attr('checked', 'checked');
	if($("#chk3").is(':checked')) $(".mchk3").attr('checked', 'checked');
	if($("#chk4").is(':checked')) $(".mchk4").attr('checked', 'checked');
	if($("#chk5").is(':checked')) $(".mchk5").attr('checked', 'checked');
	
	$(".mchk1").removeAttr('disabled');
	$(".mchk2").removeAttr('disabled');
	$(".mchk3").removeAttr('disabled');
	$(".mchk4").removeAttr('disabled');
	$(".mchk5").removeAttr('disabled');
	
	$(".hchk1").removeAttr('checked');
	$(".hchk2").removeAttr('checked');
	$(".hchk3").removeAttr('checked');
	$(".hchk4").removeAttr('checked');
	$(".hchk5").removeAttr('checked');
}

function setAll()
{
	$ = jQuery;
	spec = "a";

	$(".hchk1").removeAttr('disabled');
	$(".hchk2").removeAttr('disabled');
	$(".hchk3").removeAttr('disabled');
	$(".hchk4").removeAttr('disabled');
	$(".hchk5").removeAttr('disabled');

	if($("#chk1").is(':checked'))
	{
		$(".hchk1").attr('checked', 'checked');
		$(".mchk1").attr('checked', 'checked');
	}
	if($("#chk2").is(':checked'))
	{
		$(".hchk2").attr('checked', 'checked');
		$(".mchk2").attr('checked', 'checked');
	}
	if($("#chk3").is(':checked'))
	{
		$(".hchk3").attr('checked', 'checked');
		$(".mchk3").attr('checked', 'checked');
	}
	if($("#chk4").is(':checked'))
	{
		$(".hchk4").attr('checked', 'checked');
		$(".mchk4").attr('checked', 'checked');
	}
	if($("#chk5").is(':checked'))
	{
		$(".hchk5").attr('checked', 'checked');
		$(".mchk5").attr('checked', 'checked');
	}

	$(".mchk1").removeAttr('disabled');
	$(".mchk2").removeAttr('disabled');
	$(".mchk3").removeAttr('disabled');
	$(".mchk4").removeAttr('disabled');
	$(".mchk5").removeAttr('disabled');
}

function disableAllChecker(allchkclass)
{
//	alert(allchkclass);

	$ = jQuery;
	
	$("#chk"+allchkclass).removeAttr('checked');
}


/*


function downTab1()
{
	document.getElementById("sc:dataToTab").value = "Transcription Factor:Gene Symbol;Entrez Gene Id!"+document.getElementById("sc:agrSig").value;
}

function downTab2()
{
	document.getElementById("sc:dataToTab").value = "Stemness Signature:Gene Symbol;Entrez Gene Id!"+document.getElementById("sc:agrTF").value;
}

function downTab3()
{
	document.getElementById("sc:dataToTab").value = "Stem Cell type:Gene Symbol;Entrez Gene Id!"+document.getElementById("sc:agrCell").value;
}

*/

function downTab1()
{
	document.getElementById("sc:dataToTab").value = "Stemness Signature:Gene Symbol;Entrez Gene Id+"+document.getElementById("sc:agrSig").value;
}

function downTab2()
{
	document.getElementById("sc:dataToTab").value = "Transcription Factor:Gene Symbol;Entrez Gene Id+"+document.getElementById("sc:agrTF").value;
}

function downTab3()
{
	document.getElementById("sc:dataToTab").value = "Stem Cell type:Gene Symbol;Entrez Gene Id+"+document.getElementById("sc:agrCell").value;
}

function downTab4()
{
	document.getElementById("sc:dataToTab").value = document.getElementById("sc:ssigT").value;
	
//	alert(document.getElementById("sc:dataToTab").value);
	
}

function downTab5()
{
	document.getElementById("sc:dataToTab").value = document.getElementById("sc:tfT").value;
	
//	alert(document.getElementById("sc:dataToTab").value);
	
}

function downTab6()
{
	document.getElementById("sc:dataToTab").value = document.getElementById("sc:ccfT").value;
	
//	alert(document.getElementById("sc:dataToTab").value);
	
}

function exampleGet()
{
	if(spec == 'a') document.getElementById('sc:searchText').value = '701 22 2956 56916 5519 8463 79923 5460 20674 18392 19255 56086 11692 17684 320924';
	else document.getElementById('sc:searchText').value = 'BUB1B ABCB7 MSH6 SMARCAD1 PPP2R1B TEAD2 NANOG POU5F1 SOX2 ORC1 PTPN2 SET GFER CITED2 CCBE1';
}

function loadGraph()
{
	$ = jQuery;
	var a = $("[id='sc:daTab:daTab_activeIndex']").val();
	
	if(a==1 && !per1) radar();
	else if(a==1 && per1) radarp();
	else if(a==2 && !per2) radar2();
	else if(a==2 && per2) radar2p();
	else if(a==0 && !per3) radar3();
	else if(a==0 && per3) radar3p();
	
//	
}

function switch1()
{
	if(per1)
	{
		per1 = false;
		radar();
	}
	else
	{
		per1 = true;
		radarp();
		
	}
}

function switch2()
{
	if(per2)
	{
		per2 = false;
		radar2();
	}
	else
	{
		per2 = true;
		radar2p();
		
	}
}

function switch3()
{
	if(per3)
	{
		per3 = false;
		radar3();
	}
	else
	{
		per3 = true;
		radar3p();
		
	}
}

function addToChecked(one, val)
{
	var pos = -1;
	
//	alert("gaaoaag");
//	alert(checked1.length);
	
	var i;
	
	var dalist;
	
	if(one==true) dalist = checked1;
	else dalist = checked2;
	
	for	(i = 0; pos==-1 && i < dalist.length; i++) {
//		alert("x "+i);
		
		if(dalist[i]==val)
		{
			pos = i;
//			alert("found");
		}
		
	} 
	
	if(pos==-1)
	{
		if(one==true) checked1.push(val);
		else checked2.push(val);
//		alert("not found");
	}
	else
	{
		if(one==true) checked1.splice(pos, 1);
		else checked2.splice(pos, 1);
	}
	
	if(one==true)
	{
		if(checked1.length==0) document.getElementById('scn1').disabled = true;
		else document.getElementById('scn1').disabled = false;
	}
	else 
	{
		if(checked2.length==0) document.getElementById('scn2').disabled = true;
		else document.getElementById('scn2').disabled = false;
	}
}

function serachSCN(one)
{
	var lin = "";
	
	var i;
	
	if(one==true) dalist = checked1;
	else dalist = checked2;
	
	for	(i = 0; i < dalist.length; i++) {
		if(i>0) lin += "&";
		lin += dalist[i];
	}
	
	var links = document.getElementById("sc:dalink");
	
	links.href = "http://193.136.227.168:8282/stemcellnet/stemCellNetRedirect.jsf?"+lin;
	
//	alert(links.href);
	
    links.click();
	
//	alert(lin);
}