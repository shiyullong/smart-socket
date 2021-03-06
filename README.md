## smart-socket [![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu) 
smart-socket是一款国产开源的Java AIO框架，支持 TCP、UDP、SSL/TLS，追求代码量、性能、稳定性、接口设计各方面都达到极致。
如果smart-socket对您有一丝帮助，请Star一下我们的项目并持续关注；
如果您对smart-socket并不满意，那请多一些耐心，smart-socket一直在努力变得更好。

**愿景**：
- 数据传输更快
- 并发能力更高
- 硬件能耗更低
---
**官方QQ群：** 172299083🈵 、**830015805**（入群条件：***Star本项目***，非技术人员请勿扰，感谢）

## 本群严禁广告推销，请某云的销售朋友勿扰，给技术人员留一片净土。你们发的广告我会在第一时间清理并将你请出去，所以真的没必要。
## 因深受某云广告骚扰，调整下入群规则：
- 使用smart-socket的用户：请在[issues](https://gitee.com/smartboot/smart-socket/issues/IHV69)中完成案例登记，并在加群：830015805 时反馈( **如未使用smart-socket而登记无效案例，入群申请不予通过** )。
- 学习smart-socket的用户：付费入群：172299083，待确认为真实用户后可找群主退费，销售朋友入此群概不退费。
---
### Feature
1. 源码：代码量极少，可读性强。核心代码不到 1500 行，工程结构、包路径层次清晰。
2. 学习：学习门槛低，二次开发只需实现 2 个接口（Protocol、MessageProcessor）。具备通信开发经验的几乎无学习成本，根据作者经验来看大家普遍面临的困难在于"通信协议"的概念理解以及编解码实现，而非通信框架本身。
3. 使用：通过内存池（bufferPool）技术以及背压（back-pressure）机制，既能充分发挥机器性能，又能保证服务运行稳定性。
4. 服务：提供丰富的插件式服务，包括：SSL/TLS通信插件、心跳插件、断链重连插件、服务指标统计插件、黑名单插件、内存池监测插件。

### 交流互助
如果您在使用的过程中碰到问题，可以通过下面几个途径寻求帮助，同时我们也鼓励资深用户给新人提供帮助。

- 加入QQ群：830015805 或 Email：zhengjunweimail@163.com。
- [开源问答](https://www.oschina.net/question/tag/smart-socket)
- 通过 [Issue](https://gitee.com/smartboot/smart-socket/issues) 报告 bug 或进行咨询。
- 提交 [Pull Request](https://gitee.com/smartboot/smart-socket/pulls) 改进 smart-socket 的代码。
- 在开源中国发表smart-socket相关的技术性文章。

### 开源生态
1. [smart-http](https://gitee.com/smartboot/smart-http) 国内首款基于smart-socket实现的Http服务器
2. [irtu-gps](https://gitee.com/wendal/irtu-gps) 基于iRTU项目,实现GPS数据的接收和展示
> 如果您的项目中使用了 smart-socket 并愿意在此处展现给大众，欢迎留言反馈。

### 企业用户
> 如果您所在的公司使用了 smart-socket 并愿意在此处展现给大众，欢迎留言反馈。格式：公司名+项目简述


### 感谢
- 感谢码云提供的代码托管和 Pages 服务。
- 感谢 JetBrains 为 smart-socket 提供的 IDEA License。
- 感谢为 smart-socket [捐赠](https://smartboot.gitee.io/book/donation.html)的每一位朋友。
- 感谢正在使用及为 smart-socket 作推广的朋友。

## 性能测试
- 环境准备
    1. 测试项目：[smart-http](https://gitee.com/smartboot/smart-http) 
    2. 通信协议：Http
    3. 压测工具：[wrk](https://github.com/wg/wrk)
    4. 测试机：MacBook Pro, 2.9Ghz i5, 4核8G内存
    5. 测试命令：
    ```
    wrk -H 'Host: 10.0.0.1' -H 'Accept: text/plain,text/html;q=0.9,application/xhtml+xml;q=0.9,application/xml;q=0.8,*/*;q=0.7' -H 'Connection: keep-alive' --latency -d 15 -c 1024 --timeout 8 -t 4 http://127.0.0.1:8080/plaintext -s pipeline.lua -- 16
    ```
- 测试结果：smart-socket的性能表现基本稳定维持在 128MB/s 左右。

    |  连接数  | Requests/sec   |  Transfer/sec  |
    | -- | -- | -- |
    | 512 | 924343.47 | 128.70MB|
    | 1024 | 922967.92 | 128.51MB|
    | 2048 | 933479.41 | 129.97MB|
    | 4096 | 922589.53 | 128.46MB|

### 参考文献
- https://jfarcand.wordpress.com/2008/11/25/tricks-and-tips-with-aio-part-1-the-frightening-thread-pool/
- https://webtide.com/on-jdk-7-asynchronous-io/
- https://www.cnblogs.com/sandea/p/9094863.html

### 关于作者
Edit By 三刀  
E-mail:zhengjunweimail@163.com  
Update Date: 2019-11-07