# NovelAndroid
### 一个小说阅读软件，基本上小说业务都有， 可以独立运营，同时也是NovelServer的Android客户端。 个人业余所做


客户端目前只做了Android端， 地址[悠然读书]:http://reader.ifinder.cc

![my-logo.png](https://github.com/Richard0403/NovelAndroid/blob/master/image/binary_code.png "android端线上项目")

服务端项目地址https://github.com/Richard0403/NovelServer
## 功能
  1. 小说阅读
  2. 小说分类
  3. 评论留言区
  4. 基本的登录注册
  5. 阅读时长换积分
  6. 积分兑换
  7. 管理员，修改分类
  
## 功能很简单，但基本的小说业务功能都在，业余学习spring boot 所做

## 要运行需要配置AppConfig信息
```
  /**
     * 内部测试服务器地址
     */
    private static final String TEST_SERVER = "http://xxx.xxxx.xx/";
    private static final String LOCAL_SERVER = "http://192.168.0.136:8080/";
    /**
     * 正式线上服务器地址
     */
    private static final String BASE_URL = "http://xx.xxx.xxx/";
```
