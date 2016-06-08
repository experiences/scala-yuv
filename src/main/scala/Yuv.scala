package com.test

object Yuv {

  implicit def clamp(x: Double): Short = math.round(0.toDouble max x min 255).toShort

  def yuv_to_rgb(y: Short, u: Short, v: Short): Array[Short] = {
    val rTmp = y + 1.402 * (v - 128)
    val gTmp = y - 0.344 * (u - 128) - 0.714 * (v - 128)
    val bTmp = y + 1.772 * (u - 128)
    Array[Short](rTmp, gTmp, bTmp)
  }

  def nv21_to_rgb(src: Array[Short], width: Int): Array[Short] = {

    //  println()
    //  println("src:" + src.mkString(","))

    assert(width % 2 == 0)
    assert(src.length % (width * 3) == 0)

    val height = math.floor(src.length / 1.5 / width).toInt
    val size = width * height

    //  println("height:" + height)
    //  println("width:" + width)
    //  println("size:" + size)

    val result = for (i <- 0 until size) yield {
      val x = i % width
      val y = math.floor(i / width).toInt
      val v_x = if (x % 2 == 0) x else x - 1
      val v_y = if (y % 2 == 0) height + y / 2 else height + (y - 1) / 2
      val u_x = v_x + 1
      val u_y = v_y

      //    println("yuv:" + "Y:(" + y + "," + x + ")" + "V:(" + v_y + "," + v_x + ")" + "U:(" + u_y + "," + u_x + ")")

      val Y = src(y * width + x)
      val v = src(v_y * width + v_x)
      val u = src(u_y * width + u_x)
      yuv_to_rgb(Y, u, v)
    }
    //    println("result:" + result.flatten.toArray.mkString(","))
    result.flatten.toArray
  }

  def main(args: Array[String]) = {
    nv21_to_rgb(Array.fill[Short](6)(0), 2)
    nv21_to_rgb(Array[Short](126, 127, 128, 129, 130, 131), 2)
    nv21_to_rgb(Array[Short](30, 60, 90, 120, 150, 250), 2)
    nv21_to_rgb(Array[Short](126, 127, 128, 129, 30, 60, 90, 120, 130, 131, 150, 250), 4)

    assert(nv21_to_rgb(Array.fill[Short](6)(0), 2) sameElements Array[Short](0, 135, 0, 0, 135, 0, 0, 135, 0, 0, 135, 0))
    assert(nv21_to_rgb(Array[Short](126, 127, 128, 129, 130, 131), 2) sameElements Array[Short](129, 124, 131, 130, 125, 132, 131, 126, 133, 132, 127, 134))
    assert(nv21_to_rgb(Array[Short](30, 60, 90, 120, 150, 250), 2) sameElements Array[Short](61, 0, 246, 91, 2, 255, 121, 32, 255, 151, 62, 255))

  }
}
