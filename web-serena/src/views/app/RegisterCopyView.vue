<template>
  <AppLayout>
    <template #side>
      <StudentCheckList :id-register="route.params.id.toString()" />
    </template>

    <SmallSpinner v-if="isLoading" />

    <DetailContainer v-else current="Inscripción">
      <div class="flex justify-center items-center h-[400px]">
        <div class="text-center">
          <h1 class="font-bold text-2xl">La inscripción se encuentra lista para ser enviada</h1>

          <p class="font-semibold text-lg">Copia el siguiente link y envíaselo a tus estudiantes</p>

          <BlackButton @click="copyLink" label="Copiar link" class="my-2" />

          <p class="font-bold text-sm">El registro fue creado el: {{ register?.createdAt }}</p>

          <hr class="my-4" />

          <p class="font-semibold text-lg mt-2">
            Si el botón no funciona, puedes copiar el siguiente link de forma manual
          </p>

          <p class="">{{ url }}</p>
        </div>
      </div>
    </DetailContainer>
  </AppLayout>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'

import DetailContainer from '@/components/containers/DetailContainer.vue'
import AppLayout from '@/components/layouts/AppLayout.vue'
import StudentCheckList from '@/components/shared/StudentCheckList.vue'
import { useRegister } from '@/composable/register/useRegister'
import SmallSpinner from '@/components/shared/SmallSpinner.vue'
import { useMessage } from 'naive-ui'
import { computed } from 'vue'
import BlackButton from '@/components/basic/BlackButton.vue'

const route = useRoute()
const router = useRouter()
const message = useMessage()

const { isLoading, register } = useRegister(route.params.id.toString())

const url = computed(() => {
  const href = router.resolve({
    name: register.value?.status === 'inscription' ? 'public-register-check' : 'classroom-register',
    params: {
      id: route.params.id.toString()
    }
  }).href

  return `${window.location.origin}${href}`
})

const copyLink = () => {
  const input = document.createElement('input')
  input.value = url.value
  document.body.appendChild(input)
  input.select()
  document.execCommand('copy')
  document.body.removeChild(input)

  message.success('Link copiado al portapapeles')
}
</script>
