# Fintual Interview Project

This project is a solution to the Fintual interview challenge.

## Description

The project is a Kotlin application that helps to rebalance a portfolio of investments.
It defines a target portfolio with a set of instruments and their desired weights.
The application then calculates the necessary trades to rebalance the portfolio to match the target.

## Running the tests

The project has both unit and integration tests.

### Unit tests

To run the unit tests, execute the following command:

```bash
./gradlew test
```

### Integration tests

To run the integration tests, execute the following command:

```bash
./gradlew intTest
```

### All tests

To run all tests, you can run:

```bash
./gradlew test intTest
```

## Code Style

The project uses Spotless to enforce code style.
To check the code style, run:

```bash
./gradlew spotlessCheck
```

To apply the code style, run:

```bash
./gradlew spotlessApply
```
