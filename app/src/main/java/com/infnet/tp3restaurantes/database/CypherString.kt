package com.infnet.tp3restaurantes.database

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi

class CypherString {
    companion object{
        @JvmStatic
        val Encoder = Cypher()
    }
    private var cypher: ByteArray? = null

    fun getBase64(): String?{
        return Base64.encodeToString(cypher, Base64.DEFAULT)
    }
    fun setBase64(value: String?){
        cypher = Base64.decode(value, Base64.DEFAULT)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getClearText(): String?{
        return Encoder.decoder(cypher!!)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun setClearText(value: String?){
        cypher = Encoder.myCypher(value!!)
    }
}