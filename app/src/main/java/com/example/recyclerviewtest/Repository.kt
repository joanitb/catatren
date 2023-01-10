class Repository {
    private val apiInterface = apiInterface.create()
    suspend fun fetchData(url: String): Response<List<DatosEstacion>> {
        val response = apiInterface.getFromAPI(url)
        return response
    }
}