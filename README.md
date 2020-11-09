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
arn:aws:ssm:&ltregion&gt:&ltaccount-id&gt:parameter/&ltparameter-name&gt
