package com.kiyosuke.corona_grapher.util.color

import android.os.Parcelable
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import kotlin.math.min
import kotlin.math.round
import kotlin.math.roundToInt

@Parcelize
data class Color(val color: Int) : Serializable, Parcelable {

    /**
     * 16進数のカラーコードを受け付けます. ex) #FF0000
     */
    constructor(colorString: String) : this(parseHexColor(colorString))

    /**
     * argbから色を生成します
     */
    constructor(
        @IntRange(from = 0, to = 255) alpha: Int,
        @IntRange(from = 0, to = 255) red: Int,
        @IntRange(from = 0, to = 255) green: Int,
        @IntRange(from = 0, to = 255) blue: Int
    ) : this(argbToColor(alpha, red, green, blue))

    /**
     * rgbから色を生成します
     */
    constructor(
        @IntRange(from = 0, to = 255) red: Int,
        @IntRange(from = 0, to = 255) green: Int,
        @IntRange(from = 0, to = 255) blue: Int
    ) : this(rgbToColor(red, green, blue))

    val alpha: Int get() = color ushr 24
    val red: Int get() = (color shr 16) and 0xFF
    val green: Int get() = (color shr 8) and 0xFF
    val blue: Int get() = color and 0xFF

    /**
     * この色より暗いバージョンの色を返却します
     * RGBの値を減少させることで生成されます
     * 新しい色のalpha値は255になります
     */
    val darker: Color
        get() {
            val red = (red * BRIGHT_SCALE).toInt()
            val green = (green * BRIGHT_SCALE).toInt()
            val blue = (blue * BRIGHT_SCALE).toInt()
            return Color(255, red, green, blue)
        }

    /**
     * この色の明るいバージョンを返却します
     * RGBの値を増加させることで生成されます
     * 新しい色のalpha値は255になります
     */
    val brighter: Color
        get() {
            val hues = intArrayOf(red, green, blue)
            if (hues[0] == 0 && hues[1] == 0 && hues[2] == 0) {
                hues[0] = 3
                hues[1] = 3
                hues[2] = 3
            } else {
                for (i in 0 until 3) {
                    if (hues[i] > 2) hues[i] = min(255, (hues[i] / BRIGHT_SCALE).toInt())
                    if (hues[i] == 1 || hues[i] == 2) hues[i] = 4
                }
            }
            return Color(255, hues[0], hues[1], hues[2])
        }

    /**
     * 透明度を追加します
     * @param factor 0.0f -> 完全に透明, 1.0 -> 完全に不透明
     */
    fun addAlpha(@FloatRange(from = 0.0, to = 1.0) factor: Float): Color {
        val alpha = round(alpha * factor).roundToInt()
        return Color(argbToColor(alpha, red, green, blue))
    }

    companion object {
        private const val BRIGHT_SCALE = 0.7f

        /**
         * 色の文字列を解析して対応するカラーの数値を返却します
         * 文字列を解析できない場合は[IllegalArgumentException]を投げます
         * サポート形式は以下の通りです
         * - #RRGGBB
         * - #AARRGGBB
         */
        private fun parseHexColor(colorString: String): Int {
            if (colorString.startsWith('#')) {
                var color = colorString.substring(1).toLong(16)
                if (colorString.length == 7) {
                    color = color or 0x00000000ff000000
                } else if (colorString.length != 9) {
                    throw IllegalArgumentException("Unknown color: $colorString")
                }
                return color.toInt()
            }
            throw IllegalArgumentException("Unknown color")
        }

        /**
         * alpha, red, green, blueの値からカラーの数値を返却します
         * それぞれの値は[0-255]の範囲である必要があります
         */
        private fun argbToColor(
            @IntRange(from = 0, to = 255) alpha: Int,
            @IntRange(from = 0, to = 255) red: Int,
            @IntRange(from = 0, to = 255) green: Int,
            @IntRange(from = 0, to = 255) blue: Int
        ): Int {
            return (alpha shl 24) or (red shl 16) or (green shl 8) or blue
        }

        /**
         * red, green ,blueの値からカラーの数値を返却します
         * alpha値に関しては、255(不透明)です
         * それぞれの値は[0-255]の範囲である必要があります
         */
        private fun rgbToColor(
            @IntRange(from = 0, to = 255) red: Int,
            @IntRange(from = 0, to = 255) green: Int,
            @IntRange(from = 0, to = 255) blue: Int
        ): Int {
            return 0xff000000.toInt() or (red shl 16) or (green shl 8) or blue
        }
    }
}