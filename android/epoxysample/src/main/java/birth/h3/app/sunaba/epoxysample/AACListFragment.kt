package birth.h3.app.sunaba.epoxysample


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.content.contentValuesOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import birth.h3.app.sunaba.epoxysample.model.AACItem
import birth.h3.app.sunaba.epoxysample.model.AACList
import kotlinx.android.synthetic.main.fragment_aaclist.*


/**
 * A simple [Fragment] subclass.
 *
 */
class AACListFragment : Fragment(), AACListController.ClickListener {
    private val controler by lazy { AACListController(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aaclist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        aac_list.let {
            it.adapter = controler.adapter
            it.layoutManager = LinearLayoutManager(this.activity, RecyclerView.VERTICAL, false)
            it.addItemDecoration(DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL))
        }

        controler.setData(getData())
    }

    private fun getData() = AACList.getList()

    override fun itemClickListener(item: AACItem) =
        CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(ContextCompat.getColor(this.activity!!, R.color.colorPrimary))
            .build().launchUrl(this.activity, Uri.parse(item.url))
}
