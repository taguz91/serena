<template>
  <SinglePageLayout>
    <div class="flex flex-col items-center justify-center h-3/5 m-auto w-full">
      <AppIcon />

      <p class="my-6 mx-auto w-3/5 text-center text-xl font-semibold text-slate-500">
        Bienvenido a Serena, una aplicación web para la gestión del estado de animo y la salud
        mental de los estudiantes.
      </p>

      <hr class="w-full my-2 bg-slate-700" />

      <p v-if="needCreatePassword" class="text-sm text-slate-400 w-4/5 text-center my-2">
        Bienvenido, debes ingresar la contraseña para completar tu registro.
      </p>

      <p v-else class="text-sm text-slate-400 w-4/5 text-center my-2">
        Tu cuenta esta siendo validada, cuando un administrados verifique tus datos podrás continuar
        al sistema.
      </p>

      <div v-if="needCreatePassword">
        <NForm ref="formRef" :rules="rules" :model="model">
          <NFormItem path="password" label="Contraseña">
            <NInput v-model:value="model.password" type="password" />
          </NFormItem>

          <div class="flex justify-center">
            <NButton
              :disabled="model.password === ''"
              type="primary"
              @click="handleValidateButtonClick"
              :loading="loading"
            >
              Continuar
            </NButton>
          </div>
        </NForm>
      </div>

      <div class="mx-auto mt-4">
        <NButton type="error" bordered ghost @click="logout"> Volver al login </NButton>
      </div>
    </div>
  </SinglePageLayout>
</template>

<script lang="ts" setup>
import SinglePageLayout from '@/components/layouts/SinglePageLayout.vue'
import AppIcon from '@/components/shared/AppIcon.vue'
import { useUpdateSession } from '@/composable/teachers/useSession'
import { useAuthStore } from '@/stores/user'
import { NButton, NForm, NFormItem, NInput, type FormInst, type FormRules } from 'naive-ui'
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

const formRef = ref<FormInst | null>()

const model = ref({
  password: ''
})

const rules: FormRules = {
  password: {
    required: true,
    trigger: ['blur'],
    message: 'Ingresa la contraseña'
  }
}

const router = useRouter()
const userStore = useAuthStore()
const { updatePassword } = useUpdateSession(() => {
  loading.value = false
  logout()
})

const needCreatePassword = ref(false)
const loading = ref(false)

const logout = () => {
  userStore.logout()
}

const handleValidateButtonClick = async (e: MouseEvent) => {
  e.preventDefault()

  formRef.value?.validate((errors) => {
    if (errors) {
      return
    }
    loading.value = true

    updatePassword(model.value.password)
  })
}

onMounted(() => {
  const teacher = userStore.user
  if (!teacher) return

  needCreatePassword.value =
    teacher.lastLogin == null || teacher.lastLogin === '' || teacher.lastLogin === undefined

  const appMode = localStorage.getItem('appMode')

  // only use admin panel if is admin and  has not selected app mode
  if (teacher.isAdmin && appMode !== 'app') {
    router.push({ name: 'admin-home' })
    return
  }

  if (teacher.isActive) {
    router.push({ name: 'app-home' })
    return
  }
})
</script>
