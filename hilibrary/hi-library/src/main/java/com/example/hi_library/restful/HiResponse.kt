package com.example.hi_library.restful

open class HiResponse<T> {
    companion object {
        const val SUCCESS: Int = 0

        //有错误
        const val RC_HAS_ERROR = 5000

        //账号不错在
        const val RC_ACCOUNT_INVALID = 5001

        //密码错误
        const val RC_PWD_INVALID = 5002

        //请xian先登录
        const val RC_NEED_LOGIN = 5003

        //未购买本课程 或者是ID有错
        const val RC_NOT_PURCHASED = 5004

        //校验服务报错
        const val RC_CHECK_SERVER_ERROR = 5005

        //此用户名被占用
        const val RC_USER_NAME_EXISTS = 5006

        //  请输入HTML
        const val RC_HTML_INVALID = 8001

        // 请输入配置
        const val RC_CONFIG_INVALID = 8002

        //用户身份非法
        const val RC_USER_FORBID = 6001

        //访问Token过期
        const val RC_AUTH_TOKEN_EXPIRED = 4030

        //访问Token不正确
        const val RC_AUTH_TOKEN_INVALID = 4031
    }

        //原始数据
        var rawData: String? = null

        //业务状态码
        var code = 0

        //业务数据
        var data: T? = null

        //错误状态下的数据
        var errorData: Map<String, String>? = null

        // 错误信息
        var msg: String? = null
    }