<template>
  <NModal
    v-model:show="show"
    :mask-closable="false"
    preset="dialog"
    class="w-[500px]"
    title="Creación de una nueva metodología"
    :positive-text="props.id ? 'Actualizar' : 'Crear'"
    negative-text="Cancelar"
    @positive-click="onPositiveClick"
    @negative-click="onNegativeClick"
  >
    <template #icon>
      <NIcon class="text-slate-800">
        <Calendar />
      </NIcon>
    </template>

    <SmallSpinner v-if="isLoading" />

    <NForm v-else ref="formRef" class="mt-6" :model="model">
      <NFormItem label="Nombre" required>
        <NInput v-model:value="model.name" placeholder="Metodología ..." />
      </NFormItem>

      <NFormItem label="Emociones" required>
        <NCheckboxGroup v-model:value="model.emotions">
          <NSpace item-style="display: flex;">
            <NCheckbox value="HAPPY" label="Felicidad" />
            <NCheckbox value="SAD" label="Tristeza" />
            <NCheckbox value="ANGRY" label="Enojo" />
            <NCheckbox value="CONFUSED" label="Confundido" />
            <NCheckbox value="DISGUSTED" label="Disgustado" />
            <NCheckbox value="SURPRISED" label="Sorprendido" />
            <NCheckbox value="CALM" label="Calmado" />
            <NCheckbox value="UNKNOWN" label="Desconocido" />
            <NCheckbox value="FEAR" label="Asustado" />
          </NSpace>
        </NCheckboxGroup>
      </NFormItem>

      <NFormItem label="Resumen" required>
        <NInput
          v-model:value="model.summary"
          type="textarea"
          size="large"
          placeholder="Describe en un resumen de que trata la metodología y como puede ser utilizada"
        />
      </NFormItem>
    </NForm>
  </NModal>
</template>

<script setup lang="ts">
import {
  NCheckbox,
  NCheckboxGroup,
  NForm,
  NFormItem,
  NIcon,
  NInput,
  NModal,
  NSpace,
  type FormInst
} from 'naive-ui'
import { useVModel } from '@vueuse/core'
import { Calendar } from '@vicons/tabler'
import { ref, toRef } from 'vue'
import SmallSpinner from '@/components/shared/SmallSpinner.vue'

import { useMethodology } from '@/composable/methodology/useMethodology'

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

const { isLoading, methodologyForm: model, save } = useMethodology(id)

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
