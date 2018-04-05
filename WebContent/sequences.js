// Dimensions of sunburst.
var width = 750;
var height = 600;
var radius = Math.min(width, height) / 2;

// Breadcrumb dimensions: width, height, spacing, width of tip/tail.
var b = {
  w: 150, h: 30, s: 3, t: 10
};

// Mapping of step names to colors.
var colors = {
		  "home": "#5687d1",
		  "Stemness signatures": "#06ABA6",
		  
		  "TF Target Genes": "#3DCCC7", //"#42EAE4",
		  
		  "Hs_ESC_NANOG_targets_Boyer": "#42EAE4",
		  "Hs_ESC_OCT4_targets_Boyer": "#42EAE4",
		  "Hs_ESC_SOX2_targets_Boyer": "#42EAE4",
		  "Mm_HSC_Runx1_Wu": "#42EAE4",
		  "Mm_HSC_Tcf7_Wu": "#42EAE4",
		  
		  "RNAi Screens": "#66DDDB", // "#7BFBF9",
		  "Hs_ESC_Chia": "#7BFBF9",
		  "Mm_ESC_Ding": "#7BFBF9",
		  "Mm_ESC_Hu": "#7BFBF9",
		  "Mm_ESC_Wang": "#7BFBF9",
		  "Mm_ESC_Yang": "#7BFBF9",
		  
		  "Expression Profiles": "#07BEB8", //"#08DBD4",
		  
		  "Hs_EC_Skotheim": "#08DBD4",
		  "Hs_ESC/EC_Sperger": "#08DBD4",
		  "Hs_ESC_Assou": "#08DBD4",
		  "Hs_ESC_Bhattacharya": "#08DBD4",
		  "Hs_ESC_Sato": "#08DBD4",
		  "Hs_ESC_Skottman": "#08DBD4",
		  "Hs_HSC/MSC/NSC/ESC_Huang": "#08DBD4",
		  "Hs_HSC/MSC/NSC_Huang": "#08DBD4",
		  "Hs_HSC_Huang": "#08DBD4",
		  "Hs_HSC_Novershtern": "#08DBD4",
		  "Hs_HSC_Toren": "#08DBD4",
		  "Hs_MSC_Huang": "#08DBD4",
		  "Hs_MaSC_Pece": "#08DBD4",
		  "Hs_NSC_Huang": "#08DBD4",
		  "Hs_SC_Palmer": "#08DBD4",
		  "Hs_SC_Shats": "#08DBD4",
		  "Hs_iPSC_Shats": "#08DBD4",
		  "Hs_ESC_Wong": "#08DBD4",
		  "Mm_ESC_Fortunel": "#08DBD4",
		  "Mm_ESC_Gaspar": "#08DBD4",
		  "Mm_ESC_Glover": "#08DBD4",
		  "Mm_ESC_Ivanova": "#08DBD4",
		  "Mm_ESC_Ramalho": "#08DBD4",
		  "Mm_HSC_Ivanova": "#08DBD4",
		  "Mm_HSC_Ramalho": "#08DBD4",
		  "Mm_NSC_D'Amour": "#08DBD4",
		  "Mm_NSC_Fortunel": "#08DBD4",
		  "Mm_NSC_Ivanova": "#08DBD4",
		  "Mm_NSC_Parker": "#08DBD4",
		  "Mm_NSC_Ramalho": "#08DBD4",
		  "Mm_SC_Wong": "#08DBD4",
		  "Mm_SSC_Kokkinaki": "#08DBD4",
		  "Mm_ISC_Munoz": "#08DBD4",
		  "Mm_DSC_Noh": "#08DBD4",
		  "Mm_RPC_Fortunel": "#08DBD4",
		  
		  
		  "Literature Curation": "#97ECEA", //"#97ECEA",
		  "KEGG": "#A6FFFD",
		  "REACTOME": "#A6FFFD",
		  "PluriNetWork": "#A6FFFD",
		  "HSC Explorer": "#A6FFFD",
		  
		  "Computationally Derived": "#C4FFF9",
		  "Plurinet": "#E4FFFC",
		  "GeneCards": "#E4FFFC",
		  
		  
		  "TF Gene Sets": "#900000", //"#A11010",
		  "Nanog": "#A00000",
		  "Hs_Nanog_Boyer1": "#A11010",
		  "Mm_Nanog_Chen": "#A11010",
		  "Mm_Nanog_Loh": "#A11010",
		  "Mm_Nanog_Marson": "#A11010",
		  "Mm_Nanog_Mathur": "#A11010",
		  "Mm_Nanog_Cole": "#A11010",
		  "Mm_Nanog_Kim1": "#A11010",
		  

		  "Oct4": "#C00000", //"#C00000",
		  "Hs_OCT4_Boyer1": "#C01313",
		  "Mm_Oct4_Chen": "#C01313",
		  "Mm_Oct4_Loh": "#C01313",
		  "Mm_Oct4_Marson": "#C01313",
		  "Mm_Oct4_Mathur": "#C01313",
		  "Mm_Oct4_Cole": "#C01313",
		  "Mm_Oct4_Kim1": "#C01313",
		  
		  "Sox2": "#B00000", //"#B00000",
		  "Hs_SOX2_Boyer1": "#B11212",
		  "Mm_Sox2_Chen": "#B11212",
		  "Mm_Sox2_Marson": "#B11212",
		  "Mm_Sox2_Liu": "#B11212",
		  "Mm_Sox2_Kim1": "#B11212",
		  "Mm_Sox2_Lodato1": "#B11212",
		  "Mm_Sox2_Lodato": "#B11212",
		  
		  
		  "Ctcf": "#FC4444",
		  "Mm_Ctcf_Chen": "#FC4444",
		  "E2f1": "#FC4444",
		  "Mm_E2f1_Chen": "#FC4444",
		  "Esrrb": "#FC4444",
		  "Mm_Esrrb_Chen": "#FC4444",
		  "Klf4": "#E80000",
		  "Mm_Klf4_Chen": "#E91717",
		  "Mm_Klf4_Kim1": "#E91717",
		  "Mm_Klf4_Liu": "#E91717",
		  
		  
		  "Mycn": "#FC4444",
		  "Mm_Mycn_Chen": "#FC4444",
		  
		  "Smad1": "#FC0C0C",
		  "Mm_Smad1_Chen": "#F30C0C",
		  "Mm_Smad1_Fei": "#F30C0C",
		  "Stat3": "#FC0000",
		  "Mm_Stat3_Chen": "#FD1919",
		  "Mm_Stat3_Kidder": "#FD1919",
		  
		  
		  "E2f4": "#FC1C1C",
		  "Mm_E2f4_Kim2": "#F31B1B",
		  "Hs_E2F4_Boyer1": "#F31B1B",
		  
		  "Smad4": "#FC2828",
		  "Mm_Smad4_Fei": "#F32727",
		  "Hs_Smad4_Kim": "#F32727",
		  
		  "Smad2": "#FC3434",
		  "Hs_Smad2_Kim": "#F33333",
		  "Hs_Smad2_Brown": "#F33333",
		  
		  "Smad3": "#FC4444",
		  "Hs_Smad3_Kim": "#FC4444",
		  "Hs_Smad3_Brown": "#FC4444",
		  
		  "Runx1": "#FC4444",
		  "Mm_Runx1_Wu": "#FC4444",
		  "Mm_Runx1_Wilson": "#FC4444",
		  
		  "Fli1": "#FC4444",
		  "Mm_Fli1_Wilson": "#FC4444",
		  
		  "Brn2": "#FC4444",
		  "Mm_Brn2_Lodato1": "#FC4444",
		  
		  ///------

		  "Gata2": "#FC4444",
		  "Mm_Gata2_Wilson": "#FC4444",
		  
		  "Gfi1b": "#FC4444",
		  "Mm_Gfi1b_Wilson": "#FC4444",
		  "Lmo2": "#FC4444",
		  "Mm_Lmo2_Wilson": "#FC4444",
		  "Lyl1": "#FC4444",
		  "Mm_Lyl1_Wilson": "#FC4444",
		  "Meis1": "#FC4444",
		  "Mm_Meis1_Wilson": "#FC4444",
		  "Pu1": "#FC4444",
		  "Mm_Pu1_Wilson": "#FC4444",
		  "Scl": "#FC4444",
		  "Mm_Scl_Wilson": "#FC4444",

		  "Tcf7": "#FC4444",
		  "Mm_Tcf7_Wu": "#FC4444",
		  

		  "Erg": "#FC4444",
		  "Mm_Erg_Wilson": "#FC4444",
		  
		  
		  "Myc": "#DC0000",
		  "Mm_Myc_Chen": "#DC1616",
		  "Mm_Myc_Kim2": "#DC1616",
		  "Mm_Myc_Kim1": "#DC1616",
		  "Mm_Myc_Liu": "#DC1616",
		  "Mm_Myc_Kidder": "#DC1616",
		  
		  
		  "Suz12": "#CC0000",
		  "Mm_Suz12_Chen": "#CD1414",
		  "Mm_Suz12_Marson": "#CD1414",
		  "Mm_Suz12_Boyer2": "#CD1414",
		  "Hs_SUZ12_Lee": "#CD1414",
		  "Mm_Suz12_Ku": "#CD1414",
		  
		  
		  "Tcfcp21": "#FC4444",
		  "Mm_Tcfcp2l1_Chen": "#FC4444",
		  "Zfx": "#FC4444",
		  "Mm_Zfx_Chen": "#FC4444",
		  
		  
		  
		  "DMAP1": "#FC4444",
		  "Mm_DMAP1_Kim2": "#FC4444",
		  "GCN5": "#FC4444",
		  "Mm_GCN5_Kim2": "#FC4444",
		  "MAX": "#FC4444",
		  "Mm_MAX_Kim2": "#FC4444",
		  "TIP60": "#FC4444",
		  "Mm_TIP60_Kim2": "#FC4444",
		  "Tcf3": "#F00000",
		  "Mm_Tcf3_Marson": "#F11818",
		  "Mm_Tcf3_Cole": "#F11818",
		  "Mm_Tcf3_Tam": "#F11818",
		  "Nacc1": "#FC4444",
		  "Mm_Nacc1_Kim1": "#FC4444",
		  "Nr0b1": "#FC4444",
		  "Mm_Nr0b1_Kim1": "#FC4444",
		  "Zfp281": "#FC4444",
		  "Mm_Zfp281_Kim1": "#FC4444",
		  "Zfp42": "#FC4444",
		  "Mm_Zfp42_Kim1": "#FC4444",
		  "Eed": "#FC4444",
		  "Mm_Eed_Boyer2": "#FC4444",
		  "Phc1": "#FC4444",
		  "Mm_Phc1_Boyer2": "#FC4444",
		  "Rnf2": "#FC4444",
		  "Mm_Rnf2_Boyer2": "#FC4444",
		  "Tbx3": "#FC4444",
		  "Mm_Tbx3_Han": "#FC4444",
		  "Smad5": "#FC4444",
		  "Mm_Smad5_Fei": "#FC4444",
		  "Ring1B": "#FC4444",
		  "Mm_Ring1B_Ku": "#FC4444",
		  "Ezh2": "#FC4444",
		  "Mm_Ezh2_Ku": "#FC4444",
		  "other": "#a173d1",
		  "end": "#bbbbbb",
  
};

// Total size of all segments; we set this later, after loading the data.
var totalSize = 0; 

var vis = d3.select("#chart").append("svg:svg")
    .attr("width", width)
    .attr("height", height)
    .append("svg:g")
    .attr("id", "container")
    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

var partition = d3.layout.partition()
    .size([2 * Math.PI, radius * radius])
    .value(function(d) { return d.size; });

var arc = d3.svg.arc()
    .startAngle(function(d) { return d.x; })
    .endAngle(function(d) { return d.x + d.dx; })
    .innerRadius(function(d) { return Math.sqrt(d.y); })
    .outerRadius(function(d) { return Math.sqrt(d.y + d.dy); });

// Use d3.text and d3.csv.parseRows so that we do not need to have a header
// row, and can receive the csv as an array of arrays.
d3.text("visit-sequences.csv", function(text) {
  var csv = d3.csv.parseRows(text);
  var json = buildHierarchy(csv);
  createVisualization(json);
});

// Main function to draw and set up the visualization, once we have the data.
function createVisualization(json) {

  // Basic setup of page elements.
  initializeBreadcrumbTrail();
  drawLegend();
  d3.select("#togglelegend").on("click", toggleLegend);

  // Bounding circle underneath the sunburst, to make it easier to detect
  // when the mouse leaves the parent g.
  vis.append("svg:circle")
      .attr("r", radius)
      .style("opacity", 0);

  // For efficiency, filter nodes to keep only those large enough to see.
  var nodes = partition.nodes(json)
      .filter(function(d) {
      return (d.dx > 0.005); // 0.005 radians = 0.29 degrees
      });

  var path = vis.data([json]).selectAll("path")
      .data(nodes)
      .enter().append("svg:path")
      .attr("display", function(d) { return d.depth ? null : "none"; })
      .attr("d", arc)
      .attr("fill-rule", "evenodd")
      .style("fill", function(d) { return colors[d.name]; })
      .style("opacity", 1)
      .on("mouseover", mouseover).on("click", click);;

  // Add the mouseleave handler to the bounding circle.
  d3.select("#container").on("mouseleave", mouseleave);

  // Get total size of the tree = value of root node from partition.
  totalSize = path.node().__data__.value;
 };

// Fade all but the current sequence, and show it in the breadcrumb trail.
 
 String.prototype.replaceAll = function(str1, str2, ignore) 
 {
     return this.replace(new RegExp(str1.replace(/([\/\,\!\\\^\$\{\}\[\]\(\)\.\*\+\?\|\<\>\-\&])/g,"\\$&"),(ignore?"gi":"g")),(typeof(str2)=="string")?str2.replace(/\$/g,"$$$$"):str2);
 } 
 
function mouseover(d) {

  var percentage = (100 * d.value / totalSize).toPrecision(3);
  var percentageString = percentage + "%";
  if (percentage < 0.1) {
    percentageString = "< 0.1%";
  }

  d3.select("#percentage")
      .text(d.name.replaceAll("_", " "));
//    .text(percentageString);

  d3.select("#explanation")
      .style("visibility", "");

  var sequenceArray = getAncestors(d);
  updateBreadcrumbs(sequenceArray, percentageString);

  // Fade all the segments.
  d3.selectAll("path")
      .style("opacity", 0.3);

  // Then highlight only those that are an ancestor of the current segment.
  vis.selectAll("path")
      .filter(function(node) {
                return (sequenceArray.indexOf(node) >= 0);
              })
      .style("opacity", 1);
}

// Restore everything to full opacity when moving off the visualization.
function mouseleave(d) {

  // Hide the breadcrumb trail
  d3.select("#trail")
      .style("visibility", "hidden");

  // Deactivate all segments during transition.
  d3.selectAll("path").on("mouseover", null);

  // Transition each segment to full opacity and then reactivate it.
  d3.selectAll("path")
      .transition()
      .duration(1000)
      .style("opacity", 1)
      .each("end", function() {
              d3.select(this).on("mouseover", mouseover);
            });

  d3.select("#explanation")
      .style("visibility", "hidden");
}

// Given a node in a partition layout, return an array of all of its ancestor
// nodes, highest first, but excluding the root.
function getAncestors(node) {
  var path = [];
  var current = node;
  while (current.parent) {
    path.unshift(current);
    current = current.parent;
  }
  return path;
}

function initializeBreadcrumbTrail() {
  // Add the svg area.
  var trail = d3.select("#sequence").append("svg:svg")
      .attr("width", width)
      .attr("height", 50)
      .attr("id", "trail");
  // Add the label at the end, for the percentage.
  trail.append("svg:text")
    .attr("id", "endlabel")
    .style("fill", "#000");
}

// Generate a string that describes the points of a breadcrumb polygon.
function breadcrumbPoints(d, i) {
  var points = [];
  points.push("0,0");
  points.push(b.w + ",0");
  points.push(b.w + b.t + "," + (b.h / 2));
  points.push(b.w + "," + b.h);
  points.push("0," + b.h);
  if (i > 0) { // Leftmost breadcrumb; don't include 6th vertex.
    points.push(b.t + "," + (b.h / 2));
  }
  return points.join(" ");
}

// Update the breadcrumb trail to show the current sequence and percentage.
function updateBreadcrumbs(nodeArray, percentageString) {

  // Data join; key function combines name and depth (= position in sequence).
  var g = d3.select("#trail")
      .selectAll("g")
      .data(nodeArray, function(d) { return d.name + d.depth; });

  // Add breadcrumb and label for entering nodes.
  var entering = g.enter().append("svg:g");

  entering.append("svg:polygon")
      .attr("points", breadcrumbPoints)
      .style("fill", function(d) { return colors[d.name]; });

  entering.append("svg:text")
      .attr("x", (b.w + b.t) / 2)
      .attr("y", b.h / 2)
      .attr("dy", "0.35em")
      .attr("text-anchor", "middle")
      .text(function(d) { return d.name; });

  // Set position for entering and updating nodes.
  g.attr("transform", function(d, i) {
    return "translate(" + i * (b.w + b.s) + ", 0)";
  });

  // Remove exiting nodes.
  g.exit().remove();

  // Now move and update the percentage at the end.
  d3.select("#trail").select("#endlabel")
      .attr("x", (nodeArray.length + 0.5) * (b.w + b.s))
      .attr("y", b.h / 2)
      .attr("dy", "0.35em")
      .attr("text-anchor", "middle")
      .text(percentageString);

  // Make the breadcrumb trail visible, if it's hidden.
  d3.select("#trail")
      .style("visibility", "");

}

function drawLegend() {

  // Dimensions of legend item: width, height, spacing, radius of rounded rect.
  var li = {
    w: 75, h: 30, s: 3, r: 3
  };

  var legend = d3.select("#legend").append("svg:svg")
      .attr("width", li.w)
      .attr("height", d3.keys(colors).length * (li.h + li.s));

  var g = legend.selectAll("g")
      .data(d3.entries(colors))
      .enter().append("svg:g")
      .attr("transform", function(d, i) {
              return "translate(0," + i * (li.h + li.s) + ")";
           });

  g.append("svg:rect")
      .attr("rx", li.r)
      .attr("ry", li.r)
      .attr("width", li.w)
      .attr("height", li.h)
      .style("fill", function(d) { return d.value; });

  g.append("svg:text")
      .attr("x", li.w / 2)
      .attr("y", li.h / 2)
      .attr("dy", "0.35em")
      .attr("text-anchor", "middle")
      .text(function(d) { return d.key; });
}

function toggleLegend() {
  var legend = d3.select("#legend");
  if (legend.style("visibility") == "hidden") {
    legend.style("visibility", "");
  } else {
    legend.style("visibility", "hidden");
  }
}

// Take a 2-column CSV and transform it into a hierarchical structure suitable
// for a partition layout. The first column is a sequence of step names, from
// root to leaf, separated by hyphens. The second column is a count of how 
// often that sequence occurred.
function buildHierarchy(csv) {
  var root = {"name": "root", "children": []};
  for (var i = 0; i < csv.length; i++) {
    var sequence = csv[i][0];
    var size = +csv[i][1];
    if (isNaN(size)) { // e.g. if this is a header row
      continue;
    }
    var parts = sequence.split("-");
    var currentNode = root;
    for (var j = 0; j < parts.length; j++) {
      var children = currentNode["children"];
      var nodeName = parts[j];
      var childNode;
      if (j + 1 < parts.length) {
   // Not yet at the end of the sequence; move down the tree.
 	var foundChild = false;
 	for (var k = 0; k < children.length; k++) {
 	  if (children[k]["name"] == nodeName) {
 	    childNode = children[k];
 	    foundChild = true;
 	    break;
 	  }
 	}
  // If we don't already have a child node for this branch, create it.
 	if (!foundChild) {
 	  childNode = {"name": nodeName, "children": []};
 	  children.push(childNode);
 	}
 	currentNode = childNode;
      } else {
 	// Reached the end of the sequence; create a leaf node.
 	childNode = {"name": nodeName, "size": size};
 	children.push(childNode);
      }
    }
  }
  return root;
};

function click(d) {
//    alert("Shazam 2	");
//    alert(d.name);
    var links = document.getElementById("sc:dalink");
    
//    links.href = "stemsetdata.jsf?p=s1";
//    links.click();
    /*
     
      









     
     
     */
    
    
    if(d.name=="Hs_ESC_NANOG_targets_Boyer") repNClick(links, "s2");
    else if(d.name=="Hs_ESC_OCT4_targets_Boyer") repNClick(links, "s1");
    else if(d.name=="Hs_ESC_SOX2_targets_Boyer") repNClick(links, "s3");
    else if(d.name=="Mm_Sox2_Lodato") repNClick(links, "t81");
    else if(d.name=="Mm_Erg_Wilson") repNClick(links, "t84");
    else if(d.name=="Mm_HSC_Runx1_Wu") repNClick(links, "s62");
    else if(d.name=="Mm_HSC_Tcf7_Wu") repNClick(links, "s63");
    else if(d.name=="Mm_Brn2_Lodato1") repNClick(links, "t82");
    else if(d.name=="Mm_Sox2_Lodato1") repNClick(links, "t83");
    
    
    else if(d.name=="Hs_EC_Skotheim") repNClick(links, "s51");
    else if(d.name=="Hs_ESC/EC_Sperger") repNClick(links, "s26");
    else if(d.name=="Hs_ESC_Assou") repNClick(links, "s5");
    else if(d.name=="Hs_ESC_Bhattacharya") repNClick(links, "s16");
    else if(d.name=="Hs_ESC_Sato") repNClick(links, "s57");
    else if(d.name=="Hs_ESC_Skottman") repNClick(links, "s60");
    else if(d.name=="Hs_HSC/MSC/NSC/ESC_Huang") repNClick(links, "s56");
    else if(d.name=="Hs_HSC/MSC/NSC_Huang") repNClick(links, "s24");
    else if(d.name=="Hs_HSC_Huang") repNClick(links, "s53");
    else if(d.name=="Hs_HSC_Novershtern") repNClick(links, "s48");
    else if(d.name=="Hs_HSC_Toren") repNClick(links, "s25");
    else if(d.name=="Hs_MSC_Huang") repNClick(links, "s54");
    else if(d.name=="Hs_MaSC_Pece") repNClick(links, "s17");
    else if(d.name=="Hs_NSC_Huang") repNClick(links, "s55");
    else if(d.name=="Hs_SC_Palmer") repNClick(links, "s6");
    else if(d.name=="Hs_SC_Shats") repNClick(links, "s22");
    else if(d.name=="Hs_iPSC_Shats") repNClick(links, "s23");
    else if(d.name=="Hs_ESC_Wong") repNClick(links, "s19");
    else if(d.name=="Mm_ESC_Fortunel") repNClick(links, "s13");
    else if(d.name=="Mm_ESC_Gaspar") repNClick(links, "s15");
    else if(d.name=="Mm_ESC_Glover") repNClick(links, "s59");
    else if(d.name=="Mm_ESC_Ivanova") repNClick(links, "s7");
    else if(d.name=="Mm_ESC_Ramalho") repNClick(links, "s10");
    else if(d.name=="HSC Explorer") repNClick(links, "s52");
    else if(d.name=="Mm_HSC_Ivanova") repNClick(links, "s9");
    else if(d.name=="Mm_HSC_Ramalho") repNClick(links, "s12");
    else if(d.name=="Mm_NSC_D'Amour") repNClick(links, "s50");
    else if(d.name=="Mm_NSC_Fortunel") repNClick(links, "s14");
    else if(d.name=="Mm_NSC_Ivanova") repNClick(links, "s8");
    else if(d.name=="Mm_NSC_Parker") repNClick(links, "s49");
    else if(d.name=="Mm_NSC_Ramalho") repNClick(links, "s11");
    else if(d.name=="Mm_SC_Wong") repNClick(links, "s18");
    else if(d.name=="Mm_SSC_Kokkinaki") repNClick(links, "s20");
//    else if(d.name=="Ms_ISC_Munoz") repNClick(links, "s58");
    
    
    else if(d.name=="Hs_ESC_Chia") repNClick(links, "s27");
    else if(d.name=="Mm_ESC_Ding") repNClick(links, "s28");
    else if(d.name=="Mm_ESC_Hu") repNClick(links, "s29");
    else if(d.name=="Mm_ESC_Wang") repNClick(links, "s30");
    else if(d.name=="Mm_ESC_Yang") repNClick(links, "s61");
    
    else if(d.name=="KEGG") repNClick(links, "s31");
    else if(d.name=="REACTOME") repNClick(links, "s32");
    else if(d.name=="PluriNetWork") repNClick(links, "s33");
    

    else if(d.name=="GeneCards") repNClick(links, "s35");
    else if(d.name=="Plurinet") repNClick(links, "s34");
    
    //------------------------------------
    else if(d.name=="Mm NSC Ramalho") repNClick(links, "s11");
    else if(d.name=="Mm HSC Ramalho") repNClick(links, "s12");
    else if(d.name=="Mm ESC Fortunel") repNClick(links, "s13");
    else if(d.name=="Mm NSC Fortunel") repNClick(links, "s14");
    else if(d.name=="Mm ESC Gaspar") repNClick(links, "s15");
    else if(d.name=="Hs ESC Bhattacharya") repNClick(links, "s16");
    else if(d.name=="Hs MaSC Pace") repNClick(links, "s17");
    else if(d.name=="Mm Wong SC") repNClick(links, "s18");
    else if(d.name=="Hs Wong ESC") repNClick(links, "s19");
    else if(d.name=="Mm SSC Kokkinaki") repNClick(links, "s20");
    else if(d.name=="Mm ISC Munoz") repNClick(links, "s21");
    else if(d.name=="Hs SC Shats") repNClick(links, "s22");
    else if(d.name=="Hs iPSC Shats") repNClick(links, "s23");
    else if(d.name=="Hs HSC/MSC/NSC Huang") repNClick(links, "s24");
    else if(d.name=="Hs HSC Toren") repNClick(links, "s25");
    else if(d.name=="Hs ESC/ECs Sperger") repNClick(links, "s26");

    
    
    
    

    else if(d.name=="GeneCards") repNClick(links, "s35");
    
    
    
//    else if(d.name=="Mm_HSC_Erg_Wilson") repNClick(links, "s36");
    else if(d.name=="Mm_Fli1_Wilson") repNClick(links, "t71");
    else if(d.name=="Mm_Gata2_Wilson") repNClick(links, "t69");
    else if(d.name=="Mm_Gfi1b_Wilson") repNClick(links, "t73");
    else if(d.name=="Mm_Lmo2_Wilson") repNClick(links, "t74");
    else if(d.name=="Mm_Lyl1_Wilson") repNClick(links, "t75");
    else if(d.name=="Mm_Meis1_Wilson") repNClick(links, "t70");
    else if(d.name=="Mm_Pu1_Wilson") repNClick(links, "t76");
    else if(d.name=="Mm_Scl_Wilson") repNClick(links, "t77");
    
    else if(d.name=="Mm_Runx1_Wu") repNClick(links, "t79");
    else if(d.name=="Mm_Runx1_Wilson") repNClick(links, "t78");
    
    
//    else if(d.name=="Mm_HSC_Tcf7_Wu") repNClick(links, "t80");
    else if(d.name=="Hs HSC Novershtern") repNClick(links, "s48");
    else if(d.name=="Mm_NSC_Parker") repNClick(links, "s49");
    else if(d.name=="Mm_NSC_D'Amour") repNClick(links, "s50");
    else if(d.name=="Hs_EC_Skotheim") repNClick(links, "s51");
    else if(d.name=="HSC Explorer") repNClick(links, "s52");
    else if(d.name=="Hs_HSC_Huang") repNClick(links, "s53");
    else if(d.name=="Hs_MSC_Huang") repNClick(links, "s54");
    else if(d.name=="Hs_NSC_Huang") repNClick(links, "s55");
    else if(d.name=="Hs_HSC&MSC&NSC&ESC_Huang") repNClick(links, "s56");
    else if(d.name=="Hs_ESC_Sato") repNClick(links, "s57");
    else if(d.name=="Mm_ISC_Munoz") repNClick(links, "s58");
    else if(d.name=="Mm_ESC_Glover") repNClick(links, "s59");
    else if(d.name=="HS_ESC_Skottman") repNClick(links, "s60");
    else if(d.name=="Mm_DSC_Noh") repNClick(links, "s64");
    else if(d.name=="Mm_RPC_Fortunel") repNClick(links, "s65");
    
    
    
    else if(d.name=="Hs_Nanog_Boyer1") repNClick(links, "t1");
    else if(d.name=="Mm_Nanog_Chen") repNClick(links, "t2");
    else if(d.name=="Mm_Nanog_Loh") repNClick(links, "t3");
    else if(d.name=="Mm_Nanog_Marson") repNClick(links, "t4");
    else if(d.name=="Mm_Nanog_Mathur") repNClick(links, "t5");
    else if(d.name=="Mm_Nanog_Cole") repNClick(links, "t6");
    else if(d.name=="Mm_Nanog_Kim1") repNClick(links, "t7");
    else if(d.name=="Hs_SOX2_Boyer1") repNClick(links, "t8");
    else if(d.name=="Mm_Sox2_Chen") repNClick(links, "t9");
    else if(d.name=="Mm_Sox2_Marson") repNClick(links, "t10");
    else if(d.name=="Mm_Sox2_Liu") repNClick(links, "t11");
    else if(d.name=="Mm_Sox2_Kim1") repNClick(links, "t12");
    else if(d.name=="Hs_OCT4_Boyer1") repNClick(links, "t13");
    else if(d.name=="Mm_Oct4_Chen") repNClick(links, "t14");
    else if(d.name=="Mm_Oct4_Loh") repNClick(links, "t15");
    else if(d.name=="Mm_Oct4_Marson") repNClick(links, "t16");
    else if(d.name=="Mm_Oct4_Mathur") repNClick(links, "t17");
    else if(d.name=="Mm_Oct4_Cole") repNClick(links, "t18");
    else if(d.name=="Mm_Oct4_Kim1") repNClick(links, "t19");
    else if(d.name=="Mm_Ctcf_Chen") repNClick(links, "t20");
    else if(d.name=="Mm_E2f1_Chen") repNClick(links, "t21");
    else if(d.name=="Mm_Esrrb_Chen") repNClick(links, "t22");
    else if(d.name=="Mm_Klf4_Chen") repNClick(links, "t23");
    else if(d.name=="Mm_Klf4_Kim1") repNClick(links, "t24");
    else if(d.name=="Mm_Klf4_Liu") repNClick(links, "t25");
    else if(d.name=="Mm_Mycn_Chen") repNClick(links, "t26");
    else if(d.name=="Mm_Smad1_Chen") repNClick(links, "t27");
    else if(d.name=="Mm_Smad1_Fei") repNClick(links, "t28");
    else if(d.name=="Mm_Stat3_Chen") repNClick(links, "t29");
    else if(d.name=="Mm_Stat3_Kidder") repNClick(links, "t30");
    else if(d.name=="Mm_Suz12_Chen") repNClick(links, "t31");
    else if(d.name=="Mm_Suz12_Marson") repNClick(links, "t32");
    else if(d.name=="Mm_Suz12_Boyer2") repNClick(links, "t33");
    else if(d.name=="Hs_SUZ12_Lee") repNClick(links, "t34");
    else if(d.name=="Mm_Suz12_Ku") repNClick(links, "t35");
    else if(d.name=="Mm_Tcfcp2l1_Chen") repNClick(links, "t36");
    else if(d.name=="Mm_Zfx_Chen") repNClick(links, "t37");
    else if(d.name=="Mm_Myc_Chen") repNClick(links, "t38");
    else if(d.name=="Mm_Myc_Kim2") repNClick(links, "t39");
    else if(d.name=="Mm_Myc_Kim1") repNClick(links, "t40");
    else if(d.name=="Mm_Myc_Liu") repNClick(links, "t41");
    else if(d.name=="Mm_Myc_Kidder") repNClick(links, "t42");
    else if(d.name=="Mm_DMAP1_Kim2") repNClick(links, "t43");
    else if(d.name=="Mm_E2f4_Kim2") repNClick(links, "t44");
    else if(d.name=="Hs_E2F4_Boyer1") repNClick(links, "t45");
    else if(d.name=="Mm_GCN5_Kim2") repNClick(links, "t46");
    else if(d.name=="Mm_MAX_Kim2") repNClick(links, "t47");
    else if(d.name=="Mm_TIP60_Kim2") repNClick(links, "t48");
    else if(d.name=="Mm_Tcf3_Marson") repNClick(links, "t49");
    else if(d.name=="Mm_Tcf3_Cole") repNClick(links, "t50");
    else if(d.name=="Mm_Tcf3_Tam") repNClick(links, "t51");
    else if(d.name=="Mm_Nacc1_Kim1") repNClick(links, "t52");
    else if(d.name=="Mm_Nr0b1_Kim1") repNClick(links, "t53");
    else if(d.name=="Mm_Zfp281_Kim1") repNClick(links, "t54");
    else if(d.name=="Mm_Zfp42_Kim1") repNClick(links, "t55");
    else if(d.name=="Mm_Eed_Boyer2") repNClick(links, "t56");
    else if(d.name=="Mm_Phc1_Boyer2") repNClick(links, "t57");
    else if(d.name=="Mm_Rnf2_Boyer2") repNClick(links, "t58");
    else if(d.name=="Mm_Tbx3_Han") repNClick(links, "t59");
    else if(d.name=="Mm_Smad4_Fei") repNClick(links, "t60");
    else if(d.name=="Hs_Smad4_Kim") repNClick(links, "t61");
    else if(d.name=="Mm_Smad5_Fei") repNClick(links, "t62");
    else if(d.name=="Hs_Smad2_Kim") repNClick(links, "t63");
    else if(d.name=="Hs_Smad2_Brown") repNClick(links, "t64");
    else if(d.name=="Hs_Smad3_Kim") repNClick(links, "t65");
    else if(d.name=="Hs_Smad3_Brown") repNClick(links, "t66");
    else if(d.name=="Mm_Ring1B_Ku") repNClick(links, "t67");
    else if(d.name=="Mm_Ezh2_Ku") repNClick(links, "t68");
    
    

    else if(d.name=="Mm_Tcf7_Wu") repNClick(links, "t80");
    
//    alert(links.href);
    /*
    
    Stemness signatures-TF Target Genes-,1
Stemness signatures-TF Target Genes-,1
Stemness signatures-TF Target Genes-,1
    */
//    stemsetdata.jsf?p=t#{setd[1]}
  }

function repNClick(links, de) {
	links.href = "stemsetdata.jsf?p="+de;
    links.click();
	
}