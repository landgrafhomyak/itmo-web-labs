<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Лабоаторная №1</title>
    <link rel="stylesheet" href="common.css">
</head>
<body>
<div>
    <h1 id="title">Выбери и точка</h1>
</div>
<div id="canvas-holder">
    <svg id="canvas" viewBox="0 0 150 150">
        <!-- область -->
        <path
                d="M 25 75 V 25 H 75 V 50 A 25 25 0 0 1 100 75 H 75 V 100 L 25 75 Z"
                fill="#0ff"
        ></path>
        <!-- выбранные точки -->
        <g id="points"></g>
        <!-- вертикальная ось -->
        <line x1="75" x2="75" y1="150" y2="4" class="axis"></line>
        <polygon points="75,0 72,5 78,5" class="axis"></polygon>
        <line y1="125" y2="125" x1="72" x2="78" class="axis"></line>
        <text y="125" x="70" class="axis y">-R</text>
        <line y1="100" y2="100" x1="72" x2="78" class="axis"></line>
        <text y="100" x="70" class="axis y">-0.5R</text>
        <line y1="50" y2="50" x1="72" x2="78" class="axis"></line>
        <text y="50" x="70" class="axis y">0.5R</text>
        <line y1="25" y2="25" x1="72" x2="78" class="axis"></line>
        <text y="25" x="70" class="axis y">R</text>
        <!-- горизонтальная ось -->
        <line y1="75" y2="75" x1="0" x2="146" class="axis"></line>
        <line x1="25" x2="25" y1="72" y2="78" class="axis"></line>
        <text x="25" y="80" class="axis x">-R</text>
        <line x1="50" x2="50" y1="72" y2="78" class="axis"></line>
        <text x="50" y="80" class="axis x">-0.5R</text>
        <line x1="100" x2="100" y1="72" y2="78" class="axis"></line>
        <text x="100" y="80" class="axis x">0.5R</text>
        <line x1="125" x2="125" y1="72" y2="78" class="axis"></line>
        <text x="125" y="80" class="axis x">R</text>
        <polygon points="150,75 145,72 145,78" class="axis"></polygon>
    </svg>
</div>
<script src="canvas.js"></script>
<script src="form.js"></script>
<div>
    <form action="" method="post" autocomplete="off">
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
                    <div style="position:relative; display: none">
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
                    ><br>
                    <div style="position:relative; display: none">
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
                    <div style="position:relative; display: none">
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
                    <input type="submit" id="form-submit" disabled value="Проверить">
                </td>
            </tr>
        </table>
        <script>
            checkAll()
        </script>
    </form>
</div>
<?php

function checkPoint(int $x, float $y, int $r): bool
{
    if ($x <= 0 && $y >= 0) {
        return -$x <= $r && $y <= $r;
    } elseif ($x > 0 && $y >= 0) {
        return hypot($x, $y) < 0.5 * $r;
    } elseif ($x < 0 && $y < 0) {
        return $y >= -0.5 * $r * $x - $r;
    } else {
        return false;
    }
}

class Request
{
    public string $x;
    public string $y;
    public string $r;
    public bool $result;
    public string $date;

    static public function checkX(string $input): bool
    {
        if (!is_numeric($input)) return false;
        return in_array(intval($input), [-4, -3, -2, -1, 0, 1, 2, 3, 4]);
    }

    static public function checkY(string $input): bool
    {
        if (!is_numeric($input)) return false;
        $y = floatval($input);
        return -5 < $y && $y < 3;
    }

    static public function checkR(string $input): bool
    {
        if (!is_numeric($input)) return false;
        return in_array(intval($input), [1, 2, 3, 4, 5]);
    }
}

class Database extends SQLite3
{
    function __construct()
    {
        //todo argv
        $this->open("history.sqlite");
    }

    public function createTable()
    {
        $this->prepare(
            "CREATE TABLE IF NOT EXISTS requests_history(date TEXT, x TEXT, y TEXT, r TEXT, result BOOLEAN);"
        )->execute();
    }

    public function addRecord(string $x, string $y, string $r, bool $result)
    {
        $statement = $this->prepare(
            "INSERT INTO requests_history VALUES (datetime('now'), :x, :y, :r, :result);"
        );
        $statement->bindValue(":x", $x);
        $statement->bindValue(":y", $y);
        $statement->bindValue(":r", $r);
        $statement->bindValue(":result", $result);
        $statement->execute();
    }

    public function getLastRequests($howMany): array
    {
        $statement = $this->prepare(
            "SELECT * FROM requests_history ORDER BY date DESC LIMIT :L"
        );
        $statement->bindValue(":L", $howMany);
        $result = $statement->execute();
        $out = array();
        while ($row = $result->fetchArray()) {
            $rq = new Request();
            $rq->x = $row["x"];
            $rq->y = $row["y"];
            $rq->r = $row["r"];
            $rq->result = $row["result"];
            $rq->date = $row["date"];
            $out[] = $rq;
        }
        return $out;
    }
}

$db = new Database();
$db->createTable();

$x = @$_POST['x'];
$y = @$_POST['y'];
$r = @$_POST['r'];
if ($x != null && $y != null && $r != null) {
    if (Request::checkX($x) && Request::checkY($y) && Request::checkR($r)) {
        $xx = intval(htmlspecialchars($x));
        $yy = floatval(htmlspecialchars($y));
        $rr = intval(htmlspecialchars($r));
        $db->addRecord($x, $y, $r, checkPoint($xx, $yy, $rr));
    } else {
        $db->addRecord($x, $y, $r, false);
    }
}
?>
<div>
    <table id="request-history">
        <tr>
            <th>Время запроса</th>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Результат проверки</th>
        </tr>
        <?php
        $history = $db->getLastRequests(10);
        for ($i = 0; $i < count($history); $i++) {
            $invalidInput = false;
            echo "<tr>";
            echo "<td>" . $history[$i]->date . "</td>";
            if (Request::checkX($history[$i]->x)) {
                echo "<td>" . $history[$i]->x . "</td>";
            } else {
                $invalidInput = true;
                echo "<td class='invalid-input'>" . $history[$i]->x . "</td>";
            }
            if (Request::checkY($history[$i]->y)) {
                echo "<td>" . $history[$i]->y . "</td>";
            } else {
                $invalidInput = true;
                echo "<td class='invalid-input'>" . $history[$i]->y . "</td>";
            }
            if (Request::checkR($history[$i]->r)) {
                echo "<td>" . $history[$i]->r . "</td>";
            } else {
                $invalidInput = true;
                echo "<td class='invalid-input'>" . $history[$i]->r . "</td>";
            }
            if ($invalidInput) {
                echo "<td style='color: red'>Невалидный запрос</td>";
            } else {
                if ($history[$i]->result) {
                    echo "<td style='color: green'>В области</td>";
                } else {
                    echo "<td style='color: orange'>Не в области</td>";
                }
            }
            echo "</tr>";
        }
        ?>
    </table>
</div>
</body>
</html>