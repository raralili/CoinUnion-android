package com.rius.coinunion.ui.home.market

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.rius.coinunion.R
import com.rius.coinunion.entity.market.socket.KLineInfo
import com.rius.coinunion.injector.Injectable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.market_fragment.*
import java.text.SimpleDateFormat
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

    private val candleSet = CandleDataSet(arrayListOf<CandleEntry>(), "CANDLE-STICK")
    private val ma5Set = LineDataSet(arrayListOf<Entry>(), "MA5")
    private val ma10Set = LineDataSet(arrayListOf<Entry>(), "MA10")
    private val ma30Set = LineDataSet(arrayListOf<Entry>(), "MA30")
    private val tradeSet = BarDataSet(arrayListOf<BarEntry>(), "trade")

    private val xValues = mutableMapOf<Int, String>()

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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initChart()
    }

    private fun initChart() {
        val black = resources.getColor(R.color.colorPrimaryDark, requireContext().theme)

        with(combinedChart) {
            //            this.setNoDataTextColor()
            this.description = null
            this.legend.isEnabled = false
            this.isDragDecelerationEnabled = false
            this.minOffset = 0f
            this.extraBottomOffset = 6f
            this.isScaleXEnabled = true
            this.isScaleYEnabled = false
            this.isAutoScaleMinMaxEnabled = true
//            this.setBackgroundColor(resources.getColor(R.color.colorKChartBgr,requireContext().theme))
//            this.drawOrder = arrayOf(CombinedChart.DrawOrder.CANDLE)
        }

        //X
        with(combinedChart.xAxis) {
            this.position = XAxis.XAxisPosition.BOTTOM
            this.gridColor = black
            this.textSize = 8f
            this.textColor = black
            this.axisLineColor = black
            this.disableAxisLineDashedLine()
            this.setAvoidFirstLastClipping(true)
            this.setLabelCount(2, true)
        }

        //left-y
        with(combinedChart.axisLeft) {
            this.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
            this.gridColor = black
            this.textColor = black
            this.textSize = 8f
            this.setLabelCount(5, true)
            this.enableGridDashedLine(5f, 4f, 0f)
//            this.valueFormatter = DefaultAxisValueFormatter(2)
        }

        //right-y
        with(combinedChart.axisRight) {
            this.isEnabled = false
        }

        with(trade_chart) {
            this.description = null
            this.legend.isEnabled = false
            this.isDragDecelerationEnabled = false
            this.setScaleEnabled(false)
            this.isAutoScaleMinMaxEnabled = true

            this.xAxis.isEnabled = false

            this.axisRight.isEnabled = false
        }

        with(trade_chart.axisLeft) {
            this.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
            this.setDrawAxisLine(false)
            this.gridColor = Color.BLACK
            this.textColor = Color.GRAY
            this.textSize = 8f
            this.setLabelCount(2, true)
            this.axisMaximum = 0f
        }


        //candle
        with(candleSet) {
            this.axisDependency = YAxis.AxisDependency.LEFT
            this.setDrawHorizontalHighlightIndicator(false)
            this.highlightLineWidth = 0.5f
            this.highLightColor = resources.getColor(R.color.colorAccent, requireContext().theme)
            this.shadowWidth = 0.7f
            this.increasingColor = resources.getColor(R.color.colorIncrease, requireContext().theme)
            this.increasingPaintStyle = Paint.Style.STROKE
            this.decreasingColor = resources.getColor(R.color.colorDecrease, requireContext().theme)
            this.decreasingPaintStyle = Paint.Style.FILL
            this.neutralColor = resources.getColor(R.color.colorIncrease, requireContext().theme)
            this.shadowColorSameAsCandle = true
            this.setDrawValues(false)
            this.isHighlightEnabled = false
        }

        //ma5
        with(ma5Set) {
            this.axisDependency = YAxis.AxisDependency.LEFT
            this.color = Color.BLACK
            this.setDrawCircles(false)
            this.setDrawValues(false)
            this.isHighlightEnabled = false
        }

        with(ma10Set) {
            this.axisDependency = YAxis.AxisDependency.LEFT
            this.color = Color.BLUE
            this.setDrawCircles(false)
            this.setDrawValues(false)
            this.isHighlightEnabled = false
        }

        with(ma30Set) {
            this.axisDependency = YAxis.AxisDependency.LEFT
            this.color = Color.RED
            this.setDrawCircles(false)
            this.setDrawValues(false)
            this.isHighlightEnabled = false
        }

        with(tradeSet) {
            this.color = Color.BLUE
            this.setDrawValues(false)
            this.isHighlightEnabled = false
        }

        viewModel.onConnected { webSocket ->
            //            val gson = Gson()
//            val params = hashMapOf<String, String>()
//            params["sub"] = "market.${coinName.toLowerCase(Locale.getDefault())}usdt.kline.1min"
//            params["id"] = "rius"
//            val paramsJson = gson.toJson(params)
//            webSocket.send(paramsJson)
        }

        disposable.add(viewModel.httpGetKlineHistory("ethusdt", "1day").subscribe { result ->
            val list = result.data.reversed()

            xValues.clear()


            val combinedData = CombinedData()


            val candleValues = candleSet.values
            val ma5Values = ma5Set.values
            val ma10Values = ma10Set.values
            val ma30Values = ma30Set.values
            val tradeValues = tradeSet.values

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

            for (i in list.indices) {
                val element = list[i]

                val date = Date(element.id)
                xValues[i] = sdf.format(date)

                val index = i.toFloat()
                candleValues.add(
                    CandleEntry(
                        index,
                        element.high,
                        element.low,
                        element.open,
                        element.close
                    )
                )

                if (i >= 5) {
                    ma5Values.add(Entry(index, calculateMA(list, i, 5)))
                }
                if (i >= 10) {
                    ma10Values.add(Entry(index, calculateMA(list, i, 10)))
                }
                if (i >= 30) {
                    ma30Values.add(Entry(index, calculateMA(list, i, 30)))
                }

                tradeValues.add(BarEntry(index, element.vol))
            }

            candleSet.values = candleValues
            ma5Set.values = ma5Values
            ma10Set.values = ma10Values
            ma30Set.values = ma30Values
            tradeSet.values = tradeValues

            combinedData.setData(CandleData(candleSet))
            combinedData.setData(LineData(ma5Set, ma10Set, ma30Set))

            combinedChart.data = combinedData
            combinedChart.invalidate()

            val tradeCombinedData = CombinedData()
            tradeCombinedData.setData(BarData(tradeSet))

            trade_chart.data = tradeCombinedData
            trade_chart.invalidate()
        })
    }

    fun getColorById(@ColorRes id: Int): Int {
        return resources.getColor(id, requireContext().theme)
    }

    fun calculateMA(src: List<KLineInfo>, index: Int, _MAParameter: Int): Float {
        var i = index
        var count = 1
        var sum = src[i].close
        while (count < _MAParameter) {
            if (--i < 0) break
            sum += src[i].close
            count++
        }
        return sum / count
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
