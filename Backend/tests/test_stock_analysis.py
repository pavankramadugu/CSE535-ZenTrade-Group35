import pytest
from unittest.mock import patch, Mock
import os
import sys
module_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'lambda'))
sys.path.insert(0, module_path)
from stock_analysis import get_recommendations, build_response

@pytest.fixture
def mock_yfinance_ticker():
    with patch('yfinance.Ticker') as mock_ticker:
        mock_ticker.return_value.info = {'shortName': 'Apple Inc.'}
        yield mock_ticker

@pytest.fixture
def mock_functions():
    with patch('stock_analysis.get_tickers') as mock_get_tickers, \
         patch('stock_analysis.get_date_range') as mock_get_date_range, \
         patch('stock_analysis.get_weights') as mock_get_weights, \
         patch('stock_analysis.fetch_stock_data') as mock_fetch_stock_data, \
         patch('stock_analysis.calculate_metrics') as mock_calculate_metrics, \
         patch('stock_analysis.select_stocks') as mock_select_stocks:
        yield mock_get_tickers, mock_get_date_range, mock_get_weights, mock_fetch_stock_data, mock_calculate_metrics, mock_select_stocks

def test_get_recommendations(mock_yfinance_ticker, mock_functions):
    # Mock data and functions
    mock_functions[0].return_value = ['AAPL', 'GOOGL']
    mock_functions[1].return_value = ('2022-01-01', '2022-01-31')
    mock_functions[2].return_value = {'low_risk': 0.2, 'medium_risk': 0.5, 'high_risk': 0.3}
    mock_functions[3].return_value = {'AAPL': [10.0, 12.0, 11.5], 'GOOGL': [2000.0, 2050.0, 2030.0]}
    mock_functions[4].return_value = {'AAPL': 0.05, 'GOOGL': 0.03}
    mock_functions[5].return_value = {'AAPL': 0.05, 'GOOGL': 0.03}

    # Test get_recommendations function
    recommendations = get_recommendations(['USA', 'Canada'], 'Monthly', 'medium_risk')

    # Check if recommendations match the expected result
    expected_recommendations = ['Apple Inc.']
    assert list(set(recommendations)) == expected_recommendations

def test_build_response():
    # Test build_response function
    recommendations = ['Apple Inc.', 'Google LLC']
    response = build_response(recommendations)

    # Check if response structure and content are correct
    expected_response = {
        "statusCode": 200,
        "headers": {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json'
        },
        "body": '{"recommendations": ["Apple Inc.", "Google LLC"]}'
    }
    assert response == expected_response