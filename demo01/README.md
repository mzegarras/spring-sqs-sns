
https://github.com/awspring/spring-cloud-aws/discussions/697
https://stackoverflow.com/questions/77050763/how-to-remove-javatype-in-messageattributes-when-sending-a-message-using-sqstemp

https://howtodoinjava.com/spring-cloud/aws-sqs-with-spring-cloud-aws/


{
"Version": "2012-10-17",
"Statement": [{
"Effect": "Allow",
"Principal": {
"Service": "service.amazonaws.com"
},
"Action": [
"kms:GenerateDataKey*",
"kms:Decrypt"
],
"Resource": "*"
}]
}