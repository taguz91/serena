import { onMounted, ref, watch } from 'vue'
import { MediaUtils } from '../utils/media'

export const useMediaScreen = () => {
  const mediaStreamReady = ref(false)
  const hasError = ref(false)
  const videoWidth = ref(0)
  const videoHeight = ref(0)
  const shouldRotate = true

  watch(mediaStreamReady, () => {
    if (mediaStreamReady.value) {
      const media = MediaUtils.getMediaStreamInfo()

      videoWidth.value = media.actualWidth
      videoHeight.value = media.actualHeight
    }
  })

  onMounted(() => {
    MediaUtils.loadMediaStream(
      () => {
        mediaStreamReady.value = true
      },
      () => {
        hasError.value = true
      }
    )
  })

  return {
    mediaStreamReady,
    hasError,
    videoWidth,
    videoHeight,
    shouldRotate
  }
}
