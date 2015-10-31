[![Stories in Ready](https://badge.waffle.io/leakingtapan/rof.png?label=ready&title=Ready)](https://waffle.io/leakingtapan/rof)
[![Build Status](https://travis-ci.org/leakingtapan/rof.svg)](https://travis-ci.org/leakingtapan/rof)
## Reflection Object Factory
Reflection object factory(ROF) aims at simplifying the process of creating randomized Plain Old Java Object(POJO). One of the problem it solves is that, when developer writes unit tests, sometimes people wants input/output objects populated with arbitrary but unique data. For example, if you are testing with a mock version of a service, when you need input and output typed Java objects. Writing code to create these objects for unit tests can be time-consuming and tedious.

## Usage
Reflection object factory is fairly easy to use:

 - Add Sbt dependency:
```
 libraryDependencies += "com.amazon.datagen" % "objectfactory_2.11" % "1.0"
```

 - Create objects:
```java
    final ObjectFactory factory = new ReflectionObjectFactory();
    final AcquireMerchantInput input = factory.create(AcquireMerchantInput.class);
    final AcquireMerchantOutput output = factory.create(AcquireMerchantOutput.class);
```

### Initialization
There are two ways to create an object from reflection object factory:

 - Initial by default constructor:

   ```java
   final ObjectFactory factory = new ReflectionObjectFactory();
   ```

 - Initial by configuration, where the config object customizes the factory:

   ```java
   final Config config = Config.createDefault();
   final ObjectFactory factory = new ReflectionObjectFactory(config);
   ```

### Customization
#### Customize Primitive Creation
By default reflection object factory contains a set of suppliers that can create different primitives. For the full list of primitives and the suppliers, refer to primitive supplier. Here is an example to override the existing primitive supplier:

```java
final Config config = Config.createDefault()
                .withSupplier(int.class, new IncrementalIntSupplier(0));
final int[] incrementalIntegers = new ReflectionObjectFactory(config).create(int[].class);
```

#### Customize Array Size
The size of array could be set up in the following way:

```java
    final int minSize = 20;
    final int maxSize = 30;
    final Config config = Config.createDefault()
	                    .withArraySizeSupplier(new MinMaxIntegerSupplier(minSize, maxSize));
    final String[] randomStrings = new ReflectionObjectFactory(config).create(String[].class);
```

#### Customize Your Own Class as Primitive
Primitives are the building block to create object, but their types are not limited to integer, double, etc. Supplier for any class could be added into configuration to be treated as a building block, so that the supplier will be used first before factory looks into the constructor of that class.

Example:

```java
final Config config = Config.createDefault()
        .withSupplier(Offer.class, new OfferSupplier());
final Merchant merchant = new ReflectionObjectFactory(config).create(Merchant.class);
```

- Note:
  When overriding primitives such as int and Integer, they are treated as different classes, which means if POJO class has int field use int.class in withSupplier method.

## Basic Assumptions
This factory is supposed to be used to create Java object that holds data such as primitive, enumeration, array or plain old java object (POJO).

## License
This tool is under Apache License Version 2.0
