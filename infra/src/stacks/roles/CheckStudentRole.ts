import {
  ManagedPolicy,
  PolicyDocument,
  Role,
  ServicePrincipal,
} from "aws-cdk-lib/aws-iam";
import { Construct } from "constructs";

interface CheckStudentRoleProps {
  s3Arn: string;
}

export class CheckStudentRole extends Construct {
  public readonly arn: string;

  constructor(scope: Construct, id: string, props: CheckStudentRoleProps) {
    super(scope, id);

    const role = new Role(this, "CheckStudentRole", {
      assumedBy: new ServicePrincipal("lambda.amazonaws.com"),
      description: "Role for check the student emotion in the class",
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
                Sid: "AllowDetectFacesEmotions",
                Effect: "Allow",
                Action: ["rekognition:DetectFaces"],
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
