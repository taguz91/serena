import { Environment } from "src/interfaces";

export const getEnvironment = (): Environment => {
  const account = process.env.AWS_ACCOUNT_ID;
  const region = process.env.AWS_REGION;
  const stage = process.env.STAGE;
  const s3Arn = process.env.S3_ARN;

  if (!account) throw new Error("AWS_ACCOUNT_ID is not defined");
  if (!region) throw new Error("AWS_REGION is not defined");
  if (!stage) throw new Error("STAGE is not defined");
  if (!s3Arn) throw new Error("S3_ARN is not defined");

  return {
    stage,
    region,
    account,
    s3Arn,
  };
};
