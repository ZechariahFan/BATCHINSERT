<%--
  Created by IntelliJ IDEA.
  User: fzq15
  Date: 2019/9/1
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.3.min.js"></script>


<body>
<button id="bt">单击</button>
</body>

<script type="text/javascript">

  $("#bt").click(function(){
    $.ajax({
      url:'${pageContext.request.contextPath}/check',
      success:function(data){
        if(data.status=="ok")
          location.href="${pageContext.request.contextPath}/toWelcome";
      },

    error:function(data){

      }
    });
  });
</script>
</html>
