# Java8 新特性

### 一、Java的新特性

#### **1.Lambda表达式和函数式接口 **

​	Lambda表达式（也叫做闭包）是Java 8中最大的也是期待已久的变化。它允许我们将一个函数当作方法的参数（传递函数），或者说把代码当作数据，这是每个函数式编程者熟悉的概念。很多基于JVM平台的语言一开始就支持Lambda表达式，但是Java程序员没有选择，只能使用匿名内部类来替代Lambda表达式。

Lambda表达式的设计被讨论了很久，而且花费了很多的功夫来交流。不过最后取得了一个折中的办法，得到了一个新的简明并且紧凑的Lambda表达式结构。最简单的Lambda表达式可以用逗号分隔的参数列表、->符号和功能语句块来表示。示例如下：

```java
Arrays.asList( "a", "b", "d" ).forEach( e -> System.out.println( e ) );
```

请注意到编译器会根据上下文来推测参数的类型，或者你也可以显示地指定参数类型，只需要将类型包在括号里。举个例子：

```java
Arrays.asList( "a", "b", "d" ).forEach( ( String e ) -> System.out.println( e ) );
```

Lambda表达式可能会引用类的成员或者局部变量（会被隐式地转变成final类型），下面两种写法的效果是一样的：

```java
String separator = ",";
Arrays.asList( "a", "b", "d" ).forEach(
    ( String e ) -> System.out.print( e + separator ) );
```

=

```java
final String separator = ",";
Arrays.asList( "a", "b", "d" ).forEach(
    ( String e ) -> System.out.print( e + separator ) );
```

Lambda表达式可能会有返回值，编译器会根据上下文推断返回值的类型。如果lambda的语句块只有一行，不需要return关键字。下面两个写法是等价的：

```java
Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> e1.compareTo( e2 ) );
```

=

```java
Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> {
    int result = e1.compareTo( e2 );
    return result;
} );
```

语言的设计者们思考了很多如何让现有的功能和lambda表达式友好兼容。于是就有了函数接口这个概念。函数接口是一种只有一个方法的接口，像这样地，函数接口可以隐式地转换成lambda表达式。

`java.lang.Runnable `和`java.util.concurrent.Callable`是函数接口两个最好的例子。但是在实践中，函数接口是非常脆弱的，只要有人在接口里添加多一个方法，那么这个接口就不是函数接口了，就会导致编译失败。Java 8提供了一个特殊的注解`@FunctionalInterface`来克服上面提到的脆弱性并且显示地表明函数接口的目的（java里所有现存的接口都已经加上了`@FunctionalInterface`）。让我们看看一个简单的函数接口定义：

```java
@FunctionalInterface
public interface Functional {
    void method();
}
```

我们要记住默认的方法和静态方法（下一节会具体解释）不会违反函数接口的约定，例子如下：

```java
@FunctionalInterface
public interface FunctionalDefaultMethods {
    void method();
    default void defaultMethod() {
    }
}
```

```java
private interface Defaulable {
    // Interfaces now allow default methods, the implementer may or
    // may not implement (override) them.
    default String notRequired() {
        return "Default implementation";
    }
}

private static class DefaultableImpl implements Defaulable {

}
private static class OverridableImpl implements Defaulable {
    @Override
    public String notRequired() {
        return "Overridden implementation";
    }
}
```



#### 2.接口的默认方法和静态方法

Java 8增加了两个新的概念在接口声明的时候：默认和静态方法。默认方法和Trait有些类似，但是目标不一样。默认方法允许我们在接口里添加新的方法，而不会破坏实现这个接口的已有类的兼容性，也就是说不会强迫实现接口的类实现默认方法。

默认方法和抽象方法的区别是抽象方法必须要被实现，默认方法不是。作为替代方式，接口可以提供一个默认的方法实现，所有这个接口的实现类都会通过继承得倒这个方法（如果有需要也可以重写这个方法），让我们来看看下面的例子：

```java
public interface Defaulable {
    // Interfaces now allow default methods, the implementer may or
    // may not implement (override) them.
    default String notRequired() {
        return "Default implementation";
    }
}
public static class DefaultableImpl implements Defaulable {
}
public static class OverridableImpl implements Defaulable {
    @Override
    public String notRequired() {
        return "Overridden implementation";
    }
}
```

接口`Defaulable`使用`default`关键字声明了一个默认方法`notRequired()`，类`DefaultableImpl`实现了`Defaulable`接口，没有对默认方法做任何修改。另外一个类`OverridableImpl`重写类默认实现，提供了自己的实现方法。

Java 8 的另外一个有意思的新特性是接口里可以声明静态方法，并且可以实现。例子如下：

```java
public interface DefaulableFactory {
    // Interfaces now allow static methods
    static Defaulable create( Supplier< Defaulable > supplier ) {
        return supplier.get();
    }
}
```

下面是把接口的静态方法和默认方法放在一起的示例（::new 是构造方法引用，后面会有详细描述）：

```java
public static void main( String[] args ) {
    Defaulable defaulable = DefaulableFactory.create( DefaultableImpl::new );
    System.out.println( defaulable.notRequired() );
    defaulable = DefaulableFactory.create( OverridableImpl::new );
    System.out.println( defaulable.notRequired() );
}
```

控制台的输出如下：

```java
Default implementation
Overridden implementation
```

JVM平台的接口的默认方法实现是很高效的，并且方法调用的字节码指令支持默认方法。默认方法使已经存在的接口可以修改而不会影响编译的过程。`java.util.Collection`中添加的额外方法就是最好的例子：`stream(), parallelStream(), forEach(), removeIf()`

虽然默认方法很强大，但是使用之前一定要仔细考虑是不是真的需要使用默认方法，因为在层级很复杂的情况下很容易引起模糊不清甚至变异错误。

#### 3.方法引用

方法引用提供了一个很有用的语义来直接访问类或者实例的已经存在的方法或者构造方法。结合Lambda表达式，方法引用使语法结构紧凑简明。不需要复杂的引用。

下面我们用**Car **这个类来做示例，Car这个类有不同的方法定义。让我们来看看java 8支持的4种方法引用。

```java
public static class Car {
    public static Car create( final Supplier< Car > supplier ) {
        return supplier.get();
    }             
    public static void collide( final Car car ) {
        System.out.println( "Collided " + car.toString() );
    }
    public void follow( final Car another ) {
        System.out.println( "Following the " + another.toString() );
    }
    public void repair() {
        System.out.println( "Repaired " + this.toString() );
    }
}
```

第一种方法引用是构造方法引用，语法是：**Class::new** ，对于泛型来说语法是：**Class<T >::new**，请注意构造方法没有参数:

```java
final Car car = Car.create( Car::new );
final List< Car > cars = Arrays.asList( car );
```

第二种方法引用是静态方法引用，语法是：**Class::static_method**请注意这个静态方法只支持一个类型为Car的参数。

```java
cars.forEach( Car::collide );
```

第三种方法引用是类实例的方法引用，语法是：**Class::method**请注意方法没有参数。

```java
cars.forEach( Car::repair );
```

最后一种方法引用是引用特殊类的方法，语法是：**instance::method，**请注意只接受Car类型的一个参数。

```java
final Car police = Car.create( Car::new );
cars.forEach( police::follow );
```

运行这些例子我们将会在控制台得到如下信息（Car的实例可能会不一样）：

```java
Collided com.javacodegeeks.java8.method.references.MethodReferences$Car@7a81197d
Repaired com.javacodegeeks.java8.method.references.MethodReferences$Car@7a81197d
Following the com.javacodegeeks.java8.method.references.MethodReferences$Car@7a81197d
```

#### 4.重复注释 

自从Java 5支持注释以来，注释变得特别受欢迎因而被广泛使用。但是有一个限制，同一个地方的不能使用同一个注释超过一次。 Java 8打破了这个规则，引入了重复注释，允许相同注释在声明使用的时候重复使用超过一次。

重复注释本身需要被@Repeatable注释。实际上，他不是一个语言上的改变，只是编译器层面的改动，技术层面仍然是一样的。让我们来看看例子：

java 8之前也有重复使用注解的解决方案，但可读性不是很好，比如下面的代码：

````
public @interface Authority {
     String role();
}
 

public @interface Authorities {
    Authority[] value();
}

public class RepeatAnnotationUseOldVersion {

    @Authorities({@Authority(role="Admin"),@Authority(role="Manager")})
    public void doSomeThing(){
    }
}
````

java 8里面的做法:

````
@Repeatable(Authorities.class)
public @interface Authority {
     String role();
}
 

public @interface Authorities {
    Authority[] value();
}

public class RepeatAnnotationUseNewVersion {
    @Authority(role="Admin")
    @Authority(role="Manager")
    public void doSomeThing(){ }
}
````

不同的地方是，创建重复注解Authority时，加上@Repeatable,指向存储注解Authorities，在使用时候，直接可以重复使用Authority注解。从上面例子看出，java 8里面做法更适合常规的思维，可读性强一点

#### 5.更好的类型推断 

```java
package com.javacodegeeks.java8.repeatable.annotations;

import java.lang.annotation.ElementType;

import java.lang.annotation.Repeatable;

import java.lang.annotation.Retention;

import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.Target;

public class RepeatingAnnotations {
    @Target( ElementType.TYPE )
    @Retention( RetentionPolicy.RUNTIME )
    public @interface Filters {
        Filter[] value();
    }
    @Target( ElementType.TYPE )
    @Retention( RetentionPolicy.RUNTIME )
    @Repeatable( Filters.class )
    public @interface Filter {
        String value();
    };
    @Filter( "filter1" )
    @Filter( "filter2" )
    public interface Filterable {
    }
    public static void main(String[] args) {
        for( Filter filter: Filterable.class.getAnnotationsByType( Filter.class ) ) {
            System.out.println( filter.value() );
        }
    }
}
```

我们可以看到，注释Filter被@Repeatable( Filters.class )注释。

Filters 只是一个容器，它持有Filter, 编译器尽力向程序员隐藏它的存在。通过这样的方式，Filterable接口可以被Filter注释两次。

另外，反射的API提供一个新方法getAnnotationsByType() 来返回重复注释的类型（请注意Filterable.class.getAnnotation( Filters.class )将会返回编译器注入的Filters实例）。

程序的输出将会是这样：

filter1
filter2

#### 6.注解的扩展 

Java 8扩展了注解可以使用的范围，现在我们几乎可以在所有的地方：局部变量、泛型、超类和接口实现、甚至是方法的Exception声明。一些例子如下：

```java
package com.javacodegeeks.java8.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;

public class Annotations {
    @Retention( RetentionPolicy.RUNTIME )
    @Target( { ElementType.TYPE_USE, ElementType.TYPE_PARAMETER } )
    public @interface NonEmpty {
    }
    public static class Holder< @NonEmpty T > extends @NonEmpty Object {
        public void method() throws @NonEmpty Exception {
        }
    }
    @SuppressWarnings( "unused" )
    public static void main(String[] args) {
        final Holder< String > holder = new @NonEmpty Holder< String >();
        @NonEmpty Collection< @NonEmpty String > strings = new ArrayList<>();
    }
}
```

Java 8 新增加了两个注解的程序元素类型

`ElementType.TYPE_USE `和`ElementType.TYPE_PARAMETER`，这两个新类型描述了可以使用注解的新场合。注解处理API（`Annotation Processing API`）也做了一些细微的改动，来识别这些新添加的注解类型。

#### 7.更好的类型推断 

Java 8在类型推断方面改进了很多，在很多情况下，编译器可以推断参数的类型，从而保持代码的整洁。让我们看看例子：

```java
package com.javacodegeeks.java8.type.inference;

public class Value<T> {
    public static<T> T defaultValue() {
        return null;
    }
    public T getOrDefault( T value, T defaultValue ) {
        return ( value != null ) ? value : defaultValue;
    }
}
```

这里是Value< String >的用法

```java
package com.javacodegeeks.java8.type.inference;

public class TypeInference {
    public static void main(String[] args) {
        final Value<String> value = new Value<>();
        value.getOrDefault( "22", Value.defaultValue() );
    }
}
```

参数`Value.defaultValue()`的类型被编译器推断出来，不需要显式地提供类型。在java 7, 相同的代码不会被编译，需要写成：`Value.< String >defaultValue()`;

### 二、Java编译器的新特性

- **参数名字**  

  很长时间以来，Java程序员想尽办法把参数名字保存在java字节码里，并且让这些参数名字在运行时可用。Java 8 终于把这个需求加入到了Java语言（使用反射API和`Parameter.getName()` 方法）和字节码里（使用java编译命令javac的`–parameters`参数）。

  ```java
  package com.javacodegeeks.java8.parameter.names;

  import java.lang.reflect.Method;
  import java.lang.reflect.Parameter;

  public class ParameterNames {

    public static void main(String[] args) throws Exception {

      Method method = ParameterNames.class.getMethod( "main", String[].class );

      for( final Parameter parameter: method.getParameters() ) {

        System.out.println( "Parameter: " + parameter.getName() );
      }
    }
  }
  ```

  如果你编译这个class的时候没有添加参数**–parameters，**运行的时候你会得到这个结果：

  `Parameter: arg0`

  编译的时候添加了**–parameters**参数的话，运行结果会不一样：

  `Parameter: args`

  对于有经验的Maven使用者，–parameters参数可以添加到maven-compiler-plugin的配置部分：

  ```xml
  <plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>3.1</version>
  <configuration>
  <compilerArgument>-parameters</compilerArgument>
  <source>1.8</source>
  <target>1.8</target>
  </configuration>
  </plugin>
  ```

  

### 三、Java  库的新特性 

#### 1.Optional 

著名的[**NullPointerException**](http://examples.javacodegeeks.com/java-basics/exceptions/java-lang-nullpointerexception-how-to-handle-null-pointer-exception/) 是引起系统失败最常见的原因。很久以前[Google Guava](http://code.google.com/p/guava-libraries/)项目引入了**Optional**作为解决空指针异常的一种方式，不赞成代码被null检查的代码污染，期望程序员写整洁的代码。受[Google Guava](http://code.google.com/p/guava-libraries/)的鼓励，**Optional** 现在是Java 8库的一部分。

**Optional** 只是一个容器，它可以保存一些类型的值或者null。它提供很多有用的方法，所以没有理由不显式地检查null。

让我们看看两个**Optional** 用法的小例子：一个是允许为空的值，另外一个是不允许为空的值。

```java
Optional< String > fullName = Optional.ofNullable( null );
System.out.println( "Full Name is set? " + fullName.isPresent() );       
System.out.println( "Full Name: " + fullName.orElseGet( () -> "[none]" ) );
System.out.println( fullName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );
```

如果Optional实例有非空的值，方法` isPresent() `返回`true`否则返回`false`。方法`orElseGet`提供了回退机制，当`Optional`的值为空时接受一个方法返回默认值。`map()`方法转化`Optional`当前的值并且返回一个新的`Optional`实例。`orElse`方法和`orElseGet`类似，但是它不接受一个方法，而是接受一个默认值。上面代码运行结果如下：

```java
Full Name is set? false
Full Name: [none]
Hey Stranger!
```

让我们大概看看另外一个例子。

```java
Optional< String > firstName = Optional.of( "Tom" );
System.out.println( "First Name is set? " + firstName.isPresent() );       
System.out.println( "First Name: " + firstName.orElseGet( () -> "[none]" ) );
System.out.println( firstName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );
System.out.println();
```

输出如下：

```java
First Name is set? true
First Name: Tom
Hey Tom!
```



#### 2.Stream 

新增加的Stream API (`java.util.stream`)引入了在Java里可以工作的函数式编程。这是目前为止对java库最的一次功能添加，希望程序员通过编写有效、整洁和简明的代码，能够大大提高生产率。

`Stream API`让集合处理简化了很多（我们后面会看到不仅限于Java集合类）。让我们从一个简单的类Task开始来看看`Stream`的用法。

```java
public class Streams {
  private enum Status {
    OPEN, CLOSED
  };

  private static final class Task {
    private final Status status;
    private final Integer points;
    Task( final Status status, final Integer points ) {
      this.status = status;
      this.points = points;
    }

    public Integer getPoints() {
      return points;
    }
    public Status getStatus() {
      return status;
    }
    @Override
    public String toString() {
      return String.format( "[%s, %d]", status, points );
    }
  }
}
```

Task类有一个分数的概念（或者说是伪复杂度），其次是还有一个值可以为OPEN或CLOSED的状态.让我们引入一个Task的小集合作为演示例子：

```java
final Collection< Task > tasks = Arrays.asList(
    new Task( Status.OPEN, 5 ),
    new Task( Status.OPEN, 13 ),
    new Task( Status.CLOSED, 8 )
);
```

第一个问题是所有的开放的Task的点数是多少？在java 8 之前，通常的做法是用foreach迭代。但是Java8里头我们会用Stream。Stream是多个元素的序列，支持串行和并行操作。

```java
 // Calculate total points of all active tasks using sum()
final long totalPointsOfOpenTasks = tasks
    .stream()
    .filter( task -> task.getStatus() == Status.OPEN )
    .mapToInt( Task::getPoints )
    .sum();
System.out.println( "Total points: " + totalPointsOfOpenTasks );
```

控制台的输出将会是：

Total points: 18
上面代码执行的流程是这样的，首先Task集合会被转化为Stream表示，然后filter操作会过滤掉所有关闭的Task，接下来使用Task::getPoints 方法取得每个Task实例的点数，mapToInt方法会把Task Stream转换成Integer Stream，最后使用Sum方法将所有的点数加起来得到最终的结果。

在我们看下一个例子之前，我们要记住一些关于Stream的说明。Stream操作被分为中间操作和终点操作。

中间操作返回一个新的Stream。这些中间操作是延迟的，执行一个中间操作比如filter实际上不会真的做过滤操作，而是创建一个新的Stream，当这个新的Stream被遍历的时候，它里头会包含有原来Stream里符合过滤条件的元素。

终点操作比如说forEach或者sum会遍历Stream从而产生最终结果或附带结果。终点操作执行完之后，Stream管道就被消费完了，不再可用。在几乎所有的情况下，终点操作都是即时完成对数据的遍历操作。

Stream的另外一个价值是Stream创造性地支持并行处理。让我们看看下面这个例子，这个例子把所有task的点数加起来。

```java
// Calculate total points of all tasks
final double totalPoints = tasks
   .stream()
   .parallel()
   .map( task -> task.getPoints() ) // or map( Task::getPoints )
   .reduce( 0, Integer::sum );
System.out.println( "Total points (all tasks): " + totalPoints );
```

这个例子跟上面那个非常像，除了这个例子里使用了parallel()方法       并且计算最终结果的时候使用了reduce方法。输出如下：

```java
Total points (all tasks): 26.0
```

经常会有这个一个需求：我们需要按照某种准则来对集合中的元素进行分组。Stream也可以处理这样的需求，下面是一个例子：

```java
// Group tasks by their status
final Map< Status, List< Task > > map = tasks
    .stream()
    .collect( Collectors.groupingBy( Task::getStatus ) );
System.out.println( map );
```

控制台的输出如下：

```java
{CLOSED=[[CLOSED, 8]], OPEN=[[OPEN, 5], [OPEN, 13]]}
```

让我们来计算整个集合中每个task分数（或权重）的平均值来结束task的例子。

```java
// Calculate the weight of each tasks (as percent of total points)
final Collection< String > result = tasks
    .stream()                                        // Stream< String >
    .mapToInt( Task::getPoints )                     // IntStream
    .asLongStream()                                  // LongStream
    .mapToDouble( points -> points / totalPoints )   // DoubleStream
    .boxed()                                         // Stream< Double >
    .mapToLong( weigth -> ( long )( weigth * 100 ) ) // LongStream
    .mapToObj( percentage -> percentage + "%" )      // Stream< String>
    .collect( Collectors.toList() );                 // List< String >

System.out.println( result );
```

控制台输出如下：

```jva
[19%, 50%, 30%]
```

最后，就像前面提到的，Stream API不仅仅处理Java集合框架。像从文本文件中逐行读取数据这样典型的I/O操作也很适合用Stream API来处理。下面用一个例子来应证这一点。

```java
final Path path = new File( filename ).toPath();

try( Stream< String > lines = Files.lines( path, StandardCharsets.UTF_8 ) ) {
    lines.onClose( () -> System.out.println("Done!") ).forEach( System.out::println );
}
```

Stream的方法**onClose** 返回一个等价的有额外句柄的Stream，当Stream的close（）方法被调用的时候这个句柄会被执行。

Stream API、Lambda表达式还有接口默认方法和静态方法支持的方法引用，是Java 8对软件开发的现代范式的响应。

#### 3.日期时间API（JSR310） 

​	Java 8引入了新的日期时间API（JSR 310）改进了日期时间的管理。日期和时间管理一直是Java开发人员最痛苦的问题。`java.util.Date`和后来的`java.util.Calendar`一点也没有改变这个情况（甚至让人们更加迷茫）。

因为上面这些原因，产生了[Joda-Time](http://www.joda.org/joda-time/) ，可以替换Java的日期时间API。[Joda-Time](http://www.joda.org/joda-time/)深刻影响了 Java 8新的日期时间API，Java 8吸收了[Joda-Time](http://www.joda.org/joda-time/) 的精华。新的java.time包包含了所有关于日期、时间、日期时间、时区、Instant（跟日期类似但精确到纳秒）、duration（持续时间）和时钟操作的类。设计这些API的时候很认真地考虑了这些类的不变性（从`java.util.Calendar`吸取的痛苦教训）。如果需要修改时间对象，会返回一个新的实例。

让我们看看一些关键的类和用法示例。第一个类是Clock，Clock使用时区来访问当前的instant, date和time。Clock类可以替换 `System.currentTimeMillis()` 和 `TimeZone.getDefault()`.

```java
// Get the system clock as UTC offset
final Clock clock = Clock.systemUTC();
System.out.println( clock.instant() );
System.out.println( clock.millis() );
```

控制台输出如下：

```java
2020-09-01T15:19:29.282Z
1397315969360
```

其他类我们看看`LocalTime`和`LocalDate`。`LocalDate`只保存有ISO-8601日期系统的日期部分，有时区信息，相应地，`LocalTime`只保存ISO-8601日期系统的时间部分，没有时区信息。`LocalDate`和`LocalTime`都可以从Clock对象创建。

```java
// Get the local date and local time
final LocalDate date = LocalDate.now();
final LocalDate dateFromClock = LocalDate.now( clock );
System.out.println( date );
System.out.println( dateFromClock );
// Get the local date and local time
final LocalTime time = LocalTime.now();
final LocalTime timeFromClock = LocalTime.now( clock );
System.out.println( time );
System.out.println( timeFromClock );
```

控制台输出如下：

```java
2014-04-12
2014-04-12
11:25:54.568
15:25:54.568
```

`LocalDateTime`类合并了`LocalDate`和`LocalTime`，它保存有ISO-8601日期系统的日期和时间，但是没有时区信息。让我们看一个简单的例子。

```java
// Get the local date/time
final LocalDateTime datetime = LocalDateTime.now();
final LocalDateTime datetimeFromClock = LocalDateTime.now( clock );
System.out.println( datetime );
System.out.println( datetimeFromClock );
```

输出如下：

```java
2014-04-12T11:37:52.309
2014-04-12T15:37:52.309
```

如果您需要一个类持有日期时间和时区信息，可以使用`ZonedDateTime`，它保存有ISO-8601日期系统的日期和时间，而且有时区信息。让我们看一些例子：

```java
// Get the zoned date/time
final ZonedDateTime zonedDatetime = ZonedDateTime.now();
final ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now( clock );
final ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now( ZoneId.of( "America/Los_Angeles" ) );
System.out.println( zonedDatetime );
System.out.println( zonedDatetimeFromClock );
System.out.println( zonedDatetimeFromZone );
```

输出如下：

```java
2014-04-12T11:47:01.017-04:00[America/New_York]
2014-04-12T15:47:01.017Z
2014-04-12T08:47:01.017-07:00[America/Los_Angeles]
```

最后让我们看看Duration类，Duration持有的时间精确到纳秒。它让我们很容易计算两个日期中间的差异。让我们来看一下：

```java
// Get duration between two dates
final LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
final LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 23, 59, 59);
final Duration duration = Duration.between( from, to );
System.out.println( "Duration in days: " + duration.toDays() );
System.out.println( "Duration in hours: " + duration.toHours() );
```

上面的例子计算了两个日期（2014年4月16日和2014年5月16日）之间的持续时间（基于天数和小时）输出如下：

```java
Duration in days: 365
Duration in hours: 8783
```

对于Java 8的新日期时间的总体印象还是比较积极的。一部分是因为有经历实战的Joda-Time的基础，还有一部分是因为日期时间终于被认真对待而且听取了开发人员的声音

#### 4.Nashorn javascript引擎

`Java 8`提供了一个新的`Nashorn javascript`引擎，它允许我们在JVM上运行特定的javascript应用。`Nashorn javascript`引擎只是`javax.script.ScriptEngine`另一个实现，而且规则也一样，允许Java和JavaScript互相操作。这里有个小例子：

```java
ScriptEngineManager manager = new ScriptEngineManager();
ScriptEngine engine = manager.getEngineByName( "JavaScript" );
System.out.println( engine.getClass().getName() );
System.out.println( "Result:" + engine.eval( "function f() { return 1; }; f() + 1;" ) );
```

输出如下：

```java
jdk.nashorn.api.scripting.NashornScriptEngine
Result: 2
```



#### 5.Base64 

对Base64的支持最终成了Java 8标准库的一部分，非常简单易用：

```java
package com.javacodegeeks.java8.base64;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64s {

  public static void main(String[] args) {

    final String text = "Base64 finally in Java 8!";
    final String encoded = Base64.getEncoder()
      .encodeToString( text.getBytes( StandardCharsets.UTF_8 ) );
    System.out.println( encoded );

    final String decoded = new String(

      Base64.getDecoder().decode( encoded ),StandardCharsets.UTF_8 );

    System.out.println( decoded );

  }

}
```

控制台输出的编码和解码的字符串

```java
QmFzZTY0IGZpbmFsbHkgaW4gSmF2YSA4IQ==
Base64 finally in Java 8!
```

新的Base64API也支持URL和MINE的编码解码。

(**Base64.getUrlEncoder()** / **Base64.getUrlDecoder()**, **Base64.getMimeEncoder()** / **Base64.getMimeDecoder()**).

#### 6.并行数组 

Java 8新增加了很多方法支持并行的数组处理。最重要的大概是`parallelSort()`这个方法显著地使排序在多核计算机上速度加快。下面的小例子演示了这个新的方法（`parallelXXX`）的行为。

```java
package com.javacodegeeks.java8.parallel.arrays;
import java.util.Arrays;

import java.util.concurrent.ThreadLocalRandom;
public class ParallelArrays {

  public static void main( String[] args ) {
    long[] arrayOfLong = new long [ 20000 ];       

    Arrays.parallelSetAll( arrayOfLong,

                          index -> ThreadLocalRandom.current().nextInt( 1000000 ) );

    Arrays.stream( arrayOfLong ).limit( 10 ).forEach(

      i -> System.out.print( i + " " ) );

    System.out.println();
    Arrays.parallelSort( arrayOfLong );
    Arrays.stream( arrayOfLong ).limit( 10 ).forEach(
      i -> System.out.print( i + " " ) );
    System.out.println();
  }
}
```

这一小段代码使用**parallelSetAll()** t方法填充这个长度是2000的数组，然后使用**parallelSort()** 排序。这个程序输出了排序前和排序后的10个数字来验证数组真的已经被排序了。示例可能的输出如下（请注意这些数字是随机产生的）

```java
Unsorted: 591217 891976 443951 424479 766825 351964 242997 642839 119108 552378
Sorted: 39 220 263 268 325 607 655 678 723 793
```



#### 7.并发 

在新增Stream机制与lambda的基础之上，在`java.util.concurrent.ConcurrentHashMap`中加入了一些新方法来支持聚集操作。同时也在`java.util.concurrent.ForkJoinPool`类中加入了一些新方法来支持共有资源池（`common pool`）。

新增的`java.util.concurrent.locks.StampedLock`类提供一直基于容量的锁，这种锁有三个模型来控制读写操作（它被认为是不太有名的`java.util.concurrent.locks.ReadWriteLock`类的替代者）。

在`java.util.concurrent.atomic`包中还增加了下面这些类：

```java
DoubleAccumulator
DoubleAdder
LongAccumulator
LongAdder
```



### 四、新的工具

#### 1.Nashorn引擎：jjs

`jjs`是个基于`Nashorn`引擎的命令行工具。它接受一些JavaScript源代码为参数，并且执行这些源代码。例如，我们创建一个具有如下内容的`func.js`文件：

```javascript
function f() {
  return 1;
};
print( f() + 1 );
```

我们可以把这个文件作为参数传递给`jjs`使得这个文件可以在命令行中执行

```html
<span style="font-size: 13px;">jjs func.js</span>
```

输出结果如下

```java
2
```



#### 2.类依赖分析工具：jdeps

`Jdeps`是一个功能强大的命令行工具，它可以帮我们显示出包层级或者类层级java类文件的依赖关系。它接受class文件、目录、jar文件作为输入，默认情况下，`jdeps`会输出到控制台。

作为例子，让我们看看现在很流行的Spring框架的库的依赖关系报告。为了让报告短一些，我们只分析一个`jar:org.springframework.core-3.0.5.RELEASE.jar`

`jdeps org.springframework.core-3.0.5.RELEASE.jar `这个命令输出内容很多，我们只看其中的一部分，这些依赖关系根绝包来分组，如果依赖关系在`classpath`里找不到，就会显示`not found`

````
jdeps H:\执行项目\new_zxba\.metadata\.plugins\org.springframework.ide.eclipse.osgi.targetdefinition\2.9.1.201203220057-RELEASE\target\org.springframework.core-3.0.1.RELEASE-A.jar
````

```xml
</pre>
<pre class="brush:java">org.springframework.core-3.0.5.RELEASE.jar -> C:\Program Files\Java\jdk1.8.0\jre\lib\rt.jar
  org.springframework.core (org.springframework.core-3.0.5.RELEASE.jar)
  -> java.io
  -> java.lang
  -> java.lang.annotation
  -> java.lang.ref
  -> java.lang.reflect
  -> java.util
  -> java.util.concurrent
  -> org.apache.commons.logging                         not found
  -> org.springframework.asm                            not found
  -> org.springframework.asm.commons                    not found
  org.springframework.core.annotation (org.springframework.core-3.0.5.RELEASE.jar)
  -> java.lang
  -> java.lang.annotation
  -> java.lang.reflect
  -> java.util</pre>
<pre>
```





### 五、JVM的新特性  

 	JVM内存永久区已经被``metaspace``替换（JEP 122）。

​     JVM参数 ``-XX:PermSize`` 和 ``–XX:MaxPermSize``被``XX:MetaSpaceSize ``和`` -XX:MaxMetaspaceSize``代替。

````
jps 
jstat -gc 16928 1000 5
````

````
S0C:survivor space 0 的总量
S1C:survivor space 1 的总量
S0U:survivor space 0 已使用容量
S1U:survivor space 1 已使用的容量
EC:eden space 的总量
EU:eden space 已使用容量
OC:old space的总量
OU:old space 已使用情况
MC:meta space(本地内存) 的总量
MU：meta space 已使用情况
CCSC:压缩类空间大小
CCSU:压缩类空间使用大小
YGC:young generation GC event的总次数
YGCT:young generation GC event的总时间
FGC:full GC 的总次数
FGCT:full GC 的总时长
GCT:YGCT+FGCT，垃圾回收总时长
````

