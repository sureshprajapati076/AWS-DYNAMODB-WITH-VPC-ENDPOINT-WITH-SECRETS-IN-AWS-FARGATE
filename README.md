# AWS-DYNAMODB-WITH-VPC-ENDPOINT-WITH-SECRETS-IN-AWS-FARGATE

https://www.youtube.com/watch?v=AuUaZKd570k

{
    "Statement": [
        {
            "Action": "dynamodb:*",
            "Effect": "Deny",
            "Resource": "arn:aws:dynamodb:<your_region>:<your_account>:*",
            "Condition": {
                "StringNotEquals": {
                    "aws:SourceVpce": "<your_endpoint_id>"
                }
            }
        }
    ]
}