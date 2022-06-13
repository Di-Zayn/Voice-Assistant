package com.example.HCI_Project

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.baidu.tts.client.SpeechSynthesizer
import com.baidu.tts.client.SpeechSynthesizerListener
import com.baidu.tts.client.TtsMode
import org.json.JSONArray
import org.json.JSONObject


class AsrViewModel(context: Context) : ViewModel(), EventListener {

    // 能否在此注入全局的context 从而将其变为单例
    private var asr: EventManager = EventManagerFactory.create(context, "asr")
    private var pattern = "Chat"

    private val _text = MutableLiveData<String>()
    val text = _text

    private val _text2 = MutableLiveData<String>()
    val text2 = _text2

    private val _code = MutableLiveData("0")
    val code = _code

    private val intentObj = MutableLiveData<String>()

    init {
        asr.registerListener(this)
    }
    fun resetCode() {
        _code.value = "0"
    }
    fun changePattern() {
        if (pattern == "Chat") {
            pattern = "Assistant"
        } else {
            pattern = "Chat"
            _code.value = "0"
        }
    }

    fun getIntentObj(): String? {
        return intentObj.value
    }


    fun start() {
        when(pattern) {
            "Chat" -> {
                startChatBot()
            }
            "Assistant" -> {
                startAssistant()
            }
        }
    }
    private fun startChatBot() {
        print("chat")
        val json =
            "{\"vad\":\"touch\"," +
                    "\"accept-audio-data\":false," +
                    "\"accept-audio-volume\":true," +
                    "\"pid\":15364," +
                    "\"bot_session_list\":[{\"bot_id\":\"1194981\",\"bot_session_id\":\"\"}]}"

        asr.send(SpeechConstant.ASR_START, json, null, 0, 0)
    }

    private fun startAssistant() {
        print("start")
        val json =
            "{\"vad\":\"touch\"," +
                    "\"accept-audio-data\":false," +
                    "\"accept-audio-volume\":true," +
                    "\"pid\":15363}"

        asr.send(SpeechConstant.ASR_START, json, null, 0, 0)
    }

    fun stop() {
        asr.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0)
    }
    override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {

        when (name) {

            "unit.finish" -> {
                Log.i(TAG, "UNIT结果:"+params);
                var res= params!!.split("\"")
                var index=res.indexOf("normalized_word")
                if(res[index+2]=="errno"){
                    VoiceTTS.start("不好意思，我没有听懂")
                    _text2.value="不好意思，我没有听懂"
                }else{
                    VoiceTTS.start(res[index+2])
                    _text2.value=res[index+2]
                }

            }

            SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL -> {

                if (params == null || params.isEmpty()) {
                    return
                }

                if (params.contains("\"final_result\"")) {
                    // 一句话的最终识别结果
                    var (zero, one, two)=JSONObject(params).getString("results_recognition").split("\"")
                    _text.value = one
                    VoiceTTS.responce_list.add("$"+one)

                }

                data?.let {
                    val nlu = String(it, offset, length)
                    Log.i(TAG, "语义解析结果: " + nlu)
                    val res = nlu.split("\"")
                    when (res[res.indexOf("intent") + 2]) {
                        "SEARCH" -> {
                            val obj = res[res.indexOf("word") + 2]
                            intentObj.value = obj.dropLast(1) //去掉。
                            _code.value = "2"
                        }
                        "OPEN_ADDRESS_BOOK" -> {
                            _code.value = "1"
                        }
                        "VIEW_SNS" -> {
                            _code.value = "3"
                        }


                    }

//                    var index=res.indexOf("normalized_word")
//                    _code.value = "2"
                }
            }

            SpeechConstant.CALLBACK_EVENT_ASR_FINISH -> {
                Log.i(TAG, "识别结束: " + params);
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


