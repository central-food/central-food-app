package br.com.fomezero.joaofood.modules.img.domain.api

import android.graphics.Bitmap
import br.com.fomezero.joaofood.modules.img.data.repository.ImageRepository
import br.com.fomezero.joaofood.modules.img.domain.model.ImgResult
import br.com.fomezero.joaofood.modules.img.domain.model.ImgurUploadJson
import kotlinx.coroutines.runBlocking

object UploadImageProvider {

    fun uploadFile(bitmap: Bitmap?, title: String? = null): ImgResult {
        return runBlocking { ImageRepository().uploadImage(bitmap, title) }
    }
}