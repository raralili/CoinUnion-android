package com.rius.coinunion.utils

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream

object ApiUtils {

    /**
     * 解压消息
     *
     */
    fun decompress(depressData: ByteArray): ByteArray {
        val inputStream = ByteArrayInputStream(depressData)
        val outputStream = ByteArrayOutputStream()

        val gInputStream = GZIPInputStream(inputStream)

        var count: Int
        val buffer = ByteArray(1024)
        count = gInputStream.read(buffer, 0, 1024)
        while (count != -1) {
            outputStream.write(buffer, 0, count)
            count = gInputStream.read(buffer, 0, 1024)
        }
        gInputStream.close()
        val result = outputStream.toByteArray()
        outputStream.flush()
        outputStream.close()
        inputStream.close()
        return result
    }
}