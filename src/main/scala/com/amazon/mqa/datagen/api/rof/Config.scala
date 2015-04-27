package com.amazon.mqa.datagen.api.rof

import com.google.common.base.Supplier
import com.amazon.mqa.datagen.{Config => jConfig}

import scala.collection.JavaConversions

/**
 * Configuration for object factory.
 */
case class Config(suppliers: Map[Class[_], Supplier[_]], arraySizeSupplier: Supplier[Int]) {

//  def withSupplier[T](clazz: Class[T] , supplier: Supplier[T]): Config = {
//    suppliers += clazz -> supplier
//    copy(suppliers = suppliers)
//  }
//
//  def withArraySizeSupplier(arraySizeSupplier: Supplier[Int]): Config = {
//    copy(arraySizeSupplier = arraySizeSupplier)
//  }

}

object Config {

  /**
   * Create a config from Java config.
   *
   * @param config
   * @return
   */
  def fromConfig(config: jConfig): Config = {
    new Config(
      suppliers = JavaConversions.mapAsScalaMap(config.getSuppliers).toMap,
      arraySizeSupplier = new Supplier[Int] {
        override def get(): Int = config.getArraySizeSupplier.get().toInt
      }
    )
  }

  def apply(): Config = fromConfig(jConfig.createDefault())



}
