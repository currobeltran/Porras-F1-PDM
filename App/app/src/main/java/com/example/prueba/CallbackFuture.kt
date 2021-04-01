package com.example.prueba

import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.CompletableFuture

@RequiresApi(Build.VERSION_CODES.N)
class CallbackFuture : CompletableFuture<Response>(), Callback {
    override fun onResponse(call: Call?, response: Response?) {
        super.complete(response)
    }

    override fun onFailure(call: Call?, e: IOException?) {
        super.completeExceptionally(e)
    }
}