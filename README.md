[![Stories in Ready](https://badge.waffle.io/leakingtapan/rof.png?label=ready&title=Ready)](https://waffle.io/leakingtapan/rof)
[![Build Status](https://travis-ci.org/leakingtapan/rof.svg)](https://travis-ci.org/leakingtapan/rof)
## Reflection Object Factory
Reflection Object Factory (ROF) is a Java library which simplifies the process of creating randomized data objects for testing.

##### Simple creation of data objects
Creation of complex data types to use as inputs, mock outputs, etc. during a test can be time-consuming and tedious.
Object Factory instantiates fully populated complex data types in a single line of code, which can
be placed in the test itself to achieve clear and concise test setup.

##### Randomized test data
Most well-design software components aren't tightly coupled with exact input values. The tests
for these components don't need to (and arguably _shouldn't_) depend on exact values or static data in order to exercise
and verify the contract of the component under test. Designing components to be testable with random data tends to
promote more generic and reusable software. Object Factory makes it easy to test with random valid values, and allows
the user to configure the shape of valid values if necessary.


## Artifacts
#### Maven
```xml
<dependency>
    <groupId>com.amazon.datagen</groupId>
    <artifactId>objectfactory_2.11</artifactId>
    <version>1.0</version>
</dependency>
```

#### Gradle
```
compile group: 'com.amazon.datagen', name: 'objectfactory_2.11', version: '1.0'
```

#### SBT
```
libraryDependencies += "com.amazon.datagen" % "objectfactory" % "1.0"
```

## Usage
Reflection object factory is fairly easy to use:

### Create objects
```java
    // Instantiate object factory
    final ObjectFactory factory = new ReflectionObjectFactory();
    
    // Create an instance of FooInput populated with random values
    final FooInput input = factory.create(FooInput.class);
    
    // Create a set of instances of Bar populated with random values
    final Set<Bar> bars = factory.setOf(Bar.class);
```

### Initialization
#### Local Instance
Object Factory provides a default constructor and a constructor which takes a configuration object (see [Customization](#customization)).

```java
// Default instance
final ObjectFactory factory = new ReflectionObjectFactory();
 
// Configured instance
final Config config = Config.createDefault();
final ObjectFactory factory = new ReflectionObjectFactory(config);
```

#### Inheritance
Object Factory provides an abstract class wrapper for convenience that can be inherited by test classes.

```java
public final Class FooTest extends AbstractObjectFactory {
    
    @Test
    public void testBar() {
        final BarInput input = create(BarInput.class);  // inherited from AbstractObjectFactory
        
        ...
    }
}
```

### Supported Class Types
Object factory supports following types of object creation:

- Primitives
  - boolean, byte, char, double, float, int, long, short
  - Boolean, Byte, Character, Double, Float, Integer, Long, Short, String, Date, BigInteger, BigDecimal
  - Array of any of above such as int[] or Integer[]
- Plan Old Java Object (POJO)
- Optional of any of above such as Optional\<Integer\>
- Abstract Class Proxy
  - Note: only abstract with empty parameter constructor is supported
- Interface Proxy

__Note__

  - When calling a method from Proxied abstract class/interface, each time the return value is randomized by default.
  - You can use Config.withSupplier to customize how the return value is generated for specific method.
  - Customized type can also be add through Config class. See [Customization](#customize-your-own-class-as-primitive) 

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

## License
This tool is under Apache License Version 2.0
