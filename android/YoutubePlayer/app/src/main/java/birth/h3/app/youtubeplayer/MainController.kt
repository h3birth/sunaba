package birth.h3.app.youtubeplayer

import birth.h3.app.youtubeplayer.model.Item
import com.airbnb.epoxy.TypedEpoxyController

class MainController(val callback: ClickLister) : TypedEpoxyController<List<Item>>() {
    interface ClickLister {
        fun onItemClickListener(item: Item)
    }
    override fun buildModels(data: List<Item>) {
        data.forEach {
            ItemYoutubeDataBindingModel_()
                .item(it)
                .clickLister { _, _, _, _ ->
                    callback.onItemClickListener(it)
                }
                .id(modelCountBuiltSoFar)
                .addTo(this)
        }
    }
}
