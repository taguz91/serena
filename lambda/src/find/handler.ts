import { APIGatewayEvent } from "aws-lambda";
import {
  RekognitionClient,
  CompareFacesCommand,
  CompareFacesCommandInput
} from "@aws-sdk/client-rekognition";

export const find = async (event: APIGatewayEvent) => {
  const request = JSON.parse(event.body);

  if (!request.s3Target) {
    return {
      statusCode: 422,
      body: JSON.stringify({
        message: "The s3Target field is required"
      })
    };
  }

  if (!request.photos) {
    return {
      statusCode: 422,
      body: JSON.stringify({
        message: "The photos field is required"
      })
    };
  }

  if (request.photos.length === 0) {
    return {
      statusCode: 422,
      body: JSON.stringify({
        message: "We need at least one photo to search"
      })
    };
  }

  const client = new RekognitionClient();

  for (const { photo, idStudent } of request.photos) {
    const params: CompareFacesCommandInput = {
      SourceImage: {
        S3Object: {
          Bucket: process.env.BUCKET_NAME,
          Name: request.s3Target
        }
      },
      TargetImage: {
        S3Object: {
          Bucket: process.env.BUCKET_NAME,
          Name: photo.photo
        }
      },
      SimilarityThreshold: 90
    };

    const command = new CompareFacesCommand(params);

    try {
      const result = await client.send(command);

      if (result.FaceMatches?.length > 1) {
        console.log(`Check the response exist more faces: ${idStudent}`, result.FaceMatches);
      }

      if (result.FaceMatches?.[0]) {
        return {
          statusCode: 200,
          body: JSON.stringify({
            idStudent
          })
        };
      }
    } catch (error) {
      throw new Error(`Error comparing faces: ${error}`);
    }
  }

  return {
    statusCode: 404,
    body: JSON.stringify({
      message: "We couldn't find any matches"
    })
  };
};
