package br.com.fomezero.joaofood.modules.img.data.repository

import android.graphics.Bitmap
import br.com.fomezero.joaofood.modules.img.data.repository.imgur.ImgurApiHelper
import br.com.fomezero.joaofood.modules.img.domain.model.ImgResult
import br.com.fomezero.joaofood.modules.img.domain.model.ImgurUploadJson
import okhttp3.MultipartBody
import java.io.File
import java.io.FileOutputStream
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ImageRepository(private val imgurApiHelper: ImgurApiHelper = ImgurApiHelper()) {

    suspend fun uploadImage(bitmap: Bitmap?, title: String? = null): ImgResult {
        if (bitmap == null) {
            return ImgResult.Error(NullPointerException())
        }

        return try {
            val file = copyBitmapToFile(bitmap)

            val filePart =
                MultipartBody.Part.createFormData("image", file.name, file.asRequestBody())

            val response = imgurApiHelper.uploadImage(
                filePart,
                name = title?.toRequestBody() ?: file.name.toRequestBody()
            )

            if (response.isSuccessful) {
                ImgResult.Success(response.body()!!)
            } else {
                ImgResult.Error(Exception("Unknown network Exception."))
            }

        } catch (e: Exception) {
            ImgResult.Error(e, "")
        }
    }

    private fun copyBitmapToFile(bitmap: Bitmap?): File {
        val outputFile = File.createTempFile("temp", null)
        val outputStream = FileOutputStream(outputFile)
        bitmap?.compress(Bitmap.CompressFormat.PNG, 70, outputStream)
        return outputFile
    }
}