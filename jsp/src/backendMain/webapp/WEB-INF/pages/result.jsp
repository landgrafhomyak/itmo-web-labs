

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.landgrafhomyak.itmo.web_labs.db.PointData" %>
<%@ page import="ru.landgrafhomyak.itmo.web_labs.jsp.RowGenerator" %>
<%@ page import="ru.landgrafhomyak.itmo.web_labs.jsp.JspWriterRowGenerator" %>
<%! private PointData parsed; %>
<%
    parsed = (PointData) (request.getAttribute("parsed"));
    if (parsed.x == null || parsed.y == null || parsed.r == null)
        response.setStatus(400);
    else {
        response.setStatus(200);
        if (parsed.result) {
            response.addHeader("X-AreaCheckResult", "true");
        } else {
            response.addHeader("X-AreaCheckResult", "false");
        }
    }
    response.addHeader("X-ExecTime", Double.toString(parsed.execTime));
%>
<html>
<head>
    <title>Результат</title>
    <link rel="stylesheet" href="history.css">
</head>
<body>
<table>
<%
    RowGenerator.generateRow(new JspWriterRowGenerator(out), parsed, null, null);
%>
</table>
</body>
</html>
