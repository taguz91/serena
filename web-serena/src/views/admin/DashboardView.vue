<template>
  <NPageHeader
    title="Últimos registros creados"
    subtitle="Se mostraran los últimos 10 registros creados, para revisarlos"
  >
    <NDataTable :columns="columns" :data="data" bordered :loading="isLoading" />
  </NPageHeader>
</template>

<script setup lang="ts">
import { fetchWrapper } from '@/helpers/fetch_wrapper'
import type { Paginate, Register } from '@/interfaces'
import { NDataTable, NPageHeader, type DataTableColumns } from 'naive-ui'
import { onMounted, ref } from 'vue'

const data = ref<Register[]>([])
const isLoading = ref<boolean>(true)

const columns: DataTableColumns<Register> = [
  {
    title: 'Subject',
    key: 'classroom.subject.name'
  },
  {
    title: 'Teacher',
    key: 'classroom.teacher.name'
  },
  {
    title: 'Date',
    key: 'date'
  }
]

onMounted(async () => {
  const getLastRegisters = async () => {
    const data = await fetchWrapper.get<unknown, Paginate<Register>>(`/v1/register?size=10`)

    return data
  }

  const { data: registers } = await getLastRegisters()

  data.value = registers

  isLoading.value = false
})
</script>
