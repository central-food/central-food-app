package br.com.fomezero.joaofood.modules.img.domain.model

import java.lang.Exception


sealed class ImgResult {
    data class Success(val reponse: ImgurUploadJson?): ImgResult()
    data class Error(val exception: Exception, val additionalInfo: String? = ""): ImgResult()
}
