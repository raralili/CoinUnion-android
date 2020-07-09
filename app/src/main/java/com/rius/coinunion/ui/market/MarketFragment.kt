package com.rius.coinunion.ui.market

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.rius.coinunion.R
import com.rius.coinunion.injector.Injectable
import com.vinsonguo.klinelib.model.HisData
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.market_fragment.*
import java.util.*
import javax.inject.Inject

class MarketFragment : Fragment(), Injectable {

    companion object {
        fun newInstance() = MarketFragment()
    }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MarketViewModel by viewModels { viewModelFactory }

    private lateinit var coinName: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        coinName = requireArguments().getString("name")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.market_fragment, container, false)
        return root
    }

    val data = mutableListOf<HisData>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.onSocketData { info ->
            if (kLine.lastData == null) {
//                val hisData = HisData(
//                    info.open.toDouble(),
//                    info.close.toDouble(),
//                    info.high.toDouble(),
//                    info.low.toDouble(),
//                    info.vol.toInt(),
//                    info.id
//                )
//                data.add(hisData)
//                kLine.initData(data)
            } else {
                if (kLine.lastData.date == info.id) {
                    kLine.refreshData(info.close)
                } else {
//                    kLine.addData(
//                        HisData(
//                            info.open.toDouble(),
//                            info.close.toDouble(),
//                            info.high.toDouble(),
//                            info.low.toDouble(),
//                            info.vol.toInt(),
//                            info.id
//                        )
//                    )
                }
            }
        }

        viewModel.onConnected { webSocket ->
            val gson = Gson()
            val params = hashMapOf<String, String>()
            params["sub"] = "market.${coinName.toLowerCase(Locale.getDefault())}usdt.kline.1min"
            params["id"] = "rius"
            val paramsJson = gson.toJson(params)
            webSocket.send(paramsJson)
        }

        disposable.add(viewModel.httpGetKlineHistory("${coinName.toLowerCase(Locale.getDefault())}usdt",
            "1min")
            .subscribe { result ->
                val list = result.data
                list.mapTo(data, { info ->
                    HisData(
                        info.open.toDouble(),
                        info.close.toDouble(),
                        info.high.toDouble(),
                        info.low.toDouble(),
                        info.vol.toInt(),
                        info.id
                    )
                })
                kLine.initData(data)
            })
    }

    override fun onStart() {
        super.onStart()
        viewModel.connectWebSocket()
    }

    override fun onStop() {
        super.onStop()
        viewModel.disconnectWebSocket()
    }

}
