[色度子采样](https://en.wikipedia.org/wiki/Chroma_subsampling) 是现代视频编解码器中广泛采用的技术。它利用人体视觉系统对光的亮度比对光的色度更敏感的特点对颜色数据进行 [YUV 编码](https://en.wikipedia.org/wiki/YUV) 而有效降低了数据码率。

Android 摄像头预览使用的默认格式 [Y'UV420sp](https://en.wikipedia.org/wiki/YUV#Y.27UV420sp_.28NV21.29_to_RGB_conversion_.28Android.29) ([FOURCC](http://www.fourcc.org/fourcc.php) 代号 `NV21`) 也是 YUV 编码格式的一种。本月的试题就是把 `NV21` 编码的数据还原成 RGB 数据。

请阅读上文链接中的文档，实现函数

```scala
def nv21_to_rgb(src: Array[Short], width: Int): Array[Short]
```

参数 `src` 为 UV21 格式的源图像数据，每一个元素取值在 `[0, 255]` 区间。`width` 为图像的宽度，是 2 的整数倍，`src.length` 是 `width*3` 的整数倍(可以确保数据的完整性)。返回 RGB 格式数据，每一种颜色分量占据一个 `Short`，取值在 `[0, 255]` 区间，没有 Alpha 通道。

关于 `NV21` 的编码方式可参考下图.

![`NV21` 格式图解](https://software.intel.com/sites/default/files/managed/1e/b1/17480f2.png)

YUV（在数字领域其实是 YCbCr）与 RGB 之间的转换请使用以下公式：

![转换公式](https://upload.wikimedia.org/math/a/2/7/a279d0cb0cd235bf1a767012b9ab296c.png)

浮点类型到整数类型转换时使用四舍五入.

测试用例（不完备，请自行补充）：

```scala
assert(nv21_to_rgb(Array.fill[Short](6)(0), 2) sameElements Array[Short](0, 135, 0, 0, 135, 0, 0, 135, 0, 0, 135, 0))
assert(nv21_to_rgb(Array[Short](126, 127, 128, 129, 130, 131), 2) sameElements Array[Short](129, 124, 131, 130, 125, 132, 131, 126, 133, 132, 127, 134))
assert(nv21_to_rgb(Array[Short](30, 60, 90, 120, 150, 250), 2) sameElements Array[Short](61, 0, 246, 91, 2, 255, 121, 32, 255, 151, 62, 255))
```

要求：

    请在github上（或使用任何一个开放的代码托管服务如bitbucket.org, git.oschina.net, coding.net等）创建完整的sbt项目，使用Scala语言解决本题。将项目链接作为入群问题的答案。
    尽量不使用 var
    用ScalaTest, Specs2或ScalaCheck编写测试（我们也接受使用JUnit或其它工具编写的测试）。
    本群也欢迎猎头及HR人员入群，此类请将公司招聘网页的链接或招聘文档百度网盘链接作为入群问题的答案。

