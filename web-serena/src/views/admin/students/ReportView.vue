<template>
  <NPageHeader title="Reporte de estudiante" subtitle="Revisa el reporte de un estudiante">
    <div v-if="student">
      <h2 class="text-3xl mb-1 font-extrabold">
        {{ student.name }}
      </h2>

      <h4 class="text-xl">{{ student.identification }}</h4>

      <hr class="my-2" />

      <h4 class="text-sm mb-2 font-light">{{ student.createdAt }}</h4>
    </div>

    <NFormItem label="Selecciona un periodo académico para revisar el reporte">
      <NSelect
        filterable
        :loading="isLoadingAcademicPeriods"
        multiple
        v-model:value="academicPeriod"
        placeholder="Selecciona un periodo académico"
        :options="academicPeriods"
      >
      </NSelect>
    </NFormItem>

    <hr class="my-2" />

    <div v-if="academicPeriod.length">
      <GeneralChart :summary="summary" />

      <div class="my-4">
        <SubjectSummary
          v-for="studentSubject in subjects"
          :student-subject="studentSubject"
          :id-academic-periods="academicPeriod"
          :id-student="id.toString()"
          :key="studentSubject.id"
        />
      </div>
    </div>
  </NPageHeader>
</template>

<script setup lang="ts">
import GeneralChart from '@/components/chart/GeneralChart.vue'
import SubjectSummary from '@/components/containers/SubjectSummary.vue'
import { useEmotionSummary } from '@/composable/students/useEmotionSummary'
import { useStudent } from '@/composable/students/useStudent'
import { useStudentSubjectsSummary } from '@/composable/students/useStudentSubjectsSummary'
import { useOptions } from '@/composable/useOptions'
import { NFormItem, NPageHeader, NSelect } from 'naive-ui'
import { ref, toRef } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const id = toRef(route.params, 'id')
const academicPeriod = ref<string[]>([])

const { student } = useStudent(id)
const { isLoading: isLoadingAcademicPeriods, options: academicPeriods } =
  useOptions('academic-period')

const { summary } = useEmotionSummary(id, academicPeriod)

const { subjects } = useStudentSubjectsSummary(id, academicPeriod)
</script>
