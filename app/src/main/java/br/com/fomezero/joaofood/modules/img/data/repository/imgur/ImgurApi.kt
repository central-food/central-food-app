package br.com.fomezero.joaofood.modules.img.data.repository.imgur

import br.com.fomezero.joaofood.modules.img.domain.model.ImgurUploadJson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImgurApi {
    /**
     * An anonymous image upload endpoint:
     * https://apidocs.imgur.com/?version=latest#c85c9dfc-7487-4de2-9ecd-66f727cf3139
     */
    @Multipart
    @POST("/3/upload")
    suspend fun uploadFile(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody? = null
    ): Response<ImgurUploadJson>

    companion object {
        /**
         * Fill in your imgur client id below. Don't have a client id? Get one here: https://api.imgur.com/oauth2/addclient
         */
        const val CLIENT_ID = ""
        const val BASE_URL = "https://api.imgur.com"
    }
}
