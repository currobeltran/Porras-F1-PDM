<?php
    require_once "conexion.php";

    header("Content-Type:application/json");
    $accion = $_GET['accion'];

    switch($accion){
        case 'obtenerligas':
            $con = Conexion::getInstance();
            
            $result = $con->query("SELECT * FROM Liga");
            $a = $result->fetch_all(MYSQLI_ASSOC);
            echo json_encode($a);
            $con->close();
        break;

        case 'obtenerronda':
            $idliga = $_GET['id'];
            $con = Conexion::getInstance();

            $result = $con->query("SELECT * FROM Liga WHERE ID=".$idliga);
            $a = $result->fetch_all(MYSQLI_ASSOC);
            echo json_encode($a[0]['Ronda']);
            $con->close();
        break;

        case 'obtenertemporada':
            $idliga = $_GET['id'];
            $con = Conexion::getInstance();

            $result = $con->query("SELECT * FROM Liga WHERE ID=".$idliga);
            $a = $result->fetch_all(MYSQLI_ASSOC);
            echo json_encode($a[0]['Temporada']);
            $con->close();
        break;
    }
?>