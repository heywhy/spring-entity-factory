# spring-entity-factory

This package helps to create and instance of a given type, this reduces boilerplate
when creating an object to use during tests.

## Installation

The package is published through [jitpack.io](https://jitpack.io), below are the steps;

- Add the JitPack repository to your build file
```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```
-  Add the dependency
```groovy
dependencies {
    implementation 'com.github.heywhy:spring-entity-factory:Tag'
}
``` 

## Usage

To create an instance of a class you need to inject `com.github.heywhy.springentityfactory.contracts.ModelFactory`,
by including `com.github.heywhy.springentityfactory.FactoryConfiguration`.

```java
@Configuration
@Import({com.github.heywhy.springentityfactory.FactoryConfiguration.class})
class Config {}

// ---
import com.github.heywhy.springentityfactory.contracts.ModelFactory;

class Test {
    
    @Inject
    private ModelFactory modelFactory;

    void doAnything() {
        // creates an instance without persisting in the db.
        ObjectClass object = modelFactory.make(ObjectClass.class);
        // to create an instance and persist in datastore using entinty manager
        ObjectClass object = modelFactory.create(ObjectClass.class);
    }    
}
```

Prior to version **0.2.0**, you to register a factory for a class.
In **v0.2.0** you don't have a register a factory per class now as long
as the class/object has a default constructor.

### v0.2.0 

A default generator has been including for all basic java types: bool, char, string,
long, float, double, short and long. To register a generator for a particular class
(do if there is no-arg constructor available);

```java
import com.github.heywhy.springentityfactory.TypeValueGenerator;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;import java.math.BigDecimal;

class Test {
    private ModelFactory modelFactory;

    @BeforeEach
    void setUp() {
        // add a generator for BigDecimal
        TypeValueGenerator.add(BigDecimal.class, faker -> new BigDecimal(faker.number().randomNumber()));
        
        assertNotNull(modelFactory.make(BigDecimal.class));
    }
}
```