<template>
  <NModal
    v-model:show="show"
    :mask-closable="false"
    preset="dialog"
    class="w-[500px]"
    title="Creación de un nuevo docente"
    :positive-text="props.id ? 'Actualizar' : 'Crear'"
    negative-text="Cancelar"
    @positive-click="onPositiveClick"
    @negative-click="onNegativeClick"
  >
    <template #icon>
      <NIcon class="text-slate-800">
        <Users />
      </NIcon>
    </template>

    <SmallSpinner v-if="isLoading" />

    <NForm v-else ref="formRef" class="mt-6" :model="model">
      <NFormItem label="Nombre" required>
        <NInput v-model:value="model.name" placeholder="Juan Perez" />
      </NFormItem>

      <NFormItem label="Correo" required>
        <NInput v-model:value="model.email" placeholder="email@dominio.com" />
      </NFormItem>
    </NForm>
  </NModal>
</template>

<script setup lang="ts">
import { ref, toRef } from 'vue'

import { NForm, NFormItem, NIcon, NInput, NModal, type FormInst } from 'naive-ui'
import { useVModel } from '@vueuse/core'
import { Users } from '@vicons/tabler'

import { useTeacher } from '@/composable'
import SmallSpinner from '@/components/shared/SmallSpinner.vue'

interface Props {
  modelValue: boolean
  id?: string
}

const props = withDefaults(defineProps<Props>(), {
  id: undefined
})

const id = toRef(props, 'id')
const emit = defineEmits<{
  (event: 'update:modelValue', value: boolean): void
}>()

const formRef = ref<FormInst | null>(null)
const { isLoading, teacherForm: model, save } = useTeacher(id)

const show = useVModel(props, 'modelValue', emit)

const onPositiveClick = () => {
  save(formRef.value)
  show.value = false
  emit('update:modelValue', false)
}

const onNegativeClick = () => {
  show.value = false
  emit('update:modelValue', false)
}
</script>
