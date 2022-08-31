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
        <!-- area -->
        <path
                d="M 25 75 V 25 H 75 V 50 A 25 25 0 0 1 100 75 H 75 V 100 L 25 75 Z"
                fill="#0ff"
        ></path>
        <!-- selected points -->
        <g id="points"></g>
        <!-- vertical axis -->
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
        <!-- horizontal axis -->
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
<div>
    <form action="" method="post">
    </form>
</div>
<div>
    <table id="request-history">
    </table>
</div>
</body>
</html>