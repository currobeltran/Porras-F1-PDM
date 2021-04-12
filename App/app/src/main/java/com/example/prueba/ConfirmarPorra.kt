package com.example.prueba

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject


class ConfirmarPorra : AppCompatActivity() {
    var modoPorra: String = ""
    var opcionElegida: String = ""
    var pilotos: ArrayList<String> = arrayListOf()
    var idRonda = -1
    var temporada = -1
    var puntos = 0.0
    var idUser = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_porra)
        idRonda = intent.getIntExtra("IDRonda", -1)
        temporada = intent.getIntExtra("Temporada", -1)
        idUser = intent.getIntExtra("Usuario", -1)
        Log.i("IDUSUARIO", idUser.toString())

        var textoModo = findViewById<TextView>(R.id.modoPorra)
        textoModo.textSize = 18.0f
        var textoOpcion = findViewById<TextView>(R.id.opciones)
        textoOpcion.textSize = 15.0f
        modoPorra = intent.getStringExtra("Modo")!!

        if(modoPorra == "VueltaRapida"){
            textoModo.text = "El piloto con la vuelta rápida de carrera será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
            opcionElegida = textoOpcion.text.toString()
        }
        else if(modoPorra == "PitStop"){
            textoModo.text = "El equipo con la parada más rápida será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
            opcionElegida = textoOpcion.text.toString()
        }
        else if(modoPorra == "TOPQualiEscuderia"){
            textoModo.text = "El equipo que se llevará la Pole Position será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
            opcionElegida = textoOpcion.text.toString()
        }
        else if(modoPorra == "TOPCarreraEscuderia"){
            textoModo.text = "El equipo que ganará la carrera será:"
            textoOpcion.text = intent.getStringExtra("Opcion")
            opcionElegida = textoOpcion.text.toString()
        }
        else if(modoPorra == "TOP3QualiPiloto"){
            pilotos = intent.getStringArrayListExtra("Opcion") as ArrayList<String>
            textoModo.text = "El top 3 de pilotos en clasificación serán: "
            textoOpcion.text = pilotos[0] + "\n" + pilotos[1] + "\n" + pilotos[2]
        }
        else if(modoPorra == "TOP3CarreraPiloto"){
            pilotos = intent.getStringArrayListExtra("Opcion") as ArrayList<String>
            textoModo.text = "El top 3 de pilotos en carrera serán: "
            textoOpcion.text = pilotos[0] + "\n" + pilotos[1] + "\n" + pilotos[2]
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun enviarPorra(view: View){
        val cliente = OkHttpClient()
        val future = CallbackFuture()
        val future2 = CallbackFuture()

        if(modoPorra == "VueltaRapida"){
            val request = Request.Builder()
                    .url("http://ergast.com/api/f1/$temporada/$idRonda/fastest/1/results.json")
                    .build()

            val response = cliente.newCall(request).enqueue(future)
            val json: JSONObject = JSONObject(future.get()!!.body()!!.string())
            val nombrePiloto = json.getJSONObject("MRData")
                    .getJSONObject("RaceTable")
                    .getJSONArray("Races")
                    .getJSONObject(0)
                    .getJSONArray("Results")
                    .getJSONObject(0)
                    .getJSONObject("Driver")
                    .getString("givenName") + " " + json.getJSONObject("MRData")
                            .getJSONObject("RaceTable")
                            .getJSONArray("Races")
                            .getJSONObject(0)
                            .getJSONArray("Results")
                            .getJSONObject(0)
                            .getJSONObject("Driver")
                            .getString("familyName")

            Log.i("PETICION VUELTA RAPIDA", nombrePiloto)

            //Si existe acierto se realiza la operación de sumar puntos
            if(nombrePiloto == opcionElegida){
                //Primero obtenemos valor del acierto
                val requestPuntosPiloto = Request.Builder()
                        .url("http://192.168.1.14/?accion=obtenerpilotonombre&nombre=$opcionElegida")
                        .build()

                val response2 = cliente.newCall(requestPuntosPiloto).enqueue(future2)
                val json2 = JSONArray(future2.get()!!.body()!!.string())
                puntos = json2.getJSONObject(0)
                        .getDouble("VALORVUELTARAPIDA")

                modificaPuntosUsuario()
            }
        }
        else if(modoPorra == "PitStop"){

        }
        else if(modoPorra == "TOPQualiEscuderia"){
            val request = Request.Builder()
                    .url("http://ergast.com/api/f1/$temporada/$idRonda/qualifying/1.json")
                    .build()

            val response = cliente.newCall(request).enqueue(future)
            val json: JSONObject = JSONObject(future.get()!!.body()!!.string())
            val escuderia = json.getJSONObject("MRData")
                    .getJSONObject("RaceTable")
                    .getJSONArray("Races")
                    .getJSONObject(0)
                    .getJSONArray("QualifyingResults")
                    .getJSONObject(0)
                    .getJSONObject("Constructor")
                    .getString("name")

            Log.i("PETICION QUALIESC", escuderia)
            if(escuderia == opcionElegida){
                val requestPuntosEscuderia = Request.Builder()
                        .url("http://192.168.1.14/?accion=obtenerescuderianombre&nombre=$opcionElegida")
                        .build()

                val response2 = cliente.newCall(requestPuntosEscuderia).enqueue(future2)
                val json2 = JSONArray(future2.get()!!.body()!!.string())
                puntos = json2.getJSONObject(0)
                        .getDouble("VALORCLASIFICACION")

                modificaPuntosUsuario()
            }
        }
        else if(modoPorra == "TOPCarreraEscuderia"){
            val request = Request.Builder()
                    .url("http://ergast.com/api/f1/$temporada/$idRonda/results/1.json")
                    .build()

            val response = cliente.newCall(request).enqueue(future)
            val json: JSONObject = JSONObject(future.get()!!.body()!!.string())
            val escuderia = json.getJSONObject("MRData")
                    .getJSONObject("RaceTable")
                    .getJSONArray("Races")
                    .getJSONObject(0)
                    .getJSONArray("Results")
                    .getJSONObject(0)
                    .getJSONObject("Constructor")
                    .getString("name")

            Log.i("PETICION CARRERAESC", escuderia)
            if(escuderia == opcionElegida){
                val requestPuntosEscuderia = Request.Builder()
                        .url("http://192.168.1.14/?accion=obtenerescuderianombre&nombre=$opcionElegida")
                        .build()

                val response2 = cliente.newCall(requestPuntosEscuderia).enqueue(future2)
                val json2 = JSONArray(future2.get()!!.body()!!.string())
                puntos = json2.getJSONObject(0)
                        .getDouble("VALORCARRERA")

                modificaPuntosUsuario()
            }
        }
        else if(modoPorra == "TOP3QualiPiloto"){
            var resultadoQuali: ArrayList<String> = arrayListOf(" ", " ", " ")

            //Obtenemos el resultado de la clasificación
            for(i in 1 until 4){
                val futureAux = CallbackFuture()
                val request = Request.Builder()
                        .url("http://ergast.com/api/f1/$temporada/$idRonda/qualifying/$i.json")
                        .build()

                val response = cliente.newCall(request).enqueue(futureAux)
                val json: JSONObject = JSONObject(futureAux.get()!!.body()!!.string())
                val nombrePiloto = json.getJSONObject("MRData")
                        .getJSONObject("RaceTable")
                        .getJSONArray("Races")
                        .getJSONObject(0)
                        .getJSONArray("QualifyingResults")
                        .getJSONObject(0)
                        .getJSONObject("Driver")
                        .getString("givenName") + " " + json.getJSONObject("MRData")
                        .getJSONObject("RaceTable")
                        .getJSONArray("Races")
                        .getJSONObject(0)
                        .getJSONArray("QualifyingResults")
                        .getJSONObject(0)
                        .getJSONObject("Driver")
                        .getString("familyName")

                Log.i("NOMBRE PILOTO", nombrePiloto)
                resultadoQuali[i-1] = nombrePiloto
            }

            //Comparamos vectores
            for(i in 0 until resultadoQuali.size){
                for(j in 0 until pilotos.size){
                    if(resultadoQuali[i] == pilotos[j]){
                        val futureAux2 = CallbackFuture()
                        val requestPuntosPiloto = Request.Builder()
                                .url("http://192.168.1.14/?accion=obtenerpilotonombre&nombre=${pilotos[j]}")
                                .build()

                        val response2 = cliente.newCall(requestPuntosPiloto).enqueue(futureAux2)
                        val json2 = JSONArray(futureAux2.get()!!.body()!!.string())
                        puntos = json2.getJSONObject(0)
                                .getDouble("VALORCLASIFICACION")

                        modificaPuntosUsuario()
                    }
                }
            }
        }
        else if(modoPorra == "TOP3CarreraPiloto"){
            var resultadoCarrera: ArrayList<String> = arrayListOf(" ", " ", " ")

            //Obtenemos el resultado de la carrera
            for(i in 1 until 4){
                val futureAux = CallbackFuture()
                val request = Request.Builder()
                        .url("http://ergast.com/api/f1/$temporada/$idRonda/results/$i.json")
                        .build()

                val response = cliente.newCall(request).enqueue(futureAux)
                val json: JSONObject = JSONObject(futureAux.get()!!.body()!!.string())
                val nombrePiloto = json.getJSONObject("MRData")
                        .getJSONObject("RaceTable")
                        .getJSONArray("Races")
                        .getJSONObject(0)
                        .getJSONArray("Results")
                        .getJSONObject(0)
                        .getJSONObject("Driver")
                        .getString("givenName") + " " + json.getJSONObject("MRData")
                        .getJSONObject("RaceTable")
                        .getJSONArray("Races")
                        .getJSONObject(0)
                        .getJSONArray("Results")
                        .getJSONObject(0)
                        .getJSONObject("Driver")
                        .getString("familyName")

                Log.i("NOMBRE PILOTO", nombrePiloto)
                resultadoCarrera[i-1] = nombrePiloto
            }

            //Comparamos vectores
            for(i in 0 until resultadoCarrera.size){
                for(j in 0 until pilotos.size){
                    if(resultadoCarrera[i] == pilotos[j]){
                        val futureAux2 = CallbackFuture()
                        val requestPuntosPiloto = Request.Builder()
                                .url("http://192.168.1.14/?accion=obtenerpilotonombre&nombre=${pilotos[j]}")
                                .build()

                        val response2 = cliente.newCall(requestPuntosPiloto).enqueue(futureAux2)
                        val json2 = JSONArray(futureAux2.get()!!.body()!!.string())
                        puntos = json2.getJSONObject(0)
                                .getDouble("VALORCARRERA")

                        modificaPuntosUsuario()
                    }
                }
            }
        }

        val volverPantallaLigas = Intent(this, PantallaLigas::class.java)
        volverPantallaLigas.putExtra("CambiaGP", true)

        startActivity(volverPantallaLigas)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun modificaPuntosUsuario(){
        val cliente = OkHttpClient()
        val futureDatosUser = CallbackFuture()
        val futureEditaPuntos = CallbackFuture()

        //Obtenemos los puntos del usuario
        val requestPuntosUsuario = Request.Builder()
                .url("http://192.168.1.14/?accion=obtenerusuariobyid&iduser=$idUser")
                .build()
        val responsePuntosUsuario = cliente.newCall(requestPuntosUsuario).enqueue(futureDatosUser)
        val jsonDatosUser = JSONArray(futureDatosUser.get()!!.body()!!.string())
        val puntosActuales = jsonDatosUser.getJSONObject(0)
                .getDouble("Puntos")

        //Calculamos los nuevos puntos y modificamos
        val nuevosPuntos: Double = puntos + puntosActuales
        val requestModificaPuntos = Request.Builder()
                .url("http://192.168.1.14/?accion=modificarpuntosusuario&iduser=$idUser&puntos=$nuevosPuntos")
                .build()
        val responseModificaPuntos = cliente.newCall(requestModificaPuntos).enqueue(futureEditaPuntos)
    }
}