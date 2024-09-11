import { ref } from 'vue'

import * as faceapi from 'face-api.js'

const loadFaceDetectionModel = (url: string) => {
  const promise = faceapi.nets.tinyFaceDetector.load(url)

  promise.then(() => {
    console.log('Face detection model loaded')
  })

  return promise
}

const loadLandmarkModel = (url: string) => {
  const promise = faceapi.nets.faceLandmark68Net.load(url)

  promise.then(() => {
    console.log('Lamdmark model loaded')
  })

  return promise
}

export const useLoadFaceApi = () => {
  const isLoaded = ref(false)
  const promises: Promise<void>[] = []

  return {
    isLoaded,
    load() {
      if (promises.length !== 0) {
        return
      }

      const url = '/weights/'

      promises.push(loadFaceDetectionModel(url))
      promises.push(loadLandmarkModel(url))

      return Promise.all(promises).then(() => {
        isLoaded.value = true
      })
    }
  }
}
