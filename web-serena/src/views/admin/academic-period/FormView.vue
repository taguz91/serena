<template>
  <NModal
    v-model:show="show"
    :mask-closable="false"
    preset="dialog"
    class="w-[500px]"
    title="Creación de un nuevo periodo académico"
    positive-text="Crear"
    negative-text="Cancelar"
    @positive-click="onPositiveClick"
    @negative-click="onNegativeClick"
  >
    <template #icon>
      <NIcon class="text-slate-800">
        <Calendar />
      </NIcon>
    </template>

    <NForm class="mt-6">
      <NFormItem label="Nombre" required>
        <NInput />
      </NFormItem>

      <NFormItem label="Estado" required>
        <NCheckbox />
      </NFormItem>

      <NAlert title="Importante" type="info">
        Si se activa el periodo académico actual, se desactivarán los demás periodos activos.
      </NAlert>
    </NForm>
  </NModal>
</template>

<script setup lang="ts">
import { NAlert, NCheckbox, NForm, NFormItem, NIcon, NInput, NModal } from 'naive-ui'
import { useVModel } from '@vueuse/core'
import { Calendar } from '@vicons/tabler'

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
