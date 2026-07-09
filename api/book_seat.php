<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once 'db_config.php';

$data = json_decode(file_get_contents("php://input"));

if(!empty($data->user_id) && !empty($data->trip_id) && !empty($data->seat)){

    // Check if trip exists and has capacity
    $check_query = "SELECT t.NoPassengers, s.Capacity FROM trips t
                    JOIN shuttle s ON t.ShuttleID = s.ShuttleID
                    WHERE t.TripID = ?";
    $check_stmt = $conn->prepare($check_query);
    $check_stmt->bindParam(1, $data->trip_id);
    $check_stmt->execute();
    $trip = $check_stmt->fetch(PDO::FETCH_ASSOC);

    if($trip && $trip['NoPassengers'] < $trip['Capacity']){

        $conn->beginTransaction();

        try {
            // Insert reservation
            $query = "INSERT INTO seat_reservation (UserID, TripID, Seat, Status) VALUES (?, ?, ?, 'Reserved')";
            $stmt = $conn->prepare($query);
            $stmt->bindParam(1, $data->user_id);
            $stmt->bindParam(2, $data->trip_id);
            $stmt->bindParam(3, $data->seat);

            if($stmt->execute()){
                // Update passenger count
                $update_query = "UPDATE trips SET NoPassengers = NoPassengers + 1 WHERE TripID = ?";
                $update_stmt = $conn->prepare($update_query);
                $update_stmt->bindParam(1, $data->trip_id);
                $update_stmt->execute();

                $conn->commit();
                http_response_code(201);
                echo json_encode(array("message" => "Reservation successful."));
            } else {
                $conn->rollBack();
                http_response_code(503);
                echo json_encode(array("message" => "Unable to create reservation."));
            }
        } catch (Exception $e) {
            $conn->rollBack();
            http_response_code(500);
            echo json_encode(array("message" => "Error: " . $e->getMessage()));
        }

    } else {
        http_response_code(400);
        echo json_encode(array("message" => "Trip is full or does not exist."));
    }
} else {
    http_response_code(400);
    echo json_encode(array("message" => "Incomplete data."));
}
?>
