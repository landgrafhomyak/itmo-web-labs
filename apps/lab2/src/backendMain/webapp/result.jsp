<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.landgrafhomyak.itmo.web.impl.modules.db.PointData" %>
<%@ page import="ru.landgrafhomyak.itmo.web.impl.apps.lab2.RowGenerator" %>
<%@ page import="ru.landgrafhomyak.itmo.web.impl.apps.lab2.JspWriterRowGenerator" %>
<%
    PointData parsed = (PointData) (request.getAttribute("parsed"));
    if (!parsed.isValid())
        response.setStatus(400);
    else {
        response.setStatus(200);
        if (parsed.result()) {
            response.addHeader("X-AreaCheckResult", "true");
        } else {
            response.addHeader("X-AreaCheckResult", "false");
        }
    }
    response.addHeader("X-ExecTime", parsed.execTime().format());
%>
<html>
<head>
    <title>Результат</title>
    <link rel="stylesheet" href="common.css">
    <link rel="stylesheet" href="history.css">
</head>
<body>

<div>
    <h1 class="title">ВАШ ВЫБОР И ТОЧКА</h1>
</div>
<div style="text-align: center">
    <a href="WEB-INF/pages">&lArr; Вернуться к форме</a>
</div>
<div>
    <table class="history">
        <tr>
            <th>Дата и время запроса</th>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Результат проверки</th>
            <th>Время выполнения запроса</th>
        </tr>
        <tr id='first-static-history-entry' class='separator'>
            <td></td>
        </tr>
        <%
            RowGenerator.generateRow(new JspWriterRowGenerator(out), parsed, null, null);
        %>
    </table>
</div>
</body>
</html>
