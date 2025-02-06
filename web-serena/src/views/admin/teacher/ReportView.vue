<template>
  <NPageHeader title="Reporte de docente" subtitle="Revisa el reporte de un docente">
    <div v-if="teacher">
      <h2 class="text-3xl mb-1 font-extrabold">
        {{ teacher.name }}
      </h2>

      <hr class="my-2" />

      <h4 class="text-sm mb-2 font-light">{{ teacher.createdAt }}</h4>
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
import { useTeacher } from '@/composable'
import { useEmotionSummaryTeacher } from '@/composable/teachers/useEmotionSummaryTeacher'
import { useOptions } from '@/composable/useOptions'
import { NDatePicker, NFormItem, NPageHeader, NSelect } from 'naive-ui'
import { ref, toRef, watch } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const id = toRef(route.params, 'id')
const range = ref<[number, number]>()
const academicPeriod = ref<string[]>([])

const { teacher } = useTeacher(id)
const { isLoading: isLoadingAcademicPeriods, options: academicPeriods } =
  useOptions('academic-period')

const { summary, loadByDate, reset } = useEmotionSummaryTeacher(id, academicPeriod)

watch(range, (newValue) => {
  if (newValue) {
    loadByDate(newValue[0], newValue[1])
  } else {
    reset()
  }
})
</script>
