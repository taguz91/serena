import { ref, watch, type Ref } from 'vue'

import * as faceapi from 'face-api.js'

import { useLoadFaceApi } from './useLoadFaceApi'
import { MediaUtils } from '../utils/media'
import { useFaceState } from './useFaceState'
import { MAX_FPS } from '../utils/config'

export const useFaceDetection = (
  cameraElement: Ref<HTMLVideoElement | null>,
  canvasElement: Ref<HTMLCanvasElement | null>,
  width: number,
  height: number
) => {
  const isReady = ref(false)

  const { isLoaded, load } = useLoadFaceApi()
  const { isValid, process: processFace, reset: resetFaceState } = useFaceState(width, height)

  watch(isLoaded, () => {
    if (isLoaded.value) {
      cameraElement.value!.srcObject = MediaUtils.getMediaStreamInfo().mediaStream

      cameraElement.value!.addEventListener('loadedmetadata', () => {
        // listen
        process()
      })

      isReady.value = true
    }
  })

  const process = () => {
    if (cameraElement.value!.paused || cameraElement.value!.ended) {
      return setTimeout(() => {
        process()
      }, 100)
    }

    const options = new faceapi.TinyFaceDetectorOptions()

    faceapi
      .detectAllFaces(cameraElement.value!, options)
      .withFaceLandmarks(false)
      .then(
        (
          result: faceapi.WithFaceLandmarks<
            { detection: faceapi.FaceDetection },
            faceapi.FaceLandmarks68
          >[]
        ) => {
          if (result) {
            processDetectedResult(result)
          } else {
            setTimeout(() => process())
          }
          return result
        }
      )
  }

  const processDetectedResult = (
    results: faceapi.WithFaceLandmarks<
      { detection: faceapi.FaceDetection },
      faceapi.FaceLandmarks68
    >[]
  ) => {
    const dims = faceapi.matchDimensions(canvasElement.value!, cameraElement.value!)
    const resizedResults = faceapi.resizeResults(results, dims)
    const stateManagerOutput = processFace(results)

    const resizedDetections = resizedResults[0] ?? null

    if (resizedDetections) {
      const boxDetection = {
        x: resizedDetections.detection.box.left,
        y: resizedDetections.detection.box.top,
        width: resizedDetections.detection.box.width,
        height: resizedDetections.detection.box.height
      }
      faceapi.draw.drawDetections(canvasElement.value!, boxDetection)
    } else {
      faceapi.draw.drawDetections(canvasElement.value!, resizedResults)
    }

    const box = {
      x: stateManagerOutput.drawOptions.boxLeft,
      y: stateManagerOutput.drawOptions.boxTop,
      width: stateManagerOutput.drawOptions.boxWidth,
      height: stateManagerOutput.drawOptions.boxHeight
    }

    const drawBox = new faceapi.draw.DrawBox(box, stateManagerOutput.drawOptions)
    drawBox.draw(canvasElement.value!)

    if (stateManagerOutput.end) {
      // upload random frame take in this 10 seconds
      console.log('Face is valid, take a photo')
      setTimeout(() => {
        resetFaceState()
      }, 100)
    } else {
      const delay = 1000 / MAX_FPS
      setTimeout(() => {
        process()
      }, delay)
    }
  }

  return {
    isReady,
    isValid,

    // actions
    start: load,
    process
  }
}
