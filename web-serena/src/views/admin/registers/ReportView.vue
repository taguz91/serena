<template>
  <NPageHeader title="Reporte de registro" subtitle="Revisa el reporte ">
    <div v-if="register" class="mb-2">
      <h2 class="text-3xl mb-1 font-extrabold">{{ register.classroom.name }}</h2>
      <h4 class="text-xl">{{ register.classroom.academicPeriod?.name }}</h4>
      <h4 class="text-xl">
        {{ register.classroom.academicPeriod?.carrera?.description }}

        <span class="font-extrabold text-sm">{{
          register.classroom.academicPeriod?.carrera?.name
        }}</span>
      </h4>

      <hr class="my-2" />

      <h4 class="text-2xl mb-2 font-semibold">Docente: {{ register.classroom.teacher?.name }}</h4>
    </div>

    <GeneralChart :summary="summary" />
  </NPageHeader>
</template>

<script setup lang="ts">
import GeneralChart from '@/components/chart/GeneralChart.vue'
import { useRegister } from '@/composable/register/useRegister'
import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Summary } from '@/interfaces'
import { NPageHeader } from 'naive-ui'
import { onMounted, ref, toRef } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const id = toRef(route.params, 'id')

const summary = ref<Summary[]>([])

const { register } = useRegister(id)

const loadChart = async () => {
  const data = await fetchWrapper.get<unknown, Summary[]>(`/v1/register/summary/${id.value}`)

  summary.value = data
}

onMounted(() => {
  loadChart()
})
</script>
