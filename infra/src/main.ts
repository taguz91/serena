import { App } from "aws-cdk-lib";
import { config } from "dotenv";

import LambdaInfra from "./stacks/LambdaInfra";
import { getEnvironment } from "./utils";

config();

const env = getEnvironment();

const app = new App();

new LambdaInfra(app, `serena-infra-${env.stage}`, { env });

app.synth();
