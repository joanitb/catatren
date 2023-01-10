import com.example.mytodolist.retrofit.ApiInterface
import com.example.recyclerviewtest.DatosEstacion
import retrofit2.Response

class Repository {
    private val apiInterface = ApiInterface.create()
    suspend fun fetchData(url: String): Response<List<DatosEstacion>> {
        return apiInterface.getFromAPI(url)
    }
}