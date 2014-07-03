package net.shipilev

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.annotation.tailrec

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(3)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
class ScalaBenchNonPredict {

  @Param(Array("1", "5", "10", "15", "20"))
  var lim: Int = _

  var startDiv: Int = _
  var startVal: Int = _

  @Setup
  def init() {
    startDiv = 2
    startVal = 10
  }

  @tailrec private def isEvenlyDivisible(v: Int, div: Int, lim: Int): Boolean = {
    if (div > lim) true
    else (v % div == 0) && isEvenlyDivisible(v, div + 1, lim)
  }

  @Benchmark
  def test(): Int = {
    var v = startVal
    while(!isEvenlyDivisible(v, startDiv, lim))
      v += 2
    v
  }

}
