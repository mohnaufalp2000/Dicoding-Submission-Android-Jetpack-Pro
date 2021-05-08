package com.naufal.moviepedia.remote

import androidx.lifecycle.MutableLiveData
import com.naufal.moviepedia.model.*
import com.naufal.moviepedia.network.ConfigNetwork
import com.naufal.moviepedia.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    private val tv = MutableLiveData<ArrayList<TVItems?>?>()

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }

    fun getMovies(callback: LoadMoviesCallback) {
        ConfigNetwork.getApi().getMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val body = response.body()?.results
                val movieResults = ArrayList<MovieResp>()
                if (body != null) {
                    for (movie in body){
                        val movieResponse = MovieResp(
                            id = movie?.id,
                            title = movie?.title,
                            posterPath = movie?.posterPath,
                            rate = movie?.voteAverage,
                            language = movie?.originalLanguage
                        )
                        movieResults.add(movieResponse)
                    }
                }

                callback.onMoviesReceived(movieResults)

            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            }
        })
    }

    fun getTV(callback: LoadTVCallback) {
        ConfigNetwork.getApi().getShows().enqueue(object : Callback<TVResponse> {
            override fun onResponse(call: Call<TVResponse>, response: Response<TVResponse>) {
                val body = response.body()?.results

                val tvResults = ArrayList<TVResp>()
                if (body != null) {
                    for (tv in body){
                        val tvResponse = TVResp(
                            id = tv?.id,
                            title = tv?.name,
                            posterPath = tv?.posterPath,
                            rate = tv?.voteAverage,
                            language = tv?.originalLanguage
                        )
                        tvResults.add(tvResponse)
                    }
                }

                callback.onTVReceived(tvResults)


            }
            override fun onFailure(call: Call<TVResponse>, t: Throwable) {
            }
        })

    }

    fun getDetailMovie(id: Int, callback: LoadDetailMovieCallback) {
        ConfigNetwork.getApi().getDetailMovie(id).enqueue(object : Callback<DetailMovieResponse>{
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                val body = response.body()
                val genreItems = response.body()?.genres
                val genreResults = ArrayList<MovieGenreResp>()

                if (genreItems != null) {
                    for (item in genreItems){
                        val genreResponse = MovieGenreResp(
                            id = item?.id,
                            name = item?.name
                        )
                        genreResults.add(genreResponse)
                    }
                }

                val detailMovie = DetailMovieResp(
                    id = body?.id,
                    title = body?.title,
                    posterPath = body?.posterPath,
                    rate = body?.voteAverage,
                    language = body?.originalLanguage,
                    overview = body?.overview,
                    runtime = body?.runtime,
                    release = body?.releaseDate,
                    genres = genreResults
                )

                callback.onDetailMovieReceived(detailMovie)
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
            }
        })
    }

    fun getDetailTV(id: Int, callback: LoadDetailTVCallback) {
        ConfigNetwork.getApi().getDetailTV(id).enqueue(object : Callback<DetailTVResponse>{
            override fun onResponse(
                call: Call<DetailTVResponse>,
                response: Response<DetailTVResponse>
            ) {
                val body = response.body()
                val genreItems = response.body()?.genres
                val genreResults = ArrayList<TVGenreResp>()

                if (genreItems != null) {
                    for (item in genreItems){
                        val genreResponse = TVGenreResp(
                            id = item?.id,
                            name = item?.name
                        )
                        genreResults.add(genreResponse)
                    }
                }

                val detailTV = DetailTVResp(
                    id = body?.id,
                    title = body?.name,
                    posterPath = body?.posterPath,
                    rate = body?.voteAverage,
                    language = body?.originalLanguage,
                    overview = body?.overview,
                    runtime = body?.episodeRunTime,
                    released = body?.firstAirDate,
                    genres = genreResults
                )
             callback.onDetailTVReceived(detailTV)
            }

            override fun onFailure(call: Call<DetailTVResponse>, t: Throwable) {
            }
        })
    }

    interface LoadMoviesCallback{
        fun onMoviesReceived(movieResp : ArrayList<MovieResp>)
    }

    interface LoadTVCallback{
        fun onTVReceived(tvResp: ArrayList<TVResp>)
    }

    interface LoadDetailMovieCallback {
        fun onDetailMovieReceived(detailMovieResp : DetailMovieResp)
    }

    interface LoadDetailTVCallback {
        fun onDetailTVReceived(detailTvResp: DetailTVResp)
    }


}
