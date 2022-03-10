package com.infnet.tp3restaurantes.database

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import java.security.KeyStore
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class Cypher {
    val ks: KeyStore =
            KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getSecretKey(): SecretKey? {
        var mySecretKey: SecretKey? = null
        if(ks.containsAlias("keyCrypt")) {
            val inputentry = ks.getEntry("keyCrypt", null) as?
                    KeyStore.SecretKeyEntry
            mySecretKey = inputentry?.secretKey
        } else {
            val builder = KeyGenParameterSpec.Builder("keyCrypt",
                    KeyProperties.PURPOSE_ENCRYPT or
                            KeyProperties.PURPOSE_DECRYPT)
            val keySpec = builder.setKeySize(256)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7).build()
            val keyGen = KeyGenerator.getInstance("AES", "AndroidKeyStore")
            keyGen.init(keySpec)
            mySecretKey = keyGen.generateKey()
        }
        return mySecretKey
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun myCypher(original: String): ByteArray {
        var myKey = getSecretKey()
        return myCypher(original,myKey)
    }

    fun myCypher(myInput: String, myKey: SecretKey?): ByteArray {
        if (myKey != null) {
            Cipher.getInstance("AES/CBC/PKCS7Padding").run {
                init(Cipher.ENCRYPT_MODE,myKey)
                var cypherValue = doFinal(myInput.toByteArray())
                var cypherSize = ByteArray(16)
                iv.copyInto(cypherSize,0,0,16)
                return cypherSize + cypherValue
            }
        } else return byteArrayOf()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun decoder(cripto: ByteArray): String{
        var myKey = getSecretKey()
        return decoder(cripto,myKey)
    }

    fun decoder(myCypher: ByteArray, myKey: SecretKey?): String{
        if (myKey != null) {
            Cipher.getInstance("AES/CBC/PKCS7Padding").run {
                var cypherSize = ByteArray(16)
                var cypherValue = ByteArray(myCypher.size-16)
                myCypher.copyInto(cypherSize,0,0,16)
                myCypher.copyInto(cypherValue,0,16,myCypher.size)
                val ivParams = IvParameterSpec(cypherSize)
                init(Cipher.DECRYPT_MODE,myKey,ivParams)
                return String(doFinal(cypherValue))
            }
        } else return ""
    }

    fun getHash(myString: String): String{
        var instanceMessageDigest = MessageDigest.getInstance("MD5")
        return Base64.encodeToString(
                instanceMessageDigest.digest(myString.toByteArray()), Base64.DEFAULT).trimEnd()
    }
}