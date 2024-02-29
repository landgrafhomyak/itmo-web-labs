<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.landgrafhomyak.itmo.web_labs.db.jakarta.HttpSessionStorage" %>
<%@ page import="ru.landgrafhomyak.itmo.web_labs.db.PointData" %>
<%@ page import="ru.landgrafhomyak.itmo.web_labs.jsp.RowGenerator" %>
<%@ page import="ru.landgrafhomyak.itmo.web_labs.jsp.JspWriterRowGenerator" %>
<%@ page import="ru.landgrafhomyak.itmo.web.svg_generator.SvgPathDStringPen" %>
<%@ page import="ru.landgrafhomyak.itmo.web_labs.jsp.Model" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="form.css">
    <link rel="stylesheet" href="history.css">
</head>
<body>
<div>
    <h1 id="title">ВЫБЕРИ И ТОЧКА</h1>
</div>
<div id="canvas-holder">
    <svg viewBox="-150 -150 300 300" id="graph" style="aspect-ratio: 1; margin: auto; max-width: 500px;">
        <g transform="scale(1,-1)">
            <path d="<%= SvgPathDStringPen.draw(0.0, 0.0, 100.0, 100.0, Model.INSTANCE.getGraph()) %>" fill="#0ff"></path>
            <polygon points="-2,125 0,130 2,125" class="axis"></polygon>
            <line x1="0" x2="0" y1="125" y2="-125" class="axis"></line>
            <line x1="-2" x2="2" y1="100" y2="100" class="axis"></line>
            <line x1="-2" x2="2" y1="50" y2="50" class="axis"></line>
            <line x1="-2" x2="2" y1="0" y2="0" class="axis"></line>
            <line x1="-2" x2="2" y1="-50" y2="-50" class="axis"></line>
            <line x1="-2" x2="2" y1="-100" y2="-100" class="axis"></line>
            <polygon points="125,-2 130,0 125,2" class="axis"></polygon>
            <line y1="0" y2="0" x1="125" x2="-125" class="axis"></line>
            <line y1="-2" y2="2" x1="100" x2="100" class="axis"></line>
            <line y1="-2" y2="2" x1="50" x2="50" class="axis"></line>
            <line y1="-2" y2="2" x1="0" x2="0" class="axis"></line>
            <line y1="-2" y2="2" x1="-50" x2="-50" class="axis"></line>
            <line y1="-2" y2="2" x1="-100" x2="-100" class="axis"></line>
            <g id="graph-points"></g>
        </g>
    </svg>
</div>
<div>
    <div>
        <table id="form">
            <tr>
                <td>x</td>
                <td id="form-x"></td>
            </tr>
            <tr>
                <td>y</td>
                <td id="form-y"></td>
            </tr>
            <tr>
                <td>r</td>
                <td id="form-r"></td>
            </tr>
            <%--<tr>
                <td><label for="form-y">y</label></td>
                <td><input type="text" id="form-y"></td>
            </tr>
            <tr>
                <td><label for="form-r">r</label></td>
                <td><select id="form-r"></select></td>
            </tr>--%>
            <tr>
                <td colspan="2">
                    <button id="submit">Отправить</button>
                </td>
            </tr>
        </table>
    </div>
</div>
<div>
    <table id="history">
        <%
            HttpSessionStorage historyStorage = new HttpSessionStorage(request.getSession(), "history");
            out.println("<tr id='first-static-history-entry' class='separator'><td></td></tr>");
            for (PointData req : historyStorage.getNewerToOlderHistory(null)) {
                RowGenerator.generateRow(new JspWriterRowGenerator(out), req, null, null);
                out.println("<tr class='separator'><td></td></tr>");
            }
        %>
    </table>
</div>
<script src="form.js"></script>
</body>
</html>
