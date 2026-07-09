<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once 'db_config.php';

$query = "SELECT t.*, s.Capacity, u.Username as DriverName
          FROM trips t
          JOIN shuttle s ON t.ShuttleID = s.ShuttleID
          JOIN users u ON s.DriverID = u.UserID";

$stmt = $conn->prepare($query);
$stmt->execute();

$num = $stmt->rowCount();

if($num > 0){
    $trips_arr = array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        extract($row);
        $trip_item = array(
            "id" => $TripID,
            "shuttle_id" => $ShuttleID,
            "departure_time" => $DepartureTime,
            "arrival_time" => $ArrivalTime,
            "destination" => $Destination,
            "status" => $TripStatus,
            "passengers" => $NoPassengers,
            "capacity" => $Capacity,
            "driver_name" => $DriverName
        );
        array_push($trips_arr, $trip_item);
    }
    http_response_code(200);
    echo json_encode($trips_arr);
} else {
    http_response_code(404);
    echo json_encode(array("message" => "No trips found."));
}
?>
