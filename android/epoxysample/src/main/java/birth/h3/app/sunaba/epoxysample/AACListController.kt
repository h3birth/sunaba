package birth.h3.app.sunaba.epoxysample

import birth.h3.app.sunaba.epoxysample.model.AACList
import com.airbnb.epoxy.TypedEpoxyController

class AACListController: TypedEpoxyController<AACList>() {
    override fun buildModels(data: AACList) {
        data.aacItems.forEach {
            ItemAacBindingModel_()
                .item(it)
                .id(modelCountBuiltSoFar)
                .addTo(this)
        }
    }
}
