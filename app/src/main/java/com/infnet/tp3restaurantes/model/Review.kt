package com.infnet.tp3restaurantes.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.DocumentId
import com.infnet.tp3restaurantes.database.CypherString

class Review(
    var businessName: CypherString? = null,
    var neighbourhood: CypherString? = null,
    var answer1: String? = null,
    var answer2: String? = null,
    var answer3: String? = null,
    var answer4: String? = null,
    var answer5: String? = null,
    var answer6: String? = null,
    var score: Int? = null,
    var userId: String? = null,
    @DocumentId
    var id: String? = null
        ){
        @RequiresApi(Build.VERSION_CODES.M)
        override fun toString(): String =
                "Restaurante: ${businessName!!.getClearText().toString()} \n" +
                "Bairro: ${neighbourhood!!.getClearText().toString()} \n" +
                "Score: $score%"


}
