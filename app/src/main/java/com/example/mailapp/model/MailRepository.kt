package com.example.mailapp.model

import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlin.random.Random

class MailRepository {

    private val mails = ArrayList<MailModel>()
    private val senders = arrayOf(
        "Peter Parker",
        "제임스",
        "Tony",
        "Sam",
        "John",
        "철수",
        "영희"
    )
    private val titles = arrayOf(
        "보안알림",
        "Let's Connect!",
        "안녕하세요",
        "반갑습니다",
        "누구세요",
        "초대장",
        "회의 안내"
    )
    private val contents = arrayOf(
        "보안내용",
        "Connect start",
        "안녕하세요 반갑습니다. 저는 누구세요? 안녕하세요 반갑습니다",
        "반갑습니다 안녕하세요. 저는 누구세요? 반갑습니다 안녕하세요",
        "누구세요? 일단 반갑습니다 안녕하세요",
        "초대장: 파티에 초대되었습니다. 파티에 참석해서 즐거운 시간을 보내세요",
        "회의 안내: 회의에 참석할 시간입니다"
    )

    init {
        makeDummyData()
    }

    fun fetchMailList(type: MailModel.MailType, completion: (List<MailModel>)->(Unit)){
        Thread {
            Thread.sleep(200)
            val list = mails.filter { it.type == type }
            Handler(Looper.getMainLooper()).post {
                completion(list)
            }
        }.start()
    }

    private fun makeDummyData(){
        for(i in 0 until 100){
            val idx = Random.nextInt(senders.size)
            mails.add(
                MailModel(
                    senders[idx] + "$i",
                    titles[idx],
                    contents[idx],
                    "$i$i::$idx$idx",
                    MailModel.MailType.values()[Random.nextInt(MailModel.MailType.values().size*3)/3]
                )
            )
        }
        Log.d("TAG", "mail size => ${mails.size}")
    }

}