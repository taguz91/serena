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

    <SmallSpinner v-if="isLoading" />

    <NForm v-else ref="formRef" class="mt-6" :model="model">
      <NFormItem label="Nombre" required>
        <NInput v-model:value="model.name" placeholder="Periodo Octubre 2024 Abril 2025" />
      </NFormItem>

      <NFormItem label="Estado" required>
        <NCheckbox v-model:checked="model.isActive" />
      </NFormItem>

      <NAlert v-if="model.isActive" title="Importante" type="info">
        Si se activa el periodo académico actual, se desactivarán los demás periodos activos.
      </NAlert>
    </NForm>
  </NModal>
</template>

<script setup lang="ts">
import { NAlert, NCheckbox, NForm, NFormItem, NIcon, NInput, NModal, type FormInst } from 'naive-ui'
import { useVModel } from '@vueuse/core'
import { Calendar } from '@vicons/tabler'
import { ref, toRef } from 'vue'
import { useAcademicPeriod } from '@/composable/academicPeriods/useAcademicPeriod'
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

const { isLoading, academicPeriodForm: model, save } = useAcademicPeriod(id)

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
