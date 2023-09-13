<?php

include "./.utils.php";

$db = new Database();
$db->createTable();

$requestHistorySize = 50;
$history = $db->getLastRequests($requestHistorySize);
?>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Лабораторная №1</title>
    <link rel="stylesheet" href="common.css">
</head>
<body>
<div>
    <h1 id="title">ВЫБЕРИ И ТОЧКА</h1>
</div>
<div id="canvas-holder">
    <svg id="canvas" viewBox="0 0 150 150">
        <!-- область -->
        <path
                d="M 25 75 V 25 H 75 V 50 A 25 25 0 0 1 100 75 H 75 V 100 L 25 75 Z"
                fill="#0ff"
        ></path>
        <!-- вертикальная ось -->
        <line x1="75" x2="75" y1="150" y2="4" class="axis"></line>
        <polygon points="75,0 72,5 78,5" class="axis"></polygon>
        <line y1="125" y2="125" x1="72" x2="78" class="axis"></line>
        <line y1="100" y2="100" x1="72" x2="78" class="axis"></line>
        <line y1="50" y2="50" x1="72" x2="78" class="axis"></line>
        <line y1="25" y2="25" x1="72" x2="78" class="axis"></line>
        <!-- горизонтальная ось -->
        <line y1="75" y2="75" x1="0" x2="146" class="axis"></line>
        <line x1="25" x2="25" y1="72" y2="78" class="axis"></line>
        <line x1="50" x2="50" y1="72" y2="78" class="axis"></line>
        <line x1="100" x2="100" y1="72" y2="78" class="axis"></line>
        <line x1="125" x2="125" y1="72" y2="78" class="axis"></line>
        <polygon points="150,75 145,72 145,78" class="axis"></polygon>
        <!-- выбранные точки -->
        <g id="dot-points">
            <?php
            for ($i = count($history) - 1; $i > -1; $i--) {
                if (!$history[$i]->isDataValid()) continue;
                echo "<a id='dot-point-" . $i . "href='#request-" . $i . "'>";
                echo "<circle class='point' id='point-" . $i . "' cx='";
                echo htmlspecialchars(75 + $history[$i]->parsedX() * 50 / $history[$i]->parsedR());
                echo "' cy='";
                echo htmlspecialchars(75 - $history[$i]->parsedY() * 50 / $history[$i]->parsedR());
                echo "' r='2'/>";
                echo "</a>";
            }
            ?>
        </g>
        <text x="25" y="80" class="axis x">-R</text>
        <text x="50" y="80" class="axis x">-0.5R</text>
        <text x="100" y="80" class="axis x">0.5R</text>
        <text x="125" y="80" class="axis x">R</text>
        <text y="125" x="70" class="axis y">-R</text>
        <text y="100" x="70" class="axis y">-0.5R</text>
        <text y="50" x="70" class="axis y">0.5R</text>
        <text y="25" x="70" class="axis y">R</text>
        <!-- надписи к выбранным точкам -->
        <g id="dot-links">
            <?php
            for ($i = count($history) - 1; $i > -1; $i--) {
                if (!$history[$i]->isDataValid()) continue;
                echo "<a id='dot-title-" . $i . "' href='#request-" . $i . "'>";
                echo "<text class='point' x='";
                echo htmlspecialchars(75 + $history[$i]->parsedX() * 50 / $history[$i]->parsedR() + 3);
                echo "' y='";
                echo htmlspecialchars(75 - $history[$i]->parsedY() * 50 / $history[$i]->parsedR() - 3);
                echo "'>";
                echo htmlspecialchars("(x=" . substr($history[$i]->x, 0, 6) . "; y=" . substr($history[$i]->y, 0, 6) . "; r=" . substr($history[$i]->r, 0, 6) . ")");
                echo "</text>";
                echo "</a>";
            }
            ?>
        </g>
    </svg>
</div>
<script src="form.js"></script>
<script>
    const historySize = <?= $requestHistorySize ?>
</script>
<div>
    <h3 style="text-align: center; margin: 0">Выберите координаты и размер</h3>
    <table id="form">
        <tr>
            <th>X</th>
            <td id="form-x">
                <script>
                    for (let e of [-4, -3, -2, -1, 0, 1, 2, 3, 4]) {
                        let lbl = document.createElement("label")
                        let btn = document.createElement("input")
                        lbl.appendChild(btn)
                        xButtons.push(btn)
                        btn.type = "radio"
                        btn.name = "x"
                        btn.value = e.toString()
                        btn.oninput = function () {
                            xAccessed = true;
                            checkAll()
                        }
                        lbl.append(e.toString())
                        document.getElementById("form-x").appendChild(lbl)
                    }
                </script>
                <div style="position:relative; display: none; z-index: 3;">
                    <span class="message-box" id="form-x-error" style="margin: auto;left: 0; right: 0"></span>
                    <svg class="pointer-triangle" viewBox="0 0 6 6" style="margin: auto">
                        <path d="M 0 6 L 3 0 L 6 6"></path>
                    </svg>
                </div>
            </td>
        </tr>
        <tr>
            <th>Y</th>
            <td>
                <input
                        id='form-y'
                        type="text"
                        name="y"
                        placeholder="-5 &lt; y &lt; 3"
                        oninput="yAccessed = true; checkAll()"
                        autocomplete="false"
                >
                <script>
                    yInput = document.getElementById("form-y")
                </script>
                <br>
                <div style="position:relative; display: none;  z-index: 2;">
                    <span class="message-box" id="form-y-error" style="left: 10px"></span>
                    <svg class="pointer-triangle" viewBox="0 0 6 6" style="left: 15px;">
                        <path d="M 0 6 L 3 0 L 6 6"></path>
                    </svg>
                </div>
            </td>
        </tr>
        <tr>
            <th>R</th>
            <td id="form-r">
                <script>
                    for (let e of [1, 2, 3, 4, 5]) {
                        let lbl = document.createElement("label")
                        let btn = document.createElement("input")
                        lbl.appendChild(btn)
                        rButtons.push(btn)
                        btn.type = "checkbox"
                        btn.name = "r"
                        btn.value = e.toString()
                        btn.oninput = function () {
                            rAccessed = true;
                            checkAll()
                        }
                        lbl.append(e.toString())
                        document.getElementById("form-r").appendChild(lbl)
                    }
                </script>
                <div style="position:relative; display: none;  z-index: 1;">
                    <span class="message-box" id="form-r-error" style="margin: auto;left: 0; right: 0"></span>
                    <svg class="pointer-triangle" viewBox="0 0 6 6" style="margin: auto">
                        <path d="M 0 6 L 3 0 L 6 6"></path>
                    </svg>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <!-- отключено, потому что координаты не выбраны при загрузке -->
                <input type="submit" id="form-submit" disabled value="Проверить" onclick="submit()">
            </td>
        </tr>
    </table>
    <script>
        checkAll()
    </script>
</div>
<div>
    <h3 style="text-align: center; margin: 0">Последние <?= $requestHistorySize ?> запросов</h3>
    <table id="request-history">
        <tr>
            <th>Время запроса</th>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Результат проверки</th>
        </tr>
        <?php
        for ($i = 0; $i < count($history); $i++) {
            echo "<tr class='splitter'></tr>";
            echo "<tr  id='request-" . $i . "'>";
            echo "<td>" . htmlspecialchars($history[$i]->date) . " UTC+0</td>";
            if (Request::checkX($history[$i]->x)) {
                echo "<td>" . htmlspecialchars(substr($history[$i]->x, 0, 6)) . "</td>";
            } else {
                echo "<td class='invalid-input'>" . htmlspecialchars($history[$i]->x) . "</td>";
            }
            if (Request::checkY($history[$i]->y)) {
                echo "<td>" . htmlspecialchars(substr($history[$i]->y, 0, 6)) . "</td>";
            } else {
                echo "<td class='invalid-input'>" . htmlspecialchars($history[$i]->y) . "</td>";
            }
            if (Request::checkR($history[$i]->r)) {
                echo "<td>" . htmlspecialchars(substr($history[$i]->r, 0, 6)) . "</td>";
            } else {
                echo "<td class='invalid-input'>" . htmlspecialchars($history[$i]->r) . "</td>";
            }
            if (!$history[$i]->isDataValid()) {
                echo "<td style='color: red'>Невалидный запрос</td>";
            } else {
                if ($history[$i]->result) {
                    echo "<td><a style='color: green' href='#point-" . $i . "'>В области</a></td>";
                } else {
                    echo "<td><a style='color: #b300ff' href='#point-" . $i . "'>Не в области</a></td>";
                }
            }
            echo "</tr>";
        }
        ?>
    </table>
</div>
</body>
</html>
