import pytest
from unittest.mock import patch, Mock
import os
import sys
module_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'lambda'))
sys.path.insert(0, module_path)
from users import create_user, update_user, read_user, delete_user

def test_create_user():
    test_data = {
        "appetite": "Low",
        "email": "test@example.com",
        "firstname": "Vishnu",
        "lastname": "k",
        "recommendations": ["S1", "S2"],
        "stress": "High",
    }

    # Mock the MongoClient and collection
    mock_users_collection = Mock()
    mock_insert_result = Mock(inserted_id="mocked_id")
    mock_users_collection.insert_one.return_value = mock_insert_result

    # Patch the MongoClient to return the mock collection
    with patch('users.MongoClient') as mock_mongo_client:
        mock_mongo_client.return_value.__getitem__.return_value = mock_users_collection

        response = create_user(mock_users_collection, test_data)

    assert response["statusCode"] == 200
    assert "inserted_id" in response["body"]
    assert "User created successfully" in response["body"]

def test_update_user():
    test_data = {
        "appetite": "Low",
        "email": "test@example.com",
        "firstname": "Vishnu",
        "lastname": "k",
        "recommendations": ["S1", "S2"],
        "stress": "High",
    }

    # Mock the MongoClient and collection
    mock_users_collection = Mock()
    mock_update_result = Mock(modified_count=1)
    mock_users_collection.update_one.return_value = mock_update_result

    # Patch the MongoClient to return the mock collection
    with patch('users.MongoClient') as mock_mongo_client:
        mock_mongo_client.return_value.__getitem__.return_value = mock_users_collection

        response = update_user(mock_users_collection, test_data)

    assert response["statusCode"] == 200
    assert "User updated successfully" in response["body"]

def test_read_user():
    test_data = {"email": "test@example.com"}

    # Mock the MongoClient and collection
    mock_users_collection = Mock()
    mock_user_data = test_data = {
        "appetite": "Low",
        "email": "test@example.com",
        "firstname": "Vishnu",
        "lastname": "k",
        "recommendations": ["S1", "S2"],
        "stress": "High",
    }
    mock_users_collection.find_one.return_value = mock_user_data

    # Patch the MongoClient to return the mock collection
    with patch('users.MongoClient') as mock_mongo_client:
        mock_mongo_client.return_value.__getitem__.return_value = mock_users_collection

        response = read_user(mock_users_collection, test_data)

    assert response["statusCode"] == 200
    assert "user" in response["body"]

def test_delete_user():
    test_data = {"email": "test@example.com"}

    # Mock the MongoClient and collection
    mock_users_collection = Mock()
    mock_delete_result = Mock(deleted_count=1)
    mock_users_collection.delete_one.return_value = mock_delete_result

    # Patch the MongoClient to return the mock collection
    with patch('users.MongoClient') as mock_mongo_client:
        mock_mongo_client.return_value.__getitem__.return_value = mock_users_collection

        response = delete_user(mock_users_collection, test_data)

    assert response["statusCode"] == 200
    assert "User deleted successfully" in response["body"]