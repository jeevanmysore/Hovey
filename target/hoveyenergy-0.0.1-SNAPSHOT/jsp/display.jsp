
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<title>Dahsboard</title>
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
   		 <script type="text/javascript">
			
   		 	var utils=[];
   		 	var states=[];
   		 	
   		 	var weeks=[];
   		 	var months=[];
   		 	var years=[];
   		 	var weekCommission=[];
   		 	
   			 google.load('visualization', '1.0', {'packages':['corechart']});   		
	 	    // Set a callback to run when the Google Visualization API is loaded.
	 	    google.setOnLoadCallback(drawCharts);
	 	
	 	    // Callback that creates and populates a data table,
	 	    // instantiates the pie chart, passes in the data and
	 	    // draws it.
   		 	
	 	    <c:forEach var="util" items="${utilities}" varStatus="list">
	 	   		utils.push(['${util.property}',${util.noOfOrders}]);	 	    
	 	    </c:forEach>
	 	    
	 	    
	 	   <c:forEach var="state" items="${states}" varStatus="list">
	   		states.push(['${state.property}',${state.noOfOrders}]);	 	    
	    </c:forEach>
	    
	    <c:forEach var="week" items="${weekSales}" varStatus="list">
   			weeks.push(['${week.date}',${week.totalKwh},${week.lastYearTotalKwh}]);	 	    
   		 </c:forEach>
   		 
   		 <c:forEach var="week" items="${weekSales}" varStatus="list">
			weekCommission.push(['${week.date}',${week.totalCommission},${week.lastYearTotalCommission}]);	 	    
		 </c:forEach>
		 
   		<c:forEach var="month" items="${monthSales}" varStatus="list">
			months.push(['${month.date}',${month.totalKwh},${month.lastYearTotalKwh}]);	 	    
		 </c:forEach>
		 
		 
			<c:forEach var="year" items="${yearSales}" varStatus="list">
			years.push(['${year.date}',${year.totalKwh/100},${year.totalCommission}]);	 	    
		 </c:forEach>
		 
		 
	 	    
	 	    function drawCharts(){
	 	    	drawUtilChart();
	 	    	drawStateChart();
	 	    	drawWeekChart();
	 	    	drawMonthChart();
	 	    	drawCommissionChart();
	 	    	drawYearChart();
	 	    }
	 	    
	 	    
	 	    function drawUtilChart(){
	 	    	  // Create the data table.
	 	        var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Utility');
	 	    	data.addColumn('number','Orders');	 
	 	    	data.addRows(utils);
	 	    	/* data.addRows([
	 	    	              ['jk',5],
	 	    					['ff',3]
	 	    				]); */
	 	    	  var options = {'title':'Sales by Utility',
	                       'width':400,
	                       'height':300};

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
	        	chart.draw(data, options);	 	    	
	 	    }
	 	    
	 	    
	 	    function drawStateChart(){
	 	 	  // Create the data table.
	 	        var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Utility');
	 	    	data.addColumn('number','Orders');	 
	 	    	data.addRows(states);
	 	    	 var options = {'title':'Sales by State',
	                       'width':400,
	                       'height':300};

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.PieChart(document.getElementById('chart_div2'));
	        	chart.draw(data, options);
	 	    }
	 	    
	 	    function drawWeekChart(){
	 	       var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Date');
	 	    	data.addColumn('number','kWh this week');
	 	    	data.addColumn('number','kWh this week(last year)');
	 	    	
	 	    	data.addRows(weeks);
	 	    	 var options = {'title':'Weekly kWh Sales Report',
	                       'width':400,
	                       'height':300,
	                       vAxis: {title: "kWh", format:'#,###%'},
		                   hAxis:{title:'Order Date',format:'mm dd' }      
	 	    	 	};

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.ComboChart(document.getElementById('chart_div3'));
	        	chart.draw(data, options);
	 	    	
	 	    }
	 	    
	 	   function drawMonthChart(){
	 	       var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Date');
	 	    	data.addColumn('number','kWh this Month');
	 	    	data.addColumn('number','kWh this Month(last year)');
	 	    	 
	 	    	data.addRows(months);
	 	    	 var options = {'title':'Monthly kWh Sales Report',
	                       'width':400,
	                       'height':300,
	                       vAxis: {title: "kWh", format:'#,###'},
	                      hAxis:{title:'Order Date',format:'mm dd'}
	                                
	 	    	 };

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.ComboChart(document.getElementById('chart_div4'));
	        	chart.draw(data, options);
	 	    	
	 	    }
   		 
	 	  function drawCommissionChart(){
	 	       var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Date');
	 	      data.addColumn('number','Commission this week');
	 	    	 data.addColumn('number','Commission this week(last year)');
	 	    	 
	 	    	data.addRows(weekCommission);
	 	    	 var options = {'title':'Monthly kWh Sales Report',
	                       'width':400,
	                       'height':300,
	                       vAxis: {title: "kWh", format:'$#,###'},
	                      hAxis:{title:'Order Date',format:'mm dd'}
	                                
	 	    	 };

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.ComboChart(document.getElementById('chart_div4'));
	        	chart.draw(data, options);	 	    	
	 	    }
  		 
	 	  function drawYearChart(){
	 		 var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Date');
	 	      data.addColumn('number','kWh');
	 	    	 data.addColumn('number','Commission');
	 	    	 
	 	    	data.addRows(years);
	 	    	 var options = {'title':'Monthly kWh Sales Report',
	                       'width':800,
	                       'height':300,
	                       vAxis: {title: "kWh", format:'#,###'},
	                      hAxis:{title:'Order Date'}
	                                
	 	    	 };

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.ColumnChart(document.getElementById('chart_div5'));
	        	chart.draw(data, options);	 	    	
	 		  
	 	  }
	 	  
	 	  
	 	  
	 	  
	 	  
   		 
   		 
		</script>
	</head>
	<body>
		<%-- <img alt="Google Pie Chart" src="${utilUrl}" />
		<img alt="Google Pie Chart" src="${stateUrl}" />
		<img alt="Google Pie Chart" src="${salesUrl}" />
		<img alt="Google Pie Chart" src="${yearUrl}" /> --%>
		
		<div id="chart_div" ></div>
		<div id="chart_div2" ></div>
		<div id="chart_div3" ></div>
		<div id="chart_div4" ></div>
		<div id="chart_div5" ></div>
	</body>
</html>