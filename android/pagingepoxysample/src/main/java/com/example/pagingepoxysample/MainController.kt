package com.example.pagingepoxysample

import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.pagingepoxysample.model.Item

class MainController(val callback: ClickLister, val viewModel: MainViewModel): PagedListEpoxyController<Item>() {
    interface ClickLister{
        fun onItemClickListener(item:Item)
        fun onBindLoading(view: DataBindingEpoxyModel.DataBindingHolder)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)

        ItemLoadingBindingModel_()
            .viewModel(viewModel)
            .onBind { _, view, _ ->
                callback.onBindLoading(view)
            }
            .id("loding")
            .addTo(this)
    }

    override fun buildItemModel(currentPosition: Int, item: Item?): EpoxyModel<*> {
        return if (item == null) {
            object : EpoxyModel<Any>() {
                override fun getDefaultLayout(): Int {
                    return 0
                }
            }
        } else {
            ItemYoutubeDataBindingModel_()
                .item(item)
                .clickLister { _, _, _, _ ->
                    callback.onItemClickListener(item)
                }
                .id(item.id.videoId)
        }
    }

}
