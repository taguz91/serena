<script setup lang="ts">
import { Pie } from 'vue-chartjs'
import GraphContainer from '../containers/GraphContainer.vue'
import { emotionColor, emotionLabel } from '@/utils/translate'
import type { ChartData } from 'chart.js'
import { onMounted, onUpdated, ref } from 'vue'
import type { Summary } from '@/interfaces'
import { loadChartConfig, pieOptions } from '@/helpers/loadChartConfig'

interface Props {
  summary: Summary[]
}

const props = defineProps<Props>()

const dataset = ref<ChartData<'pie'> | undefined>()
const totalSummary = ref(0)

onMounted(async () => {
  await loadChartConfig()

  prepareData()
})

onUpdated(async () => {
  prepareData()
})

const prepareData = () => {
  if (props.summary.length === 0) {
    dataset.value = undefined
    return
  }

  const summary = [...props.summary].sort((a, b) => b.count - a.count)
  const total = summary.reduce((acc, s) => acc + s.count, 0)

  totalSummary.value = total

  dataset.value = {
    labels: summary.map(
      (s) => `${((s.count * 100) / total).toFixed(2)}% ${s.count} - ${emotionLabel(s.emotion)}`
    ),
    datasets: [
      {
        label: 'Estados',
        backgroundColor: summary.map((s) => emotionColor(s.emotion)),
        data: summary.map((s) => s.count)
      }
    ]
  }
}
</script>

<template>
  <GraphContainer title="Resumen general">
    <p class="text-xl font-semibold">Total de estados: {{ totalSummary }}</p>

    <div class="p-5 mx-24">
      <div class="h-96">
        <Pie v-if="dataset" :data="dataset" :options="pieOptions" />
      </div>
    </div>
  </GraphContainer>
</template>
