<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="style.css" rel="stylesheet" type="text/css" />
<style>
#section2{
	display:none;
}
</style>
<title>File Upload</title>
</head>
<body>
<div class="wrapper">
<div class="header">
    <h1>SpringField Challenge<a class="baselink" href="#"></a></h1>
    Making over a billion documents usable! Serving billions of texts per month.</div>
</div>
<br><br>
    <center>
    <div id = "section1">
    <h1>Upload Document Here</h1>
    <hr width="100%" size="5px" color ="black">
    <br><br>
        <form method="post" action="UploadServlet" enctype="multipart/form-data">
            Select file to upload: <input type="file" name="uploadFile" id="inputfile" ${requestScope.dis}/>
            <br/><br/>
            <input type="submit" value="Upload" id="inputsubmit"/>
        </form>
       </div>
       <h3>${requestScope.message}</h3>
       <br><br>
       <div id= " section2" style="display:none${requestScope.text};">
       <h1>Define Rules Here</h1>
        <hr width="100%" size="5px" color ="black">
        <h4>
        Please Upload Rules to Extract Data<br>
Choose which headers you want to extract !<br>
        Example : 
    	Header Fields to Add - booking number,amount,contact person etc.</h4>
        <img src="invoice.png" width="20%">
    	<br><br>
    	<form method="post" action="UploadRules" enctype="multipart/form-data">
    		
    		<input id="hello" type="hidden" name="fname" value="${requestScope.fileName}" /> 
            Select file to upload: <input type="file" name="uploadRule" />
            <br/><br/>
            
            <input type="submit" value="Upload" />
        </form>
       </div>
    </center>

</body>

</html>