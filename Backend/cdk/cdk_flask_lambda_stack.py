from aws_cdk import core
from aws_cdk import (
    aws_apigateway as apigateway,
    aws_lambda as _lambda,
    aws_logs as logs,
)
from dotenv import load_dotenv
import os

class CdkFlaskLambdaStack(core.Stack):

    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        load_dotenv()

        package_layer = _lambda.LayerVersion(
            self, "application_package_layer",
            compatible_runtimes=[
                _lambda.Runtime.PYTHON_3_9
            ],
            code=_lambda.Code.from_asset("layers/application"),
            description='The layer with all dependencies',
        )

        DB_URL = os.getenv("DB_URL")
        DB_NAME = os.getenv("DB_NAME")
        COLLECTION_NAME = os.getenv("COLLECTION_NAME")

        Recommendation_func = _lambda.Function(
            self, 'Recommendationfunction',
            runtime=_lambda.Runtime.PYTHON_3_9,
            code=_lambda.Code.asset('lambda'),
            handler='app.handler',
            timeout=core.Duration.minutes(5),
            layers=[package_layer],
            memory_size=512,
            environment={
                'DB_URL': DB_URL,
                'DB_NAME':DB_NAME,
                'COLLECTION_NAME': COLLECTION_NAME
            }
        )

        api_created = apigateway.RestApi(
            self, 'Application-api',
            cloud_watch_role=True,
            deploy_options={
                'access_log_destination': apigateway.LogGroupLogDestination(
                    logs.LogGroup(
                        self, "Application-api_log_group",
                        log_group_name="Application-api_log_group-demo",
                        retention=logs.RetentionDays.ONE_MONTH,
                        removal_policy=core.RemovalPolicy.DESTROY,
                    )
                ),
            }
        )

        # Creating an API Gateway Lambda Integration
        Recommendation_func_integration = apigateway.LambdaIntegration(
            handler=Recommendation_func
        )

        # Declaring the resource and adding a method
        Recommendation_func_api_path = api_created.root.add_resource('recommendations')

        # Adding the POST method
        Recommendation_func_api_path.add_method(
            http_method='POST',
            integration=Recommendation_func_integration
        )

        # Declaring the resource and adding a method
        user_creation_func_api_path = api_created.root.add_resource('userscreation')

        # Adding the GET method
        user_creation_func_api_path.add_method(
            http_method='POST',
            integration=Recommendation_func_integration
        )

        # Declaring the resource and adding a method
        user_updation_func_api_path = api_created.root.add_resource('usersupdation')

        # Adding the GET method
        user_updation_func_api_path.add_method(
            http_method='POST',
            integration=Recommendation_func_integration
        )

        # Declaring the resource and adding a method
        user_get_func_api_path = api_created.root.add_resource('usersreading')

        # Adding the GET method
        user_get_func_api_path.add_method(
            http_method='POST',
            integration=Recommendation_func_integration
        )

        user_deletion_func_api_path = api_created.root.add_resource('usersdeletion')

        # Adding the POST method
        user_deletion_func_api_path.add_method(
            http_method='POST',
            integration=Recommendation_func_integration
        )


        



