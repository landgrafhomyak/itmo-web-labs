<?php

$eStart = microtime(true);

include "./.utils.php";

$db = new Database();
$db->createTable();


$x = @$_POST['x'];
$y = @$_POST['y'];
$r = @$_POST['r'];
$time = timeNow();
if ($x != null && $y != null && $r != null) {
    $currentRequest = new Request($x, $y, $r, false, "", 0);
    if ($currentRequest->isDataValid()) {
        $currentRequest->result = checkPoint($currentRequest->parsedX(), $currentRequest->parsedY(), $currentRequest->parsedR());
        $et = round((microtime(true) - $eStart) * 1000, 3);
        $db->addRecord($time, $currentRequest->x, $currentRequest->y, $currentRequest->r, $currentRequest->result, $et);
        echo json_encode(
            array(
                "state" => ($currentRequest->result ? "y" : "n"),
                "x" => $currentRequest->x,
                "y" => $currentRequest->y,
                "r" => $currentRequest->r,
                "time" => $time,
                "execute_time" => $et
            ),
            JSON_PRETTY_PRINT
        );
    }
} else {
    $et = round((microtime(true) - $eStart) * 1000, 3);
    $db->addRecord($time, $x, $y, $r, false, $et);
    json_encode(
        array(
            "state" => "i",
            "x" => $x,
            "y" => $y,
            "r" => $r,
            "time" => $time,
            "execute_time" => $et
        ),
        JSON_PRETTY_PRINT
    );
}
?>