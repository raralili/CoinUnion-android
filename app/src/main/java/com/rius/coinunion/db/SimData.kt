package com.rius.coinunion.db

import com.rius.coinunion.entity.user.UserInfo

class SimData {

    val adminUser = UserInfo(
        name = "波多野鸡",
        sign = "人生如梦，做做就过去了",
        avatar = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595848272663&di=a58f6c59f59b2eb40ae261cce2a7242d&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201410%2F09%2F20141009224754_AswrQ.jpeg",
        fans = listOf(),
        writings = listOf(),
        privateInfo = UserInfo.Private("admin_1"),
        stars = listOf(
            UserInfo(
                name = "巴菲特",
                sign = "我是巴菲特，我最爱的是别人请我吃的百万大餐！",
                avatar = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=529336021,1817113508&fm=26&gp=0.jpg",
                fans = listOf(),
                writings = listOf(),
                stars = listOf(),
                privateInfo = UserInfo.Private(uid = "1")
            ),
            UserInfo(
                name = "格雷厄姆",
                sign = "我是格雷厄姆，别人都说我是巴菲特的老师",
                avatar = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596022666023&di=f255a11e3b711b75b47dc33bf7e98d47&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171106%2F896d670cd18b409c9fcc4a877f487907.png",
                fans = listOf(),
                writings = listOf(),
                privateInfo = UserInfo.Private(uid = "2"),
                stars = listOf()
            ),
            UserInfo(
                name = "徐明星",
                sign = "我是徐明星，我。。。",
                avatar = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2368728627,3384649836&fm=26&gp=0.jpg",
                fans = listOf(),
                writings = listOf(),
                privateInfo = UserInfo.Private(uid = "3"),
                stars = listOf()
            ),
            UserInfo(
                name = "孙宇晨",
                sign = "我是孙宇晨，一名年轻的投资家和狗逼",
                avatar = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596022784838&di=cc69d8d72999d88a7c59bef0938b32a0&imgtype=0&src=http%3A%2F%2Fdingyue.ws.126.net%2FDeZXKwcIRRAb1zE4xGNi9Y7n8JNfbuJl6a9WIzVPYWjnt1559824727865.jpg",
                fans = listOf(),
                writings = listOf(),
                privateInfo = UserInfo.Private("4"),
                stars = listOf()
            )
        )
    )

    val users = listOf(
        UserInfo(
            name = "巴菲特",
            sign = "我是巴菲特，我最爱的是别人请我吃的百万大餐！",
            avatar = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=529336021,1817113508&fm=26&gp=0.jpg",
            fans = listOf(adminUser),
            writings = listOf(),
            stars = listOf(),
            privateInfo = UserInfo.Private(uid = "1")
        ),
        UserInfo(
            name = "格雷厄姆",
            sign = "我是格雷厄姆，别人都说我是巴菲特的老师",
            avatar = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596022666023&di=f255a11e3b711b75b47dc33bf7e98d47&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171106%2F896d670cd18b409c9fcc4a877f487907.png",
            fans = listOf(adminUser),
            writings = listOf(),
            privateInfo = UserInfo.Private(uid = "2"),
            stars = listOf()
        ),
        UserInfo(
            name = "徐明星",
            sign = "我是徐明星，我。。。",
            avatar = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2368728627,3384649836&fm=26&gp=0.jpg",
            fans = listOf(adminUser),
            writings = listOf(),
            privateInfo = UserInfo.Private(uid = "3"),
            stars = listOf()
        ),
        UserInfo(
            name = "孙宇晨",
            sign = "我是孙宇晨，一名年轻的投资家和狗逼",
            avatar = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596022784838&di=cc69d8d72999d88a7c59bef0938b32a0&imgtype=0&src=http%3A%2F%2Fdingyue.ws.126.net%2FDeZXKwcIRRAb1zE4xGNi9Y7n8JNfbuJl6a9WIzVPYWjnt1559824727865.jpg",
            fans = listOf(adminUser),
            writings = listOf(),
            privateInfo = UserInfo.Private("4"),
            stars = listOf()
        )
    )
}