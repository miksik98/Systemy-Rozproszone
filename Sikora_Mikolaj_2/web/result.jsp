<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Air Quality Query Result</title>
</head>
<body>
<h1>Air Quality</h1>
Given address: <%= ((String) session.getAttribute("address")) %> <br>
Closest station: <%= ((String) session.getAttribute("stationAddress")) %> <br>
<br>
<h2>Actual Values</h2>
SO2: <%= session.getAttribute("SO2actual") %> = <b><%= ((String) session.getAttribute("SO2index")) %></b><br>
NO2: <%= session.getAttribute("NO2actual") %> = <b><%= ((String) session.getAttribute("NO2index")) %></b><br>
PM10: <%= session.getAttribute("PM10actual") %> = <b><%= ((String) session.getAttribute("PM10index")) %></b><br>
PM2.5: <%= session.getAttribute("PM25actual") %> = <b><%= ((String) session.getAttribute("PM25index")) %></b><br>
CO: <%= session.getAttribute("COactual") %> = <b><%= ((String) session.getAttribute("COindex")) %> </b><br>
C6H6: <%= session.getAttribute("C6H6actual") %> = <b><%= ((String) session.getAttribute("C6H6index")) %> </b><br>
O3: <%= session.getAttribute("O3actual") %> = <b><%= ((String) session.getAttribute("O3index")) %> </b><br>
<h2>Last 24h Max Values</h2>
SO2: <%= session.getAttribute("SO2max") %> <br>
NO2: <%= session.getAttribute("NO2max") %> <br>
PM10: <%= session.getAttribute("PM10max") %> <br>
PM2.5: <%= session.getAttribute("PM25max") %> <br>
CO: <%= session.getAttribute("COmax") %> <br>
C6H6: <%= session.getAttribute("C6H6max") %> <br>
O3: <%= session.getAttribute("O3max") %> <br>
<h2>Last 24h Min Values</h2>
SO2: <%= session.getAttribute("SO2min") %> <br>
NO2: <%= session.getAttribute("NO2min") %> <br>
PM10: <%= session.getAttribute("PM10min") %> <br>
PM2.5: <%= session.getAttribute("PM25min") %> <br>
CO: <%= session.getAttribute("COmin") %> <br>
C6H6: <%= session.getAttribute("C6H6min") %> <br>
O3: <%= session.getAttribute("O3min") %> <br>
</body>
</html>