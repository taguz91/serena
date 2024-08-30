<template>
  <NModal
    v-model:show="show"
    :mask-closable="false"
    preset="dialog"
    class="w-[500px]"
    title="Creación de una nueva clase"
    positive-text="Crear"
    negative-text="Cancelar"
    @positive-click="onPositiveClick"
    @negative-click="onNegativeClick"
  >
    <template #icon>
      <NIcon class="text-slate-800">
        <School />
      </NIcon>
    </template>

    <NForm class="mt-6">
      <NFormItem label="Periodo académico" required>
        <NSelect
          filterable
          placeholder="Selecciona un periodo académico"
          :options="optionsAcademicPeriods"
        >
        </NSelect>
      </NFormItem>

      <NFormItem label="Docente" required>
        <NSelect filterable placeholder="Selecciona un docente" :options="optionsTeachers">
        </NSelect>
      </NFormItem>

      <NFormItem label="Materia" required>
        <NSelect filterable placeholder="Selecciona una materia" :options="optionsSubjects">
        </NSelect>
      </NFormItem>
    </NForm>
  </NModal>
</template>

<script setup lang="ts">
import { NForm, NFormItem, NIcon, NModal, NSelect } from 'naive-ui'
import { useVModel } from '@vueuse/core'
import { School } from '@vicons/tabler'

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

// data

const optionsAcademicPeriods = [
  {
    label: 'Octubre 2024 - Abril 2025',
    value: 'AHQH1YUWBD7T'
  },
  {
    label: 'Abril 2025 - Octubre 2025',
    value: 'AHQHUWBD7T'
  },
  {
    label: 'Octubre 2025 - Abril 2026',
    value: 'AHQH1asdawdYUWBD7T'
  },
  {
    label: 'Abril 2026 - Octubre 2026',
    value: 'lkahwdbawjk1212'
  }
]

const optionsTeachers = [
  {
    label: 'Juan Pérez',
    value: 'AHQH1YUWBD7T'
  },
  {
    label: 'María González',
    value: 'AHQHUWBD7T'
  },
  {
    label: 'Pedro Rodríguez',
    value: 'AHQH1asdawd'
  },
  {
    label: 'Ana Pérez',
    value: 'lkahwdbawjk1212'
  },
  {
    label: 'José González',
    value: 'AHQH1YUWBD7T'
  },
  {
    label: 'María Rodríguez',
    value: 'AHQHUWBD7T'
  },
  {
    label: 'Pedro Pérez',
    value: 'AHQH1asdawd'
  }
]

const optionsSubjects = [
  {
    label: 'Matemáticas',
    value: 'AHQH1YUWBD7T'
  },
  {
    label: 'Español',
    value: 'AHQHUWBD7T'
  },
  {
    label: 'Ciencias',
    value: 'AHQH1asdawd'
  },
  {
    label: 'Historia',
    value: 'lkahwdbawjk1212'
  },
  {
    label: 'Geografía',
    value: 'AHQH1YUWBD7T'
  },
  {
    label: 'Física',
    value: 'AHQHUWBD7T'
  },
  {
    label: 'Química',
    value: 'AHQH1asdawd'
  }
]
</script>
