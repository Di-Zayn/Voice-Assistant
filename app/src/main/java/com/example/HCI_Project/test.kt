//package com.example.HCI_Project
//
//import android.util.Log
//import com.baidu.speech.asr.SpeechConstant
//
//class MainActivity : AppCompatActivity(), EventListener {
//    protected var txtResult //识别结果
//            : TextView? = null
//    protected var startBtn //开始识别  一直不说话会自动停止，需要再次打开
//            : Button? = null
//    protected var stopBtn //停止识别
//            : Button? = null
//    private var asr //语音识别核心库
//            : EventManager? = null
//
//    protected fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        initView()
//        initPermission()
//
//        //初始化EventManager对象
//        asr = EventManagerFactory.create(this, "asr")
//        //注册自己的输出事件类
//        asr.registerListener(this) //  EventListener 中 onEvent方法
//    }
//
//    /**
//     * 初始化控件
//     */
//    private fun initView() {
//        txtResult = findViewById(R.id.tv_txt) as TextView?
//        startBtn = findViewById(R.id.btn_start) as Button?
//        stopBtn = findViewById(R.id.btn_stop) as Button?
//        startBtn.setOnClickListener(object : OnClickListener() {
//            //开始
//            fun onClick(v: View?) {
//                asr.send(SpeechConstant.ASR_START, "{}", null, 0, 0)
//            }
//        })
//        stopBtn.setOnClickListener(object : OnClickListener() {
//            //停止
//            fun onClick(v: View?) {
//                asr.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0)
//            }
//        })
//    }
//
//    /**
//     * android 6.0 以上需要动态申请权限
//     */
//    private fun initPermission() {
//        val permissions = arrayOf<String>(
//            Manifest.permission.RECORD_AUDIO,
//            Manifest.permission.ACCESS_NETWORK_STATE,
//            Manifest.permission.INTERNET,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//        val toApplyList = ArrayList<String>()
//        for (perm in permissions) {
//            if (PackageManager.PERMISSION_GRANTED !== ContextCompat.checkSelfPermission(
//                    this,
//                    perm
//                )
//            ) {
//                toApplyList.add(perm)
//                // 进入到这里代表没有权限.
//                Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show()
//            }
//        }
//        val tmpList = arrayOfNulls<String>(toApplyList.size())
//        if (!toApplyList.isEmpty()) {
//            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123)
//        }
//    }
//
//    /**
//     * 权限申请回调，可以作进一步处理
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>?,
//        grantResults: IntArray?
//    ) {
//        // 此处为android 6.0以上动态授权的回调，用户自行实现。
//    }
//
//    /**
//     * 自定义输出事件类 EventListener 回调方法
//     */
//    fun onEvent(name: String, params: String?, data: ByteArray?, offset: Int, length: Int) {
//        if (name == SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL) {
//            // 识别相关的结果都在这里
//            if (params == null || params.isEmpty()) {
//                return
//            }
//            if (params.contains("\"final_result\"")) {
//                // 一句话的最终识别结果
//                Log.i("ars.event", params)
//                val gson = Gson()
//                val asRresponse: ASRresponse = gson.fromJson(params, ASRresponse::class.java)
//                    ?: return //数据解析转实体bean
//                //从日志中，得出Best_result的值才是需要的，但是后面跟了一个中文输入法下的逗号，
//                if (asRresponse.getBest_result()
//                        .contains("，")
//                ) { //包含逗号  则将逗号替换为空格，这个地方还会问题，还可以进一步做出来，你知道吗？
//                    txtResult.setText(
//                        asRresponse.getBest_result().replace('，', ' ').trim()
//                    ) //替换为空格之后，通过trim去掉字符串的首尾空格
//                } else { //不包含
//                    txtResult.setText(asRresponse.getBest_result().trim())
//                }
//            }
//        }
//    }
//
//    protected fun onDestroy() {
//        super.onDestroy()
//        // 基于SDK集成4.2 发送取消事件
//        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0)
//        // 基于SDK集成5.2 退出事件管理器
//        // 必须与registerListener成对出现，否则可能造成内存泄露
//        asr.unregisterListener(this)
//    }
//}