<script setup lang="ts">
import { Chart, Pie, Tooltip } from 'vue3-charts'
import GraphContainer from '../containers/GraphContainer.vue'
import { emotionLabel } from '@/utils/translate'

interface Props {
  summary: any
}

defineProps<Props>()
</script>

<template>
  <GraphContainer title="Resumen general">
    <div class="flex justify-between p-5 mx-24">
      <div class="w-full">
        <Chart
          direction="circular"
          :size="{ width: 400, height: 400 }"
          :config="{ controlHover: false }"
          :data="summary"
        >
          <template #layers>
            <Pie
              :data-keys="['emotion', 'count']"
              :pie-style="{ innerRadius: 100, padAngle: 0.01 }"
            />
          </template>

          <template #widgets>
            <Tooltip
              :config="{
                emotion: { label: 'Estado' },
                count: { label: 'Total' }
              }"
            />
          </template>
        </Chart>
      </div>

      <div class="mr-10 w-40">
        <h2 class="text-xl font-bold mb-2">Estados</h2>
        <p v-for="emotion in summary" :key="emotion.emotion">
          <span class="font-bold text-xl">{{ emotion.count }}</span>
          {{ emotionLabel(emotion.emotion) }}
        </p>
      </div>
    </div>
  </GraphContainer>
</template>
