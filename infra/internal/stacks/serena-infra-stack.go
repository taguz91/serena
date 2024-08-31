package stacks

import (
	"github.com/aws/aws-cdk-go/awscdk/v2"
	"github.com/aws/constructs-go/constructs/v10"
)

type SerenaInfraStackProps struct {
	StackProps awscdk.StackProps
}

func NewSerenaInfraStack(scope constructs.Construct, id string, props *SerenaInfraStackProps)  awscdk.Stack {
	
	var sprops awscdk.StackProps

	if props != nil {
		sprops = props.StackProps
	}

	stack := awscdk.NewStack(scope, &id, &sprops)

	return stack;
}