<?php

include "./.utils.php";

$db = new Database();
$db->createTable();


$x = @$_POST['x'];
$y = @$_POST['y'];
$r = @$_POST['r'];
if ($x != null && $y != null && $r != null) {
    $currentRequest = new Request($x, $y, $r, false, "");
    $time = timeNow();
    if ($currentRequest->isDataValid()) {
        $currentRequest->result = checkPoint($currentRequest->parsedX(), $currentRequest->parsedY(), $currentRequest->parsedR());
        $db->addRecord($time, $currentRequest->x, $currentRequest->y, $currentRequest->r, $currentRequest->result);
        echo json_encode(
            array(
                "state" => ($currentRequest->result ? "y" : "n"),
                "x" => $currentRequest->x,
                "y" => $currentRequest->y,
                "r" => $currentRequest->r,
                "time" => $time
            ),
            JSON_PRETTY_PRINT
        );
    } else {
        $db->addRecord($time, $currentRequest->x, $currentRequest->y, $currentRequest->r, false);
        json_encode(
            array(
                "state" => "i",
                "x" => $currentRequest->x,
                "y" => $currentRequest->y,
                "r" => $currentRequest->r,
                "time" => $time
            ),
            JSON_PRETTY_PRINT
        );
    }
}
?>