<template>
  <div class="bg-white w-full py-10 px-14 rounded-l-lg">
    <p class="text-xl font-bold text-center mb-1">Ingresar</p>
    <p class="text-slate-500 mb-6 text-center mx-2">
      Ingresa tu correo y contraseña para continuar, si no tienes cuenta puedes crear
    </p>

    <NForm>
      <NFormItem path="task" label="Email">
        <NInput v-model:value="model.email" type="text" />
      </NFormItem>

      <NFormItem path="task" label="Contraseña">
        <NInput v-model:value="model.password" type="password" />
      </NFormItem>

      <div class="flex justify-end">
        <NButton
          :disabled="model.email === null || model.password === null"
          type="primary"
          @click="handleValidateButtonClick"
          :loading="loading"
        >
          Continuar
        </NButton>
      </div>
    </NForm>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import { NButton, NForm, NFormItem, NInput } from 'naive-ui'
import { useAuthStore } from '@/stores/user'

const authStore = useAuthStore()

const model = ref({
  email: null,
  password: null
})
const loading = ref(false)

const handleValidateButtonClick = async (e: MouseEvent) => {
  e.preventDefault()

  if (model.value.email && model.value.password) {
    authStore.returnUrl = '/welcome'
    authStore.login(model.value.email, model.value.password)
  }
}
</script>
