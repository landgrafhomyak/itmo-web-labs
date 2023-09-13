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

function timeNow(): string
{
    return date("c" /*'Y-m-d H:i:s'*/);
}

class Request
{
    public string $x;
    public string $y;
    public string $r;
    public bool $result;
    public string $date;

    function __construct(string $x, string $y, string $r, bool $result, string $date)
    {
        $this->x = $x;
        $this->y = $y;
        $this->r = $r;
        $this->result = $result;
        $this->date = $date;
    }

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

    public function isDataValid(): bool
    {
        return self::checkX($this->x) && self::checkY($this->y) && self::checkR($this->r);
    }

    public function parsedX(): int
    {
        return intval($this->x);
    }

    public function parsedY(): float
    {
        return floatval($this->y);
    }

    public function parsedR(): int
    {
        return intval($this->r);
    }
}

class Database extends SQLite3
{
    function __construct()
    {
        parent::__construct("history.sqlite");
    }

    public function createTable()
    {
        $this->prepare(
            "CREATE TABLE IF NOT EXISTS requests_history(date TEXT, x TEXT, y TEXT, r TEXT, result BOOLEAN);"
        )->execute();
    }

    public function clearHistory()
    {
        $this->prepare(
            "DELETE FROM requests_history;"
        )->execute();
    }

    public function addRecord(string $time, string $x, string $y, string $r, bool $result)
    {
        $statement = $this->prepare(
            "INSERT INTO requests_history VALUES (:t, :x, :y, :r, :result);"
        );
        $statement->bindValue(":t", $time);
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
            $out[] = new Request($row["x"], $row["y"], $row["r"], boolval($row["result"]), $row["date"]);
        }
        return $out;
    }


}


?>