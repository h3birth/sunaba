package birth.h3.app.sunaba.epoxysample

import birth.h3.app.sunaba.epoxysample.model.AACItem
import birth.h3.app.sunaba.epoxysample.model.AACList
import com.airbnb.epoxy.TypedEpoxyController

class AACListController(val callback: ClickListener): TypedEpoxyController<AACList>() {

    interface ClickListener {
        fun itemClickListener(item: AACItem)
    }

    override fun buildModels(data: AACList) {
        data.aacItems.forEach {
            ItemAacBindingModel_()
                .item(it)
                .itemClickListener { _, _, _, _ ->
                    callback.itemClickListener(it)
                }
                .id(modelCountBuiltSoFar)
                .addTo(this)
        }
    }
}
