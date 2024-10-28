import { CfnOutput, Stack } from "aws-cdk-lib";
import { Construct } from "constructs";

import { CheckStudentRole } from "./roles/CheckStudentRole";
import { FindStudentRole } from "./roles/FindStudentRole";
import { Environment } from "src/interfaces";

interface Props {
  env: Environment;
}

export default class LambdaInfra extends Stack {
  constructor(scope: Construct, id: string, props: Props) {
    super(scope, id, props);

    const findStudentRole = new FindStudentRole(this, "FindStudentRole", {
      s3Arn: props.env.s3Arn,
    });

    const checkStudent = new CheckStudentRole(this, "CheckStudentRole", {
      s3Arn: props.env.s3Arn,
    });

    // outputs
    new CfnOutput(this, "FindStudentRoleOutput", {
      value: findStudentRole.arn,
      description: "Role for find student role",
      exportName: this.exportNameWrapper("FindStudentRoleArn"),
    });

    new CfnOutput(this, "CheckStudentRoleOutput", {
      value: checkStudent.arn,
      description: "Role for check student role",
      exportName: this.exportNameWrapper("CheckStudentRoleArn"),
    });
  }

  private exportNameWrapper(name: string) {
    return `${this.stackName}-${name}`;
  }
}
