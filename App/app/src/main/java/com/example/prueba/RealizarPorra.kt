package com.example.prueba

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class RealizarPorra : AppCompatActivity() {
    var pilotoSeleccionado: String = ""
    var escuderiaSeleccionada: String = ""
    var modoPorra: String = ""
    var podio: ArrayList<String> = arrayListOf(" ", " ", " ")

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realizar_porra)

        val titulo = findViewById<TextView>(R.id.textView14)
        titulo.text = intent.getStringExtra("Titulo")
        var lay: LinearLayout = findViewById(R.id.layout_pilotos)
        val modo = intent.getStringExtra("Modo")
        modoPorra = modo.toString()
        var contador = 0

        //Configuramos el boton para que no pueda ser pulsado hasta tener completada la porra
        var boton: Button = findViewById(R.id.button9)
        boton.isClickable = false
        boton.setBackgroundColor(Color.GRAY)

        if (modo == "TOP3QualiPiloto" || modo == "TOP3CarreraPiloto" || modo == "VueltaRapida"){
            val jsonPilotos = ObtenerPilotos()

            for(i in 0 until jsonPilotos.length()){
                val piloto = jsonPilotos.getJSONObject(i)

                var lay2: LinearLayout = LinearLayout(this)
                lay2.orientation = LinearLayout.HORIZONTAL
                lay.addView(lay2)

                val parametros_layout = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                parametros_layout.setMargins(20,0,20,0)

                var check = CheckBox(this)
                check.text = piloto.getString("NOMBRE")
                check.textSize = 18.0f
                check.layoutParams = parametros_layout

                //Con el siguiente listener evitamos que haya mas opciones marcadas de las esperadas
                check.setOnCheckedChangeListener{buttonView, isChecked ->
                    val nombreOpcion = buttonView.text.toString()

                    //Movemos contador
                    if (isChecked){
                        //Este if nos ayudará a introducir elementos en el array podio
                        if(contador<3){
                            val indice = podio.indexOfFirst { it == " " }
                            if(indice != -1){
                                podio[indice] = nombreOpcion
                            }
                        }
                        contador++
                    }
                    else{
                        contador--
                        val indice = podio.indexOf(nombreOpcion)
                        podio[indice] = " "
                    }

                    //Si se supera el límite, se revoca la accion
                    if((modo == "TOP3QualiPiloto" || modo == "TOP3CarreraPiloto") && contador>3){
                        buttonView.isChecked = false
                        contador--
                    }
                    else if(contador>1 && modo == "VueltaRapida"){
                        buttonView.isChecked = false
                        contador--
                    }

                    //Si se tienen los pilotos seleccionados, se activa el boton de realizar porra
                    if((modo == "TOP3QualiPiloto" || modo == "TOP3CarreraPiloto") && contador==3){
                        boton.isClickable = true
                        boton.setBackgroundColor(getColor(R.color.teal_700))
                    }
                    else if(contador == 1 && modo == "VueltaRapida"){
                        if(buttonView.isChecked){
                            pilotoSeleccionado = nombreOpcion
                        }
                        boton.isClickable = true
                        boton.setBackgroundColor(getColor(R.color.teal_700))
                    }

                    //Si los valores anteriores se incumplen, se vuelve a desactivar el boton
                    if((modo == "TOP3QualiPiloto" || modo == "TOP3CarreraPiloto") && contador!=3){
                        boton.isClickable = false
                        boton.setBackgroundColor(Color.GRAY)
                    }
                    else if(contador != 1 && modo == "VueltaRapida"){
                        boton.isClickable = false
                        boton.setBackgroundColor(Color.GRAY)
                    }
                    Log.i("CONTADOR",contador.toString())
                }

                var valorPiloto: TextView = TextView(this)
                if(modo == "TOP3QualiPiloto"){
                    valorPiloto.text = piloto.getString("VALORCLASIFICACION")
                }
                else if(modo == "TOP3CarreraPiloto"){
                    valorPiloto.text = piloto.getString("VALORCARRERA")
                }
                else{
                    valorPiloto.text = piloto.getString("VALORVUELTARAPIDA")
                }
                valorPiloto.textSize = 18.0f
                valorPiloto.layoutParams = parametros_layout

                lay2.addView(check)
                lay2.addView(valorPiloto)
            }
        }
        else{
            val jsonEscuderias = ObtenerEscuderias()

            for(i in 0 until jsonEscuderias.length()){
                val escuderia = jsonEscuderias.getJSONObject(i)

                var lay2: LinearLayout = LinearLayout(this)
                lay2.orientation = LinearLayout.HORIZONTAL
                lay.addView(lay2)

                val parametros_layout = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                parametros_layout.setMargins(20,0,20,0)

                var check = CheckBox(this)
                check.text = escuderia.getString("NOMBRE")
                check.textSize = 18.0f
                check.layoutParams = parametros_layout

                //Con el siguiente listener evitamos que haya mas opciones marcadas de las esperadas
                check.setOnCheckedChangeListener{buttonView, isChecked ->
                    //Movemos contador
                    if (isChecked){
                        contador++
                    }
                    else{
                        contador--
                    }

                    //Si se supera el límite, revocamos la acción
                    if(contador>1){
                        buttonView.isChecked = false
                        contador--
                    }

                    //Si se tiene la escuderia seleccionada, se habilita el boton de realizar porra
                    if(contador == 1){
                        boton.isClickable = true
                        boton.setBackgroundColor(getColor(R.color.teal_700))
                        if(buttonView.isChecked){
                            escuderiaSeleccionada = buttonView.text.toString()
                        }
                    }

                    //Si se desmarca la escuderia, se vuelve a desactivar el boton
                    if(contador!=1){
                        boton.isClickable = false
                        boton.setBackgroundColor(Color.GRAY)
                    }
                    Log.i("CONTADOR",contador.toString())
                }

                var valorEscuderia: TextView = TextView(this)
                if(modo == "PitStop"){
                    valorEscuderia.text = escuderia.getString("VALORPITSTOP")
                }
                else if(modo == "TOPQualiEscuderia"){
                    valorEscuderia.text = escuderia.getString("VALORCLASIFICACION")
                }
                else{
                    valorEscuderia.text = escuderia.getString("VALORCARRERA")
                }
                valorEscuderia.textSize = 18.0f
                valorEscuderia.layoutParams = parametros_layout

                lay2.addView(check)
                lay2.addView(valorEscuderia)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun ObtenerPilotos(): JSONArray{
        val cliente: OkHttpClient = OkHttpClient()
        val future = CallbackFuture()
        val future2 = CallbackFuture()

        //Se divide en 2 peticiones porque en 1 se sobrecarga el buffer de lectura
        val peticion: Request = Request.Builder()
                .url("http://192.168.1.14/?accion=obtenerpilotos&from=1&to=10")
                .build()
        val respuesta = cliente.newCall(peticion).enqueue(future)
        var primeraparte: JSONArray = JSONArray(future.get()!!.body()!!.string())

        val peticion2: Request = Request.Builder()
                .url("http://192.168.1.14/?accion=obtenerpilotos&from=11&to=20")
                .build()
        val respuesta2 = cliente.newCall(peticion2).enqueue(future2)
        val segundaparte: JSONArray = JSONArray(future2.get()!!.body()!!.string())

        for(i in 0 until segundaparte.length()){
            primeraparte.put(segundaparte.getJSONObject(i))
        }

        return primeraparte
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun ObtenerEscuderias(): JSONArray{
        val cliente: OkHttpClient = OkHttpClient()
        val future = CallbackFuture()

        val peticion: Request = Request.Builder()
                .url("http://192.168.1.14/?accion=obtenerescuderias")
                .build()
        val respuesta = cliente.newCall(peticion).enqueue(future)

        return JSONArray(future.get()!!.body()!!.string())
    }

    fun botonRealizarPorra(view: View){
        val intentConfirmarPorra = Intent(this, ConfirmarPorra::class.java)
        intentConfirmarPorra.putExtra("Modo", modoPorra)

        if(modoPorra == "VueltaRapida"){
            intentConfirmarPorra.putExtra("Opcion", pilotoSeleccionado)
        }
        else if(modoPorra == "PitStop" || modoPorra == "TOPQualiEscuderia" || modoPorra == "TOPCarreraEscuderia"){
            intentConfirmarPorra.putExtra("Opcion", escuderiaSeleccionada)
        }
        else{
            intentConfirmarPorra.putExtra("Opcion", podio)
        }

        startActivity(intentConfirmarPorra)
    }
}