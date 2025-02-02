<template>
  <AppLayout>
    <template #side>
      <StudentTeacherList />
    </template>

    <DetailContainer current="Clase">
      <div class="mb-2">
        <h2 class="text-2xl mb-1">{{ classroom?.name }}</h2>
        <h4 class="text-xl">{{ classroom?.academicPeriod?.name }}</h4>
        <h4 class="text-xl">
          {{ classroom?.academicPeriod?.carrera?.description }}

          <span class="font-extrabold text-sm">{{ classroom?.academicPeriod?.carrera?.name }}</span>
        </h4>

        <hr class="my-2" />

        <h4 class="text-2xl mb-2">
          {{ classroom?.teacher?.name }}
        </h4>
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
import { toRef } from 'vue'
import { useRoute } from 'vue-router'

import { NPagination } from 'naive-ui'

import AppLayout from '@/components/layouts/AppLayout.vue'
import DetailContainer from '@/components/containers/DetailContainer.vue'
import { useClassroomSummary } from '@/composable/classrooms/useClassroomSummary'
import { useRegistersClassroom } from '@/composable/classrooms/useRegistersClassroom'
import SummaryItem from '@/components/containers/SummaryItem.vue'
import GeneralChart from '@/components/chart/GeneralChart.vue'
import { useClassroom } from '@/composable/classrooms/useClassroom'
import StudentTeacherList from '@/components/shared/StudentTeacherList.vue'

const route = useRoute()

const id = toRef(route.params, 'id')

const { classroom } = useClassroom(id)

const { summary } = useClassroomSummary(id)

const { metaData, currentPage, getPage, registers } = useRegistersClassroom(id)
</script>
