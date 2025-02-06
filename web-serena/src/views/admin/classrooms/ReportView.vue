<template>
  <NPageHeader title="Reporte de clases" subtitle="Revisa el reporte de una clase">
    <div v-if="classroom">
      <h2 class="text-3xl mb-1 font-extrabold">{{ classroom?.name }}</h2>
      <h4 class="text-xl">{{ classroom?.academicPeriod?.name }}</h4>
      <h4 class="text-xl">
        {{ classroom?.academicPeriod?.carrera?.description }}

        <span class="font-extrabold text-sm">{{ classroom?.academicPeriod?.carrera?.name }}</span>
      </h4>

      <hr class="my-2" />

      <h4 class="text-2xl mb-2 font-semibold">Docente: {{ classroom?.teacher?.name }}</h4>
    </div>

    <NFormItem label="Selecciona un periodo académico para revisar el reporte">
      <NSelect
        filterable
        :loading="isLoadingAcademicPeriods"
        multiple
        v-model:value="academicPeriod"
        placeholder="Selecciona un periodo académico"
        :options="academicPeriods"
      >
      </NSelect>
    </NFormItem>

    <NFormItem label="Selecciona un rango de fechas para revisar el reporte">
      <NDatePicker v-model:value="range" type="daterange" clearable />
    </NFormItem>

    <hr class="my-2" />

    <div v-if="academicPeriod.length">
      <GeneralChart :summary="summary" />
    </div>
  </NPageHeader>
</template>

<script setup lang="ts">
import GeneralChart from '@/components/chart/GeneralChart.vue'
import { useClassroom } from '@/composable/classrooms/useClassroom'
import { useClassroomSummary } from '@/composable/classrooms/useClassroomSummary'
import { useOptions } from '@/composable/useOptions'
import { NDatePicker, NFormItem, NPageHeader, NSelect } from 'naive-ui'
import { ref, toRef, watch } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const id = toRef(route.params, 'id')
const range = ref<[number, number]>()
const academicPeriod = ref<string[]>([])

const { classroom } = useClassroom(id)
const { isLoading: isLoadingAcademicPeriods, options: academicPeriods } =
  useOptions('academic-period')

const { summary, loadByDate, reset } = useClassroomSummary(id, academicPeriod)

watch(range, (newValue) => {
  if (newValue) {
    loadByDate(newValue[0], newValue[1])
  } else {
    reset()
  }
})
</script>
