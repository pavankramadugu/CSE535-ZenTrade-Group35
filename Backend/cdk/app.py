#!/usr/bin/env python3
import os

from aws_cdk import core
import os 
from cdk_flask_lambda_stack import CdkFlaskLambdaStack

app = core.App()
aws_account = os.getenv("AWS_ACCOUNT")
aws_region  = os.getenv("AWS_REGION")
CdkFlaskLambdaStack(app, "CdkStack",

    env=core.Environment(account=aws_account, region=aws_region),

    )

app.synth()
