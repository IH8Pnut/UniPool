<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once 'db_config.php';

$data = json_decode(file_get_contents("php://input"));

if(
    !empty($data->shuttle_id) &&
    !empty($data->departure_time) &&
    !empty($data->arrival_time) &&
    !empty($data->destination)
){
    $query = "INSERT INTO trips (ShuttleID, DepartureTime, ArrivalTime, Destination, TripStatus, NoPassengers)
              VALUES (?, ?, ?, ?, 'Scheduled', 0)";

    $stmt = $conn->prepare($query);
    $stmt->bindParam(1, $data->shuttle_id);
    $stmt->bindParam(2, $data->departure_time);
    $stmt->bindParam(3, $data->arrival_time);
    $stmt->bindParam(4, $data->destination);

    if($stmt->execute()){
        http_response_code(201);
        echo json_encode(array("message" => "Trip created successfully."));
    } else {
        http_response_code(503);
        echo json_encode(array("message" => "Unable to create trip."));
    }
} else {
    http_response_code(400);
    echo json_encode(array("message" => "Incomplete data."));
}
?>
