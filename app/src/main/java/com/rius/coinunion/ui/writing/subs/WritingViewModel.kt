package com.rius.coinunion.ui.writing.subs

import androidx.lifecycle.ViewModel
import com.rius.coinunion.entity.writing.WritingInfo
import javax.inject.Inject

class WritingViewModel @Inject constructor() : ViewModel() {

    fun getListData(type: Int): MutableList<WritingInfo> {
        val res = mutableListOf<WritingInfo>()
        for (i in 0 until 10) {
            when (type) {
                WRITING_TYPE_RECOMMEND -> {
                    res.add(
                        WritingInfo(
                            "强势突破：ETH突破两月之久震荡平台，即将迎来大涨?",
                            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595509538282&di=ab9330206de0aaeda44b5ceaf2089c32&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171220%2Fd980699224324e72ba2b474053c92976.jpeg"
                        )
                    )
                }
                WRITING_TYPE_LATEST -> {
                    res.add(
                        WritingInfo(
                            "突破上方重要阻力：BTC指日可待",
                            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595566223025&di=7085456743bbb874f7dc4ee0d625997c&imgtype=0&src=http%3A%2F%2Fwx3.sinaimg.cn%2Forj360%2F005BZWYVgy1gh0zr88glhj30nt0d0wi9.jpg"
                        )
                    )
                }
                WRITING_TYPE_STAR -> {
                    res.add(
                        WritingInfo(
                            "弱势？：BCH长期低位箱体震荡，看超长期吸筹，近期若突破平台中轨，则上行可期！",
                            "https://timgsa.baidu.com/https://timgsa.baidu.com/https://timgsa.baidu.com/https://timgsa.baidu.com/https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595566459634&di=1da217bffabef1ce0e3c1d0e0c83ed7e&imgtype=0&src=http%3A%2F%2Fpics4.baidu.com%2Ffeed%2Fb8389b504fc2d562a0839350f0d344e974c66cc8.jpeg%3Ftoken%3Db9324f93242e4c3d760fb12b9ac8d5e6"
                        )
                    )
                }
            }
        }
        return res
    }

}
