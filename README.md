# loopback-query
A Java library that generates query filters for LoopBack APIs programmatically.

## Installation

The source distribution available at this repository can be built using Maven:
`mvn install`. The generated jar file will be located under `target/`. It is also possible to run unit tests using the `mvn test` command. Release jars are found under the `releases` tab of the repository in GitHub.

### Examples

Building a query and generating the corresponding LoopBack filter:
```java
Query.newQuery()
    .setWhere(Where.newWhere(Clause.c("p", "v")))
    .addField("f1") // Request a specific field.
    .addField("f2")
    .addOrder(Order.newAsc("f1")) // Add an order clause.
    .addOrder(Order.newDesc("f2"))
    .setInclude(Include.newInclude().addSimple("p")) // Include related entities.
    .setLimit(10) // Set a limit clause.
    .setSkip(10) // Set a skip clause.
    .setCustom("skip", 10) // Set a custom clause string.
    .toURLEncodedString();
```
Building a complex where clause:
```java
Where.newWhere(
    Clause.c("p",
        Operator.OR.p(
            Clause.c("p1", Operator.LT.p(10)),
            Clause.c("p2", 1))));
```
Building a complex, nested include clause:
```java
Include.newInclude()
    .addSimple("p1")
    .addNested("p2", Include.newInclude().addSimple("p3"));
```

All new* methods, the Clause.c method and Operators can be statically imported:
```java
newWhere(
    c("p",
        OR.p(
            c("p1", LT.p(10)),
            c("p2", 1))));
```