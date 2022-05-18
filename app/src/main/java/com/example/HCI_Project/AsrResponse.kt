//package com.example.HCI_Project
//
//
//class AsrResponse {
//    /**
//     * results_recognition : ["你好，"]
//     * result_type : final_result
//     * best_result : 你好，
//     * origin_result : {"asr_align_begin":80,"asr_align_end":130,"corpus_no":6835867007181645805,"err_no":0,"raf":133,"result":{"word":["你好，"]},"sn":"82d975e0-6eb4-43ac-a0e7-850bb149f28e"}
//     * error : 0
//     */
//    var result_type: String? = null
//    var best_result: String? = null
//    var origin_result: OriginResultBean? = null
//    var error = 0
//    var results_recognition: List<String>? = null
//
//    class OriginResultBean {
//        /**
//         * asr_align_begin : 80
//         * asr_align_end : 130
//         * corpus_no : 6835867007181645805
//         * err_no : 0
//         * raf : 133
//         * result : {"word":["你好，"]}
//         * sn : 82d975e0-6eb4-43ac-a0e7-850bb149f28e
//         */
//        var asr_align_begin = 0
//        var asr_align_end = 0
//        var corpus_no: Long = 0
//        var err_no = 0
//        var raf = 0
//        var result: ResultBean? = null
//        var sn: String? = null
//
//        class ResultBean {
//            var word: List<String>? = null
//        }
//    }
//}
//
