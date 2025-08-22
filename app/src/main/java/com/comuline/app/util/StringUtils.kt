package com.comuline.app.util

import java.util.Locale

/**
 * Converts an uppercase string to sentence case.
 * Examples:
 * - "MANGGARAI" -> "Manggarai"
 * - "JAKARTAKOTA" -> "Jakarta Kota" 
 * - "KAMPUNGBANDAN" -> "Kampung Bandan"
 */
fun String.toSentenceCase(): String {
    if (this.isBlank()) return this
    
    return this.lowercase(Locale.getDefault())
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { 
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() 
            }
        }
}

/**
 * Converts station names with special handling for compound words
 * This handles cases like "JAKARTAKOTA" -> "Jakarta Kota"
 */
fun String.toStationNameCase(): String {
    if (this.isBlank()) return this
    
    // Handle special compound station names
    val formatted = when (this.uppercase(Locale.getDefault())) {
        "JAKARTAKOTA" -> "Jakarta Kota"
        "KAMPUNGBANDAN" -> "Kampung Bandan" 
        "PONDOKRANJI" -> "Pondok Ranji"
        "PONDOKCINA" -> "Pondok Cina"
        "PONDOKJATI" -> "Pondok Jati"
        "PARUNGPANJANG" -> "Parung Panjang"
        "LENTENGAGUNG" -> "Lenteng Agung"
        "DURENKALIBATA" -> "Duren Kalibata"
        "BANDARASOEKARNOHATTA" -> "Bandara Soekarno Hatta"
        "BEKASITIMUR" -> "Bekasi Timur"
        "BOJONGINDAH" -> "Bojong Indah"
        "BOJONGGEDE" -> "Bojong Gede"
        "CIBINONG" -> "Cibinong"
        "CIKARANG" -> "Cikarang"
        "CITAYAM" -> "Citayam"
        "DEPOKBARU" -> "Depok Baru"
        "GANGSENTIONG" -> "Gang Sentiong"
        "JATINEGARA" -> "Jatinegara"
        "JURANGMANGU" -> "Jurang Mangu"
        "KLENDERBARU" -> "Klender Baru"
        "MANGGABESAR" -> "Mangga Besar"
        "PASARSENEN" -> "Pasar Senen"
        else -> this.toSentenceCase()
    }
    
    return formatted
}