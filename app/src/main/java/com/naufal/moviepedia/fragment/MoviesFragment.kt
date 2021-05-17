package com.naufal.moviepedia.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.moviepedia.adapter.MovieAdapter
import com.naufal.moviepedia.databinding.FragmentMoviesBinding
import com.naufal.moviepedia.viewmodel.MovieViewModel
import com.naufal.moviepedia.viewmodel.ViewModelFactory
import com.naufal.moviepedia.vo.Status

class MoviesFragment : Fragment() {

    private var binding : FragmentMoviesBinding? = null
    private val adapterMovies = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(context)
        val mMovieViewModel by lazy {
            ViewModelProvider(this, factory).get(MovieViewModel::class.java)
        }

        mMovieViewModel.getMovies(context).observe(viewLifecycleOwner, { list ->
            if(list != null){
                when(list.status) {
                    Status.SUCCESS -> {
                        list.data?.let { adapterMovies.setMovies(it) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        showRecyclerView()
    }

    private fun showRecyclerView() {
        binding?.rvMovies?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = adapterMovies
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}