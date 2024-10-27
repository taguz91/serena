<template>
  <AppLayout>
    <template #side>
      <StudentList />
    </template>

    <DetailContainer current="Clase">
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
import StudentList from '@/components/shared/StudentList.vue'
import DetailContainer from '@/components/containers/DetailContainer.vue'
import { useClassroomSummary } from '@/composable/classrooms/useClassroomSummary'
import { useRegistersClassroom } from '@/composable/classrooms/useRegistersClassroom'
import SummaryItem from '@/components/containers/SummaryItem.vue'
import GeneralChart from '@/components/chart/GeneralChart.vue'

const route = useRoute()

const id = toRef(route.params, 'id')

const { summary } = useClassroomSummary(id)

const { metaData, currentPage, getPage, registers } = useRegistersClassroom(id)
</script>
