import { awscdk } from "projen";
const project = new awscdk.AwsCdkTypeScriptApp({
  cdkVersion: "2.1.0",
  defaultReleaseBranch: "main",
  name: "infra-lambda-emotions",
  projenrcTs: true,
  githubOptions: {
    workflows: false,
  },
  tsconfig: {
    compilerOptions: {
      baseUrl: ".",
    },
  },
  deps: ["dotenv"],
  gitignore: [".env"],
});

project.eslint?.addRules({
  quotes: ["error", "double"],
  semi: ["error", "always"],
  indent: ["error", 2],
});

project.prettier?.addOverride({
  files: "*.ts",
  options: {
    singleQuote: true,
    semi: false,
    tabWidth: 2,
  },
});

project.synth();
