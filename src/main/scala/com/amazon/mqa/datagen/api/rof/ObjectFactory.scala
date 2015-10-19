package com.amazon.mqa.datagen.api.rof

import com.amazon.mqa.datagen.supplier.{RandomShortSupplier, RandomCharacterSupplier, RandomIntegerSupplier, AlphanumericStringSupplier}
import com.amazon.mqa.datagen.rof.{DefaultObjectFactory => jObjectFactory}
import com.google.common.base.Supplier
import scala.collection.JavaConversions
import scala.reflect._
import scala.reflect.runtime.universe._

/**
 * Scala Api for object factory
 */
trait ObjectFactory { self =>

  def create[T: TypeTag: ClassTag]: T

  def isDefinedAt[T: TypeTag: ClassTag]: Boolean

  def orElse(factory: ObjectFactory): ObjectFactory = new ObjectFactory {
    override def create[T: TypeTag: ClassTag]: T = {
      if (self.isDefinedAt[T]) self.create[T]
      else if (factory.isDefinedAt[T]) factory.create[T]
      else throw new RuntimeException(s"unsupported type: ${typeOf[T]}")
    }
    override def isDefinedAt[T: TypeTag: ClassTag]: Boolean = self.isDefinedAt[T] || factory.isDefinedAt[T]
  }
}

trait ReflectionObjectFactory extends ObjectFactory {

  def apply(): ObjectFactory = apply(Config())

  def apply(config: Config): ObjectFactory =
         orElse(new BasicObjectFactory(config.suppliers))
        .orElse(new JavaObjectFactory(
          new jObjectFactory(JavaConversions.mapAsJavaMap(config.suppliers),
            new Supplier[Integer] {
              override def get(): Integer = config.arraySizeSupplier.get()
            })
        ))

  def fromSupplier[S: TypeTag](supplier: Supplier[S]): ObjectFactory = new ObjectFactory {
    override def create[T: TypeTag: ClassTag]: T = supplier.get().asInstanceOf[T]
    override def isDefinedAt[T: TypeTag: ClassTag]: Boolean = typeOf[T] =:= typeOf[S]
  }

//  def fromSupplier[S: TypeTag](supplier: Supplier[_]): ObjectFactory = new ObjectFactory {
//    override def create[T: TypeTag: ClassTag]: T = supplier.get().asInstanceOf[T]
//    override def isDefinedAt[T: TypeTag: ClassTag]: Boolean = typeOf[T] =:= typeOf[S]
//  }

  def fromSupplier[S: TypeTag](f: => S): ObjectFactory = new ObjectFactory {
    override def create[T: TypeTag: ClassTag]: T = f.asInstanceOf[T]
    override def isDefinedAt[T: TypeTag: ClassTag]: Boolean = typeOf[T] =:= typeOf[S]
  }

  override def isDefinedAt[T: TypeTag: ClassTag]: Boolean = false

  override def create[T: TypeTag: ClassTag]: T = throw new RuntimeException("unsupported")
}

object ReflectionObjectFactory extends ReflectionObjectFactory {
  def apply(factories: ObjectFactory *): ObjectFactory = {
    factories reduce { (f1, f2) =>
      f1.orElse(f2)
    }
  }
}

class BasicObjectFactory(suppliers: Map[Class[_], Supplier[_]]) extends ObjectFactory {

  override def create[T: TypeTag: ClassTag]: T = {
    suppliers.get(classTag[T].runtimeClass) match {
      case Some(supplier) => supplier.get().asInstanceOf[T]
      case None => throw new RuntimeException(s"unsupported basic class: ${classTag[T].runtimeClass}")
    }
  }
  override def isDefinedAt[T: TypeTag: ClassTag]: Boolean = suppliers.contains(classTag[T].runtimeClass)
}

class TypedObjectFactory(objectFactory: ObjectFactory) extends ObjectFactory {

  override def create[T: TypeTag: ClassTag]: T = {
//    val TypeRef(_, _, args) = typeOf[T]
//
//    if (args.length == 1) {
//      (1 to 10) map { _=>
//        objectFactory.create[args.type]
//      }
//
//    } else {
//      throw new RuntimeException
//    }

    ???
  }

  override def isDefinedAt[T: TypeTag : ClassTag]: Boolean = ??? //typeOf[T] <:< typeOf[List]
}

class JavaObjectFactory(factory: jObjectFactory) extends ObjectFactory {
  override def create[T: TypeTag: ClassTag]: T = factory.create(classTag[T].runtimeClass).asInstanceOf[T]
  override def isDefinedAt[T: TypeTag: ClassTag]: Boolean = {
    val TypeRef(_, symbol, _) = typeOf[T]

    symbol.isJava
  }
}