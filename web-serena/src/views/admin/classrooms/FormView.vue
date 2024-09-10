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

    <SmallSpinner v-if="isLoading" />

    <NForm v-else ref="formRef" class="mt-6" :model="model">
      <NFormItem label="Periodo académico" required>
        <NSelect
          filterable
          :loading="isLoadingAcademicPeriods"
          v-model:value="model.idAcademicPeriod"
          placeholder="Selecciona un periodo académico"
          :options="academicPeriods"
        >
        </NSelect>
      </NFormItem>

      <NFormItem label="Docente" required>
        <NSelect
          filterable
          :loading="isLoadingTeachers"
          v-model:value="model.idTeacher"
          placeholder="Selecciona un docente"
          :options="teachers"
        >
        </NSelect>
      </NFormItem>

      <NFormItem label="Materia" required>
        <NSelect
          filterable
          :loading="isLoadingSubjects"
          v-model:value="model.idSubject"
          placeholder="Selecciona una materia"
          :options="subjects"
        >
        </NSelect>
      </NFormItem>
    </NForm>
  </NModal>
</template>

<script setup lang="ts">
import { NForm, NFormItem, NIcon, NModal, NSelect, type FormInst } from 'naive-ui'
import { useVModel } from '@vueuse/core'
import { School } from '@vicons/tabler'
import { ref, toRef } from 'vue'
import { useClassroom } from '@/composable/classrooms/useClassroom'
import SmallSpinner from '@/components/shared/SmallSpinner.vue'
import { useOptions } from '@/composable/useOptions'

interface Props {
  id?: string
  modelValue: boolean
}

const props = withDefaults(defineProps<Props>(), {
  id: undefined
})

const id = toRef(props, 'id')
const formRef = ref<FormInst | null>(null)
const emit = defineEmits<{
  (event: 'update:modelValue', value: boolean): void
}>()

const { save, isLoading, classroomForm: model } = useClassroom(id)
const { isLoading: isLoadingAcademicPeriods, options: academicPeriods } =
  useOptions('academic-period')
const { isLoading: isLoadingTeachers, options: teachers } = useOptions('teacher')
const { isLoading: isLoadingSubjects, options: subjects } = useOptions('subject')

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
