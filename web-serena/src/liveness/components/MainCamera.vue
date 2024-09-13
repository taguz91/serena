<template>
  <div v-if="!mediaStreamReady && !hasError" class="flex justify-center h-full items-center">
    <h4>Estamos iniciando todo...</h4>
  </div>

  <div v-else-if="hasError">
    <h4>Ocurrió un problema ...</h4>
  </div>

  <CameraCanvas
    v-else-if="mediaStreamReady"
    :width="videoWidth"
    :height="videoHeight"
    :shouldRotate="shouldRotate"
    :save-photo="savePhoto"
  />

  <div v-else>
    <h4>No se pudo iniciar la cámara...</h4>
  </div>
</template>

<script lang="ts" setup>
import { useMediaScreen } from '../composables/useMediaScreen'
import CameraCanvas from './CameraCanvas.vue'

defineProps<{
  savePhoto: (photo: string) => Promise<void>
}>()

const { mediaStreamReady, hasError, videoHeight, videoWidth, shouldRotate } = useMediaScreen()
</script>
