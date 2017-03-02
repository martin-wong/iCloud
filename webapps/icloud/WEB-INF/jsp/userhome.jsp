<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的iCloud</title>
</head>
<body>
		
   <div>
     <a href="${pageContext.request.contextPath}/requestout.action" >log out</a> &nbsp;
     <a href="${pageContext.request.contextPath}/index.jsp" >首页</a> &nbsp;
     <a href="${pageContext.request.contextPath}/help.jsp">帮助</a> &nbsp;
     <a href="${pageContext.request.contextPath}/shutdown.action" >远程关机</a> 
  </div>
  
  <div style="font-size: 24px ; text-align: center">欢迎你登陆iCloud <div style="font-size: 20px; color: green;font-style: oblique; float:inherit; ">${user_name}</div></div>
  
  <hr color="blue" size="2"/><br/>
  
   <form action="${pageContext.request.contextPath}/upload.action" method="post" enctype="multipart/form-data">
        <input type="submit" onclick="return checkfile()" value="上传文件" style="background: white;"/>
    	<input type="file" onchange="checkfile()" id="fileupload" name="file" onpropertychange="getFileSize(this.value)"/><br/>
    	<input type="hidden" name="username" value="${user_name}" /><br/>
        <img id="tempimg" dynsrc="" src="" style="display:none" />  
    	${message }
  </form>
  <br/>
  <hr color="red" size="2"/><br/>
  
  
  
  

  <div style="text-align: center">
  <style type="text/css">
    .even{background-color: pink}
    .old{background-color: yellow}
  </style>
  
  <br/>
   <div style="font-size: 30px ; color: blue ; font-style: italic;"><font>Your Files In iCloud</font></div>
   
   <br/>
    <table frame="border" width="100%" align="center">
    	<tr >
    		<td>文件名</td>
    		<td>文件大小</td>
    		<td>上传日期</td>
    		<td>下载文件</td>
    		<td>是否共享</td>
    		<td>操作</td>
    	<tr>
    	
    	<c:forEach var="c" items="${requestScope.pagebean.list}" varStatus="stat">
    		<tr class="${stat.count%2==0?'even':'old'}">
	    		<td>${c.filename }</td>
	    		<td>${c.filesize } kb</td>
	    		<td>${c.createtime }</td>
	    		<td>
	    			<a href="${pageContext.request.contextPath}/download.action?id=${c.id }&filename=${c.filename }">下载</a>
	    		</td>
	    		<td>
	    		<form>
	    		      <select  id="${c.id}" onchange="gochange(${pagebean.currentpage},${c.id})" >
	    		         <c:if test="${c.canshare==0 }">
    					         <option value="0">私有</option> 
    					         <option value="1" >共享</option> 
    					 </c:if>
	    		         <c:if test="${c.canshare==1 }">
   						         <option value="1" selected="selected">共享</option>
    					         <option value="0" >私有</option> 
 					     </c:if>
 					  </select>
 			    </form>
	    		</td>
	    		<td>
                  <a href="javascript:void(0)" onclick="godelete(${pagebean.currentpage},${c.id})">删除文件</a>
	    		</td>
    		<tr>
    	</c:forEach>
    	
    </table>
    <br/>
       共[${requestScope.pagebean.totalrecord}]条记录,
     每页 <input type="text" id = "pagesize" value="${pagebean.pagesize }" onchange="gotopage(${pagebean.currentpage})" style="10px" maxlength="5">条
       共[${requestScope.pagebean.totalpage}]页,
       当前是第[${requestScope.pagebean.currentpage}]页,
      <a href="javascript:void(0)" onclick="gotopage(1)">回到首页</a>
      <a href="javascript:void(0)" onclick="gotopage(${requestScope.pagebean.previouspage})">上一页</a> 
           <c:forEach var="pagenum" items="${requestScope.pagebean.pagebar}">
               <c:if test="${pagenum==pagebean.currentpage }">
                   <font color="red">${pagenum }</font>
		       </c:if>
		        <c:if test="${pagenum!=pagebean.currentpage }">
                  <a href="javascript:void(0)" onclick="gotopage(${pagenum})">${pagenum}</a>
		       </c:if>
           </c:forEach>
      <a href="javascript:void(0)" onclick="gotopage(${requestScope.pagebean.nextpage})">下一页</a>   
      <input type="button" value="跳转至第" onclick="gotopage1(document.getElementById('pagenum').value)" />
      <input type="text" style="10px" maxlength="5" id="pagenum">页
      
      
  <script type="text/javascript">
      
      function godelete(currentpage,fileid){
    	  var pagesize = document.getElementById("pagesize").value;
    	  
    	  if(pagesize > 10 || pagesize >= ${pagebean.totalrecord - pagebean.pagesize * ( pagebean.currentpage - 1 )}){
    		  pagesize = Math.min(pagesize,${pagebean.totalrecord});
    		  currentpage = 1 ;
    	  }else if(pagesize < 1){
    		  pagesize = 1;
    	  }
    	  
    	  var r=confirm("确认删除文件？");
    	  if(r==true){
        	  window.location.href = '${pageContext.request.contextPath}/deletefile.action?currentpage='+currentpage+'&pagesize='+ pagesize+'&id='+fileid;
    	  }else{
    		  return false;
    	  }
      }
      
      function gochange(currentpage,fileid){
    	  
    	  var canshare = document.getElementById(fileid).value;
    	  var pagesize = document.getElementById("pagesize").value;
    	  var r=confirm("如果设置共享，您的文件将可以被其他人搜索到");
    	  if (r==true){
        	  window.location.href = '${pageContext.request.contextPath}/changefilestatus.action?currentpage='+currentpage+'&pagesize='+ pagesize+'&id='+fileid+'&canshare='+canshare;
    	  }else{
    		  location.reload();
    	  }
      }
      
  </script>  
      
  <script type="text/javascript">
      function gotopage(currentpage){
    	  
    	  var pagesize = document.getElementById("pagesize").value;
    	  
    	  if(pagesize > 10 || pagesize >= ${pagebean.totalrecord - pagebean.pagesize * ( pagebean.currentpage - 1 )}){
    		  pagesize = Math.min(10,${pagebean.totalrecord});
    		  currentpage = 1 ;
    	  }else if(pagesize < 1){
    		  pagesize = 1;
    	  }
    	  window.location.href = '${pageContext.request.contextPath}/searchUserfile.action?currentpage='+currentpage+'&pagesize='+ pagesize;
    	  
      }
  
      function gotopage1(currentpage){
    	  
    	  var pagesize = document.getElementById("pagesize").value;
    	  
    	  if(currentpage > ${pagebean.totalpage}){
    		  currentpage = ${pagebean.totalpage};
    		  pagesize = ${pagebean.pagesize};
    	  }else if(currentpage < 1){
    		  currentpage = 1 ;
    		  pagesize = ${pagebean.pagesize};
    	  }
    	  
    	  window.location.href = '${pageContext.request.contextPath}/searchUserfile.action?currentpage='+currentpage+'&pagesize='+ pagesize;
      }
  </script>
  
    <script type="text/javascript">
        var vipmaxsize = 50*1024*1024 ;
        var normalmaxsize = 20*1024*1024 ;
        var viperrMsg = "VIP用户上传的附件文件不能超过50M！！！";
        var normalerrMsg = "普通用户上传的附件文件不能超过20M！！！";
        var tipMsg = "建议使用chrome firefox ie等浏览器";  
        var  browserCfg = {};
        //下面一段鉴别使用者的浏览器
        var ua = window.navigator.userAgent;
        if (ua.indexOf("MSIE")>=1){
            browserCfg.ie = true;
        }else if(ua.indexOf("Firefox")>=1){  
            browserCfg.firefox = true;  
        }else if(ua.indexOf("Chrome")>=1){  
            browserCfg.chrome = true;  
        }  
        function checkfile(){  
            try{  
                var obj_file = document.getElementById("fileupload"); 
                var isvip = ${isvip};
                if(obj_file.value==""){  
                    alert("请先选择上传文件");  
                    return;  
               } 
                var filesize = 0;  
                if(browserCfg.firefox || browserCfg.chrome ){  
                    filesize = obj_file.files[0].size;  //chrome等浏览器支持这个方法拿到文件大小
                }else if(browserCfg.ie){  
                    var obj_img = document.getElementById('tempimg');  
                    obj_img.dynsrc=obj_file.value;  
                    filesize = obj_img.fileSize;  
                }else{  
                    alert(tipMsg);  
                return false;  
                }  
                if(filesize==-1){  
                    alert(tipMsg);  
                    return false;  
                }else if(isvip==1 && filesize>vipmaxsize){  
                    alert(viperrMsg);  
                    return false;  
                }else if(isvip==0 && filesize>normalmaxsize){
                    alert(normalerrMsg);  
                    return false;  
                }else{  
                    return true;  
                }  
            }catch(e){  
                alert(e); 
                return false; 
            } 
           }
  </script>
  
  </div>
  
</body>
</html>




		
		
		

