package com.amazon.mqa.datagen.api.rof

import com.amazon.mqa.datagen.api.rof.ObjectFactory
import com.amazon.mqa.datagen.supplier.{AlphanumericStringSupplier, MinMaxIntegerSupplier}

/**
 * Created by chengpan on 4/20/15.
 */
object ObjectFactoryTest {

  def main(args: Array[String]) {
    val factory = ReflectionObjectFactory(
      ReflectionObjectFactory.fromSupplier(new MinMaxIntegerSupplier(1, 11)),
      ReflectionObjectFactory.fromSupplier(new AlphanumericStringSupplier)
    )

    println(factory.create[String])
    println(factory.create[Integer])
    println(factory.create[Int])
    val x = factory.create[TestClassA]
    println(x)

    println(factory.create[List[Int]])
  }

}
