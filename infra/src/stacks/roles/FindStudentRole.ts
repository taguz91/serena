import {
  ManagedPolicy,
  PolicyDocument,
  Role,
  ServicePrincipal,
} from "aws-cdk-lib/aws-iam";
import { Construct } from "constructs";

interface FindStudentRoleProps {
  s3Arn: string;
}

export class FindStudentRole extends Construct {
  public readonly arn: string;

  constructor(scope: Construct, id: string, props: FindStudentRoleProps) {
    super(scope, id);

    const role = new Role(this, "FindStudentRole", {
      assumedBy: new ServicePrincipal("lambda.amazonaws.com"),
      description: "Role for find the student, in inscription records",
      managedPolicies: [
        ManagedPolicy.fromAwsManagedPolicyName(
          "service-role/AWSLambdaBasicExecutionRole",
        ),
        new ManagedPolicy(this, "FindStudentPolicy", {
          document: PolicyDocument.fromJson({
            Statement: [
              {
                Sid: "AllowReadFilesFromS3",
                Effect: "Allow",
                Action: ["S3:GetObject"],
                Resource: `${props.s3Arn}/*`,
              },
              {
                Sid: "AllowCompareFaces",
                Effect: "Allow",
                Action: ["rekognition:CompareFaces"],
                Resource: "*",
              },
            ],
          }),
        }),
      ],
    });

    this.arn = role.roleArn;
  }
}
