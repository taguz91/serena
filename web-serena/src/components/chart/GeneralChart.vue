<script setup lang="ts">
import { Pie } from 'vue-chartjs'
import GraphContainer from '../containers/GraphContainer.vue'
import { emotionColor, emotionLabel } from '@/utils/translate'
import type { ChartData } from 'chart.js'
import { onMounted, ref } from 'vue'
import type { Summary } from '@/interfaces'
import { loadChartConfig, pieOptions } from '@/helpers/loadChartConfig'

interface Props {
  summary: Summary[]
}

const props = defineProps<Props>()

const dataset = ref<ChartData<'pie'> | undefined>()

onMounted(async () => {
  await loadChartConfig()
  const summary = [...props.summary].sort((a, b) => b.count - a.count)

  dataset.value = {
    labels: summary.map((s) => `${s.count} - ${emotionLabel(s.emotion)}`),
    datasets: [
      {
        label: 'Estudiantes',
        backgroundColor: summary.map((s) => emotionColor(s.emotion)),
        data: summary.map((s) => s.count)
      }
    ]
  }
})
</script>

<template>
  <GraphContainer title="Resumen general">
    <div class="p-5 mx-24">
      <div class="w-full h-96">
        <Pie v-if="dataset" :data="dataset" :options="pieOptions" />
      </div>
    </div>
  </GraphContainer>
</template>
