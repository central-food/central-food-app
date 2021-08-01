package br.com.fomezero.joaofood.modules.img.data.repository.imgur

import br.com.fomezero.joaofood.modules.img.domain.model.ImgurUploadJson
import br.com.fomezero.joaofood.util.RetrofitUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class ImgurApiHelper {
    suspend fun uploadImage(image: MultipartBody.Part?, name: RequestBody? = null): Response<ImgurUploadJson> {
        return RetrofitUtil
            .getApiService(ImgurApi.BASE_URL, ImgurApi::class.java)
            .uploadFile(image, name)
    }
}