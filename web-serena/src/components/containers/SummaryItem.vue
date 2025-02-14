<template>
  <div class="mb-2 w-full shadow-md p-2 rounded-md">
    <div class="flex items-center justify-between">
      <div class="flex justify-between w-full mr-6">
        <p class="text-lg">
          {{ register.topic }} -
          {{ register.createdAt }}
        </p>

        <NTag :round="true" :type="register.status === 'open' ? 'success' : 'info'">{{
          statusLabel(register.status)
        }}</NTag>
      </div>

      <div class="self-center cursor-pointer" @click="loadChart">
        <NIcon :size="25">
          <CaretDown />
        </NIcon>
      </div>
    </div>

    <div :hidden="hidden" :class="hidden ? '' : ' p-5 mx-14'">
      <p class="text-xl font-semibold">Total de estados: {{ totalSummary }}</p>

      <div class="w-full mt-4">
        <Pie v-if="dataset" :data="dataset" :options="pieOptions" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Register, Summary } from '@/interfaces'
import { CaretDown } from '@vicons/tabler'
import { NIcon, NTag } from 'naive-ui'
import { ref } from 'vue'
import { emotionColor, emotionLabel, statusLabel } from '@/utils/translate'
import type { ChartData } from 'chart.js'
import { loadChartConfig, pieOptions } from '@/helpers/loadChartConfig'
import { Pie } from 'vue-chartjs'

const props = defineProps<{
  register: Register
}>()

const hidden = ref(true)
const summary = ref<Summary[]>([])
const dataset = ref<ChartData<'pie'> | undefined>()
const totalSummary = ref(0)

const loadChart = async () => {
  await loadChartConfig()
  if (!hidden.value) {
    hidden.value = true
    return
  }
  hidden.value = !hidden.value

  const data = await fetchWrapper.get<unknown, Summary[]>(
    `/v1/register/summary/${props.register.id}`
  )

  summary.value = data.sort((a, b) => b.count - a.count)
  const total = data.reduce((acc, s) => acc + s.count, 0)
  totalSummary.value = total

  if (total === 0) {
    return
  }

  dataset.value = {
    labels: summary.value.map(
      (s) => `${((s.count * 100) / total).toFixed(2)}% ${s.count} - ${emotionLabel(s.emotion)}`
    ),
    datasets: [
      {
        label: 'Estados',
        backgroundColor: summary.value.map((s) => emotionColor(s.emotion)),
        data: summary.value.map((s) => s.count)
      }
    ]
  }
}
</script>
