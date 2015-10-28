<%@ include file="header.jsp"%>
<head>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
    	var week=[];
    	var year=[];
    	var month=[];
		// Load the Visualization API and the columnchart package.
	    google.load('visualization', '1.0', {'packages':['corechart']});
	
	    // Set a callback to run when the Google Visualization API is loaded.
	    google.setOnLoadCallback(drawYearChart);
	
	    // Callback that creates and populates a data table,
	    // instantiates the pie chart, passes in the data and
	    // draws it.
	    <c:forEach var="order" items="${chartDatasWeekly}" varStatus="list">
		    week.push(['${order.date}', ${order.totalKwh},${order.totalCommission}]);
   	    </c:forEach>
	     
	   	 <c:forEach var="order" items="${chartDatasYearly}" varStatus="list">
		    year.push(['${order.date}', ${order.totalKwh},${order.totalCommission}]);
	    </c:forEach>
	    
	    <c:forEach var="order" items="${chartDatasMonthly}" varStatus="list">
	    	month.push(['${order.date}', ${order.totalKwh},${order.totalCommission}]);
	    </c:forEach>
	   /* function drawChart(){
	    	drawWeekChart();
	    	drawMonthChart();
	    	drawYearChart();
	    } */
	    
	   /* function drawMonthChart(){
	       // Create the data table.
	       var data = new google.visualization.DataTable();
           data.addColumn('string', 'Date');
           data.addColumn('number', 'Kwh');
		   data.addColumn('number', 'Commission');
	       data.addRows(month);       
	
	      // Set chart options
	      var options = {'title':'Monthly Sales',
	                     'width':650,
	                     'height':300};
	
	      // Instantiate and draw our chart, passing in some options.
	      var chart = new google.visualization.ColumnChart(document.getElementById('chart_div_month'));
	      chart.draw(data, options);
	    	
	    }
	    
	    function drawWeekChart() {
	
	      // Create the data table.
	       var data = new google.visualization.DataTable();
           data.addColumn('string', 'Date');
           data.addColumn('number', 'Kwh');
		   data.addColumn('number', 'Commission');
	       data.addRows(week);       
	
	      // Set chart options
	      var options = {'title':'Weekly Sales',
	                     'width':650,
	                     'height':300};
	
	      // Instantiate and draw our chart, passing in some options.
	      var chart = new google.visualization.ColumnChart(document.getElementById('chart_div_week'));
	      chart.draw(data, options);
	    }*/
	    
	    function drawYearChart() {
	    	
	      // Create the data table.
	       var data = new google.visualization.DataTable();
           data.addColumn('string', 'Date');
           data.addColumn('number', 'Kwh');
		   data.addColumn('number', 'Commission');
	       data.addRows(year);   
	       
	
	      // Set chart options
	      var options = {'title':'Yearly Sales',
	                     'width':500,
	                     'height':300,
	                     hAxis : {
	                    	 baselineColor:'red',
	                     	 slantedText:true,
	                     	 slantedTextAngle:45}
	      				};
	
	      // Instantiate and draw our chart, passing in some options.
	      var chart = new google.visualization.ColumnChart(document.getElementById('chart_div_year'));
	      chart.draw(data, options);
	    }
    </script>
    
    <style type="text/css">
		.style2 {color: #FFFFFF}
		.box_text { font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#000;padding-left: 10px; background:#e5e5e5; }
		.box_text1 { font-family:Arial, Helvetica, sans-serif; font-size:14px; color:#000;padding-left: 10px; background:rgb(252, 250, 250); }
    </style>
	<title>Admin Dashboard</title>
	
</head>
<body class="firefox">
	
<div id="content" style="margin-left: 2%;">
 <br><br>
 	
<ul id="tabs">
		
		
	</ul>
	<div id="outerContainer" style="width: 98.5%;margin-bottom: 30px;">
	    <div id="chart_div_week"></div>
	    <div id="chart_div_month"></div>
	    <div id="chart_div_year" style="float:right;margin-top: 43px;"></div>
	    <table style="width: 30%;" cellspacing="0" cellpadding="0">
        <tbody>
              <tr>
				<td>
				  <table width="100%" cellpadding="2" cellspacing="2" style="border:2px solid #ccc;">
					<tbody><tr>
					  <td><table width="100%" cellspacing="0" cellpadding="0">
						  <tbody><tr>
							<td width="73%" height="45" align="left" bordercolor="#FFFFFF" class="box_text" colspan="3" style="text-align: center;font-weight: bold; color: 0662a9">Weekly Sales</td>
						  </tr>
						  <tr>
							<td height="45" bgcolor="#999999" class="box_text1">Total KWH</td>
                            <td height="45" align="center" bgcolor="#999999" class="box_text1"><span class="style2">:</span></td>
                            <td height="45" class="box_text1">${totalSales.weeklyTotalKwh }</td>
						  </tr>
						  <tr>
							<td height="45" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text">Total Commission</td>
			                <td height="45" align="center" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text"><span class="style2">:</span></td>
			                <td height="45" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text"><fmt:formatNumber value="${totalSales.weeklyTotalCommission }" type="currency" currencySymbol="$"/> </td>
						  </tr>
					  </tbody></table></td>
					</tr>
				   </tbody>
				  </table>
				</td>
		      </tr>
		      <tr>
		      	<td>&nbsp;</td>
		      </tr>
		 	  <tr>
		  		<td>
		  			<table width="100%" cellpadding="2" cellspacing="2" style="border:2px solid #ccc;">
						<tbody>
						<tr>
			  				<td>
			  					<table width="100%" cellspacing="0" cellpadding="0">
				  				<tbody>
				  				<tr>
									<td width="73%" height="45" align="left" bordercolor="#FFFFFF" class="box_text" colspan="3" style="text-align: center;font-weight: bold;color: 0662a9;">Monthly Sales</td>
				  				</tr>
				  				<tr>
									<td height="45" bgcolor="#999999" class="box_text1">Total KWH</td>
                                    <td height="45" align="center" bgcolor="#999999" class="box_text1"><span class="style2">:</span></td>
                                    <td height="45" class="box_text1">${totalSales.monthlyTotalKwh }</td>
				  				</tr>
				  				<tr>
									<td height="45" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text" >Total Commission</td>
                                    <td height="45" align="center" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text"><span class="style2">:</span></td>
                                    <td height="45" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text"><fmt:formatNumber value="${totalSales.monthlyTotalCommission }" type="currency" currencySymbol="$"/></td>
				  				</tr>
					     		</tbody>
					     		</table>
					     	</td>
						</tr>
		   				</tbody>
		  			</table>
		  		</td>
		 </tr>
		 <tr>
		 	<td>&nbsp;</td>
		 </tr>
         <tr>
		  <td>
		  	  <table width="100%" cellpadding="2" cellspacing="2" style="border:2px solid #ccc;">
				<tbody>
				<tr>
			  		<td>
			  			<table width="100%" cellspacing="0" cellpadding="0">
				  		<tbody>
				  		<tr>
						  <td width="73%" height="45" align="left" bordercolor="#FFFFFF" class="box_text" colspan="3" style="text-align: center;font-weight: bold;color: 0662a9;">Yearly Sales</td>
						</tr>
				  		<tr>
							<td height="45" bgcolor="#999999" class="box_text1">Total KWH</td>
                            <td height="45" align="center" bgcolor="#999999" class="box_text1"><span class="style2">:</span></td>
                            <td height="45" class="box_text1">${totalSales.yearlyTotalKwh }</td>
				  		</tr>
				  		<tr>
							<td height="45" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text">Total Commission</td>
	                        <td height="45" align="center" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text"><span class="style2">:</span></td>
	                        <td height="45" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text"><fmt:formatNumber value="${totalSales.yearlyTotalCommission }" type="currency" currencySymbol="$"/></td>
				  		</tr>
					    </tbody>
					    </table>
					</td>
			    </tr>
		   		</tbody>
		  	 </table></td>
		 </tr>
		 <tr>
		 	<td>&nbsp;</td>
		 </tr>
         <tr>
            <td>
            	<table cellpadding="2" cellspacing="2" style="border:2px solid #ccc;width: 100%;">
                   <tbody>
                   <tr>
                      <td>
                      	  <table width="100%" cellspacing="0" cellpadding="0">
                          <tbody>
                          <tr>
                             <td width="73%" height="45" align="left" bordercolor="#FFFFFF" class="box_text" colspan="3" style="text-align: center;font-weight: bold;color: 0662a9;">Last year '${totalSales.dateRange }' : Avg KWH </td>
                          </tr>
                          <tr>
                            <td height="45" bgcolor="#999999" class="box_text1">Average KWH</td>
                            <td height="45" align="center" bgcolor="#999999" class="box_text1"><span class="style2">:</span></td>
                            <td height="45" class="box_text1"><fmt:formatNumber value="${totalSales.avgKwh }" type="currency" currencySymbol=""/></td>
                          </tr>
                          <tr>
                            <td height="45" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text">Total No.Of Sales</td>
                            <td height="45" align="center" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text"><span class="style2">:</span></td>
                            <td height="45" bordercolor="#FFFFFF" bgcolor="#CCCCCC" class="box_text">${totalSales.total }</td>
                          </tr>
                          </tbody>
                          </table>
                       </td>
                    </tr>
                    </tbody>
                 </table></td>
            </tr>
            </table>
        </div>
	</div>
</body>