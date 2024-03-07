<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.landgrafhomyak.itmo.web.impl.modules.db.PointData" %>
<%@ page import="ru.landgrafhomyak.itmo.web.impl.apps.lab2.RowGenerator" %>
<%@ page import="ru.landgrafhomyak.itmo.web.impl.apps.lab2.JspWriterRowGenerator" %>
<%
    PointData parsed = (PointData) (request.getAttribute("parsed"));
    if (parsed.getX() == null || parsed.getY() == null || parsed.getR() == null)
        response.setStatus(400);
    else {
        response.setStatus(200);
        if (parsed.getResult()) {
            response.addHeader("X-AreaCheckResult", "true");
        } else {
            response.addHeader("X-AreaCheckResult", "false");
        }
    }
    response.addHeader("X-ExecTime", Double.toString(parsed.getExecTime()));
%>
<html>
<head>
    <title>Результат</title>
    <link rel="stylesheet" href="history.css">
</head>
<body>
<a href="">&lArr; back</a>
<table id="history">
    <%
        RowGenerator.generateRow(new JspWriterRowGenerator(out), parsed, null, null);
    %>
</table>
</body>
</html>
