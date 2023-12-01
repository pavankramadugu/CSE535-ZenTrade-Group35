# ZenTrade

ZenTrade Is a context-aware mobile app for high-net-worth individuals (HNWIs) offering personalized investment advice and portfolio rebalancing across various assets, including stocksinvestment advisory by embodying the "Guardian Angel" philosophy. This app uniquely integrates financial technology with stress monitoring, adapting to individual risk tolerance, stress levels, and trading frequency patterns."

## Readme for Executing the Database User Operations and Testing Scripts

This readme provides instructions on how to execute the user database operations, fetching recommendations and testing scripts.

## Requirements

Before running the scripts, ensure you have the following installed:

- Python 3.8+
- packaages: yfinance, pandas, niftystocks, pytickersymbols, numpy, pymongo, python-dotenv, pytest

You can install these packages using pip and follow below step and command:

Enter into the lambda folder using cd lambda then execute this command

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
    - `test_stock_analysis.py`: Contains unit tests for the functions in `stock_ana;lysis.py`.
    - `test_users.py`: Contains unit tests for the functions in `users.py`

## Running the Main Script (`stock_analysis.py`)

1. Open your command line interface (CLI).
2. Navigate to the directory(lambda) containing the script.
3. Run the script using Python:

   ```bash
   python stock_analysis.py
   ```

This script will show the recommendations fetched from the recommendation engine and print them as a list, also making a new API response.

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

