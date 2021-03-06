package com.example.HCI_Project

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.google.gson.Gson

class AsrViewModel(context: Context) : ViewModel(), EventListener {

    // 能否在此注入全局的context 从而将其变为单例
    private var asr: EventManager = EventManagerFactory.create(context, "asr")


    private val _text = MutableLiveData<String>()
    val text = _text

    private val a = MutableLiveData<String>()

    init {
        asr.registerListener(this)
    }


    fun start() {
        asr.send(SpeechConstant.ASR_START, "{}", null, 0, 0)
    }

    fun stop() {
        asr.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0)
    }

    override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {
        println("rec")
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            // 识别相关的结果都在这里
            if (params == null || params.isEmpty()) {
                return
            }
            if (params.contains("\"final_result\"")) {
                // 一句话的最终识别结果
                _text.value = params
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        //发送取消事件
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        //退出事件管理器
        // 必须与registerListener成对出现，否则可能造成内存泄露
        asr.unregisterListener(this);
    }

//    override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {
//        if (name == SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL) {
//            // 识别相关的结果都在这里
//            if (params == null || params.isEmpty()) {
//                return
//            }
//            if (params.contains("\"final_result\"")) {
//                // 一句话的最终识别结果
//                Log.i("ars.event", params)
//                val gson = Gson()
//                val asRresponse: AsrResponse = gson.fromJson(params, AsrResponse::class.java)
//                if (asRresponse == null) return //数据解析转实体bean
//                //从日志中，得出Best_result的值才是需要的，但是后面跟了一个中文输入法下的逗号，
//                if (asRresponse.getBest_result()
//                        .contains("，")
//                ) { //包含逗号  则将逗号替换为空格，这个地方还会问题，还可以进一步做出来，你知道吗？
//                    _text.value = asRresponse.getBest_result().replace('，', ' ').trim()
//                //替换为空格之后，通过trim去掉字符串的首尾空格
//                } else { //不包含
//                    _text.value = asRresponse.getBest_result().trim()
//                }
//            }
//        }
//    }


}