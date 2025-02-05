<template>
  <div class="mb-2 w-full shadow-md p-2 rounded-md">
    <div class="flex items-center justify-between">
      <div class="flex justify-between w-full mr-6">
        <p class="text-lg">{{ studentSubject.name }}</p>
      </div>

      <div class="self-center cursor-pointer" @click="loadChart">
        <NIcon :size="25">
          <CaretDown />
        </NIcon>
      </div>
    </div>

    <div :hidden="hidden" :class="hidden ? '' : 'flex justify-between p-5 mx-14'">
      <div class="w-full">
        <Chart
          direction="circular"
          :size="{ width: 300, height: 300 }"
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
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { NIcon } from 'naive-ui'

import { Chart, Pie, Tooltip } from 'vue3-charts'

import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { StudentSubject, Summary } from '@/interfaces'
import { CaretDown } from '@vicons/tabler'
import { emotionLabel } from '@/utils/translate'

const props = defineProps<{
  idStudent: string
  studentSubject: StudentSubject
  idAcademicPeriods: string[]
}>()

const hidden = ref(true)
const summary = ref<Summary[]>([])

const loadChart = async () => {
  if (!hidden.value) {
    hidden.value = true
    return
  }
  hidden.value = !hidden.value

  const data = await fetchWrapper.get<unknown, Summary[]>(
    `/v1/student/summary/${props.idStudent}/subject/${props.studentSubject.id}?periods=${props.idAcademicPeriods.join(',')}`
  )

  summary.value = data
}
</script>
