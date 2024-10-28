import {
  DetectFacesCommand,
  DetectFacesCommandInput,
  RekognitionClient
} from "@aws-sdk/client-rekognition";
import { APIGatewayEvent } from "aws-lambda";

export const check = async (event: APIGatewayEvent) => {
  const request = JSON.parse(event.body);

  if (!request.s3Key) {
    return {
      statusCode: 422,
      body: JSON.stringify({
        message: "The s3Key is required"
      })
    };
  }

  const client = new RekognitionClient();

  const params: DetectFacesCommandInput = {
    Image: {
      S3Object: {
        Bucket: process.env.BUCKET_NAME,
        Name: request.s3Key
      }
    }
  };

  try {
    const result = await client.send(new DetectFacesCommand(params));

    if (result.FaceDetails && result.FaceDetails.length > 0) {
      const detail = result.FaceDetails[0];

      return {
        statusCode: 200,
        body: JSON.stringify({
          emotions: detail.Emotions.map(emotion => ({
            type: emotion.Type,
            confidence: emotion.Confidence
          }))
        })
      };
    }
  } catch (error) {
    console.log(`No faces detected in the image: ${request.s3Key}`, error);
  }

  return {
    statusCode: 200,
    body: JSON.stringify({
      emotions: [
        {
          type: "UNKNOWN",
          confidence: 0
        }
      ]
    })
  };
};
