<template>
  <div class="mb-4">
    <div
      class="mx-auto"
      :style="{
        width: `${width}px`
      }"
    >
      <template v-if="isReady">
        <h4 class="text-center font-extrabold mb-2 text-2xl">Vamos a tomarte una fotografía</h4>
        <p v-if="showCompleteMessage" class="text-center mt-2 font-light text-lg">
          Se completo correctamente tu registro, es momento de cambiar de estudiante
        </p>

        <p v-else class="text-center mt-2 font-light text-lg">
          Colocate en el centro de la cámara, te tomaremos una foto en lo que parpadeas
        </p>

        <p
          v-if="confirmText"
          class="text-sm text-center mt-3 bg-green-200 text-green-600 rounded-xl p-2 font-semibold"
        >
          {{ confirmText }}
        </p>

        <p
          v-if="helpMessage !== ''"
          class="text-sm text-center mt-3 bg-blue-200 text-blue-600 rounded-xl p-2 font-semibold"
        >
          {{ helpMessage }}
        </p>
      </template>

      <template v-else>
        <h4>Estamos iniciando todo...</h4>
      </template>

      <div class="my-4 relative w-full max-w-[640px] box-border text-center">
        <video
          ref="cameraElement"
          id="main-camera"
          :class="{
            'rotate-camera': shouldRotate
          }"
          :width="width"
          :height="height"
          autoplay
          muted
          playsinline
          :style="{
            width: `${width}px`,
            height: `${height}px`
          }"
        />

        <canvas
          ref="canvasElement"
          id="overlay-canvas"
          :class="{
            'rotate-camera': shouldRotate
          }"
          :width="width"
          :height="height"
        />
      </div>
    </div>
  </div>

  <hr />

  <div class="hidden justify-center mt-[500px]">
    <template v-if="images.length > 0">
      <div v-for="(image, index) in images" :key="index" class="mx-2">
        <img :src="image" alt="Foto" class="w-32 h-32" />
      </div>
    </template>
  </div>
</template>

<script lang="ts" setup>
import { onUnmounted, ref, watch } from 'vue'
import { useFaceDetection } from '../composables/useFaceDetection'
import { useFrame } from '../composables/useFrame'
import { MediaUtils } from '../utils/media'

interface Props {
  width: number
  height: number
  shouldRotate: boolean
  infinite: boolean
  savePhoto: (photo: string) => Promise<void>
  confirmText?: string
}

const props = defineProps<Props>()

const cameraElement = ref<HTMLVideoElement | null>(null)
const canvasElement = ref<HTMLCanvasElement | null>(null)
const showCompleteMessage = ref(false)

const { isReady, start, isValid, process, helpMessage } = useFaceDetection(
  cameraElement,
  canvasElement,
  props.width,
  props.height
)

const { images, takePhoto } = useFrame(cameraElement, props.shouldRotate)

// start in the firs load
watch(cameraElement, () => {
  if (!cameraElement.value || !canvasElement.value) {
    return
  }

  setTimeout(() => {
    start()
  }, 1000)
})

// process again new frames

watch(isValid, async () => {
  if (isValid.value) {
    const image = await takePhoto()
    await props.savePhoto(image)
    showCompleteMessage.value = true
    // wait 10 seconds to change the student

    if (props.infinite) {
      setTimeout(() => {
        showCompleteMessage.value = false
        process()
      }, 10000)
    }
  }
})

onUnmounted(() => {
  // stop all video tracks
  MediaUtils.getMediaStreamInfo()
    .mediaStream.getTracks()
    .forEach((track) => {
      track.stop()
    })
})
</script>

<style scoped>
#main-camera {
  width: 100%;
  max-width: 640px;
}

#main-camera,
#overlay-canvas {
  position: absolute;
  left: 50%;
}

.rotate-camera {
  transform: translate(-50%) rotateY(180deg);
}
</style>
