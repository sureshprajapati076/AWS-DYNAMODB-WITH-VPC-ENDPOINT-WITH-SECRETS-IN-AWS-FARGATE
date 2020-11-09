# AWS-DYNAMODB-WITH-VPC-ENDPOINT-WITH-SECRETS-IN-AWS-FARGATE

Table will be auto created during boot up. [See](https://github.com/sureshprajapati076/AWS-DYNAMODB-WITH-VPC-ENDPOINT-WITH-SECRETS-IN-AWS-FARGATE/blob/master/src/main/java/com/prajapati/dynamodb/config/DynamodbConfig.java)

https://www.youtube.com/watch?v=AuUaZKd570k

```json
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
```
For parameter Read:
```sh
arn:aws:ssm:<region>:<account-id>:parameter/<parameter-name>
```

For ECR login:
```shell script
$(aws ecr get-login --no-include-email --region us-east-1)
```
