# Fintual Interview Project

[![codecov](https://codecov.io/github/nschmidtg/fintual_interview/branch/main/graph/badge.svg)](https://codecov.io/github/nschmidtg/fintual_interview)

This project is a solution to the Fintual interview challenge.

## Description

The project is a Kotlin application that helps to rebalance a portfolio of investments.
It defines a target portfolio with a set of instruments and their desired weights.
The application then calculates the necessary trades to rebalance the portfolio to match the target.

### Key Assumptions

- There could be multiple strategies to rebalance a portfolio, this implementation focuses on two: Fractional and Whole Units.
  - Fractional Strategy allows buying and selling fractional shares of instruments.
  - Whole Units Strategy only allows buying and selling whole shares of instruments. We round the number of shares down so we never spend more than the actual value of the portfolio.
- The application assumes that the initial portfolio and target portfolio are provided as input.
- The application assumes that there are no transaction costs or taxes involved in the trades.
- The application assumes that the market prices of the instruments are constant during the rebalancing process.
- The application assumes that there is sufficient liquidity in the market to execute the trades.
- The rebalancing algorithm is simple and focuses on clarity and correctness rather than optimization. In a real-world scenario, more complex algorithms might be needed to account for various factors such as transaction costs, taxes, and market impact. In that case I would have used a solver for linear programming problems, and probably another language more suited for numerical computing like Python.

### Some Design Decisions

- I split the Portfolio and TargetPortfolio into separate classes to clearly distinguish between the current state and the desired state. IMO composing the target inside the portfolio would create unnecessary coupling.
- I created a Strategy interface to define the contract for different rebalancing strategies. This allows for easy extension in the future if new strategies need to be added.
- Stock and MutualFund classes inherit from a common Instrument abstract class. This allows for polymorphism when dealing with different types of instruments, and extension in the future if new instrument types need to be added.


### Future Improvements
- Implement more sophisticated rebalancing algorithms that take into account transaction costs, taxes, and market impact.
- Add support for more types of financial instruments, such as bonds, ETFs, etc.
- Implement a user interface (CLI or web-based) to allow users to input their portfolios and target portfolios easily.
- Add functionality to fetch real-time market prices for instruments.
- Implement a strategy pattern for different Instrument instantiation, so we don't have to choose the strategy when creating the object.


### Links to LLMs conversations
- [Initial Discovery](https://gemini.google.com/share/2488a9e34ea2)
- [System Design](https://chatgpt.com/share/68f7f5e9-6be8-8012-b5cf-393cb8a1f0f9)

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
