<template>
  <NModal
    v-model:show="show"
    :mask-closable="false"
    preset="dialog"
    class="w-[500px]"
    title="Creación de un nuevo docente"
    positive-text="Crear"
    negative-text="Cancelar"
    @positive-click="onPositiveClick"
    @negative-click="onNegativeClick"
  >
    <template #icon>
      <NIcon class="text-slate-800">
        <Users />
      </NIcon>
    </template>

    <NForm class="mt-6">
      <NFormItem label="Nombre" required>
        <NInput placeholder="Juan Perez" />
      </NFormItem>

      <NFormItem label="Correo" required>
        <NInput placeholder="email@dominio.com" />
      </NFormItem>

      <NFormItem label="Contraseña">
        <NInput placeholder="**********" type="password" />
      </NFormItem>
    </NForm>
  </NModal>
</template>

<script setup lang="ts">
import { NForm, NFormItem, NIcon, NInput, NModal } from 'naive-ui'
import { useVModel } from '@vueuse/core'
import { Users } from '@vicons/tabler'

const props = defineProps<{
  modelValue: boolean
}>()

const emit = defineEmits<{
  (event: 'update:modelValue', value: boolean): void
}>()

const show = useVModel(props, 'modelValue', emit)

const onPositiveClick = () => {
  show.value = false
  emit('update:modelValue', false)
}

const onNegativeClick = () => {
  show.value = false
  emit('update:modelValue', false)
}
</script>
