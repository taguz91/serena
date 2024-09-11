import * as faceapi from 'face-api.js'
import {
  FACE_AREA_TOLERANCE_PERCENT,
  MIN_FACE_AREA_PERCENT,
  MIN_FRAMES_FACE_STATE
} from '../utils/config'
import { ref } from 'vue'

const getAreaBox = (imgWidth: number, imgHeight: number) => {
  const areaHeight = imgHeight * 0.75
  const areaWidth = Math.min(imgWidth * 0.75, imgHeight * 0.75)
  const areaLeft = imgWidth / 2 - areaWidth / 2
  const areaTop = imgHeight / 2 - areaHeight / 2

  return [areaLeft, areaTop, areaWidth, areaHeight]
}

export const useFaceState = (width: number, height: number) => {
  const numFramesCorrect = ref(0)
  const isValid = ref(false)

  function isFaceBoxAreBiggerThanMin(faceBox: faceapi.Box) {
    const [, , areaWidth, areaHeight] = getAreaBox(width, height)

    const totalArea = areaWidth * areaHeight
    const faceAreaPercent = (faceBox.area * 100) / totalArea
    const faceAreaTolerance = FACE_AREA_TOLERANCE_PERCENT
    const minFaceAreaPercent = MIN_FACE_AREA_PERCENT
    const isBigger = faceAreaPercent + faceAreaTolerance >= minFaceAreaPercent
    console.log(
      `isFaceBoxAreBiggerThanMin: ${isBigger} Face area: ${faceAreaPercent}% Minimum: ${
        minFaceAreaPercent - faceAreaTolerance
      }%`
    )
    return isBigger
  }

  return {
    isValid,
    reset() {
      isValid.value = false
      numFramesCorrect.value = 0
    },
    process(
      faces: faceapi.WithFaceLandmarks<
        { detection: faceapi.FaceDetection },
        faceapi.FaceLandmarks68
      >[]
    ) {
      const [areaLeft, areaTop, areaWidth, areaHeight] = getAreaBox(width, height)
      let helpMessage = ''

      switch (faces.length) {
        case 0:
          numFramesCorrect.value = 0
          helpMessage = 'No se encuentra un rostro, por favor acerque su rostro a la cámara'
          break

        case 1:
          if (isFaceBoxAreBiggerThanMin(faces[0].detection.box)) {
            numFramesCorrect.value = numFramesCorrect.value + 1

            if (numFramesCorrect.value >= MIN_FRAMES_FACE_STATE) {
              isValid.value = true
            }
          } else {
            helpMessage = 'Acercar el rostro a la cámara'
          }
          break

        default:
          numFramesCorrect.value = 0
          helpMessage = 'Demasiadas rostros detectados, por favor una persona a la vez'
          break
      }

      const drawOptions = {
        boxColor: 'rgba(29, 82, 64, 1)',
        boxHeight: areaHeight,
        boxLeft: areaLeft,
        boxTop: areaTop,
        boxWidth: areaWidth
      }

      return {
        drawOptions: drawOptions,
        helpMessage: helpMessage,
        end: isValid.value
      }
    }
  }
}
