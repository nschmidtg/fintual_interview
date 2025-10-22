# Fintual Interview Project

[![codecov](https://codecov.io/github/nschmidtg/fintual_interview/branch/main/graph/badge.svg)](https://codecov.io/github/nschmidtg/fintual_interview)

This project is a solution to the Fintual interview challenge.

## Description

The project is a Kotlin application that helps to rebalance a portfolio of investments.
It defines a target portfolio with a set of instruments and their desired weights.
The application then calculates the necessary trades to rebalance the portfolio to match the target.

### Key Assumptions

- There could be multiple strategies to rebalance a portfolio, this implementation focuses on two: Fractional and Whole Units.
  - Fractional Strategy allows buying and selling fractional shares of instruments. Normally used for mutual funds, cryptocurrencies, ETFs, etc.
  - Whole Units Strategy only allows buying and selling whole shares of instruments. We round the number of shares down so we never spend more than the actual value of the portfolio. Normally used for stocks and other instruments that don't allow fractional shares.
- The application assumes that the initial portfolio and target portfolio are provided as input.
- The application assumes that there are no transaction costs or taxes involved in the trades.
- The application assumes that the market prices of the instruments are constant during the rebalancing process.
- The application assumes that there is sufficient liquidity in the market to execute the trades.
- The rebalancing algorithm is simple and focuses on clarity and correctness rather than optimization. In a real-world scenario, more complex algorithms might be needed to account for various factors such as transaction costs, taxes, and market impact. In that case I would have used a solver for linear programming problems, and probably another language more suited for numerical computing like Python.

### Some Design Decisions

- I split the Portfolio and TargetPortfolio into separate classes to clearly distinguish between the current state and the desired state. IMO composing the target inside the portfolio would create unnecessary coupling.
- I created a Strategy interface to define the contract for different rebalancing strategies. This allows for easy extension in the future if new strategies need to be added.
- Stock and MutualFund classes inherit from a common Instrument abstract class. This allows for polymorphism when dealing with different types of instruments, and extension in the future if new instrument types need to be added.
- Added an additional TradingService to be able to execute the trades calculated by the PortfolioBalanceService and check the finalPortfolio composition, value, freeCash (in case of an instrument using Whole Units strategy).
- Added a Main function with a simple case with 3 investment instrument (2 stocks and 1 mutual fund) to demonstrate how the application works, along with the respective logs.


### Future Improvements
- Implement more sophisticated rebalancing algorithms that take into account transaction costs, taxes, and market impact.
- Add support for more types of financial instruments, such as bonds, ETFs, etc.
- Implement a user interface (CLI or web-based) to allow users to input their portfolios and target portfolios easily.
- Add functionality to fetch real-time market prices for instruments.
- Implement a strategy pattern for different Instrument instantiation, so we don't have to choose the strategy when creating the object.


### Links to LLMs conversations
- [Initial Discovery](https://gemini.google.com/share/2488a9e34ea2)
- [System Design](https://chatgpt.com/share/68f7f5e9-6be8-8012-b5cf-393cb8a1f0f9)


## Running the Application

This project is a Kotlin application built with Gradle. Java Development Kit (JDK) 21 or newer**: This project is configured to use JDK 21. You can download it from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use a version manager like `sdkman`.

### Building the Project

```bash
./gradlew build && ./gradlew run
```

You should be able to see the logs of the main function with a dummy example:

```
Current Holdings:
Initial Portfolio Total Value: 3000.0
Stock(CMPA), Quantity: 10.0, Value: $1000.00
MutualFund(MFX), Quantity: 40.0, Value: $1000.00
Stock(CMPB), Quantity: 20.0, Value: $1000.00

Target Allocations:
Stock(CMPA), Target Percentage: 40.0%
Stock(CMPB), Target Percentage: 34.0%
MutualFund(MFX), Target Percentage: 26.0%

Instrument: CMPA, Current Quantity: 10.0, Buying Quantity: 2.0. Value to trade $200.00. New Quantity: 12.0. New Instrument Value: $1200.0
Instrument: CMPB, Current Quantity: 20.0, Buying Quantity: 0.0. Value to trade $0.00. New Quantity: 20.0. New Instrument Value: $1000.0
Instrument: MFX, Current Quantity: 40.0, Selling Quantity: -8.8. Value to trade $-220.00. New Quantity: 31.2. New Instrument Value: $780.0

Final Portfolio Total Value: 2980.0 with Free Cash: $20.00.
```


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
