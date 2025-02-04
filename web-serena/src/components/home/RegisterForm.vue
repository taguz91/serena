<template>
  <div class="bg-white w-full py-10 px-14 rounded-l-lg">
    <p class="text-xl font-bold text-center mb-1">Registrarse</p>
    <p class="text-slate-500 mb-6 text-center mx-2">
      Ingresa el correo y validaremos que exista una cuenta pendiente de crear contraseña.
    </p>

    <NForm ref="formRef" :rules="rules" :model="model">
      <NFormItem path="email" label="Email">
        <NInput v-model:value="model.email" type="text" />
      </NFormItem>

      <div class="flex justify-end">
        <OutlineButton label="Registrarse" @click="handleValidateButtonClick" :loading="loading" />
      </div>

      <div class="flex justify-start mt-2 underline text-blue-400">
        <RouterLink to="/"> Ya tengo cuenta, regresar </RouterLink>
      </div>
    </NForm>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

import { NForm, NFormItem, NInput, useMessage, type FormInst, type FormRules } from 'naive-ui'
import { useAuthStore } from '@/stores/user'
import { RouterLink } from 'vue-router'
import OutlineButton from '@/components/basic/OutlineButton.vue'

const authStore = useAuthStore()
const message = useMessage()

const formRef = ref<FormInst | null>()

const model = ref({
  email: null,
  password: '******'
})
const loading = ref(false)

const rules: FormRules = {
  email: {
    required: true,
    trigger: ['blur'],
    message: 'Ingresa un correo valido',
    type: 'email'
  }
}

const handleValidateButtonClick = async (e: MouseEvent) => {
  e.preventDefault()

  formRef.value?.validate(async (errors) => {
    if (!errors) {
      authStore.returnUrl = '/welcome'
      const user = await authStore.login(model.value.email!, model.value.password!)

      if (!user) {
        message.error(
          'No se encontró una cuenta con el correo ingresado, revíselo con el administrador del sitio'
        )
      } else {
        message.success('Bienvenido a Serena, estamos validando tu cuenta.')
      }
    }
  })
}
</script>
