# Inventory Example for Learning Mocking

This Java project contains sample code for an inventory system. The pom.xml file is configured to use [mockito](https://site.mockito.org) but you can change the dependency to a different mocking library of your choice.


## Prerequisites

- **Java** (JDK 8 or higher)
- **Maven** (3.6 or higher)

## Getting Started

1. **Clone the Repository**: Clone the project to your local machine.
    ```bash
    git clone <repository-url>
    cd <your-project-directory>
    ```

2. **Build the Project**: Compile the project using Maven.
    ```bash
    mvn clean compile
    ```

3. Run the current existing unit tests

```bash
mvn test
```

4. Generate a coverage report

```
mvn jacoco:report
```

This will generate a `target/site/jacoco/index.html` file, which you can open in your browser to view the coverage report. This is just to remember what we did before.

## Your Task

You want to add tests for the `Inventory.java` class, but notice how neither DatabaseService nor NotificationService have any concrete implementations. Your goal is to test that the Inventory class does what it's supposed to do, even though these services are not yet implemented. Accordingly, you will use mocks and stubs to implement your tests. 

You should be able to get 100% coverage for the Inventory class.
You should think about the behavior you want to make sure happens when testing (e.g., when we add inventory, we want to make sure the Inventory class actually save things in the Database or that it sent notifications when it's supposed to send them but not in other times.)

Go about the task systematically:

1. first explore the code and understand what each class is doing and how the classes interact with each other
2. run the current tests and observe the current coverage
3. take each function in the Inventory class and write tests for it to ensure its coverage and to ensure it covers the different expected behavior based on its specs (i.e., the javadoc function comments)
