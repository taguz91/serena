<template>
  <div class="mb-2 w-full shadow-md p-2 rounded-md">
    <div class="flex items-center justify-between">
      <div class="flex justify-between w-full mr-6">
        <p class="text-lg">{{ studentSubject.name }}</p>
        <p class="text-slate-400 mt-2">Docente: {{ studentSubject.teacher }}</p>
      </div>

      <div class="self-center cursor-pointer" @click="loadChart">
        <NIcon :size="25">
          <CaretDown />
        </NIcon>
      </div>
    </div>

    <div :hidden="hidden" :class="hidden ? '' : 'p-5 mx-14'">
      <div class="w-full">
        <Pie v-if="dataset" :data="dataset" :options="pieOptions" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { NIcon } from 'naive-ui'

import { Pie } from 'vue-chartjs'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { StudentSubject, Summary } from '@/interfaces'
import { CaretDown } from '@vicons/tabler'
import { emotionColor, emotionLabel } from '@/utils/translate'
import type { ChartData } from 'chart.js'
import { loadChartConfig, pieOptions } from '@/helpers/loadChartConfig'

const props = defineProps<{
  idStudent: string
  studentSubject: StudentSubject
  idAcademicPeriods: string[]
}>()

const hidden = ref(true)
const summary = ref<Summary[]>([])
const dataset = ref<ChartData<'pie'> | undefined>()

const loadChart = async () => {
  await loadChartConfig()

  if (!hidden.value) {
    hidden.value = true
    return
  }
  hidden.value = !hidden.value

  const data = await fetchWrapper.get<unknown, Summary[]>(
    `/v1/student/summary/${props.idStudent}/subject/${props.studentSubject.id}?periods=${props.idAcademicPeriods.join(',')}`
  )

  summary.value = data.sort((a, b) => b.count - a.count)

  dataset.value = {
    labels: summary.value.map((s) => `${s.count} - ${emotionLabel(s.emotion)}`),
    datasets: [
      {
        label: 'Estudiantes',
        backgroundColor: summary.value.map((s) => emotionColor(s.emotion)),
        data: summary.value.map((s) => s.count)
      }
    ]
  }
}
</script>
