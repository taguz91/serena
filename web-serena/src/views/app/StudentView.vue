<template>
  <AppLayout>
    <template #side>
      <div class="p-4 mb-2 border-b" v-for="methodology in methodologies" :key="methodology.id">
        <p class="font-bold mb-3">{{ methodology.name }}</p>
        <p>
          {{ methodology.summary }}
        </p>
      </div>
    </template>

    <DetailContainer current="Estudiante">
      <GeneralChart :summary="summary" />

      <div class="my-4">
        <SubjectSummary
          v-for="studentSubject in subjects"
          :student-subject="studentSubject"
          :id-student="id.toString()"
          :key="studentSubject.id"
        />
      </div>
    </DetailContainer>
  </AppLayout>
</template>

<script setup lang="ts">
import { toRef } from 'vue'
import { useRoute } from 'vue-router'

import GeneralChart from '@/components/chart/GeneralChart.vue'
import DetailContainer from '@/components/containers/DetailContainer.vue'
import SubjectSummary from '@/components/containers/SubjectSummary.vue'
import AppLayout from '@/components/layouts/AppLayout.vue'
import { useEmotionSummary } from '@/composable/students/useEmotionSummary'
import { useStudentSubjectsSummary } from '@/composable/students/useStudentSubjectsSummary'

const route = useRoute()
const id = toRef(route.params, 'id')

const { summary, methodologies } = useEmotionSummary(id)

const { subjects } = useStudentSubjectsSummary(id)
</script>
