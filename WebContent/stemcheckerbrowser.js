var myRadarChart;


function presearch()
{
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

	$("#sc\\:settouse").val(paras);
	
}

function postsearch()
{
	$ = jQuery;
	
	//sc:printb
	
	$("sc:printb").hide();
	
	var tab= document.getElementById('checkerboard');
	
	sorttable.makeSortable(tab);
	
	radar();
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
				var w3 = w2[1].split("color:")[1].split(";")[0];
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

function radar()
{
	$ = jQuery;
	var ctx = $("#myChart").get(0).getContext("2d");
	
	/*
	 var data = {
		    labels: ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"],
		    datasets: [
		        {
		            label: "My First dataset",
		            fillColor: "rgba(220,220,220,0.2)",
		            strokeColor: "rgba(220,220,220,1)",
		            pointColor: "rgba(220,220,220,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: [2.5, 3, 2, 3, 6, 2, 100]
		        }
		    ]
		};
	 */
	
	var rbg = document.getElementById("sc:rbg").value.split("!");;

	//labels: ["Red", "Blue", "Green"],
	
	
	var data = {
		    labels: ["Target Genes", "Expression Profiles", "RNAi Screens"],
		    datasets: [
		        {
		            label: "",
		            fillColor: "rgba(0,0,128,0.2)",
		            strokeColor: "rgba(0,0,128,1)",
		            pointColor: "rgba(0,0,128,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: [rbg[0], rbg[1], rbg[2]]
		        }
		    ]
		};
	
	myRadarChart = new Chart(ctx).Radar(data, {responsive: true, scaleOverride: true, scaleSteps: 10, scaleStepWidth: 10, scaleStartValue: 0, angleLineColor: "rgba(0,0,0,.3)"});
	
//	alert(myRadarChart.toBase64Image());
	
//	alert("Done");	
}

function checkClass(chkclass)
{
	$ = jQuery;
//	alert(chkclass+":"+$("#"+chkclass).is(':checked'));
	
	var b = $("#"+chkclass).is(':checked');
	
	$("."+chkclass).attr('checked', b);
}

function downloadgraph()
{
	var url = myRadarChart.toBase64Image().replace(/^data:image\/[^;]/, 'data:application/octet-stream');
	window.open(url);
}
/*
 function preprinttable()
{
	
//	alert(document.getElementById("sc:toPDF").value);
//	alert("done");
	
	var params = document.URL.split("/");
	
	var root = "";
	
	for(var i=0;i<params.length-1;i++)
	{
		if(i>0) root += "/"+params[i];
		else root = params[i];
	}
	
//	alert(root);
	
	// ./images/
	
	var tab= document.getElementById('checkerboard').innerHTML;
	
	
	params = tab.split("./images/");
	
	tab2 = "";
	
	tab2 = params[0];
	
	for(var i=1;i<params.length;i++)
	{
		var chars = params[i].split('');
		
		var found = false;
		
		var correctedString = '';
		
		for(var s=0;s<chars.length;s++)
		{
			if(!found && chars[s]==">")
			{
				correctedString += "></img>";
				found = true;
			}
			else correctedString += chars[s];
		}
		
//		alert(correctedString);
		
		if(i>0) tab2 += root+"/images/"+correctedString;
	}
	
	document.getElementById("sc:toPDF").value = tab2;
	document.getElementById("sc:radarLink").value = myRadarChart.toBase64Image().replace(/^data:image\/[^;]/, 'data:application/octet-stream');
	
//	alert(document.getElementById("sc:toPDF").value);
}
*/