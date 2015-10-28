<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<head>
	<title>Admin Dashboard</title>
	
	
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
   		 <script type="text/javascript">
			
   		 	var utils=[];
   		 	var states=[];
   		 	var suppliers=[];
   		 	var weeks=[];
   		 	var months=[];
   		 	var years=[];
   		 	var weekCommission=[];
   		 	var monthCommission=[];
   		 	
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
	    
	    
	 	   <c:forEach var="supplier" items="${suppliers}" varStatus="list">
	 	  suppliers.push(['${supplier.property}',${supplier.noOfOrders}]);	 	    
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
		 <c:forEach var="month" items="${monthSales}" varStatus="list">
		 monthCommission.push(['${month.date}',${month.totalCommission},${month.lastYearTotalCommission}]);	 	    
		 </c:forEach>
		 
		 
			<c:forEach var="year" items="${yearSales}" varStatus="list">
			years.push(['${year.date}',${year.totalKwh/100},${year.totalCommission}]);	 	    
		 </c:forEach>
		 
		 
	 	    
	 	    function drawCharts(){
	 	    	drawUtilChart();
	 	    	drawStateChart();
	 	    	drawSupplierChart();
	 	    	drawYearChart();
	 	    	drawWeekChart();
	 	    	drawWeekCommission();
	 	    	drawMonthChart();
	 	    	drawMonthCommission();
	 	    //	drawYearChart();
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
	                       'width':350,
	                       'height':200};

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
	                       'width':350,
	                       'height':200};

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.PieChart(document.getElementById('chart_div2'));
	        	chart.draw(data, options);
	 	    }
	 	    
	 	    
	 	   
	 	    function drawSupplierChart(){
	 	 	  // Create the data table.
	 	        var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Supplier');
	 	    	data.addColumn('number','Orders');	 
	 	    	data.addRows(suppliers);
	 	    	 var options = {'title':'Sales by Supplier',
	                       'width':350,
	                       'height':200};

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.PieChart(document.getElementById('chart_div_1'));
	        	chart.draw(data, options);
	 	    }
	 	    
	 	    
	 	    
	 	    
	 	    
	 	    function drawWeekChart(){
	 	       var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Date');
	 	    	data.addColumn('number','kWh this week');
	 	    	data.addColumn('number','kWh this week(last year)');
	 	    	
	 	    	data.addRows(weeks);
	 	    	 var options = {'title':'Weekly kWh Sales Report',
	                       'width':250,
	                       'height':200,
	                       vAxis: {title: "kWh", format:'#,###'},
		                   hAxis:{title:'Order Date',format:'mm dd' }      
	 	    	 	};

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.ComboChart(document.getElementById('chart_div4'));
	        	chart.draw(data, options);
	 	    	
	 	    }
	 	    
	 	    
	 	   function drawWeekCommission(){
	 	       var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Date');
	 	      data.addColumn('number','Commission this week');
	 	    	 data.addColumn('number','Commission this week(last year)');
	 	    	 
	 	    	data.addRows(weekCommission);
	 	    	 var options = {'title':'Weekly Commission Report',
	                       'width':250,
	                       'height':200,
	                       vAxis: {title: "Commission", format:'$#,###'},
	                      hAxis:{title:'Order Date',format:'mm dd'}
	                                
	 	    	 };

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.ComboChart(document.getElementById('chart_div5'));
	        	chart.draw(data, options);	 	    	
	 	    }
  		 
	 	    
	 	   function drawMonthChart(){
	 	       var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Date');
	 	    	data.addColumn('number','kWh this Month');
	 	    	data.addColumn('number','kWh this Month(last year)');
	 	    	 
	 	    	data.addRows(months);
	 	    	 var options = {'title':'Monthly kWh Sales Report',
	                       'width':350,
	                       'height':200,
	                       vAxis: {title: "kWh", format:'#,###'},
	                      hAxis:{title:'Order Date',format:'mm dd'}
	                                
	 	    	 };

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.ComboChart(document.getElementById('chart_div6'));
	        	chart.draw(data, options);
	 	    	
	 	    }
	 	   
	 	   
	 	  function drawMonthCommission(){
	 	       var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Date');
	 	      data.addColumn('number','Commission this Month');
	 	    	 data.addColumn('number','Commission this Month(last year)');
	 	    	 
	 	    	data.addRows(monthCommission);
	 	    	 var options = {'title':'Monthly Commission Report',
	                       'width':350,
	                       'height':200,
	                       vAxis: {title: "Commission", format:'$#,###'},
	                      hAxis:{title:'Order Date',format:'mm dd'}
	                                
	 	    	 };

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.ComboChart(document.getElementById('chart_div7'));
	        	chart.draw(data, options);	 	    	
	 	    }
	 	   
   		 
	 	
	 	  function drawYearChart(){
	 		 var data = new google.visualization.DataTable();
	 	       data.addColumn('string','Date');
	 	        data.addColumn('number','kWh');
	 	    	 data.addColumn('number','Commission');	 	    	 
	 	    	data.addRows(years);
	 	    	 var options = {'title':'Annual kWh v/s Commission Report',
	                       'width':900,
	                       'height':200,
	                       vAxis: {title: "kWh/100, Commission", format:'#,###'},
	                       hAxis:{title:'Order Date'}
	                                
	 	    	 };

	        	// Instantiate and draw our chart, passing in some options.
	        	var chart = new google.visualization.ColumnChart(document.getElementById('chart_div3'));
	        	chart.draw(data, options);	 	    	
	 		  
	 	  }
	 	  
	</script>
	
	<style>
		#dashboard{
			display: inline-block;
			margin: 0px auto;
			margin-top: 38px;
			width: 100%;
		}
		
		.chart{
			border :1px solid #c2c2c2;
		}
		
		#chart_div,#chart_div2,#chart_div_1{
			float:left;
			width:380px;
			display:block;
		}
		table{
			text-align: left;
		}
	
	</style>
	
</head>
<body class="firefox">
	<%@ include file="header.jsp"%>
	<div id="content" style="margin-left: 2%;">
			
		<div id="dashboard">
		
			 <div style="width:1200px;margin: 0px auto;" >
			  
					<div id="chart_div" class="chart" ></div>
			   		<div id="chart_div_1" class="chart" style="margin-left: 27px;"></div>
				
					<div id="chart_div2" style="float: right;"class="chart"></div>
			   </div>
			<p style="clear:both;"></p>
			<p style="clear:both;"></p>
			<div style="width:1200px;margin: 0px auto;margin-top: 30px;height: 250px;" class="chart">
					<div id="chart_div3" style="900px;display: block;float: left;" ></div>
					<div style="float:right;width:250px;display: block; margin-top: 20px;margin-right: 10px;">
					
						<table  cellspacing="0" style="border: 1px solid #4D323B;width: 250px;">
							<tr style="background-color: #94BBFF;">
								<td>Total kWh</td>
								<td><fmt:formatNumber groupingUsed="true" currencyCode="US" maxFractionDigits="0" value="${yearKwh}" /> </td>
							</tr>
							<tr style="background-color: #F5FFCF;">
								<td>Total Commission </td>
								<td>$ <fmt:formatNumber groupingUsed="true" currencyCode="US" value="${yearCommission }" maxFractionDigits="0" minFractionDigits="0"/></td>
							</tr>						
						</table>				
					</div>
					
					<div style="display: block;width: 250px;float: right;margin-right: 10px;">
					<table  cellspacing="0" style="border: 1px solid #4D323B;width: 250px;margin-top: 30px;">
							<tr style="background-color: #94BBFF;">
								<td>Average kWh </td>
								<td><fmt:formatNumber groupingUsed="true" currencyCode="US" value="${avgKwh }" maxFractionDigits="0" /></td>
								
							</tr>
							
							<tr style="background-color: #F5FFCF;">
								<td>Total Orders </td>
								<td><fmt:formatNumber groupingUsed="true" currencyCode="US" value="${totalOrders }" /></td>
								
							</tr>						
						</table>	
				</div>
				
				<div style="float:right;width:250px;display: block; margin-top: 20px;margin-right: 10px;">
					
						<table  cellspacing="0" style="border: 1px solid #4D323B;width: 250px;">
							<tr style="background-color: #94BBFF;">
								<td>Total Rescinded Orders </td>
								<td><fmt:formatNumber groupingUsed="true" currencyCode="US" value="${totalResOrders}" maxFractionDigits="0" minFractionDigits="0"/></td>
							</tr>
							<tr style="background-color: #F5FFCF;">
								<td>Total Rescinded kWh</td>
								<td><fmt:formatNumber groupingUsed="true" currencyCode="US" maxFractionDigits="0" value="${yearResKwh}" /> </td>
							</tr>
							<tr style="background-color: #F7DEE8;">
								<td>Total Rescinded Commission </td>
								<td>$ <fmt:formatNumber groupingUsed="true" currencyCode="US" value="${yearResCommission }" maxFractionDigits="0" minFractionDigits="0"/></td>
							</tr>						
						</table>				
					</div>
				
			</div>
			<p style="clear:both;"></p>
			<div style="width:1200px;margin: 0px auto;margin-top: 30px;height:300px;" class="chart">
			
				<div id="chart_div4" style="display: block;float: left;"></div>
				<div id="chart_div5" style="display: block;float: left;"></div>
				<div id="chart_div6" style="display: block;float: left;"></div>
				<div id="chart_div7" style="display: block;float: left;"></div>
				
				<div style="display: block;width: 400px;float: left;margin-left: 10px;">
					<table  cellspacing="0" style="border: 1px solid #4D323B;width: 400px;">
							<tr style="background-color: #94BBFF;">
								<td></td>
								<td colspan="1">This Week</td>
								<td colspan="1">Last Year This Week</td>
								<%-- <td><fmt:formatNumber groupingUsed="true" currencyCode="US" value="${yearKwh}" /> </td> --%>
							</tr>
							
							<tr style="background-color: #F5FFCF;">
								<td>Total kWh </td>
								<td><fmt:formatNumber groupingUsed="true" currencyCode="US" value="${weekKwh }"  maxFractionDigits="0" minFractionDigits="0"/></td>
								<td><fmt:formatNumber groupingUsed="true" currencyCode="US" value="${lastYearWeekKwh}"  maxFractionDigits="0" minFractionDigits="0"/></td>
							</tr>	
							<tr style="background-color: #F5FFCF;">
								<td>Total Commission </td>
								<td>$ <fmt:formatNumber groupingUsed="true" currencyCode="US" value="${weekCommission }"  maxFractionDigits="0" minFractionDigits="0"/></td>
								<td>$ <fmt:formatNumber groupingUsed="true" currencyCode="US" value="${lastYearWeekCommission }"  maxFractionDigits="0" minFractionDigits="0"/></td>
							</tr>						
						</table>	
				</div>
				
				<div style="display: block;width: 400px;float: left;margin-left: 100px;">
					<table  cellspacing="0" style="border: 1px solid #4D323B;width: 400px;">
							<tr style="background-color: #94BBFF;">
								<td  ></td>
								<td colspan="1" >This Month</td>
								<td colspan="1">Last Year This Month</td>
								<%-- <td><fmt:formatNumber groupingUsed="true" currencyCode="US" value="${yearKwh}" /> </td> --%>
							</tr>
							
							<tr style="background-color: #F5FFCF;">
								<td>Total kWh </td>
								<td><fmt:formatNumber groupingUsed="true" currencyCode="US" value="${monthKwh }"  maxFractionDigits="0" minFractionDigits="0"/></td>
								<td><fmt:formatNumber groupingUsed="true" currencyCode="US" value="${lastYearMonthKwh}"  maxFractionDigits="0" minFractionDigits="0"/></td>
							</tr>	
							<tr style="background-color: #F5FFCF;">
								<td>Total Commission </td>
								<td>$ <fmt:formatNumber groupingUsed="true" currencyCode="US" value="${monthCommission }"  maxFractionDigits="0" minFractionDigits="0"/></td>
								<td>$ <fmt:formatNumber groupingUsed="true" currencyCode="US" value="${lastYearMonthCommission }" maxFractionDigits="0" minFractionDigits="0"/></td>
							</tr>						
						</table>	
				</div>			
			
			</div>
			
			
	
		</div>	
	
	</div>
	
	
	
	
	
</body>