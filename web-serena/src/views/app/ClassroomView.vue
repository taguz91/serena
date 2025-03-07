<template>
  <AppLayout>
    <template #side>
      <h3 class="text-xl px-4 pt-4">Se recomienda el uso de las siguientes metodologías:</h3>

      <div class="p-4 mb-4 border-b" v-for="methodology in methodologies" :key="methodology.id">
        <p class="font-bold mb-3">{{ methodology.name }}</p>
        <p>
          {{ methodology.summary }}
        </p>
      </div>

      <StudentList :students="students" />
    </template>

    <DetailContainer current="Clase">
      <div class="mb-2">
        <h2 class="text-3xl mb-1 font-extrabold">{{ classroom?.name }}</h2>
        <h4 class="text-xl">{{ classroom?.academicPeriod?.name }}</h4>
        <h4 class="text-xl">
          {{ classroom?.academicPeriod?.carrera?.description }}

          <span class="font-extrabold text-sm">{{ classroom?.academicPeriod?.carrera?.name }}</span>
        </h4>

        <hr class="my-2" />

        <h4 class="text-2xl mb-2 font-semibold">Docente: {{ classroom?.teacher?.name }}</h4>
      </div>

      <GeneralChart :summary="summary" />

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
import { computed, toRef, type ComputedRef } from 'vue'
import { useRoute } from 'vue-router'

import { NPagination } from 'naive-ui'

import AppLayout from '@/components/layouts/AppLayout.vue'
import DetailContainer from '@/components/containers/DetailContainer.vue'
import { useClassroomSummary } from '@/composable/classrooms/useClassroomSummary'
import { useRegistersClassroom } from '@/composable/classrooms/useRegistersClassroom'
import SummaryItem from '@/components/containers/SummaryItem.vue'
import GeneralChart from '@/components/chart/GeneralChart.vue'
import { useClassroom } from '@/composable/classrooms/useClassroom'
import { useAuthStore } from '@/stores/user'
import { useStudentsClassroom } from '@/composable/students/useStudentClassroom'
import { useMethodologyFilter } from '@/composable/methodology/useMethodologyFilter'
import StudentList from '@/components/shared/StudentList.vue'

const route = useRoute()

const id = toRef(route.params, 'id')
const { sessionInfo } = useAuthStore()

const { classroom } = useClassroom(id)
const { students } = useStudentsClassroom(id)

const { summary } = useClassroomSummary(id, toRef(sessionInfo?.academicPeriods ?? []))

const emotions: ComputedRef<string> = computed(() => {
  const items = summary?.value ?? []

  // sort by great number
  items.sort((a, b) => b.count - a.count)

  const emotions = items.map((item) => item.emotion)

  if (emotions.length >= 2) {
    return [emotions[0], emotions[1]].join(',')
  }

  return emotions[0] ?? ''
})

const { metaData, currentPage, getPage, registers } = useRegistersClassroom(id)

const { methodologies } = useMethodologyFilter(emotions)
</script>
