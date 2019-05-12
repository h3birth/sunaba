package com.example.pagingepoxysample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.example.pagingepoxysample.model.Item

class MainActivity : AppCompatActivity(), MainController.ClickLister {
    private lateinit var viewModel: MainViewModel
    private val controller by lazy { MainController(this, viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


    }

    override fun onItemClickListener(item: Item) {

    }

    override fun onBindLoading(view: DataBindingEpoxyModel.DataBindingHolder) {
        view.dataBinding.lifecycleOwner = this
    }

}
