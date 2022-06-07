package com.felix.movielist

import android.app.ProgressDialog
import android.content.Intent
import com.felix.movielist.network.Status
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.compose.ui.layout.ContentScale
import coil.imageLoader
import coil.load
import com.felix.movielist.databinding.ActivityDetailBinding
import com.felix.movielist.view.MainPageActivity
import com.felix.movielist.viewModel.MovieDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"
    private val viewModel: MovieDetailViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("id",0)
        val progressDialog = ProgressDialog(this)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, MainPageActivity::class.java))
        }

        viewModel.getAllDetailMovies(movieId)

        viewModel.detailMovie.observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    progressDialog.setMessage("Loading...")
                    progressDialog.show()
                }
                Status.SUCCESS -> {
                    binding.tvDetailTitle.text = resource.data?.title
                    binding.tvDescDetail.text = resource.data?.overview

                    binding.ivBackdrop.load(IMAGE_BASE+resource.data?.backdropPath)
                    binding.ivPoster.load(IMAGE_BASE+resource.data?.posterPath)
                    progressDialog.dismiss()
                }
                Status.ERROR -> {
                    Toast.makeText(this, "Error get Data : ${resource.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}