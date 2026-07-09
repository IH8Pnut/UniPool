<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include_once 'db_config.php';

$data = json_decode(file_get_contents("php://input"));

if(!empty($data->email) && !empty($data->password)){
    $query = "SELECT UserID, Username, Email, Password, Role FROM users WHERE Email = ? LIMIT 0,1";
    $stmt = $conn->prepare($query);
    $stmt->bindParam(1, $data->email);
    $stmt->execute();
    $num = $stmt->rowCount();

    if($num > 0){
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        // Note: In production, use password_verify() with hashed passwords.
        // For this sample, we compare directly as the sample data has plain text.
        if($data->password == $row['Password']){
            http_response_code(200);
            echo json_encode(array(
                "message" => "Login successful.",
                "user" => array(
                    "id" => $row['UserID'],
                    "username" => $row['Username'],
                    "email" => $row['Email'],
                    "role" => $row['Role']
                )
            ));
        } else {
            http_response_code(401);
            echo json_encode(array("message" => "Login failed. Invalid password."));
        }
    } else {
        http_response_code(404);
        echo json_encode(array("message" => "User not found."));
    }
} else {
    http_response_code(400);
    echo json_encode(array("message" => "Incomplete data."));
}
?>
