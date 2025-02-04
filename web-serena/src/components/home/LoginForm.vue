<template>
  <div class="bg-white w-full py-10 px-14 rounded-l-lg">
    <p class="text-xl font-bold text-center mb-1">Ingresar</p>
    <p class="text-slate-500 mb-6 text-center mx-2">
      Ingresa tu correo y contraseña para continuar, si no tienes cuenta puedes crear
    </p>

    <NForm ref="formRef" :rules="rules" :model="model">
      <NFormItem path="email" label="Email">
        <NInput v-model:value="model.email" type="text" />
      </NFormItem>

      <NFormItem path="password" label="Contraseña">
        <NInput v-model:value="model.password" type="password" />
      </NFormItem>

      <div class="flex justify-end">
        <OutlineButton label="Ingresar" @click="handleValidateButtonClick" :loading="loading" />
      </div>

      <div class="flex justify-start mt-2 underline text-blue-400">
        <RouterLink to="register"> Registrarse </RouterLink>
      </div>
    </NForm>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import { NForm, NFormItem, NInput, type FormInst, type FormRules } from 'naive-ui'
import { useAuthStore } from '@/stores/user'
import { RouterLink } from 'vue-router'
import OutlineButton from '@/components/basic/OutlineButton.vue'

const authStore = useAuthStore()

const formRef = ref<FormInst | null>()

const model = ref({
  email: null,
  password: null
})
const loading = ref(false)

const rules: FormRules = {
  email: {
    required: true,
    trigger: ['blur'],
    message: 'Ingresa un correo valido',
    type: 'email'
  },
  password: {
    required: true,
    trigger: ['blur'],
    message: 'Ingresa la contraseña'
  }
}

const handleValidateButtonClick = async (e: MouseEvent) => {
  e.preventDefault()

  formRef.value?.validate((errors) => {
    if (!errors) {
      authStore.returnUrl = '/welcome'
      authStore.login(model.value.email!, model.value.password!)
    } else {
      console.log(errors)
    }
  })
}
</script>
