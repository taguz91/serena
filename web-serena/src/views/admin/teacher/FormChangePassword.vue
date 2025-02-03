<template>
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

    <NForm ref="formRef" class="mt-6" :model="model" :rules="rules">
      <NFormItem label="Nueva contraseña" required>
        <NInput v-model:value="model.password" placeholder="********" type="password" />
      </NFormItem>
    </NForm>
  </NModal>
</template>

<script setup lang="ts">
import { ref, toRef } from 'vue'

import { NForm, NFormItem, NIcon, NInput, NModal, type FormInst, type FormRules } from 'naive-ui'
import { useVModel } from '@vueuse/core'
import { Users } from '@vicons/tabler'

import { useTeachers } from '@/composable'

interface Props {
  modelValue: boolean
  id?: string
}

const props = withDefaults(defineProps<Props>(), {
  id: undefined
})

const model = ref({
  password: ''
})

const rules: FormRules = {
  email: {
    required: true,
    trigger: ['blur'],
    message: 'Ingresa un correo valido',
    type: 'email'
  }
}

const id = toRef(props, 'id')
const emit = defineEmits<{
  (event: 'update:modelValue', value: boolean): void
}>()

const formRef = ref<FormInst | null>(null)
const { updatePassword } = useTeachers()

const show = useVModel(props, 'modelValue', emit)

const onPositiveClick = () => {
  updatePassword(id.value!, model.value.password)
  show.value = false
  emit('update:modelValue', false)
}

const onNegativeClick = () => {
  show.value = false
  emit('update:modelValue', false)
}
</script>
