<template>
  <AppLayout>
    <template #side>
      <StudentTeacherList />
    </template>

    <DetailContainer current="Perfil">
      <div class="flex justify-end">
        <NButton type="info" @click="showModal"> Cambiar contraseña </NButton>
      </div>

      <NForm ref="formRef" class="w-[600px]">
        <NFormItem label="Nombre" required>
          <NInput v-model:value="form.name" placeholder="Juan Perez" />
        </NFormItem>

        <NFormItem label="Correo" required>
          <NInput v-model:value="form.email" placeholder="email@dominio.com" />
        </NFormItem>

        <div class="flex justify-end">
          <NButton
            :disabled="form.email === null || form.name === null"
            type="primary"
            @click="handleValidateButtonClick"
          >
            Actualizar
          </NButton>
        </div>
      </NForm>
    </DetailContainer>

    <NModal
      v-model:show="show"
      :mask-closable="false"
      preset="dialog"
      class="w-[500px]"
      title="Cambiar contraseña"
      :positive-text="'Actualizar'"
      negative-text="Cancelar"
      @positive-click="onPositiveClick"
      @negative-click="onNegativeClick"
    >
      <template #icon>
        <NIcon class="text-slate-800">
          <Users />
        </NIcon>
      </template>

      <NForm ref="formRef" class="mt-6" :model="form" :rules="rules">
        <NFormItem label="Nueva contraseña" required>
          <NInput v-model:value="form.password" placeholder="********" type="password" />
        </NFormItem>
      </NForm>
    </NModal>
  </AppLayout>
</template>

<script setup lang="ts">
import DetailContainer from '@/components/containers/DetailContainer.vue'
import AppLayout from '@/components/layouts/AppLayout.vue'
import StudentTeacherList from '@/components/shared/StudentTeacherList.vue'
import { useSessionProfile, useUpdateSessionProfile } from '@/composable/session/useProfile'
import { Users } from '@vicons/tabler'
import {
  NButton,
  NForm,
  NFormItem,
  NIcon,
  NInput,
  NModal,
  type FormInst,
  type FormRules
} from 'naive-ui'
import { ref } from 'vue'

const { form } = useSessionProfile()
const { update } = useUpdateSessionProfile()

const formRef = ref<FormInst | null>(null)
const show = ref(false)

const rules: FormRules = {
  password: {
    required: true,
    trigger: ['blur'],
    message: 'Ingresa una contraseña'
  }
}

const onPositiveClick = () => {
  if (form.value.password) {
    update({
      password: form.value.password
    })
  }

  show.value = false
}

const showModal = () => {
  show.value = true
}

const onNegativeClick = () => {
  show.value = false
}

const handleValidateButtonClick = async (e: MouseEvent) => {
  e.preventDefault()

  formRef.value?.validate((errors) => {
    if (!errors) {
      update(form.value)
    }
  })
}
</script>
