import { ref, type Ref } from 'vue'

export const useFrame = (cameraElement: Ref<HTMLVideoElement | null>, shouldRotate: boolean) => {
  const invisibleCanvas = document.createElement('canvas')

  const images = ref<string[]>([])

  return {
    // states
    images,

    // actions
    takePhoto() {
      const invisibleContext = invisibleCanvas.getContext('2d')

      if (!invisibleContext) {
        throw Error('Error getting canvas context')
      }

      invisibleCanvas.width = cameraElement.value!.videoWidth
      invisibleCanvas.height = cameraElement.value!.videoHeight

      invisibleContext.drawImage(
        cameraElement.value!,
        0,
        0,
        cameraElement.value!.videoWidth,
        cameraElement.value!.videoHeight
      )

      if (shouldRotate) {
        invisibleContext.scale(-1, 1)
      }

      const canvas = invisibleCanvas

      return new Promise((resolve: (value: string) => void) => {
        const image = canvas.toDataURL('image/jpeg', 0.7)

        setTimeout(() => {
          images.value.push(image)
          resolve(image)
        }, 1000)

        return image
      })
    }
  }
}
