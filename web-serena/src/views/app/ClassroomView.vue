<template>
  <AppLayout>
    <template #side>
      <StudentList />
    </template>

    <DetailContainer current="Clase">
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
              <span class="font-bold text-xl">{{ emotion.count }}</span> {{ emotion.emotion }}
            </p>
          </div>
        </div>
      </GraphContainer>

      <!-- list of registers -->

      <div class="my-4">
        <SummaryItem v-for="register in registers" :register="register" :key="register.id" />

        <div class="mt-4 flex justify-end">
          <NPagination
            :page-count="metaData.pages"
            v-model:page="currentPage"
            @update:page="getPage"
          />
        </div>
      </div>
    </DetailContainer>
  </AppLayout>
</template>

<script setup lang="ts">
import AppLayout from '@/components/layouts/AppLayout.vue'

import StudentList from '@/components/shared/StudentList.vue'
import DetailContainer from '@/components/containers/DetailContainer.vue'
import GraphContainer from '@/components/containers/GraphContainer.vue'
import { useClassroomSummary } from '@/composable/classrooms/useClassroomSummary'
import { useRoute } from 'vue-router'
import { toRef } from 'vue'
import { Chart, Pie, Tooltip } from 'vue3-charts'
import { useRegistersClassroom } from '@/composable/classrooms/useRegistersClassroom'
import SummaryItem from '@/components/containers/SummaryItem.vue'
import { NPagination } from 'naive-ui'

const route = useRoute()

const id = toRef(route.params, 'id')

const { summary } = useClassroomSummary(id)

const { metaData, currentPage, getPage, registers } = useRegistersClassroom(id)
</script>
