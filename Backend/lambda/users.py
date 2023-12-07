# users.py

from pymongo import MongoClient
from bson import json_util
import os
import json

def handle_users_request(path, data):
    """
    Handle various user-related requests based on the provided path.

    Parameters:
    - path (str): Path indicating the type of user-related operation.
    - data (dict): Data associated with the request.

    Returns:
    - dict: Response containing the result of the user operation.
    """
    # Connect to the MongoDB database
    client = MongoClient(os.getenv("DB_URL"))
    db = client[os.getenv("DB_NAME")]
    users_collection = db[os.getenv("COLLECTION_NAME")]

    if path == "/userscreation":
        return create_user(users_collection, data)
    elif path == "/usersupdation":
        return update_user(users_collection, data)
    elif path == "/usersreading":
        return read_user(users_collection, data)
    elif path == "/usersdeletion":
        return delete_user(users_collection, data)
    else:
        return {"error": "Invalid user endpoint"}

def create_user(users_collection, data):
    """
    Create a new user and insert it into the database.

    Parameters:
    - users_collection: MongoDB collection for storing user data.
    - data (dict): Data of the new user.

    Returns:
    - dict: Response containing the result of the user creation.
    """
    try:
        # Insert the new user into the 'users' collection
        result = users_collection.insert_one(data)

        # Construct the response
        response = {
            "statusCode": 200,
            "headers": {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            },
            "body": json.dumps({
                "inserted_id": str(result.inserted_id),
                "message": "User created successfully"
            })
        }

        return response
    except Exception as e:
        # If there is an error, send an error response
        response = {
            "statusCode": 500,
            "headers": {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            },
            "body": json.dumps({
                "error": str(e)
            })
        }

        return response

def update_user(users_collection, data):
    """
    Update an existing user in the database.

    Parameters:
    - users_collection: MongoDB collection for storing user data.
    - data (dict): Data for updating the user.

    Returns:
    - dict: Response containing the result of the user update.
    """
    try:
        # Update the user with the specified username
        result = users_collection.update_one(
            {"user_name": data['email']},
            {"$set": data}
        )

        # Check if the update was successful
        if result.modified_count > 0:
            response = {
                "statusCode": 200,
                "body": json.dumps({
                    "message": "User updated successfully"
                })
            }
        else:
            response = {
                "statusCode": 404,
                "body": json.dumps({
                    "error": "User not found"
                })
            }

        return response
    except Exception as e:
        # If there is an error, send an error response
        response = {
            "statusCode": 500,
            "body": json.dumps({
                "error": str(e)
            })
        }

        return response

def read_user(users_collection, data):
    """
    Retrieve user information from the database.

    Parameters:
    - users_collection: MongoDB collection for storing user data.
    - data (dict): Data containing the user information.

    Returns:
    - dict: Response containing the user information.
    """
    try:
        # If a specific username is provided, query that user
        if 'email' in data:
            user_name = data['email']
            user = users_collection.find_one({"user_name": user_name})

            if user:
                response = {
                    "statusCode": 200,
                    "headers": {
                        'Access-Control-Allow-Origin': '*',
                        'Content-Type': 'application/json'
                    },
                    "body": json_util.dumps({
                        "user": user
                    })
                }
            else:
                response ={
                    "statusCode": 404,
                    "headers": {
                        'Access-Control-Allow-Origin': '*',
                        'Content-Type': 'application/json'
                    },
                    "body": json.dumps({
                        "error": "User not found"
                    })
                }
        else:
            # Retrieve all users if no specific username is provided
            users = list(users_collection.find({}))
            response = {
                "statusCode": 200,
                "headers": {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json'
                },
                "body": json.dumps({
                    "users": users
                })
            }

        return response
    except Exception as e:
        # If there is an error, send an error response
        response = {
            "statusCode": 500,
            "headers": {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json'
            },
            "body": json.dumps({
                "error": str(e)
            })
        }

        return response

def delete_user(users_collection, data):
    """
    Delete a user from the database.

    Parameters:
    - users_collection: MongoDB collection for storing user data.
    - data (dict): Data containing the user information for deletion.

    Returns:
    - dict: Response containing the result of the user deletion.
    """
    try:
        # Delete the user with the specified username
        result = users_collection.delete_one({"user_name": data['email']})

        # Check if the deletion was successful
        if result.deleted_count > 0:
            response = {
                "statusCode": 200,
                "body": json.dumps({
                    "message": "User deleted successfully"
                })
            }
        else:
            response = {
                "statusCode": 404,
                "body": json.dumps({
                    "error": "User not found"
                })
            }

        return response
    except Exception as e:
        # If there is an error, send an error response
        response = {
            "statusCode": 500,
            "body": json.dumps({
                "error": str(e)
            })
        }

        return response

if __name__ == "__main__":
    
    test_data = {"appetite":"Low","email":"check@gmaiul.com","firstname":"abcd","frequency":"Quarterly","lastname":"efgh","recommendations":["Loews Corporation","SHANXI XINGHUACUN FENWINE FACTO","SHAANXI COAL INDUSTRY","WUXI APPTEC CO LTD","AGRICULTURAL BANK OF CHINA","CHINA SHENHUA ENERGY COMPANY LT","SAIC MOTOR CORPORATION LIMITED","CITIC SECURITIES CO LTD","CHINA YANGTZE POWER CO","WILL SEMICONDUCTOR CO LTD SHANG","INDUSTRIAL & COMMERCIAL BK OF C","GREAT WALL MOTOR COMPANY LIMITE","CHINA SECURITIES CO.  LTD.","ZIJIN MINING GROUP CO.LTD","KWEICHOW MOUTAI"],"stress":"High"}

    # Connect to the MongoDB database
    client = MongoClient(os.getenv("DB_URL"))
    db = client[os.getenv("DB_NAME")]
    users_collection = db[os.getenv("COLLECTION_NAME")]

    # Example: Create a new user
    create_user_response = create_user(users_collection, test_data)
    print("Create User Response:", create_user_response)

    # Example: Update an existing user
    update_user_response = update_user(users_collection, test_data)
    print("Update User Response:", update_user_response)

    # Example: Read user information
    read_user_response = read_user(users_collection, test_data)
    print("Read User Response:", read_user_response)

    # Example: Delete a user
    delete_user_response = delete_user(users_collection, test_data)
    print("Delete User Response:", delete_user_response)
