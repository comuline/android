package com.comuline.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.comuline.app.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Roboto")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)

val fontNameMono = GoogleFont("Roboto Mono", true)

val fontFamilyMono = FontFamily(
    Font(googleFont = fontNameMono, fontProvider = provider)
)


val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp/*...*/
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp,
        fontSize = 12.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold

    ),

    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamilyMono,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),

    titleSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = fontFamilyMono,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamilyMono,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamilyMono,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    )
)