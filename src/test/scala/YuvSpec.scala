package com.test

import org.scalatest._
import com.test.Yuv.nv21_to_rgb

class YuvSpec extends FlatSpec with Matchers {
  "Yuv" should "calculate right" in {
    nv21_to_rgb(Array.fill[Short](6)(0), 2) should be === Array[Short](0, 135, 0, 0, 135, 0, 0, 135, 0, 0, 135, 0)
    nv21_to_rgb(Array[Short](126, 127, 128, 129, 130, 131), 2) should be === Array[Short](129, 124, 131, 130, 125, 132, 131, 126, 133, 132, 127, 134)
    nv21_to_rgb(Array[Short](30, 60, 90, 120, 150, 250), 2) should be === Array[Short](61, 0, 246, 91, 2, 255, 121, 32, 255, 151, 62, 255)
  }
}
