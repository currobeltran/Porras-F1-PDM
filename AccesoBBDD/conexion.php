<?php

class Conexion{
    private static $instance;
    private static $host = "localhost";
    private static $database= "PorrasF1"; 
    private static $user= "root"; 
    private static $password= "clave";
    
    private function __construct() {}// Constructor privado
    
    private static function connect() {   // Función auxiliar privada
        $bbdd=new mysqli(self::$host, self::$user, self::$password, self::$database);
        return $bbdd;
    }
    
    public static function getInstance() {   // Devuelve la única instancia (y la crea)
        if (!self::$instance)
            self::$instance= self::connect();
            
        return self::$instance;
    }
}

?>