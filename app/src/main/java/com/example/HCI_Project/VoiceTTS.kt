package com.example.HCI_Project

import android.content.Context
import android.util.Log
import com.baidu.tts.client.SpeechError
import com.baidu.tts.client.SpeechSynthesizer
import com.baidu.tts.client.SpeechSynthesizerListener
import com.baidu.tts.client.TtsMode


object VoiceTTS : SpeechSynthesizerListener {
    private var TAG:String = VoiceTTS::class.java.simpleName
    private const val APP_ID="26255916"
    private const val API_KEY="G5xp7rwAxUpTmWuj45dcYcdj"
    private const val SECRET_KEY="32ZImyYx5xNu7frVbBxBHi0iKWgoOkRW"
    //TTS对象
    private lateinit var mSpeechSynthesizer:SpeechSynthesizer
    //存放回答内容
    public var responce_list= ArrayList<String>()
    //初始化TTS
    fun initTTS(mContext:Context){
        mSpeechSynthesizer = SpeechSynthesizer.getInstance()
        //设置上下文
        mSpeechSynthesizer.setContext(mContext)
        //设置key
        mSpeechSynthesizer.setAppId(APP_ID)
        mSpeechSynthesizer.setApiKey(API_KEY, SECRET_KEY)
        mSpeechSynthesizer.setSpeechSynthesizerListener(this)
        mSpeechSynthesizer.initTts(TtsMode.ONLINE)

        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "4")


    }
    //开始播放，保存回答内容
    fun start(text:String){
        responce_list.add(text)
        mSpeechSynthesizer.speak(text)
    }

    fun pause(){
        mSpeechSynthesizer.pause()
    }

    fun resume(){
        mSpeechSynthesizer.resume()
    }
    //停止播放
    fun stop(){
        mSpeechSynthesizer.stop()
    }

    fun release(){
        mSpeechSynthesizer.release()
    }

    override fun onSynthesizeStart(p0: String?) {
        Log.i(TAG,"合成开始")
    }

    override fun onSynthesizeDataArrived(p0: String?, p1: ByteArray?, p2: Int, p3: Int) {
    }

    override fun onSynthesizeFinish(p0: String?) {
        Log.i(TAG,"合成结束")
    }

    override fun onSpeechStart(p0: String?) {
        Log.i(TAG,"播放开始")
    }

    override fun onSpeechProgressChanged(p0: String?, p1: Int) {
    }

    override fun onSpeechFinish(p0: String?) {
        Log.i(TAG,"播放结束")
    }

    override fun onError(p0: String?, p1: SpeechError?) {
        Log.i(TAG, "出错$p0$p1")
    }
}


