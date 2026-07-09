<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once 'db_config.php';

$query = "SELECT UserID, Username, Email, Role FROM users";
$stmt = $conn->prepare($query);
$stmt->execute();

$num = $stmt->rowCount();

if($num > 0){
    $users_arr = array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        array_push($users_arr, $row);
    }
    http_response_code(200);
    echo json_encode($users_arr);
} else {
    http_response_code(404);
    echo json_encode(array("message" => "No users found."));
}
?>
