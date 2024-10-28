import { App } from "aws-cdk-lib";
import { Template } from "aws-cdk-lib/assertions";
import LambdaInfra from "../src/stacks/LambdaInfra";

test("Snapshot", () => {
  const app = new App();
  const stack = new LambdaInfra(app, "test", {
    env: {
      s3Arn: "arn:aws:s3:::test",
      stage: "dev",
      region: "us-east-1",
      account: "test",
    },
  });

  const template = Template.fromStack(stack);
  expect(template.toJSON()).toMatchSnapshot();
});
