# ZenTrade

ZenTrade is a context-aware mobile app that tailors investment advice and optimizes portfolios based on individual preferences. It embraces the "Guardian Angel" philosophy, combining financial technology with stress monitoring. The app adjusts to users' risk tolerance, stress levels, and trading frequency patterns, providing a personalized and adaptive investment experience across different assets such as stocks.

## Readme for Executing the Recommendation fetching, Database User Operations and Testing Scripts

This readme provides instructions on how to execute the recommendations fetching, user database operations and testing scripts.

## Requirements

Before running the scripts, ensure you have the following installed:

- Python 3.8+
- packages: pymongo, yfinance, pandas, numpy, niftystocks, pytickersymbols, python-dotenv, pytest, etc (Which are mentioned in the requirements.txt file)

You can install these packages using pip and follow the below command:

Execute this command

```bash
pip install -r requirements.txt
```

## File Structure

- `cdk`: Contains cdk code for AWS deployment.
- `lambda`: The folder which contains the code for the AWS lambda
    - `app.py`: The starting point of the lambda which contains the code for proper routing.
    - `stock_analysis.py`: The file that deals with fetching the records from the recommendations.py file
    - `users.py`: The file that deals with the Create, read, and delete operations of the user database.
-`layers`: contains all the dependencies required for deployment of the lambda functions in AWS.
- `tests`: The directory which contains the unit-test cases
    - `test_stock_analysis.py`: Contains unit tests for the functions in `stock_analysis.py`.
    - `test_users.py`: Contains unit tests for the functions in `users.py`

## Running the Stock Analysis Script (`stock_analysis.py`)

1. Open your command line interface (CLI).
2. Navigate to the directory(lambda) using the command `cd lambda'.
3. Run the script using Python command:

   ```bash
   python stock_analysis.py
   ```

This script will show the recommendations fetched from the recommendation engine and print them as a list, also performs API response build.

## Running the Test Scripts

1. Move to the `tests` directory
2. Run the test script using Python:

   ```bash
   pytest test_stock_analysis.py
   ```
   ```bash
   pytest test_users.py
   ```

This will execute a series of unit tests defined in the script to ensure the functionality of `stock_analysis.py` and `users.py`.

